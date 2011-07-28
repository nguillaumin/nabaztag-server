package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface ScheduledMessage extends Record<ScheduledMessage> {

	String getPacket();

	Long getMessage_id();

}
