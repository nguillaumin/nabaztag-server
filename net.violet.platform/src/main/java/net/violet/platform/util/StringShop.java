package net.violet.platform.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class StringShop extends net.violet.common.StringShop {

	private StringShop() {
		// This space for rent
	}

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

	// words
	public static final String ID = "id";
	public static final String IS = "IS";
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	public static final String FILE = "file";
	public static final String LANG = "lang";
	public static final String SERVICE = "service";
	public static final String APPLICATION = "application";
	public static final String AND = "AND";
	public static final String WHERE = "WHERE";
	public static final String NULL = "NULL";
	public static final String LIKE = "LIKE";
	public static final String INDEX = "index";
	public static final String CONTENT = "content";
	public static final String ACTION = "action";
	public static final String MESSENGER = "messenger";
	public static final String MESSAGE = "message";
	public static final String STATE = "state";
	public static final String RECIPIENT = "recipient";
	public static final String NABCAST = "nabcast";
	public static final String TYPE = "type";

	// The conditions material
	/** ie : AND */
	public static final String CONDITION_ACCUMULATOR;

	/** ie : LIKE */
	public static final String CONDITION_LIKE;
	/** ie : = ? */
	public static final String CONDITION;
	/** ie : IS NULL */
	public static final String IS_NULL;
	/** ie : id DESC */
	public static final String ID_DESC;
	/** ie : id ASC */
	public static final String ID_ASC;

	static {
		StringBuilder theBuilder = new StringBuilder();

		theBuilder.append(net.violet.common.StringShop.SPACE);
		theBuilder.append(StringShop.AND);
		theBuilder.append(net.violet.common.StringShop.SPACE);
		CONDITION_ACCUMULATOR = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(net.violet.common.StringShop.SPACE);
		theBuilder.append(StringShop.LIKE);
		theBuilder.append(net.violet.common.StringShop.SPACE);
		CONDITION_LIKE = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(net.violet.common.StringShop.SPACE);
		theBuilder.append(OPERATORS.EQUAL.getSymbol());
		theBuilder.append(net.violet.common.StringShop.SPACE);
		theBuilder.append(net.violet.common.StringShop.QUESTION_MARK);
		theBuilder.append(net.violet.common.StringShop.SPACE);
		CONDITION = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(net.violet.common.StringShop.SPACE);
		theBuilder.append(StringShop.IS);
		theBuilder.append(net.violet.common.StringShop.SPACE);
		theBuilder.append(StringShop.NULL);
		theBuilder.append(net.violet.common.StringShop.SPACE);
		IS_NULL = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(net.violet.common.StringShop.SPACE);
		theBuilder.append(StringShop.ID);
		theBuilder.append(net.violet.common.StringShop.SPACE);
		theBuilder.append(StringShop.DESC);
		theBuilder.append(net.violet.common.StringShop.SPACE);
		ID_DESC = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(net.violet.common.StringShop.SPACE);
		theBuilder.append(StringShop.ID);
		theBuilder.append(net.violet.common.StringShop.SPACE);
		theBuilder.append(StringShop.ASC);
		theBuilder.append(net.violet.common.StringShop.SPACE);
		ID_ASC = theBuilder.toString();
	}

	// Field names

	/** ie : file_id */
	public static final String FILE_ID;
	/** ie : service_id */
	public static final String SERVICE_ID;
	/** ie : application_id */
	public static final String APPLICATION_ID;
	/** ie : lang_id */
	public static final String LANG_ID;
	/** ie : content_id */
	public static final String CONTENT_ID;
	/** ie : action_id */
	public static final String ACTION_ID;
	/** ie : messenger_id */
	public static final String MESSENGER_ID;
	/** ie : message_id */
	public static final String MESSAGE_ID;
	/** ie : recipient_id */
	public static final String RECIPIENT_ID;
	/** ie : message_state */
	public static final String MESSAGE_STATE;
	/** ie : `index` */
	public static final String INDEX_BACK_QUOTED;

	static {
		final String UNDERSCORE_ID = net.violet.common.StringShop.UNDERSCORE + StringShop.ID;

		StringBuilder theBuilder = new StringBuilder();
		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.FILE);
		theBuilder.append(UNDERSCORE_ID);
		FILE_ID = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.SERVICE);
		theBuilder.append(UNDERSCORE_ID);
		SERVICE_ID = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.APPLICATION);
		theBuilder.append(UNDERSCORE_ID);
		APPLICATION_ID = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.LANG);
		theBuilder.append(UNDERSCORE_ID);
		LANG_ID = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.CONTENT);
		theBuilder.append(UNDERSCORE_ID);
		CONTENT_ID = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.ACTION);
		theBuilder.append(UNDERSCORE_ID);
		ACTION_ID = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(net.violet.common.StringShop.BACK_QUOTE);
		theBuilder.append(StringShop.INDEX);
		theBuilder.append(net.violet.common.StringShop.BACK_QUOTE);
		INDEX_BACK_QUOTED = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.MESSENGER);
		theBuilder.append(UNDERSCORE_ID);
		MESSENGER_ID = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.MESSAGE);
		theBuilder.append(UNDERSCORE_ID);
		MESSAGE_ID = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.MESSAGE);
		theBuilder.append(net.violet.common.StringShop.UNDERSCORE);
		theBuilder.append(StringShop.STATE);
		MESSAGE_STATE = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.RECIPIENT);
		theBuilder.append(UNDERSCORE_ID);
		RECIPIENT_ID = theBuilder.toString();

	}

	// Simple conditions on fields
	/** ie : file_id = ? */
	public static final String FILE_ID_CONDITION;
	/** ie : service_id = ? */
	public static final String SERVICE_ID_CONDITION;
	/** ie : application_id = ? */
	public static final String APPLICATION_ID_CONDITION;
	/** ie : lang_id = ? */
	public static final String LANG_ID_CONDITION;
	/** ie : content_id = ? */
	public static final String CONTENT_ID_CONDITION;
	/** ie : action_id = ? */
	public static final String ACTION_ID_CONDITION;
	/** ie : application_id IS NULL */
	public static final String APPLICATION_IS_NULL;
	/** ie : messenger_id = ? */
	public static final String MESSENGER_ID_CONDITION;
	/** ie : message_id = ? */
	public static final String MESSAGE_ID_CONDITION;
	/** ie : recipient_id = ? */
	public static final String RECIPIENT_ID_CONDITION;
	/** ie : message_state = ? */
	public static final String MESSAGE_STATE_CONDITION;

	static {
		StringBuilder theBuilder = new StringBuilder();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.FILE_ID);
		theBuilder.append(StringShop.CONDITION);
		FILE_ID_CONDITION = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.SERVICE_ID);
		theBuilder.append(StringShop.CONDITION);
		SERVICE_ID_CONDITION = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.APPLICATION_ID);
		theBuilder.append(StringShop.CONDITION);
		APPLICATION_ID_CONDITION = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.APPLICATION_ID);
		theBuilder.append(StringShop.IS_NULL);
		APPLICATION_IS_NULL = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.LANG_ID);
		theBuilder.append(StringShop.CONDITION);
		LANG_ID_CONDITION = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.CONTENT_ID);
		theBuilder.append(StringShop.CONDITION);
		CONTENT_ID_CONDITION = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.ACTION_ID);
		theBuilder.append(StringShop.CONDITION);
		ACTION_ID_CONDITION = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.MESSENGER_ID);
		theBuilder.append(StringShop.CONDITION);
		MESSENGER_ID_CONDITION = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.MESSAGE_ID);
		theBuilder.append(StringShop.CONDITION);
		MESSAGE_ID_CONDITION = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.RECIPIENT_ID);
		theBuilder.append(StringShop.CONDITION);
		RECIPIENT_ID_CONDITION = theBuilder.toString();

		theBuilder = new StringBuilder();
		theBuilder.append(StringShop.MESSAGE_STATE);
		theBuilder.append(StringShop.CONDITION);
		MESSAGE_STATE_CONDITION = theBuilder.toString();
	}

	// trigger constant param
	/** ie : :: */
	public static final String DOUBLE_COLUMNS;

	static {
		final StringBuilder theBuilder = new StringBuilder();

		theBuilder.append(net.violet.common.StringShop.COLUMN);
		theBuilder.append(net.violet.common.StringShop.COLUMN);
		DOUBLE_COLUMNS = theBuilder.toString();
	}

	// Extensions

	public static final String MP3 = "mp3";
	public static final String MIDI = "mid";
	public static final String CHOR = "chor";
	public static final String ADP = "adp";
	public static final String JPG = "jpg";
	public static final String GIF = "gif";
	public static final String PCM = "pcm";
	public static final String AAC = "aac";
	public static final String FLV = "flv";
	public static final String WAV = "wav";
	public static final String JSON = "json";
	public static final String JS = "js";
	public static final String XML = "xml";
	public static final String MP3_EXT;
	public static final String MIDI_EXT;
	public static final String CHOR_EXT;
	public static final String ADP_EXT;
	public static final String PCM_EXT;
	public static final String AAC_EXT;
	public static final String FLV_EXT;
	public static final String JPG_SMALL_EXT;
	public static final String JPG_BIG_EXT;
	public static final String GIF_EXT;
	public static final String JSON_EXT;
	public static final String JS_EXT;
	public static final String XML_EXT;

	static {
		StringBuilder theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.MP3);
		MP3_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.MIDI);
		MIDI_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.CHOR);
		CHOR_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.ADP);
		ADP_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.UNDERSCORE);
		theBuilder.append("S");
		theBuilder.append(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.JPG);
		JPG_SMALL_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.UNDERSCORE);
		theBuilder.append("B");
		theBuilder.append(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.JPG);
		JPG_BIG_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.GIF);
		GIF_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.PCM);
		PCM_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.AAC);
		AAC_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.FLV);
		FLV_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.JSON);
		JSON_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.JS);
		JS_EXT = theBuilder.toString();

		theBuilder = new StringBuilder(net.violet.common.StringShop.POINT);
		theBuilder.append(StringShop.XML);
		XML_EXT = theBuilder.toString();
	}

	/**
	 * Does the exact opposite of split.
	 * 
	 * @param myCollection : a collection of objects
	 * @param notNull : if true we will not put "null" in the string else we
	 *            will
	 * @return returns a String
	 */
	public static <T> String unsplit(Collection<T> myCollection, final String separator, boolean notNull) {

		if ((myCollection != null) && (0 < myCollection.size())) {

			final SortedSet<String> mySortedSet = new TreeSet<String>() {

				@Override
				public String toString() {
					final StringBuilder theBuilder = new StringBuilder();

					for (final Iterator<String> i = iterator(); i.hasNext();) {
						theBuilder.append(i.next());
						if (i.hasNext()) {
							theBuilder.append(separator);
						}
					}

					return theBuilder.toString();
				}
			};

			for (final T anElement : myCollection) {
				if (anElement != null) {
					mySortedSet.add(String.valueOf(anElement));
				} else if (!notNull) {
					mySortedSet.add(StringShop.NULL);
				}
			}

			return mySortedSet.toString();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * Does the exact opposite of split.
	 * 
	 * @param StringArray : an array of strings
	 * @param notNull : if true we will not put "null" in the string else we
	 *            will
	 * @return returns a String
	 */
	public static String unsplit(String[] StringArray, String separator, boolean notNull) {
		return StringShop.unsplit(Arrays.asList(StringArray), separator, notNull);
	}
}
