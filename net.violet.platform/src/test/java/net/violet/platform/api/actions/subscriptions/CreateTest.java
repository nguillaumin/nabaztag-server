package net.violet.platform.api.actions.subscriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.applications.EarsCommunionHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.MessengerMock;
import net.violet.platform.datamodel.mock.SourceMock;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class CreateTest extends AbstractTestBase {

	@Test
	public void airCreateTest() throws APIException {
		new SourceMock(0, "air.france.paris", 0);
		final VObject theObject = getKowalskyObject();

		final Application application = Application.NativeApplication.AIR.getApplication();

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(Create.APPLICATION_ID, ApplicationData.getData(application).getApiId(caller));
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObject).getApiId(caller));

		Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("source", "air.france.paris");
		settings.put("language", "fr-FR");

		theParams.put("settings", settings);

		final Map<String, Object> scheduling = new HashMap<String, Object>();
		final Map<String, Object> mondayAtom = new HashMap<String, Object>();
		mondayAtom.put("time_h", 17);
		mondayAtom.put("time_m", 0);
		final Map<String, Object> fridayAtom = new HashMap<String, Object>();
		fridayAtom.put("time_h", 8);
		fridayAtom.put("time_m", 30);
		scheduling.put("type", "Daily");
		scheduling.put("Monday", mondayAtom);
		scheduling.put("Friday", fridayAtom);

		final List<Map<String, Object>> schedulingList = new ArrayList<Map<String, Object>>();
		schedulingList.add(scheduling);

		theParams.put("scheduling", schedulingList);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);
		final Map theResultAsMap = (Map) theResult;

		final Subscription theSubscription = Factories.SUBSCRIPTION.find(SubscriptionData.findByAPIId((String) theResultAsMap.get("id"), theActionParam.getCallerAPIKey()).getId());

		settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());

		Assert.assertEquals("air.france.paris", settings.get("source"));
		Assert.assertEquals("fr-FR", settings.get("language"));

		final List<SubscriptionScheduling> theResultList = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);

		Assert.assertEquals(1, theResultList.size());
		final SubscriptionScheduling theScheduling = theResultList.get(0);
		Assert.assertEquals(SCHEDULING_TYPE.Daily, theScheduling.getType());
		final Map<String, SubscriptionSchedulingSettings> theSchedSett = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findAllBySubscriptionSchedulingAsMap(theScheduling);
		Assert.assertEquals(2, theSchedSett.size());
		final SubscriptionSchedulingSettings mondaySett = theSchedSett.get("Monday");
		Assert.assertNotNull(mondaySett);
		Assert.assertEquals("17:00:00", mondaySett.getValue());
		final SubscriptionSchedulingSettings fridaySett = theSchedSett.get("Friday");
		Assert.assertNotNull(fridaySett);
		Assert.assertEquals("08:30:00", fridaySett.getValue());
	}

	@Test
	public void clockCreateTest() throws APIException {

		final VObject theObject = getKowalskyObject();

		final Application application = Application.NativeApplication.CLOCK.getApplication();

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(Create.APPLICATION_ID, ApplicationData.getData(application).getApiId(caller));
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObject).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("languages", Arrays.asList("fr-FR", "en-US", "en-GB"));
		settings.put("types", Arrays.asList("1", "3"));

		theParams.put("settings", settings);

		final Map<String, Object> scheduling = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put("time_h", 17);
		atom.put("time_m", 0);
		scheduling.put("type", "Daily");
		scheduling.put("Monday", atom);

		final List<Map<String, Object>> schedulingList = new ArrayList<Map<String, Object>>();
		schedulingList.add(scheduling);

		theParams.put("scheduling", schedulingList);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);
		final Map theResultAsMap = (Map) theResult;

		final Subscription theSubscription = Factories.SUBSCRIPTION.find(SubscriptionData.findByAPIId((String) theResultAsMap.get("id"), theActionParam.getCallerAPIKey()).getId());

		Assert.assertEquals(2, theSubscription.getSettings().size());

		Assert.assertEquals("[fr-FR, en-US, en-GB]", theSubscription.getSettings().get("languages").toString());
		Assert.assertEquals("[1, 3]", theSubscription.getSettings().get("types").toString());

		final List<SubscriptionScheduling> theResultList = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);

		Assert.assertEquals(1, theResultList.size());
	}

	@Test
	public void weatherCreateTest() throws APIException {

		final VObject theObject = getKowalskyObject();
		new SourceMock(0, "Nmeteo.FRANCE.paris.weather", 0);
		final Application application = Application.NativeApplication.WEATHER.getApplication();

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(Create.APPLICATION_ID, ApplicationData.getData(application).getApiId(caller));
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObject).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("source", "Nmeteo.FRANCE.paris.weather");
		settings.put("language", "fr-FR");
		settings.put("unit", "1");

		theParams.put("settings", settings);

		final Map<String, Object> scheduling = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put("time_h", 17);
		atom.put("time_m", 0);
		scheduling.put("type", "Daily");
		scheduling.put("Monday", atom);

		final List<Map<String, Object>> schedulingList = new ArrayList<Map<String, Object>>();
		schedulingList.add(scheduling);

		theParams.put("scheduling", schedulingList);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);
		final Map theResultAsMap = (Map) theResult;

		final Subscription theSubscription = Factories.SUBSCRIPTION.find(SubscriptionData.findByAPIId((String) theResultAsMap.get("id"), theActionParam.getCallerAPIKey()).getId());

		Assert.assertEquals(3, theSubscription.getSettings().size());

		Assert.assertEquals("Nmeteo.FRANCE.paris.weather", theSubscription.getSettings().get("source").toString());
		Assert.assertEquals("1", theSubscription.getSettings().get("unit").toString());
		Assert.assertEquals("fr-FR", theSubscription.getSettings().get("language"));

		final List<SubscriptionScheduling> theResultList = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);

		Assert.assertEquals(1, theResultList.size());
	}

	@Test(expected = NoSuchApplicationException.class)
	public void wrongApplicationTest() throws APIException {

		final VObject theObject = getKowalskyObject();

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(Create.APPLICATION_ID, "wrongApplicationId");
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObject).getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test
	public void createInteractionTriggerTest() throws APIException {
		final VObject theObject = getKowalskyObject();
		final Application application = Application.NativeApplication.WEATHER.getApplication();
		final APICaller caller = getPublicApplicationAPICaller();
		new SourceMock(0, "Nmeteo.France.paris.weather", 0);
		final Action theAction = new Create();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(Create.APPLICATION_ID, ApplicationData.getData(application).getApiId(caller));
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObject).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("source", "Nmeteo.France.paris.weather");
		settings.put("language", "fr-FR");
		settings.put("unit", "1");

		theParams.put("settings", settings);

		final Map<String, Object> scheduling = new HashMap<String, Object>();
		scheduling.put("type", "InteractionTrigger");
		scheduling.put("event", "ZtampDetection");
		final Map<String, Integer> from = new HashMap<String, Integer>();
		from.put("time_h", 10);
		from.put("time_m", 30);
		final Map<String, Integer> to = new HashMap<String, Integer>();
		to.put("time_h", 15);
		to.put("time_m", 45);
		final Map<String, Map<String, Integer>> validity = new HashMap<String, Map<String, Integer>>();
		validity.put("from", from);
		validity.put("to", to);
		final Map<String, Map<String, Map<String, Integer>>> validities = new HashMap<String, Map<String, Map<String, Integer>>>();
		validities.put("Sunday", validity);
		validities.put("Friday", validity);
		scheduling.put("validity", validities);

		final List<Map<String, Object>> schedulingList = new ArrayList<Map<String, Object>>();
		schedulingList.add(scheduling);
		theParams.put("scheduling", schedulingList);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);
		final Map theResultAsMap = (Map) theResult;

		final Subscription theSubscription = Factories.SUBSCRIPTION.find(SubscriptionData.findByAPIId((String) theResultAsMap.get("id"), theActionParam.getCallerAPIKey()).getId());

		final List<SubscriptionScheduling> theResultList = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);
		Assert.assertEquals(1, theResultList.size());
		final SubscriptionScheduling theSched = theResultList.get(0);
		Assert.assertEquals(SCHEDULING_TYPE.InteractionTrigger, theSched.getType());
		final SubscriptionSchedulingSettings sunday = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findBySubscriptionSchedulingAndKey(theSched, "Sunday");
		final SubscriptionSchedulingSettings friday = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findBySubscriptionSchedulingAndKey(theSched, "Friday");
		Assert.assertEquals("10:30-15:45", sunday.getValue());
		Assert.assertEquals("10:30-15:45", friday.getValue());
	}

	@Test(expected = InvalidParameterException.class)
	public void createInteractionTriggerWrongParamTest() throws APIException {
		final VObject theObject = getKowalskyObject();
		final Application application = Application.NativeApplication.WEATHER.getApplication();
		final APICaller caller = getPublicApplicationAPICaller();

		final Action theAction = new Create();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(Create.APPLICATION_ID, ApplicationData.getData(application).getApiId(caller));
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObject).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("source", "France/Paris");
		settings.put("language", "fr-FR");
		settings.put("unit", "1");

		theParams.put("settings", settings);

		final Map<String, Object> scheduling = new HashMap<String, Object>();
		scheduling.put("type", "InteractionTrigger");
		scheduling.put("event", "ZtampDetection");
		final Map<String, Integer> from = new HashMap<String, Integer>();
		from.put("time_h", -10);
		from.put("time_m", 30);
		final Map<String, Integer> to = new HashMap<String, Integer>();
		to.put("time_h", 15);
		to.put("time_m", 45);
		final Map<String, Map<String, Integer>> validity = new HashMap<String, Map<String, Integer>>();
		validity.put("from", from);
		validity.put("to", to);
		final Map<String, Map<String, Map<String, Integer>>> validities = new HashMap<String, Map<String, Map<String, Integer>>>();
		validities.put("Sunday", validity);
		validities.put("Friday", validity);
		scheduling.put("validity", validities);

		final List<Map<String, Object>> schedulingList = new ArrayList<Map<String, Object>>();
		schedulingList.add(scheduling);
		theParams.put("scheduling", schedulingList);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);

	}

	@Test(expected = InvalidParameterException.class)
	public void createInteractionTriggerIllogicTimeTest() throws APIException {
		final VObject theObject = getKowalskyObject();
		final Application application = Application.NativeApplication.WEATHER.getApplication();
		final APICaller caller = getPublicApplicationAPICaller();

		final Action theAction = new Create();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(Create.APPLICATION_ID, ApplicationData.getData(application).getApiId(caller));
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObject).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("source", "France/Paris");
		settings.put("language", "fr-FR");
		settings.put("unit", "1");

		theParams.put("settings", settings);

		final Map<String, Object> scheduling = new HashMap<String, Object>();
		scheduling.put("type", "InteractionTrigger");
		scheduling.put("event", "ZtampDetection");
		final Map<String, Integer> from = new HashMap<String, Integer>();
		from.put("time_h", 16);
		from.put("time_m", 30);
		final Map<String, Integer> to = new HashMap<String, Integer>();
		to.put("time_h", 15);
		to.put("time_m", 45);
		final Map<String, Map<String, Integer>> validity = new HashMap<String, Map<String, Integer>>();
		validity.put("from", from);
		validity.put("to", to);
		final Map<String, Map<String, Map<String, Integer>>> validities = new HashMap<String, Map<String, Map<String, Integer>>>();
		validities.put("Sunday", validity);
		validities.put("Friday", validity);
		scheduling.put("validity", validities);

		final List<Map<String, Object>> schedulingList = new ArrayList<Map<String, Object>>();
		schedulingList.add(scheduling);
		theParams.put("scheduling", schedulingList);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);

	}

	@Test
	public void EarCommunionCreateTest() throws APIException {

		final VObject theObjectSender = getKowalskyObject();
		final VObject theObjectRecipient = getPrivateObject();

		new MessengerMock(0, getKowalskyUser(), getKowalskyObject(), "sender");
		new MessengerMock(0, getPrivateUser(), getPrivateObject(), "recipient");

		final Application application = Application.NativeApplication.EARS_COMMUNION.getApplication();

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String theFriendApiId = VObjectData.getData(theObjectRecipient).getApiId(caller);

		theParams.put(Create.APPLICATION_ID, ApplicationData.getData(application).getApiId(caller));
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObjectSender).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, VObjectData.getData(theObjectRecipient).getApiId(caller));

		theParams.put("settings", settings);
		theParams.put("scheduling", new ArrayList<Map<String, Object>>());

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);
		final Map theResultAsMap = (Map) theResult;

		final Subscription theSubscription = Factories.SUBSCRIPTION.find(SubscriptionData.findByAPIId((String) theResultAsMap.get("id"), theActionParam.getCallerAPIKey()).getId());

		Assert.assertEquals(2, theSubscription.getSettings().size());

		final Map<String, Object> getSettings = (Map<String, Object>) theResultAsMap.get("settings");
		Assert.assertEquals(theFriendApiId, getSettings.get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString());
		Assert.assertEquals(NOTIFICATION_STATUS.PENDING.toString(), getSettings.get(EarsCommunionHandler.STATUS).toString());

		final List<SubscriptionScheduling> theResultList = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);

		Assert.assertEquals(0, theResultList.size());

		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(application, theObjectRecipient).isEmpty());

		Assert.assertEquals(1, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipient(Factories.MESSENGER.getByObject(theObjectRecipient), 0, 0).size());
		Assert.assertEquals(1, NotificationData.getSent(VObjectData.getData(theObjectSender), 0, 0).size());
		Assert.assertEquals(0, NotificationData.getSent(VObjectData.getData(theObjectRecipient), 0, 0).size());
		Assert.assertEquals(1, NotificationData.getReceived(VObjectData.getData(theObjectRecipient), 0, 0).size());
		Assert.assertEquals(0, NotificationData.getReceived(VObjectData.getData(theObjectSender), 0, 0).size());
	}
}
