package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.Messenger;

public class MessageSentMock extends AbstractMockRecord<MessageSent, MessageSentMock> implements MessageSent {

	private final Message mMessage;
	private final Messenger mSender;
	private final Messenger mRecipient;
	private final String mState;

	public MessageSentMock(long inId, Message inMessage, Messenger inSender, Messenger inRecipient) {
		super(inId);
		this.mMessage = inMessage;
		this.mSender = inSender;
		this.mRecipient = inRecipient;
		this.mState = "SENT";
	}

	public Message getMessage() {
		return this.mMessage;
	}

	public long getMessage_id() {
		return this.mMessage.getId();
	}

	public String getMessage_state() {
		return this.mState;
	}

	public Messenger getRecipient() {
		return this.mRecipient;
	}

	public long getRecipient_id() {
		return this.mRecipient.getId();
	}

	public Messenger getSender() {
		return this.mSender;
	}

	public long getSender_id() {
		return this.mSender.getId();
	}

}
