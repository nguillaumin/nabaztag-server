package net.violet.platform.api.converters;

import java.io.Reader;

import net.violet.platform.api.converters.pojo.BinaryDataWrapper;
import net.violet.platform.api.converters.pojo.VioletPojoFixer;
import net.violet.platform.api.exceptions.APIException;

import org.json.JSONException;
import org.json.simple.JSONValue;
import org.json.simple.serializer.JSONSerializer;
import org.json.simple.serializer.Serializer;

/**
 * A converter between JSON strings and POJO structures (Plain Old Java Objects) 
 * POJO structures are natively JSON conformant and are based solely upon the types : 
 * Map, List, String, Date, Integer, Double, Boolean, byte[]
 * 
 * @author christophe
 */
public class JSONConverter implements Converter {

	public static final String JSON_FORMAT = "json";

	private static final Serializer SILENT_JSON_SERIALIZER = new JSONSerializer(true);
	private static final Serializer VALIDATING_JSON_SERIALIZER = new JSONSerializer(false);

	JSONConverter() {
		// for the use of ConverterFactory only.
	}

	/**
	 * @see net.violet.platform.api.converters.Converter#getFormatName()
	 */
	public String getFormatName() {
		return JSONConverter.JSON_FORMAT;
	}

	/**
	 * @param inJSONString
	 * @return
	 * @throws ConversionException
	 * @throws APIException
	 */
	public <T> T convertFrom(String inJSONString) throws ConversionException {

		if (inJSONString == null) {
			return null;
		}

		try {
			return (T) VioletPojoFixer.fixPojo(JSONValue.parse(inJSONString));

		} catch (final JSONException e) {
			throw new ConversionException("JSON conversion failed for input string : " + inJSONString);
		}
	}

	/**
	 * @param inJSONReader
	 * @return
	 * @throws ConversionException
	 * @throws APIException
	 */
	public <T> T convertFrom(Reader inJSONReader) throws ConversionException {

		try {
			return (T) VioletPojoFixer.fixPojo(JSONValue.parse(inJSONReader));

		} catch (final JSONException e) {
			throw new ConversionException(e);
		}
	}

	/**
	 * Serialize an object in the JavaScript Object Notation 
	 * Supported Data types are : Map, List, JSON, String, Date, Number, Boolean, null 
	 * Illegal members will be dropped
	 * 
	 * @return a String conformant to the JavaScript Objects Notation
	 */
	public String convertTo(Object value) {
		try {
			return convertTo(value, true); // silent mode
		} catch (final ConversionException ignore) {
			// we can't be here !
			return net.violet.common.StringShop.EMPTY_STRING;
		}
	}

	/**
	 * @param inObject
	 * @param silentMode : use FALSE to throw exceptions
	 * @return a String conformant to the JavaScript Objects Notation
	 * @throws ConversionException if an errors occurs when silentMode=false
	 */
	public String convertTo(Object inPojo, boolean silentMode) throws ConversionException {

		final Serializer jsonSerializer = (silentMode ? JSONConverter.SILENT_JSON_SERIALIZER : JSONConverter.VALIDATING_JSON_SERIALIZER);

		return convertUsing(inPojo, jsonSerializer);
	}

	protected String convertUsing(Object inPojo, Serializer inJsonSerializer) throws ConversionException {
		try {
			if (inPojo == null) {
				return "null";
			}

			if (inPojo instanceof byte[]) {
				// byte arrays are not included in the JSON specifications so we put them in a Base64 String
				return inJsonSerializer.serialize(new BinaryDataWrapper((byte[]) inPojo));

			}

			return inJsonSerializer.serialize(inPojo);

		} catch (final JSONException e) {
			throw new ConversionException(e);
		}
	}

}
