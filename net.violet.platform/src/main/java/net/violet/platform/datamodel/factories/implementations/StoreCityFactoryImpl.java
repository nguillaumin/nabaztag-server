package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.StoreCityImpl;
import net.violet.platform.datamodel.factories.StoreCityFactory;

import org.apache.log4j.Logger;

public class StoreCityFactoryImpl extends RecordFactoryImpl<StoreCity, StoreCityImpl> implements StoreCityFactory {


	private static final Logger LOGGER = Logger.getLogger(StoreCityFactoryImpl.class);

	StoreCityFactoryImpl() {
		super(StoreCityImpl.SPECIFICATION);
	}

	public List<StoreCity> getCountryStoreCities(Long country_id) {
		final String condition = "pays_id = ?";
		final String orderBy = "name";
		return findAll(condition, Arrays.asList(new Object[] { country_id }), orderBy);
	}

	public StoreCity findByCityAndCountry(String inCityName, Long inCountryId) {
		final String theCondition = "name = ? and pays_id = ?";
		final String inOrderBy = "id";
		/*
		 * return the StoreCity row who has the most smaller id  from among the set of the same StoreCity (the same name and country, but not id) rows; 
		 */
		final List<StoreCity> result = findAll(theCondition, Arrays.asList(new Object[] { inCityName, inCountryId }), inOrderBy);
		//if (result != null) {
		if (result.size() > 0) {
			return result.get(0);
		}
		//}
		return null;
	}

	public List<StoreCity> getByCountry(long inCountryId) {
		final String theCondition = "pays_id = ?";
		final String inOrderBy = "id";
		final List<StoreCity> result = findAll(theCondition, Arrays.asList(new Object[] { inCountryId }), inOrderBy);
		return result;
	}

	public StoreCity create(String inCity, Long inCountryId) {
		try {
			return new StoreCityImpl(inCity, inCountryId);
		} catch (final SQLException e) {
			StoreCityFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}
}
