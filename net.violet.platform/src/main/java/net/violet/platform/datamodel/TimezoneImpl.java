package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.TimeZone;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class TimezoneImpl extends ObjectRecord<Timezone, TimezoneImpl> implements Timezone {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<TimezoneImpl> SPECIFICATION = new SQLObjectSpecification<TimezoneImpl>("timezone", TimezoneImpl.class, new SQLKey("timezone_id"));

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected TimezoneImpl(long id) throws SQLException {
		init(id);
	}

	protected TimezoneImpl() {
		// This space for rent.
	}

	/**
	 * Champs de l'enregistrement.
	 */

	protected long timezone_id;
	protected String timezone_javaId;
	protected String timezone_name;

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Timezone#getId()
	 */
	@Override
	public Long getId() {
		return getTimezone_id();
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Timezone#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<TimezoneImpl> getSpecification() {
		return TimezoneImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Timezone#getTimezone_id()
	 */
	public final long getTimezone_id() {
		return this.timezone_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Timezone#getTimezone_javaId()
	 */
	public final String getTimezone_javaId() {
		return this.timezone_javaId;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Timezone#getTimezone_name()
	 */
	public final String getTimezone_name() {
		return this.timezone_name;
	}

	public TimeZone getJavaTimeZone() {
		return TimeZone.getTimeZone(this.timezone_javaId);
	}
}
