package net.violet.platform.api.actions.storecities;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidDataException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.store.StoreCityInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.dataobjects.StoreCityData;
import net.violet.platform.util.Constantes;

public class GetStoreCities extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, InvalidDataException {

		final String theCountryCode = inParam.getMainParamAsString();
		CountryData theCountry = null;
		theCountry = CountryData.findByCode(theCountryCode);
		if (theCountry == null) {
			throw new InvalidDataException(APIErrorMessage.NO_SUCH_COUNTRY);
		}
		final List<StoreCityInformationMap> theCityInformationList = new LinkedList<StoreCityInformationMap>();
		final List<StoreCityData> aux = StoreCityData.getByCountry(theCountry);
		for (final StoreCityData inCity : aux) {
			theCityInformationList.add(new StoreCityInformationMap(inCity));
		}
		return theCityInformationList;
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return true;
	}

	/**
	 * User informations may be cached one day
	 * 
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.GET;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
