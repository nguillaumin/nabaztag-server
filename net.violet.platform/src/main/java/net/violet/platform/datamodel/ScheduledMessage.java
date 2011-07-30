package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Store messages to be sent in the future.
 * Used for Nabaztag V2.
 * 
 * Each message is deleted from here once sent.
 * 
 *
 */
public interface ScheduledMessage extends Record<ScheduledMessage> {

	String getPacket();

	Long getMessage_id();

}
