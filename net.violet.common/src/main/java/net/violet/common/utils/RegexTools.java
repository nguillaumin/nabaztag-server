package net.violet.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTools {

	private static final Pattern INTEGER_REGEX = Pattern.compile("^\\d+$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\d-]+(\\.[\\w\\d-]+)*@[\\w\\d-]+(\\.[\\w\\d-]+)+$");
	private static final Pattern VAR_EXPAND_PATTERN = Pattern.compile("\\$\\{(.*?)\\}");

	public static boolean isInt(String s) {
		return (s != null) && RegexTools.INTEGER_REGEX.matcher(s).matches();
	}

	public static boolean isAValidEmail(String s) {
		return s != null && EMAIL_PATTERN.matcher(s).matches();
	}

	public static String expandWithSysProp(String s) {
		Matcher m = VAR_EXPAND_PATTERN.matcher(s);
		if (m.find()) {
			StringBuffer sb = new StringBuffer();
			do {
				m.appendReplacement(sb, System.getProperty(m.group(1)));
			} while (m.find());
			m.appendTail(sb);
			return sb.toString();
		} else {
			return s;
		}
	}

}
