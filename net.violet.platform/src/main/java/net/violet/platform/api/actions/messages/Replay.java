package net.violet.platform.api.actions.messages;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.MessageData.StatusMessage;

public class Replay extends AbstractMessageAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchMessageException {

		final APICaller caller = inParam.getCaller();

		final UserData userData;

		if (caller.getApplicationClass().equals(Application.ApplicationClass.NATIVE)) {
			try {
				userData = getRequestedUser(inParam, "userId");
			} catch (final NoSuchPersonException e) {
				throw new InvalidSessionException();
			}

		} else {
			userData = SessionManager.getUserFromSessionParam(inParam);
		}

		final MessageData msgData = getRequestedMessage(inParam, StatusMessage.INBOX);

		// check si c'est bien un message recu de l'utilisateur
		if ((msgData.getRecipient() != null) && userData.equals(msgData.getRecipient().getOwner())) {
			MessageData.replayMessage(msgData);

		} else {
			throw new ForbiddenException();
		}
		return null;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
