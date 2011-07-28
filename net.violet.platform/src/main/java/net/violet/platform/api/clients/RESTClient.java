package net.violet.platform.api.clients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.Action.ActionType;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.endpoints.APIConstants;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.BadCredentialsException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.util.StringShop;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

/**
 * A REST client using the JSON format to retrieve responses from the API
 * 
 * @author christophe - Violet
 */
public class RESTClient implements APIClient {

	private static final Logger LOGGER = Logger.getLogger(RESTClient.class);

	// regular expression to parse the action name
	private static final Pattern actionNamePattern = Pattern.compile("violet\\.([\\w]+)\\.([\\w]+)");

	private final String mRestServiceUrl;
	private final APICaller mEmitter;
	private final HttpClient mHttpClient;

	/**
	 * Build a new REST API client with the requested authentication credentials
	 * 
	 * @param restServiceUrl , ie : api.violet.net/rest, localhost:8080/vl/rest
	 * @param inEmitter caller to make the call
	 * @throws MalformedURLException
	 */
	public RESTClient(String restServiceUrl, APICaller inEmitter) {

		// Check the validity of the provided URL
		URL apiBaseURL;
		try {
			apiBaseURL = new URL("http://" + restServiceUrl);
		} catch (final MalformedURLException e) {
			throw new IllegalArgumentException("Service URL is malformed : http://" + restServiceUrl);
		}
		this.mRestServiceUrl = apiBaseURL.toExternalForm(); // restServiceUrl.
		// endsWith("/") ?
		// restServiceUrl :
		// restServiceUrl +
		// "/";

		this.mHttpClient = new HttpClient();

		// Build the DIGEST authentication credentials for this application
		this.mEmitter = inEmitter;
		final Credentials creds = new UsernamePasswordCredentials(inEmitter.getAPIKey(), inEmitter.getAPIPassword());

		final AuthScope apiAuthScope = new AuthScope(apiBaseURL.getHost(), apiBaseURL.getPort(), APIConstants.API_REALM, HttpServletRequest.DIGEST_AUTH);

		this.mHttpClient.getState().setCredentials(apiAuthScope, creds);
		this.mHttpClient.getParams().setAuthenticationPreemptive(true);

	}

	/**
	 * Simple method call for the REST urls like
	 * rest/&lt;object&gt;/&lt;method&gt;/id NOTE : this call works only with
	 * GET requests !
	 * 
	 * @param actionName
	 * @param inMainParamValue
	 * @return
	 * @throws APIException when the API call has failed
	 */
	public Object executeMethodCall(String actionName, String inMainParamValue) throws APIException {

		try {
			// parse the action
			final Matcher parsedActionName = RESTClient.actionNamePattern.matcher(actionName);

			if (!parsedActionName.matches()) {
				throw new InvalidParameterException(APIErrorMessage.NO_SUCH_METHOD, StringShop.EMPTY_STRING);
			}

			// build the HTTP GET method
			final String actionUri = this.mRestServiceUrl + StringShop.SLASH + parsedActionName.group(1) + StringShop.SLASH + parsedActionName.group(2) + StringShop.SLASH + inMainParamValue;
			final GetMethod get = new GetMethod(actionUri);

			// retrieve the result
			return getRequestResult(get);

		} catch (final APIException e) { // comes from the API
			throw e;

		} catch (final ConnectException refused) {
			throw new APIException(APIErrorMessage.CONNECTION_REFUSED, refused);

		} catch (final Exception unexpected) {
			RESTClient.LOGGER.error("RESTClient encountered an unexpected exception when making HTTP call", unexpected);
			throw new APIException(APIErrorMessage.INTERNAL_ERROR, unexpected.getMessage());
		}

	}

