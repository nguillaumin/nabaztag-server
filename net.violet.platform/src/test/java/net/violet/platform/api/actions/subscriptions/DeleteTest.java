package net.violet.platform.api.actions.subscriptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.applications.EarsCommunionHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.MessengerMock;
import net.violet.platform.datamodel.mock.NotificationMock;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class DeleteTest extends AbstractTestBase {

	@Test
	public void deleteEarCommunion_PendingMode() throws APIException {

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

		final Action theAction = new Delete();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, SubscriptionData.getData(subscription).getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);

		Assert.assertTrue(NotificationData.getSent(VObjectData.getData(theObjectRecipient), 0, 0).isEmpty());
		Assert.assertEquals(0, NotificationData.getReceived(VObjectData.getData(theObjectSender), 0, 0).size());
		Assert.assertEquals(0, NotificationData.getSent(VObjectData.getData(theObjectSender), 0, 0).size());
		final List<NotificationData> theReceivedList = NotificationData.getReceived(VObjectData.getData(theObjectRecipient), 0, 0);
		Assert.assertEquals(1, theReceivedList.size());
		Assert.assertEquals(NOTIFICATION_STATUS.CANCELLED.toString(), theReceivedList.get(0).getStatus());
		Assert.assertTrue(theReceivedList.get(0).getSender().equals(VObjectData.getData(theObjectSender)));
		Assert.assertTrue(theReceivedList.get(0).getRecipient().equals(VObjectData.getData(theObjectRecipient)));
		Assert.assertTrue(theReceivedList.get(0).getApplication().equals(ApplicationData.getData(theEarApplication)));

		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectRecipient).isEmpty());
		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectSender).isEmpty());

		Assert.assertEquals(0, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipient(Factories.MESSENGER.getByObject(theObjectRecipient), 0, 0).size());

	}

	@Test
	public void deleteEarCommunion_CommunionOKMode() throws APIException {

		final VObject theObjectSender = getKowalskyObject();
		final VObject theObjectRecipient = getPrivateObject();

		new MessengerMock(0, getKowalskyUser(), getKowalskyObject(), "sender");
		new MessengerMock(0, getPrivateUser(), getPrivateObject(), "recipient");

		final Application theEarApplication = Application.NativeApplication.EARS_COMMUNION.getApplication();

		final Map<String, Object> theObjectSenderSettings = new HashMap<String, Object>();
		theObjectSenderSettings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, theObjectRecipient.getId());
		theObjectSenderSettings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.ACCEPTED.toString());

		final Subscription subscription = new SubscriptionMock(0, theEarApplication, theObjectSender, theObjectSenderSettings);

		final Map<String, Object> theObjectRecipientSettings = new HashMap<String, Object>();
		theObjectRecipientSettings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, theObjectSender.getId());
		theObjectRecipientSettings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.ACCEPTED.toString());

		new SubscriptionMock(0, theEarApplication, theObjectRecipient, theObjectRecipientSettings);

		final Action theAction = new Delete();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, SubscriptionData.getData(subscription).getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);

		Assert.assertTrue(NotificationData.getSent(VObjectData.getData(theObjectRecipient), 0, 0).isEmpty());
		Assert.assertEquals(0, NotificationData.getReceived(VObjectData.getData(theObjectSender), 0, 0).size());
		Assert.assertEquals(0, NotificationData.getSent(VObjectData.getData(theObjectSender), 0, 0).size());
		final List<NotificationData> theReceivedList = NotificationData.getReceived(VObjectData.getData(theObjectRecipient), 0, 0);
		Assert.assertEquals(1, theReceivedList.size());
		Assert.assertEquals(NOTIFICATION_STATUS.FINISHED.toString(), theReceivedList.get(0).getStatus());
		Assert.assertTrue(theReceivedList.get(0).getSender().equals(VObjectData.getData(theObjectSender)));
		Assert.assertTrue(theReceivedList.get(0).getRecipient().equals(VObjectData.getData(theObjectRecipient)));
		Assert.assertTrue(theReceivedList.get(0).getApplication().equals(ApplicationData.getData(theEarApplication)));

		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectRecipient).isEmpty());
		Assert.assertTrue(Factories.SUBSCRIPTION.findByApplicationAndObject(theEarApplication, theObjectSender).isEmpty());

		Assert.assertEquals(1, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipient(Factories.MESSENGER.getByObject(theObjectRecipient), 0, 0).size());

	}
}
