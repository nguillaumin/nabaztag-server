package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.City;

public class CityMock extends AbstractMockRecord<City, CityMock> implements City {

	private String city_country_code;
	private String city_name;

	protected CityMock(long inId) {
		super(inId);
		this.city_country_code = "VD";
		this.city_name = "Vide";
	}

	public CityMock(long inId, String codePays, String nomVille) {
		super(inId);
		this.city_country_code = codePays;
		this.city_name = nomVille;
	}

	public String getCountryCode() {
		return this.city_country_code;
	}

	public String getName() {
		return this.city_name;
	}

	public void setAllInformation(String cityCountryCode, String cityName) {
		this.city_country_code = cityCountryCode;
		this.city_name = cityName;

	}
}
