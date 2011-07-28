package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Country;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Store;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.Store.STORE_STATUS;
import net.violet.platform.datamodel.Store.STORE_TYPE;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class StoreData extends APIData<Store> {

	private static final Logger LOGGER = Logger.getLogger(StoreData.class);

	protected StoreData(Store inStore) {
		super(inStore);
	}

	public static StoreData getData(Store inStore) {
		try {
			return RecordData.getData(inStore, StoreData.class, Store.class);
		} catch (final InstantiationException e) {
			StoreData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			StoreData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			StoreData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			StoreData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		final Store theStore = getRecord();
		if (theStore != null) {
			return theStore.getId();
		}

		return 0;
	}

	public String getName() {
		final Store theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getName();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getType() {
		final Store theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getType();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getCityName() {
		final Store theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getStoreCity() != null)) {
			return theRecord.getStoreCity().getName();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getCountryName() {

		final Store theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getStoreCity() != null) && (theRecord.getStoreCity().getCountry() != null)) {
			return theRecord.getStoreCity().getCountry().getName();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static List<StoreData> getStoresByCountry(String countryCode) {
		final List<StoreData> result = new ArrayList<StoreData>();
		for (final Store inStore : Factories.STORE.getStoreByCountry(countryCode)) {
			result.add(StoreData.getData(inStore));
		}
		return result;
	}

	public String getPicturePath() {
		final Store theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getPicture() != null)) {
			return theRecord.getPicture().getPath();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getAddress() {
		final Store theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getAddress();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getZipCode() {
		final Store theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getZipCode();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getStatus() {
		final Store theRecord = getRecord();
		if (theRecord != null) {
			return getRecord().getStatus();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getUrl() {
		final Store theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getUrl();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public Long getRank() {
		final Store theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getRank();
		}
		return 0L;
	}

	public String getComment() {
		final Store theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getComment();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static StoreData findByAPIId(String inAPIId, String inAPIKey) {
		StoreData theResult = null;
		final long theId = APIData.fromObjectID(inAPIId, ObjectClass.STORE, inAPIKey);
		if (theId != 0) {
			final Store theStore = Factories.STORE.find(theId);
			if (theStore != null) {
				theResult = StoreData.getData(theStore);
			}
		}
		return theResult;
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.STORE;
	}

	public void update(String inName, String inType, FilesData inPicture, String inAddress, String inZipcode, String inCity, CountryData inCountry, String inStatus, String inURL, long inRank, String inComment) {
		final Store theStore = this.getRecord();

		Files thePicture = null;
		if (inPicture != null) {
			thePicture = inPicture.getReference();
		}

		STORE_TYPE theType = null;
		if (inType != null) {
			theType = STORE_TYPE.valueOf(inType);
		}

		STORE_STATUS theStatus = null;
		if (inStatus != null) {
			theStatus = STORE_STATUS.valueOf(inStatus);
		}

		StoreCity theCity = null;
		final Country theCountry;

		if (inCountry != null) {
			theCountry = inCountry.getRecord();

			if (!net.violet.common.StringShop.EMPTY_STRING.equals(inCity)) {

				final StoreCityData theCityData = StoreCityData.findByCityAndCountry(inCity, theCountry);
				if (theCityData != null) {
					theCity = theCityData.getReference();
				} else {
					theCity = Factories.STORE_CITY.create(inCity, theCountry.getId());
				}
			}
		}

		if (theStore != null) {
			theStore.update(inName, theType, thePicture, inAddress, inZipcode, theCity, theStatus, inURL, inRank, inComment);
		}
	}

	public static StoreData create(String inName, String inType, FilesData inPictureData, String inAddress, String inZipcode, String inCity, CountryData inCountry, String inStatus, String inURL, Long inRankValue, String inComment) {
		Files thePicture = null;
		if (inPictureData != null) {
			thePicture = inPictureData.getReference();
		}
		STORE_TYPE theType = null;
		if (inType != null) {
			theType = STORE_TYPE.valueOf(inType);
		}
		STORE_STATUS theStatus = null;
		if (inStatus != null) {
			theStatus = STORE_STATUS.valueOf(inStatus);
		}

		final Country theCountry = inCountry.getRecord();
		StoreCity theStoreCity = null;
		final StoreCityData theCityData = StoreCityData.findByCityAndCountry(inCity, theCountry);
		theStoreCity = theCityData.getReference();
		if (theStoreCity == null) {
			theStoreCity = Factories.STORE_CITY.create(inCity, theCountry.getId());
		}

		final Store theStore = Factories.STORE.create(inName, theType, thePicture, inAddress, inZipcode, theStoreCity, theStatus, inURL, inRankValue, inComment);
		if (theStore != null) {
			return StoreData.getData(theStore);
		}
		return StoreData.getData(null);
	}
}
