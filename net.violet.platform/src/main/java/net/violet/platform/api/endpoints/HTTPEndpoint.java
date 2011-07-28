package net.violet.platform.api.endpoints;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.MissingParameterException;
import net.violet.platform.api.formats.ContentType;
import net.violet.platform.api.formats.EnumResponsesFormats;
import net.violet.platform.api.formats.HttpResponseHelper;
import net.violet.platform.api.maps.PojoMap;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Classe de base pour les APIs: endpoint HTTP.
 */
public abstract class HTTPEndpoint extends HttpServlet {

	// Logger.
	private static final Logger LOGGER = Logger.getLogger(HTTPEndpoint.class);

	/**
	 * Find the asked response format passed in a request params or try to guess
	 * it with the Accept header
	 * 
	 * @param req
	 * @return
	 * @throws APIException when the asked format is not recognized
	 */
	protected EnumResponsesFormats getResponseFormat(HttpServletRequest req) throws APIException {

		EnumResponsesFormats fmt;

		// try the first hint : the format parameter
		final String formatParam = req.getParameter("format");

		if (formatParam != null) {
			fmt = EnumResponsesFormats.getFormatFor(formatParam);

		} else {
			// guess response format by analysing the Accept header
			final String acceptHeader = req.getHeader("Accept");
			final ContentType bestResponse = ContentType.getBestContentType(acceptHeader, EnumResponsesFormats.getContentTypes());

			fmt = (bestResponse == null) ? EnumResponsesFormats.getDefault() : EnumResponsesFormats.getFormatFor(bestResponse);
		}

		// the JSON response format within script tags is in fact an HTML response
		if (fmt.equals(EnumResponsesFormats.JSON) && (req.getParameter("callback") != null)) {
			fmt = EnumResponsesFormats.HTML;
		}

		return fmt;

	}

	/**
	 * An optional callback param is used only in JSON
	 * and is the name of the JavaScript function to call with the JSON result
	 * @param req
	 * @return
	 */
	protected String getCallbackParam(HttpServletRequest req) {
		return req.getParameter("callback");
	}

	/**
	 * Look for parameter value
	 * @param req
	 * @param inParamName
	 * @param inDefaultValue
	 * @return the provided default value if param not found
	 */
	protected String getParameter(HttpServletRequest req, String inParamName, String inDefaultValue) {
		final String paramValue = req.getParameter(inParamName);
		return (paramValue == null) ? inDefaultValue : paramValue;
	}

	/**
	 * Look for parameter value
	 * @param req
	 * @param inParamName
	 * @param inMandatory 
	 * @return the param value or thrown an exception if not found
	 * @throws MissingParameterException 
	 */
	protected String getParameter(HttpServletRequest req, String inParamName, boolean inMandatory) throws MissingParameterException {
		final String paramValue = req.getParameter(inParamName);
		if (paramValue == null) {
			throw new MissingParameterException(inParamName);
		}
		return paramValue;
	}

	/**
	 * @param resp
	 * @param format
	 * @param message
	 */
	protected void writeErrorMessage(HttpServletResponse resp, EnumResponsesFormats format, String message, String callback) {

		final Map<String, Object> errResponse = new PojoMap(2);
		errResponse.put("statut", "error");
		errResponse.put("msg", message);

		String strErrMsg;
		try {
			strErrMsg = (String) HttpResponseHelper.formatResp(errResponse, format, callback);

		} catch (final Exception e) {
			strErrMsg = message;
		}

		writeResponse(resp, null, format, strErrMsg);
	}

	/**
	 * @param resp
	 * @param format
	 * @param message
	 */
	protected void writeErrorMessage(HttpServletResponse resp, APIException ex, EnumResponsesFormats format, String callback) {

		String strErrMsg;
		try {
			strErrMsg = (String) HttpResponseHelper.formatResp(ex, format, callback);

		} catch (final Exception ignore) {
			// exception can only occur when the output format is unknown which
			// is irrelevant here
			strErrMsg = ex.getMessage();
		}

		writeResponse(resp, null, format, strErrMsg);
	}

	/**
	 * @param resp
	 * @param ex
	 */
	protected void writeErrorMessage(HttpServletResponse resp, Exception ex) {

		// build a serializable exception
		final APIException internalError = (ex instanceof APIException) ? (APIException) ex : new APIException(APIErrorMessage.INTERNAL_ERROR, ex.getMessage());
		writeErrorMessage(resp, internalError, EnumResponsesFormats.JSON, null);
	}

	/**
	 * @param resp
	 * @param strMsg
	 */
	protected void writeResponse(HttpServletResponse resp, Action action, EnumResponsesFormats format, String strMsg) {

		// Now send the response to the servlet output..
		Writer writer = null;

		try {
			HttpResponseHelper.setHeaders(resp, action, format);
			writer = resp.getWriter();
			writer.write(strMsg);
			writer.flush();

		} catch (final IOException e) {
			HTTPEndpoint.LOGGER.error("Write error when writing " + strMsg + " to servlet out put !", e);

		} finally {
			IOUtils.closeQuietly(writer);
		}

	}

}
