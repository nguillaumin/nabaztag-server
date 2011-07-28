package net.violet.platform.api.converters.pojo;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.violet.platform.util.SafeBase64;

import org.json.JSON;

/**
 * A wrapper around a simple byte array to implement the POJO-required Map
 * Interface And to provide a specific JSON rendering for this object in Base64
 * format : {"_Base64Encoded":"base64 encoded value"} 
 * If the Binary data has to be transfered to JavaScript, just wrap it (again) 
 * with the ScriptableMapWrapper
 * 
 * @author chdes - Violet
 */
public class BinaryDataWrapper extends AbstractMap<String, Object> implements JSON {

	private static final String ATTRIBUTE_NAME = "_Base64Encoded";
	private static final char[] jsonPrefix = ("{\"" + BinaryDataWrapper.ATTRIBUTE_NAME + "\":\"").toCharArray();
	private static final char[] jsonSuffix = "\"}".toCharArray();

	/**
	 * The array buffer into which the elements of the List are stored.
	 */
	private final byte[] bData;
	private final String str64;

	private Set<Entry<String, Object>> entrySet;

	/**
	 * @param in
	 */
	public BinaryDataWrapper(byte[] in) {
		this.bData = in;
		this.str64 = SafeBase64.encode(in, true);
	}

	/**
	 * @param inStr64 a Base64 representation of the binary data
	 */
	public BinaryDataWrapper(String inStr64) {
		this.str64 = inStr64;
		this.bData = SafeBase64.decode(inStr64, true);
	}

	public byte[] getBytes() {
		return this.bData;
	}

	public String getEncodedData() {
		return this.str64;
	}

	/**
	 * @param maybeBase64Data
	 * @return TRUE if this object is a wrapper around a single base 64 encoded
	 *         byte array
	 */

	public static boolean isWrapper(Object maybeBase64Data) {

		if (!(maybeBase64Data instanceof Map)) {
			return false;
		}

		try {
			final Map<String, Object> map = (Map<String, Object>) maybeBase64Data;
			return (map.size() == 1) && (map.containsKey(BinaryDataWrapper.ATTRIBUTE_NAME));

		} catch (final ClassCastException e) {
			return false;
		}
	}

	/**
	 * @param base64Data a Map {"_Base64Encoded":
	 *            "binary data in base 64 format"}
	 * @return
	 */
	public static byte[] unwrap(Map<String, Object> base64Data) {

		return SafeBase64.decode((String) base64Data.get(BinaryDataWrapper.ATTRIBUTE_NAME), true);
	}

	/**
	 * @param base64Data a Map {"_Base64Encoded":
	 *            "binary data in base 64 format"}
	 * @return
	 */
	public static BinaryDataWrapper decode(Map<String, Object> base64Data) {
		return new BinaryDataWrapper(SafeBase64.decode((String) base64Data.get(BinaryDataWrapper.ATTRIBUTE_NAME), true));
	}

	/**
	 * Walk the pojo structure and make sure that all binaries data (byte[]) are
	 * wrapped USAGE NOTES : When the input param is a Map or List structure,
	 * then the returned value is the map itself where some elements may have
	 * been wrapped. So the call may simply be :
	 * <code>BinaryDataWrapper.wrapBinaries(myMap)</code> But in the case where
	 * the input is a binary array, then, the result must be read.
	 * <code>Object wrapped = BinaryDataWrapper.wrapBinaries(myBinaryArray)</code>
	 * 
	 * @param inPojo
	 * @return the modified object
	 */
	public static Object wrapBinaries(Object inPojo) {

		if (inPojo instanceof Map) {

			if (BinaryDataWrapper.isWrapper(inPojo)) {
				return inPojo; // allready wrapped
			}

			final Map<String, Object> pojoMap = (Map<String, Object>) inPojo;

			final int len = pojoMap.size();
			final String[] keys = new String[len];
			pojoMap.keySet().toArray(keys); // copy this set into an array as we will be modifying the underlying structure

			for (int i = 0; i < len; i++) {
				final String key = keys[i];

				// now scan the object itself if it is a collection
				final Object indexedVal = pojoMap.get(key);

				if ((indexedVal instanceof Map) || ((indexedVal instanceof List))) {
					BinaryDataWrapper.wrapBinaries(indexedVal);

				} else if (indexedVal instanceof byte[]) {
					pojoMap.put(key, new BinaryDataWrapper((byte[]) indexedVal));

				}
			}

		} else if (inPojo instanceof List) {

			final List<Object> pojoList = (List<Object>) inPojo;
			final int len = pojoList.size();
			for (int i = 0; i < len; i++) {

				final Object indexedVal = pojoList.get(i);

				if ((indexedVal instanceof Map) || (indexedVal instanceof List)) {
					BinaryDataWrapper.wrapBinaries(indexedVal);

				} else if (indexedVal instanceof byte[]) {
					pojoList.set(i, new BinaryDataWrapper((byte[]) indexedVal));

				}
			}

		} else if (inPojo instanceof byte[]) {
			return new BinaryDataWrapper((byte[]) inPojo);

		}

		return inPojo;

	}

	/**
	 * @see org.json.JSON#toJSONString()
	 */
	public String toJSONString() {
		final CharArrayWriter writer = new CharArrayWriter();

		try {
			writer.write(BinaryDataWrapper.jsonPrefix);
			SafeBase64.encodeTo(this.bData, true, writer);
			writer.write(BinaryDataWrapper.jsonSuffix);

		} catch (final IOException ignore) {}

		return writer.toString();
	}

	/**
	 * @see java.util.AbstractMap#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {

		if (this.entrySet == null) { // lazy initialization pattern
			this.entrySet = Collections.singletonMap(BinaryDataWrapper.ATTRIBUTE_NAME, (Object) this.str64).entrySet();
		}

		return this.entrySet;
	}

}
