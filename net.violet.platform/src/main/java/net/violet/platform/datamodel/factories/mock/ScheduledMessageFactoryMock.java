package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.ScheduledMessage;
import net.violet.platform.datamodel.factories.ScheduledMessageFactory;
import net.violet.platform.datamodel.mock.ScheduledMessageMock;

public class ScheduledMessageFactoryMock extends RecordFactoryMock<ScheduledMessage, ScheduledMessageMock> implements ScheduledMessageFactory {

	ScheduledMessageFactoryMock() {
		super(ScheduledMessageMock.class);
	}

	public ScheduledMessage findByMessageId(Long inMessageId) {
		for (final ScheduledMessage theMessage : findAll()) {
			if (theMessage.getId() == inMessageId) {
				return theMessage;
			}
		}
		return null;
	}

}
