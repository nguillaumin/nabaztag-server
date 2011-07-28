package net.violet.platform.datamodel.factories.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.City;
import net.violet.platform.datamodel.CityImpl;
import net.violet.platform.datamodel.factories.CityFactory;

public class CityFactoryImpl extends RecordFactoryImpl<City, CityImpl> implements CityFactory {

	CityFactoryImpl() {
		super(CityImpl.SPECIFICATION);
	}

	public City getByCityAndCountry(String annu_country, String annu_city) {
		final String condition = "city_country_code = ? AND city_name = ?";
		return find(condition, Arrays.asList(new Object[] { annu_country, annu_city }));
	}

	public List<String> findAllCities(String countryCode) {
		final String condition = "city_country_code = ?";
		final List<City> listCities = findAllDistinct(null, condition, Arrays.asList(new Object[] { countryCode }), "city_name ASC");
		final List<String> result = new ArrayList<String>();
		for (final City cityName : listCities) {
			result.add(cityName.getName());
		}
		return result;
	}
}
