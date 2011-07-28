package net.violet.platform.api.maps;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.violet.platform.dataobjects.ObjectSleepData;
import net.violet.platform.dataobjects.VObjectData;

public class PerDaySleepTimeMap extends AbstractAPIMap {

	private static String[] daysNames = { "sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday" };

	public PerDaySleepTimeMap(VObjectData inObject) {
		super(7);

		for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
			final ObjectSleepData theSleepData = inObject.getSleepObject(i);
			if (theSleepData == null) {
				putNullValue(PerDaySleepTimeMap.daysNames[i - 1]);
			} else {
				put(PerDaySleepTimeMap.daysNames[i - 1], new SleepTimeMap(theSleepData));
			}
		}
	}

	private static class SleepTimeMap extends AbstractAPIMap {

		public SleepTimeMap(ObjectSleepData inObjectSleepData) {
			super(4);

			put("wakeup_time_h", getTime(inObjectSleepData, false, true));
			put("wakeup_time_m", getTime(inObjectSleepData, false, false));
			put("sleep_time_h", getTime(inObjectSleepData, true, true));
			put("sleep_time_m", getTime(inObjectSleepData, true, false));

		}

		private Integer getTime(ObjectSleepData inObjectSleepData, boolean sleep, boolean hours) {
			final GregorianCalendar gc = new GregorianCalendar();

			if (sleep) {
				gc.setTime(inObjectSleepData.getSleepTime());
			} else {
				gc.setTime(inObjectSleepData.getWakeUpTime());
			}

			if (hours) {
				return gc.get(Calendar.HOUR_OF_DAY); // 0 à 23
			}
			return gc.get(Calendar.MINUTE); // 0 à 59
		}
	}
}
