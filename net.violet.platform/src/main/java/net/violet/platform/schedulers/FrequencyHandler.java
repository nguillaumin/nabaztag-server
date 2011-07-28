package net.violet.platform.schedulers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.CCalendar;

public class FrequencyHandler implements SchedulingHandler {

	public static enum Frequency {

		HOURLY("Hourly", 3600000L),

		RARELY("Rarely", 10800000L, 1),
		SOMETIMES("Sometimes", 7200000L, 3),
		OFTEN("Often", 3600000L, 6),
		VERY_OFTEN("Very often", 1200000L, 10);

		private final String label;
		private final String timeAsString;
		private final long time;
		private final Integer moodFrequency;

		Frequency(String label, long inTime) {
			this(label, inTime, null);
		}

		Frequency(String label, long inTime, Integer moodFrequency) {
			this.label = label;
			this.time = inTime;
			final CCalendar theCalendar = new CCalendar(true);
			theCalendar.addMillis(this.time);
			this.timeAsString = theCalendar.getTimeFormated(true);
			this.moodFrequency = moodFrequency;
		}

		private static final Map<String, Frequency> TIME_MAP;
		private static final Map<String, Frequency> LABEL_MAP;
		private static final Map<Integer, Frequency> MOOD_MAP;

		private static final List<Frequency> GLOBAL_FREQUENCY;
		private static final List<Frequency> HOURLY_FREQUENCY;

		static {
			final Map<String, Frequency> timeMap = new HashMap<String, Frequency>();
			final Map<String, Frequency> labelMap = new HashMap<String, Frequency>();
			final Map<Integer, Frequency> moodMap = new HashMap<Integer, Frequency>();
			final List<Frequency> globalFreq = new LinkedList<Frequency>();

			for (final Frequency aFrequency : Frequency.values()) {
				timeMap.put(aFrequency.getTimeAsString(), aFrequency);
				labelMap.put(aFrequency.getLabel(), aFrequency);
				if (aFrequency.getMoodFrequency() > 0) {
					moodMap.put(aFrequency.getMoodFrequency(), aFrequency);
					globalFreq.add(aFrequency);
				}
			}

			TIME_MAP = Collections.unmodifiableMap(timeMap);
			LABEL_MAP = Collections.unmodifiableMap(labelMap);
			MOOD_MAP = Collections.unmodifiableMap(moodMap);

			GLOBAL_FREQUENCY = Collections.unmodifiableList(globalFreq);
			HOURLY_FREQUENCY = Arrays.asList(HOURLY);
		}

		public static Frequency findByTime(String inTime) {
			return Frequency.TIME_MAP.get(inTime);
		}

		public static Frequency findByLabel(String inLabel) {
			return Frequency.LABEL_MAP.get(inLabel);
		}

		public final String getLabel() {
			return this.label;
		}

		public final int getMoodFrequency() {
			return (this.moodFrequency != null) ? this.moodFrequency.intValue() : -1;
		}

		public final String getTimeAsString() {
			return this.timeAsString;
		}

		public final long getTimeInMillis() {
			return this.time;
		}

		public final long getTimeInSecond() {
			return this.time / 1000L;
		}

		public static Frequency findByMoodFrequency(int moodFrequency) {
			return Frequency.MOOD_MAP.get(moodFrequency);
		}

		public static List<Frequency> getGlobalFrequencies() {
			return Frequency.GLOBAL_FREQUENCY;
		}

		public static List<Frequency> getHourlyFrequencies() {
			return Frequency.HOURLY_FREQUENCY;
		}

	}

	public static final String FREQUENCY = "Frequency";
	public static final String LAST_TIME = "Last_time";

	public void deleteElements(SubscriptionSchedulingData scheduling) {
		// nothing to do
	}

	public void executeWhenDone(SubscriptionSchedulingData scheduling) {
		// nothing to do right here
	}

	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		final Map<String, String> theResult = new HashMap<String, String>();
		theResult.put(FrequencyHandler.FREQUENCY, (String) settings.get("frequency"));

		if (settings.containsKey("last_time")) {
			theResult.put(FrequencyHandler.LAST_TIME, settings.get("last_time").toString());
		} else {
			final TimeZone theTimeZone = object.getReference().getTimeZone().getJavaTimeZone();
			theResult.put(FrequencyHandler.LAST_TIME, new CCalendar(false, theTimeZone).getTimestamp());
		}

		return theResult;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings, String callerKey) throws MissingSettingException, InvalidSettingException {
		final Object theFrequency = settings.get("frequency");
		if (theFrequency == null) {
			throw new MissingSettingException("frequency");
		}

		if (FrequencyHandler.Frequency.findByLabel(theFrequency.toString()) == null) {
			throw new InvalidSettingException("frequency", String.valueOf(theFrequency));
		}

	}

	public SchedulingInformationMap getSchedulingInformation(SubscriptionSchedulingData scheduling, APICaller caller) {
		final SchedulingInformationMap infoMap = new SchedulingInformationMap(scheduling);
		final SubscriptionSchedulingSettingsData theSettings = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, FrequencyHandler.FREQUENCY);
		infoMap.put("frequency", theSettings.getValue());
		return infoMap;
	}

}
