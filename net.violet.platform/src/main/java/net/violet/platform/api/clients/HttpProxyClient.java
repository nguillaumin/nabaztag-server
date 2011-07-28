package net.violet.platform.api.clients;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.Converter;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.formats.ContentType;
import net.violet.platform.api.maps.PojoMap;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;

/**
 * Make an HTTP call on profit of an interactive application
 * 
 * @author christophe - Violet
 */
public class HttpProxyClient {

	private static final Logger LOGGER = Logger.getLogger(HttpProxyClient.class);

	/**
	 * We expect only a certain list of content types
	 */
	private static final String ACCEPT_HEADER = "application/json,application/xml,text/yaml,text/javascript,text/plain,text/xml,application/atom+xml,application/rss+xml";

	private static final List<ContentType> ACCEPTED_CONTENT_TYPES;
	static {
		ACCEPTED_CONTENT_TYPES = new ArrayList<ContentType>();
		for (final String accepted : HttpProxyClient.ACCEPT_HEADER.split(net.violet.common.StringShop.COMMA)) {
			HttpProxyClient.ACCEPTED_CONTENT_TYPES.add(new ContentType(accepted));
		}
	}

	private static final HttpClientParams HTTP_CLIENT_PARAMS;
	static {
		HTTP_CLIENT_PARAMS = new HttpClientParams();
		HttpProxyClient.HTTP_CLIENT_PARAMS.setContentCharset("UTF-8");
		HttpProxyClient.HTTP_CLIENT_PARAMS.setConnectionManagerTimeout(5 * 1000); // wait 5 secs to obtain a connection
		HttpProxyClient.HTTP_CLIENT_PARAMS.setSoTimeout(10 * 1000); // wait 10 secs for data
	}

	/**
	 * @param inUrl the url of the HTTP call
	 * @param inPojoParams a map of additional parameters to pass to the GET or
	 *            POST request
	 * @param inPojoOptions a map of additional options : - method : GET|POST -
	 *            headers : a set of headers to set - user, pwd for
	 *            authentication - async to perform the request asynchronously -
	 *            callback the name of the callback function
	 * @return a POJO structure with the status code and body of the response
	 */
	public static Map<String, Object> call(String inUrl, Map<String, Object> inPojoParams, Map<String, Object> inPojoOptions) {

		Map<String, Object> response = null;

		try {
			final URL url = new URL(inUrl);

//			if (url.getPort() != -1) {
//				return new APIException(APIErrorMessage.HTTP_REQUEST_FAILURE, "Invalid port number. (only 80 and 443 authorized)");
//			}

			// Convert our Native JavaScript objects to an PojoMap
			final PojoMap params = new PojoMap(inPojoParams);
			final PojoMap options = new PojoMap(inPojoOptions);

			// Prepare the connection
			final HttpClient httpClient = new HttpClient(HttpProxyClient.HTTP_CLIENT_PARAMS);

			// Prepare the HTTP request
			final HttpMethod method = HttpProxyClient.prepareMethodForCall(httpClient, url, params, options);

			// Execute the HTTP request
			httpClient.executeMethod(method);

			// Build a response map according to the API specifications
			response = HttpProxyClient.getRequestResult(method);

		} catch (final Exception e) {
			HttpProxyClient.LOGGER.fatal("HTTP Call from JS application failed !\n Requested URL was : " + inUrl + ((response != null) ? "\nResponse status : " + response.get("status") : ""), e);
			response = new PojoMap(3);
			response.put("status", 500);
			response.put("error", e.getMessage());
		}

		return response;
	}

