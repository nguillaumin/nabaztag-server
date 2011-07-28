package net.violet.platform.datamodel.factories.mock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.factories.MessageSentFactory;
import net.violet.platform.datamodel.mock.MessageSentMock;

public class MessageSentFactoryMock extends RecordFactoryMock<MessageSent, MessageSentMock> implements MessageSentFactory {

	MessageSentFactoryMock() {
		super(MessageSentMock.class);
	}

	public MessageSent findMessageSentByMessageId(long inId) {
		for (final MessageSent theMessageSent : findAllMapped().values()) {
			if (theMessageSent.getMessage().getId().equals(inId)) {
				return theMessageSent;
			}
		}

		return null;
	}

	public List<MessageSent> findMessageSentBySenderOrRecipient(Messenger inMessenger) {
		if (inMessenger == null) {
			return Collections.emptyList();
		}
		final List<MessageSent> theResult = new LinkedList<MessageSent>();
		for (final MessageSent theMessageSent : findAllMapped().values()) {
			if (inMessenger.equals(theMessageSent.getSender()) || inMessenger.equals(theMessageSent.getRecipient())) {
				theResult.add(theMessageSent);
			}
		}
		return theResult;
	}

	public MessageSent create(Message inMessage, Messenger inReceiver, Messenger inSender) {
		return new MessageSentMock(0L, inMessage, inSender, inReceiver);
	}

	public List<MessageSent> findMessageSentBySender(Messenger inMessenger, int inIndex, int inGetCount) {
		final List<MessageSent> theResult = new LinkedList<MessageSent>();
		for (final MessageSent aMessageSent : findAll()) {
			if (aMessageSent.getRecipient().getId().equals(inMessenger.getId())) {
				theResult.add(aMessageSent);
			}
		}
		return theResult;
	}
}
