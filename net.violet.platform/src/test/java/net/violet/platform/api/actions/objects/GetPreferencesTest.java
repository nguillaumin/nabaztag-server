package net.violet.platform.api.actions.objects;

import java.sql.Time;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractPeopleWithSessionBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.ObjectSleepMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class GetPreferencesTest extends AbstractPeopleWithSessionBase {


	private static final Logger LOGGER = Logger.getLogger(GetPreferencesTest.class);

	@Override
	public void testNoSession() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getSiteFrLang(), StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		try {
			testExistingRecord(caller, theObject, null);
		} catch (final ParseException e) {
			GetPreferencesTest.LOGGER.fatal(e);
		}
	}

	@Override
	public void testOtherApplicationSession() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getSiteFrLang(), StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		try {
			testExistingRecord(caller, theObject, generateSessionAlterApplication(UserData.getData(theOwner)));
		} catch (final ParseException e) {
			GetPreferencesTest.LOGGER.fatal(e);
		}
	}

	@Override
	public void testOtherUserSession() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getSiteFrLang(), StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		try {
			testExistingRecord(caller, theObject, generateSessionAlterUser(caller));
		} catch (final ParseException e) {
			GetPreferencesTest.LOGGER.fatal(e);
		}
	}

	@Override
	public void testValidSession() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getSiteFrLang(), StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), now.getTime());
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		try {
			testExistingRecord(caller, theObject, generateSession(UserData.getData(theOwner), caller));
		} catch (final ParseException e) {
			GetPreferencesTest.LOGGER.fatal(e);
		}
	}

	public void testExistingRecord(APICaller inCaller, VObject inObject, String inSessionId) throws APIException, ParseException {
		final Time sleepTime = CCalendar.getSQLTime("07:18:01");
		final Time wakeUpTime = CCalendar.getSQLTime("4:10:02");
		final Time sleepTime_WE = CCalendar.getSQLTime("17:25:01");
		final Time wakeUpTime_WE = CCalendar.getSQLTime("14:05:02");
		final Time midnight = CCalendar.getSQLTime(StringShop.MIDNIGHT);
		final Time beforeMidnight = CCalendar.getSQLTime("23:59:03");

		for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
			if ((i == Calendar.SUNDAY) || (i == Calendar.SATURDAY)) {
				new ObjectSleepMock(0, inObject, i, midnight, wakeUpTime_WE, "SLEEP"); // 0 - 4
				new ObjectSleepMock(0, inObject, i, wakeUpTime_WE, sleepTime_WE, "WAKE"); // 14 - 17
				new ObjectSleepMock(0, inObject, i, sleepTime_WE, beforeMidnight, "SLEEP"); // 7 - 23.59
			} else { // fait la sieste
				new ObjectSleepMock(0, inObject, i, midnight, wakeUpTime, "WAKE"); // 0 - 4
				new ObjectSleepMock(0, inObject, i, wakeUpTime, sleepTime, "SLEEP"); // 4 - 7
				new ObjectSleepMock(0, inObject, i, sleepTime, beforeMidnight, "WAKE"); // 7 - 23.59
			}
		}

		// Calls the action getPreferences
		final Action theAction = new GetPreferences();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, inSessionId);

		final String objectAPIId = VObjectData.getData(inObject).getApiId(inCaller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof Map);
		final Map theResultAsMap = (Map) theResult;
		Assert.assertEquals(true, theResultAsMap.get("visible"));
		Assert.assertEquals(false, theResultAsMap.get("private"));
		Assert.assertEquals(true, theResultAsMap.get("notify_received"));
		Assert.assertEquals("Europe/Paris", theResultAsMap.get("timezone"));
		Assert.assertEquals("fr-FR", theResultAsMap.get("language"));
		final Object PDST = theResultAsMap.get("sleep_times");
		Assert.assertNotNull(PDST);
		Assert.assertTrue(PDST instanceof Map);
		final Map PDSTAsMap = (Map) PDST;
		Assert.assertEquals(7, PDSTAsMap.size());
		final Object sunday = PDSTAsMap.get("sunday");
		Assert.assertNotNull(sunday);

		final String[] daysNames = { "sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday" };

		for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
			final Object courrentDay = PDSTAsMap.get(daysNames[i - 1]);
			Assert.assertNotNull(daysNames[i - 1]);
			Assert.assertTrue(courrentDay instanceof Map);
			final Map<String, Object> courrentDayAsMap = (Map<String, Object>) courrentDay;
			if ((i == Calendar.SUNDAY) || (i == Calendar.SATURDAY)) {
				Assert.assertEquals(14, courrentDayAsMap.get("wakeup_time_h"));
				Assert.assertEquals(05, courrentDayAsMap.get("wakeup_time_m"));
				Assert.assertEquals(17, courrentDayAsMap.get("sleep_time_h"));
				Assert.assertEquals(25, courrentDayAsMap.get("sleep_time_m"));
			} else {
				Assert.assertEquals(7, courrentDayAsMap.get("wakeup_time_h"));
				Assert.assertEquals(18, courrentDayAsMap.get("wakeup_time_m"));
				Assert.assertEquals(4, courrentDayAsMap.get("sleep_time_h"));
				Assert.assertEquals(10, courrentDayAsMap.get("sleep_time_m"));
			}
		}
	}

	@Test(expected = NoSuchObjectException.class)
	public void testInvalidAPIId() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(42, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getSiteFrLang(), StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new GetPreferences();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9zozo");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = NoSuchObjectException.class)
	public void testInexistingRecord() throws APIException {
		final Date now = new Date();
		final User theOwner = new UserMock(43, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getSiteFrLang(), StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new GetPreferences();
		final Application theApplication = new ApplicationMock(43, "My first application", theOwner, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		theParams.put(ActionParam.MAIN_PARAM_KEY, "109d0O9770913a");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}
}
