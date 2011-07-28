package net.violet.platform.api.actions.timezones;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class TimezonesTest extends MockTestBase {

	@Test
	public void testGet() throws APIException {

		final Action theAction = new Get();
		final Application theApplication = new ApplicationMock(43, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final ActionParam theActionParam = new ActionParam(caller, new HashMap<String, Object>());
		theAction.processRequest(theActionParam);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof List);
		final List<Object> theResultAsList = (List<Object>) theResult;

		Assert.assertEquals(533, theResultAsList.size());

	}

	@Test
	public void getCurrentOffsetTest() throws APIException {

		final Action theAction = new GetCurrentOffset();
		final Application app = new ApplicationMock(43, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", app);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> params = new HashMap<String, Object>();

		params.put(ActionParam.MAIN_PARAM_KEY, "Europe/Paris");
		ActionParam actionParam = new ActionParam(caller, params);
		theAction.processRequest(actionParam);
		Object apiResult = theAction.processRequest(actionParam);

		final Calendar parisCal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		final int parisOffset = (parisCal.get(Calendar.DST_OFFSET) + parisCal.get(Calendar.ZONE_OFFSET)) / 60000;

		Assert.assertEquals(parisOffset, apiResult);

		// Europe/Moscow is +3:00 / +4:00 depending of day saving time 
		params.put(ActionParam.MAIN_PARAM_KEY, "Europe/Moscow");
		actionParam = new ActionParam(caller, params);
		theAction.processRequest(actionParam);
		apiResult = theAction.processRequest(actionParam);

		final Calendar moscowCal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
		final int moskowOffset = (moscowCal.get(Calendar.DST_OFFSET) + moscowCal.get(Calendar.ZONE_OFFSET)) / 60000;

		Assert.assertEquals(moskowOffset, apiResult);

		// Tokyo has no Daylight Saving Time system. It's allways GMT+9:00
		params.put(ActionParam.MAIN_PARAM_KEY, "Asia/Tokyo");
		actionParam = new ActionParam(caller, params);
		theAction.processRequest(actionParam);
		apiResult = theAction.processRequest(actionParam);

		Assert.assertEquals(Integer.valueOf(9 * 60), apiResult);
	}

	@Test
	public void getByOffsetTest() throws APIException {

		final Action theAction = new GetByOffset();
		final Application theApplication = new ApplicationMock(43, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, 0);

		ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);

		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof List);
		List<Object> theResultAsList = (List<Object>) theResult;

		Assert.assertNotNull(theResultAsList);
		Assert.assertTrue(theResultAsList.contains("Africa/Casablanca"));
		Assert.assertTrue(theResultAsList.contains("Europe/London"));
		Assert.assertFalse(theResultAsList.contains("America/Sao_Paulo"));

		theParams.put(ActionParam.MAIN_PARAM_KEY, 60);

		theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);

		theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof List);
		theResultAsList = (List<Object>) theResult;

		Assert.assertTrue(theResultAsList.contains("Africa/Lagos"));
		Assert.assertTrue(theResultAsList.contains("Europe/Sarajevo"));
		Assert.assertTrue(theResultAsList.contains("Europe/Paris"));
		Assert.assertFalse(theResultAsList.contains("America/Sao_Paulo"));

		theParams.put(ActionParam.MAIN_PARAM_KEY, -180);

		theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);

		theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof List);
		theResultAsList = (List<Object>) theResult;

		Assert.assertTrue(theResultAsList.contains("America/Sao_Paulo"));
		Assert.assertFalse(theResultAsList.contains("Europe/Paris"));
	}

	@Test
	public void getByCountryTest() throws APIException {

		final Action theAction = new GetByCountry();
		final Application theApplication = new ApplicationMock(43, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, "GB");
		ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof List);
		List<Object> theResultAsList = (List<Object>) theResult;

		Assert.assertEquals(4, theResultAsList.size());
		Assert.assertEquals("Europe/Belfast", theResultAsList.get(0));
		Assert.assertEquals("Europe/London", theResultAsList.get(1));
		Assert.assertEquals("GB", theResultAsList.get(2));
		Assert.assertEquals("GB-Eire", theResultAsList.get(3));

		theParams.put(ActionParam.MAIN_PARAM_KEY, "RS");
		theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
		theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof List);
		theResultAsList = (List<Object>) theResult;

		Assert.assertEquals(1, theResultAsList.size());
		Assert.assertEquals("Europe/Belgrade", theResultAsList.get(0));
	}
}
