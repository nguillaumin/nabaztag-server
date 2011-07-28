package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public final class TimezoneData extends RecordData<Timezone> {

	private static final Logger LOGGER = Logger.getLogger(TimezoneData.class);

	public static TimezoneData getData(Timezone inTimeZone) {
		try {
			return RecordData.getData(inTimeZone, TimezoneData.class, Timezone.class);
		} catch (final InstantiationException e) {
			TimezoneData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			TimezoneData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			TimezoneData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			TimezoneData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected TimezoneData(Timezone inTimeZone) {
		super(inTimeZone);
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		final Timezone theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}

		return 0;
	}

	/**
	 * @return the attibute name
	 */
	public String getName() {
		final Timezone theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getTimezone_name() != null)) {
			return theRecord.getTimezone_name().replace("LOC_", net.violet.common.StringShop.EMPTY_STRING);
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * find the Timezone
	 * 
	 * @param timezone
	 * @return a <code>TimezoneData</code> or <code>null</code> If
	 *         <code>null</code>, an <code>InvalidParameterException</code> must
	 *         be catched
	 */
	public static TimezoneData findByJavaId(String timezone) {
		final Timezone result = Factories.TIMEZONE.findByJavaId(timezone);
		if (result != null) {
			return TimezoneData.getData(result);
		}

		return null;
	}

	public String getJavaId() {
		final Timezone theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getTimezone_javaId();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static List<String> getAllTimezones() {
		return Factories.TIMEZONE.findAllNames();
	}

	/**
	 * @param inTimeZoneName
	 * @return the current time zone GMT offset (including Daylight Saving Time adjustments)
	 */
	public static Integer getCurrentOffset(final String inTimeZoneName) {
		final Calendar localCal = Calendar.getInstance(TimeZone.getTimeZone(inTimeZoneName));
		return (localCal.get(Calendar.ZONE_OFFSET) + localCal.get(Calendar.DST_OFFSET)) / 60000;
	}

	public static List<String> getByOffset(int rawOffset) {
		final List<String> result = Factories.TIMEZONE.findAllByOffset(rawOffset * 60000);
		return (result != null) ? result : Collections.<String> emptyList();
	}

	public static List<String> getByCountry(String codePays) {
		String codePays1;
		if (codePays.equals("BV")) {
			codePays1 = "NO";
		} else if (codePays.equals("AC") || codePays.equals("TA")) {
			codePays1 = "SH";
		} else if (codePays.equals("HM")) {
			codePays1 = "AU";
		} else {
			codePays1 = codePays;
		}

		return Arrays.asList(com.ibm.icu.util.TimeZone.getAvailableIDs(codePays1));
	}

}
