package net.violet.platform.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.common.StringShop;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.ObjectLangData;

import org.apache.log4j.Logger;

public final class ConvertTools {

	private static final Logger LOGGER = Logger.getLogger(ConvertTools.class);

	// used in the jsp.
	public static String strbackslash(String inString) {
		final StringBuilder cleanedString = new StringBuilder("");
		final String s = inString == null ? StringShop.EMPTY_STRING : inString.trim();

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\'') {
				cleanedString.append("\\'");
			} else if (s.charAt(i) == '\"') {
				cleanedString.append("\\\"");
			} else {
				cleanedString.append(s.charAt(i));
			}
		}

		return cleanedString.toString();
	}

	// see above.
	public static String strreturn(String s) {
		return s.replace('\r', ' ').replace('\n', ' ');
	}

	public static int atoi(String inString) {
		try {
			return ConvertTools.atoi_safe(inString);
		} catch (final NumberFormatException e) {
			ConvertTools.LOGGER.fatal(e, e);
			return 0;
		}
	}

	public static int atoi_safe(String inString) throws NumberFormatException {
		if ((inString == null) || (inString.trim().length() == 0)) {
			return 0;
		}

		final String trimedString = inString.trim();
		if (trimedString.startsWith("0x") || trimedString.startsWith("0X")) {
			return Integer.parseInt(trimedString.substring(2), 16);
		}

		return Integer.parseInt(trimedString, 10);
	}

	public static long atol(String inString) {
		if ((inString == null) || (inString.trim().length() == 0)) {
			return 0;
		}

		final String trimedString = inString.trim();
		try {
			if ((trimedString.startsWith("0x")) || (trimedString.startsWith("0X"))) {
				return Integer.parseInt(trimedString.substring(2), 16);
			}
			return Long.parseLong(trimedString);
		} catch (final NumberFormatException anException) {
			ConvertTools.LOGGER.fatal(anException, anException);
			return 0;
		}
	}

	private static final Random RANDOM_GENERATOR = new Random(System.currentTimeMillis());

	/**
	 * gnre un int alatoire
	 * 
	 * @return retourne le int
	 */
	public static int randCodeInt() {
		return 10000 + ConvertTools.RANDOM_GENERATOR.nextInt(65536);
	}

	public static String extractInfo(String content, String before, String after, int inValue) {
		if (inValue < 0) {
			return StringShop.EMPTY_STRING;
		}

		int i = inValue;

		if ((before.length() != 0) && ((i = content.indexOf(before, i)) < 0)) {
			return StringShop.EMPTY_STRING;
		}

		i += before.length();
		int j = content.indexOf(after, i);

		if (j < 0) {
			j = content.length();
		}

		return content.substring(i, j).trim();
	}

	private static final Pattern SHORT_LANGUAGE_REGEX = Pattern.compile("(^[a-z]{2})");

	/**
	 * ce code est utilisé pour l'upload de l'horloge communautaire.
	 * net.violet.platform.web.include_jsp.utils.ClockHC
	 */
	@Deprecated
	public static Lang switchLangByShortIsocode(String shortIsocode) {
		String isocodePath = shortIsocode;
		if (isocodePath.equalsIgnoreCase("us")) {
			isocodePath = "en-US";
		} else if (isocodePath.equalsIgnoreCase("uk")) {
			isocodePath = "en-GB";
		} else if (isocodePath.equalsIgnoreCase("br")) {
			isocodePath = "pt-BR";
		}

		final Matcher theMatcher = ConvertTools.SHORT_LANGUAGE_REGEX.matcher(isocodePath);
		if (theMatcher.matches()) {
			isocodePath = theMatcher.group(1) + "-" + theMatcher.group(1).toUpperCase();
		}

		Lang theLang = null;
		try {
			final ObjectLangData theObjectLang = ObjectLangData.getByISOCode(isocodePath);
			if (theObjectLang.isValid()) {
				theLang = theObjectLang.getReference();
			}
		} catch (final InvalidParameterException e) {}

		return theLang;
	}

	public static int htoi(String inS) {
		return ConvertTools.htoi(inS, true);
	}

	public static int htoi(String inS, boolean isLog) {
		try {
			String s = inS;
			if (s == null) {
				return 0;
			}
			s = s.trim();
			if (s.length() == 0) {
				return 0;
			}
			return Integer.parseInt(s, 16);
		} catch (final NumberFormatException anException) {
			if (isLog) {
				LibBasic.LOGGER.fatal(anException, anException);
			}
		}
		return 0;
	}

}
