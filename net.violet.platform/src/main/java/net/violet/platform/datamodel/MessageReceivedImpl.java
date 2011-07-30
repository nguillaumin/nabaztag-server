package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.associations.AssociationRecord;
import net.violet.db.records.associations.SQLAssociationSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class MessageReceivedImpl extends AssociationRecord<Message, MessageReceived, MessageReceivedImpl> implements MessageReceived {


	private static final Logger LOGGER = Logger.getLogger(MessageReceivedImpl.class);

	/**
	 * Spécification
	 */
	public static final SQLAssociationSpecification<MessageReceivedImpl> SPECIFICATION = new SQLAssociationSpecification<MessageReceivedImpl>("message_received", MessageReceivedImpl.class, new SQLKey("message_id"));

	protected long message_id;
	protected long sender_id;
	protected long recipient_id;
	protected String message_state;

	private final SingleAssociationNotNull<MessageReceived, Messenger, MessengerImpl> recipient;
	private final SingleAssociationNotNull<MessageReceived, Messenger, MessengerImpl> sender;
	private final SingleAssociationNotNull<MessageReceived, Message, MessageImpl> message;

	protected MessageReceivedImpl() {
		this.recipient = new SingleAssociationNotNull<MessageReceived, Messenger, MessengerImpl>(this, "recipient_id", MessengerImpl.SPECIFICATION);
		this.sender = new SingleAssociationNotNull<MessageReceived, Messenger, MessengerImpl>(this, "sender_id", MessengerImpl.SPECIFICATION);
		this.message = new SingleAssociationNotNull<MessageReceived, Message, MessageImpl>(this, "message_id", MessageImpl.SPECIFICATION);
	}

	public MessageReceivedImpl(Message inMessage, Messenger inRecipient, Messenger inSender) throws IllegalArgumentException {
		this((Long) AbstractSQLRecord.getObjectId((MessageImpl) inMessage), (Long) AbstractSQLRecord.getObjectId((MessengerImpl) inRecipient), (Long) AbstractSQLRecord.getObjectId((MessengerImpl) inSender));
	}

	protected MessageReceivedImpl(long inMessageid, long inRecipientId, long inSenderId) {
		this.message_id = inMessageid;
		this.recipient_id = inRecipientId;
		this.sender_id = inSenderId;
		this.message_state = MessageReceived.MESSAGE_RECEIVED_STATES.INBOX.toString();
		this.recipient = new SingleAssociationNotNull<MessageReceived, Messenger, MessengerImpl>(this, "recipient_id", MessengerImpl.SPECIFICATION);
		this.sender = new SingleAssociationNotNull<MessageReceived, Messenger, MessengerImpl>(this, "sender_id", MessengerImpl.SPECIFICATION);
		this.message = new SingleAssociationNotNull<MessageReceived, Message, MessageImpl>(this, "message_id", MessageImpl.SPECIFICATION);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageReceived#getSpecification()
	 */
	@Override
	public SQLAssociationSpecification<MessageReceivedImpl> getSpecification() {
		return MessageReceivedImpl.SPECIFICATION;
	}

	/**
	 * Sends all the messages from the INBOX state to the ARCHIVED state
	 * 
	 * @param inVObject
	 * @return
	 */
	public static void ackAll(Messenger inRecipient) {
		final List<MessageReceived> theMessages2Ack = new ArrayList<MessageReceived>();
		try {
			theMessages2Ack.addAll(AbstractSQLRecord.findAll(MessageReceivedImpl.SPECIFICATION, "recipient_id = ? AND message_state = 'INBOX'", Collections.singletonList((Object) inRecipient.getId())));
		} catch (final SQLException e) {
			MessageReceivedImpl.LOGGER.fatal(e, e);
		}

		for (final MessageReceived aMessage2Ack : theMessages2Ack) {
			aMessage2Ack.setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED);
			final Message theMessage = aMessage2Ack.getMessage();
			if (theMessage != null) {
				try {
					theMessage.deleteEvent();
				} catch (final SQLException e) {
					MessageReceivedImpl.LOGGER.fatal(e, e);
				}
			}
		}
	}

	/**
	 * Return the amount of occurances of the given {@link MessageImpl} in
	 * {@link MessageReceived}
	 * 
	 * @param inMessage
	 * @return
	 */
	public static long countByMessage(MessageImpl inMessage) {
		try {
			return AbstractSQLRecord.count(MessageReceivedImpl.SPECIFICATION, null, "message_id = ?", Collections.singletonList((Object) inMessage.getId()), null);
		} catch (final SQLException e) {
			MessageReceivedImpl.LOGGER.fatal(e, e);
		}

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageReceived#getRecipient()
	 */
	public Messenger getRecipient() {
		if (this.recipient != null) {
			return this.recipient.get(this.recipient_id);
		}
		return Factories.MESSENGER.getByObjectId(this.recipient_id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageReceived#getSender()
	 */
	public Messenger getSender() {
		if (this.sender != null) {
			return this.sender.get(this.sender_id);
		}
		return Factories.MESSENGER.getByUserId(this.sender_id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageReceived#getMessage()
	 */
	public Message getMessage() {
		if (this.message != null) {
			return this.message.get(this.message_id);
		}
		return Factories.MESSAGE.find(this.message_id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageReceived#getMessageState()
	 */
	public MessageReceived.MESSAGE_RECEIVED_STATES getMessageState() {
		if (MessageReceived.MESSAGE_RECEIVED_STATES.INBOX.toString().equals(this.message_state)) {
			return MessageReceived.MESSAGE_RECEIVED_STATES.INBOX;
		} else if (MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED.toString().equals(this.message_state)) {
			return MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED;
		}

		return MessageReceived.MESSAGE_RECEIVED_STATES.PENDING;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.MessageReceived#setMessage_state(net.violet
	 * .platform.datamodel.MessageReceivedImpl.MESSAGE_RECEIVED_STATES)
	 */
	public void setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES message_state) {
		if (message_state != null) {
			final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
			setMessage_state(theUpdateMap, message_state.toString());
			update(theUpdateMap);
		}
	}

	/**
	 * @param message_state the message_state to set
	 */
	private void setMessage_state(Map<String, Object> inUpdateMap, String message_state) {
		if (!this.message_state.equals(message_state)) {
			synchronized (this) {

				this.message_state = message_state;
				inUpdateMap.put("message_state", message_state);
			}
		}
	}

	/**
	 * @return message_id
	 */
	public Long getId() {
		return this.message_id;
	}

}
