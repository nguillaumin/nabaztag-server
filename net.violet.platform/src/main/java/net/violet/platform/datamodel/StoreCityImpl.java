package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNull;

public class StoreCityImpl extends ObjectRecord<StoreCity, StoreCityImpl> implements StoreCity {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<StoreCityImpl> SPECIFICATION = new SQLObjectSpecification<StoreCityImpl>("store_city", StoreCityImpl.class, new SQLKey("id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] {

	"name", "pays_id", };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected String name;
	protected long pays_id;

	private final SingleAssociationNull<StoreCity, Country, CountryImpl> mCountry;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected StoreCityImpl(long id) throws SQLException {
		init(id);
		this.mCountry = new SingleAssociationNull<StoreCity, Country, CountryImpl>(this, "pays_id", CountryImpl.SPECIFICATION, CountryImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected StoreCityImpl() {
		// This space for rent.
		this.mCountry = new SingleAssociationNull<StoreCity, Country, CountryImpl>(this, "pays_id", CountryImpl.SPECIFICATION, CountryImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 */
	public StoreCityImpl(String inCityName, long inCountryId) throws SQLException {

		this.pays_id = inCountryId;
		this.name = inCityName;
		this.mCountry = new SingleAssociationNull<StoreCity, Country, CountryImpl>(this, "pays_id", CountryImpl.SPECIFICATION, CountryImpl.SPECIFICATION.getPrimaryKey());

		init(StoreCityImpl.NEW_COLUMNS);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<StoreCityImpl> getSpecification() {
		return StoreCityImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getCity_city()
	 */
	public String getName() {
		return this.name;
	}

	public Country getCountry() {
		return this.mCountry.get(this.pays_id);
	}
}
