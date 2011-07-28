package net.violet.platform.api.actions.continents;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchContinentException;
import net.violet.platform.api.maps.CountryInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ContinentData;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.util.Constantes;

public class GetContinentCountries extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchContinentException {

		final String continentName = inParam.getMainParamAsString();
		final ContinentData continent = ContinentData.findByName(continentName);
		if ((continent == null) || !continent.isValid()) {
			throw new NoSuchContinentException(APIErrorMessage.NO_SUCH_CONTINENT, continentName);
		}
		final List<CountryInformationMap> result = new ArrayList<CountryInformationMap>();
		for (final CountryData inCountry : CountryData.findAllByContinent(continent)) {
			result.add(new CountryInformationMap(inCountry));
		}
		return result;
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
