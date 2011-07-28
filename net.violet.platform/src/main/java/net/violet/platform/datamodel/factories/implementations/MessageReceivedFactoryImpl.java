package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageReceivedImpl;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.factories.MessageReceivedFactory;
import net.violet.platform.util.StringShop;
import net.violet.platform.util.StringShop.OPERATORS;

public class MessageReceivedFactoryImpl extends RecordFactoryImpl<MessageReceived, MessageReceivedImpl> implements MessageReceivedFactory {

	MessageReceivedFactoryImpl() {
		super(MessageReceivedImpl.SPECIFICATION);
	}

	public long countReceivedMessagesByMessenger(Messenger inMessenger, MessageReceived.MESSAGE_RECEIVED_STATES messageState, boolean displayNabcast) {
		return countReceivedMessagesByMessenger(inMessenger, messageState, displayNabcast, 0);
	}

	public long countReceivedMessagesByMessenger(Messenger inMessenger, MessageReceived.MESSAGE_RECEIVED_STATES messageState, boolean displayNabcast, int inLimit) {
		final List<Object> theValues = Arrays.asList(new Object[] { inMessenger.getId(), messageState.toString() });
		final StringBuilder theStringBuilder = new StringBuilder();

		// message_id = message.id AND recipient_id = ? AND message_state = ?

		theStringBuilder.append(StringShop.MESSAGE_ID);
		theStringBuilder.append(OPERATORS.EQUAL.getSymbol());
		theStringBuilder.append(StringShop.MESSAGE);
		theStringBuilder.append(net.violet.common.StringShop.POINT);
		theStringBuilder.append(StringShop.ID);
		theStringBuilder.append(StringShop.CONDITION_ACCUMULATOR);
		theStringBuilder.append(StringShop.RECIPIENT_ID_CONDITION);
		theStringBuilder.append(StringShop.CONDITION_ACCUMULATOR);
		theStringBuilder.append(StringShop.MESSAGE_STATE_CONDITION);

		if (!displayNabcast) {
			// AND message.nabcast is NULL
			theStringBuilder.append(StringShop.CONDITION_ACCUMULATOR);
			theStringBuilder.append(StringShop.MESSAGE);
			theStringBuilder.append(net.violet.common.StringShop.POINT);
			theStringBuilder.append(StringShop.NABCAST);
			theStringBuilder.append(StringShop.IS_NULL);
		}

		final long theResult;

		if (inLimit > 0) {
			final List<MessageReceived> theMessages = findAll(new String[] { StringShop.MESSAGE }, theStringBuilder.toString(), theValues, null, 0, inLimit);
			theResult = theMessages.size();
		} else {
			theResult = count(new String[] { StringShop.MESSAGE }, theStringBuilder.toString(), theValues, null);
		}

		return theResult;
	}

	public MessageReceived findMessageReceivedByMessageId(long inId) {
		return find("message_id = ?", Collections.singletonList((Object) inId));
	}

	public List<MessageReceived> findMessageReceivedByRecipientOrSender(Messenger inMessenger) {
		return (inMessenger != null) ? findAll("sender_id = ? OR recipient_id = ?", Arrays.asList(new Object[] { inMessenger.getId(), inMessenger.getId() }), null) : (List<MessageReceived>) (Object) Collections.emptyList();
	}

	public MessageReceived create(Message inMessage, Messenger inReceiver, Messenger inSender) {
		return new MessageReceivedImpl(inMessage, inReceiver, inSender);
	}

	public List<MessageReceived> findMessageReceivedByRecipient(Messenger inMessenger, int inIndex, int inGetCount) {
		return (inMessenger != null) ? findAll("recipient_id = ?", Arrays.asList(new Object[] { inMessenger.getId() }), null, inIndex, inGetCount) : (List<MessageReceived>) (Object) Collections.emptyList();
	}
}
