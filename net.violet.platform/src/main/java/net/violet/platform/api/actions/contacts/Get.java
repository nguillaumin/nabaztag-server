package net.violet.platform.api.actions.contacts;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.maps.persons.PersonInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ContactData;
import net.violet.platform.dataobjects.UserData;

public class Get extends AbstractContactAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException {

		final UserData theUser = getRequestedUser(inParam, null);

		final int skip = inParam.getInt("skip", 0);
		final int count = inParam.getInt("count", 10);

		final List<PersonInformationMap> resultList = new LinkedList<PersonInformationMap>();
		for (final ContactData theContactData : ContactData.getContacts(theUser, skip, count)) {
			resultList.add(new PersonInformationMap(inParam.getCaller(), theContactData.getContact(), false));
		}
		return resultList;
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return false;
	}

	/**
	 * User informations may be cached one day
	 * 
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return 0;
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.GET;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_ALL;
	}
}
