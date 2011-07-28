package net.violet.platform.api.converters;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

/**
 * Basic utilities to test POJO (Plain Old Java Objects) objects structures and
 * convert primitive types into their textual POJO representation
 * 
 * @author christophe - Violet
 */
public class PojoHelper {

	/**
	 * Used to know if an object is a primitive type according to POJO
	 * definition
	 * 
	 * @param inObject
	 * @return TRUE if the object is a String, Number, Boolean, Date or has a
	 *         NULL value
	 */
	public static boolean isPrimitiveType(Object inObject) {
		return (inObject == null) || (inObject instanceof String) || (inObject instanceof Number) || (inObject instanceof Boolean) || (inObject instanceof Date);
	}

	/**
	 * Used to know if an object is a primitive type according to POJO
	 * definition
	 * 
	 * @param inObject
	 * @return true if the object is a String, Number, Boolean, Date or has a
	 *         NULL value
	 */
	public static boolean isPojo(Object inObject) {
		return (inObject instanceof Map) || (inObject instanceof List) || PojoHelper.isPrimitiveType(inObject);
	}

	public static void checkPojoConformance(Map<String, Object> inMaybePojoMap) throws InvalidParameterException {

		for (final String key : inMaybePojoMap.keySet()) {
			try {
				PojoHelper.checkPojoConformance(inMaybePojoMap.get(key));
			} catch (final InvalidParameterException ipe) {
				// update the JSON path of the invalid value
				String newJsonPath;
				final String currentJsonPath = ipe.getReplacementValue(0);
				if (currentJsonPath == StringShop.EMPTY_STRING) {
					newJsonPath = key;
				} else {
					newJsonPath = (currentJsonPath.startsWith("[") ? key + currentJsonPath : key + "." + currentJsonPath);
				}
				ipe.setReplacementValue(0, newJsonPath);
				throw ipe;
			}
		}
	}

	public static void checkPojoConformance(List<Object> inMaybePojoList) throws InvalidParameterException {

		for (int i = 0, len = inMaybePojoList.size(); i < len; i++) {
			try {
				PojoHelper.checkPojoConformance(inMaybePojoList.get(i));
			} catch (final InvalidParameterException ipe) {
				// update the JSON path of the invalid value
				String newJsonPath;
				final String currentJsonPath = ipe.getReplacementValue(0);
				if (currentJsonPath == StringShop.EMPTY_STRING) {
					newJsonPath = "[" + i + "]";
				} else {
					newJsonPath = "[" + i + "]." + currentJsonPath;
				}
				ipe.setReplacementValue(0, ("[" + i + "]") + ipe.getReplacementValue(0));
				throw ipe;
			}
		}
	}

	public static void checkPojoConformance(Object inMaybePojo) throws InvalidParameterException {

		if (!PojoHelper.isPojo(inMaybePojo)) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_PRIMITIVE_TYPE, StringShop.EMPTY_STRING);
		}
	}

	public static boolean isPojoConformant(Object inMaybePojo) {

		try {
			PojoHelper.checkPojoConformance(inMaybePojo);
			return true;

		} catch (final InvalidParameterException omg) {
			return false;
		}
	}

	/**
	 * Converts the provided object in a String object.
	 * 
	 * @param inPrimitiveValue
	 * @return a String object.
	 */
	public static String convertToText(Object inPrimitiveValue) {

		if (inPrimitiveValue == null) {
			return "null";
		}

		if (inPrimitiveValue instanceof Date) {
			final CCalendar calendar = new CCalendar(false);
			calendar.setTime((Date) inPrimitiveValue);
			return calendar.getUTCDateTimeFormated();

		} else if ((inPrimitiveValue instanceof String) || (inPrimitiveValue instanceof Number) || (inPrimitiveValue instanceof Boolean)) {
			return inPrimitiveValue.toString();

		} else {
			return StringShop.EMPTY_STRING;
		}
	}

}
