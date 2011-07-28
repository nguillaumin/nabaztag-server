package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.platform.datamodel.City;

public interface CityFactory {

	City getByCityAndCountry(String annu_country, String annu_city);

	List<String> findAllCities(String countryCode);

}
