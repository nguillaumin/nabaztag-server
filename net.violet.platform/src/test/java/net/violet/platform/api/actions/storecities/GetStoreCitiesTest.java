package net.violet.platform.api.actions.storecities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.store.StoreCityInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.CountryMock;
import net.violet.platform.datamodel.mock.StoreCityMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class GetStoreCitiesTest extends AbstractTestBase {

	static long i = 1L;

	private List<StoreCityMock> creerBDMock() {
		new CountryMock(79L, "PL", "Poland");
		new CountryMock(77L, "FR", "France");
		final List<StoreCityMock> resultList = new ArrayList<StoreCityMock>();
		resultList.add(new StoreCityMock(GetStoreCitiesTest.i++, "Warszawa", "PL"));
		resultList.add(new StoreCityMock(GetStoreCitiesTest.i++, "Bydgoszcz", "PL"));
		resultList.add(new StoreCityMock(GetStoreCitiesTest.i++, "Marseille", "FR"));
		resultList.add(new StoreCityMock(GetStoreCitiesTest.i++, "Paris", "FR"));
		resultList.add(new StoreCityMock(GetStoreCitiesTest.i++, "Dijon", "FR"));
		resultList.add(new StoreCityMock(GetStoreCitiesTest.i++, "Nantes", "FR"));
		return resultList;
	}

	
	@Test
	public void testGetCodes() throws APIException {
		creerBDMock();
		final Date now = new Date();
		final Action theAction = new GetStoreCities();
		final Application theApplication = new ApplicationMock(43, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, "PL");
		ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof List);
		List<Object> theResultAsList = (List<Object>) theResult;
		Assert.assertEquals(2, theResultAsList.size());

		theParams.put(ActionParam.MAIN_PARAM_KEY, "FR");
		theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
		theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof List);
		theResultAsList = (List<Object>) theResult;
		Assert.assertEquals(4, theResultAsList.size());
		final StoreCityInformationMap theResultFirstMap = (StoreCityInformationMap) theResultAsList.get(0);
		Assert.assertEquals("FR", theResultFirstMap.get(StoreCityInformationMap.CODEPAYS));
		Assert.assertEquals("France", theResultFirstMap.get(StoreCityInformationMap.PAYS));
	}
}
