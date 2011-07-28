package net.violet.platform.api.maps.admin;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.api.maps.persons.PersonInformationMap;
import net.violet.platform.api.maps.persons.PersonPreferencesMap;
import net.violet.platform.api.maps.persons.PersonProfileMap;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

public class AdminAccountInformationMap extends AbstractAPIMap {

	public static String USER_INFORMATION = "user_information";
	public static String USER_PREFERENCES = "user_preferences";
	public static String USER_PROFILE = "user_profile";
	public static String USER_OBJECTS = "user_objects";
	public static String USER_PASSWORD = "user_password";

	public AdminAccountInformationMap(APICaller inCaller, UserData inUser, List<VObjectData> inObjects) {
		super();

		final PersonInformationMap informationMap = new PersonInformationMap(inCaller, inUser, true);
		// TODO : Password is filled here. Another idea ?
		informationMap.put(AdminAccountInformationMap.USER_PASSWORD, inUser.getPassword());
		final PersonPreferencesMap preferencesMap = new PersonPreferencesMap(inUser);
		final PersonProfileMap profileMap = new PersonProfileMap(inUser);
		final List<AdminObjectInformationMap> userObjects = new LinkedList<AdminObjectInformationMap>();
		for (final VObjectData anObjectData : inObjects) {
			userObjects.add(new AdminObjectInformationMap(inCaller, anObjectData));
		}

		put(AdminAccountInformationMap.USER_INFORMATION, informationMap);
		put(AdminAccountInformationMap.USER_PREFERENCES, preferencesMap);
		put(AdminAccountInformationMap.USER_PROFILE, profileMap);
		put(AdminAccountInformationMap.USER_OBJECTS, userObjects);
	}
}
