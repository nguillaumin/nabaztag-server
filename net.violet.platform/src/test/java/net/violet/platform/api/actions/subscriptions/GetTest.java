package net.violet.platform.api.actions.subscriptions;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.datamodel.mock.SubscriptionSchedulingMock;
import net.violet.platform.datamodel.mock.SubscriptionSchedulingSettingsMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.InteractionTriggerHandler;
import net.violet.platform.schedulers.DailyHandler.Weekday;

import org.junit.Assert;
import org.junit.Test;

public class GetTest extends AbstractTestBase {

	@Test
	public void testGet() throws APIException {

		final Lang frLang = getFrLang();
		final User theOwner = new UserMock(42, "subs.Get1", net.violet.common.StringShop.EMPTY_STRING, "subs.Get3", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Date now = new Date();
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObjectData theObjectData = VObjectData.getData(theObject);
		final Application inApplication2 = new ApplicationMock(356L, "bourse", theOwner, now);
		final Application inApplication1 = new ApplicationMock(357L, "meteo", theOwner, now);

		final Subscription inSubscripion2 = new SubscriptionMock(1L, inApplication2, theObject);
		final SubscriptionScheduling sched = new SubscriptionSchedulingMock(2L, inSubscripion2, SCHEDULING_TYPE.Weekly);
		new SubscriptionSchedulingSettingsMock(5L, sched, DailyHandler.Weekday.FRIDAY.getValue(), "12:35");

		new SubscriptionMock(4L, inApplication1, theObject);

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, theObjectData.getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List theResultAsList = (List) theResult;
		Assert.assertEquals(theResultAsList.size(), 2);

	}

	@Test
	public void testGetWithInformationField() throws APIException {

		final VObject theObject = getKowalskyObject();
		final Application theApplication = Application.NativeApplication.RSS_FULL.getApplication();

		final Subscription theSubscription = Factories.SUBSCRIPTION.create(theApplication, theObject);
		Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SCHEDULING_TYPE.NewContent);

		theSubscription.setSettings(Collections.singletonMap(VActionFullHandler.LABEL, "myAlert"));

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, VObjectData.getData(theObject).getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List theResultAsList = (List) theResult;
		Assert.assertEquals(theResultAsList.size(), 1);

		final Map<String, Object> subscriptionMap = ((Map<String, Object>) ((List) theResult).get(0));

		Assert.assertTrue(subscriptionMap.containsKey("information"));
		Assert.assertEquals("myAlert", subscriptionMap.get("information"));
	}

