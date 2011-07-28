package net.violet.platform.schedulers;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;

public class DailyHandler implements SchedulingHandler {

	public static enum Weekday {
		SUNDAY("Sunday", Calendar.SUNDAY), MONDAY("Monday", Calendar.MONDAY), TUESDAY("Tuesday", Calendar.TUESDAY), WEDNESDAY("Wednesday", Calendar.WEDNESDAY), THURSDAY("Thursday", Calendar.THURSDAY), FRIDAY("Friday", Calendar.FRIDAY), SATURDAY("Saturday", Calendar.SATURDAY);

		private static final Map<String, Weekday> NAME_ALL_DAYS;

		static {
			final Map<String, Weekday> theMap = new HashMap<String, Weekday>();

			for (final Weekday aDay : Weekday.values()) {
				theMap.put(aDay.getValue(), aDay);
			}

			NAME_ALL_DAYS = Collections.unmodifiableMap(theMap);
		}

		private static final List<Weekday> WORKWEEK = Arrays.asList(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);
		private static final List<Weekday> WEEKENDDAYS = Arrays.asList(SATURDAY, SUNDAY);
		private static final List<Weekday> ALLDAYS = Arrays.asList(Weekday.values());
		private static final Weekday[] ALL_DAYS = { SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY };

		private final String label;
		private final int id;

		private Weekday(String inValue, int inId) {
			this.id = inId;
			this.label = inValue;
		}

		public static List<Weekday> getWorkweekDays() {
			return Weekday.WORKWEEK;
		}

		public static List<Weekday> getWeekEndDays() {
			return Weekday.WEEKENDDAYS;
		}

		public static List<Weekday> getAllDays() {
			return Weekday.ALLDAYS;
		}

		public String getValue() {
			return this.label;
		}

		@Override
		public String toString() {
			return getValue();
		}

		public int getCalendarId() {
			return this.id;
		}

		public int getBilanId() {
			return (this.id + 5) % 7;
		}

		public Weekday next() {
			final int theDay = (this.id + 1) % 8;

			return Weekday.findByCalendarId((theDay == 0) ? 1 : theDay);
		}

		public static Weekday findByCalendarId(int day) {
			return Weekday.ALL_DAYS[day - 1];
		}

		public static Weekday findByBilanId(int id) {
			return Weekday.ALL_DAYS[(id + 1) % 7];
		}

		public static boolean isValidLabel(String inLabel) {
			if (inLabel != null) {
				return Weekday.NAME_ALL_DAYS.containsKey(inLabel);
			}
			return false;
		}

	}

	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		final Map<String, String> theSettings = new HashMap<String, String>();

		for (final Entry<String, Object> anEntry : settings.entrySet()) {

			if (anEntry.getValue() != null) {
				final SchedulingAtom atom = new SchedulingAtom((Map<String, Object>) anEntry.getValue());
				theSettings.put(anEntry.getKey(), atom.getFormattedTime());
			}
		}

		return theSettings;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings, String callerKey) throws MissingSettingException, InvalidSettingException {
		if (settings.isEmpty()) {
			throw new MissingSettingException("scheduling");
		}

		for (final Entry<String, Object> anEntry : settings.entrySet()) {
			if (!DailyHandler.Weekday.isValidLabel(anEntry.getKey())) {
				throw new InvalidSettingException("day", anEntry.getKey());
			}

			final Object atom = anEntry.getValue();
			if ((atom != null) && !isAtomValid(atom)) {
				throw new InvalidSettingException("atom", String.valueOf(atom));
			}
		}

	}

	protected boolean isAtomValid(Object atom) {
		return SchedulingAtom.isValid(atom, false, false);
	}

	protected SchedulingAtom generateSchedulingAtom(Map<String, SubscriptionSchedulingSettingsData> settings, Weekday day) {
		final SubscriptionSchedulingSettingsData timeSetting = settings.get(day.getValue());
		return new SchedulingAtom(timeSetting.getValue(), null, null);
	}

	public void deleteElements(SubscriptionSchedulingData scheduling) {
		// nothing to do right here
	}

	public void executeWhenDone(SubscriptionSchedulingData scheduling) {
		// nothing to do right here
	}

	public SchedulingInformationMap getSchedulingInformation(SubscriptionSchedulingData scheduling, APICaller caller) {
		final SchedulingInformationMap dailyMap = new SchedulingInformationMap(scheduling);
		final Map<String, SubscriptionSchedulingSettingsData> settings = SubscriptionSchedulingSettingsData.findAllBySubscriptionSchedulingAsMap(scheduling);
		for (final Weekday aDay : Weekday.getAllDays()) {
			if (settings.containsKey(aDay.getValue())) {
				final SchedulingAtom atom = generateSchedulingAtom(settings, aDay);
				dailyMap.put(aDay.getValue(), atom.getSchedulingAtomMap(caller));
			}
		}

		return dailyMap;
	}

}
