package net.violet.platform.dataobjects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.Map.Entry;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public final class MessageSentData {

	private static final Logger LOGGER = Logger.getLogger(MessageSentData.class);

	/**
	 * Comparateur, par ordre anti-chronologique.
	 */
	private static final Comparator<MessageSentData> DESCENDING_COMPARATOR = new Comparator<MessageSentData>() {

		public int compare(MessageSentData o1, MessageSentData o2) {
			final long theDiff = o2.getTimeOfDelivery() - o1.getTimeOfDelivery();
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
	};

	private final Message messageRef;
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
	private MessageSentData(Message inMessage, MessageSent inMessageSent, User inUser) throws NullPointerException {
		this.messageRef = inMessage;
		this.sender = UserData.getData(inUser);
		if ((inMessageSent != null) && (inMessageSent.getRecipient() != null)) {
			this.recipient = VObjectData.getData(inMessageSent.getRecipient().getObject());
		} else {
			this.recipient = null;
		}

		if ((inUser != null) && (this.messageRef != null)) {
			final boolean use24 = true;
			TimeZone tz = null;

			final Lang lang = inUser.getAnnu().getLangPreferences();

			if ((inUser.getTimezone() != null) && (inUser.getTimezone().getTimezone_javaId() != null)) {
				tz = TimeZone.getTimeZone(inUser.getTimezone().getTimezone_javaId());
			}

			final CCalendar myCalendar = this.messageRef.getTimeOfDelivery(tz);
			this.date_formated = myCalendar.getTimeFormatedRelativeToNow(tz, use24, lang);
		} else {
			this.date_formated = StringShop.MIDNIGHT;
		}

	}

	/**
	 * Construct and EventData
	 * 
	 * @param inEvent
	 * @param inUser
	 * @throws SQLException , NullPointerException
	 */
	private MessageSentData(MessageSent inMessageSent, User inUser) throws NullPointerException {
		final MessageSent theMessageSent = inMessageSent;
		this.messageRef = inMessageSent.getMessage();
		this.sender = UserData.getData(inUser);
		if ((theMessageSent != null) && (theMessageSent.getRecipient() != null)) {
			this.recipient = VObjectData.getData(theMessageSent.getRecipient().getObject());
		} else {
			this.recipient = null;
		}

		if ((inUser != null) && (this.messageRef != null)) {
			final boolean use24 = true;
			TimeZone tz = null;

			final Lang lang = inUser.getAnnu().getLangPreferences();

			if ((inUser.getTimezone() != null) && (inUser.getTimezone().getTimezone_javaId() != null)) {
				tz = TimeZone.getTimeZone(inUser.getTimezone().getTimezone_javaId());
			}

			final CCalendar myCalendar = this.messageRef.getTimeOfDelivery(tz);
			this.date_formated = myCalendar.getTimeFormatedRelativeToNow(tz, use24, lang);
		} else {
			this.date_formated = StringShop.MIDNIGHT;
		}

	}

	private static final Comparator<MessageSent> COMPARATOR = new Comparator<MessageSent>() {

		public int compare(MessageSent o1, MessageSent o2) {
			return o2.getMessage().getTimeOfDelivery().compareTo(o1.getMessage().getTimeOfDelivery());
		}
	};

	public static List<MessageSentData> findAllSentMessagesByObjectFromUser(User inUser, int index, int nbr) {
		final Messenger theSender = Factories.MESSENGER.getByUser(inUser);
		if ((index >= 0) && (nbr >= 0)) {
			List<MessageSent> list = new ArrayList<MessageSent>();
			list.addAll(theSender.getMessageSent().values());

			// triage de la liste
			Collections.sort(list, MessageSentData.COMPARATOR);

			// troncage de la liste
			int nbItems = nbr;
			if ((nbItems + index) >= list.size()) {
				nbItems = list.size() - index;
			}
			list = list.subList(index, index + nbItems);
			final List<MessageSentData> l = MessageSentData.generateList(list, inUser);
			return l;

		}

		final Set<Entry<Message, MessageSent>> messagesSentEntrySet = theSender.getMessageSent().entrySet();
		return MessageSentData.generateList(messagesSentEntrySet, inUser);
	}

	public static List<MessageSentData> findAllSentMessagesByUserToObject(VObject inObject, User inUser, int nbr) {
		final Messenger theRecipient = Factories.MESSENGER.getByObject(inObject);
		final Messenger theSender = Factories.MESSENGER.getByUser(inUser);
		final int nbrReturn = nbr;
		try {
			final List<MessageSentData> theList = MessageSentData.generateList(theSender.getMessageSentToMessenger(theRecipient), inObject.getOwner());

			if ((nbrReturn >= theList.size()) || (0 >= nbrReturn)) {
				return theList;
			}

			return theList.subList(0, nbrReturn);

		} catch (final SQLException e) {
			MessageSentData.LOGGER.fatal(e, e);
		}

		return Collections.emptyList();
	}

	/**
	 * Generates a list of MessagesSentData with the given MessageSentImpl set &
	 * UserImpl
	 * 
	 * @param inMusics MusicImpl list
	 * @param inUser the user
	 * @return
	 */
	private static List<MessageSentData> generateList(Set<Entry<Message, MessageSent>> inMessagesSentEntrySet, User inUser) {
		final List<MessageSentData> messagesSentDataList = new ArrayList<MessageSentData>();
		for (final Entry<Message, MessageSent> tempMessage : inMessagesSentEntrySet) {
			try {
				messagesSentDataList.add(new MessageSentData(tempMessage.getKey(), tempMessage.getValue(), inUser));
			} catch (final NullPointerException e) {
				MessageSentData.LOGGER.fatal(e, e);
			}
		}

		Collections.sort(messagesSentDataList, MessageSentData.DESCENDING_COMPARATOR);

		return messagesSentDataList;
	}

	private static List<MessageSentData> generateList(List<MessageSent> inMessagesSent, User inUser) {
		final List<MessageSentData> messagesSentDataList = new ArrayList<MessageSentData>();
		for (final MessageSent tempMessage : inMessagesSent) {
			try {
				messagesSentDataList.add(new MessageSentData(tempMessage, inUser));
			} catch (final NullPointerException e) {
				MessageSentData.LOGGER.fatal(e, e);
			}
		}

		return messagesSentDataList;
	}

	/**
	 * Generates a list of MessagesSentData with the given MessageSentImpl set &
	 * UserImpl
	 * 
	 * @param inMessageSentEntryMap the list will not be sorted
	 * @param inUser
	 * @return
	 */
	private static List<MessageSentData> generateList(SortedMap<Message, MessageSent> inMessageSentEntryMap, User inUser) {
		final List<MessageSentData> messagesSentDataList = new ArrayList<MessageSentData>();
		for (final Entry<Message, MessageSent> tempMessageReceived : inMessageSentEntryMap.entrySet()) {
			try {
				messagesSentDataList.add(new MessageSentData(tempMessageReceived.getKey(), tempMessageReceived.getValue(), inUser));
			} catch (final NullPointerException e) {
				MessageSentData.LOGGER.fatal(e, e);
			}
		}

		return messagesSentDataList;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		if (this.messageRef != null) {
			return this.messageRef.getId();
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
		if (this.messageRef != null) {
			return this.messageRef.getTimeOfDelivery().getTimeInMillis();
		}

		return 0;
	}

	/**
	 * @return the attribute eventsend_usersend
	 */
	public long getUserSenderId() {
		return this.sender.getId();
	}

	/**
	 * @return the date the eventSend was created ready to display
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
	 * @return the attribute eventsend_title
	 */
	public String getEventsend_title() {
		if ((this.messageRef != null) && (this.messageRef.getText() != null)) {
			return this.messageRef.getText();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getTitle() {
		return getEventsend_title();
	}

	/**
	 * @return the attribute eventsend_url
	 */
	public String getEventsend_url() {
		if ((this.messageRef != null) && (this.messageRef.getBody() != null) && (this.messageRef.getBody().getPath() != null)) {
			return this.messageRef.getBody().getPath();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getUrl() {
		return getEventsend_url();
	}

	/**
	 * @return the attribute nbPlayed
	 */
	public long getNbPlayed() {
		if (this.messageRef != null) {
			return this.messageRef.getCount();
		}
		return 0;
	}
}
