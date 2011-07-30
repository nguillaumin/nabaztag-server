package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Used for Nabaztags v1, contains immediate messages
 * or pending ones.
 * 
 *
 */
public interface Event extends Record<Event> {

	int getEvent_obj();

	void setObject(int object);

	VObject getEventObject();
}
