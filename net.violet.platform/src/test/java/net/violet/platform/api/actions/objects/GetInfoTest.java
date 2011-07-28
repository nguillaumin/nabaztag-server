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
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class GetInfoTest extends AbstractTestBase {

	@Test
	public void testExistingRecord() throws APIException {

		final Date now = new Date();
		final Lang frLang = getFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		final Action theAction = new GetInfo();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String objectAPIId = VObjectData.getData(theObject).getApiId(caller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);

		final Map theResultAsMap = (Map) theResult;

		Assert.assertEquals(objectAPIId, theResultAsMap.get("id"));
		Assert.assertEquals("test42", theResultAsMap.get("name"));
		Assert.assertEquals(ObjectType.NABAZTAG_V2.getTypeName(), theResultAsMap.get("hw_type"));
		Assert.assertEquals(null, theResultAsMap.get("serial_number"));
		Assert.assertEquals(UserData.getData(theOwner).getApiId(caller), theResultAsMap.get("owner"));
		Assert.assertEquals(VObjectData.getData(theObject).getCreationDate(), theResultAsMap.get("creation_date"));
	}

	@Test
	public void testSerialVisibility() throws APIException {

		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, getParisTimezone(), frLang, now.getTime());

		final Action theAction = new GetInfo();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String objectAPIId = VObjectData.getData(theObject).getApiId(caller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime()));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);

		final Map theResultAsMap = (Map) theResult;

		Assert.assertEquals(objectAPIId, theResultAsMap.get("id"));
		Assert.assertEquals("test42", theResultAsMap.get("name"));
		Assert.assertEquals(ObjectType.NABAZTAG_V2.getTypeName(), theResultAsMap.get("hw_type"));
		Assert.assertEquals("F00004000001", theResultAsMap.get("serial_number"));
		Assert.assertEquals(UserData.getData(theOwner).getApiId(caller), theResultAsMap.get("owner"));
		Assert.assertEquals(VObjectData.getData(theObject).getCreationDate(), theResultAsMap.get("creation_date"));
	}

	@Test(expected = NoSuchObjectException.class)
	public void testInvalidAPIId() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new GetInfo();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9zozo");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = NoSuchObjectException.class)
	public void testInexistingRecord() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new GetInfo();
		final Application theApplication = new ApplicationMock(43, "My first application", theOwner, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "109d0O9770913a");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidSessionException.class)
	public void testInvalidSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final UserMock theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		final Action theAction = new GetInfo();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String objectAPIId = VObjectData.getData(theObject).getApiId(caller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, "fsdjfklg");

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}
}
