package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class SchedulingTypeImpl extends ObjectRecord<SchedulingType, SchedulingTypeImpl> implements SchedulingType {

	protected long id;
	protected String label;
	protected Timestamp covered_for;

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<SchedulingTypeImpl> SPECIFICATION = new SQLObjectSpecification<SchedulingTypeImpl>("scheduling_type", SchedulingTypeImpl.class, new SQLKey("id"));

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected SchedulingTypeImpl(long id) throws SQLException {
		init(id);
	}

	/**
	 * Constructeur par défaut.
	 */
	protected SchedulingTypeImpl() {
	}

	@Override
	public SQLObjectSpecification<SchedulingTypeImpl> getSpecification() {
		return SchedulingTypeImpl.SPECIFICATION;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public Timestamp getCoveredFor() {
		return this.covered_for;
	}

	public void setCoveredFor(Timestamp inTimestamp) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setCovered_for(theUpdateMap, inTimestamp);
		update(theUpdateMap);
	}

	private void setCovered_for(Map<String, Object> inUpdateMap, Timestamp inTimestamp) {
		if ((inTimestamp != null) && !inTimestamp.equals(this.covered_for)) {
			this.covered_for = inTimestamp;
			inUpdateMap.put("covered_for", inTimestamp);
		}
	}

}
