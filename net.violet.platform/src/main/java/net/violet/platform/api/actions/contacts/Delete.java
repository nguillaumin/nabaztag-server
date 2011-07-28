package net.violet.platform.api.actions.contacts;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ContactData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.StringShop;

public class Delete extends AbstractContactAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchPersonException {

		final UserData theSessionUser = SessionManager.getUserFromSessionParam(inParam);

		final UserData theContact = getRequestedUser(inParam, null);

		final ContactData theContactData = ContactData.getContactByUserAndContact(theSessionUser, theContact);

		if (!ContactData.deleteContact(theContactData)) {
			throw new InvalidParameterException(APIErrorMessage.NOT_IN_THE_CONTACT_LIST, StringShop.EMPTY_STRING);
		}

		return null;
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return true;
	}

	/**
	 * User informations may be cached one day
	 * 
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.DELETE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
