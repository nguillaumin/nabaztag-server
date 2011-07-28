package net.violet.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class contains convenient methods to compute data signature according to a given algorithm.
 */
public class DigestTools {

	public enum Algorithm {
		MD5 {

			@Override
			protected MessageDigest getDigester() {
				try {
					return MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {
					// should never happen
				}

				return null;
			}
		},
		SHA1 {

			@Override
			protected MessageDigest getDigester() {
				try {
					return MessageDigest.getInstance("SHA-1");
				} catch (NoSuchAlgorithmException e) {
					// Should never happen
				}

				return null;
			}
		};

		protected abstract MessageDigest getDigester();
	}

	public static String digest(String inData, Algorithm inAlgorithm) {
		return digest(inData.getBytes(), inAlgorithm);
	}

	public static String digest(byte[] inData, Algorithm inAlgorithm) {
		final MessageDigest theDigest = inAlgorithm.getDigester();
		theDigest.update(inData);
		final byte[] digest = theDigest.digest();
		final StringBuilder theResult = new StringBuilder();
		for (int i = 0; i < digest.length; ++i) {
			int value = digest[i];
			if (value < 0) {
				value += 256;
			}
			if (value < 16) {
				theResult.append("0");
			}
			theResult.append(Integer.toHexString(value));
		}
		return theResult.toString();
	}

}
