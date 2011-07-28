package net.violet.platform.api.actions.stores;

import java.util.Map;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidDataException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.maps.store.StoreInformationMap;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.StoreData;

public class Create extends AbstractAction {

	//private static final Logger LOGGER = Logger.getLogger(Update.class);

	public static final String STORE_PARAM = "store";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, InvalidDataException, NoSuchFileException {

		final Map<String, String> theStoreMap = inParam.getMap(Create.STORE_PARAM, true);

		final String theName = theStoreMap.get(StoreInformationMap.NAME);
		final String theType = theStoreMap.get(StoreInformationMap.TYPE);
		final String thePictureAPIID = theStoreMap.get(StoreInformationMap.PICTURE);
		final String theAddress = theStoreMap.get(StoreInformationMap.ADDRESS);
		final String theZipcode = theStoreMap.get(StoreInformationMap.ZIPCODE);
		final String theCity = theStoreMap.get(StoreInformationMap.CITY);
		final String theCountryCode = theStoreMap.get(StoreInformationMap.COUNTRY);
		final String theStatus = theStoreMap.get(StoreInformationMap.STATUS);
		final String theURL = theStoreMap.get(StoreInformationMap.URL);
		final String theRank = theStoreMap.get(StoreInformationMap.RANK);
		final String theComment = theStoreMap.get(StoreInformationMap.COMMENT);

		FilesData thePicture = null;
		if (thePictureAPIID != null) {
			thePicture = FilesData.getFilesData(thePictureAPIID, inParam.getCallerAPIKey());
		}

		Long theRankValue = 0L;
		if ((theRank != null) && theRank.matches("^([0-9]+)$")) {
			theRankValue = Long.parseLong(theRank);
		}
		CountryData theCountry = null;
		theCountry = CountryData.findByCode(theCountryCode);
		if (theCountry == null) {
			throw new InvalidDataException(APIErrorMessage.NO_SUCH_COUNTRY);
		}

		if (theCity != null) {
			if (theCountryCode == null) {
				throw new InvalidDataException(APIErrorMessage.MISSING_COUNTRY);
			}
		}

		final StoreData theStore = StoreData.create(theName, theType, thePicture, theAddress, theZipcode, theCity, theCountry, theStatus, theURL, theRankValue, theComment);

		return new StoreInformationMap(inParam.getCaller(), theStore);
	}

	public long getExpirationTime() {
		return 0L;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

}
