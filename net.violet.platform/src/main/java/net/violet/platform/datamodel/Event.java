package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface Event extends Record<Event> {

	int getEvent_obj();

	void setObject(int object);

	VObject getEventObject();
}
