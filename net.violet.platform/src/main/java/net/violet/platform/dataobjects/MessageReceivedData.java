package net.violet.platform.dataobjects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.Map.Entry;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public final class MessageReceivedData extends RecordData<Message> {

	private static final Logger LOGGER = Logger.getLogger(MessageReceivedData.class);

	private final UserData sender;
	private final VObjectData recipient;
	private final String date_formated;

	/**
	 * Construct and EventData
	 * 
	 * @param inEvent
	 * @param inUser
	 * @throws SQLException , NullPointerException
	 */
	private MessageReceivedData(Message inMessage, MessageReceived inMessageReceived, User inUser) {
		super(inMessage);
		final Messenger theSender = inMessageReceived.getSender();

		if (theSender == null) {
			MessageReceivedData.LOGGER.fatal("Messenger cannot be located for the messageRef of id : " + getRecord().getId());
			this.sender = UserData.getData(null);
		} else {
			this.sender = UserData.getData(theSender.getUser());
		}

		if (inMessageReceived.getRecipient() != null) {
			this.recipient = VObjectData.getData(inMessageReceived.getRecipient().getObject());
		} else {
			this.recipient = null;
		}

		if ((inUser != null) && (getRecord() != null)) {
			final boolean use24 = true;
			TimeZone tz = null;

			final Lang lang = inUser.getAnnu().getLangPreferences();

			if ((inUser.getTimezone() != null) && (inUser.getTimezone().getTimezone_javaId() != null)) {
				tz = TimeZone.getTimeZone(inUser.getTimezone().getTimezone_javaId());
			}

			final CCalendar myCalendar = getRecord().getTimeOfDelivery(tz);
			this.date_formated = myCalendar.getTimeFormatedRelativeToNow(tz, use24, lang);
		} else {
			this.date_formated = StringShop.MIDNIGHT;
		}

	}

	private MessageReceivedData(MessageReceived m, User inUser) throws NullPointerException {
		super(m.getMessage());
		final MessageReceived theMessageReceived = m;

		this.sender = UserData.getData(m.getSender().getUser());
		if (theMessageReceived.getRecipient() != null) {
			this.recipient = VObjectData.getData(theMessageReceived.getRecipient().getObject());
		} else {
			this.recipient = null;
		}

		if ((inUser != null) && (getRecord() != null)) {
			final boolean use24 = true;
			TimeZone tz = null;

			final Lang lang = inUser.getAnnu().getLangPreferences();

			if ((inUser.getTimezone() != null) && (inUser.getTimezone().getTimezone_javaId() != null)) {
				tz = TimeZone.getTimeZone(inUser.getTimezone().getTimezone_javaId());
			}
			final CCalendar myCalendar = getRecord().getTimeOfDelivery(tz);
			this.date_formated = myCalendar.getTimeFormatedRelativeToNow(tz, use24, lang);
		} else {
			this.date_formated = StringShop.MIDNIGHT;
		}

	}

	/**
	 * Returns the amount of messages received by an object
	 * 
	 * @param inObject
	 * @param displayNabcast
	 * @return
	 * @throws SQLException
	 */
	public static long countReceivedMessagesByVObject(VObject inObject, MessageReceived.MESSAGE_RECEIVED_STATES messageState, boolean displayNabcast) {
		if (inObject != null) {
			final Messenger theRecipient = Factories.MESSENGER.getByObject(inObject);

			if (theRecipient != null) {
				return theRecipient.countReceivedMessagesByState(messageState, displayNabcast);
			}
			MessageReceivedData.LOGGER.fatal("Could not get a Messenger for this object id : " + inObject.getId());
		}
		return 0;
	}

	/**
	 * Finds all the messages received by the given user
	 */
	public static List<MessageReceivedData> findAllMessagesReceivedByObject(VObject inObject, MessageReceived.MESSAGE_RECEIVED_STATES messageState, boolean displayNabcast, int index, int nbr) {
		final Messenger theRecipient = Factories.MESSENGER.getByObject(inObject);
		final User theOwner = inObject.getOwner();

		final List<MessageReceived> list = new LinkedList<MessageReceived>();

		// construit la liste
		for (final MessageReceived m : theRecipient.getMessageReceived().values()) {
			if ((m.getMessageState() == messageState) && (displayNabcast || ((null != m.getMessage()) && (null == m.getMessage().getNabcast())))) {
				list.add(m);
			}
		}

		// triage de la liste
		Collections.sort(list, new Comparator<MessageReceived>() {

			public int compare(MessageReceived o1, MessageReceived o2) {
				final long theDiff = o2.getMessage().getTimeOfDelivery().getTimeInMillis() - o1.getMessage().getTimeOfDelivery().getTimeInMillis();
				final int theResult;
				if (theDiff < 0) {
					theResult = -1;
				} else if (theDiff > 0) {
					theResult = 1;
				} else {
					theResult = 0;
				}
				return theResult;
			}
		});

		if ((index >= 0) && (nbr >= 0)) {

			// troncage de la liste
			int nbItems = nbr;
			if ((nbItems + index) >= list.size()) {
				nbItems = list.size() - index;
			}
			return MessageReceivedData.generateList(list.subList(index, index + nbItems), theOwner);
		}

		return MessageReceivedData.generateList(list, theOwner);
	}

	/**
	 * Finds all the messages sent by an UserImpl to a VObjectImpl
	 * 
	 * @param inObject
	 * @param inUser
	 * @param nbr
	 * @return
	 */
	public static List<MessageReceivedData> findAllReceivedMessagesByObjectFromUser(VObject inObject, User inUser, int nbr) {
		final Messenger theRecipient = Factories.MESSENGER.getByObject(inObject);
		final Messenger theSender = Factories.MESSENGER.getByUser(inUser);
		final int nbrReturn = nbr;
		try {
			final List<MessageReceivedData> theList = MessageReceivedData.generateList(theRecipient.getMessageReceivedFromMessenger(theSender), inObject.getOwner());

			if ((nbrReturn >= theList.size()) || (0 >= nbrReturn)) {
				return theList;
			}

			return theList.subList(0, nbrReturn);

		} catch (final SQLException e) {
			MessageReceivedData.LOGGER.fatal(e, e);
		}

		return Collections.emptyList();
	}

	/**
	 * Provided list has to be filtered (according to wished state) and sorted.
	 * This methods only creates a MessageReceivedData object for each
	 * MessageReceivedImpl object.
	 * 
	 * @param list
	 * @param inUser
	 * @return
	 */
	private static List<MessageReceivedData> generateList(List<MessageReceived> list, User inUser) {
		final List<MessageReceivedData> messagesSentDataList = new ArrayList<MessageReceivedData>(list.size());
		for (final MessageReceived m : list) {
			try {
				messagesSentDataList.add(new MessageReceivedData(m, inUser));
			} catch (final NullPointerException e) {
				MessageReceivedData.LOGGER.fatal(e, e);
			}
		}

		return messagesSentDataList;
	}

	/**
	 * Generates a list of MessagesSentData with the given MessageSentImpl set &
	 * UserImpl
	 * 
	 * @param inMessageReceivedEntryMap the list will not be sorted
	 * @param inUser
	 * @return
	 */
	private static List<MessageReceivedData> generateList(SortedMap<Message, MessageReceived> inMessageReceivedEntryMap, User inUser) {
		final List<MessageReceivedData> messagesSentDataList = new ArrayList<MessageReceivedData>();
		for (final Entry<Message, MessageReceived> tempMessageReceived : inMessageReceivedEntryMap.entrySet()) {
			messagesSentDataList.add(new MessageReceivedData(tempMessageReceived.getKey(), tempMessageReceived.getValue(), inUser));
		}

		return messagesSentDataList;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		if (getRecord() != null) {
			return getRecord().getId();
		}

		return 0;
	}

	/**
	 * @return the attribute message_id
	 */
	public long getMessage_id() {
		return getId();
	}

	/**
	 * @return the attribute TimeOfDelivery
	 */
	public long getTimeOfDelivery() {
		if (getRecord() != null) {
			return getRecord().getTimeOfDelivery().getTimeInMillis();
		}

		return 0;
	}

	/**
	 * @return the attribute sender_name
	 */
	public String getSender_name() {
		return this.sender.getAnnu().getFirstName();
	}

	/**
	 * @return the attribute userSenderId
	 */
	public long getUserSenderId() {
		return this.sender.getId();
	}

	public UserData getSender() {
		return this.sender;
	}

	/**
	 * @return the date the messageRef was created ready to display
	 */
	public String getDateOfDelivery() {
		return this.date_formated;
	}

	/**
	 * @return the attribute eventsend_obj
	 */
	public long getRecipientsObjectId() {
		return this.recipient.getId();
	}

	public VObjectData getRecipient() {
		return this.recipient;
	}

	/**
	 * @return the attribute recipientsObjectName
	 */
	public String getRecipientsObjectName() {
		return this.recipient.getObject_login();
	}

	/**
	 * @return the attribute recipientsUserId
	 */
	public long getRecipientsUserId() {
		return this.recipient.getOwner().getId();
	}

	/**
	 * @return the attribute title
	 */
	public String getTitle() {
		if ((getRecord() != null) && (getRecord().getText() != null)) {
			return getRecord().getText();
		}
		return StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute url
	 */
	public String getUrl() {
		if ((getRecord() != null) && (getRecord().getBody() != null) && (getRecord().getBody().getPath() != null)) {
			return getRecord().getBody().getPath();
		}
		return StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute nbPlayed
	 */
	public long getNbPlayed() {
		if (getRecord() != null) {
			return getRecord().getCount();
		}
		return 0;
	}

	public Message getMessage() {
		return getRecord();
	}
}
