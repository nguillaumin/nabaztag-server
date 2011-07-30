package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.Map;
import java.util.SortedMap;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.datamodel.factories.Factories;

/**
 * Users and objects are identified in a Messenger.
 *
 */
public interface Messenger extends Record<Messenger> {

	/**
	 * Return the VObjectImpl currently associated with this MessengerImpl.
	 * 
	 * @return the VObjectImpl or <code>null</code> if there are not associated
	 *         VObjects
	 */
	VObject getObject();

	/**
	 * Return the UserImpl currently associated with this MessengerImpl.
	 * 
	 * @return the UserImpl or <code>null</code> if there are not associated
	 *         UserImpl
	 */
	User getUser();

	/**
	 * @return the name
	 */
	String getName();

	// Special test
	void setName(String name);

	/**
	 * @return the messageReceived
	 * @throws SQLException
	 */
	Map<Message, MessageReceived> getMessageReceived();

	/**
	 * Retourne la liste des messages dans la inbox, par ordre de réception.
	 * 
	 * @throws SQLException
	 */
	SortedMap<Message, MessageReceived> getInboxMessagesSorted() throws SQLException;

	/**
	 * Finds all messages received by a MessengerImpl to a MessengerImpl
	 * 
	 * @param inSender
	 * @return
	 * @throws SQLException
	 */
	SortedMap<Message, MessageReceived> getMessageReceivedFromMessenger(Messenger inSender) throws SQLException;

	/**
	 * Finds all messages sent by a MessengerImpl to a MessengerImpl
	 * 
	 * @param inSender
	 * @return
	 * @throws SQLException
	 */
	SortedMap<Message, MessageSent> getMessageSentToMessenger(Messenger inSender) throws SQLException;

	/**
	 * @return the messageSent
	 * @throws SQLException
	 */
	Map<Message, MessageSent> getMessageSent();

	/**
	 * The amount of received messaged in the given state
	 * 
	 * @param messageState
	 * @param displayNabcast
	 * @return the amount of received messaged in the given state, 0 otherwise
	 * @throws SQLException
	 */
	long countReceivedMessagesByState(MessageReceived.MESSAGE_RECEIVED_STATES messageState, boolean displayNabcast);

	/**
	 * Compute the actual rabbit state, but do not compute the actual count of
	 * messages.
	 * 
	 * @return the amount of received messaged in the given state, 0 otherwise
	 */
	RABBIT_STATE computeRabbitState();

	/**
	 * delete all messages nabcast in future from inSender
	 * 
	 * @param inSender :
	 * @param inNabcastId : id du nabcast
	 * @throws SQLException
	 */
	void deleteMessageNabcastInFutureFromMessenger(Messenger inSender, Long inNabcastId) throws SQLException;

	class MessengerCommon {

		/**
		 * The amount of received messaged
		 * 
		 * @param inMessenger
		 * @param displayNabcast
		 * @return the amount of received messaged in the given state, 0
		 *         otherwise
		 * @throws SQLException
		 */
		public long countReceivedMessagesByVObject(Messenger inMessenger, boolean displayNabcast) {

			if (inMessenger == null) {
				return 0;
			}

			final Integer amountOfMessages = Factories.MESSAGE_COUNTER.countAmountInboxMessagesByMessenger(inMessenger, displayNabcast);

			if (amountOfMessages != null) {
				return amountOfMessages;
			}

			// le nombre de message est invalide donc on recalcule
			final Integer theAmount = (int) Factories.MESSAGE_RECEIVED.countReceivedMessagesByMessenger(inMessenger, MessageReceived.MESSAGE_RECEIVED_STATES.INBOX, displayNabcast);

			final MessageCounter theMessageCounter = Factories.MESSAGE_COUNTER.get(inMessenger.getId());

			if (displayNabcast) {
				theMessageCounter.setW_nabcast(theAmount);
			} else {
				theMessageCounter.setW_o_nabcast(theAmount);
			}

			return theAmount;

		}

		/**
		 * The amount of archived messaged This method is called in website,
		 * when the user is in message archived
		 * 
		 * @param inMessenger
		 * @param displayNabcast
		 * @return the amount of received messaged in the given state, 0
		 *         otherwise
		 * @throws SQLException
		 */
		public long countArchivedMessagesByVObject(Messenger inMessenger, boolean displayNabcast) {

			if (inMessenger == null) {
				return 0;
			}

			final Integer theAmount = (int) Factories.MESSAGE_RECEIVED.countReceivedMessagesByMessenger(inMessenger, MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED, displayNabcast);

			return theAmount;

		}

		public RABBIT_STATE computeRabbitState(Messenger inMessenger) {
			final long theAmount = Factories.MESSAGE_RECEIVED.countReceivedMessagesByMessenger(inMessenger, MessageReceived.MESSAGE_RECEIVED_STATES.INBOX, true, 2);
			final RABBIT_STATE theResult = RABBIT_STATE.getState((int) theAmount);
			return theResult;

		}
	}

}
