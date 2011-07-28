package net.violet.platform.api.actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.violet.platform.api.authentication.AuthenticationManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.Converter;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.converters.QueryStringConverter;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidDataException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.UnsupportedException;
import net.violet.platform.api.formats.ContentType;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.util.StringTools;

import org.apache.commons.io.IOUtils;

/**
 * The object received by an Action to process the request.
 */
public class ActionParam extends PojoMap {

	// when not passed directly in the REST uri, the main param must be found under the key 'id'
	public static final String MAIN_PARAM_KEY = "id";

	public static final String SESSION_PARAM_KEY = "session";

	public static final String XML_DATA_PARAM_KEY = "xml-data";
	public static final String JSON_DATA_PARAM_KEY = "json-data";
	public static final String YAML_DATA_PARAM_KEY = "yaml-data";

	private final APICaller mCaller;

	/**
	 * An agnostic constructor where all the action parameters have been put in
	 * the map, including the API_KEY and API_SIGN
	 * 
	 * @param inParams
	 * @throws APIException
	 */
	public ActionParam(APICaller inEmitter, Map<String, Object> inParams) {

		super(inParams);
		this.mCaller = inEmitter;
	}

	/**
	 * The constructor to use with an HTTP request 
	 * IMPORTANT : this constructor must be called AFTER a call to 
	 * ApplicationAuthenticationManager.authenticateCall(req) 
	 * The accepted request formats are 
	 * - GET, 
	 * - POST application/x-www-form-urlencoded, 
	 * - POST application/json, 
	 * - POST text/xml, 
	 * - POST text/yaml,
	 * - PUT
	 * 
	 * @param req
	 */
	public ActionParam(HttpServletRequest req, String mainParamValue) throws APIException {

		super(16);
		this.mCaller = AuthenticationManager.getAPICallEmitter(req);

		// build the parameters map
		final ContentType contentType = new ContentType(req.getContentType());
		final String httpMethod = req.getMethod();

		try {

			if (httpMethod.equals("POST") && !contentType.match(ContentType.FORM_URL_ENCODED)) {

				// every POST body content must use the UTF-8 encoding
				if (!contentType.isUTF8()) {
					throw new UnsupportedException(APIErrorMessage.BAD_ENCODING, contentType.getCharset());
				}

				// we will have to convert the POST body according to its format

				Reader reader = null;
				final StringWriter reqBody = new StringWriter();
				try { // read all body content and free resources
					reader = req.getReader();
					IOUtils.copy(reader, reqBody);
				} catch (final IOException e) {
					throw new InternalErrorException(e.getMessage());
				} finally {
					IOUtils.closeQuietly(reader);
					reader = null;
				}

				final Map<String, Object> convertedParamsMap;
				final Converter converter = ConverterFactory.getConverter(contentType, false);

				if (converter == null) {
					throw new UnsupportedException(APIErrorMessage.UNKNOWN_FORMAT, contentType.toString());
				}

				convertedParamsMap = converter.convertFrom(reqBody.toString());

				if (convertedParamsMap != null) { // some converters will return null for an empty body
					this.putAll(convertedParamsMap);
				}

			} else if (httpMethod.equals("PUT")) {
				// PUT methods are used to upload one file at a time

				InputStream inputStream;
				final ByteArrayOutputStream bodyBuffer;
				try {
					inputStream = req.getInputStream();
					bodyBuffer = new ByteArrayOutputStream(2048);
					IOUtils.copy(inputStream, bodyBuffer);

				} catch (final IOException e) {
					throw new InternalErrorException(e.getMessage());
				}

				// store the mime-type and content of the uploaded file
				this.put("body", bodyBuffer.toByteArray());
				this.put("mime-type", contentType.getMediaType());

			}

			// request parameters are in the URL or in the POST body when it is URL encoded
			// add them in every case, because, even a PUT or a POST in JSON format may use URL parameters
			addURLParameters(req);

			if ((mainParamValue != null) && !this.containsKey(ActionParam.MAIN_PARAM_KEY)) {
				this.put(ActionParam.MAIN_PARAM_KEY, mainParamValue);
			}

		} catch (final ConversionException convertException) {
			throw new InvalidDataException(APIErrorMessage.BAD_FORMAT, convertException.getMessage());
		}

	}

	private void addURLParameters(HttpServletRequest inReq) {
		// HTTP request parameters values are String arrays >> we keep only the first value
		// special parameter names are used to code structures in JSON or XML format
		for (final Enumeration<String> names = inReq.getParameterNames(); names.hasMoreElements();) {
			final String name = names.nextElement();
			for (final String value : inReq.getParameterValues(name)) {
				this.createProperty(name, QueryStringConverter.convertParamValue(value));
			}
		}
	}

	/**
	 * @return
	 */
	public APICaller getCaller() {
		return this.mCaller;
	}

	/**
	 * Accesseur sur la clé.
	 * 
	 * @return
	 */
	public String getCallerAPIKey() {
		return this.mCaller.getAPIKey();
	}

	/**
	 * @return
	 */
	public boolean hasMainParam() {
		return this.containsKey(ActionParam.MAIN_PARAM_KEY);
	}

	/**
	 * Détermine si la map contient un paramètre donné.
	 * 
	 * @param inParamName nom du paramètre.
	 * @return <code>true</code> si la map contient le paramètre spécifié.
	 */
	public boolean hasParam(String inParamName) {
		return this.containsKey(inParamName);
	}

	/**
	 * Récupère la valeur du paramètre principal sous la forme d'une chaîne.
	 * 
	 * @param inParams les paramètres.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas une chaîne ou
	 *             n'est pas présent.
	 */
	public String getMainParamAsString() throws InvalidParameterException {
		return this.getString(ActionParam.MAIN_PARAM_KEY, true);
	}

	public Integer getMainParamAsInt() throws InvalidParameterException {
		return this.getInteger(ActionParam.MAIN_PARAM_KEY);
	}

	/**
	 * Trim the incoming string from control chars and surrounding quotes
	 * Keeps the TAB and LF
	 * @param inKey
	 * @param inValue
	 */
	public void put(String inKey, String inValue) {
		if (inValue != null) {
			super.put(inKey, StringTools.cleanControlChars(inValue, false, true));
		}
	}

	/**
	 * Remove and return the main param from the parameters map
	 * 
	 * @return The main (id) parameter casted as a String
	 */
	public String consomMainParam() {
		return (String) this.remove(ActionParam.MAIN_PARAM_KEY);
	}

	@Override
	public String toString() {
		return "caller : " + this.mCaller + " - params : " + this.toJSON();
	}

	public byte[] getBody() throws InvalidParameterException {
		return getBytes("body", true);
	}

}
