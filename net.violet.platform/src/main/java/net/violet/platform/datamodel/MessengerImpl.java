package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.DecoratedAssociation;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.ConvertTools;

import org.apache.log4j.Logger;

/**
 * Encapsule un utilisateur ou (exclusive) un object
 */
public class MessengerImpl extends ObjectRecord<Messenger, MessengerImpl> implements Messenger {


	private static final Logger LOGGER = Logger.getLogger(MessengerImpl.class);

	private static MessengerCommon MESSENGER_COMMON = new MessengerCommon();

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<MessengerImpl> SPECIFICATION = new SQLObjectSpecification<MessengerImpl>("messenger", MessengerImpl.class, new SQLKey[] { new SQLKey("id"), new SQLKey("object_id"), new SQLKey("user_id") });

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "object_id", "user_id", "name", "anim_id", "music_id", "color" };

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected MessengerImpl(long id) throws SQLException {
		init(id);
		this.mObject = new SingleAssociationNotNull<Messenger, VObject, VObjectImpl>(this, "object_id", VObjectImpl.SPECIFICATION);
		this.mUser = new SingleAssociationNotNull<Messenger, User, UserImpl>(this, "user_id", UserImpl.SPECIFICATION);
	}

	protected MessengerImpl() {
		this.mObject = new SingleAssociationNotNull<Messenger, VObject, VObjectImpl>(this, "object_id", VObjectImpl.SPECIFICATION);
		this.mUser = new SingleAssociationNotNull<Messenger, User, UserImpl>(this, "user_id", UserImpl.SPECIFICATION);
	}

	/**
	 * Creates a new MessengerImpl using a VObjectImpl
	 * 
	 * @param inObject
	 * @param inName
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	public MessengerImpl(VObject inObject, String inName) throws SQLException, IllegalArgumentException {
		this((Long) AbstractSQLRecord.getObjectId((VObjectImpl) inObject), inName, false);
	}

	/**
	 * Creates a new MessengerImpl using a UserImpl
	 * 
	 * @param inUser
	 * @param inName
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	public MessengerImpl(User inUser, String inName) throws SQLException, IllegalArgumentException {
		this((Long) AbstractSQLRecord.getObjectId((UserImpl) inUser), inName, true);
	}

	/**
	 * Creates a MessengerImpl using either a UserImpl or a VObjectImpl
	 * 
	 * @param inId
	 * @param inName
	 * @param userBased true if the inId is a UserImpl's, false if it is a
	 *            VObjectImpl's
	 * @throws SQLException
	 */
	protected MessengerImpl(long inId, String inName, boolean userBased) throws SQLException {
		User myUser = null;
		if (userBased) {
			this.user_id = inId;
			this.object_id = null;
			myUser = Factories.USER.find(inId);
		} else {
			this.object_id = inId;
			myUser = Factories.VOBJECT.find(this.object_id).getOwner();
			this.user_id = null;
		}

		this.name = inName;
		this.anim_id = myUser.getColor() == null ? null : myUser.getColor().getId();
		this.music_id = myUser.getMusic() == null ? null : myUser.getMusic().getId();
		this.color = ConvertTools.htoi(myUser.getColorSign());
		init(MessengerImpl.NEW_COLUMNS);
		this.mObject = new SingleAssociationNotNull<Messenger, VObject, VObjectImpl>(this, "object_id", VObjectImpl.SPECIFICATION);
		this.mUser = new SingleAssociationNotNull<Messenger, User, UserImpl>(this, "user_id", UserImpl.SPECIFICATION);
	}

	protected long id;
	protected Long object_id;
	protected Long user_id;
	protected Long anim_id;
	protected Long music_id;
	protected long color;	
	protected String name;

	private Map<Message, MessageSent> messageSent;
	private Map<Message, MessageReceived> messageReceived;

	private final SingleAssociationNotNull<Messenger, VObject, VObjectImpl> mObject;
	private final SingleAssociationNotNull<Messenger, User, UserImpl> mUser;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.violet.platform.datamodel.Messenger#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.violet.platform.datamodel.Messenger#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<MessengerImpl> getSpecification() {
		return MessengerImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.violet.platform.datamodel.Messenger#getObject()
	 */
	public VObject getObject() {
		if (this.object_id != null) {
			if (this.mObject != null) {
				return this.mObject.get(this.object_id);
			}
			return Factories.VOBJECT.find(this.object_id);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.violet.platform.datamodel.Messenger#getUser()
	 */
	public User getUser() {
		long userId = 0;
		if (this.user_id == null) {
			final VObject myObject = getObject();

			if (myObject != null) {
				userId = myObject.getOwner().getId();
			} else {
				return null;
			}
		} else {
			userId = this.user_id;
		}

		if (this.mUser != null) {
			return this.mUser.get(userId);
		}
		return Factories.USER.find(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.violet.platform.datamodel.Messenger#getName()
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setName(theUpdateMap, name);
		update(theUpdateMap);
	}

	/**
	 * @param name the name to set
	 */
	private void setName(Map<String, Object> inUpdateMap, String name) {
		if (((this.name == null) && (name != null)) || !this.name.equals(name)) {
			this.name = name;
			inUpdateMap.put("name", name);
		}
	}

	public Map<Message, MessageReceived> getMessageReceived() {
		if (this.messageReceived == null) {
			try {
				this.messageReceived = DecoratedAssociation.createDecoratedAssociation(this, MessageImpl.SPECIFICATION, "message_id", MessageReceivedImpl.SPECIFICATION, "recipient_id");
			} catch (final SQLException e) {
				MessengerImpl.LOGGER.fatal(e, e);
			}
		}
		return this.messageReceived;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.violet.platform.datamodel.Messenger#getInboxMessagesSorted()
	 */
	public SortedMap<Message, MessageReceived> getInboxMessagesSorted() {
		final Map<Message, MessageReceived> theMessages = getMessageReceived();
		final SortedMap<Message, MessageReceived> theResult = new TreeMap<Message, MessageReceived>(MessageImpl.ASCENDING_COMPARATOR);

		for (final Entry<Message, MessageReceived> theEntry : theMessages.entrySet()) {
			final MessageReceived theMessageReceived = theEntry.getValue();
			if (MessageReceived.MESSAGE_RECEIVED_STATES.INBOX == theMessageReceived.getMessageState()) {
				final Message theMessage = theEntry.getKey();
				theResult.put(theMessage, theMessageReceived);
			}
		}

		return theResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.violet.platform.datamodel.Messenger#getMessageReceivedFromMessenger
	 *      (net.violet.platform.datamodel.Messenger)
	 */
	public SortedMap<Message, MessageReceived> getMessageReceivedFromMessenger(Messenger inSender) {
		final SortedMap<Message, MessageReceived> theResult = new TreeMap<Message, MessageReceived>(MessageImpl.DESCENDING_COMPARATOR);

		final Map<Message, MessageReceived> theMessageReceived = getMessageReceived();

		synchronized (theMessageReceived) { // We do not want to get a concurrent modication on that.

			for (final Entry<Message, MessageReceived> theEntry : theMessageReceived.entrySet()) {
				if (inSender.equals(theEntry.getValue().getSender()) && (MessageReceived.MESSAGE_RECEIVED_STATES.PENDING != theEntry.getValue().getMessageState())) {
					theResult.put(theEntry.getKey(), theEntry.getValue());
				}
			}
		}

		return theResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.violet.platform.datamodel.Messenger#getMessageSentToMessenger(com
	 *      .violet.platform.datamodel.Messenger)
	 */
	public SortedMap<Message, MessageSent> getMessageSentToMessenger(Messenger inSender) {
		final SortedMap<Message, MessageSent> theResult = new TreeMap<Message, MessageSent>(MessageImpl.DESCENDING_COMPARATOR);
		final Map<Message, MessageSent> theMessageSent = getMessageSent();

		synchronized (theMessageSent) { // We do not want to get a concurrent modication on that.
			for (final Entry<Message, MessageSent> theEntry : theMessageSent.entrySet()) {
				if (inSender.equals(theEntry.getValue().getRecipient())) {
					theResult.put(theEntry.getKey(), theEntry.getValue());
				}
			}
		}

		return theResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.violet.platform.datamodel.Messenger#getMessageSent()
	 */
	public Map<Message, MessageSent> getMessageSent() {
		if (this.messageSent == null) {
			try {
				this.messageSent = DecoratedAssociation.createDecoratedAssociation(this, MessageImpl.SPECIFICATION, "message_id", MessageSentImpl.SPECIFICATION, "sender_id");
			} catch (final SQLException e) {
				MessengerImpl.LOGGER.fatal(e, e);
			}
		}
		return this.messageSent;
	}

	/*
	 * (non-Javadoc) @seenet.violet.platform.datamodel.Messenger#
	 * deleteMessageNabcastInFutureFromMessenger
	 * (net.violet.platform.datamodel.Messenger, java.lang.Long)
	 */
	public void deleteMessageNabcastInFutureFromMessenger(Messenger inSender, Long inNabcastId) {
		final Map<Message, MessageReceived> theMessageReceivedMap = getMessageReceived();
		final Set<Message> theMessageList = new HashSet<Message>(theMessageReceivedMap.keySet());

		synchronized (theMessageReceivedMap) {

			for (final Message theMessage : theMessageList) {

				final MessageReceived theMessageReceived = theMessageReceivedMap.get(theMessage);

				if (inSender.equals(theMessageReceived.getSender()) && (MessageReceived.MESSAGE_RECEIVED_STATES.PENDING == theMessageReceived.getMessageState()) && inNabcastId.equals(theMessage.getNabcast())) {
					theMessageReceivedMap.remove(theMessage);
					theMessage.delete();
				}
			}
		}
	}

	public long countReceivedMessagesByState(MessageReceived.MESSAGE_RECEIVED_STATES inMessageState, boolean displayNabcast) {
		long result = 0;
		if (MessageReceived.MESSAGE_RECEIVED_STATES.INBOX == inMessageState) {
			result = MessengerImpl.MESSENGER_COMMON.countReceivedMessagesByVObject(this, displayNabcast);
		} else if (MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED == inMessageState) {
			result = MessengerImpl.MESSENGER_COMMON.countArchivedMessagesByVObject(this, displayNabcast);
		}
		return result;
	}

	public RABBIT_STATE computeRabbitState() {
		return MessengerImpl.MESSENGER_COMMON.computeRabbitState(this);
	}

}
