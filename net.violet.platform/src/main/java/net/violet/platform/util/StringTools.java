package net.violet.platform.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class StringTools {

	private static final Logger LOGGER = Logger.getLogger(StringTools.class);

	// all control characters including CR (\r), LF	(\n) and TAB (\t)
	private static final Pattern ALL_CTL_CHARS = Pattern.compile("[\\x00-\\x1F]");
	// all control characters including CR (\r) but keep LF	(\n) and TAB (\t)
	private static final Pattern CTL_CHARS = Pattern.compile("[\\x00-\\x1F&&[^\\n\\t]]");

	private static final Pattern SURR_QUOTES = Pattern.compile("^('|\\s|\")*|('|\\s|\")*$");

	/**
	 * Get rid of all invisible and control chars (in the plage 0..31)
	 * Useful before XML parsing (control chars are forbidden in XML)
	 * or before YAML serializing (you can't imagine how YAML serializer is.. sensible)
	 * 
	 * @param inInput
	 * @param inRemoveTabAndLf TRUE to remove too TABs and LFs
	 * @param inRemoveSurroundingQuotes TRUE to remove leading and trailing simple quotes (')
	 * @return the cleaned String
	 */
	public static String cleanControlChars(String inInput, boolean inRemoveTabAndLf, boolean inRemoveSurroundingQuotes) {
		if (inInput == null) {
			return net.violet.common.StringShop.EMPTY_STRING;
		}

		final Pattern cleanPattern = (inRemoveTabAndLf ? StringTools.ALL_CTL_CHARS : StringTools.CTL_CHARS);
		final String firstPass = cleanPattern.matcher(inInput).replaceAll(net.violet.common.StringShop.EMPTY_STRING);

		if (inRemoveSurroundingQuotes) {
			return StringTools.SURR_QUOTES.matcher(firstPass).replaceAll(net.violet.common.StringShop.EMPTY_STRING);
		}
		return firstPass;
	}

	/**
	 * Useful for HTML display
	 * @param inInput
	 * @return
	 */
	public static String cr2br(String inInput) {
		if (inInput == null) {
			return net.violet.common.StringShop.EMPTY_STRING;
		}
		return StringTools.cleanControlChars(inInput.replaceAll("\n", "<br/>"), true, false);
	}

	/**
	 * Unescapes a string from HTML, XML elements and tags. It also removes \n
	 * and/or \r
	 * 
	 * @param myString
	 * @return the clean string
	 */
	public static String unescapeString(String myString) {
		try {
			final String theResult = StringEscapeUtils.unescapeHtml(myString).replaceAll("\n|\r|<[^>]+>", net.violet.common.StringShop.EMPTY_STRING);

			if (theResult != null) {
				return StringEscapeUtils.unescapeXml(theResult);
			}
		} catch (final RuntimeException e) {

			if (e instanceof NumberFormatException) {
				String badString = net.violet.common.StringShop.EMPTY_STRING;
				final Pattern pRegex = Pattern.compile("For input string: \"([\\w]+)\"");
				final Matcher mRegex = pRegex.matcher(e.getMessage());

				if (mRegex.find()) {
					if (1 == mRegex.groupCount()) {
						badString = mRegex.group(1);
					}
				}
				return StringTools.unescapeString(StringTools.unescape(myString, badString));
			}
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * Try a recover a HTML or XML code within a String
	 * 
	 * @param str
	 * @return
	 */
	private static String unescape(String fullString, String badString) {
		int workingString = 8194;
		final StringBuilder buf = new StringBuilder(fullString.length());

		buf.append(fullString.substring(0, fullString.indexOf(badString)));

		if (badString.toLowerCase().startsWith("x")) {

			// First we try to see if the given string was not supposed to start
			// with an x:
			// it might not be hexa
			try {
				workingString = Integer.parseInt(badString.substring(1));
			} catch (final NumberFormatException e) {
				StringTools.LOGGER.fatal(e, e);
			}

			// Then we try to see if the given string was supposed to start with
			// a 0:
			// it might have been hexa after all
			if (workingString == 8194) {
				try {
					workingString = Integer.decode("0" + badString);
				} catch (final NumberFormatException e) {
					StringTools.LOGGER.fatal(e, e);
				}
			}
		}
		buf.append(workingString);
		buf.append(fullString.substring(fullString.indexOf(badString) + badString.length()));
		return buf.toString();
	}

	/**
	 * Tronque une chaîne si elle est plus grande que n caractères.
	 */
	public static String truncate(String inString, int inMaxSize) {
		if (inString == null) {
			return net.violet.common.StringShop.EMPTY_STRING;
		}

		return (inString.length() > inMaxSize) ? inString.substring(0, inMaxSize) : inString;
	}

	/**
	 * Tronque une chaîne en indiquant le marqueur de fin
	 */
	public static String truncate(String inString, int inMaxSize, String inEndMarker) {

		if (inString == null) {
			return net.violet.common.StringShop.EMPTY_STRING;
		}

		if (inString.length() > inMaxSize) {
			return inString.substring(0, inMaxSize - inEndMarker.length()) + inEndMarker;
		}
		return inString;
	}

	/**
	 * Extract the first sentence from the given text. A sentence ends with a period (.) The last character of the 
	 * returned string IS THE PERIOD.
	 * 
	 * The maxLength parameter indicates the maximum length for the returned String, if it is lower than 0 then
	 * it is ignored and there is not any size restriction.
	 * 
	 * If a period is found in the valid part of the text (depending on the maxLength parameter) then the sentence is returned.
	 * If there is not any period in the valid part either the whole text is returned (if there is not size restriction) or 
	 * the text is truncated to match the size condition.
	 * 
	 * Some exemples :
	 * text = a nice little text. With two sentences.
	 * . getFirstSentence(text, 40) -> a nice little text. 
	 * . getFirstSentence(text, 4) -> a ni
	 * . getFirstSentence(text, -1) -> a nice little text.
	 * 
	 * text = text without dot
	 * . getFirstSentence(text, -1) -> text without dot
	 * . getFirstSentence(text, 4) -> text
	 * . getFirstSentence(text, 40) -> text without dot
	 * 
	 * 
	 * @param text
	 * @param maxLength
	 * @return
	 */
	public static String getFirstSentence(String text, int maxLength) {
		final int indexOfPoint = text.indexOf('.');

		// the string contains a dot and the potential substring has a good size or there is no size restriction
		if ((indexOfPoint > 0) && ((indexOfPoint < maxLength) || (maxLength < 0))) {
			return text.substring(0, indexOfPoint + 1);
		}

		// there is not any dot in the text, if there is no size restriction the text is returned else
		// the string is cut with the lowest possible size.
		return maxLength < 0 ? text : text.substring(0, Math.min(text.length(), maxLength));
	}

}
