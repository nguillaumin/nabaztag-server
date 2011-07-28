package net.violet.platform.datamodel.factories;

import java.sql.SQLException;
import java.util.List;

import net.violet.db.records.associations.AssociationRecord;
import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Messenger;

public interface MessageReceivedFactory extends RecordFactory<MessageReceived> {

	/**
	 * The amount of received messaged in the given state
	 * 
	 * @param inMessenger
	 * @param messageState
	 * @param displayNabcast
	 * @return the amount of received messaged in the given state, 0 otherwise
	 * @throws SQLException
	 */
	long countReceivedMessagesByMessenger(Messenger inMessenger, MessageReceived.MESSAGE_RECEIVED_STATES messageState, boolean displayNabcast);

	/**
	 * The amount of received messaged in the given state, with a limit.
	 * 
	 * @param inMessenger
	 * @param messageState
	 * @param displayNabcast
	 * @param inLimit maximum amount of messages scanned.
	 * @return the amount of received messaged in the given state, 0 otherwise
	 * @throws SQLException
	 */
	long countReceivedMessagesByMessenger(Messenger inMessenger, MessageReceived.MESSAGE_RECEIVED_STATES messageState, boolean displayNabcast, int inLimit);

	/**
	 * Finds MessageReceived by Message id
	 * 
	 * @param inId
	 * @return
	 */
	MessageReceived findMessageReceivedByMessageId(long inId);

	/**
	 * Creates a new {@link MessageReceived}
	 * 
	 * BEWARE: does not insert anything if not called in an {@link AssociationRecord} context
	 * 
	 * @param inMessage
	 * @param inReceiver
	 * @param inSender
	 * 
	 * @return the created object
	 */
	MessageReceived create(Message inMessage, Messenger inReceiver, Messenger inSender);

	List<MessageReceived> findMessageReceivedByRecipientOrSender(Messenger inMessenger);

	List<MessageReceived> findMessageReceivedByRecipient(Messenger inMessenger, int inIndex, int inGetCount);

}
