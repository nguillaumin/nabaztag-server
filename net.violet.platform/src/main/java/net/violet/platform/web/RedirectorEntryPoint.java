package net.violet.platform.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.endpoints.HTTPEndpoint;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.formats.EnumResponsesFormats;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.SecretTimestamp;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Le service de redirection vers les médias des partenaires Violet en accès exclusifs.
 * @author christophe - Violet
 */
public class RedirectorEntryPoint extends HTTPEndpoint {

	// Logger.
	private static final Logger LOGGER = Logger.getLogger(RedirectorEntryPoint.class);

	// this service URL
	private static final String REDIRECTOR_SERVICE_URL = Constantes.OS_SERVLET_ROOT + "/redirector?" + RedirectorEntryPoint.SECRET_TIMESTAMP + "=";

	// param names
	private static final String SECRET_TIMESTAMP = "secret";
	private static final String REDIRECT_URL = "url";
	private static final String FALLBACK_URL = "fallbackUrl";

	// default values
	private static final int EXPIRATION_DELAY_FOR_REDIRECTION = 30 * 1000; // in ms
	private static final String FAILED_REDIRECTION_URL = "http://my.violet.net";

	/**
	 * Points d'entrée 
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (final UnsupportedEncodingException ignore) {}

		RedirectorEntryPoint.LOGGER.info("Processing redirector request : " + req.getRequestURI() + "?" + req.getQueryString());

		try {
			process(req, resp);
		} catch (final APIException e) {
			RedirectorEntryPoint.LOGGER.fatal("Error processing redirector request " + req.getRequestURI() + "?" + req.getQueryString(), e);
			writeErrorMessage(resp, e, EnumResponsesFormats.JSON, null);
		}
	}

	/**
	 * Redirect servlet response to url
	 * if and only the secret timestamp was provided and not expirated
	 * Use a fallback url if provided when the timestamp is not valid
	 * @param req
	 * @param resp
	 * @throws APIException
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws APIException {

		final String fallbackUrl = getParameter(req, RedirectorEntryPoint.FALLBACK_URL, RedirectorEntryPoint.FAILED_REDIRECTION_URL);
		final String secretTimestamp = req.getParameter(RedirectorEntryPoint.SECRET_TIMESTAMP);

		String redirectUrl = getParameter(req, RedirectorEntryPoint.REDIRECT_URL, fallbackUrl);

		if ((secretTimestamp == null) || !SecretTimestamp.isValid(secretTimestamp, RedirectorEntryPoint.EXPIRATION_DELAY_FOR_REDIRECTION)) {
			// This should be protected because everyone can use this redirector URL to access private content
			redirectUrl = fallbackUrl;
		}

		Writer writer = null;
		try {
			RedirectorEntryPoint.LOGGER.info("Dispatch to secret URL : " + redirectUrl);
			final String finalUrl = (redirectUrl == null) ? RedirectorEntryPoint.FAILED_REDIRECTION_URL : redirectUrl;

			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");

			writer = resp.getWriter();
			// this is so ugly but the resp.sendRedirect method with positioning of the referer doesn't work !!
			// (referer is not set)
			writer.write("<script type='text/javascript'>");
			writer.write("document.location='" + resp.encodeRedirectURL(finalUrl) + "';");
			writer.write("</script>");

		} catch (final IOException e) {
			throw new InternalErrorException(e);
		} finally {
			IOUtils.closeQuietly(writer);
		}

	}

	/**
	 * The URL to retrieve the secret from this Locker Service
	 * @param lockerKey
	 * @return
	 */
	public static String getRedirectUrl(String lockerKey) {

		return RedirectorEntryPoint.REDIRECTOR_SERVICE_URL + lockerKey;
	}

}
