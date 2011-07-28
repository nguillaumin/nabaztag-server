package net.violet.platform.api.actions.contacts;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidContactException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchContactException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ContactData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Constantes;

public class RejectRequest extends AbstractContactAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchContactException, InvalidContactException {

		final UserData theSessionUser = SessionManager.getUserFromSessionParam(inParam);

		final ContactData theContact = getRequestedContact(inParam);

		// Check si l'utilisateur en session est bien celui qui a recu la
		// demande d'ami
		if (theSessionUser.equals(theContact.getContact())) {
			boolean result = false;
			if (theContact.getStatus().equalsIgnoreCase(Contact.STATUS.PENDING.toString())) {
				theContact.delete();
				result = true;
			}

			return result;
		}

		throw new InvalidContactException();
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
		return ActionType.UPDATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
