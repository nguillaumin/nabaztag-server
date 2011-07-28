package net.violet.platform.util;

import java.io.CharArrayWriter;
import java.io.IOException;

import org.apache.commons.codec.BinaryEncoder;

/**
 * A Base64 Encoder/Decoder.
 * <p>
 * This class is used to encode and decode data in the RFC 1521 Base64 format
 * and in a modificated version like URL Safe
 * <p>
 * This is "Open Source" software and released under the <a
 * href="http://www.gnu.org/licenses/lgpl.html">GNU/LGPL</a> license.<br>
 * It is provided "as is" without warranty of any kind.<br>
 * Copyright 2003: Christian d'Heureuse, Inventec Informatik AG, Switzerland.<br>
 * Home page: <a href="http://www.source-code.biz">www.source-code.biz</a><br>
 * <p>
 * Version history:<br>
 * 2003-07-22 Christian d'Heureuse (chdh): Module created.<br>
 * 2005-08-11 chdh: Lincense changed from GPL to LGPL.<br>
 * 2006-11-21 chdh:<br>
 * &nbsp; Method encode(String) renamed to encodeString(String).<br>
 * &nbsp; Method decode(String) renamed to decodeString(String).<br>
 * &nbsp; New method encode(byte[],int) added.<br>
 * &nbsp; New method decode(String) added.<br>
 */
public class SafeBase64 {

	public static class Encoder implements BinaryEncoder {

		/**
		 * @see org.apache.commons.codec.BinaryEncoder#encode(byte[])
		 */
		public byte[] encode(byte[] array) {
			final String str64 = SafeBase64.encode(array, true);
			return str64.getBytes();
		}

		/**
		 * @see org.apache.commons.codec.Encoder#encode(java.lang.Object)
		 */
		public Object encode(Object object) {
			return SafeBase64.encodeString(object.toString(), true);
		}

	}

	private static final char PADDING_CHAR = '=';

	// Mapping table from 6-bit nibbles to Base64 characters.
	private static char[] rfc1521CharMap = new char[64];
	private static char[] urlSafeCharMap = new char[64];
	static {
		int i = 0;
		for (char c = 'A'; c <= 'Z'; c++) {
			SafeBase64.rfc1521CharMap[i++] = c;
		}
		for (char c = 'a'; c <= 'z'; c++) {
			SafeBase64.rfc1521CharMap[i++] = c;
		}
		for (char c = '0'; c <= '9'; c++) {
			SafeBase64.rfc1521CharMap[i++] = c;
		}
		SafeBase64.urlSafeCharMap = SafeBase64.rfc1521CharMap.clone();
		SafeBase64.rfc1521CharMap[i] = '+';
		SafeBase64.urlSafeCharMap[i++] = '.'; // replace the usual '+' that is not URL safe
		SafeBase64.rfc1521CharMap[i] = '/';
		SafeBase64.urlSafeCharMap[i++] = '_'; // replace the usual '/'
	}

	// Mapping table from Base64 characters to 6-bit nibbles.
	private static byte[] rfc1521ReverseMap = new byte[128];
	private static byte[] urlSafeReverseMap = new byte[128];
	static {
		for (int i = 0; i < 128; i++) {
			SafeBase64.rfc1521ReverseMap[i] = SafeBase64.urlSafeReverseMap[i] = -1;
		}
		for (int i = 0; i < 64; i++) {
			SafeBase64.rfc1521ReverseMap[SafeBase64.rfc1521CharMap[i]] = (byte) i;
			SafeBase64.urlSafeReverseMap[SafeBase64.urlSafeCharMap[i]] = (byte) i;
		}
	}

	// Dummy constructor.
	protected SafeBase64() {
	}

	/**
	 * Encodes a string into Base64 RFC 1521 format. No blanks or line breaks
	 * are inserted.
	 * 
	 * @param s a String to be encoded.
	 * @return A String with the Base64 encoded data.
	 */
	public static String encodeString(String s) {
		return new String(SafeBase64.encode(s.getBytes()));
	}

	/**
	 * Encodes a string into Base64 RFC 1521 or URL Safe format. No blanks or
	 * line breaks are inserted.
	 * 
	 * @param s the String to be encoded.
	 * @param applyUrlSafeMapping TRUE to use URL safe character mappings ('._'
	 *            'instead of '+/')
	 * @return A String with the Base64 encoded data.
	 */
	public static String encodeString(String s, boolean applyUrlSafeMapping) {
		return new String(SafeBase64.encode(s.getBytes(), applyUrlSafeMapping));
	}

	/**
	 * Encodes a byte array into Base64 RFC 1521 conformant format. No blanks or
	 * line breaks are inserted.
	 * 
	 * @param in the array containing the data bytes to be encoded.
	 * @return A character array with the Base64 encoded data.
	 */
	public static String encode(byte[] in) {
		return SafeBase64.encode(in, false);
	}

	/**
	 * Encodes a byte array into Base64 RFC 1521 or URL Safe format.
	 * 
	 * @param in
	 * @param applyUrlSafeMapping TRUE to use URL safe character mappings ('._'
	 *            'instead of '+/')
	 * @return
	 */
	public static String encode(byte[] in, boolean applyUrlSafeMapping) {

		final char[] charMap = applyUrlSafeMapping ? SafeBase64.urlSafeCharMap : SafeBase64.rfc1521CharMap;

		final int iLen = in.length;
		final int oDataLen = (iLen * 4 + 2) / 3; // output length without padding
		final int oLen = ((iLen + 2) / 3) * 4; // output length including padding
		final char[] out = new char[oLen];
		int ip = 0;
		int op = 0;

		while (ip < iLen) {
			final int i0 = in[ip++] & 0xff;
			final int i1 = ip < iLen ? in[ip++] & 0xff : 0;
			final int i2 = ip < iLen ? in[ip++] & 0xff : 0;
			final int o0 = i0 >>> 2;
			final int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
			final int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
			final int o3 = i2 & 0x3F;
			out[op++] = charMap[o0];
			out[op++] = charMap[o1];
			out[op] = op < oDataLen ? charMap[o2] : SafeBase64.PADDING_CHAR;
			op++;
			out[op] = op < oDataLen ? charMap[o3] : SafeBase64.PADDING_CHAR;
			op++;
		}

		return new String(out);
	}

