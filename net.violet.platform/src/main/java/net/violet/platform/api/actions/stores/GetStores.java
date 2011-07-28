package net.violet.platform.api.actions.stores;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.store.StoreInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.dataobjects.StoreData;

public class GetStores extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String theCountryCode = inParam.getMainParamAsString();
		final CountryData theCountry = CountryData.findByCode(theCountryCode);
		if (theCountry == null) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_COUNTRY_CODE, theCountryCode);
		}

		final List<StoreInformationMap> storeMapsList = new ArrayList<StoreInformationMap>();

		for (final StoreData inStoreData : StoreData.getStoresByCountry(theCountryCode)) {
			storeMapsList.add(new StoreInformationMap(inParam.getCaller(), inStoreData));
		}
		return storeMapsList;
	}

	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}
}