	/**
	 * Build the adequate HTTP Method
	 * 
	 * @param inUrl
	 * @param inParams
	 * @param inOptions
	 * @return
	 * @throws InvalidParameterException
	 * @throws EncoderException 
	 */
	private static HttpMethod prepareMethodForCall(HttpClient httpClient, URL inUrl, PojoMap inParams, PojoMap inOptions) throws InvalidParameterException, EncoderException {

		final HttpMethod method;

		final boolean urlSerializable = inParams.containsOnlyPrimitiveTypes();
		final String methodName = inOptions.getString("method", "GET");

		// Only POST and GET support at the moment
		if (urlSerializable && "GET".equalsIgnoreCase(methodName)) {
			final GetMethod get = new GetMethod(inUrl.toExternalForm());

			if (inParams.size() > 0) {

				final String urlQueryString = inUrl.getQuery();
				if (urlQueryString == null) {
					// we are safe here
					get.setQueryString(inParams.asNameValuePairs());

				} else {
					// we must append the content of inParams after the existing query string 
					get.setQueryString(urlQueryString + "&" + inParams.toQueryString());
				}
			}

			method = get;

		} else {
			final PostMethod post = new PostMethod(inUrl.toExternalForm());
			// create the JSON body
			post.setRequestBody(inParams.asNameValuePairs());

			method = post;
		}

		// Add the optional headers passed by the user
		if (inOptions.containsKey("headers")) {
			final PojoMap headers = inOptions.getPojoMap("headers", true);
			for (final String headerName : headers.getSortedParamNames()) {
				method.setRequestHeader(headerName, headers.getString(headerName, net.violet.common.StringShop.EMPTY_STRING));
			}
		}

		// Look for an authentication credentials
		if (inOptions.containsKey("user")) {

			final Credentials creds = new UsernamePasswordCredentials(inOptions.getString("user", true), inOptions.getString("pwd", true));
			final AuthScope apiAuthScope = new AuthScope(inUrl.getHost(), inUrl.getPort());
			httpClient.getState().setCredentials(apiAuthScope, creds);

			method.setDoAuthentication(true);

		} else {
			// Set some other options
			method.setFollowRedirects(true);

		}

		// Identify ourself as the emitter
		method.setRequestHeader("Referer", "my.violet.net");
		method.setRequestHeader("Accept", HttpProxyClient.ACCEPT_HEADER);

		if (HttpProxyClient.LOGGER.isDebugEnabled()) {
			HttpProxyClient.LOGGER.debug("Sending HTTP request " + method.getName() + " to " + method.getPath());
		}

		return method;
	}

	/**
	 * Read the result and populate a pojo response in the form :
	 * <pre>
	 * { status: <i>HTTP response status</i>
	 *   error: <i>an error message (if any)</i>
	 *   body: <i>the response body, (in a parsed form or not)</i>
	 * }
	 * </pre>
	 * @param method
	 * @return
	 * @throws IOException
	 */
	private static Map<String, Object> getRequestResult(HttpMethod method) {

		final Map<String, Object> response = new HashMap<String, Object>(4);

		try {
			final int status = method.getStatusCode();

			response.put("status", new Integer(status));

			if (status != HttpStatus.SC_OK) {
				// HTTP response in error
				response.put("error", method.getStatusText());
				return response;
			}

			final ContentType contentType = new ContentType(method.getResponseHeader("Content-type").getValue());

			if (!contentType.match(HttpProxyClient.ACCEPTED_CONTENT_TYPES)) {
				// warn if the received content doesn't correspond to our accepted content
				response.put("error", "Response received in an unsupported content type : " + contentType.toString());
				if (HttpProxyClient.LOGGER.isDebugEnabled()) {
					HttpProxyClient.LOGGER.debug(method.getResponseBodyAsString());
				}
				return response;
			}

			// HTTP STATUS is 200 and we have a content-type we can try to parse.
			final String body = method.getResponseBodyAsString();

			// try to find a converter for the received content type
			Converter converter = ConverterFactory.getConverter(contentType, true);

			if (converter == null) {
				converter = ConverterFactory.guessConverterFromContent(body);
			}

			if (converter != null) {
				try {
					if (HttpProxyClient.LOGGER.isDebugEnabled()) {
						HttpProxyClient.LOGGER.debug("Parsing HTTP response with " + converter.getFormatName() + " converter");
					}
					response.put("body", converter.convertFrom(body));

				} catch (final Exception e) {
					// error.. take the body as it is and add the error message
					response.put("body", body);
					response.put("error", e.getMessage());
				}

			} else {
				response.put("body", body);
			}

		} catch (final IOException io) {
			HttpProxyClient.LOGGER.error("Error when trying to read HTTP response body : " + method.getPath() + " " + method.getPath(), io);
			response.put("status", 500);
			response.put("error", io.getMessage());

		} finally {
			// release HTTP connection
			method.releaseConnection();
		}

		return response;
	}

//  -------------------------------------------------------------------------80

	/**
	 * Test : enter the URL to call
	 * @param args
	 */
	public static void main(String[] args) {

		final String url = args[0];
		final Map<String, Object> additionalTestParam = new HashMap<String, Object>(2);
		additionalTestParam.put("famous_quote", "Hé Bé !");

		System.out.println("Calling " + url);

		final Map<String, Object> resp = HttpProxyClient.call(url, additionalTestParam, null);

		System.out.println(resp.toString());

	}

}
