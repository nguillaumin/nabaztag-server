package net.violet.platform.schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.util.CCalendar;

public class SchedulingAtom {

	public static final String HOUR = "time_h";
	public static final String MINUTE = "time_m";
	public static final String DURATION = "duration";
	public static final String MEDIA = "media";

	private final int hour;
	private final int minute;
	private final Integer duration;
	private final String media;

	/**
	 * Returns true if the given object is a valid atom.
	 * @param atom the object to test
	 * @param withDuration true if the atom should contain duration information
	 * @param withMedia true if the atom should contain media information
	 * @return
	 */
	public static boolean isValid(Object atom, boolean withDuration, boolean withMedia) {
		if (!(atom instanceof Map)) {
			return false;
		}

		final Map<String, Object> theAtom = (Map<String, Object>) atom;
		final Object hourSetting = theAtom.get(SchedulingAtom.HOUR);
		final Object minuteSetting = theAtom.get(SchedulingAtom.MINUTE);

		if ((hourSetting == null) || (minuteSetting == null)) {
			return false;
		}

		try {
			final int hour = (Integer) hourSetting;
			final int minute = (Integer) minuteSetting;
			if ((hour < 0) || (hour > 23) || (minute < 0) || (minute > 59)) {
				return false;
			}

			if (withDuration && (!theAtom.containsKey(SchedulingAtom.DURATION) || ((Integer) theAtom.get(SchedulingAtom.DURATION) <= 0))) {
				return false;
			}
		} catch (final ClassCastException e) {
			return false;
		}

		if (withMedia && !theAtom.containsKey(SchedulingAtom.MEDIA)) {
			return false;
		}

		return true;
	}

	public SchedulingAtom(Map<String, Object> atomMap) {
		this((Integer) atomMap.get(SchedulingAtom.HOUR), (Integer) atomMap.get(SchedulingAtom.MINUTE), (Integer) atomMap.get(SchedulingAtom.DURATION), (String) atomMap.get(SchedulingAtom.MEDIA));
	}

	public SchedulingAtom(int hour, int minute, Integer duration, String media) {
		this.hour = hour;
		this.minute = minute;
		this.duration = duration;
		this.media = media;
	}

	public SchedulingAtom(String formattedTime, Integer duration, String media) {
		final CCalendar calendar = new CCalendar(formattedTime, TimeZone.getTimeZone("UTC"));
		this.hour = calendar.getHour();
		this.minute = calendar.getMinute();
		this.duration = duration;
		this.media = media;
	}

	/**
	 * Return true if the current atom is after the given atom object.
	 * @param atom
	 * @return
	 */
	public boolean isAfter(SchedulingAtom atom) {
		return (this.hour * 60 + this.minute) > (atom.hour * 60 + atom.minute);
	}

	public int getHour() {
		return this.hour;
	}

	public int getMinute() {
		return this.minute;
	}

	public Integer getDuration() {
		return this.duration;
	}

	public String getMedia() {
		return this.media;
	}

	public String getFormattedTime() {
		final CCalendar theCalendar = new CCalendar(true); //new CCalendar(true, TimeZone.getTimeZone("UTC"));
		theCalendar.setHour(this.hour);
		theCalendar.setMinute(this.minute);

		return theCalendar.getTimeFormated(true);
	}

	public Map<String, Object> getSchedulingAtomMap(APICaller caller) {
		final Map<String, Object> atomMap = new HashMap<String, Object>();
		atomMap.put(SchedulingAtom.HOUR, this.hour);
		atomMap.put(SchedulingAtom.MINUTE, this.minute);

		if (getDuration() != null) {
			atomMap.put(SchedulingAtom.DURATION, this.duration);
		}

		if (this.media != null) {
			final Music theMusic = Factories.MUSIC.find(Long.parseLong(this.media));
			if (theMusic != null) {
				atomMap.put(SchedulingAtom.MEDIA, MusicData.getData(theMusic).getApiId(caller));
			}
		}
		return atomMap;
	}

}
