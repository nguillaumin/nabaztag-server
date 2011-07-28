/**
 * 
 */
package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Event;
import net.violet.platform.datamodel.VObject;

/**
 * @author slorg1
 */
public class EventMock extends AbstractMockRecord<Event, EventMock> implements Event {

	protected EventMock(long inId) {
		super(inId);
	}

	public VObject getEventObject() {
		throw new UnsupportedOperationException();
	}

	public long getEvent_creation() {
		throw new UnsupportedOperationException();
	}

	public long getEvent_id() {
		throw new UnsupportedOperationException();
	}

	public int getEvent_obj() {
		throw new UnsupportedOperationException();
	}

	public int getEvent_purge() {
		throw new UnsupportedOperationException();
	}

	public void setObject(int object) {
		throw new UnsupportedOperationException();
	}
}
