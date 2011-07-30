package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.associations.AssociationRecord;
import net.violet.db.records.associations.SQLAssociationSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class MessageSentImpl extends AssociationRecord<Message, MessageSent, MessageSentImpl> implements MessageSent {


	private static final Logger LOGGER = Logger.getLogger(MessageSentImpl.class);

	/**
	 * Spécification
	 */
	public static final SQLAssociationSpecification<MessageSentImpl> SPECIFICATION = new SQLAssociationSpecification<MessageSentImpl>("message_sent", MessageSentImpl.class, new SQLKey("message_id"));

	protected long message_id;
	protected long sender_id;
	protected long recipient_id;	
	protected String message_state;

	private final SingleAssociationNotNull<MessageSent, Messenger, MessengerImpl> recipient;
	private final SingleAssociationNotNull<MessageSent, Messenger, MessengerImpl> sender;
	private final SingleAssociationNotNull<MessageSent, Message, MessageImpl> message;

	protected MessageSentImpl() {
		this.recipient = new SingleAssociationNotNull<MessageSent, Messenger, MessengerImpl>(this, "recipient_id", MessengerImpl.SPECIFICATION);
		this.sender = new SingleAssociationNotNull<MessageSent, Messenger, MessengerImpl>(this, "sender_id", MessengerImpl.SPECIFICATION);
		this.message = new SingleAssociationNotNull<MessageSent, Message, MessageImpl>(this, "message_id", MessageImpl.SPECIFICATION);
	}

	public MessageSentImpl(Message inMessage, Messenger inRecipient, Messenger inSender) throws IllegalArgumentException {
		this((Long) AbstractSQLRecord.getObjectId((MessageImpl) inMessage), (Long) AbstractSQLRecord.getObjectId((MessengerImpl) inRecipient), (Long) AbstractSQLRecord.getObjectId((MessengerImpl) inSender));
	}

	protected MessageSentImpl(long inMessageid, long inRecipientId, long inSenderId) {
		this.message_id = inMessageid;
		this.recipient_id = inRecipientId;
		this.sender_id = inSenderId;
		this.message_state = "SENT";

		this.recipient = new SingleAssociationNotNull<MessageSent, Messenger, MessengerImpl>(this, "recipient_id", MessengerImpl.SPECIFICATION);
		this.sender = new SingleAssociationNotNull<MessageSent, Messenger, MessengerImpl>(this, "sender_id", MessengerImpl.SPECIFICATION);
		this.message = new SingleAssociationNotNull<MessageSent, Message, MessageImpl>(this, "message_id", MessageImpl.SPECIFICATION);
	}

	/**
	 * The amount of received messaged in the given state
	 * 
	 * @param inMessenger
	 * @param messageState
	 * @param displayNabcast
	 * @return the amount of received messaged in the given state, 0 otherwise
	 * @throws SQLException
	 */
	public static long countSentMessages(Messenger inMessenger, boolean displayNabcast) throws SQLException {
		if (inMessenger == null) {
			return 0;
		}

		final List<Object> theValues = Arrays.asList(new Object[] { inMessenger.getId() });
		String condition = " sender_id = ? ";

		if (!displayNabcast) {
			condition += " AND nabcast is NULL";
		}
		return AbstractSQLRecord.count(MessageSentImpl.SPECIFICATION, null, condition, theValues, null);
	}

	/**
	 * Return the amount of occurances of the given {@link MessageImpl} in
	 * {@link MessageReceivedImpl}
	 * 
	 * @param inMessage
	 * @return
	 */
	public static long countByMessage(MessageImpl inMessage) {
		try {
			return AbstractSQLRecord.count(MessageSentImpl.SPECIFICATION, null, "message_id = ?", Collections.singletonList((Object) inMessage.getId()), null);
		} catch (final SQLException e) {
			MessageSentImpl.LOGGER.fatal(e, e);
		}

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageSent#getRecipient()
	 */
	public Messenger getRecipient() {
		if (this.recipient != null) {
			return this.recipient.get(this.recipient_id);
		}
		return Factories.MESSENGER.getByObjectId(this.recipient_id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageSent#getSender()
	 */
	public Messenger getSender() {
		if (this.sender != null) {
			return this.sender.get(this.sender_id);
		}
		return Factories.MESSENGER.getByUserId(this.sender_id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageSent#getMessage()
	 */
	public Message getMessage() {
		if (this.message != null) {
			return this.message.get(this.message_id);
		}
		return Factories.MESSAGE.find(this.message_id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageSent#getSpecification()
	 */
	@Override
	public SQLAssociationSpecification<MessageSentImpl> getSpecification() {
		return MessageSentImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageSent#getMessage_id()
	 */
	public long getMessage_id() {
		return this.message_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageSent#getRecipient_id()
	 */
	public long getRecipient_id() {
		return this.recipient_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageSent#getSender_id()
	 */
	public long getSender_id() {
		return this.sender_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageSent#getMessage_state()
	 */
	public String getMessage_state() {
		return this.message_state;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
