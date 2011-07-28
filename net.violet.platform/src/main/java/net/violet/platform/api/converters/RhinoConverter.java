package net.violet.platform.api.converters;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.pojo.ScriptableList;
import net.violet.platform.api.converters.pojo.ScriptableListWrapper;
import net.violet.platform.api.converters.pojo.ScriptableMap;
import net.violet.platform.api.converters.pojo.ScriptableMapWrapper;
import net.violet.platform.api.converters.pojo.VioletPojoFixer;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.applets.js.JSEnvironment;
import net.violet.platform.applets.js.helpers.JSDateHelper;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.Wrapper;

/**
 * A converter between JavaScript (Rhino Scriptable) objects and a pure Java
 * structure that is equally JSON conformant and is based upon types : Map,
 * List, String, Date, Integer, Double, Boolean
 * 
 * @author christophe
 */
public class RhinoConverter {

	private static final Logger LOGGER = Logger.getLogger(RhinoConverter.class);

	/**
	 * Takes a Rhino JavaScript structure and convert it into a Java structure
	 * whose accepted members are only : Map, List, String, Date, Integer,
	 * Double, Boolean
	 * 
	 * @param inRhinoObject
	 * @param dropMalformedItems TRUE
	 * @return a Java structure mimiquing the JavaScript structure : Map<String,
	 *         X>, List<X>, Integer, Long, Double, Boolean, null
	 * @throws ConversionException when dropMalformedItems is false and an
	 *             undesired data type is found (ex : a function)
	 * @throws APIException
	 */

	public static Object convertFromJS(Object inRhinoObject, boolean dropMalformedItems) throws ConversionException {

		if (inRhinoObject == null) {
			return null;
		}

		// The object to convert may be a native Java object wrapped in a
		// Scriptable Rhino wrapper
		final Object toConvert = (inRhinoObject instanceof Wrapper) ? ((Wrapper) inRhinoObject).unwrap() : inRhinoObject;

		Object result;

		// Type de l'objet.
		if (toConvert instanceof Map) {
			// the object allready implement the Map or List interface !
			result = VioletPojoFixer.fixMap((Map) toConvert);

		} else if (toConvert instanceof List) {
			result = VioletPojoFixer.fixList((List) toConvert);

		} else if (toConvert instanceof Scriptable) {

			// Native JavaScript scriptable object
			final Scriptable scriptable = (Scriptable) toConvert;
			final String className = scriptable.getClassName();

			if (className.equals("Object")) { // javascript object {} >> Map les clés des attributs de l'objet
				final Object[] theKeys = scriptable.getIds();

				// dimensionner la Map
				final int len = theKeys.length;
				final Map<String, Object> mapObject = new ScriptableMap(len);

				for (int i = 0; i < len; i++) {
					final String key = (String) theKeys[i];
					final Object value = scriptable.get(key, scriptable);
					try {
						mapObject.put(key, RhinoConverter.convertFromJS(value, dropMalformedItems));

					} catch (final ConversionException e) {
						if (dropMalformedItems) {
							RhinoConverter.LOGGER.warn("Conversion from JavaScript structure " + Arrays.asList(theKeys) + " dropped malformed item under key : " + key + "(" + e.getMessage() + ")");
						} else {
							throw e;
						}
					}
				}

				result = mapObject; // VioletPojoFixer.fixMap(mapObject);

			} else if (className.equals("Array")) { // javascript array >> List

				final int len = ((Double) scriptable.get("length", scriptable)).intValue();
				final List<Object> lst = new ScriptableList(len);

				for (int i = 0; i < len; i++) {
					final Object value = scriptable.get(i, scriptable);
					try {
						lst.add(RhinoConverter.convertFromJS(value, dropMalformedItems));

					} catch (final ConversionException e) {
						if (dropMalformedItems) {
							RhinoConverter.LOGGER.debug("Dropped malformed array item at position : " + i);
						} else {
							throw e;
						}
					}
				}

				result = lst; // VioletPojoFixer.fixList(lst);

			} else if (className.equals("Date")) { // c'est une date
				result = JSDateHelper.convertFromJs(scriptable); // js Date >> java .util. Date

			} else {
				throw new ConversionException("Bad Format for member " + className);
			}

		} else if ((toConvert instanceof String) || (toConvert instanceof Boolean) || (toConvert instanceof Integer)) {
			result = toConvert;

		} else if (toConvert instanceof Double) {
			// Rhino maps every JavaScript numbers to Double, even when they may
			// be integers
			// >> Try to see if this double value cannot be shrinked to an
			// Integer !
			final Integer truncated = new Integer(((Double) toConvert).intValue());

			if (truncated.doubleValue() == ((Double) toConvert).doubleValue()) {
				result = truncated;
			} else {
				result = toConvert;
			}

		} else if (toConvert instanceof Undefined) {
			result = null; // JavaScript Undefined is Null in Java

		} else {
			throw new ConversionException("Bad Format for member " + toConvert.getClass());
		}

		return result;
	}

	/**
	 * @param obj Java Structure
	 * @return a value suitable to pass to any API that takes JavaScript values.
	 * @throws ConversionException
	 */

	public static Object convertToJS(Object javaObj) {

		final Context ctx = JSEnvironment.getInstance().getNewContext();

		try {
			if (javaObj == null) {
				return null;

			} else if (javaObj instanceof ScriptableObject) {
				return javaObj;

			} else if (javaObj instanceof Map) {
				// Wrap the map inside a ScriptableMap
				final Map jsMap = (javaObj instanceof Scriptable) ? (Map) javaObj : new ScriptableMapWrapper((Map) javaObj);

				// Now replace all mapped values by their JavaScript Scriptable
				// equivalent
				for (final Object key : jsMap.keySet()) {
					jsMap.put(key, RhinoConverter.convertToJS(jsMap.get(key)));
				}

				return jsMap;

			} else if (javaObj instanceof List) {
				// Wrap the List inside a ScriptableList
				final List<Object> jsList = (javaObj instanceof Scriptable) ? (List<Object>) javaObj : new ScriptableListWrapper((List<Object>) javaObj);

				// Now replace all list values by their JavaScript Scriptable
				// equivalent
				final int len = jsList.size();
				for (int i = 0; i < len; i++) {
					jsList.set(i, RhinoConverter.convertToJS(jsList.get(i)));
				}

				return jsList;

			} else if ((javaObj instanceof String) || (javaObj instanceof Boolean) || (javaObj instanceof Number)) {
				// The natives Java types are directly usable in Rhino
				return javaObj;

			} else if (javaObj instanceof Date) {
				// java.util.Date is translated into an equivalent NativeDate
				return JSDateHelper.createJsDate(ctx, (Date) javaObj);

			} else {
				RhinoConverter.LOGGER.error("Unable to convert member " + javaObj.getClass());
				return null;
			}

		} finally {
			Context.exit();
		}

	}
}
