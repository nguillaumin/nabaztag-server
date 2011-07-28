package net.violet.platform.api.converters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.clients.HttpProxyClient;
import net.violet.platform.api.formats.ContentType;
import net.violet.platform.datamodel.MimeType;

public class ConverterFactory {

	public static final JSONConverter JSON = new JSONConverter();
	public static final PojoXMLConverter XML = new PojoXMLConverter();
	public static final AgnosticXMLConverter AGNOSTIC_XML_FORMAT = new AgnosticXMLConverter();
	public static final YAMLConverter YAML = new YAMLConverter();

	private static final Map<MimeType.MIME_TYPES, Converter> CONVERTERS_MIME_TYPES;

	static {
		final Map<MimeType.MIME_TYPES, Converter> mimeMap = new HashMap<MimeType.MIME_TYPES, Converter>();

		mimeMap.put(MimeType.MIME_TYPES.JSON, ConverterFactory.JSON);
		mimeMap.put(MimeType.MIME_TYPES.YAML, ConverterFactory.YAML);
		mimeMap.put(MimeType.MIME_TYPES.XML, ConverterFactory.XML);

		CONVERTERS_MIME_TYPES = Collections.unmodifiableMap(mimeMap);
	}

	/**
	 * Used with the HttpProxyClient to convert response body in a POJO structure
	 * 
	 * @param inContentType
	 * @return
	 * @see HttpProxyClient#call(String, Map, Map)
	 */
	public static Converter getConverter(ContentType inContentType, boolean useAgnosticXmlConverter) {

		if (inContentType.isJson()) {
			return ConverterFactory.JSON;

		} else if (inContentType.isXml()) {
			// it is XML ! but we don't know
			return (useAgnosticXmlConverter) ? ConverterFactory.AGNOSTIC_XML_FORMAT : ConverterFactory.XML;

		} else if (inContentType.isYaml()) {
			return ConverterFactory.YAML;

		} else {
			return null;
		}
	}

	public static Converter getConverter(MimeType.MIME_TYPES type) {
		return ConverterFactory.CONVERTERS_MIME_TYPES.get(type);
	}

	/**
	 * Sometimes the content type or mime type are not enough or not passed as expected
	 * We can try a last guess by eamining the content
	 * @param inContent
	 */
	public static Converter guessConverterFromContent(String inContent) {

		if ((inContent == null) || (inContent.length() == 0)) {
			// cannot guess from empty content
			return null;
		}

		try {
			char firstChar, lastChar;
			int i = 0;
			do {
				firstChar = inContent.charAt(i++);
			} while (Character.isWhitespace(firstChar));

			i = inContent.length() - 1;
			do {
				lastChar = inContent.charAt(i--);
			} while (Character.isWhitespace(lastChar));

			// JSON and XML have very specific markers
			if ((firstChar == '<') && (lastChar == '>')) {
				return ConverterFactory.AGNOSTIC_XML_FORMAT;

			} else if (((firstChar == '{') && (lastChar == '}')) || ((firstChar == '[') && (lastChar == ']'))) {
				return ConverterFactory.JSON;

			}

		} catch (final Exception e) {
			// index out of bound may occur if the content is a blank string
		}

		// halas poor yorick..
		return null;

	}
}
