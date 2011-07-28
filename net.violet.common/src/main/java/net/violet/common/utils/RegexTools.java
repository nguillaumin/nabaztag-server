package net.violet.common.utils;

import java.util.regex.Pattern;

public class RegexTools {

	private static final Pattern INTEGER_REGEX = Pattern.compile("^\\d+$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\d-]+(\\.[\\w\\d-]+)*@[\\w\\d-]+(\\.[\\w\\d-]+)+$");

	public static boolean isInt(String s) {
		return (s != null) && RegexTools.INTEGER_REGEX.matcher(s).matches();
	}

	public static boolean isAValidEmail(String s) {
		return s != null && EMAIL_PATTERN.matcher(s).matches();
	}

}
