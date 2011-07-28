package net.violet.platform.api.actions.notifications;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchNotificationException;
import net.violet.platform.api.exceptions.UnsupportedException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Notification;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.NotificationMock;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class DeleteTest extends AbstractTestBase {

	@Test
	public void delete() throws APIException {
		final Application theEarApplication = Factories.APPLICATION.findByName(Application.NativeApplication.EARS_COMMUNION.getName());

		final Notification theNotification = new NotificationMock(0, getPrivateObject(), getKowalskyObject(), theEarApplication, NOTIFICATION_STATUS.ACCEPTED);

		final Action theAction = new Delete();
		final ApplicationAPICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String notificationId = NotificationData.getData(theNotification).getApiId(caller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, notificationId);
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(getKowalskyUser()), theCalendar.getTime()));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);

		Assert.assertTrue(NotificationData.getSent(VObjectData.getData(getPrivateObject()), 0, 0).isEmpty());
		Assert.assertTrue(NotificationData.getReceived(VObjectData.getData(getPrivateObject()), 0, 0).isEmpty());
		Assert.assertTrue(NotificationData.getSent(VObjectData.getData(getKowalskyObject()), 0, 0).isEmpty());
		Assert.assertTrue(NotificationData.getReceived(VObjectData.getData(getKowalskyObject()), 0, 0).isEmpty());
	}

	@Test(expected = UnsupportedException.class)
	public void unsupportedRequest() throws APIException {
		final Application theEarApplication = Factories.APPLICATION.findByName(Application.NativeApplication.EARS_COMMUNION.getName());
		final Notification theNotification = new NotificationMock(0, getKowalskyObject(), getPrivateObject(), theEarApplication, NOTIFICATION_STATUS.PENDING);

		final Action theAction = new Delete();
		final ApplicationAPICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String notificationId = NotificationData.getData(theNotification).getApiId(caller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, notificationId);
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(getPrivateUser()), theCalendar.getTime()));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = NoSuchNotificationException.class)
	public void noSuchNotification() throws APIException {

		final Action theAction = new Delete();
		final ApplicationAPICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String notificationId = "1245646";

		theParams.put(ActionParam.MAIN_PARAM_KEY, notificationId);
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(getKowalskyUser()), theCalendar.getTime()));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);

	}

	@Test(expected = InvalidSessionException.class)
	public void invalidSession() throws APIException {
		final Action theAction = new Delete();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.SESSION_PARAM_KEY, "123453154");
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = ForbiddenException.class)
	public void forbidden() throws APIException {
		final Application theEarApplication = Factories.APPLICATION.findByName(Application.NativeApplication.EARS_COMMUNION.getName());

		final Action theAction = new Delete();
		final Notification theNotification = new NotificationMock(0, getPrivateObject(), getKowalskyObject(), theEarApplication, NOTIFICATION_STATUS.ACCEPTED);
		final String notificationId = NotificationData.getData(theNotification).getApiId(getPublicApplicationAPICaller());
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);
		theParams.put(ActionParam.MAIN_PARAM_KEY, notificationId);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(getPublicApplicationAPICaller(), UserData.getData(getPrivateUser()), theCalendar.getTime()));
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
		theAction.processRequest(theActionParam);
	}
}
