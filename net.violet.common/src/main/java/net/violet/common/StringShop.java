package net.violet.common;

public abstract class StringShop {

	/**
	 * Operators with their corresponding {@link String}s
	 */
	public static enum OPERATORS {
		EQUAL;

		private static final String EQUALS = "=";

		public String getSymbol() {
			switch (this) {
			case EQUAL:
				return OPERATORS.EQUALS;

			default:
				return null;
			}
		}
	};

	// Common static Strings

	public static final String MIDNIGHT = "00:00:00";

	// Numbers
	public static final String ZERO = "0";

	// symbols
	public static final String QUESTION_MARK = "?";
	public static final String DOLLAR = "$";
	public static final String AMPERSAND = "&";
	public static final String EMPTY_STRING = "";
	public static final String SPACE = " ";
	public static final String UNDERSCORE = "_";
	public static final String PERCENT = "%";
	public static final String SIMPLE_QUOTE = "'";
	public static final String DOUBLE_QUOTE = "\"";
	public static final String BACK_QUOTE = "`";
	public static final String POINT = ".";
	public static final String COLUMN = ":";
	public static final String SEMI_COLUMN = ";";
	public static final String COMMA = ",";
	public static final String SLASH = "/";
	public static final String HYPHEN = "-";

	public static final String EOL = "\n";

	public static final String LANG_ID = "lang_id";
	public static final String WAV_EXT = ".wav";

}
