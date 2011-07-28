package net.violet.platform.api.actions.objects;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.ObjectAlreadyExistsException;
import net.violet.platform.applications.ClockHandler;
import net.violet.platform.applications.MoodHandler;
import net.violet.platform.applications.TaichiHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.TagTmpSiteMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.TaichiDataFactory.TAICHI_FREQUENCY;
import net.violet.platform.schedulers.AmbiantHandler;

import org.junit.Assert;
import org.junit.Test;

public class CreateTest extends AbstractTestBase {

	@Test
	public void createObject() throws APIException {

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final UserData theUserData = UserData.getData(getKowalskyUser());

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String theSerial = "123456123456";
		new TagTmpSiteMock(0L, theSerial, HARDWARE.V2);

		final String inLabel = "newObject";
		final String harwareType = "violet.nabaztag.tagtag";

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, theUserData, theCalendar.getTime()));

		theParams.put("serial_number", theSerial);
		theParams.put("object_name", inLabel);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = new Create().processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof Map);

		final Map theResultAsMap = (Map) theResult;

		Assert.assertEquals(Hardware.HARDWARE.V2.getLabel() + theSerial, theResultAsMap.get("name"));
		Assert.assertEquals(harwareType, theResultAsMap.get("hw_type"));
		Assert.assertEquals(theSerial, theResultAsMap.get("serial_number"));
		Assert.assertEquals(theUserData.getApiId(caller), theResultAsMap.get("owner"));

		final VObjectData theNewObjectData = VObjectData.findByAPIId(theResultAsMap.get("id").toString(), caller.getAPIKey());
		final User theOwner = getKowalskyUser();
		Assert.assertEquals(theNewObjectData.getObjectType(), ObjectType.NABAZTAG_V2);
		Assert.assertEquals(theNewObjectData.getTimeZone(), theOwner.getTimezone().getTimezone_javaId());
		Assert.assertEquals(theOwner.getUserHasObject(), true);
		Assert.assertNull(Factories.TAG_TMP_SITE.findBySerial(theSerial));

		Assert.assertEquals(inLabel, theNewObjectData.getProfile().getLabel());

		checkObjectSubscription(theNewObjectData);
	}

	private void checkObjectSubscription(VObjectData inObject) {
		final List<SubscriptionData> theSubscriptions = SubscriptionData.findAllByObject(inObject.getReference());
		Assert.assertEquals(3, theSubscriptions.size());
		final List<SubscriptionData> theMoodSubscriptions = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MOOD.getApplication(), inObject.getReference());
		Assert.assertEquals(1, theMoodSubscriptions.size());
		final Map<String, Object> theMoodSettings = theMoodSubscriptions.get(0).getSettings();
		Assert.assertEquals(1, theMoodSettings.size());
		Assert.assertEquals(Collections.singletonList("fr-FR"), theMoodSettings.get(MoodHandler.LANGUAGES));
		final List<SubscriptionSchedulingData> theMoodSchedulings = SubscriptionSchedulingData.findAllBySubscription(theMoodSubscriptions.get(0));
		Assert.assertEquals(1, theMoodSchedulings.size());
		Assert.assertEquals(SchedulingType.SCHEDULING_TYPE.RandomWithFrequency, theMoodSchedulings.get(0).getType());

		final List<SubscriptionData> theTaichiSubscriptions = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.TAICHI.getApplication(), inObject.getReference());
		Assert.assertEquals(1, theTaichiSubscriptions.size());
		final Map<String, Object> theTaichiSettings = theTaichiSubscriptions.get(0).getSettings();
		Assert.assertEquals(1, theTaichiSettings.size());
		Assert.assertEquals(TAICHI_FREQUENCY.FAST.getLabel(), theTaichiSettings.get(TaichiHandler.SOURCE).toString());
		final List<SubscriptionSchedulingData> theTaichiSchedulings = SubscriptionSchedulingData.findAllBySubscription(theTaichiSubscriptions.get(0));
		Assert.assertEquals(1, theTaichiSchedulings.size());
		Assert.assertEquals(SchedulingType.SCHEDULING_TYPE.Ambiant, theTaichiSchedulings.get(0).getType());
		final List<SubscriptionSchedulingSettingsData> taichiScheSettings = SubscriptionSchedulingSettingsData.findAllBySubscriptionScheduling(theTaichiSchedulings.get(0));
		Assert.assertEquals(1, taichiScheSettings.size());
		Assert.assertEquals("0", taichiScheSettings.get(0).getValue());
		Assert.assertEquals(AmbiantHandler.LAST_TIME, taichiScheSettings.get(0).getKey());

		final List<SubscriptionData> theClockSubscriptions = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.CLOCK.getApplication(), inObject.getReference());
		Assert.assertEquals(1, theClockSubscriptions.size());
		final Map<String, Object> theClockSettings = theClockSubscriptions.get(0).getSettings();
		Assert.assertEquals(2, theClockSettings.size());
		Assert.assertEquals("[fr-FR]", theClockSettings.get(ClockHandler.LANGUAGES).toString());
		Assert.assertEquals("[1, 2]", theClockSettings.get(ClockHandler.TYPES).toString());

		final List<SubscriptionSchedulingData> theClockSchedulings = SubscriptionSchedulingData.findAllBySubscription(theClockSubscriptions.get(0));
		Assert.assertEquals(1, theClockSchedulings.size());
		Assert.assertEquals(SchedulingType.SCHEDULING_TYPE.Frequency, theClockSchedulings.get(0).getType());
	}

	@Test(expected = NoSuchObjectException.class)
	public void objectNotConnected() throws APIException {

		final User theOwner = new UserMock(42, "newUser", "123", "newuser@test.com", getFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Action theAction = new Create();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String inSerial = "abc456123456";

		final String inName = "newObject";

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime()));
		theParams.put("serial_number", inSerial);
		theParams.put("object_name", inName);
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);

	}

	@Test(expected = ObjectAlreadyExistsException.class)
	public void objectSerialAlreadyExist() throws APIException {

		final User theOwner = new UserMock(42, "newUser", "123", "newuser@test.com", getFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Action theAction = new Create();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String inSerial = "12345612345f";
		new VObjectMock(61009, inSerial, "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), getFrLang(), new Date().getTime());

		final String inName = "newObject";

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		new TagTmpSiteMock(0L, inSerial, HARDWARE.V2);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime()));
		theParams.put("serial_number", inSerial);
		theParams.put("object_name", inName);
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);

	}

	@Test(expected = InvalidParameterException.class)
	public void invalidObjectHardwareType() throws APIException {

		final User theOwner = new UserMock(42, "newUser", "123", "newuser@test.com", getFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Action theAction = new Create();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String inSerial = "123456123aef";
		new TagTmpSiteMock(0L, inSerial, HARDWARE.V2);
		final String inName = "newObject2";

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime()));
		theParams.put("object_name", inName);
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);

	}
}
