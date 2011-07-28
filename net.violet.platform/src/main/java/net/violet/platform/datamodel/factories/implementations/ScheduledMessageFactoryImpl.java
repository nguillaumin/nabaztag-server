package net.violet.platform.datamodel.factories.implementations;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.ScheduledMessage;
import net.violet.platform.datamodel.ScheduledMessageImpl;
import net.violet.platform.datamodel.factories.ScheduledMessageFactory;

public class ScheduledMessageFactoryImpl extends RecordFactoryImpl<ScheduledMessage, ScheduledMessageImpl> implements ScheduledMessageFactory {

	ScheduledMessageFactoryImpl() {
		super(ScheduledMessageImpl.SPECIFICATION);
	}

	public ScheduledMessage findByMessageId(Long inMessageId) {
		return findByKey(1, inMessageId);
	}

}