	/**
	 * Encodes a byte array into Base64 format. No blanks or line breaks are
	 * inserted.
	 * 
	 * @param in an array containing the data bytes to be encoded.
	 * @param in an array containing the data bytes to be encoded.
	 * @throws IOException
	 */
	public static void encodeTo(byte[] in, boolean applyUrlSafeMapping, CharArrayWriter out) {

		final char[] charMap = applyUrlSafeMapping ? SafeBase64.urlSafeCharMap : SafeBase64.rfc1521CharMap;

		final int iLen = in.length;
		int ip = 0;
		final char[] output4 = new char[4];

		while (ip < iLen) {
			final int i0 = in[ip++] & 0xff;
			final int i1 = ip < iLen ? in[ip++] & 0xff : 0;
			final int i2 = ip < iLen ? in[ip++] & 0xff : 0;

			final int o0 = i0 >>> 2;
			final int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
			final int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
			final int o3 = i2 & 0x3F;

			output4[0] = charMap[o0];
			output4[1] = charMap[o1];
			output4[2] = (o2 == 0) ? SafeBase64.PADDING_CHAR : charMap[o2];
			output4[3] = (o3 == 0) ? SafeBase64.PADDING_CHAR : charMap[o3];

			try {
				out.write(output4);
			} catch (final IOException ignore) {}
		}

	}

	/**
	 * Apply strict RFC 1521 mapping
	 * 
	 * @param in
	 * @param out
	 */
	public static void encodeTo(byte[] in, CharArrayWriter out) {
		SafeBase64.encodeTo(in, false, out);
	}

	/**
	 * Decodes a string from Base64 format.
	 * 
	 * @param inStr64 the Base64 String to be decoded.
	 * @param applyUrlSafeMapping TRUE to apply a modified version where + and /
	 *            are replaced by . and _
	 * @return A String containing the decoded data.
	 * @throws IllegalArgumentException if the input is not valid Base64 encoded
	 *             data.
	 */
	public static String decodeString(String inStr64, boolean applyUrlSafeMapping) {
		return new String(SafeBase64.decode(inStr64, applyUrlSafeMapping));
	}

	/**
	 * Decodes a byte array from Base64 format.
	 * 
	 * @param inStr64 the Base64 String to be decoded.
	 * @param applyUrlSafeMapping TRUE to apply a modified version where + and /
	 *            are replaced by . and _
	 * @return An array containing the decoded data bytes.
	 * @throws IllegalArgumentException if the input is not valid Base64 encoded
	 *             data.
	 */
	public static byte[] decode(String inStr64, boolean applyUrlSafeMapping) {
		return SafeBase64.decode(inStr64.toCharArray(), applyUrlSafeMapping);
	}

	/**
	 * Decode String in Base64 format.
	 * 
	 * @param inStr64
	 * @return
	 */
	public static byte[] decode(String inStr64) {
		return SafeBase64.decode(inStr64.toCharArray(), true);
	}

	/**
	 * Decodes a byte array from Base64 format. No blanks or line breaks are
	 * allowed within the Base64 encoded data.
	 * 
	 * @param in64 a character array containing the Base64 encoded data.
	 * @return An array containing the decoded data bytes.
	 * @throws IllegalArgumentException if the input is not valid Base64 encoded
	 *             data.
	 */
	public static byte[] decode(char[] in64, boolean applyUrlSafeMapping) {

		final byte[] reverseMap = applyUrlSafeMapping ? SafeBase64.urlSafeReverseMap : SafeBase64.rfc1521ReverseMap;
		int iLen = in64.length;

		while ((iLen > 0) && (in64[iLen - 1] == '=')) {
			iLen--;
		}

		final int oLen = (iLen * 3) / 4;
		final byte[] out = new byte[oLen];
		int ip = 0;
		int op = 0;

		while (ip < iLen) {
			final int i0 = in64[ip++];
			final int i1 = in64[ip++];
			final int i2 = ip < iLen ? in64[ip++] : 'A';
			final int i3 = ip < iLen ? in64[ip++] : 'A';

			if ((i0 > 127) || (i1 > 127) || (i2 > 127) || (i3 > 127)) {
				throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
			}

			final int b0 = reverseMap[i0];
			final int b1 = reverseMap[i1];
			final int b2 = reverseMap[i2];
			final int b3 = reverseMap[i3];

			if ((b0 < 0) || (b1 < 0) || (b2 < 0) || (b3 < 0)) {
				throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
			}

			final int o0 = (b0 << 2) | (b1 >>> 4);
			final int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
			final int o2 = ((b2 & 3) << 6) | b3;

			out[op++] = (byte) o0;
			if (op < oLen) {
				out[op++] = (byte) o1;
			}
			if (op < oLen) {
				out[op++] = (byte) o2;
			}
		}
		return out;
	}

	public static BinaryEncoder getEncoder() {
		return new SafeBase64.Encoder();
	}
}
