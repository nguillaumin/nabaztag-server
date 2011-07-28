package net.violet.platform.api.actions.subscriptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.SubscriptionData;

import org.junit.Assert;
import org.junit.Test;

public class SetSchedulingTest extends AbstractTestBase {

	@Test
	public void testGet() throws APIException {

		final Lang frLang = getFrLang();
		final User theOwner = new UserMock(42156, "subs.Get1", net.violet.common.StringShop.EMPTY_STRING, "subs.Get3", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Date now = new Date();
		final VObject theObject = new VObjectMock(61339, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		final Application application = new ApplicationMock(356L, "bourse", theOwner, now);
		final Subscription subscription = new SubscriptionMock(1L, application, theObject);

		Factories.SUBSCRIPTION_SCHEDULING.create(subscription, SchedulingType.SCHEDULING_TYPE.Daily);

		final Action theAction = new SetScheduling();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, SubscriptionData.getData(subscription).getApiId(caller));

		final Map<String, Object> setting1 = new HashMap<String, Object>();
		setting1.put("type", "NewContentWithFrequency");
		setting1.put("frequency", "Often");

		final Map<String, Object> setting2 = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put("time_h", 17);
		atom.put("time_m", 0);
		setting2.put("type", "Daily");
		setting2.put("Monday", atom);

		final List<Map<String, Object>> settingsList = new ArrayList<Map<String, Object>>();
		settingsList.add(setting1);
		settingsList.add(setting2);

		theParams.put("scheduling", settingsList);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

		final List<SubscriptionScheduling> theList = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(subscription);
		Assert.assertEquals(2, theList.size());

		SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(subscription, SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency).get(0);
		Map<String, SubscriptionSchedulingSettings> scheSettings = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findAllBySubscriptionSchedulingAsMap(scheduling);
		Assert.assertEquals(2, scheSettings.size());
		Assert.assertEquals("Often", scheSettings.get("Frequency").getValue());

		scheduling = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(subscription, SchedulingType.SCHEDULING_TYPE.Daily).get(0);
		scheSettings = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findAllBySubscriptionSchedulingAsMap(scheduling);
		Assert.assertEquals(1, scheSettings.size());
		Assert.assertEquals("17:00:00", scheSettings.get("Monday").getValue());

		Assert.assertTrue(theResult instanceof List);
		final List theResultAsList = (List) theResult;
		Assert.assertTrue(theResultAsList.get(0) instanceof SchedulingInformationMap);

	}
}
