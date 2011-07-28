package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.MessageCounter;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.MessageCounterFactory;
import net.violet.platform.datamodel.mock.MessageCounterMock;

public class MessageCounterFactoryMock extends RecordFactoryMock<MessageCounter, MessageCounterMock> implements MessageCounterFactory {

	public MessageCounterFactoryMock() {
		super(MessageCounterMock.class);
	}

	public MessageCounter get(long id) {
		final MessageCounter theMessageCounter = find(id);

		if (theMessageCounter == null) {
			final Messenger theMessenger = Factories.MESSENGER.find(id);
			final int count = (int) Factories.MESSAGE_RECEIVED.countReceivedMessagesByMessenger(theMessenger, MessageReceived.MESSAGE_RECEIVED_STATES.INBOX, true);

			final int state;
			if (count == 0) {
				state = 0;
			} else if (count > 1) {
				state = 2;
			} else {
				state = 1;
			}
			return new MessageCounterMock(id, state, count);
		}

		return theMessageCounter;
	}

	public Integer countAmountInboxMessagesByMessenger(Messenger inMessenger, boolean displayNabcast) {

		for (final MessageCounter aMessageCounter : findAll()) {
			if (aMessageCounter.getMessenger_id() == inMessenger.getId().longValue()) {
				return displayNabcast ? aMessageCounter.getW_nabcast() : aMessageCounter.getW_o_nabcast();
			}
		}

		return null;
	}

	public RABBIT_STATE getRabbitStateByRecipient(Messenger inRecipient) {
		if (inRecipient == null) {
			return null;
		}

		final MessageCounter theMessageCounter = get(inRecipient.getId());

		return theMessageCounter.getRabbit_state();
	}

}
