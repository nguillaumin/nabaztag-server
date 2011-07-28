package net.violet.platform.api.actions.contacts;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.ContactAlreadyExistsException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.maps.ContactInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ContactData;
import net.violet.platform.dataobjects.FriendsListsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.Templates;

public class Add extends AbstractContactAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchPersonException, ContactAlreadyExistsException {

		final UserData theSessionUser = SessionManager.getUserFromSessionParam(inParam);

		final UserData theContact = getRequestedUser(inParam, null);

		return Add.addContact(theSessionUser, theContact, inParam.getCaller());
	}

	/**
	 * Ajoute dans les contacts en vérifiant les préférences du contact
	 * 
	 * @param inUser : l'utilisateur qui demande a etre ami
	 * @param inContact : le contact
	 * @param inApplication : application concerné
	 * @return null si il est ajouté automatiquement ou sinon
	 *         ContactInformationMap en cas d'attente de réponse du contact
	 * @throws ContactAlreadyExistsException
	 */
	protected static Object addContact(UserData inUser, UserData inContact, APICaller inCaller) throws ContactAlreadyExistsException {
		ContactInformationMap theResult = null;

		if (ContactData.getContactByUserAndContact(inUser, inContact).isValid()) {
			throw new ContactAlreadyExistsException();
		}

		final FriendsListsData theFriendListData = FriendsListsData.findByUser(inContact);

		final long theConfirmation = theFriendListData.getFriendslists_confirmationlevel();

		// le contact veux pouvoir accepter la demande
		if ((theConfirmation == 3) || (theConfirmation == 1)) {
			final ContactData theResultData = ContactData.createContact(inUser, inContact, Contact.STATUS.PENDING);

			Templates.validateAddFriend(inUser.getReference(), inContact.getReference()); // mail de validation d 'ami

			theResult = new ContactInformationMap(inCaller, theResultData);
		}

		// le contact veux etre notifié par mail
		if ((theConfirmation == 3) || (theConfirmation == 2) || (theConfirmation == 0)) {
			if (theConfirmation != 3) {
				ContactData.createContact(inUser, inContact, Contact.STATUS.AUTOMATICALLY_ACCEPTED);
			}
			if (theConfirmation != 0) {
				Templates.notifyAddFriend(inUser.getReference(), inContact.getReference()); // mail de notification
			}
		}

		return theResult;
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
		return ActionType.CREATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
