package net.violet.platform.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.SiteLangData;

import org.apache.log4j.Logger;

public class CCalendar extends GregorianCalendar {

	private static final Logger LOGGER = Logger.getLogger(CCalendar.class);

	private static final SimpleDateFormat HOUR_FORMAT24 = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat HOUR_FORMAT12 = new SimpleDateFormat("hh:mm:ss a");
	private static final SimpleDateFormat HOUR_FORMAT24_SHORT = new SimpleDateFormat("HH:mm");
	private static final SimpleDateFormat HOUR_FORMAT12_SHORT = new SimpleDateFormat("hh:mm a");
	private static final SimpleDateFormat DATE_Of_BIRTH = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat MAINTENANCE_FORMAT_JS = new SimpleDateFormat("MM/dd/yyyy HH:mm a", new Locale("en"));
	private static final SimpleDateFormat MAINTENANCE_FORMAT_MESSAGE = new SimpleDateFormat("MMMM, dd 'at' HH:mm a zzzz", new Locale("en", "FR"));
	private static final SimpleDateFormat DATE_TIME_ISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	private static final SimpleDateFormat TIMESTAMP_SQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final TimeZone PARIS_TIME = TimeZone.getTimeZone("Europe/Paris");
	public static final TimeZone UNIVERSAL_TIME = TimeZone.getTimeZone("UTC");

	public static final String[] MONTH_LABELS = CCalendar.createLabelsMonths();

	/*** CONSTRUCTORS ***/

	/**
	 * create a calendar set on the current time using the local locale
	 * 
	 *@param initialized tell whether or not to initialized the time to 00:00:00:00 on Paris TimeZone
	 */
	public CCalendar(boolean initialized) {
		this(initialized, CCalendar.PARIS_TIME);
	}

	/**
	 * create a calendar set on the current time using the local locale
	 * 
	 *@param timeInMilliseconds initialize the CCalendar at this time
	 */
	public CCalendar(long timeInMilliseconds) {
		this(timeInMilliseconds, CCalendar.PARIS_TIME);
	}

	/**
	 * create a calendar set on the current time using the local locale
	 * 
	 *@param timeInMilliseconds initialize the CCalendar at this time
	 */
	public CCalendar(long timeInMilliseconds, TimeZone inTimeZone) {
		this(inTimeZone);
		setTimeInMillis(timeInMilliseconds);
	}

	/**
	 * create a calendar set on the current time using the given timezone.
	 */
	public CCalendar(TimeZone timeZone) {
		super(timeZone);
	}

	/**
	 * create a calendar set on the current time using the local locale
	 * 
	 *@param initialized tell whether or not to initialized the time to 00:00:00:00 on the given TimeZone
	 */
	public CCalendar(boolean initialized, TimeZone timeZone) {
		this(timeZone);
		if (initialized) {
			reset();
		}
	}

	/**
	 * create a calendar set on the given time (HH:MM:SS) using the local locale and the given time zone
	 */
	public CCalendar(String time, TimeZone timeZone) {
		this(timeZone);
		setTimeMYSQL(time);
	}

	/*** SET TIME ***/

	/**
	 * Sets into the calendar a time given in a MySQL friendly form CAUTION: The date (YYYY/MM/DD part of the CCalendar depends on your
	 * initialisation)
	 * 
	 * @param the time : HH:MM:SS
	 */
	public void setTimeMYSQL(String time) {
		try {
			setTimeInMillis(CCalendar.parseTimeForTZ(CCalendar.HOUR_FORMAT24, getTimeZone(), time).getTimeInMillis());
		} catch (final ParseException e) {
			CCalendar.LOGGER.fatal(e, e);
		}
	}

	private static CCalendar parseTimeForTZ(SimpleDateFormat inFormat, TimeZone inTimeZone, String timeFormated) throws ParseException {
		final SimpleDateFormat theFormat = (SimpleDateFormat) inFormat.clone();
		theFormat.setTimeZone(inTimeZone);
		// Important to keep that way b.c some formaters only take the time into
		// account and not the date part of a CCalendar
		final CCalendar theResult = new CCalendar(true, inTimeZone);
		final CCalendar tmpCalendar = new CCalendar(true, inTimeZone);
		tmpCalendar.setTimeInMillis(theFormat.parse(timeFormated).getTime());
		theResult.setHour(tmpCalendar.getHour());
		theResult.setMinute(tmpCalendar.getMinute());
		theResult.setSecond(tmpCalendar.getSecond());
		theResult.complete();
		return theResult;
	}

