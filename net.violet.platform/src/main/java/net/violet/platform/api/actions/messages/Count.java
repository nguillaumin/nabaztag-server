package net.violet.platform.api.actions.messages;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.StatusMessage;

public class Count extends AbstractMessageAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException, NoSuchObjectException {

		final StatusMessage msgStatus = StatusMessage.getStatusByName(inParam.getMainParamAsString());
		final String theObjectID = inParam.getString("object", null);
		final UserData userData = SessionManager.getUserFromSessionParam(inParam);

		int msgCount = 0;

		VObjectData objectData = VObjectData.getData(null);

		if (theObjectID != null) {
			objectData = VObjectData.findByAPIId(theObjectID, inParam.getCallerAPIKey());
			if ((objectData == null) || !objectData.isValid()) {
				throw new NoSuchObjectException();
			}
		}

		// message recu ou archivé
		if ((msgStatus == StatusMessage.ARCHIVED) || (msgStatus == StatusMessage.INBOX)) {
			msgCount = (int) MessageData.countMessageReceivedOrArchivedByStatus(msgStatus, userData, objectData);

		} else if (msgStatus == StatusMessage.SENT) {
			msgCount = (int) MessageData.countMessageSent(userData);
		}
		return msgCount;
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
