package net.violet.platform.api.maps.objects;

import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.api.maps.PerDaySleepTimeMap;
import net.violet.platform.dataobjects.VObjectData;

public class ObjectPreferencesMap extends AbstractAPIMap {

	public static final String VISIBLE = "visible";
	public static final String PRIVATE = "private";
	public static final String NOTIFY_RECEIVED = "notify_received";
	public static final String TIMEZONE = "timezone";
	public static final String SLEEP_TIMES = "sleep_times";
	public static final String LANGUAGE = "language";

	public ObjectPreferencesMap(VObjectData inObjectData) {
		super(5);

		put(ObjectPreferencesMap.VISIBLE, inObjectData.getPreferences().isVisible());
		put(ObjectPreferencesMap.PRIVATE, inObjectData.getPreferences().isPrivate());
		put(ObjectPreferencesMap.NOTIFY_RECEIVED, inObjectData.getOwner().notifyIfReceived());
		put(ObjectPreferencesMap.TIMEZONE, inObjectData.getTimeZone());
		put(ObjectPreferencesMap.SLEEP_TIMES, new PerDaySleepTimeMap(inObjectData));
		put(ObjectPreferencesMap.LANGUAGE, inObjectData.getPreferences().getLang().getLang_iso_code());

	}
}
