package net.violet.platform.api.actions.messages;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.StatusMessage;

public class Archive extends AbstractMessageAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchMessageException {

		final UserData theSessionUser = SessionManager.getUserFromSessionParam(inParam);

		final List<MessageData> theMessages = new ArrayList<MessageData>();

		if (inParam.hasMainParam()) {
			theMessages.add(getRequestedMessage(inParam, StatusMessage.INBOX));
		}

		if (inParam.hasParam("ids")) {
			for (final Object anId : inParam.getList("ids", false)) {
				final MessageData aMessage = MessageData.findByAPIId(anId.toString(), inParam.getCaller().getAPIKey(), StatusMessage.INBOX);
				if (aMessage == null) {
					throw new NoSuchMessageException(APIErrorMessage.NO_SUCH_MESSAGE);
				}
				theMessages.add(aMessage);
			}
		}

		// check the validity of all the messages
		for (final MessageData aMessage : theMessages) {
			final VObjectData theObjectData = aMessage.getRecipient();
			if ((theObjectData != null) && theSessionUser.equals(theObjectData.getOwner())) {
				if (StatusMessage.INBOX != aMessage.getStatusMessage()) {
					throw new InvalidParameterException(APIErrorMessage.MESSAGE_ALREADY_ARCHIVED, net.violet.common.StringShop.EMPTY_STRING);
				}
			} else {
				throw new ForbiddenException();
			}
		}

		for (int i = theMessages.size() - 1; i >= 0; i--) {
			MessageData.archiveMessage(theMessages.get(i), i == 0);
		}

		return null;
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
		return ActionType.UPDATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
