package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.datamodel.Country;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class StoreCityData extends RecordData<StoreCity> {

	private static final Logger LOGGER = Logger.getLogger(StoreCityData.class);

	protected StoreCityData(StoreCity inCity) {
		super(inCity);
	}

	public static StoreCityData getData(StoreCity inStoreCity) {
		try {
			return RecordData.getData(inStoreCity, StoreCityData.class, StoreCity.class);
		} catch (final InstantiationException e) {
			StoreCityData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			StoreCityData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			StoreCityData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			StoreCityData.LOGGER.fatal(e, e);
		}

		return null;
	}

	public StoreCity getReference() {
		final StoreCity theRecord = getRecord();
		if (theRecord != null) {
			return theRecord;
		}
		return null;
	}

	public String getName() {
		final StoreCity theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getName();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getCountryName() {
		final StoreCity theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getCountry().getName();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public Country getCountry() {
		final StoreCity theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getCountry();
		}
		return null;
	}

	public static StoreCityData findByCityAndCountry(String inCityName, Country inCountry) {
		if (inCountry != null) {
			final long theCountryId = inCountry.getId();
			final StoreCity theStoreCity = Factories.STORE_CITY.findByCityAndCountry(inCityName, theCountryId);
			return StoreCityData.getData(theStoreCity);
		}
		return null;
	}

	public static List<StoreCityData> getByCountry(CountryData inCountry) {
		final Country theCountry = inCountry.getRecord();
		if (theCountry != null) {
			final long theCountryId = theCountry.getId();
			final List<StoreCityData> result = new LinkedList<StoreCityData>();
			final List<StoreCity> list = Factories.STORE_CITY.getByCountry(theCountryId);
			for (final StoreCity inStoreCity : list) {
				final StoreCityData aux1 = StoreCityData.getData(inStoreCity);
				if (aux1 != null) {}
				result.add(aux1);
			}
			return result;
		}
		return null;
	}
}
