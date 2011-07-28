package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.MessageSentImpl;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.factories.MessageSentFactory;

public class MessageSentFactoryImpl extends RecordFactoryImpl<MessageSent, MessageSentImpl> implements MessageSentFactory {

	MessageSentFactoryImpl() {
		super(MessageSentImpl.SPECIFICATION);
	}

	public MessageSent findMessageSentByMessageId(long inId) {
		return find("message_id = ?", Collections.singletonList((Object) inId));
	}

	public List<MessageSent> findMessageSentBySenderOrRecipient(Messenger inMessenger) {
		return (inMessenger != null) ? findAll("sender_id = ? OR recipient_id = ?", Arrays.asList(new Object[] { inMessenger.getId(), inMessenger.getId() }), null) : (List<MessageSent>) (Object) Collections.emptyList();
	}

	public MessageSent create(Message inMessage, Messenger inReceiver, Messenger inSender) {
		return new MessageSentImpl(inMessage, inReceiver, inSender);
	}

	public List<MessageSent> findMessageSentBySender(Messenger inMessenger, int inIndex, int inGetCount) {
		return (inMessenger != null) ? findAll("sender_id = ?", Arrays.asList(new Object[] { inMessenger.getId() }), null, inIndex, inGetCount) : (List<MessageSent>) (Object) Collections.emptyList();
	}

}
