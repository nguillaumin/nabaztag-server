package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.factories.StoreCityFactory;
import net.violet.platform.datamodel.mock.StoreCityMock;

public class StoreCityFactoryMock extends RecordFactoryMock<StoreCity, StoreCityMock> implements StoreCityFactory {

	//private static final Logger LOGGER = Logger.getLogger(StoreCityFactoryMock.class);

	StoreCityFactoryMock() {
		super(StoreCityMock.class);
	}

	public List<StoreCity> getCountryStoreCities(Long countryId) {
		final List<StoreCity> result = new LinkedList<StoreCity>();
		for (final StoreCity inCity : findAll()) {
			if (inCity.getCountry().getId().equals(countryId)) {
				result.add(inCity);
			}
		}
		return result;
	}

	public StoreCity findByCityAndCountry(String inCityName, Long inCountryId) {
		for (final StoreCity inCity : findAll()) {
			if (inCity.getName().equals(inCityName) && inCity.getCountry().getId().equals(inCountryId)) {
				return inCity;
			}
		}
		return null;
	}

	public List<StoreCity> getByCountry(long inCountryId) {
		final List<StoreCity> result = new LinkedList<StoreCity>();
		for (final StoreCity inCity : findAll()) {
			if (inCity.getCountry().getId().equals(inCountryId)) {
				result.add(inCity);
			}
		}
		return result;
	}

	public StoreCity create(String inCity, String inCountryCode) {
		final StoreCity theStoreCity = new StoreCityMock(0L, inCity, inCountryCode);
		return theStoreCity;
	}

	public StoreCity create(String inCity, Long inCountryId) {
		final StoreCity theStoreCity = new StoreCityMock(0L, inCity, inCountryId);
		return theStoreCity;
	}
}
