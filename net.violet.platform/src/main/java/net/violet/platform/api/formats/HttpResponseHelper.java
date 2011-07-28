package net.violet.platform.api.formats;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.converters.pojo.BinaryDataWrapper;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;

/**
 * @author christophe - Violet
 */
public class HttpResponseHelper {

	// Logger.
	private static final Logger LOGGER = Logger.getLogger(HttpResponseHelper.class);

	/**
	 * @param inResp
	 * @param format
	 * @return
	 * @throws APIException
	 */
	public static Object formatResp(Object inResp, EnumResponsesFormats format) throws APIException {

		if (format == null) {
			throw new InvalidParameterException(APIErrorMessage.UNKNOWN_FORMAT, net.violet.common.StringShop.EMPTY_STRING);
		}

		try {
			if ((format == EnumResponsesFormats.JSON)) {
				BinaryDataWrapper.wrapBinaries(inResp);
				return ConverterFactory.JSON.convertTo(inResp);

			} else if (format == EnumResponsesFormats.XML_RPC) {
				// xml rpc knows already how to convert byte[] in base 64
				return inResp;

			} else if (format == EnumResponsesFormats.XML) {
				BinaryDataWrapper.wrapBinaries(inResp);
				return ConverterFactory.XML.convertTo(inResp, true);

			} else if (format == EnumResponsesFormats.YAML) {
				return ConverterFactory.YAML.convertTo(inResp);

			} else {
				throw new APIException(APIErrorMessage.UNKNOWN_FORMAT);
			}

		} catch (final ConversionException e) {
			HttpResponseHelper.LOGGER.fatal(e, e);
			throw new InternalErrorException(e.getMessage());
		}
	}

	/**
	 * JSON with callback
	 * @param inResp
	 * @param inFormat
	 * @param inJsonCallback
	 * @return
	 * @throws APIException
	 */
	public static Object formatResp(Object inResp, EnumResponsesFormats inFormat, String inJsonCallback) throws APIException {

		if ((inJsonCallback != null) && (inFormat == EnumResponsesFormats.HTML)) {
			final String jsonResp = (String) HttpResponseHelper.formatResp(inResp, EnumResponsesFormats.JSON);
			return new StringBuilder("<html><script type='text/javascript'>").append(inJsonCallback).append("(").append(jsonResp).append(");</script></html>").toString();
		}

		if ((inJsonCallback != null) && ((inFormat == EnumResponsesFormats.JSON) || (inFormat == EnumResponsesFormats.JSON_WMETA))) {
			final String jsonResp = (String) HttpResponseHelper.formatResp(inResp, inFormat);
			return new StringBuilder(inJsonCallback).append("(").append(jsonResp).append(");").toString();
		}

		return HttpResponseHelper.formatResp(inResp, inFormat);
	}

	/**
	 * Position the headers when the response is an HTTP servlet output
	 * 
	 * @param resp
	 * @param format
	 * @throws ConversionException
	 * @throws APIException
	 */
	public static void setHeaders(HttpServletResponse resp, Action action, EnumResponsesFormats format) {

		// resp.setCharacterEncoding("UTF-8");
		resp.setContentType(format.getContentType().toString());

		// resp.setContentLength(length);

		// Set the CACHE headers
		if ((action != null) && (action.isCacheable())) {
			final Date cacheExpiration = new Date(System.currentTimeMillis() + (action.getExpirationTime() * 1000));
			resp.setHeader("Expires", DateUtil.formatDate(cacheExpiration));

		} else {
			// Set to expire far in the past.
			resp.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
			resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
		}

	}

}
