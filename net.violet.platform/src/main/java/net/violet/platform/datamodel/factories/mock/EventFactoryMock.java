package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Event;
import net.violet.platform.datamodel.factories.EventFactory;
import net.violet.platform.datamodel.mock.EventMock;

public class EventFactoryMock extends RecordFactoryMock<Event, EventMock> implements EventFactory {

	public EventFactoryMock() {
		super(EventMock.class);
	}

	public Long insert(int obj, int creation, int purge, int mode) {
		// TODO Auto-generated method stub
		return null;
	}

}