	private static CCalendar parseDateForTZ(SimpleDateFormat inFormat, TimeZone inTimeZone, String timeFormated) throws ParseException {
		final SimpleDateFormat theFormat = (SimpleDateFormat) inFormat.clone();
		theFormat.setTimeZone(inTimeZone);
		return new CCalendar(theFormat.parse(timeFormated).getTime(), inTimeZone);
	}

	/**
	 * Sets into the calendar a time given in a friendly form BEWARE: the time you are giving has to be possible: NOT something like: 22:20 AM
	 * 
	 * @param the time : HH:MM or hh:MM AM/PM
	 * @throws ParseException
	 */
	public void setTimeFormatted(String time) throws ParseException {

		try {
			setTime(CCalendar.parseTimeForTZ(CCalendar.HOUR_FORMAT12_SHORT, getTimeZone(), time).getTime());
		} catch (final ParseException e) {
			setTime(CCalendar.parseTimeForTZ(CCalendar.HOUR_FORMAT24_SHORT, getTimeZone(), time).getTime());
		}
	}

	/**
	 * Sets into the calendar a time given in a friendly form
	 * 
	 * @param the time :yyyy-MM-dd HH:mm:ss
	 */
	public static Date parseTimestamp(String time) {
		return CCalendar.parseTimestamp(time, "UTC").getTime();
	}

	public static CCalendar parseTimestamp(String time, String inTimeZone) {
		try {
			return CCalendar.parseDateForTZ(CCalendar.TIMESTAMP_SQL, TimeZone.getTimeZone(inTimeZone), time);
		} catch (final ParseException e) {
			CCalendar.LOGGER.fatal(e, e);
		}
		return null;
	}

	/*** FORMAT TIME ***/

	/**
	 * Returns the calendar in a friendly form
	 * 
	 * @return the date : yyyy-mm-dd
	 */
	public String getDateOfBirthFormatted() {
		return formatForTZ(CCalendar.DATE_Of_BIRTH);
	}

	/**
	 * Returns the calendar in a friendly form
	 * 
	 * @return the date : dd [dicokey] yyyy
	 */
	private String getDateFormattedDico(Lang inLang) {
		return this.getDay() + net.violet.common.StringShop.SPACE + DicoTools.dico(inLang, CCalendar.MONTH_LABELS[this.getMonth() + 1]) + net.violet.common.StringShop.SPACE + this.getYear();
	}

	/**
	 * Returns the calendar in a friendly form
	 * 
	 * @return the date : [dicokey] dd yyyy
	 */
	private String getDateFormattedUSDico(Lang inLang) {
		return DicoTools.dico(inLang, CCalendar.MONTH_LABELS[this.getMonth() + 1]) + net.violet.common.StringShop.SPACE + this.getDay() + net.violet.common.StringShop.SPACE + this.getYear();
	}

	/**
	 * Returns the calendar in a friendly form
	 * 
	 * @return the date : yyyy [dicokey] dd
	 */
	private String getDateFormattedJPDico(Lang inLang) {
		return this.getYear() + net.violet.common.StringShop.EMPTY_STRING + DicoTools.dico(inLang, CCalendar.MONTH_LABELS[this.getMonth() + 1]) + net.violet.common.StringShop.SPACE + this.getDay();
	}

	private static String[] createLabelsMonths() {
		final SimpleDateFormat theMonthFormat = new SimpleDateFormat("MMMM", new Locale("en"));
		final String[] months = new String[13];
		final CCalendar myCalendar = new CCalendar(false);
		months[0] = "profile/date_month";

		for (int i = 0; i <= 11; i++) {
			myCalendar.set(Calendar.MONTH, i);
			months[i + 1] = "profile/date_month_" + theMonthFormat.format(myCalendar.getTime());
		}

		return months;
	}

	/**
	 * Returns the calendar
	 * 
	 * @return the date : HH:MM:SS
	 */
	private String getTimeFormated(boolean is24, boolean short_format) {
		final SimpleDateFormat theSimpleDateFormat;

		if (is24) {
			theSimpleDateFormat = (short_format) ? CCalendar.HOUR_FORMAT24_SHORT : CCalendar.HOUR_FORMAT24;
		} else {
			theSimpleDateFormat = (short_format) ? CCalendar.HOUR_FORMAT12_SHORT : CCalendar.HOUR_FORMAT12;
		}
		return formatForTZ(theSimpleDateFormat);
	}

	public String getTimeFormated(boolean is24) {
		return getTimeFormated(is24, false);
	}

	private String formatForTZ(SimpleDateFormat inFormat) {
		final SimpleDateFormat theFormat = (SimpleDateFormat) inFormat.clone();
		theFormat.setTimeZone(getTimeZone());
		return theFormat.format(getTime());
	}

