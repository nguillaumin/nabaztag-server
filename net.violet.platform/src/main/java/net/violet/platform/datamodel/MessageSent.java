package net.violet.platform.datamodel;

import net.violet.db.records.associations.AssoRecord;

public interface MessageSent extends AssoRecord<Message, MessageSent> {

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
	 * @return the message_id
	 */
	long getMessage_id();

	/**
	 * @return the recipient_id
	 */
	long getRecipient_id();

	/**
	 * @return the sender_id
	 */
	long getSender_id();

	/**
	 * @return the message_state
	 */
	String getMessage_state();

}
