package net.violet.platform.datamodel.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;

public class MessengerMock extends AbstractMockRecord<Messenger, MessengerMock> implements Messenger {

	private static MessengerCommon MESSENGER_COMMON = new MessengerCommon();
	private final User mUser;
	private final VObject mObject;
	private String mName;
	private Anim mAnim;
	private Music mMusic;
	private final Map<Message, MessageReceived> messageReceived;
	private final Map<Message, MessageSent> messageSent;

	public MessengerMock(long inId, User inUser, VObject inObject, String inName) {
		super(inId);

		this.mUser = inUser;
		this.mObject = inObject;
		this.mName = inName;
		this.messageReceived = new HashMap<Message, MessageReceived>() {

			@Override
			public MessageReceived remove(Object key) {

				final MessageReceived theMessage = super.remove(key);

				if (theMessage != null) {
					theMessage.delete();
				}

				return theMessage;
			}
		};
		this.messageSent = new HashMap<Message, MessageSent>() {

			@Override
			public MessageSent remove(Object key) {

				final MessageSent theMessage = super.remove(key);

				if (theMessage != null) {
					theMessage.delete();
				}

				return theMessage;
			}
		};
	}

	public void deleteMessageNabcastInFutureFromMessenger(Messenger inSender, Long inNabcastId) {
		throw new UnsupportedOperationException();
	}

	public Anim getAnim() {
		return this.mAnim;
	}

	public SortedMap<Message, MessageReceived> getInboxMessagesSorted() {
		throw new UnsupportedOperationException();
	}

	public Map<Message, MessageReceived> getMessageReceived() {
		return this.messageReceived;
	}

	public SortedMap<Message, MessageReceived> getMessageReceivedFromMessenger(Messenger inSender) {
		return null;
	}

	public Map<Message, MessageSent> getMessageSent() {
		return this.messageSent;
	}

	public SortedMap<Message, MessageSent> getMessageSentToMessenger(Messenger inSender) {
		throw new UnsupportedOperationException();
	}

	public Music getMusic() {
		return this.mMusic;
	}

	public String getName() {
		return this.mName;
	}

	public VObject getObject() {
		return this.mObject;
	}

	public User getUser() {
		return this.mUser;
	}

	public void setName(String inName) {
		this.mName = inName;
	}

	public long countReceivedMessagesByState(MessageReceived.MESSAGE_RECEIVED_STATES messageState, boolean displayNabcast) {
		long result = 0;
		if (MessageReceived.MESSAGE_RECEIVED_STATES.INBOX.compareTo(messageState) == 0) {
			result = MessengerMock.MESSENGER_COMMON.countReceivedMessagesByVObject(this, displayNabcast);
		} else if (MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED.compareTo(messageState) == 0) {
			result = MessengerMock.MESSENGER_COMMON.countArchivedMessagesByVObject(this, displayNabcast);
		}
		return result;
	}

	public RABBIT_STATE computeRabbitState() {
		return MessengerMock.MESSENGER_COMMON.computeRabbitState(this);
	}

	public void setSignatureInformation(Anim inAnim, String colorSign, Music inMusic) {
		this.mAnim = inAnim;
		this.mMusic = inMusic;
	}

}
