package net.violet.platform.api.maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.converters.YAMLConverter;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.MissingParameterException;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.json.helper.JsonDateHelper;

import com.sun.syndication.io.impl.DateParser;

/**
 * @author christophe - Violet
 */
public class PojoMap extends HashMap<String, Object> implements YAMLConverter.YAMLTagged {

	public static final PojoMap EMPTY_MAP = new PojoMap(Collections.<String, Object> emptyMap());

	public PojoMap(int capacity) {
		super(capacity);
	}

	public PojoMap(Map<String, Object> inSimpleMap) {

		if (inSimpleMap == null) {
			return;
		}

		this.putAll(inSimpleMap);
	}

	/**
	 * Extract a property from the map using the JSON path to the property
	 * Example : <code>id, countries[0].name, object.owner.city</code> are valid JSON path
	 * that explore the structure of the POJO object
	 * 
	 * @param inPropertyPath
	 * @return NULL if the expression doesn't correspond to an existing member
	 *         in the POJO structure TODO chdes : use regular expressions to
	 *         analyze the JSON path expression ?
	 */
	public <V> V getProperty(String inPropertyPath) {

		Object returnVal = this;

		for (final String property : inPropertyPath.split("\\.")) {

			// find if the expression is in form <propertyName> or <propertyName[index]>
			final int bracketPos = property.indexOf("[");
			final String propertyName = (bracketPos > 0) ? property.substring(0, bracketPos) : property;
			final int index = (bracketPos > 0) ? Integer.parseInt(property.substring(bracketPos + 1, property.length() - 1)) : -1;

			try {
				returnVal = ((Map<String, Object>) returnVal).get(propertyName);

				if (returnVal == null) {
					return null; // property not found or null value

				} else if (index != -1) { // access the indexed value of a list
					returnVal = ((List<Object>) returnVal).get(index);

				}

			} catch (final Exception e) { // ClassCastException, IndexOutOfBoundsException
				// expression doesn't conform to object structure
				return null;
			}
		}

		return (V) returnVal;
	}

	/**
	 * Add a field based on its property (JSON) path 
	 * Example : <code>id, object.owner.city</code> are valid JSON path
	 * that explore the structure of the POJO object
	 * 
	 * IMPORTANT: Multiple calls to createProperty with the same JSON path
	 * will result in creating a list property with the different values 
	 * added inside.
	 * 
	 * @param inPropertyPath
	 * @param inValue
	 */
	public void createProperty(String inPropertyPath, Object inValue) {

		final String[] names = inPropertyPath.split("\\.");

		final int lastIndex = names.length - 1;

		// we have a path to create
		Map<String, Object> propertyBag = this;
		for (int i = 0; i < lastIndex; i++) {
			final String name = names[i];
			if (!propertyBag.containsKey(name)) {
				propertyBag.put(name, new HashMap<String, Object>(4));
			}

			propertyBag = (Map<String, Object>) propertyBag.get(name);
		}

		// now deal with the case where the key has allready some values
		final Object iWasThereBefore = propertyBag.get(names[lastIndex]);

		if (iWasThereBefore == null) { // noone there : just sit down and relax !
			propertyBag.put(names[lastIndex], inValue);

		} else if (iWasThereBefore instanceof List) { // allready converted to List
			((List) iWasThereBefore).add(inValue);

		} else { // transform the singleton value into a List
			final List<Object> lstProperty = new ArrayList<Object>(4);
			lstProperty.add(iWasThereBefore);
			lstProperty.add(inValue);
			propertyBag.put(names[lastIndex], lstProperty);
		}

	}

