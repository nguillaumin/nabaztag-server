package net.violet.platform.api.authentication;

import java.util.Calendar;
import java.util.Date;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class SessionManagerTest extends MockTestBase {

	@Test
	public void validSessionTest() {
		final User theOwner = new UserMock(42, "myLogin", "myPassword", "myEmail@gmail.com", getFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		final String sessionId = SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime());

		Assert.assertTrue(SessionManager.isSessionValid(sessionId, caller));
	}

	@Test
	public void invalidSessionTest() {
		final User theOwner = new UserMock(42, "myLogin", "myPassword", "myEmail@gmail.com", getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, -1);

		final String sessionId = SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime());

		Assert.assertFalse(SessionManager.isSessionValid(sessionId, caller));
	}

	@Test
	public void getUserTest() {
		final User theOwner = new UserMock(42, "myLogin", "myPassword", "myEmail@gmail.com", getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		final String sessionId = SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime());

		final UserData theUser = SessionManager.getUserFromValidSessionId(sessionId);
		Assert.assertEquals(theOwner.getId().longValue(), theUser.getId());
	}

	@Test(expected = InvalidSessionException.class)
	public void invalidSessionId() throws APIException {
		new UserMock(42, "myLogin", "myPassword", "myEmail@gmail.com", getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		SessionManager.getUserFromSessionId("blabla", caller);
	}
}
