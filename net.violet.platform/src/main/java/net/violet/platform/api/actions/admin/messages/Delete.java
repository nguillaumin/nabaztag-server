package net.violet.platform.api.actions.admin.messages;

import java.util.Collections;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

public class Delete extends AbstractAction {

	public static int NB_ROWS = 1000;

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException, NoSuchPersonException {

		final UserData theUserData = getRequestedUser(inParam, null);
		final String objectId = inParam.getString("object_id", false);
		int nbRowsAffected = 0;

		if (objectId != null) {
			final VObjectData theObjectData = VObjectData.findByAPIId(objectId, inParam.getCallerAPIKey());
			if ((theObjectData == null) || !theObjectData.isValid()) {
				throw new NoSuchObjectException();
			}

			final Messenger theMessenger = Factories.MESSENGER.findByObject(theObjectData.getReference());
			final List<MessageReceived> theReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipient(theMessenger, 0, Delete.NB_ROWS);
			nbRowsAffected = theReceived.size();
			for (final MessageReceived aMessageReceived : theReceived) {
				aMessageReceived.delete();
			}
		} else {
			final Messenger theMessenger = Factories.MESSENGER.findByUser(theUserData.getReference());
			final List<MessageSent> theSent = Factories.MESSAGE_SENT.findMessageSentBySender(theMessenger, 0, Delete.NB_ROWS);
			nbRowsAffected = theSent.size();
			for (final MessageSent aMessageSent : theSent) {
				aMessageSent.delete();
			}
		}
		return new Integer(nbRowsAffected);
	}

	public long getExpirationTime() {
		return 0;
	}

	public boolean isCacheable() {
		return false;
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
		return Collections.emptyList();
	}

}
