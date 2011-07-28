package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.associations.AssociationRecord;
import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.Messenger;

public interface MessageSentFactory extends RecordFactory<MessageSent> {

	/**
	 * Finds MessageSent by message id
	 * 
	 * @param inId
	 * @return
	 */
	MessageSent findMessageSentByMessageId(long inId);

	/**
	 * Creates a new {@link MessageSent}
	 * 
	 * BEWARE: does not insert anything if not called in an {@link AssociationRecord} context
	 * 
	 * @param inMessage
	 * @param inReceiver
	 * @param inSender
	 * 
	 * @return the created object
	 */
	MessageSent create(Message inMessage, Messenger inReceiver, Messenger inSender);

	List<MessageSent> findMessageSentBySenderOrRecipient(Messenger inMessenger);

	List<MessageSent> findMessageSentBySender(Messenger inMessenger, int inIndex, int inGetCount);
}
