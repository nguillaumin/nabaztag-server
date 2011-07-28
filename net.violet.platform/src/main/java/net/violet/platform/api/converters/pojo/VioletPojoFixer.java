package net.violet.platform.api.converters.pojo;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.applets.AppLanguages;

public class VioletPojoFixer {

	public static Object fixPojo(Object inPojo) throws ConversionException {

		if (inPojo instanceof Map) {
			return VioletPojoFixer.fixMap((Map<String, Object>) inPojo);

		} else if (inPojo instanceof List) {
			return VioletPojoFixer.fixList((List<Object>) inPojo);
		}

		return inPojo;

	}

	/**
	 * @param inPojo
	 * @return
	 * @throws ConversionException
	 */
	public static Object fixMap(Map<String, Object> inPojo) throws ConversionException {

		// A map with a special key that contains encoded binary data
		if (BinaryDataWrapper.isWrapper(inPojo)) {
			return BinaryDataWrapper.unwrap(inPojo);
		}

		for (final Entry<String, Object> anEntry : inPojo.entrySet()) {

			if (!VioletPojoFixer.validateAttributeNameFor(anEntry.getKey(), AppLanguages.JAVASCRIPT)) {
				// Bad attribute name
				throw new ConversionException("The attribute name : '" + anEntry.getKey() + "' does not conform to name convention, or is a reserved word !");
			}

			// now scan the object itself if it is a collection
			anEntry.setValue(VioletPojoFixer.fixPojo(anEntry.getValue()));

		}

		return inPojo;
	}

	/**
	 * Iterate over the list elements to fix them
	 * 
	 * @param inPojo
	 * @return List
	 * @throws APIException
	 */
	public static Object fixList(List<Object> inPojo) throws ConversionException {
		for (int i = 0; i < inPojo.size(); i++) {
			inPojo.set(i, VioletPojoFixer.fixPojo(inPojo.get(i)));
		}
		return inPojo;
	}

	private static final Pattern CHECK_PATTERN = Pattern.compile("[a-z_][\\w.-]*", Pattern.CASE_INSENSITIVE);

	/**
	 * Check the name form and that it is not included in the reserved words
	 * list of any of the given languages
	 * 
	 * @param attName
	 * @param languages
	 * @return TRUE if the given attribute name is conformant
	 */
	static boolean validateAttributeNameFor(String attName, AppLanguages... languages) {

		if (!VioletPojoFixer.CHECK_PATTERN.matcher(attName).matches()) {
			return false;
		}

		for (final AppLanguages lang : languages) {
			if (lang.isReserved(attName)) {
				return false;
			}
		}

		return true;
	}

}
