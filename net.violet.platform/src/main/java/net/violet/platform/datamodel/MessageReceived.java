package net.violet.platform.datamodel;

import net.violet.db.records.associations.AssoRecord;
import net.violet.platform.datamodel.factories.Factories;

public interface MessageReceived extends AssoRecord<Message, MessageReceived> {

	/**
	 * Gets the Recipient of the message
	 * 
	 * @return
	 */
	Messenger getRecipient();

	/**
	 * Gets the sender of the message
	 * 
	 * @return
	 */
	Messenger getSender();

	/**
	 * Gets the message
	 * 
	 * @return
	 */
	Message getMessage();

	/**
	 * @return the message_state
	 */
	MessageReceived.MESSAGE_RECEIVED_STATES getMessageState();

	/**
	 * @param message_state the message_state to set
	 */
	void setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES message_state);

	class CommonMessageReceived {

		public static void resetCount(Messenger inMessenger) {
			final MessageCounter theMessageCounter = Factories.MESSAGE_COUNTER.get(inMessenger.getId());
			theMessageCounter.resetCount();
		}

		public static void invalidateIncrement(Messenger inMessenger) {
			final MessageCounter theMessageCounter = Factories.MESSAGE_COUNTER.get(inMessenger.getId());
			theMessageCounter.invalidateIncrement();
		}

		public static void invalidateDecrement(Messenger inMessenger) {
			final MessageCounter theMessageCounter = Factories.MESSAGE_COUNTER.get(inMessenger.getId());
			theMessageCounter.invalidateDecrement();
		}
	}

	public static enum MESSAGE_RECEIVED_STATES {
		INBOX,
		ARCHIVED,
		PENDING
	}

}
