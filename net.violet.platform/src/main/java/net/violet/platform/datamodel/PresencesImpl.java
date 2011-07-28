package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.violet.db.connector.Connector;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class PresencesImpl extends ObjectRecord<Presences, PresencesImpl> implements Presences {

	public static final SQLObjectSpecification<PresencesImpl> SPECIFICATION = new SQLObjectSpecification<PresencesImpl>("presence", PresencesImpl.class, new SQLKey("username", "resource"), false, Connector.ConnectionMode.EJABBERD);

	protected String username;
	protected String resource;

	/**
	 * Constructeur par défaut.
	 */
	protected PresencesImpl() {
		// This space for rent.
	}

	@Override
	public SQLObjectSpecification<PresencesImpl> getSpecification() {
		return PresencesImpl.SPECIFICATION;
	}

	public String getResource() {
		return this.resource;
	}

	public String getUsername() {
		return this.username;
	}

	public static List<Presences> findByUsername(String inUsername) throws SQLException {
		return AbstractSQLRecord.findAll(PresencesImpl.SPECIFICATION, "username = ?", Arrays.asList((Object) inUsername));
	}
}