	/**
	 * Returns the time of this CCalendar as a Timestamp ( YYYY-MM-DD HH:MM:SS ) (We are using the UTC Timezone)
	 * 
	 * @return
	 */
	public String getTimestampUTC() {
		return getTimestamp(CCalendar.UNIVERSAL_TIME);
	}

	public String getTimestamp(TimeZone inTimeZone) {
		setTimeZone(inTimeZone);
		return getTimestamp();
	}

	public String getTimestamp() {
		return formatForTZ(CCalendar.TIMESTAMP_SQL);
	}

	/**
	 * Returns the calendar
	 * 
	 * @return the date : HH:MM
	 */
	public String getTimeFormatedShort(boolean is24) {
		return formatForTZ((is24) ? CCalendar.HOUR_FORMAT24_SHORT : CCalendar.HOUR_FORMAT12_SHORT);
	}

	public int getHour() {
		return get(Calendar.HOUR_OF_DAY);
	}

	public int getMinute() {
		return get(Calendar.MINUTE);
	}

	private int getSecond() {
		return get(Calendar.SECOND);
	}

	public int getYear() {
		return get(Calendar.YEAR);
	}

	public int getMonth() {
		return get(Calendar.MONTH);
	}

	public int getDay() {
		return get(Calendar.DAY_OF_MONTH);
	}

	public CCalendar setHour(int hour) {
		set(Calendar.HOUR_OF_DAY, hour);
		complete();
		return this;
	}

	public CCalendar setMinute(int minute) {
		set(Calendar.MINUTE, minute);
		complete();
		return this;
	}

	public CCalendar setSecond(int second) {
		set(Calendar.SECOND, second);
		complete();
		return this;
	}

	/**
	 * Set an amount of milliseconds to the calendar, should be used to create periods of time. This isn't a time stamp.
	 * 
	 * @param millisecond
	 */
	public CCalendar setMillisecond(int millisecond) {
		set(Calendar.MILLISECOND, millisecond);
		complete();
		return this;
	}

	public CCalendar reset() {
		setHour(0);
		setMinute(0);
		setSecond(0);
		setMillisecond(0);
		return this;
	}

	/**
	 * Compare 2 CCalendars in terms of days
	 * 
	 * @return returns 0 if the 2 CCalendar are set to the same day , 1 for yesterday, other -1
	 */
	private long compareTo(CCalendar inCCalendar) {
		int result = -1;
		if ((this.getYear() == inCCalendar.getYear()) && (this.getMonth() == inCCalendar.getMonth())) {
			result = this.getDay() - inCCalendar.getDay();
		}

		return result;
	}

	/**
	 * Gets the time formated relatively to now: ex : if the 2 calendars describe the same day it will say Today ....
	 * 
	 * @param timeZone if <code>null</code> then the current time zone is used
	 * @param use24
	 * @param inLang
	 * @return
	 */
	public String getTimeFormatedRelativeToNow(TimeZone timeZone, boolean use24, Lang inLang) {
		if (timeZone != null) {
			return getTimeFormatedRelativeTo(new CCalendar(false, timeZone), use24, inLang);
		}

		return getTimeFormatedRelativeTo(new CCalendar(false), use24, inLang);
	}

	/**
	 * Gets the time formated relatively to now: ex : if the 2 calendars describe the same day it will say Today ....
	 * 
	 * @param inCalendar
	 * @param use24
	 * @param inLang
	 * @return
	 */
	private String getTimeFormatedRelativeTo(CCalendar inCalendar, boolean use24, Lang inLang) {
		String date = net.violet.common.StringShop.EMPTY_STRING;
		final long diffDays = inCalendar.compareTo(this);

		if (diffDays == 0) {
			date = DicoTools.dico(inLang, "profile/today");
		} else if (diffDays == 1) {
			date = DicoTools.dico(inLang, "profile/yesterday");
		} else if (SiteLangData.DEFAULT_SITE_LANGUAGE_ISOCODE.equals(inLang.getIsoCode())) {
			date = this.getDateFormattedUSDico(inLang);
		} else if (SiteLangData.JAPAN_SITE_LANGUAGE_ISOCODE.equals(inLang.getIsoCode())) {
			date = this.getDateFormattedJPDico(inLang);
		} else {
			date = this.getDateFormattedDico(inLang);
		}

		return date + net.violet.common.StringShop.SPACE + this.getTimeFormated(use24, true);
	}

	/**
	 * Returns the current time in Seconds
	 * 
	 * @return
	 */
	public static int getCurrentTimeInSecond() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * Add the given amount of milliseconds to this {@link CCalendar}
	 * 
	 * @param inMillis
	 * @return the modified calendar
	 */
	public CCalendar addMillis(long inMillis) {
		add(Calendar.MILLISECOND, (int) (inMillis % 1000));
		add(Calendar.SECOND, (int) (inMillis / 1000));
		return this;
	}

