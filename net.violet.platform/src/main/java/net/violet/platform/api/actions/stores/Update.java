package net.violet.platform.api.actions.stores;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidDataException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.store.StoreInformationMap;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.StoreData;

public class Update extends AbstractAction {

	//private static final Logger LOGGER = Logger.getLogger(Update.class);

	public static final String STORE_PARAM = "store";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, InvalidDataException, NoSuchFileException {

		final String theAPIId = inParam.getMainParamAsString();
		final PojoMap theStoreMap = inParam.getPojoMap(Update.STORE_PARAM, true);

		final String theName = theStoreMap.getString(StoreInformationMap.NAME);
		final String theType = theStoreMap.getString(StoreInformationMap.TYPE);
		final String thePictureAPIID = theStoreMap.getString(StoreInformationMap.PICTURE);// /
		final String theAddress = theStoreMap.getString(StoreInformationMap.ADDRESS);
		final String theZipcode = theStoreMap.getString(StoreInformationMap.ZIPCODE);
		final String theCity = theStoreMap.getString(StoreInformationMap.CITY);
		final String theCountryCode = theStoreMap.getString(StoreInformationMap.COUNTRY);
		final String theStatus = theStoreMap.getString(StoreInformationMap.STATUS);
		final String theURL = theStoreMap.getString(StoreInformationMap.URL);
		final String theRank = theStoreMap.getString(StoreInformationMap.RANK);
		final String theComment = theStoreMap.getString(StoreInformationMap.COMMENT);

		FilesData thePicture = null;
		if (thePictureAPIID != null) {
			thePicture = FilesData.getFilesData(thePictureAPIID, inParam.getCallerAPIKey());
		}
		final StoreData theStore = StoreData.findByAPIId(theAPIId, inParam.getCallerAPIKey());
		Long theRankValue = 0L;
		if ((theRank != null) && theRank.matches("^([0-9]+)$")) {
			theRankValue = Long.parseLong(theRank);
		}

		CountryData theCountry = null;
		if (theCity != null) {
			if (theCountryCode == null) {
				throw new InvalidDataException(APIErrorMessage.MISSING_COUNTRY);
			}
			theCountry = CountryData.findByCode(theCountryCode);
			if (theCountry == null) {
				throw new InvalidDataException(APIErrorMessage.NO_SUCH_COUNTRY);
			}
		}

		theStore.update(theName, theType, thePicture, theAddress, theZipcode, theCity, theCountry, theStatus, theURL, theRankValue, theComment);

		return new StoreInformationMap(inParam.getCaller(), theStore);
	}

	public long getExpirationTime() {
		return 0L;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

}
