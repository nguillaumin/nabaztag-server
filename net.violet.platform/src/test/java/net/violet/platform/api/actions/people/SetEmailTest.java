package net.violet.platform.api.actions.people;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractPeopleWithSessionBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NameAlreadyExistsException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class SetEmailTest extends AbstractPeopleWithSessionBase {

	@Override
	public void testNoSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setEmailTest(caller, theOwner, null);
	}

	@Override
	public void testOtherApplicationSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setEmailTest(caller, theOwner, generateSessionAlterApplication(UserData.getData(theOwner)));
	}

	@Override
	public void testOtherUserSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setEmailTest(caller, theOwner, generateSessionAlterUser(caller));
	}

	@Override
	public void testValidSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setEmailTest(caller, theOwner, generateSession(UserData.getData(theOwner), caller));
	}

	public void setEmailTest(APICaller inCaller, User inUser, String inSessionId) throws APIException {

		final Action theAction = new SetEmail();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, inSessionId);
		final String userId = UserData.getData(inUser).getApiId(inCaller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		theParams.put("email", "newEmail@gmail.com");
		theParams.put("password", "myPass");

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
		Assert.assertEquals("newEmail@gmail.com", inUser.getUser_email());
	}

	@Test(expected = InvalidParameterException.class)
	public void setBadEmailTest() throws APIException {

		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Action theAction = new SetEmail();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, UserData.getData(theOwner).getApiId(caller));
		theParams.put("email", "badEmail");
		theParams.put("password", "myPass");

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void setAlreadyExistsTest() throws APIException {
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Action theAction = new SetEmail();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String userId = UserData.getData(theOwner).getApiId(caller);

		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		theParams.put("email", "myEmail@gmail.com");
		theParams.put("password", "myPass");

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = ForbiddenException.class)
	public void inValidPasswordTest() throws APIException {
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Action theAction = new SetEmail();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String userId = UserData.getData(theOwner).getApiId(caller);

		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		theParams.put("email", "myEmail2@gmail.com");
		theParams.put("password", "myBadPass");

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}
}
