package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.platform.datamodel.StoreCity;

public interface StoreCityFactory {

	List<StoreCity> getCountryStoreCities(Long countryId);

	StoreCity findByCityAndCountry(String inCityName, Long countryId);

	StoreCity create(String inCity, Long inCountryId);

	List<StoreCity> getByCountry(long theCountryId);

}
