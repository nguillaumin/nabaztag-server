package net.violet.platform.datamodel.factories;

import java.sql.SQLException;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Event;

public interface EventFactory extends RecordFactory<Event> {

	Long insert(int obj, int creation, int purge, int mode) throws SQLException;
}
