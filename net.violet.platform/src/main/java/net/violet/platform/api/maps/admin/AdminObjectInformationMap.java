package net.violet.platform.api.maps.admin;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.api.maps.objects.ObjectInformationMap;
import net.violet.platform.api.maps.objects.ObjectPreferencesMap;
import net.violet.platform.dataobjects.VObjectData;

public class AdminObjectInformationMap extends AbstractAPIMap {

	public static String OBJECT_INFORMATION = "object_information";
	public static String OBJECT_PREFERENCES = "object_preferences";

	public static String LAST_PING = "last_ping";
	public static String MODE = "mode";

	public AdminObjectInformationMap(APICaller inCaller, VObjectData inObject) {
		super(2);

		final ObjectInformationMap informationMap = new ObjectInformationMap(inCaller, inObject, true);
		// TODO: create another map for admin stuff if necessary
		informationMap.put(AdminObjectInformationMap.LAST_PING, inObject.getLastPing());
		informationMap.put(AdminObjectInformationMap.MODE, inObject.getMode());
		final ObjectPreferencesMap preferencesMap = new ObjectPreferencesMap(inObject);

		put(AdminObjectInformationMap.OBJECT_INFORMATION, informationMap);
		put(AdminObjectInformationMap.OBJECT_PREFERENCES, preferencesMap);
	}

}