	/**
	 * Use this method to pass more than one param or structured params
	 * 
	 * @param actionName
	 * @param inParam
	 * @return
	 * @throws APIException when the API call has failed
	 */
	public Object executeMethodCall(ActionType actionType, String actionName, Map<String, Object> inParams) throws APIException {

		try {
			final Matcher parsedActionName = RESTClient.actionNamePattern.matcher(actionName);

			if (!parsedActionName.matches()) {
				throw new InvalidParameterException(APIErrorMessage.NO_SUCH_METHOD, StringShop.EMPTY_STRING);
			}

			// Prepare the request uri
			final ActionParam actionParam = new ActionParam(this.mEmitter, inParams);
			String actionUri = this.mRestServiceUrl + StringShop.SLASH + parsedActionName.group(1) + StringShop.SLASH + parsedActionName.group(2);

			if (actionParam.hasMainParam()) { // create the REST uri with an id
				// :
				// /rest/<object>/<method>/<id>
				actionUri += "/" + actionParam.consomMainParam();
			}

			// Determine the HTTP methods and its parameters formats
			final HttpMethod httpMethod = prepareMethodForCall(actionType, actionUri, actionParam);

			// retrieve the result
			return getRequestResult(httpMethod);

		} catch (final APIException e) { // comes from the API
			throw e;

		} catch (final Exception unexpected) {
			throw new APIException(APIErrorMessage.INTERNAL_ERROR, unexpected.getMessage());
		}

	}

	/**
	 * @param method
	 * @return
	 * @throws IOException
	 * @throws APIException
	 * @throws ConversionException
	 */
	private Object getRequestResult(HttpMethod method) throws IOException, ConversionException, APIException {

		// tell the API that we accept only JSON result
		method.setRequestHeader("Accept", "application/json");
		method.setRequestHeader("Accept-Charset", "UTF-8");
		method.setRequestHeader("User-Agent", "net.violet.platform.api.clients.RESTClient");

		try {
			// send the request
			this.mHttpClient.executeMethod(method);

			switch (method.getStatusCode()) {
			case HttpStatus.SC_OK:
				// Open the response stream..
				final Reader reader = new InputStreamReader(method.getResponseBodyAsStream());

				// return the converted JSON response
				return ConverterFactory.JSON.convertFrom(reader);

			case HttpStatus.SC_UNAUTHORIZED:
				throw new BadCredentialsException(APIErrorMessage.UNAUTHORIZED);

			default:
				throw new APIException(APIErrorMessage.INTERNAL_ERROR, method.getStatusText());
			}

		} finally {
			// release HTTP connection
			method.releaseConnection();
		}
	}

	/**
	 * Determine the adequate HTTP Method
	 * 
	 * @param inActionType
	 * @param uri
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private HttpMethod prepareMethodForCall(ActionType inActionType, String uri, ActionParam inParams) throws UnsupportedEncodingException {

		final boolean urlSerializable = inParams.containsOnlyPrimitiveTypes();
		final ActionType theActionType;
		if (inActionType == null) {
			theActionType = ActionType.UPDATE;
		} else {
			theActionType = inActionType;
		}

		if (urlSerializable && theActionType.equals(ActionType.GET)) {
			final GetMethod get = new GetMethod(uri);
			get.setQueryString(inParams.asNameValuePairs());

			return get;
		}

		if (theActionType.equals(ActionType.UPDATE) || theActionType.equals(ActionType.GET)) {
			final PostMethod post = new PostMethod(uri);
			// create the JSON body
			final String jsonBody = ConverterFactory.JSON.convertTo(inParams);
			final RequestEntity postedJSONBody = new StringRequestEntity(jsonBody, "application/json", "UTF-8");
			post.setRequestEntity(postedJSONBody);
			return post;
		}

		if (theActionType.equals(ActionType.CREATE)) {
			final PutMethod put = new PutMethod(uri);
			// TODO..
			return put;
		}

		if (theActionType.equals(ActionType.DELETE)) {
			final DeleteMethod delete = new DeleteMethod(uri);
			// TODO..
			return delete;
		}

		return null;
	}
}
