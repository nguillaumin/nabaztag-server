package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Messenger;

public class MessageReceivedMock extends AbstractMockRecord<MessageReceived, MessageReceivedMock> implements MessageReceived {

	private final Message mMessage;
	private final Messenger mSender;
	private final Messenger mRecipient;
	private MessageReceived.MESSAGE_RECEIVED_STATES mState;

	public MessageReceivedMock(long inId, Message inMessage, Messenger inSender, Messenger inRecipient, MessageReceived.MESSAGE_RECEIVED_STATES inState) {
		super(inId);
		this.mMessage = inMessage;
		this.mSender = inSender;
		this.mRecipient = inRecipient;
		this.mState = inState;
	}

	public Message getMessage() {
		return this.mMessage;
	}

	public MessageReceived.MESSAGE_RECEIVED_STATES getMessageState() {
		return this.mState;
	}

	public Messenger getRecipient() {
		return this.mRecipient;
	}

	public Messenger getSender() {
		return this.mSender;
	}

	public void setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES inState) {
		this.mState = inState;
	}
}
