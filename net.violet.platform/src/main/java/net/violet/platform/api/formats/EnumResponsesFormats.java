package net.violet.platform.api.formats;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;

/**
 * L'énumération des formats de réponse valides pour l'API publique. Note : le
 * format Rhino scriptable n'est pas dans cette énumération car il n'est pas
 * exposé dans l'API publique
 * 
 * @author chdes - Violet
 */
public enum EnumResponsesFormats {

	// enum values
	JSON("json", ContentType.JSON, true),
	JSON_WMETA("json-meta", ContentType.JSON_WMETA, true), // on en fait un nouveau content type. (pas terrible..)
	HTML("html", ContentType.TEXT_HTML, true),
	XML("xml", ContentType.TEXT_XML, true),
	XML_RPC("xml-rpc", ContentType.TEXT_XML, false),
	YAML("yaml", ContentType.TEXT_YAML, true);

	// la liste des types de contenus acceptés (attention : il y a deux formats XML)
	private static List<ContentType> contentsList = Arrays.asList(ContentType.JSON, ContentType.TEXT_XML, ContentType.TEXT_YAML, ContentType.JSON_WMETA);

	// Give the appropriate Format for a given content type
	private static final Map<String, EnumResponsesFormats> FORMATS_MAP_BY_NAME;
	// Give the appropriate Format for a given content type
	private static final Map<ContentType, EnumResponsesFormats> FORMATS_MAP_BY_CONTENT_TYPE;

	// initialisation block for the 2 static maps
	static {
		final Map<String, EnumResponsesFormats> byName = new HashMap<String, EnumResponsesFormats>();
		final Map<ContentType, EnumResponsesFormats> byContentType = new HashMap<ContentType, EnumResponsesFormats>();

		for (final EnumResponsesFormats fmt : EnumResponsesFormats.values()) {
			byName.put(fmt.formatName.toUpperCase(), fmt);
			if (fmt.isDefaultFormat) {
				byContentType.put(fmt.getContentType(), fmt);
			}
		}

		FORMATS_MAP_BY_NAME = Collections.unmodifiableMap(byName);
		FORMATS_MAP_BY_CONTENT_TYPE = Collections.unmodifiableMap(byContentType);
	}

	// private attributes and pseudo-constructor
	private String formatName;
	private ContentType garContent;
	private boolean isDefaultFormat;

	/**
	 * @param formatName the format name
	 * @param contentType its content type 
	 * @param isDefaultFormat is it the defaut format for this content type ?
	 */
	EnumResponsesFormats(String formatName, ContentType contentType, boolean isDefaultFormat) {
		this.formatName = formatName;
		this.garContent = contentType;
		this.isDefaultFormat = isDefaultFormat;
	}

	// public methods
	@Override
	public String toString() {
		return this.formatName;
	}

	public String getFormatName() {
		return this.formatName;
	}

	public ContentType getContentType() {
		return this.garContent;
	}

	/**
	 * Find the response format corresponding to this name
	 * 
	 * @param formatName (case insensitive)
	 * @throws APIException if no corresponding format was found
	 */
	public static EnumResponsesFormats getFormatFor(String inFormatName) throws APIException {

		if (inFormatName != null) {
			final EnumResponsesFormats theFormat = EnumResponsesFormats.FORMATS_MAP_BY_NAME.get(inFormatName.toUpperCase());
			if (theFormat != null) {
				return theFormat;
			}
		}

		throw new APIException(APIErrorMessage.UNKNOWN_FORMAT, inFormatName);

	}

	/**
	 * Find the response format corresponding to this name
	 * 
	 * @param formatName (case insensitive)
	 * @param defaultFmt value to return when no corresponding format is found
	 */
	public static EnumResponsesFormats getFormatFor(String formatName, EnumResponsesFormats defaultFmt) {

		EnumResponsesFormats theResult;

		if (formatName == null) {
			theResult = defaultFmt;
		} else {
			try {
				theResult = EnumResponsesFormats.getFormatFor(formatName);
			} catch (final APIException e) {
				theResult = defaultFmt;
			}
		}
		return theResult;
	}

	public static EnumResponsesFormats getDefault() {
		return EnumResponsesFormats.JSON;
	}

	/**
	 * @param bestResponse
	 * @return
	 */
	public static EnumResponsesFormats getFormatFor(ContentType bestResponse) {
		return EnumResponsesFormats.FORMATS_MAP_BY_CONTENT_TYPE.get(bestResponse);
	}

	/**
	 * @return
	 */
	public static List<ContentType> getContentTypes() {
		return EnumResponsesFormats.contentsList;
	}

}
