package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.MessageReceived.MESSAGE_RECEIVED_STATES;
import net.violet.platform.datamodel.factories.MessageReceivedFactory;
import net.violet.platform.datamodel.mock.MessageReceivedMock;

public class MessageReceivedFactoryMock extends RecordFactoryMock<MessageReceived, MessageReceivedMock> implements MessageReceivedFactory {

	MessageReceivedFactoryMock() {
		super(MessageReceivedMock.class);
	}

	public long countReceivedMessagesByMessenger(Messenger inMessenger, MessageReceived.MESSAGE_RECEIVED_STATES messageState, boolean displayNabcast) {
		return countReceivedMessagesByMessenger(inMessenger, messageState, displayNabcast, 0);
	}

	public long countReceivedMessagesByMessenger(Messenger inMessenger, MessageReceived.MESSAGE_RECEIVED_STATES messageState, boolean displayNabcast, int inLimit) {

		int count = 0;

		for (final Entry<Message, MessageReceived> anEntry : inMessenger.getMessageReceived().entrySet()) {
			if ((anEntry.getValue().getMessageState() == messageState) && (!displayNabcast || (anEntry.getKey().getNabcast() != null))) {
				count++;
				if (count == inLimit) {
					break;
				}
			}
		}

		return count;
	}

	public MessageReceived findMessageReceivedByMessageId(long inId) {
		for (final MessageReceived theMessageReceived : findAllMapped().values()) {
			if (theMessageReceived.getMessage().getId().equals(inId)) {
				return theMessageReceived;
			}
		}

		return null;
	}

	public List<MessageReceived> findMessageReceivedByRecipientOrSender(Messenger inMessenger) {
		final List<MessageReceived> theResult = new LinkedList<MessageReceived>();
		for (final MessageReceived theMessageReceived : findAllMapped().values()) {
			if (theMessageReceived.getRecipient().equals(inMessenger) || theMessageReceived.getSender().equals(inMessenger)) {
				theResult.add(theMessageReceived);
			}
		}
		return theResult;
	}

	public MessageReceived create(Message inMessage, Messenger inReceiver, Messenger inSender) {
		return new MessageReceivedMock(0, inMessage, inSender, inReceiver, MESSAGE_RECEIVED_STATES.INBOX);
	}

	public List<MessageReceived> findMessageReceivedByRecipient(Messenger inMessenger, int inIndex, int inGetCount) {
		final List<MessageReceived> theResult = new LinkedList<MessageReceived>();
		for (final MessageReceived aMessageReceived : findAll()) {
			if (aMessageReceived.getRecipient().getId().equals(inMessenger.getId())) {
				theResult.add(aMessageReceived);
			}
		}
		return theResult;
	}
}
