package net.violet.platform.api.actions.contacts;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.maps.ContactInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ContactData;
import net.violet.platform.dataobjects.UserData;

public class GetSentContactRequests extends AbstractContactAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException {

		final UserData theSessionUser = SessionManager.getUserFromSessionParam(inParam);

		final int skip = inParam.getInt("skip", 0);
		final int count = inParam.getInt("count", 10);

		final List<ContactInformationMap> resultList = new LinkedList<ContactInformationMap>();
		for (final ContactData theContactData : ContactData.getSentContactRequest(theSessionUser, skip, count)) {
			resultList.add(new ContactInformationMap(inParam.getCaller(), theContactData));
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
		return Application.CLASSES_UI;
	}
}
