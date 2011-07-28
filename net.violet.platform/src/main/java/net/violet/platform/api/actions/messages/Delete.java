package net.violet.platform.api.actions.messages;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchMailBoxException;
import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.MessageData.StatusMessage;
import net.violet.platform.util.Constantes;

public class Delete extends AbstractMessageAction {

	static final String STATUS_PARAM = "status";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidParameterException, NoSuchMailBoxException, InvalidSessionException, NoSuchMessageException {

		final UserData userData = SessionManager.getUserFromSessionParam(inParam);
		final StatusMessage theStatusMessage = StatusMessage.getStatusByName(inParam.getString("status", true));

		if (theStatusMessage == null) {
			throw new NoSuchMailBoxException(inParam.getString(Delete.STATUS_PARAM, true));
		}

		final MessageData theMessage = getRequestedMessage(inParam, theStatusMessage);
		final StatusMessage theMessageStatus = theMessage.getStatusMessage();

		// check si c'est bien un message recu de l'utilisateur ou un message envoyé par lui
		if ((userData.equals(theMessage.getRecipient().getOwner()) && ((theMessageStatus == StatusMessage.INBOX) || (theMessageStatus == StatusMessage.ARCHIVED))) || ((userData.equals(theMessage.getSender()) && (theMessageStatus == StatusMessage.SENT)))) {
			MessageData.deleteMessage(theMessage, userData);
		} else {
			throw new ForbiddenException();
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
