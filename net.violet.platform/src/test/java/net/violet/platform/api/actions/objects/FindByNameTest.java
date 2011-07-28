package net.violet.platform.api.actions.objects;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ObjectPreferences;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.ObjectPreferencesMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class FindByNameTest extends AbstractTestBase {

	@Test
	public void findByNameTest() throws APIException {
		final Timezone theTimezone = getParisTimezone();
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();

		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, theTimezone);

		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, theTimezone, frLang, now.getTime());

		final Action theAction = new FindByName();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "test42");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof Map);

		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;

		Assert.assertEquals(VObjectData.getData(theObject).getApiId(caller), theResultAsMap.get("id"));
		Assert.assertEquals("test42", theResultAsMap.get("name"));
		Assert.assertEquals(null, theResultAsMap.get("serial_number"));
	}

	@Test
	public void findByNameSerialVisibilityTest() throws APIException {
		final Timezone theTimezone = getParisTimezone();
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();

		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, theTimezone);

		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, theTimezone, frLang, now.getTime());

		final Action theAction = new FindByName();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "test42");

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);
		final String id = SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime());
		theParams.put(ActionParam.SESSION_PARAM_KEY, id);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof Map);

		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;

		Assert.assertEquals(VObjectData.getData(theObject).getApiId(caller), theResultAsMap.get("id"));
		Assert.assertEquals("test42", theResultAsMap.get("name"));
		Assert.assertEquals("F00004000001", theResultAsMap.get("serial_number"));
	}

	@Test
	public void findByNameNotVisibleTest() throws APIException {
		final Timezone theTimezone = getParisTimezone();
		final Date now = new Date();
		final Lang frLang = getFrLang();

		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, theTimezone);

		final VObjectMock theObjectMock = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, theTimezone, frLang, now.getTime());
		final ObjectPreferences thePref = new ObjectPreferencesMock(theObjectMock, frLang);
		thePref.setVisible(false);
		theObjectMock.setPreferences(thePref);

		final Action theAction = new FindByName();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "test42");

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
	}

	@Test
	public void findByNameNotVisibleButSessionUserOKTest() throws APIException {
		final Timezone theTimezone = getParisTimezone();
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();

		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, theTimezone);

		final VObjectMock theObjectMock = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, theTimezone, frLang, now.getTime());
		final ObjectPreferences thePref = new ObjectPreferencesMock(theObjectMock, frLang);
		thePref.setVisible(false);
		theObjectMock.setPreferences(thePref);

		final Action theAction = new FindByName();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "test42");
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);
		final String id = SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime());

		theParams.put(ActionParam.SESSION_PARAM_KEY, id);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof Map);

		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;

		Assert.assertEquals(VObjectData.getData(theObjectMock).getApiId(caller), theResultAsMap.get("id"));
		Assert.assertEquals("test42", theResultAsMap.get("name"));
		Assert.assertEquals("F00004000001", theResultAsMap.get("serial_number"));
	}

	@Test(expected = NoSuchObjectException.class)
	public void findNonExistingTest() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Action theAction = new FindByName();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "Ghost");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}
}
