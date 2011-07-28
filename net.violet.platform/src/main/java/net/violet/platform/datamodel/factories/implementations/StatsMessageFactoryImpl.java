package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.StatsMessage;
import net.violet.platform.datamodel.StatsMessageImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.StatsMessageFactory;

import org.apache.log4j.Logger;

public class StatsMessageFactoryImpl extends RecordFactoryImpl<StatsMessage, StatsMessageImpl> implements StatsMessageFactory {

	private static final Logger LOGGER = Logger.getLogger(StatsMessageFactoryImpl.class);

	StatsMessageFactoryImpl() {
		super(StatsMessageImpl.SPECIFICATION);
	}

	public void insert(User user, VObject object, String canal) {
		try {
			final StatsMessageImpl theRecord = new StatsMessageImpl(user, object, canal);
			theRecord.insertRecord(StatsMessageImpl.NEW_COLUMNS);
		} catch (final SQLException e) {
			StatsMessageFactoryImpl.LOGGER.fatal(e, e);
		}
	}
}
