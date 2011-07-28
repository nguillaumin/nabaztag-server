package net.violet.platform.api.actions.notifications;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.api.exceptions.NotificationAlreadyExistsException;
import net.violet.platform.api.exceptions.UnsupportedException;
import net.violet.platform.applications.EarsCommunionHandler;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.MessengerMock;
import net.violet.platform.datamodel.mock.NotificationMock;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class AddTest extends AbstractTestBase {

	@Test
	public void add() throws APIException {
		final Application theEarApplication = Factories.APPLICATION.findByName(Application.NativeApplication.EARS_COMMUNION.getName());
		final User theUserSender = new UserMock(0, "blabla", "12345", "test1@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		final User theUserRecipient = new UserMock(0, "blabla2", "12345", "test2@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);

		final VObject theObjectSender = new VObjectMock(0, "F00000300001", "sender", theUserSender, HARDWARE.V2, getParisTimezone(), getFrLang());
		final VObject theObjectRecipient = new VObjectMock(0, "F00000300002", "recipient", theUserRecipient, HARDWARE.V2, getParisTimezone(), getFrLang());

		new MessengerMock(0, theUserSender, theObjectSender, "sender");
		new MessengerMock(0, theUserRecipient, theObjectRecipient, "recipient");

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, theObjectRecipient.getId());
		settings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.PENDING.toString());

		new SubscriptionMock(0, theEarApplication, theObjectSender, settings);

		final Action theAction = new Add();
		final ApplicationAPICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String objectSenderId = VObjectData.getData(theObjectSender).getApiId(caller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, objectSenderId);
		theParams.put(Add.APPLICATION_ID, ApplicationData.getData(theEarApplication).getApiId(caller));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theUserSender), theCalendar.getTime()));
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

		final Map<String, Object> settingsEar = Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectSender).get(0).getSettings();
		Assert.assertEquals(NOTIFICATION_STATUS.PENDING.toString(), settingsEar.get(EarsCommunionHandler.STATUS));
		Assert.assertEquals(theObjectRecipient.getId(), settingsEar.get(EarsCommunionHandler.FRIEND_OBJECT_ID));

		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectRecipient).isEmpty());

		Assert.assertEquals(1, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipient(Factories.MESSENGER.getByObject(theObjectRecipient), 0, 0).size());
	}

	@Test
	public void addButFriendObjectHasSendPendingAlso() throws APIException {
		final Application theEarApplication = Factories.APPLICATION.findByName(Application.NativeApplication.EARS_COMMUNION.getName());
		final User theUserSender = new UserMock(0, "blabla", "12345", "test1@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		final User theUserRecipient = new UserMock(0, "blabla2", "12345", "test2@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);

		final VObject theObjectSender = new VObjectMock(0, "F00000300001", "sender", theUserSender, HARDWARE.V2, getParisTimezone(), getFrLang());
		final VObject theObjectRecipient = new VObjectMock(0, "F00000300002", "recipient", theUserRecipient, HARDWARE.V2, getParisTimezone(), getFrLang());

		new MessengerMock(0, theUserSender, theObjectSender, "sender");
		new MessengerMock(0, theUserRecipient, theObjectRecipient, "recipient");

		final Map<String, Object> recipientSettings = new HashMap<String, Object>();
		recipientSettings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, theObjectSender.getId());
		recipientSettings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.PENDING.toString());

		new SubscriptionMock(0, theEarApplication, theObjectRecipient, recipientSettings);

		new NotificationMock(0, theObjectRecipient, theObjectSender, theEarApplication, NOTIFICATION_STATUS.PENDING);

		final Map<String, Object> senderSettings = new HashMap<String, Object>();
		senderSettings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, theObjectRecipient.getId());
		new SubscriptionMock(0, theEarApplication, theObjectSender, senderSettings);

		final Action theAction = new Add();
		final ApplicationAPICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String objectSenderId = VObjectData.getData(theObjectSender).getApiId(caller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectSenderId);
		theParams.put(Add.APPLICATION_ID, ApplicationData.getData(theEarApplication).getApiId(caller));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theUserSender), theCalendar.getTime()));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);

		Assert.assertTrue(NotificationData.getSent(VObjectData.getData(theObjectRecipient), 0, 0).isEmpty());
		Assert.assertEquals(0, NotificationData.getReceived(VObjectData.getData(theObjectSender), 0, 0).size());
		Assert.assertEquals(0, NotificationData.getSent(VObjectData.getData(theObjectSender), 0, 0).size());
		final List<NotificationData> theReceivedList = NotificationData.getReceived(VObjectData.getData(theObjectRecipient), 0, 0);
		Assert.assertEquals(1, theReceivedList.size());
		Assert.assertEquals(NOTIFICATION_STATUS.ACCEPTED.toString(), theReceivedList.get(0).getStatus());
		Assert.assertTrue(theReceivedList.get(0).getSender().equals(VObjectData.getData(theObjectSender)));
		Assert.assertTrue(theReceivedList.get(0).getRecipient().equals(VObjectData.getData(theObjectRecipient)));
		Assert.assertTrue(theReceivedList.get(0).getApplication().equals(ApplicationData.getData(theEarApplication)));

		Map<String, Object> settingsEar = Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectSender).get(0).getSettings();
		Assert.assertEquals(NOTIFICATION_STATUS.ACCEPTED.toString(), settingsEar.get(EarsCommunionHandler.STATUS));
		Assert.assertEquals(theObjectRecipient.getId(), settingsEar.get(EarsCommunionHandler.FRIEND_OBJECT_ID));

		settingsEar = Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectRecipient).get(0).getSettings();
		Assert.assertEquals(NOTIFICATION_STATUS.ACCEPTED.toString(), settingsEar.get(EarsCommunionHandler.STATUS));
		Assert.assertEquals(theObjectSender.getId(), settingsEar.get(EarsCommunionHandler.FRIEND_OBJECT_ID));

		Assert.assertEquals(1, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipient(Factories.MESSENGER.getByObject(theObjectRecipient), 0, 0).size());
	}

	@Test(expected = NotificationAlreadyExistsException.class)
	public void alreadyExists() throws APIException {
		final Application theEarApplication = Factories.APPLICATION.findByName(Application.NativeApplication.EARS_COMMUNION.getName());
		new NotificationMock(0, getKowalskyObject(), getPrivateObject(), theEarApplication, NOTIFICATION_STATUS.PENDING);

		final Action theAction = new Add();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, VObjectData.getData(getKowalskyObject()).getApiId(getPublicApplicationAPICaller()));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(getPublicApplicationAPICaller(), UserData.getData(getKowalskyUser()), theCalendar.getTime()));
		theParams.put(Add.APPLICATION_ID, ApplicationData.getData(theEarApplication).getApiId(getPublicApplicationAPICaller()));
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = UnsupportedException.class)
	public void unsupportedApplication() throws APIException {
		final Application theMailApplication = Factories.APPLICATION.findByName(Application.NativeApplication.MAIL.getName());
		final Action theAction = new Add();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, VObjectData.getData(getKowalskyObject()).getApiId(getPublicApplicationAPICaller()));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(getPublicApplicationAPICaller(), UserData.getData(getKowalskyUser()), theCalendar.getTime()));
		theParams.put(Add.APPLICATION_ID, ApplicationData.getData(theMailApplication).getApiId(getPublicApplicationAPICaller()));
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = NoSuchSubscriptionException.class)
	public void noSubscription() throws APIException {
		final Application theEarApplication = Factories.APPLICATION.findByName(Application.NativeApplication.EARS_COMMUNION.getName());
		final Action theAction = new Add();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, VObjectData.getData(getKowalskyObject()).getApiId(getPublicApplicationAPICaller()));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(getPublicApplicationAPICaller(), UserData.getData(getKowalskyUser()), theCalendar.getTime()));
		theParams.put(Add.APPLICATION_ID, ApplicationData.getData(theEarApplication).getApiId(getPublicApplicationAPICaller()));
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = NoSuchApplicationException.class)
	public void noSuchApplication() throws APIException {

		final Action theAction = new Add();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, VObjectData.getData(getKowalskyObject()).getApiId(getPublicApplicationAPICaller()));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(getPublicApplicationAPICaller(), UserData.getData(getKowalskyUser()), theCalendar.getTime()));
		theParams.put(Add.APPLICATION_ID, "123123");
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
		theAction.processRequest(theActionParam);

	}

	@Test(expected = NoSuchObjectException.class)
	public void noSuchObject() throws APIException {

		final Action theAction = new Add();
		final ApplicationAPICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String objectId = "1245646";

		theParams.put(ActionParam.MAIN_PARAM_KEY, objectId);
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(getKowalskyUser()), theCalendar.getTime()));
		theParams.put(Add.APPLICATION_ID, "123453154");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);

	}

	@Test(expected = InvalidSessionException.class)
	public void invalidSession() throws APIException {
		final Action theAction = new Add();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, VObjectData.getData(getKowalskyObject()).getApiId(getPublicApplicationAPICaller()));
		theParams.put(ActionParam.SESSION_PARAM_KEY, "123453154");
		theParams.put(Add.APPLICATION_ID, "123453154");
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = ForbiddenException.class)
	public void forbidden() throws APIException {
		final Action theAction = new Add();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, VObjectData.getData(getKowalskyObject()).getApiId(getPublicApplicationAPICaller()));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(getPublicApplicationAPICaller(), UserData.getData(getPrivateUser()), theCalendar.getTime()));
		theParams.put(Add.APPLICATION_ID, "123453154");
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
		theAction.processRequest(theActionParam);
	}
}
