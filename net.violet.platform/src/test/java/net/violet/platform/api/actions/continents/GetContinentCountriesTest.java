package net.violet.platform.api.actions.continents;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NoSuchContinentException;
import net.violet.platform.api.maps.CountryInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.ContinentMock;
import net.violet.platform.datamodel.mock.CountryMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class GetContinentCountriesTest extends MockTestBase {

	static long i = 0L, j = 0L;

	private List<CountryMock> creerBDMock() {

		final List<ContinentMock> continentsList = new ArrayList<ContinentMock>();
		continentsList.add(new ContinentMock(++GetContinentCountriesTest.i, "Asia"));
		continentsList.add(new ContinentMock(++GetContinentCountriesTest.i, "Europe"));
		continentsList.add(new ContinentMock(++GetContinentCountriesTest.i, "Africa"));
		continentsList.add(new ContinentMock(++GetContinentCountriesTest.i, "Oceania"));
		continentsList.add(new ContinentMock(++GetContinentCountriesTest.i, "South America"));
		continentsList.add(new ContinentMock(++GetContinentCountriesTest.i, "North America"));
		continentsList.add(new ContinentMock(++GetContinentCountriesTest.i, "Antartica"));

		final List<CountryMock> resultList = new ArrayList<CountryMock>();
		resultList.add(new CountryMock(GetContinentCountriesTest.i++, "PL", "Pologne", 2L));
		resultList.add(new CountryMock(GetContinentCountriesTest.i++, "CH", "Suisse", 2L));
		resultList.add(new CountryMock(GetContinentCountriesTest.i++, "FR", "Marseille", 2L));
		resultList.add(new CountryMock(GetContinentCountriesTest.i++, "DE", "Germany", 2L));
		resultList.add(new CountryMock(GetContinentCountriesTest.i++, "KE", "Kenya", 3L));
		resultList.add(new CountryMock(GetContinentCountriesTest.i++, "AU", "Australia", 2L));
		resultList.add(new CountryMock(GetContinentCountriesTest.i++, "DI", "Dijonya", 2L));
		resultList.add(new CountryMock(GetContinentCountriesTest.i++, "LX", "Linuxya", 2L));
		return resultList;
	}

	@Test
	public void testContinentCountries() throws APIException {
		creerBDMock();
		final Date now = new Date();
		final Action theAction = new GetContinentCountries();
		final Application theApplication = new ApplicationMock(43, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, "Africa");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof List);
		final List<CountryInformationMap> theResultAsList = (List<CountryInformationMap>) theResult;
		Assert.assertEquals(1, theResultAsList.size());
		final CountryInformationMap theCountry = theResultAsList.get(0);
		Assert.assertEquals("Kenya", theCountry.get(CountryInformationMap.NAME));
		Assert.assertEquals("KE", theCountry.get(CountryInformationMap.ISO));
		Assert.assertEquals("Africa", theCountry.get(CountryInformationMap.CONTINENT));

	}

	@Test(expected = NoSuchContinentException.class)
	public void testNoSuchContinent() throws APIException {
		creerBDMock();
		final Date now = new Date();
		final Action theAction = new GetContinentCountries();
		final Application theApplication = new ApplicationMock(43, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, "Vide");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);

	}
}
