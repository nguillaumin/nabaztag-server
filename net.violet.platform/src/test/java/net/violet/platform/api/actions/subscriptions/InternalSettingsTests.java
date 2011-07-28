package net.violet.platform.api.actions.subscriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.CipherTools;

import org.junit.Assert;
import org.junit.Test;

public class InternalSettingsTests extends AbstractTestBase {

	@Test
	public void testClock() throws APIException {

		final Lang frLang = getFrLang();
		final User theOwner = new UserMock(42126, "subs.Get4", net.violet.common.StringShop.EMPTY_STRING, "subs.Get5", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Date now = new Date();
		final VObject theObject = new VObjectMock(613219, "F00004078007", "test", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		final Application application = Factories.APPLICATION.findByName("net.violet.clock");

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(Create.APPLICATION_ID, ApplicationData.getData(application).getApiId(caller));
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObject).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("types", Arrays.asList("1"));
		settings.put("languages", Arrays.asList("1", "2"));

		theParams.put("settings", settings);

		final Map<String, Object> scheduling1 = new HashMap<String, Object>();
		scheduling1.put("type", "NewContentWithFrequency");
		scheduling1.put("frequency", "Often");

		final Map<String, Object> scheduling2 = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put("time_h", 17);
		atom.put("time_m", 0);
		scheduling2.put("type", "Daily");
		scheduling2.put("Monday", atom);

		final List<Map<String, Object>> schedulingList = new ArrayList<Map<String, Object>>();
		schedulingList.add(scheduling1);
		schedulingList.add(scheduling2);

		theParams.put("scheduling", schedulingList);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);
		final Map theResultAsMap = (Map) theResult;

		final Subscription theSubscription = Factories.SUBSCRIPTION.find(SubscriptionData.findByAPIId((String) theResultAsMap.get("id"), theActionParam.getCallerAPIKey()).getId());

		Assert.assertEquals("[1,2]", theSubscription.getSettings().get("languages").toString());
		Assert.assertEquals("[1]", theSubscription.getSettings().get("types").toString());

		final List<SubscriptionScheduling> theResultList = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);

		Assert.assertEquals(2, theResultList.size());
		/*
		 * for(SubscriptionScheduling theSubscriptionScheduling : theResultList)
		 * { assertEquals(2,Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.
		 * findAllBySubscriptionScheduling(theSubscriptionScheduling).size()); }
		 */
	}

	@Test
	public void testMail() throws APIException {

		final Lang frLang = getFrLang();
		final User theOwner = new UserMock(42798, "subs.Get4", net.violet.common.StringShop.EMPTY_STRING, "subs.Get5", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Date now = new Date();
		final VObject theObject = new VObjectMock(61079, "F00004000007", "test", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		final Application application = Factories.APPLICATION.findByName("net.violet.mail");

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(Create.APPLICATION_ID, ApplicationData.getData(application).getApiId(caller));
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObject).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("login", "marfab_fr");
		settings.put("password", "fab0607");
		settings.put("host", "pop.mail.yahoo.fr");
		settings.put("type", "pop");
		settings.put("secure", "1");

		theParams.put("settings", settings);

		final Map<String, Object> scheduling1 = new HashMap<String, Object>();
		scheduling1.put("type", "NewContent");

		final List<Map<String, Object>> schedulingList = new ArrayList<Map<String, Object>>();
		schedulingList.add(scheduling1);

		theParams.put("scheduling", schedulingList);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);
		final Map theResultAsMap = (Map) theResult;

		final Subscription theSubscription = Factories.SUBSCRIPTION.find(SubscriptionData.findByAPIId((String) theResultAsMap.get("id"), theActionParam.getCallerAPIKey()).getId());

		Assert.assertEquals(7, theSubscription.getSettings().size());

		Assert.assertEquals(CipherTools.cipher("marfab_fr"), theSubscription.getSettings().get("login").toString());
		Assert.assertEquals(CipherTools.cipher("fab0607"), theSubscription.getSettings().get("password").toString());
		Assert.assertEquals("pop.mail.yahoo.fr", theSubscription.getSettings().get("host").toString());
		Assert.assertEquals("pop", theSubscription.getSettings().get("type").toString());
		Assert.assertEquals("1", theSubscription.getSettings().get("secure").toString());

		final List<SubscriptionScheduling> theResultList = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);
		Assert.assertEquals(1, theResultList.size());
	}

	@Test
	public void testTwitter() throws APIException {

		final Lang frLang = getFrLang();
		final User theOwner = new UserMock(42123, "subs.Get4", net.violet.common.StringShop.EMPTY_STRING, "subs.Get5", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Date now = new Date();
		final VObject theObject = new VObjectMock(65719, "F00004000007", "test", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		final Application application = Factories.APPLICATION.findByName("net.violet.twitter");

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(Create.APPLICATION_ID, ApplicationData.getData(application).getApiId(caller));
		theParams.put(Create.OBJECT_ID, VObjectData.getData(theObject).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("language", "1");
		settings.put("login", "testsViolet");
		settings.put("password", "violet");

		theParams.put("settings", settings);

		final Map<String, Object> scheduling1 = new HashMap<String, Object>();
		scheduling1.put("type", "NewContent");

		final List<Map<String, Object>> schedulingList = new ArrayList<Map<String, Object>>();
		schedulingList.add(scheduling1);

		theParams.put("scheduling", schedulingList);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);
		final Map theResultAsMap = (Map) theResult;

		final Subscription theSubscription = Factories.SUBSCRIPTION.find(SubscriptionData.findByAPIId((String) theResultAsMap.get("id"), theActionParam.getCallerAPIKey()).getId());

		Assert.assertEquals(4, theSubscription.getSettings().size());

		Assert.assertEquals("testsViolet", theSubscription.getSettings().get("login").toString());
		Assert.assertEquals("10", theSubscription.getSettings().get("nbNews").toString());

		final List<SubscriptionScheduling> theResultList = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);
		Assert.assertEquals(1, theResultList.size());
	}

}
