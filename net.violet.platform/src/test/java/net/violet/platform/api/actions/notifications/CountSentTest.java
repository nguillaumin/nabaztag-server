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
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.NotificationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class CountSentTest extends AbstractTestBase {

	@Test
	public void countSent() throws APIException {
		final Application theEarApplication = Factories.APPLICATION.findByName(Application.NativeApplication.EARS_COMMUNION.getName());
		final User theUserSender = new UserMock(0, "blabla", "12345", "test1@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		final User theUserRecipient = new UserMock(0, "blabla2", "12345", "test2@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);

		final VObject theObjectSender = new VObjectMock(0, "F00000300001", "sender", theUserSender, HARDWARE.V2, getParisTimezone(), getFrLang());
		final VObject theObjectRecipient = new VObjectMock(0, "F00000300002", "recipient", theUserRecipient, HARDWARE.V2, getParisTimezone(), getFrLang());

		new NotificationMock(0, theObjectSender, theObjectRecipient, theEarApplication, NOTIFICATION_STATUS.PENDING);
		new NotificationMock(0, getBrewsterObject(), theObjectRecipient, theEarApplication, NOTIFICATION_STATUS.CANCELLED);
		new NotificationMock(0, getKowalskyObject(), theObjectRecipient, theEarApplication, NOTIFICATION_STATUS.REJECTED);
		new NotificationMock(0, theObjectRecipient, getPrivateObject(), theEarApplication, NOTIFICATION_STATUS.PENDING);

		final Action theAction = new CountSent();
		final ApplicationAPICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String objectRecipient = VObjectData.getData(theObjectRecipient).getApiId(caller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, objectRecipient);
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theUserRecipient), theCalendar.getTime()));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertEquals(1, theResult);

	}

	@Test(expected = NoSuchObjectException.class)
	public void noSuchObject() throws APIException {

		final Action theAction = new CountSent();
		final ApplicationAPICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "1245646");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidSessionException.class)
	public void invalidSession() throws APIException {
		final Action theAction = new CountSent();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, VObjectData.getData(getKowalskyObject()).getApiId(getPublicApplicationAPICaller()));
		theParams.put(ActionParam.SESSION_PARAM_KEY, "123453154");
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = ForbiddenException.class)
	public void forbidden() throws APIException {
		final Action theAction = new CountSent();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, VObjectData.getData(getKowalskyObject()).getApiId(getPublicApplicationAPICaller()));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(getPublicApplicationAPICaller(), UserData.getData(getPrivateUser()), theCalendar.getTime()));
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
		theAction.processRequest(theActionParam);
	}
}
