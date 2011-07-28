package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageImpl;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.MessageServices;
import net.violet.platform.ping.EventMng;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class MessageData extends APIData<Message> {

	private static final Logger LOGGER = Logger.getLogger(MessageData.class);

	/**
	 * Comparateur, par ordre anti-chronologique.
	 */
	private static final Comparator<MessageData> DESCENDING_COMPARATOR = new Comparator<MessageData>() {

		public int compare(MessageData o1, MessageData o2) {
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

	private UserData sender;
	private VObjectData recipient;
	private String date_formated;
	private StatusMessage theStatus;

	public enum StatusMessage {
		INBOX,
		ARCHIVED,
		SENT;

		private static final Map<String, StatusMessage> NAME_STATUS;

		static {
			final Map<String, StatusMessage> theStatuses = new HashMap<String, StatusMessage>();

			for (final StatusMessage status : StatusMessage.values()) {
				theStatuses.put(status.name().toUpperCase(), status);
			}

			NAME_STATUS = Collections.unmodifiableMap(theStatuses);

		}

		/**
		 * NOTE : case insensitive search
		 * 
		 * @param inName
		 * @return the StatusMessage corresponding to this name, null if there is none.
		 */
		public static StatusMessage getStatusByName(String inName) {
			return (inName != null) ? StatusMessage.NAME_STATUS.get(inName.toUpperCase()) : null;
		}
	}

	/**
	 * Palette are predefined table of colors for the led of Nabaztag They are
	 * used during a choregraphy play
	 */
	public enum Palette {

		/**
		 * Choose a palette between 0 (flash) and 6 (nature)
		 */
		RANDOM("random", -1) {

			/**
			 * If the palette is random, returns one of the valid palette
			 * internal value by generating a random value between 0 and 6
			 * 
			 * @return
			 */
			@Override
			public long getInternalValue() {
				return Palette.RAND_GENE.nextInt(7);
			}
		},

		FLASH("flash", 0),
		VIOLET("violet", 1),
		LIGHT("light", 2),
		EMOTION("emotion", 3),
		ORIENTAL("oriental", 4),
		PASTEL("pastel", 5),
		NATURE("nature", 6),

		/**
		 * Specific to nabaztag v2 : plays random lights during streaming
		 */
		RANDOM_V2("random_v2", 8);

		private final String mName;
		private final int mValue;
		private static final Random RAND_GENE = new Random(System.currentTimeMillis());

		private static final Map<Integer, Palette> ID_PALETTE;
		private static final Map<String, Palette> NAME_PALETTE;

		static {
			final Map<Integer, Palette> theMapId = new HashMap<Integer, Palette>();
			final Map<String, Palette> theMapName = new HashMap<String, Palette>();

			for (final Palette aPalette : Palette.values()) {
				theMapId.put(new Integer(aPalette.mValue), aPalette);
				theMapName.put(aPalette.mName, aPalette);
			}

			ID_PALETTE = Collections.unmodifiableMap(theMapId);
			NAME_PALETTE = Collections.unmodifiableMap(theMapName);
		}

		private Palette(String inName, int inValue) {
			this.mName = inName;
			this.mValue = inValue;
		}

		public String getName() {
			return this.mName;
		}

		/**
		 * If the palette is random, returns one of the valid palette internal
		 * value by generating a random value between 0 and 6
		 * 
		 * @return
		 */
		public long getInternalValue() {
			return this.mValue;
		}

		/**
		 * @param inName
		 * @return the corresponding palette or NULL if not found
		 */
		public static Palette findPaletteByName(String inName) {
			return Palette.NAME_PALETTE.get(inName);
		}

		/**
		 * @param inNum
		 * @return the corresponding palette or NULL if not found
		 */
		public static Palette findPaletteByNum(Integer inNum) {
			return Palette.ID_PALETTE.get(inNum);
		}
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.MESSAGE;
	}

	/**
	 * Constructeur à partir d'un message et du status recu ou envoyé
	 * 
	 * @param inMessage
	 * @param inStatus not <code>null</code>
	 * 
	 */
	public MessageData(Message inMessage, StatusMessage inStatus) {

		super(inMessage);
		this.theStatus = inStatus;

		if (StatusMessage.SENT == inStatus) {
			final MessageSent theMessageSent = Factories.MESSAGE_SENT.findMessageSentByMessageId(inMessage.getId());
			if (theMessageSent != null) {
				this.recipient = VObjectData.getData(theMessageSent.getRecipient().getObject());
				this.sender = UserData.getData(theMessageSent.getSender().getUser());
				this.theStatus = StatusMessage.SENT;
			}
		} else {
			final MessageReceived theMessageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(inMessage.getId());
			if (theMessageReceived != null) {
				this.recipient = VObjectData.getData(theMessageReceived.getRecipient().getObject());
				this.sender = UserData.getData(theMessageReceived.getSender().getUser());
				this.theStatus = StatusMessage.getStatusByName(theMessageReceived.getMessageState().name());
			}

		}

		this.date_formated = "00:00:00";
	}

	/**
	 * Constructeur à partir d'un message recu
	 * 
	 * @param inMessage
	 * @param inMessageReceived
	 */
	private MessageData(Message inMessage, MessageReceived inMessageReceived) {
		super(inMessage);
		this.theStatus = StatusMessage.INBOX;
		final Messenger theSender = inMessageReceived.getSender();

		if (theSender == null) {
			MessageData.LOGGER.fatal("Messenger cannot be located for the messageRef of id : " + getRecord().getId());
			this.sender = UserData.getData(null);
		} else {
			this.sender = UserData.getData(theSender.getUser());
		}

		if (inMessageReceived.getRecipient() != null) {
			this.recipient = VObjectData.getData(inMessageReceived.getRecipient().getObject());
		} else {
			this.recipient = null;
		}

	}

	/**
	 * Constructeur à partir d'un message envoyé
	 * 
	 * @param inMessage
	 * @param inMessageSent
	 * @param inUser
	 */
	private MessageData(Message inMessage, MessageSent inMessageSent, User inUser) {
		super(inMessage);
		this.theStatus = StatusMessage.SENT;
		this.sender = UserData.getData(inUser);

		if (inMessageSent.getRecipient() != null) {
			this.recipient = VObjectData.getData(inMessageSent.getRecipient().getObject());
		} else {
			this.recipient = null;
		}
	}

	/**
	 * Accesseur à partir d'un ID application.
	 * 
	 * @param inAPIId
	 * @param inAPIKey
	 * @param inStatus not <code>null</code>
	 * 
	 * @return MessageData ou <code>null</code> si le message n'existe pas.
	 */
	public static MessageData findByAPIId(String inAPIId, String inAPIKey, StatusMessage inStatus) {

		MessageData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.MESSAGE, inAPIKey);

		if (theID != 0) {
			final Message message = Factories.MESSAGE.find(theID);

			if (message != null) {
				theResult = new MessageData(message, inStatus);
			}
		}

		return theResult;
	}

	public static List<MessageData> findAllMessageReceivedOrArchivedByStatus(StatusMessage inStatus, UserData inUserData, VObjectData inObjectData, int inSkip, int inGetCount) {
		final List<MessageData> theResult = new ArrayList<MessageData>();

		final boolean use24h = inUserData.use24h();
		final TimeZone theTimeZone;

		final SiteLangData theUserlang = inUserData.getUserLang();

		if ((inUserData.getTimezone() != null) && (inUserData.getTimezone().getTimezone_javaId() != null)) {
			theTimeZone = TimeZone.getTimeZone(inUserData.getTimezone().getTimezone_javaId());
		} else {
			theTimeZone = null;
		}

		Factories.MESSAGE.walkInMessageReceivedOrArchivedByStatus(inStatus, inUserData.getRecord(), inObjectData.getReference(), inSkip, inGetCount, new MessageImpl.JoinRecordsWalker<Message, MessageReceived>() {

			public void process(Message inMessage, MessageReceived inMessageReceived) {
				final MessageData theMessageData = new MessageData(inMessage, inMessageReceived);
				theMessageData.setDateOfDelivery(theUserlang.getReference(), theTimeZone, use24h);
				theResult.add(theMessageData);
			}
		});

		Collections.sort(theResult, MessageData.DESCENDING_COMPARATOR);
		return theResult;
	}

	public static List<MessageData> findAllMessageReceived(UserData inUserData, VObjectData inObjectData) {

		final List<MessageData> theResult = new ArrayList<MessageData>();

		final boolean use24h = inUserData.use24h();
		final TimeZone theTimeZone;

		final SiteLangData theUserlang = inUserData.getUserLang();

		if ((inUserData.getTimezone() != null) && (inUserData.getTimezone().getTimezone_javaId() != null)) {
			theTimeZone = TimeZone.getTimeZone(inUserData.getTimezone().getTimezone_javaId());
		} else {
			theTimeZone = null;
		}

		Factories.MESSAGE.walkInMessageReceived(inObjectData.getReference(), new MessageImpl.JoinRecordsWalker<Message, MessageReceived>() {

			public void process(Message inMessage, MessageReceived inMessageReceived) {
				final MessageData theMessageData = new MessageData(inMessage, inMessageReceived);
				theMessageData.setDateOfDelivery(theUserlang.getReference(), theTimeZone, use24h);
				theResult.add(theMessageData);
			}
		});

		Collections.sort(theResult, MessageData.DESCENDING_COMPARATOR);
		return theResult;
	}

	public static List<MessageData> findAllMessageSent(UserData inUserData, int inSkip, int inGetCount) {
		final List<MessageData> theResult = new ArrayList<MessageData>();

		final User theUser = inUserData.getRecord();
		final boolean use24h = inUserData.use24h();
		final TimeZone theTimeZone;

		final SiteLangData theUserlang = inUserData.getUserLang();

		if ((inUserData.getTimezone() != null) && (inUserData.getTimezone().getTimezone_javaId() != null)) {
			theTimeZone = TimeZone.getTimeZone(inUserData.getTimezone().getTimezone_javaId());
		} else {
			theTimeZone = null;
		}

		Factories.MESSAGE.walkInMessageSent(theUser, inSkip, inGetCount, new MessageImpl.JoinRecordsWalker<Message, MessageSent>() {

			public void process(Message inMessage, MessageSent inMessageSent) {
				final MessageData theMessageData = new MessageData(inMessage, inMessageSent, theUser);
				theMessageData.setDateOfDelivery(theUserlang.getReference(), theTimeZone, use24h);
				MessageData.LOGGER.info("Message ID = " + inMessage.getId() + " Date : " + theMessageData.getDateOfDelivery());
				theResult.add(theMessageData);
			}
		});

		Collections.sort(theResult, MessageData.DESCENDING_COMPARATOR);
		return theResult;
	}

	public static long countMessageReceivedOrArchivedByStatus(StatusMessage inStatus, UserData inUserData, VObjectData inObjectData) {
		return Factories.MESSAGE.countMessageReceivedOrArchivedByStatus(inStatus, inUserData.getReference(), inObjectData.getReference());
	}

	public static long countMessageSent(UserData inUserData) {
		return Factories.MESSAGE.countMessageSent(inUserData.getReference());
	}

	public static void replayMessage(MessageData inMessage) {
		final MessageReceived theMessageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(inMessage.getId());

		if (theMessageReceived != null) {
			MessageServices.resendUserMessage(inMessage.getMessage(), theMessageReceived.getSender(), theMessageReceived.getRecipient());
			EventMng.refreshCountMessagesAfterSending(inMessage.getRecipient().getRecord());

		} else {
			MessageData.LOGGER.fatal("The messageReceived with the message_id = " + inMessage.getId() + "cannot be found");
		}
	}

	public static void archiveMessage(MessageData inMessage, boolean notify) {
		final MessageReceived theMessageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(inMessage.getId());

		if (theMessageReceived != null) {
			theMessageReceived.setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED);
			if (notify) {
				EventMng.refreshCountMessagesAfterPlaying(theMessageReceived.getRecipient());
			}
		} else {
			MessageData.LOGGER.fatal("The messageReceived with the message_id = " + inMessage.getId() + "cannot be found");
		}
	}

	public static void deleteMessage(MessageData inMessage, UserData inUser) {

		final Message msg = inMessage.getRecord();
		final StatusMessage msgStatus = inMessage.getStatusMessage();

		if ((msgStatus != null) && (msg != null)) {
			// on delete un message recu ou archivé
			if ((msgStatus == StatusMessage.INBOX) || (msgStatus == StatusMessage.ARCHIVED)) {
				MessageData.deleteRecipientMessageReceived(inMessage);

			} else if (msgStatus == StatusMessage.SENT) { // on delete un
				final Messenger theMessenger = Factories.MESSENGER.findByUser(inUser.getRecord());
				// message envoyé

				if (theMessenger != null) {
					theMessenger.getMessageSent().remove(msg);
					if (msg.getTimeOfDelivery().after(Calendar.getInstance())) { // delete
						// message
						// différé
						MessageData.deleteRecipientMessageReceived(inMessage);
					}
				}
			}

			if (msg.isOrphan()) {
				msg.delete(); // il clean aussi dans les tables
				// scheduled_message et event selon l'objet
			}
		}
	}

	private static void deleteRecipientMessageReceived(MessageData inMessage) {

		final VObject theObjectRecipient = inMessage.getRecipient().getRecord();
		if (theObjectRecipient != null) {
			final Messenger theMessenger = Factories.MESSENGER.getByObject(theObjectRecipient);
			theMessenger.getMessageReceived().remove(inMessage.getRecord());
			EventMng.refreshCountMessagesAfterPlaying(theObjectRecipient);
		}
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		final Message theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}

		return 0;
	}

	/**
	 * @return the attribute TimeOfDelivery
	 */
	public long getTimeOfDelivery() {
		final Message theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getTimeOfDelivery().getTimeInMillis();
		}

		return 0;
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
	 * set date for display
	 * 
	 * @param inUserLang
	 * @param userTimeZone
	 * @param inUser24h
	 */
	public void setDateOfDelivery(Lang inUserLang, TimeZone userTimeZone, boolean inUser24h) {
		if ((userTimeZone != null) && (getRecord() != null)) {
			final CCalendar myCalendar = getRecord().getTimeOfDelivery(userTimeZone);
			this.date_formated = myCalendar.getTimeFormatedRelativeToNow(userTimeZone, inUser24h, inUserLang);
		} else {
			this.date_formated = StringShop.MIDNIGHT;
		}
	}

	public VObjectData getRecipient() {
		return this.recipient;
	}

	/**
	 * @return the attribute title
	 */
	public String getTitle() {
		final Message theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getText() != null)) {
			return theRecord.getText();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute url
	 */
	public String getUrl() {
		final Message theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getBody() != null) && (theRecord.getBody().getPath() != null)) {
			return theRecord.getBody().getPath();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute nbPlayed
	 */
	public long getNbPlayed() {
		final Message theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getCount();
		}
		return 0;
	}

	public Message getMessage() {
		return getRecord();
	}

	public StatusMessage getStatusMessage() {
		if (this.theStatus != null) {
			return this.theStatus;
		}
		return null;
	}
}
