package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Country;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.factories.Factories;

public class StoreCityMock extends AbstractMockRecord<StoreCity, StoreCityMock> implements StoreCity {

	/**
	 * Champs de l'enregistrement.
	 */

	private String name = "Store Vide";
	private Country country = null;

	public StoreCityMock(long inId) {
		super(inId);
	}

	public StoreCityMock(long inId, String storeName) {
		super(inId);
		this.name = storeName;
	}

	public StoreCityMock(long inId, String inStoreCityName, String inCountryCode) {
		super(inId);
		this.name = inStoreCityName;
		this.country = Factories.COUNTRIES.findByCode(inCountryCode);
	}

	public StoreCityMock(long inId, String storeName, Long countryId) {
		super(inId);
		this.name = storeName;
		this.country = Factories.COUNTRIES.find(countryId);
	}

	public StoreCityMock(long inId, String storeName, String countryCode, String countryName) {
		super(inId);
		this.name = storeName;
		this.country = new CountryMock(inId, countryCode, countryName);
	}

	public String getName() {
		return this.name;
	}

	public Country getCountry() {
		return this.country;
	}
}
