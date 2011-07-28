package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.City;
import net.violet.platform.datamodel.factories.CityFactory;
import net.violet.platform.datamodel.mock.CityMock;

public class CityFactoryMock extends RecordFactoryMock<City, CityMock> implements CityFactory {

	public CityFactoryMock() {
		super(CityMock.class);
	}

	public City getByCityAndCountry(String annu_country, String annu_city) {
		for (final City c : findAll()) {
			if (c.getCountryCode().equals(annu_country) && c.getName().equals(annu_city)) {
				return c;
			}
		}
		return null;
	}

	public List<String> findAllCities(String countryCode) {
		final List<String> result = new ArrayList<String>();
		for (final City c : findAll()) {
			if (c.getCountryCode().equals(countryCode)) {
				result.add(c.getName());
			}
		}
		Collections.sort(result, new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		return result;
	}

	/*
	 * public City findByCode(String countryCode) { for( City p :
	 * mFactory.findAllMapped().values() ){ if(
	 * p.getCity_code().equals(countryCode) ) return p; } return null; }
	 */
}
