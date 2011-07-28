package net.violet.platform.api.endpoints;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.actions.APIController;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.AuthenticationManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.BadCredentialsException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.formats.EnumResponsesFormats;
import net.violet.platform.api.formats.HttpResponseHelper;

import org.apache.log4j.Logger;

/**
 * REST Servlet
 * 
 * @author chdes - Violet
 */

public class RESTEndpoint extends HTTPEndpoint {

	// Logger.
	private static final Logger LOGGER = Logger.getLogger(RESTEndpoint.class);

	// the REST URL regular expression to match the following format :
	// /rest/<response format>/<object name>/<method name>/<main param>
	private static final Pattern REST_URL_PATTERN = Pattern.compile("/rest/([\\w]+)/([\\w]+)(?:/(.[^ \\t\\n\\x0B\\f\\r\\x00-\\x1F\\x7F]+))?"); // Pattern . compile ( "/rest/([\\w]+)/([\\w]+)/([\\w]+)" ) ; position of each member in the expression
	private static final int OBJECT_NAME = 1;
	private static final int METHOD_NAME = 2;
	private static final int MAIN_PARAM = 3;

	/**
	 * Points d'entrée de la servlet.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (final UnsupportedEncodingException ignore) {}

		EnumResponsesFormats format;

		// The first thing to know is the expected response format
		// because even an error response must be in this format
		try {
			format = getResponseFormat(req);
		} catch (final APIException e) {
			writeErrorMessage(resp, e, EnumResponsesFormats.XML, null);
			return;
		}

		// now try to process or catch the API Exception..
		try {
			doProcess(req, resp, format);
		} catch (final APIException e) {
			writeErrorMessage(resp, e, format, getCallbackParam(req));
		}
	}

	/**
	 * Génère la réponse à une requète REST
	 * 
	 * @param req
	 * @param resp
	 * @throws APIException
	 */
	private void doProcess(HttpServletRequest req, HttpServletResponse resp, EnumResponsesFormats format) throws APIException {

		// Authentication process : put the Application in the request
		if (!AuthenticationManager.authenticateHttpCall(req)) {
			throw new BadCredentialsException(APIErrorMessage.UNAUTHORIZED);
		}

		// Parse the REST URL
		final String fullPath = req.getRequestURI().substring(req.getContextPath().length());
		final Matcher parsedUrl = RESTEndpoint.REST_URL_PATTERN.matcher(fullPath);

		if (!parsedUrl.matches()) {
			writeErrorMessage(resp, format, "request path " + fullPath + " does not conform to REST form : /rest/<object>/<methodName>/<paramId>", getCallbackParam(req));
			return;
		}

		// the action key is the class name of the action
		final String actionKey = "violet." + parsedUrl.group(RESTEndpoint.OBJECT_NAME) + "." + parsedUrl.group(RESTEndpoint.METHOD_NAME);

		final Action action = APIController.getAction(actionKey);

		// Populate the action params
		final String id = parsedUrl.group(RESTEndpoint.MAIN_PARAM);
		final ActionParam actionParam = new ActionParam(req, id);

		// Process the request and format the response
		String formatedResponse;
		try {

			// Delegate the req treatment to the dedicated action
			final Object response = action.processRequest(actionParam);

			if (RESTEndpoint.LOGGER.isDebugEnabled()) {
				RESTEndpoint.LOGGER.debug("Processing of API call " + fullPath + " (" + actionParam + ") returned : " + response);
			}

			// Apply the correct format to the response
			formatedResponse = (String) HttpResponseHelper.formatResp(response, format);

		} catch (final APIException bad) {
			// LOG here because we have all the advanced informations
			final String strErrMsg = "PROCESSING OF API CALL " + fullPath + " (" + actionParam + ") FAILED !";
			RESTEndpoint.LOGGER.error(strErrMsg, bad);
			throw bad;

		} catch (final java.lang.RuntimeException e) {
			// Unexpected Runtime exception !
			final String strErrMsg = "PROCESSING OF API CALL " + fullPath + "(" + actionParam + ") FAILED !";
			RESTEndpoint.LOGGER.error(strErrMsg, e);
			throw new InternalErrorException(strErrMsg);
		}

		// special callback function for JSON response
		if (format.equals(EnumResponsesFormats.JSON)) {
			final String callback = req.getParameter("callback");

			if (callback != null) {
				formatedResponse = new StringBuilder(callback).append("(").append(formatedResponse).append(");").toString();
			}
		}

		writeResponse(resp, action, format, formatedResponse);
	}
}