	/**
	 * Récupère la valeur d'un paramètre texte.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @return la valeur du paramètre ou <code>null</code>.
	 * @throws InvalidParameterException si le paramètre n'est pas une chaîne.
	 */
	public String getString(String inParamName) {

		try {
			return (String) getProperty(inParamName);
		} catch (final ClassCastException bad) {
			// hmm.. cas limite ou une valeur a été parsée en numérique
			// mais l'utilisateur la veux sous forme de chaine, ex : version=0.2
			// >> il aurait du la passer entre quotes mais un bug dans req.getParameter() a viré les quotes !
			// >> on fait un effort

			return String.valueOf(getProperty(inParamName));
			// throw new InvalidParameterException(APIErrorMessage.NOT_A_STRING);
		}
	}

	/**
	 * Récupère la valeur d'un paramètre texte obligatoire.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas une chaîne ou
	 *             n'est pas présent.
	 */
	public String getString(String inParamName, boolean isMandatory) throws InvalidParameterException {

		final String paramValue = getString(inParamName);

		if ((paramValue == null) && isMandatory) {
			throw new MissingParameterException(inParamName);
		}

		return paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre texte avec valeur par défaut.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param inDefaultValue la valeur par défaut.
	 * @return la valeur lue du paramètre s'il existe ou la valeur par défaut.
	 * @throws InvalidParameterException si le paramètre n'est pas une chaîne
	 */
	public String getString(String inParamName, String inDefaultValue) {

		final String paramValue = getString(inParamName);
		return (paramValue == null) ? inDefaultValue : paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre date ou <code>null</code> si absent.
	 * 
	 * @param inParamName name of the parameter we want to retrieve the value.
	 * @return the value of the parameter, or null.
	 */
	public Date getDate(String inParamName) throws InvalidParameterException {

		try {
			// Date parameters passed via XML-RPC calls or in JSON format
			// may already have been parsed according to their specific formats
			return (Date) getProperty(inParamName);

		} catch (final ClassCastException ignore) {
			// assume that the string has not been parsed by the format converter
		}

		final String strDate = getString(inParamName); // InvalidParameterException if param is not a String either !

		if (strDate == null) {
			return null;
		}

		// try to parse date in RFC822 or W3C format
		final Date parsed = DateParser.parseDate(strDate);

		if (parsed == null) { // null means that the parsing was impossible
			throw new InvalidParameterException(APIErrorMessage.NOT_A_DATE, inParamName);
		}

		return parsed;
	}

	/**
	 * Récupère la valeur d'un paramètre date obligatoire.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas une date ou
	 *             n'est pas présent.
	 */
	public Date getDate(String inParamName, boolean isMandatory) throws InvalidParameterException {

		final Date paramValue = getDate(inParamName);

		if ((paramValue == null) && isMandatory) {
			throw new MissingParameterException(inParamName);
		}

		return paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre date avec une valeur par défaut.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param inDefaultValue la valeur par défaut.
	 * @return la valeur lue du paramètre s'il existe ou la valeur par défaut.
	 * @throws InvalidParameterException si le paramètre n'est pas une date
	 */
	public Date getDate(String inParamName, Date inDefaultValue) throws InvalidParameterException {

		final Date paramValue = getDate(inParamName);
		return (paramValue == null) ? inDefaultValue : paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre entier <code>Integer</code> ou
	 * <code>null</code>.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @return la valeur du paramètre ou <code>null</code>.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier.
	 */
	public Integer getInteger(String inParamName) throws InvalidParameterException {

		try {
			return (Integer) getProperty(inParamName);
		} catch (final ClassCastException passThatOne) {
			throw new InvalidParameterException(APIErrorMessage.NOT_AN_INTEGER, inParamName);
		}
	}

	/**
	 * Récupère la valeur d'un paramètre entier obligatoire
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier
	 *             signé ou n'est pas présent.
	 */
	public Integer getInt(String inParamName, boolean isMandatory) throws InvalidParameterException {
		final Integer paramValue = getInteger(inParamName);

		if ((paramValue == null) && isMandatory) {
			throw new MissingParameterException(inParamName);
		}

		return paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre entier, avec une valeur par défaut.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param inDefaultValue la valeur par défaut.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier ou
	 *             n'est pas présent.
	 */
	public int getInt(String inParamName, int inDefaultValue) throws InvalidParameterException {
		final Integer paramValue = getInteger(inParamName);
		return (paramValue == null) ? inDefaultValue : paramValue.intValue();
	}

	/**
	 * Récupère la valeur d'un paramètre entier <code>Long</code>ou
	 * <code>null</code>
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @return la valeur du paramètre ou <code>null</code>.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier.
	 */
	public Long getLong(String inParamName) throws InvalidParameterException {

		final Object value = getProperty(inParamName);

		try {
			return (Long) value;
		} catch (final ClassCastException passThatOne) {
			// maybe Integer..?
		}

		try {
			final Integer i = (Integer) value;
			return new Long(i.longValue());

		} catch (final ClassCastException definitivelyNot) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_LONG, inParamName);
		}
	}

	/**
	 * Récupère la valeur d'un paramètre entier long obligatoire
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier
	 *             long ou n'est pas présent.
	 */
	public long getLong(String inParamName, boolean isMandatory) throws InvalidParameterException {
		final Long paramValue = getLong(inParamName);

		if ((paramValue == null) && isMandatory) {
			throw new MissingParameterException(inParamName);
		}

		return paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre entier long, avec une valeur par
	 * défaut.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param inDefaultValue la valeur par défaut.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier
	 *             long ou n'est pas présent.
	 */
	public long getLong(String inParamName, long inDefaultValue) throws InvalidParameterException {
		final Long paramValue = getLong(inParamName);
		return (paramValue == null) ? inDefaultValue : paramValue.longValue();
	}

	/**
	 * Récupère un tableau de bytes ou <code>null</code>
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @return la valeur du paramètre ou <code>null</code>.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier.
	 */
	public byte[] getBytes(String inParamName) throws InvalidParameterException {

		final Object value = getProperty(inParamName);

		try {
			return (byte[]) value;
		} catch (final ClassCastException passThatOne) {
			// YAML converter renders binaries as Strings..
		}

		try {
			return ((String) value).getBytes();
		} catch (final ClassCastException definitivelyNot) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_BYTE_ARRAY, inParamName);
		}
	}

	/**
	 * Récupère un tableau de bytes obligatoire
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier
	 *             long ou n'est pas présent.
	 */
	public byte[] getBytes(String inParamName, boolean isMandatory) throws InvalidParameterException {
		final byte[] paramValue = getBytes(inParamName);

		if ((paramValue == null) && isMandatory) {
			throw new MissingParameterException(inParamName);
		}

		return paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre booléen, s'il est présent.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @return la valeur du paramètre ou <code>NULL</code>.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier ou
	 *             n'est pas présent.
	 */
	public Boolean getBoolean(String inParamName) throws InvalidParameterException {
		try {
			return (Boolean) getProperty(inParamName);

		} catch (final ClassCastException bad) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_BOOLEAN, inParamName);
		}
	}

	/**
	 * Récupère la valeur d'un paramètre booléen, avec une valeur par défaut.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param inDefaultValue la valeur par défaut.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas un booléen.
	 */
	public boolean getBoolean(String inParamName, boolean inDefaultValue) throws InvalidParameterException {
		final Boolean paramValue = getBoolean(inParamName);
		return (paramValue == null) ? inDefaultValue : paramValue;
	}

	/**
	 * @param <T>
	 * @param inParamName
	 * @return
	 * @throws InvalidParameterException
	 */
	public <V> List<V> getList(String inParamName) throws InvalidParameterException {
		try {
			final List<V> lstParamValue = getProperty(inParamName);
			if (lstParamValue == null) {
				return null;
			}
			if (lstParamValue instanceof PojoList) {
				return lstParamValue;
			}
			return new PojoList(inParamName, lstParamValue);

		} catch (final ClassCastException bad) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_LIST, inParamName);
		}
	}

	/**
	 * Récupère la valeur d'un paramètre sous forme d'une liste.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas une liste ou
	 *             n'est pas présent.
	 */
	public <V> List<V> getList(String inParamName, boolean isMandatory) throws InvalidParameterException {
		final List<V> paramValue = getList(inParamName);

		if (isMandatory && (paramValue == null)) {
			throw new MissingParameterException(inParamName);
		}

		return paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre liste, avec une valeur par défaut.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param inDefaultValue la valeur par défaut.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas une map.
	 */
	public <V> List<V> getList(String inParamName, List<V> inDefaultValue) throws InvalidParameterException {
		final List<V> paramValue = getList(inParamName);
		if (paramValue == null) {
			if (inDefaultValue instanceof PojoList) {
				return inDefaultValue;
			}
			return new PojoList(inParamName, inDefaultValue);

		}
		return paramValue;

	}

	/**
	 * @param <T>
	 * @param inParamName
	 * @return
	 * @throws InvalidParameterException
	 */

	public <V> Map<String, V> getMap(String inParamName) throws InvalidParameterException {
		try {
			return getProperty(inParamName);
		} catch (final ClassCastException bad) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_MAP, inParamName);
		}
	}

	public PojoMap getPojoMap(String inParamName) throws InvalidParameterException {
		final Map<String, Object> theMap = getMap(inParamName);
		if (theMap == null) {
			return PojoMap.EMPTY_MAP;
		}
		if (theMap instanceof PojoMap) {
			return (PojoMap) theMap;
		}
		return new PojoMap(theMap);
	}

	/**
	 * Récupère la valeur d'un paramètre sous forme d'une map.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas une liste ou
	 *             n'est pas présent.
	 */
	public PojoMap getPojoMap(String inParamName, boolean isMandatory) throws InvalidParameterException {
		final PojoMap mapValue = getPojoMap(inParamName);

		if (isMandatory && (mapValue == PojoMap.EMPTY_MAP)) {
			throw new MissingParameterException(inParamName);
		}

		return mapValue;
	}

	public <V> Map<String, V> getMap(String inParamName, boolean isMandatory) throws InvalidParameterException {
		final Map<String, V> mapValue = getMap(inParamName);

		if (isMandatory && (mapValue == null)) {
			throw new MissingParameterException(inParamName);
		}

		return mapValue;
	}

	/**
	 * Récupère la valeur d'un paramètre map, avec une valeur par défaut.
	 * 
	 * @param inParamName le nom du paramètre à récupérer.
	 * @param inDefaultValue la valeur par défaut.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas une map.
	 */
	public PojoMap getPojoMap(String inParamName, Map inDefaultValue) throws InvalidParameterException {
		final PojoMap mapValue = getPojoMap(inParamName);

		if (mapValue == PojoMap.EMPTY_MAP) {

			if (inDefaultValue instanceof PojoMap) {
				return (PojoMap) inDefaultValue;
			}
			return new PojoMap(inDefaultValue);
		}

		return mapValue;
	}

	/**
	 * Easy construction of a pojo map from a JSON string
	 * 
	 * @param inJSONString
	 * @return
	 * @throws ConversionException
	 */
	public static PojoMap fromJSON(String inJSONString) throws ConversionException {
		return new PojoMap(ConverterFactory.JSON.<Map<String, Object>> convertFrom(inJSONString));
	}

	/**
	 * Easy construction of a pojo map from an XML string
	 * 
	 * @param inXMLString
	 * @return
	 * @throws ConversionException
	 */
	public static PojoMap fromXML(String inXMLString) throws ConversionException {
		return new PojoMap(ConverterFactory.XML.<Map<String, Object>> convertFrom(inXMLString));
	}

	/**
	 * Easy construction of a pojo map from a YAML string
	 * 
	 * @param inYAMLString
	 * @return
	 * @throws ConversionException 
	 */
	public static PojoMap fromYAML(String inYAMLString) throws ConversionException {
		return new PojoMap(ConverterFactory.YAML.<Map<String, Object>> convertFrom(inYAMLString));
	}

	/**
	 * Serialize this POJO Map to a JSON string
	 * 
	 * @return a JSON representation of this map
	 * @throws ConversionException
	 */
	public String toJSON() {
		return ConverterFactory.JSON.convertTo(this);
	}

	/**
	 * Serialize this POJO Map to an XML string
	 * 
	 * @return an XML representation of this map
	 * @throws ConversionException
	 */
	public String toXML() throws ConversionException {
		return ConverterFactory.XML.convertTo(this);
	}

	/**
	 * Serialize this POJO Map to a YAML string
	 * 
	 * @return an XML representation of this map
	 * @throws ConversionException
	 */
	public String toYAML() throws ConversionException {
		return ConverterFactory.YAML.convertTo(this);
	}

	/**
	 * @return the sorted set of keys in this map
	 */
	public Set<String> getSortedParamNames() {
		return new TreeSet<String>(this.keySet());
	}

	/**
	 * @return TRUE if no structured types (Map, List) are present in the
	 *         parameters values
	 */
	public boolean containsOnlyPrimitiveTypes() {
		for (final Object val : this.values()) {
			if (!PojoMap.isPrimitiveType(val)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return an array of NameValuePair suitable to create a query string or a
	 *         Form URL encoded body IMPORTANT : only primitives data types
	 *         parameters are treated ! other data types are dropped.
	 * @see HttpMethod#setQueryString(NameValuePair[])
	 */
	public NameValuePair[] asNameValuePairs() {
		final NameValuePair[] pairs = new NameValuePair[this.size()];
		int i = 0;

		for (final String key : this.keySet()) {
			final Object value = this.get(key);

			String strVal = net.violet.common.StringShop.EMPTY_STRING;

			if (PojoMap.isPrimitiveType(value)) {

				if (value instanceof String) {
					strVal = (String) value;

				} else if (value instanceof Date) {
					strVal = JsonDateHelper.formatDate((Date) value);

				} else { // numbers and booleans..
					strVal = String.valueOf(value);
				}
			}

			pairs[i++] = new NameValuePair(key, strVal);
		}

		return pairs;

	}

	/**
	 * @return a String suitable for use in an URL
	 * @throws EncoderException 
	 */
	public String toQueryString() throws EncoderException {

		final StringBuilder sbQueryString = new StringBuilder(this.size() * 15);
		final URLCodec codec = new URLCodec("UTF-8");

		boolean addedOne = false;

		for (final String key : this.keySet()) {

			if (addedOne) {
				sbQueryString.append(net.violet.common.StringShop.AMPERSAND);
			} else {
				addedOne = true;
			}

			final Object value = this.get(key);

			sbQueryString.append(codec.encode(key)).append('=');

			if (PojoMap.isPrimitiveType(value)) {

				if (value instanceof String) {
					sbQueryString.append(codec.encode((String) value));

				} else if (value instanceof Date) { // (no need to URL encode here : date ISO format is safe)
					sbQueryString.append(JsonDateHelper.formatDate((Date) value));

				} else { // numbers and booleans (no need to URL encode here either)..
					sbQueryString.append(String.valueOf(value));
				}
			}

		}

		return sbQueryString.toString();

	}

	/**
	 * @param val
	 * @return TRUE if the object is one of the primitive types accepted in our
	 *         POJO structures
	 */
	private static boolean isPrimitiveType(Object val) {
		return (val == null) || (val instanceof String) || (val instanceof Number) || (val instanceof Date) || (val instanceof Boolean);
	}

	public void putNullValue(String inKey) {
		put(inKey, null);
	}

	/**
	 * Use the map class name without the Map suffix as YAML tag
	 * 
	 * @see YAMLConverter.YAMLTagged
	 */
	public final String getYAMLTag() {
		return "tag:violet.net:map:" + getClass().getSimpleName().replaceAll("Map$", net.violet.common.StringShop.EMPTY_STRING);
	}

	/**
	 * Seal this map TODO chdes : is it really needed ??
	 */
	protected void seal() {
	}

}