	/**
	 * Add the given amount of seconds to this {@link CCalendar}
	 * 
	 * @param inMillis
	 * @return the modified calendar
	 */
	public CCalendar addSeconds(int inSeconds) {
		add(Calendar.SECOND, inSeconds);
		return this;
	}

	/**
	 * Create a calendar set on the current time plus a given offset.
	 * 
	 * @param inMillisecOffset offset in milliseconds.
	 */
	public static CCalendar getTimeInFuture(long inMillisecOffset) {
		return CCalendar.getTimeInFuture(System.currentTimeMillis(), inMillisecOffset);
	}

	public static CCalendar getTimeInFuture(long nowTimeStamp, long inMillisecOffset) {
		return CCalendar.getTimeInFuture(new CCalendar(nowTimeStamp), inMillisecOffset);
	}

	public static CCalendar getTimeInFuture(CCalendar inRefTime, long inMillisecOffset) {
		final CCalendar theCalendar = (CCalendar) inRefTime.clone();
		theCalendar.addMillis(inMillisecOffset);
		return theCalendar;
	}

	/**
	 * Create a calendar set on the current time plus a given offset.
	 * 
	 * @param inMillisecOffset offset in milliseconds.
	 */
	public static CCalendar getTimeInFuture(TimeZone inTimeZone, long inMillisecOffset) {
		final CCalendar theResult = new CCalendar(inTimeZone);
		theResult.addMillis(inMillisecOffset);
		return theResult;
	}

	/**
	 * Returns a String corresponding to this date formated.
	 * 
	 * @param isJS for the JS is true, for the message otherwise
	 * @return
	 */
	public String getMaintenanceDateFormated(boolean isJS) {
		return formatForTZ((isJS) ? CCalendar.MAINTENANCE_FORMAT_JS : CCalendar.MAINTENANCE_FORMAT_MESSAGE);
	}

	/**
	 * Try to parse the provided string into a date. If the string cannot be parsed, return <code>null</code>. Uses the UTC timezone. Correct dateTime
	 * format : yyy-MM-ddThh:mm:ssZ
	 * 
	 * @param inDateTime
	 * @return the parsed date or null.
	 */
	public static CCalendar parseISODate(String inDateTime) {
		try {
			return CCalendar.parseDateForTZ(CCalendar.DATE_TIME_ISO, CCalendar.UNIVERSAL_TIME, inDateTime);
		} catch (final ParseException e) {
			// This space for rent.
		}

		return null;
	}

	public String getUTCDateTimeFormated() {
		setTimeZone(CCalendar.UNIVERSAL_TIME);
		return formatForTZ(CCalendar.DATE_TIME_ISO);
	}

	/**
	 * Récupère une heure SQL au format HH:mm ou HH:mm:ss
	 * 
	 * @param inSQLFormattedTime l'heure (au format HH:mm ou HH:mm:ss)
	 * @return l'heure SQL
	 * @throws ParseException
	 * @throws ParseException si l'heure est dans aucun des deux formats.
	 */
	public static Time getSQLTime(String inSQLFormattedTime) throws ParseException {
		final CCalendar theResult = CCalendar.getSQLCCalendar(inSQLFormattedTime);
		return CCalendar.getSQLTime(theResult.getHour(), theResult.getMinute());
	}

	private static CCalendar getSQLCCalendar(String inSQLFormattedTime) throws ParseException {
		return CCalendar.getSQLCCalendar(inSQLFormattedTime, CCalendar.UNIVERSAL_TIME);
	}

	/**
	 * Parses formats : hh:mm(:ss)? (am/pm)?
	 * 
	 * @param inSQLFormattedTime
	 * @param inTimeZone
	 * @return the {@link CCalendar} set to the time and to now for the remaining fields
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static CCalendar getSQLCCalendar(String inSQLFormattedTime, TimeZone inTimeZone) throws ParseException {
		final CCalendar theResult = new CCalendar(true, inTimeZone);
		try {
			return CCalendar.parseTimeForTZ(CCalendar.HOUR_FORMAT24, inTimeZone, inSQLFormattedTime);
		} catch (final ParseException aParseException) {
			try {
				return CCalendar.parseTimeForTZ(CCalendar.HOUR_FORMAT12, inTimeZone, inSQLFormattedTime);
			} catch (final ParseException e) {
				theResult.setTimeFormatted(inSQLFormattedTime);
			}
		}
		return theResult;
	}

	public static Time getSQLTime(int inHour, int inMinute) {
		return new Time(inHour, inMinute, 0);
	}

}
