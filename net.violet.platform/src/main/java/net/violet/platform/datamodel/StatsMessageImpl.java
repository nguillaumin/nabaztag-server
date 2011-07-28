package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;

import net.violet.db.connector.Connector.ConnectionMode;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public class StatsMessageImpl extends ObjectRecord<StatsMessage, StatsMessageImpl> implements StatsMessage {

	private static final Logger LOGGER = Logger.getLogger(StatsMessageImpl.class);

	public static final SQLObjectSpecification<StatsMessageImpl> SPECIFICATION = new SQLObjectSpecification<StatsMessageImpl>("stats_message", StatsMessageImpl.class, new SQLKey("id"), false, ConnectionMode.STATS);

	protected StatsMessageImpl(long id) throws SQLException {
		super();
		init(id);
	}

	protected StatsMessageImpl() {
		super();
	}

	protected long id;
	protected long user_id;
	protected long object_id;
	protected Timestamp time;
	protected String canal;
	protected long hardware_id;

	public static final String[] NEW_COLUMNS = new String[] { "user_id", "object_id", "canal", "hardware_id" };

	public StatsMessageImpl(User user, VObject object, String canal) {
		this.user_id = user.getId();
		this.object_id = object.getId();
		this.canal = canal;
		this.hardware_id = object.getHardware().getId();
	}

	public static StatsMessage find(long id) {
		StatsMessage result = null;
		try {
			result = AbstractSQLRecord.findByKey(StatsMessageImpl.SPECIFICATION, StatsMessageImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			StatsMessageImpl.LOGGER.fatal(se, se);
		}
		return result;
	}

	@Override
	public SQLObjectSpecification<StatsMessageImpl> getSpecification() {
		return StatsMessageImpl.SPECIFICATION;
	}
}
