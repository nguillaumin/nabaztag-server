package net.violet.vadmin.actions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.continents.GetContinentCountries;
import net.violet.platform.api.actions.continents.GetContinents;
import net.violet.platform.api.actions.storecities.GetStoreCities;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.CountryInformationMap;
import net.violet.platform.api.maps.store.StoreCityInformationMap;
import net.violet.vadmin.forms.AdminLocationForm;
import net.violet.vadmin.objects.data.ContinentData;
import net.violet.vadmin.objects.data.CountryData;
import net.violet.vadmin.objects.data.StoreCityData;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdminLocationAction extends AdminAction {

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		AdminLocationForm myForm = (AdminLocationForm) form;
		myForm.setContinents(getContinents());

		saveErrors(request, myForm.getErrors());
		return mapping.getInputForward();

	}

	public ActionForward displayCountry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		AdminLocationForm myForm = (AdminLocationForm) form;
		String theContinentSelected = myForm.getContinent();

		myForm.setCountries(getCountries(theContinentSelected));
		myForm.setContinent(theContinentSelected);
		return load(mapping, myForm, request, response);
	}

	protected List<CountryData> getCountries(String continent) {

		final List<CountryData> theCountries = new LinkedList<CountryData>();
		final Action theAction = new GetContinentCountries();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", continent);
		try {
			for (CountryInformationMap aMap : (List<CountryInformationMap>) Admin.processRequest(theAction, theParams)) {
				theCountries.add(GetCountryData(aMap));
			}
		} catch (APIException e) {}
		return theCountries;
	}

	protected List<StoreCityData> getCities(String country) {
		final List<StoreCityData> cities = new LinkedList<StoreCityData>();

		final Action theAction = new GetStoreCities();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", country);
		try {
			for (StoreCityInformationMap aCity : (List<StoreCityInformationMap>) Admin.processRequest(theAction, theParams)) {
				cities.add(GetStoreCityData(aCity));
			}
		} catch (APIException e) {}
		return cities;
	}

	public ActionForward displayCities(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		AdminLocationForm myForm = (AdminLocationForm) form;
		String theCountryCodeSelected = myForm.getCountry();
		String theContinentSelected = myForm.getContinent();

		myForm.setContinent(theContinentSelected);
		myForm.setCountry(theCountryCodeSelected);
		myForm.setCountries(getCountries(theContinentSelected));
		myForm.setCities(getCities(theCountryCodeSelected));
		return load(mapping, myForm, request, response);
	}

	protected List<ContinentData> getContinents() {

		final List<ContinentData> theContinents = new LinkedList<ContinentData>();
		final Action theAction = new GetContinents();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		try {
			for (String aContinent : (List<String>) Admin.processRequest(theAction, theParams)) {
				ContinentData theContinentData = new ContinentData();
				theContinentData.setName(aContinent);
				theContinents.add(theContinentData);
			}
		} catch (APIException e) {}
		return theContinents;
	}

	private CountryData GetCountryData(CountryInformationMap inCountryMap) {
		CountryData theCountryData = new CountryData();
		if (inCountryMap != null) {
			theCountryData.setIsoCode((String) inCountryMap.get(CountryInformationMap.ISO));
			theCountryData.setName((String) inCountryMap.get(CountryInformationMap.NAME));
		}
		return theCountryData;
	}

	private StoreCityData GetStoreCityData(StoreCityInformationMap inStoreCityMap) {
		StoreCityData theStoreCityData = new StoreCityData();
		if (inStoreCityMap != null) {
			theStoreCityData.setName((String) inStoreCityMap.get(CountryInformationMap.NAME));
		}
		return theStoreCityData;
	}

}
