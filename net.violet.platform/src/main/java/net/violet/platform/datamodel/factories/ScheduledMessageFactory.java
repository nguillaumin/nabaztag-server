package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.ScheduledMessage;

public interface ScheduledMessageFactory extends RecordFactory<ScheduledMessage> {

	ScheduledMessage findByMessageId(Long inMessageId);
}
