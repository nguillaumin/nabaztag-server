package net.violet.platform.api.maps.persons;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.UserData;

public class PersonInformationMap extends AbstractAPIMap {

	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String CREATION_DATE = "creation_date";

	public PersonInformationMap(APICaller inCaller, UserData inUser, boolean isPrivate) {
		super(3);

		put(PersonInformationMap.ID, inUser.getApiId(inCaller));
		if (isPrivate) {
			put(PersonInformationMap.EMAIL, inUser.getUser_email());
		}
		put(PersonInformationMap.CREATION_DATE, inUser.getCreationDate());

	}
}
