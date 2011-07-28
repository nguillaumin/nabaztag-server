package net.violet.platform.api.actions.cities;

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
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.CityMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class GetPeopleCitiesTest extends MockTestBase {

	static long i = 1L;

	private List<CityMock> creerBDMock() {
		final List<CityMock> resultList = new ArrayList<CityMock>();
		resultList.add(new CityMock(GetPeopleCitiesTest.i++, "PL", "Warszawa"));
		resultList.add(new CityMock(GetPeopleCitiesTest.i++, "PL", "Bydgoszcz"));
		resultList.add(new CityMock(GetPeopleCitiesTest.i++, "FR", "Marseille"));
		resultList.add(new CityMock(GetPeopleCitiesTest.i++, "EH", "Smara"));
		resultList.add(new CityMock(GetPeopleCitiesTest.i++, "EH", "Boukraa"));
		resultList.add(new CityMock(GetPeopleCitiesTest.i++, "FR", "Paris"));
		resultList.add(new CityMock(GetPeopleCitiesTest.i++, "FR", "Dijon"));
		resultList.add(new CityMock(GetPeopleCitiesTest.i++, "FR", "Nantes"));
		return resultList;
	}

	@Test
	public void testGetCodes() throws APIException {
		creerBDMock();
		final Date now = new Date();
		final Action theAction = new GetPeopleCities();
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
		Assert.assertEquals("Paris", theResultAsList.get(3));
		Assert.assertEquals("Nantes", theResultAsList.get(2));
		Assert.assertEquals("Marseille", theResultAsList.get(1));
		Assert.assertEquals("Dijon", theResultAsList.get(0));
	}
}
