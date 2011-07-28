package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.MessageCounter;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;

public interface MessageCounterFactory extends RecordFactory<MessageCounter> {

	/**
	 * Finds a {@link MessageCounter} from its {@link Messenger}'s id and
	 * creates it if it does not exist
	 * 
	 * @param id
	 * @return
	 */
	MessageCounter get(long id);

	Integer countAmountInboxMessagesByMessenger(Messenger inMessenger, boolean displayNabcast);

	RABBIT_STATE getRabbitStateByRecipient(Messenger inRecipient);

}
