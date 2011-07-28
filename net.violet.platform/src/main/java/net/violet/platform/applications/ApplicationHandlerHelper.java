package net.violet.platform.applications;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.util.CCalendar;

public class ApplicationHandlerHelper {

	private static final Executor THREAD_POOL = Executors.newFixedThreadPool(20);

	public static class ExternalSettingToolBox {

		private static final Executor THREAD_INNER_POOL = Executors.newFixedThreadPool(20);

		public static List<Map<String, Object>> generateDailySchedulings(List<Map<List<DailyHandler.Weekday>, CCalendar>> inTimesList) throws InvalidParameterException {
			final List<Map<String, Object>> theSchedulings = new LinkedList<Map<String, Object>>();

			// if the list has elements
			if ((inTimesList != null) && !inTimesList.isEmpty()) {
				// We are going to be watching out for the list's content.
				final CountDownLatch theLatch = new CountDownLatch(inTimesList.size());

				// For each elements...
				for (final Map<List<DailyHandler.Weekday>, CCalendar> aTime : inTimesList) {
					ExternalSettingToolBox.THREAD_INNER_POOL.execute(new Runnable() {

						public void run() {
							try {
								// ... if it is not null or empty...
								if ((aTime != null) && !aTime.isEmpty()) {
									// ... we create the daily scheduling...
									final Map<String, Object> dailyScheduling = new HashMap<String, Object>();

									for (final Entry<List<DailyHandler.Weekday>, CCalendar> aTime2 : aTime.entrySet()) {
										dailyScheduling.putAll(ExternalSettingToolBox.buildDailyScheduling(aTime2.getKey(), aTime2.getValue()));
									}
									// ... and add it to the resulting list.
									synchronized (theSchedulings) {
										theSchedulings.add(dailyScheduling);
									}
								}
							} finally {
								theLatch.countDown();
							}
						}
					});
				}

				try {
					// Wait for every threads to be done running.
					theLatch.await();
				} catch (final InterruptedException e) {}
				return theSchedulings;
			}
			throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "time list");
		}

		public static Map<String, Object> buildDailyScheduling(List<DailyHandler.Weekday> daysList, CCalendar inTime) {
			return ExternalSettingToolBox.buildDailyBasedScheduling(SchedulingType.SCHEDULING_TYPE.Daily, inTime, daysList, null, null);
		}

		public static Map<String, Object> buildDailyWithMediaScheduling(CCalendar inTime, List<DailyHandler.Weekday> daysList, String mediaId) {
			return ExternalSettingToolBox.buildDailyBasedScheduling(SchedulingType.SCHEDULING_TYPE.DailyWithMedia, inTime, daysList, mediaId, null);
		}

		public static Map<? extends String, ? extends Object> buildDailyWithDurationScheduling(CCalendar inTime, List<DailyHandler.Weekday> daysList, int duration) {
			return ExternalSettingToolBox.buildDailyBasedScheduling(SchedulingType.SCHEDULING_TYPE.DailyWithDuration, inTime, daysList, null, duration);
		}

		private static Map<String, Object> buildDailyBasedScheduling(SchedulingType.SCHEDULING_TYPE inType, CCalendar inTime, List<DailyHandler.Weekday> daysList, String inMediaId, Integer inDuration) {
			final Map<String, Object> dailyScheduling = new HashMap<String, Object>();
			dailyScheduling.put("type", inType.getLabel());
			for (final DailyHandler.Weekday aDay : daysList) {
				final Map<String, Object> atom = new HashMap<String, Object>();
				atom.put("time_h", inTime.getHour());
				atom.put("time_m", inTime.getMinute());
				if (inMediaId != null) {
					atom.put("media", inMediaId);
				} else if (inDuration != null) {
					atom.put("duration", inDuration);
				}

				dailyScheduling.put(aDay.getValue(), atom);
			}

			return dailyScheduling;

		}
	}

	/**
	 * Creates a {@link List} of Daily scheduling settings given a {@link List}
	 * of times (hh:mm(:ss)) a {@link TimeZone}
	 * 
	 * @param inTimesList the {@link List} of times
	 * @param inTimeZone the {@link TimeZone}
	 * @param isScheduledAllWeek says whether or not these settings are for the
	 *            whole week
	 * @return the {@link List} schedulings or a (modifiable) empty {@link List}
	 *         if none of the times were a fit
	 * @throws InvalidParameterException if any of the given times could not be
	 *             parsed
	 */

	public static List<Map<String, Object>> generateDailySchedulings(List<String> inTimesList, final TimeZone inTimeZone, final boolean isScheduledAllWeek) throws InvalidParameterException {
		final List<Map<List<DailyHandler.Weekday>, CCalendar>> theList = new LinkedList<Map<List<DailyHandler.Weekday>, CCalendar>>();

		// if the list has elements
		if ((inTimesList != null) && !inTimesList.isEmpty()) {
			final AtomicBoolean isValid = new AtomicBoolean(true);
			// We are going to be watching out for the list's content.
			final CountDownLatch theLatch = new CountDownLatch(inTimesList.size());

			// For each elements...
			for (final String aTime : inTimesList) {

				if (isValid.get()) {
					ApplicationHandlerHelper.THREAD_POOL.execute(new Runnable() {

						public void run() {
							try {
								final Map<List<DailyHandler.Weekday>, CCalendar> theMap = new HashMap<List<DailyHandler.Weekday>, CCalendar>();

								if ((aTime != null) && !aTime.equals(net.violet.common.StringShop.EMPTY_STRING) && isValid.get()) {
									final CCalendar theTime = CCalendar.getSQLCCalendar(aTime, inTimeZone);

									if (isScheduledAllWeek) {
										theMap.put(DailyHandler.Weekday.getAllDays(), theTime);
									} else {
										theMap.put(DailyHandler.Weekday.getWorkweekDays(), theTime);
									}

									synchronized (theList) {
										theList.add(theMap);
									}
								}
							} catch (final ParseException e) {
								isValid.set(false);
							} finally {
								theLatch.countDown();
							}
						}
					});
				} else {
					theLatch.countDown();
				}
			}
			try {
				// Wait for every threads to be done running.
				theLatch.await();
			} catch (final InterruptedException e) {
				isValid.set(false);
			}
			if (isValid.get()) {
				if (!theList.isEmpty()) {
					return ExternalSettingToolBox.generateDailySchedulings(theList);
				}

				return Collections.emptyList();
			}

		}
		throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "time");
	}
}
