package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.MessageCounter;
import net.violet.platform.datamodel.MessageCounterImpl;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.MessageCounterFactory;

import org.apache.log4j.Logger;

public class MessageCounterFactoryImpl extends RecordFactoryImpl<MessageCounter, MessageCounterImpl> implements MessageCounterFactory {

	private static final Logger LOGGER = Logger.getLogger(MessageCounterFactoryImpl.class);

	public MessageCounterFactoryImpl() {
		super(MessageCounterImpl.SPECIFICATION);
	}

	public MessageCounter get(long id) {
		MessageCounter theMessageCounter = find(id);

		if (theMessageCounter == null) {
			final Messenger theMessenger = Factories.MESSENGER.find(id);
			final int count = (int) Factories.MESSAGE_RECEIVED.countReceivedMessagesByMessenger(theMessenger, MessageReceived.MESSAGE_RECEIVED_STATES.INBOX, true);
			final int state;
			if (count == 0) {
				state = 0;
			} else if (count > 1) {
				state = 2;
			} else {
				state = 1;
			}

			try {
				return new MessageCounterImpl(id, state, count);
			} catch (final SQLException e) {
				theMessageCounter = find(id);

				if (theMessageCounter == null) {
					MessageCounterFactoryImpl.LOGGER.fatal(e, e);
				}
			}
		}
		return theMessageCounter;
	}

	public Integer countAmountInboxMessagesByMessenger(Messenger inMessenger, boolean displayNabcast) {
		final MessageCounter theMessageCounter = get(inMessenger.getId());

		return displayNabcast ? theMessageCounter.getW_nabcast() : theMessageCounter.getW_o_nabcast();
	}

	public RABBIT_STATE getRabbitStateByRecipient(Messenger inRecipient) {
		final MessageCounter theMessageCounter = get(inRecipient.getId());
		final RABBIT_STATE theRabbitState;

		if (theMessageCounter.getRabbit_state() == RABBIT_STATE.INVALIDE) {
			final long before = System.currentTimeMillis();
			theRabbitState = inRecipient.computeRabbitState();
			theMessageCounter.setRabbit_state(theRabbitState);
			final long after = System.currentTimeMillis();
			if (after - before > 1000) {
				MessageCounterFactoryImpl.LOGGER.info("TIME LEAK EventMng.countInboxMessages_2 " + (after - before) + net.violet.common.StringShop.SPACE + inRecipient.getObject().getObject_serial());
			}
		} else {
			theRabbitState = theMessageCounter.getRabbit_state();
		}

		return theRabbitState;
	}
}
