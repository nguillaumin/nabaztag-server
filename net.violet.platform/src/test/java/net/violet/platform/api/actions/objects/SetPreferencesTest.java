package net.violet.platform.api.actions.objects;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractPeopleWithSessionBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.maps.PerDaySleepTimeMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.ObjectSleep;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.StringShop;

import org.junit.Assert;
import org.junit.Test;

public class SetPreferencesTest extends AbstractPeopleWithSessionBase {

	@Override
	public void testNoSession() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		testSetPreferences(caller, theObject, null);
	}

	@Override
	public void testOtherApplicationSession() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		testSetPreferences(caller, theObject, generateSessionAlterApplication(UserData.getData(theOwner)));
	}

	@Override
	public void testOtherUserSession() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		testSetPreferences(caller, theObject, generateSessionAlterUser(caller));
	}

	@Override
	public void testValidSession() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		testSetPreferences(caller, theObject, generateSession(UserData.getData(theOwner), caller));
	}

	public void testSetPreferences(APICaller inCaller, VObject inObject, String inSessionId) throws APIException {

		final VObjectData inObjectData = VObjectData.getData(inObject);

		inObject.getPreferences().setPreferences(false, false, null);

		final PerDaySleepTimeMap PDSTMap = new PerDaySleepTimeMap(inObjectData);
		Assert.assertEquals(false, PDSTMap.isEmpty());

		Assert.assertEquals(false, inObject.getPreferences().isVisible());
		Assert.assertEquals(false, inObject.getPreferences().isPrivate());
		Assert.assertEquals(true, inObjectData.getOwner().notifyIfReceived());
		Assert.assertEquals(true, inObjectData.getOwner().notifyIfPlayed());

		inObject.getPreferences().setVisible(true);
		Assert.assertEquals(true, inObject.getPreferences().isVisible());
		inObject.getPreferences().setPrivate(true);

		Assert.assertEquals(true, inObject.getPreferences().isPrivate());
		// TimezoneData inTimezoneData =
		// TimezoneData.findByJavaId(inObjectData.getOwner
		// ().getTimezone().getTimezone_javaId());
		// SleepTime inSleepTime = new SleepTime("07:18:01",
		// "21:55:02StringShop.COMMA09:34:03", "23:05:04");
		// assertNotNull(inSleepTime);
		// inObjectData.setPreferences(false, false, true, true, inTimezoneData,
		// inSleepTime);
		final Action theAction = new SetPreferences();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String objectAPIId = VObjectData.getData(inObject).getApiId(inCaller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);

		final Map<String, Object> preferencesObject = new HashMap<String, Object>();
		preferencesObject.put("visible", true);
		preferencesObject.put("private", true);
		preferencesObject.put("notify_received", false);
		preferencesObject.put("timezone", "Europe/London");
		preferencesObject.put("language", "en-GB");

		final Map<String, Object> saturdayTimes = new HashMap<String, Object>();
		saturdayTimes.put("wakeup_time_h", 8);
		saturdayTimes.put("wakeup_time_m", 0);
		saturdayTimes.put("sleep_time_h", 14);
		saturdayTimes.put("sleep_time_m", 15);
		final Map<String, Object> mondayTimes = new HashMap<String, Object>();
		mondayTimes.put("wakeup_time_h", 11);
		mondayTimes.put("wakeup_time_m", 10);
		mondayTimes.put("sleep_time_h", 15);
		mondayTimes.put("sleep_time_m", 20);
		final Map<String, Object> sleepTimes = new HashMap<String, Object>();
		sleepTimes.put("monday", mondayTimes);
		sleepTimes.put("saturday", saturdayTimes);
		preferencesObject.put("sleep_times", sleepTimes);

		theParams.put("preferences", preferencesObject);
		setSessionParam(theParams, inSessionId);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);

		Assert.assertEquals(true, inObject.getPreferences().isVisible());
		Assert.assertEquals(true, inObject.getPreferences().isPrivate());
		Assert.assertEquals("Europe/London", inObject.getTimeZone().getTimezone_javaId());

		for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {

			final List<ObjectSleep> list = Factories.OBJECT_SLEEP.findByObjectAndDay(inObject, i);
			Assert.assertEquals(3, list.size());
			if ((i == Calendar.SUNDAY) || (i == Calendar.SATURDAY)) {
				for (final ObjectSleep o : list) {
					if (o.getTimeAction().equals("WAKE")) {
						Assert.assertEquals("08:00:00", o.getTimeFrom().toString());
						Assert.assertEquals("14:15:00", o.getTimeTo().toString());
					}
				}
			} else {
				for (final ObjectSleep o : list) {
					if (o.getTimeAction().equals("WAKE")) {
						Assert.assertEquals("11:10:00", o.getTimeFrom().toString());
						Assert.assertEquals("15:20:00", o.getTimeTo().toString());
					}
				}
			}
		}

	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParameter() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		theOwner.setComment("Hello world");
		final VObjectMock theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());

		final Action theAction = new SetPreferences();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String objectAPIId = VObjectData.getData(theObject).getApiId(caller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParameterNotMap() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		theOwner.setComment("Hello world");
		final VObjectMock theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());

		final Action theAction = new SetPreferences();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		final String objectAPIId = VObjectData.getData(theObject).getApiId(caller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);
		theParams.put("preferences", Boolean.TRUE);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParameterVisiblePropertyNotBoolean() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		theOwner.setComment("Hello world");
		final VObjectMock theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());

		final Action theAction = new SetPreferences();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));

		final String objectAPIId = VObjectData.getData(theObject).getApiId(caller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);

		final Map<String, Object> preferencesObject = new HashMap<String, Object>();
		preferencesObject.put("visible", "Hello");
		preferencesObject.put("private", false);
		preferencesObject.put("notify_received", true);
		preferencesObject.put("timezone", true);

		theParams.put("preferences", preferencesObject);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);

	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParameterPrivatePropertyNotBoolean() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		theOwner.setComment("Hello world");
		final VObjectMock theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());

		final Action theAction = new SetPreferences();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));

		final String objectAPIId = VObjectData.getData(theObject).getApiId(caller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);

		final Map<String, Object> preferencesObject = new HashMap<String, Object>();
		preferencesObject.put("visible", false);
		preferencesObject.put("private", "Hello");
		preferencesObject.put("notify_received", true);

		theParams.put("preferences", preferencesObject);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParameterNotifyReceivedPropertyNotBoolean() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		theOwner.setComment("Hello world");
		final VObjectMock theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());

		final Action theAction = new SetPreferences();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));

		final String objectAPIId = VObjectData.getData(theObject).getApiId(caller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);

		final Map<String, Object> preferencesObject = new HashMap<String, Object>();
		preferencesObject.put("visible", false);
		preferencesObject.put("private", false);
		preferencesObject.put("notify_received", "Hello");

		theParams.put("preferences", preferencesObject);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = NoSuchObjectException.class)
	public void testInvalidAPIId() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getSiteFrLang(), StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new SetPreferences();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9zozo");
		theParams.put("preferences", new HashMap<String, Object>());
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = NoSuchObjectException.class)
	public void testInexistingRecord() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getSiteFrLang(), StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new SetPreferences();
		final Application theApplication = new ApplicationMock(43, "My first application", theOwner, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		theParams.put(ActionParam.MAIN_PARAM_KEY, "109d0O9770913a");
		theParams.put("preferences", new HashMap<String, Object>());
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}
}
