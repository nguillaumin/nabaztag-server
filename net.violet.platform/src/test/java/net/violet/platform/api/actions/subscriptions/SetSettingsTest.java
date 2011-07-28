package net.violet.platform.api.actions.subscriptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.applications.EarsCommunionHandler;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.MessengerMock;
import net.violet.platform.datamodel.mock.NotificationMock;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class SetSettingsTest extends AbstractTestBase {

	@Test
	public void testGet() throws APIException {

		final Lang frLang = getFrLang();
		final User theOwner = new UserMock(427, "subs.Get1", net.violet.common.StringShop.EMPTY_STRING, "subs.Get3", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Date now = new Date();
		final VObject theObject = new VObjectMock(61119, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		final Application application = new ApplicationMock(856L, "bourse", theOwner, now);
		final Subscription subscription = new SubscriptionMock(189L, application, theObject);

		Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("key1", "value1");
		settings.put("key2", "value2");
		subscription.setSettings(settings);

		settings = subscription.getSettings();
		Assert.assertEquals(2, settings.size());

		final Action theAction = new SetSettings();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, SubscriptionData.getData(subscription).getApiId(caller));

		settings = new HashMap<String, Object>();
		settings.put("key2", "value2-2");
		settings.put("key3", "value3");

		theParams.put("settings", settings);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);

		settings = subscription.getSettings();
		Assert.assertEquals(2, settings.size());

		Assert.assertEquals("value2-2", subscription.getSettings().get("key2"));
		Assert.assertEquals("value3", subscription.getSettings().get("key3"));

	}

	@Test
	public void testInternalSettings() throws APIException {

		final Lang frLang = getFrLang();
		final User theOwner = new UserMock(123042, "subs.Get1", net.violet.common.StringShop.EMPTY_STRING, "subs.Get3", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Date now = new Date();
		final VObject theObject = new VObjectMock(61617, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		final Application application = new ApplicationMock(3589L, "mail", theOwner, now);
		final Subscription subscription = new SubscriptionMock(31L, application, theObject);

		Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("key1", "value1");
		settings.put("key2", "value2");
		subscription.setSettings(settings);

		settings = subscription.getSettings();
		Assert.assertEquals(2, settings.size());

		final Action theAction = new SetSettings();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, SubscriptionData.getData(subscription).getApiId(caller));
		settings = new HashMap<String, Object>();
		settings.put("key2", "value2-2");
		settings.put("key3", "value3");

		theParams.put("settings", settings);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);

		settings = subscription.getSettings();
		Assert.assertEquals(2, settings.size());

		Assert.assertEquals("value2-2", subscription.getSettings().get("key2"));
		Assert.assertEquals("value3", subscription.getSettings().get("key3"));

	}

	@Test
	public void updateSamePartnerInEarCommunionSettings() throws APIException {

		final VObject theObjectSender = getKowalskyObject();
		final VObject theObjectRecipient = getPrivateObject();

		new MessengerMock(0, getKowalskyUser(), getKowalskyObject(), "sender");
		new MessengerMock(0, getPrivateUser(), getPrivateObject(), "recipient");

		final Application theEarApplication = Application.NativeApplication.EARS_COMMUNION.getApplication();

		final Map<String, Object> theObjectSettings = new HashMap<String, Object>();
		theObjectSettings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, theObjectRecipient.getId());
		theObjectSettings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.PENDING.toString());

		final Subscription subscription = new SubscriptionMock(0, theEarApplication, theObjectSender, theObjectSettings);

		new NotificationMock(0, theObjectSender, theObjectRecipient, theEarApplication, NOTIFICATION_STATUS.PENDING);

		final Action theAction = new SetSettings();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, SubscriptionData.getData(subscription).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, VObjectData.getData(theObjectRecipient).getApiId(caller));

		theParams.put("settings", settings);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);

		Assert.assertTrue(NotificationData.getSent(VObjectData.getData(theObjectRecipient), 0, 0).isEmpty());
		Assert.assertEquals(0, NotificationData.getReceived(VObjectData.getData(theObjectSender), 0, 0).size());
		Assert.assertEquals(1, NotificationData.getSent(VObjectData.getData(theObjectSender), 0, 0).size());
		final List<NotificationData> theReceivedList = NotificationData.getReceived(VObjectData.getData(theObjectRecipient), 0, 0);
		Assert.assertEquals(1, theReceivedList.size());
		Assert.assertEquals(NOTIFICATION_STATUS.PENDING.toString(), theReceivedList.get(0).getStatus());
		Assert.assertTrue(theReceivedList.get(0).getSender().equals(VObjectData.getData(theObjectSender)));
		Assert.assertTrue(theReceivedList.get(0).getRecipient().equals(VObjectData.getData(theObjectRecipient)));
		Assert.assertTrue(theReceivedList.get(0).getApplication().equals(ApplicationData.getData(theEarApplication)));

		Assert.assertEquals(subscription.getId(), Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectSender).get(0).getId());

		final Map<String, Object> settingsEar = Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectSender).get(0).getSettings();
		Assert.assertEquals(NOTIFICATION_STATUS.PENDING.toString(), settingsEar.get(EarsCommunionHandler.STATUS));
		Assert.assertEquals(theObjectRecipient.getId(), settingsEar.get(EarsCommunionHandler.FRIEND_OBJECT_ID));

		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectRecipient).isEmpty());

		Assert.assertEquals(0, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipient(Factories.MESSENGER.getByObject(theObjectRecipient), 0, 0).size());

	}

	@Test
	public void updateAnotherPartnerInEarCommunionSettings_PendingMode() throws APIException {

		final VObject theObjectSender = getKowalskyObject();
		final VObject theObjectRecipient = getPrivateObject();

		final User theAnotherUserRecipient = new UserMock(0, "blabla2", "12345", "test2@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);

		final VObject theAnotherObjectRecipient = new VObjectMock(0, "F00000300002", "newrecipient", theAnotherUserRecipient, HARDWARE.V2, getParisTimezone(), getFrLang());

		new MessengerMock(0, getKowalskyUser(), getKowalskyObject(), "sender");
		new MessengerMock(0, getPrivateUser(), getPrivateObject(), "recipient");
		new MessengerMock(0, theAnotherUserRecipient, theAnotherObjectRecipient, "newrecipient");

		final Application theEarApplication = Application.NativeApplication.EARS_COMMUNION.getApplication();

		final Map<String, Object> theObjectSettings = new HashMap<String, Object>();
		theObjectSettings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, theObjectRecipient.getId());
		theObjectSettings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.PENDING.toString());

		final Subscription subscription = new SubscriptionMock(0, theEarApplication, theObjectSender, theObjectSettings);

		new NotificationMock(0, theObjectSender, theObjectRecipient, theEarApplication, NOTIFICATION_STATUS.PENDING);

		final Action theAction = new SetSettings();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, SubscriptionData.getData(subscription).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, VObjectData.getData(theAnotherObjectRecipient).getApiId(caller));

		theParams.put("settings", settings);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);

		Assert.assertTrue(NotificationData.getSent(VObjectData.getData(theObjectRecipient), 0, 0).isEmpty());
		Assert.assertEquals(0, NotificationData.getReceived(VObjectData.getData(theObjectSender), 0, 0).size());
		Assert.assertEquals(1, NotificationData.getSent(VObjectData.getData(theObjectSender), 0, 0).size());
		List<NotificationData> theReceivedList = NotificationData.getReceived(VObjectData.getData(theObjectRecipient), 0, 0);
		Assert.assertEquals(1, theReceivedList.size());
		Assert.assertEquals(NOTIFICATION_STATUS.CANCELLED.toString(), theReceivedList.get(0).getStatus());
		Assert.assertTrue(theReceivedList.get(0).getSender().equals(VObjectData.getData(theObjectSender)));
		Assert.assertTrue(theReceivedList.get(0).getRecipient().equals(VObjectData.getData(theObjectRecipient)));
		Assert.assertTrue(theReceivedList.get(0).getApplication().equals(ApplicationData.getData(theEarApplication)));

		theReceivedList = NotificationData.getReceived(VObjectData.getData(theAnotherObjectRecipient), 0, 0);
		Assert.assertEquals(1, theReceivedList.size());
		Assert.assertEquals(NOTIFICATION_STATUS.PENDING.toString(), theReceivedList.get(0).getStatus());
		Assert.assertTrue(theReceivedList.get(0).getSender().equals(VObjectData.getData(theObjectSender)));
		Assert.assertTrue(theReceivedList.get(0).getRecipient().equals(VObjectData.getData(theAnotherObjectRecipient)));
		Assert.assertTrue(theReceivedList.get(0).getApplication().equals(ApplicationData.getData(theEarApplication)));

		Assert.assertEquals(subscription.getId(), Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectSender).get(0).getId());
		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectRecipient).isEmpty());
		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theAnotherObjectRecipient).isEmpty());

		final Map<String, Object> settingsEar = Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectSender).get(0).getSettings();
		Assert.assertEquals(NOTIFICATION_STATUS.PENDING.toString(), settingsEar.get(EarsCommunionHandler.STATUS));
		Assert.assertEquals(theAnotherObjectRecipient.getId(), settingsEar.get(EarsCommunionHandler.FRIEND_OBJECT_ID));

		Assert.assertEquals(1, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipient(Factories.MESSENGER.getByObject(theAnotherObjectRecipient), 0, 0).size());
	}

	@Test
	public void updateAnotherPartnerInEarCommunionSettings_CommunionOKMode() throws APIException {

		final VObject theObjectSender = getKowalskyObject();
		final VObject theObjectRecipient = getPrivateObject();

		final User theAnotherUserRecipient = new UserMock(0, "blabla2", "12345", "test2@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);

		final VObject theAnotherObjectRecipient = new VObjectMock(0, "F00000300002", "newrecipient", theAnotherUserRecipient, HARDWARE.V2, getParisTimezone(), getFrLang());

		new MessengerMock(0, getKowalskyUser(), getKowalskyObject(), "sender");
		new MessengerMock(0, getPrivateUser(), getPrivateObject(), "recipient");
		new MessengerMock(0, theAnotherUserRecipient, theAnotherObjectRecipient, "newrecipient");

		final Application theEarApplication = Application.NativeApplication.EARS_COMMUNION.getApplication();

		final Map<String, Object> theObjectSenderSettings = new HashMap<String, Object>();
		theObjectSenderSettings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, theObjectRecipient.getId());
		theObjectSenderSettings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.ACCEPTED.toString());

		final Subscription subscription = new SubscriptionMock(0, theEarApplication, theObjectSender, theObjectSenderSettings);

		final Map<String, Object> theObjectRecipientSettings = new HashMap<String, Object>();
		theObjectRecipientSettings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, theObjectSender.getId());
		theObjectRecipientSettings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.ACCEPTED.toString());

		new SubscriptionMock(0, theEarApplication, theObjectRecipient, theObjectRecipientSettings);

		final Action theAction = new SetSettings();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, SubscriptionData.getData(subscription).getApiId(caller));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, VObjectData.getData(theAnotherObjectRecipient).getApiId(caller));

		theParams.put("settings", settings);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);

		Assert.assertTrue(NotificationData.getSent(VObjectData.getData(theObjectRecipient), 0, 0).isEmpty());
		Assert.assertEquals(0, NotificationData.getReceived(VObjectData.getData(theObjectSender), 0, 0).size());
		Assert.assertEquals(1, NotificationData.getSent(VObjectData.getData(theObjectSender), 0, 0).size());
		List<NotificationData> theReceivedList = NotificationData.getReceived(VObjectData.getData(theObjectRecipient), 0, 0);
		Assert.assertEquals(1, theReceivedList.size());
		Assert.assertEquals(NOTIFICATION_STATUS.FINISHED.toString(), theReceivedList.get(0).getStatus());
		Assert.assertTrue(theReceivedList.get(0).getSender().equals(VObjectData.getData(theObjectSender)));
		Assert.assertTrue(theReceivedList.get(0).getRecipient().equals(VObjectData.getData(theObjectRecipient)));
		Assert.assertTrue(theReceivedList.get(0).getApplication().equals(ApplicationData.getData(theEarApplication)));

		theReceivedList = NotificationData.getReceived(VObjectData.getData(theAnotherObjectRecipient), 0, 0);
		Assert.assertEquals(1, theReceivedList.size());
		Assert.assertEquals(NOTIFICATION_STATUS.PENDING.toString(), theReceivedList.get(0).getStatus());
		Assert.assertTrue(theReceivedList.get(0).getSender().equals(VObjectData.getData(theObjectSender)));
		Assert.assertTrue(theReceivedList.get(0).getRecipient().equals(VObjectData.getData(theAnotherObjectRecipient)));
		Assert.assertTrue(theReceivedList.get(0).getApplication().equals(ApplicationData.getData(theEarApplication)));

		Assert.assertEquals(subscription.getId(), Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectSender).get(0).getId());
		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectRecipient).isEmpty());
		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theAnotherObjectRecipient).isEmpty());

		final Map<String, Object> settingsEar = Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectSender).get(0).getSettings();
		Assert.assertEquals(NOTIFICATION_STATUS.PENDING.toString(), settingsEar.get(EarsCommunionHandler.STATUS));
		Assert.assertEquals(theAnotherObjectRecipient.getId(), settingsEar.get(EarsCommunionHandler.FRIEND_OBJECT_ID));

		Assert.assertEquals(1, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipient(Factories.MESSENGER.getByObject(theAnotherObjectRecipient), 0, 0).size());
		Assert.assertEquals(1, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipient(Factories.MESSENGER.getByObject(theObjectRecipient), 0, 0).size());
	}
}
