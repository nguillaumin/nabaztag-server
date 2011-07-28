package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Event;
import net.violet.platform.datamodel.EventImpl;
import net.violet.platform.datamodel.factories.EventFactory;

public class EventFactoryImpl extends RecordFactoryImpl<Event, EventImpl> implements EventFactory {

	public EventFactoryImpl() {
		super(EventImpl.SPECIFICATION);
	}

	public Long insert(int obj, int creation, int purge, int mode) throws SQLException {
		final EventImpl theRecord = new EventImpl(obj, creation, purge, mode);
		return theRecord.insertRecord(EventImpl.NEW_COLUMNS);
	}

}
