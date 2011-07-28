package net.violet.platform.datamodel;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class ContinentImpl extends ObjectRecord<Continent, ContinentImpl> implements Continent {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<ContinentImpl> SPECIFICATION = new SQLObjectSpecification<ContinentImpl>("continent", ContinentImpl.class, new SQLKey("id"));

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected String name;

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected ContinentImpl() {
		// This space for rent.
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<ContinentImpl> getSpecification() {
		return ContinentImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getCity_city()
	 */
	public String getName() {
		return this.name;
	}
}
