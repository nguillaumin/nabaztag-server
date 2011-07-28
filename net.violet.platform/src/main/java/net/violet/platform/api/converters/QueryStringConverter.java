package net.violet.platform.api.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.syndication.io.impl.DateParser;

/**
 * Parse the parameters received in the query string or
 * in a body with <code>application-form-url-encoded</code> content type.<br/><br/>
 * 
 * 05-05-2009 : Great amelioration in URL parameters parsing :<br/>
 * Allow to pass query strings like 
 * <pre>  http://api.violet.net/[method]?arr=[val1]&arr=[val2]&arr=[val3]</pre>to retrieve an array of values.<br/>
 * and to pass a structured object by setting each of its property with the dotted notation :
 * <pre>
 *   http://api.violet.net/[method]?obj.property1=[val1]&obj.property2=[val2]
 * </pre>
 * 
 * @author christophe - Violet
 */
public final class QueryStringConverter {

	/**
	 * Singleton pattern
	 */
	private QueryStringConverter() {
	}

	private static final QueryStringConverter singleton = new QueryStringConverter();

	/**
	 * Une interface pour tous les convertisseurs de types primitifs : String >
	 * Object
	 */
	interface ParamConverter {

		Object convert(String inParamValue);
	}

	class StringParamConverter implements ParamConverter {

		public Object convert(String inParamValue) {
			return inParamValue;
		}
	}

	class IntParamConverter implements ParamConverter {

		public Object convert(String inParamValue) {
			return Integer.valueOf(inParamValue);
		}
	}

	class DoubleParamConverter implements ParamConverter {

		public Object convert(String inParamValue) {
			return Double.valueOf(inParamValue);
		}
	}

	class BooleanParamConverter implements ParamConverter {

		public Object convert(String inParamValue) {
			return Boolean.valueOf(inParamValue);
		}
	}

	/*
	 * les expressions régulières pour identifier les types
	 */
	private static final Pattern stringPattern = Pattern.compile("\"(.*)\"");
	private static final Pattern intPattern = Pattern.compile("(-?[0-9]+)");
	private static final Pattern doublePattern = Pattern.compile("(-?[0-9]+\\.[0-9]+)");
	private static final Pattern booleanPattern = Pattern.compile("(true|false)");

	private static final Pattern[] patterns = { QueryStringConverter.stringPattern, QueryStringConverter.intPattern, QueryStringConverter.doublePattern, QueryStringConverter.booleanPattern, };
	private static final ParamConverter[] converters = { QueryStringConverter.singleton.new StringParamConverter(), QueryStringConverter.singleton.new IntParamConverter(), QueryStringConverter.singleton.new DoubleParamConverter(), QueryStringConverter.singleton.new BooleanParamConverter(), };

	/**
	 * Create the map of parameters from a URL-encoded query string Note :
	 * extended characters must be encoded with their UTF-8 hexadecimal value
	 * Query parameters are readen as Strings, so we have to infer their type
	 * 
	 * @return
	 */
	public static Map<String, Object> convertFromQueryString(String inQueryString) {

		final String[] namedValues = inQueryString.split("&");
		final int len = namedValues.length;
		final Map<String, Object> parameters = new HashMap<String, Object>(len);

		for (int i = 0; i < len; i++) {
			final String[] nameValue = namedValues[i].split("=");
			final String name = nameValue[0];
			final String strVal = nameValue[1];

			parameters.put(name, QueryStringConverter.convertParamValue(strVal));
		}

		return parameters;
	}

	/**
	 * Convert a single parameter value by guessing its type
	 * 
	 * @param strVal
	 * @return
	 */
	public static Object convertParamValue(String strVal) {

		if ((strVal == null) || "null".equals(strVal)) {
			return null;
		}

		/**
		 * Try the String, Number and Boolean identification patterns..
		 */
		for (int j = 0; j < QueryStringConverter.patterns.length; j++) {
			final Pattern pattern = QueryStringConverter.patterns[j];
			final Matcher matcher = pattern.matcher(strVal);
			if (matcher.matches()) {
				return QueryStringConverter.converters[j].convert(matcher.group(1));
			}
		}

		// let's try the date patterns !
		final Date dateVal = DateParser.parseDate(strVal);

		return (dateVal != null) ? dateVal : strVal; // in last resort we keep the string as is (may be the user forgot the quotes)
	}

	/**
	 * Takes an array of String parameters and return a list of converted parameter's value
	 * 
	 * @param strValues
	 * @return
	 */
	public static Object convertParamValue(String[] strValues) {

		final List<Object> convertedValues = new ArrayList<Object>(8);

		if (strValues == null) {
			return convertedValues;
		}

		for (final String strValue : strValues) {
			convertedValues.add(QueryStringConverter.convertParamValue(strValue));
		}

		return convertedValues;
	}

	/**
	 * @param inParams
	 * @return
	 */
	public static String convertToQueryString(Map<String, Object> inParams) {
		// TODO
		return net.violet.common.StringShop.EMPTY_STRING;
	}
}