	@Test
	public void testGetwithApplicationId() throws APIException {

		final Lang frLang = getFrLang();
		final User theOwner = new UserMock(42, "subs.Get1", net.violet.common.StringShop.EMPTY_STRING, "subs.Get3", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Date now = new Date();
		final VObject theObject = new VObjectMock(61010, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObjectData theObjectData = VObjectData.getData(theObject);

		final Application inApplication1 = new ApplicationMock(357L, "bourse", theOwner, now);
		final Application inApplication2 = new ApplicationMock(356L, "meteo", theOwner, now);
		new SubscriptionMock(101L, inApplication1, theObject);
		final Subscription inSubscripion2 = new SubscriptionMock(102L, inApplication2, theObject);
		new SubscriptionMock(103L, inApplication2, theObject);

		final SubscriptionData theSub = SubscriptionData.getData(inSubscripion2);

		final SubscriptionScheduling scheduling = new SubscriptionSchedulingMock(102L, inSubscripion2, SCHEDULING_TYPE.Daily);
		new SubscriptionSchedulingSettingsMock(18L, scheduling, "Monday", "18:00:00");

		inSubscripion2.setSettings(Collections.singletonMap("source", "Nmeteo.ARABIE SAOUDITE.Riyadh.weather"));

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, theObjectData.getApiId(caller));
		theParams.put("application_id", ApplicationData.getData(inApplication2).getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List theResultAsList = (List) theResult;
		Assert.assertEquals(2, theResultAsList.size());

		Assert.assertTrue(theResultAsList.get(0) instanceof Map);
		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResultAsList.get(0);

		Assert.assertEquals(theResultAsMap.get("id"), theSub.getApiId(caller));
		Assert.assertEquals(theResultAsMap.get("application_id"), theSub.getApplication().getApiId(caller));
		Assert.assertEquals(theResultAsMap.get("object_id"), theSub.getObject().getApiId(caller));

		final Map<String, Object> resultSettings = (Map<String, Object>) theResultAsMap.get("settings");
		Assert.assertEquals(resultSettings.get("source"), "Nmeteo.ARABIE SAOUDITE.Riyadh.weather");

		final Map<String, Object> schedMap = ((List<Map<String, Object>>) theResultAsMap.get("scheduling")).get(0);
		Assert.assertEquals(2, schedMap.size());
		Assert.assertEquals(schedMap.get("type"), scheduling.getType().toString());
		final Map<String, Object> atomMap = (Map<String, Object>) schedMap.get(Weekday.MONDAY.getValue());
		Assert.assertEquals(18, atomMap.get("time_h"));
		Assert.assertEquals(0, atomMap.get("time_m"));

	}

	@Test(expected = NoSuchObjectException.class)
	public void testNoSuchObject() throws APIException {
		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "importequoi");

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	@Test(expected = NoSuchApplicationException.class)
	public void testNoSuchApplication() throws APIException {
		final Lang frLang = getFrLang();
		final User theOwner = new UserMock(42, "subs.Get1", net.violet.common.StringShop.EMPTY_STRING, "subs.Get3", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Date now = new Date();
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObjectData theObjectData = VObjectData.getData(theObject);

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, theObjectData.getApiId(caller));
		theParams.put("application_id", "importequoi");

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	@Test
	public void InteractionTriggerRetrivalTest() throws APIException {

		final VObjectData theObjectData = VObjectData.getData(getKowalskyObject());
		final Application inApplication = getMyFirstApplication();
		final SubscriptionData theSub = SubscriptionData.getData(new SubscriptionMock(0, inApplication, theObjectData.getReference()));

		final SubscriptionScheduling scheduling = new SubscriptionSchedulingMock(0, theSub.getReference(), SCHEDULING_TYPE.InteractionTrigger);
		final SubscriptionSchedulingSettings eventSetting = new SubscriptionSchedulingSettingsMock(0, scheduling, InteractionTriggerHandler.EVENT, "MyEvent");
		new SubscriptionSchedulingSettingsMock(0, scheduling, "Monday", "9:12-15:18");
		new SubscriptionSchedulingSettingsMock(0, scheduling, "Tuesday", "10:4-17:8");

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, theObjectData.getApiId(caller));
		theParams.put("application_id", ApplicationData.getData(inApplication).getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		final Map<String, Object> theResultAsMap = (Map<String, Object>) ((List) theResult).get(0);
		final Map<String, Object> schedMap = ((List<Map<String, Object>>) theResultAsMap.get("scheduling")).get(0);
		Assert.assertEquals(3, schedMap.size());
		Assert.assertEquals(schedMap.get("type"), scheduling.getType().toString());
		Assert.assertEquals(eventSetting.getValue(), schedMap.get(InteractionTriggerHandler.EVENT));
		final Map<String, Map<String, Map<String, Integer>>> validity = (Map<String, Map<String, Map<String, Integer>>>) schedMap.get("validity");
		{
			final Map<String, Map<String, Integer>> monday = validity.get("Monday");
			Assert.assertTrue(monday.containsKey("from") && monday.containsKey("to"));
			final Map<String, Integer> from = monday.get("from");
			Assert.assertEquals(2, from.size());
			Assert.assertEquals(9, from.get("time_h").intValue());
			Assert.assertEquals(12, from.get("time_m").intValue());
			final Map<String, Integer> to = monday.get("to");
			Assert.assertEquals(2, to.size());
			Assert.assertEquals(15, to.get("time_h").intValue());
			Assert.assertEquals(18, to.get("time_m").intValue());
		}
		{
			final Map<String, Map<String, Integer>> tuesday = validity.get("Tuesday");
			Assert.assertTrue(tuesday.containsKey("from") && tuesday.containsKey("to"));
			final Map<String, Integer> from = tuesday.get("from");
			Assert.assertEquals(2, from.size());
			Assert.assertEquals(10, from.get("time_h").intValue());
			Assert.assertEquals(4, from.get("time_m").intValue());
			final Map<String, Integer> to = tuesday.get("to");
			Assert.assertEquals(2, to.size());
			Assert.assertEquals(17, to.get("time_h").intValue());
			Assert.assertEquals(8, to.get("time_m").intValue());
		}
	}
}
