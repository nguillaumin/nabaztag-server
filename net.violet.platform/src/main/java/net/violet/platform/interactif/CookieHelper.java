package net.violet.platform.interactif;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Classe Helper pour lire/mettre à jour une propriété dans la chaine d'un
 * cookie dont la structure est la suivante : Les noms des propriétés est case
 * insensitive <prop1>:<val1>;<prop2>:<val2>;..
 * 
 * @author christophe
 */
public class CookieHelper {

	/**
	 * Create a cookie string conformant to our specification from the content
	 * of the properties object
	 * 
	 * @param properties
	 * @return a cookie string of concatenated <key>:<value> pairs as found in
	 *         the properties object
	 */
	public static String getCookieString(Properties properties) {

		if ((properties == null) || (properties.size() == 0)) {
			return net.violet.common.StringShop.EMPTY_STRING; // empty cookie string
		}

		final StringBuilder sbCookie = new StringBuilder(10 * properties.size());
		final Enumeration<?> propertyNames = properties.propertyNames();

		while (propertyNames.hasMoreElements()) {
			final String propertyName = propertyNames.nextElement().toString();
			final String propertyVal = properties.getProperty(propertyName);
			sbCookie.append(propertyName).append(":").append(propertyVal).append(";");

		}

		return sbCookie.toString();
	}

	/**
	 * Renvoie une chaine conforme à notre structure de cookie pour l'ensemble
	 * des couples nom/valeur présents dans la Map
	 * 
	 * @param properties
	 * @return a cookie string <prop1>:<val1>;<prop2>:<val2>;..
	 */
	public static String getCookieString(Map<String, String> properties) {

		if ((properties == null) || (properties.size() == 0)) {
			return net.violet.common.StringShop.EMPTY_STRING; // empty cookie string
		}

		final StringBuilder sbCookie = new StringBuilder(10 * properties.size());
		final Iterator propertyNames = properties.keySet().iterator();

		while (propertyNames.hasNext()) {
			final String propertyName = (String) propertyNames.next();
			final String propertyVal = properties.get(propertyName);
			sbCookie.append(propertyName).append(":").append(propertyVal).append(";");

		}

		return sbCookie.toString();

	}

	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static String getCookieString(String property, String value) {

		// 
		if (property == null) {
			return net.violet.common.StringShop.EMPTY_STRING;
		}

		final String theValue = (value == null) ? net.violet.common.StringShop.EMPTY_STRING : value;

		return property.concat(":").concat(theValue).concat(";");
	}

	/**
	 * Extrait la valeur d'une propriété dans la chaine du cookie
	 * 
	 * @param cookieString
	 * @param propertyName
	 * @return NULL si la propriété ne peut être trouvée
	 */
	public static String getPropertyValue(String cookieString, String propertyName) {

		if ((cookieString == null) || (propertyName == null)) {
			return null;
		}

		final StringTokenizer tokenList = new StringTokenizer(cookieString, ";");

		while (tokenList.hasMoreTokens()) {
			final String[] nameValuePair = tokenList.nextToken().split(":");
			final String name = nameValuePair[0];

			if (propertyName.equalsIgnoreCase(name)) {
				return nameValuePair[1];
			}

		}

		return null;
	}

	/**
	 * Met à jour la valeur d'une propriété dans la chaine du cookie
	 * 
	 * @param cookieString la chaine <prop1>:<val1>;<prop2>:<val2>;..
	 * @param propertyName le nom de la propriété >> (to lower case)
	 * @param newValue
	 * @return la nouvelle valeur du cookie
	 */
	public static String updatePropertyValue(String inCookieString, String propertyName, String newValue) {
		final String cookieString;
		if (inCookieString == null) {
			cookieString = net.violet.common.StringShop.EMPTY_STRING;
		} else {
			cookieString = inCookieString;
		}

		if (propertyName == null) {
			return cookieString;
		}

		final String theNewValue = (newValue == null) ? net.violet.common.StringShop.EMPTY_STRING : newValue;

		final StringTokenizer tokenList = new StringTokenizer(cookieString, ";");
		final StringBuilder sbUpdatedCookie = new StringBuilder(cookieString.length() + 2);
		boolean updated = false;

		while (tokenList.hasMoreTokens()) {
			final String[] nameValuePair = tokenList.nextToken().split(":");
			final String name = nameValuePair[0];

			sbUpdatedCookie.append(name).append(":");

			if (propertyName.equalsIgnoreCase(name)) {
				sbUpdatedCookie.append(theNewValue);
				updated = true;

			} else {
				sbUpdatedCookie.append(nameValuePair[1]);
			}

			sbUpdatedCookie.append(";");
		}

		if (!updated) {
			sbUpdatedCookie.append(propertyName).append(":").append(theNewValue).append(";");
		}

		return sbUpdatedCookie.toString();
	}
}
