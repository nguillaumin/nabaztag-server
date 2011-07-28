package net.violet.platform.util;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class CipherTools {

	public static final Logger LOGGER = Logger.getLogger(CipherTools.class);

	public static String cipher(String inString) {
		String src = inString;
		try {

			String res = net.violet.common.StringShop.EMPTY_STRING;
			final int l0 = src.length();
			int l = l0;
			if ((l & 7) != 0) {
				l = (l + 7) & ~7;
				src = (src + "       ").substring(0, l);
			}
			final byte[] s = src.getBytes("ISO-8859-1");

			final int[] k = new int[4];
			k[0] = 0x1234; // initialisation de la clef
			k[1] = 0x5678;
			k[2] = 0x9abc;
			k[3] = 0xdef0;

			int i;
			for (i = 0; i < l; i += 8) {
				int y = (s[i] & 255) + ((s[i + 1] & 255) << 8) + ((s[i + 2] & 255) << 16) + ((s[i + 3] & 255) << 24);
				int z = (s[i + 4] & 255) + ((s[i + 5] & 255) << 8) + ((s[i + 6] & 255) << 16) + ((s[i + 7] & 255) << 24);
				int sum = 0;
				final int delta = 0x9E3779B9;
				int n;
				for (n = 0; n < 32; n++) {
					y += (z << 4 ^ z >> 5) + z ^ sum + k[sum & 3];
					sum += delta;
					z += (y << 4 ^ y >> 5) + y ^ sum + k[sum >> 11 & 3];
				}
				res += CipherTools.itoalpha(y) + CipherTools.itoalpha(z);
			}
			if (l != l0) {
				res += "xxxxxxx".substring(0, l - l0);
			}
			return res;
		} catch (final Throwable t) {}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	private static String itoalpha(int inValue) {
		int i = inValue;
		try {
			final byte[] b = new byte[8];
			for (int j = 0; j < 8; j++) {
				final Integer x = new Integer(97 + (i & 15));
				b[j] = x.byteValue();
				i >>= 4;
			}
			return new String(b, "ISO-8859-1");
		} catch (final NumberFormatException anException) {
			CipherTools.LOGGER.fatal(anException, anException);
		} catch (final UnsupportedEncodingException anException) {
			CipherTools.LOGGER.fatal(anException, anException);
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static String uncipher(String src) {
		try {
			final int l0 = src.length();
			final int l = l0 & (~15);
			final byte[] s = src.getBytes("ISO-8859-1");

			final byte[] res = new byte[l / 2];

			final int[] k = new int[4];
			k[0] = 0x1234; // initialisation de la clef
			k[1] = 0x5678;
			k[2] = 0x9abc;
			k[3] = 0xdef0;

			int i;
			for (i = 0; i < l; i += 16) {
				int y = CipherTools.alphatoi(s, i);
				int z = CipherTools.alphatoi(s, i + 8);

				int sum = 0xC6EF3720;
				final int delta = 0x9E3779B9;

				int n;
				for (n = 0; n < 32; n++) {
					z -= (y << 4 ^ y >> 5) + y ^ sum + k[sum >> 11 & 3];
					sum -= delta;
					y -= (z << 4 ^ z >> 5) + z ^ sum + k[sum & 3];
				}
				CipherTools.itobtab(y, res, i / 2);
				CipherTools.itobtab(z, res, (i / 2) + 4);
			}
			String r = new String(res, "ISO-8859-1");
			if (l != l0) {
				r = r.substring(0, r.length() + l - l0);
			}
			return r;
		} catch (final Throwable t) {}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	private static int alphatoi(byte[] b, int i) {
		int res = 0;
		for (int j = 0; j < 8; j++) {
			res <<= 4;
			res += (b[i + 7 - j] & 255) - 97;
		}
		return res;
	}

	private static void itobtab(int inValue, byte[] b, int i0) {
		int v = inValue;
		for (int i = 0; i < 4; i++) {
			final Integer x = new Integer(v);
			b[i0 + i] = x.byteValue();
			v >>= 8;
		}
	}

}
