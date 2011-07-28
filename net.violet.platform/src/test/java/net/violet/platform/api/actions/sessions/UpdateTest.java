package net.violet.platform.api.actions.sessions;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
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

public class UpdateTest extends MockTestBase {

	@Test
	public void updateTest() throws APIException {

		final User theOwner = new UserMock(42, "myLogin", "myPassword", "myEmail@gmail.com", getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.HOUR_OF_DAY, 1);
		final Date expiration = theCal.getTime();
		theCal.add(Calendar.HOUR_OF_DAY, 1);
		final Date newExpiration = theCal.getTime();

		final String sessionId = SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration);

		theParams.put(ActionParam.MAIN_PARAM_KEY, sessionId);
		theParams.put("expiration", newExpiration);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new Update();
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof String);

		final String theResultAsString = theResult.toString();

		Assert.assertEquals(SessionManager.generateSessionId(caller, UserData.getData(theOwner), newExpiration), theResultAsString);
	}

	@Test(expected = InvalidParameterException.class)
	public void invalidTtlTest() throws APIException {
		final User theOwner = new UserMock(42, "myLogin", "myPassword", "myEmail@gmail.com", getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.HOUR_OF_DAY, 1);
		final Date expiration = theCal.getTime();
		theCal.add(Calendar.HOUR_OF_DAY, -10);
		final Date newExpiration = theCal.getTime();

		final String sessionId = SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration);

		theParams.put(ActionParam.MAIN_PARAM_KEY, sessionId);
		theParams.put("expiration", newExpiration);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new Update();
		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidSessionException.class)
	public void invalidSessionTest() throws APIException {
		new UserMock(42, "myLogin", "myPassword", "myEmail@gmail.com", getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.HOUR_OF_DAY, 2);
		final Date newExpiration = theCal.getTime();

		theParams.put(ActionParam.MAIN_PARAM_KEY, "invalidID");
		theParams.put("expiration", newExpiration);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new Update();
		theAction.processRequest(theActionParam);
	}

}
