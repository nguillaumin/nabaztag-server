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
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;

public class SetPasswordTest extends AbstractPeopleWithSessionBase {

	@Override
	public void testValidSession() throws APIException {
		final Date now = new Date();
		final User theUser = getPrivateUser();
		final Application theApplication = new ApplicationMock(42, "My first application", theUser, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		testSetPassword(caller, theUser, generateSession(UserData.getData(theUser), caller));
	}

	@Override
	public void testNoSession() throws APIException {
		final Date now = new Date();
		final User theUser = getPrivateUser();
		final Application theApplication = new ApplicationMock(42, "My first application", theUser, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		try {
			testSetPassword(caller, theUser, null);
		} finally {
			Assert.assertFalse(theUser.checkPasswordPlain("newPass"));
		}
	}

	@Override
	public void testOtherUserSession() throws APIException {
		final Date now = new Date();
		final User theUser = getPrivateUser();
		final Application theApplication = new ApplicationMock(42, "My first application", theUser, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		try {
			testSetPassword(caller, theUser, generateSessionAlterUser(caller));
		} finally {
			Assert.assertFalse(theUser.checkPasswordPlain("newPass"));
		}
	}

	@Override
	public void testOtherApplicationSession() throws APIException {
		final Date now = new Date();
		final User theUser = getPrivateUser();
		final Application theApplication = new ApplicationMock(42, "My first application", theUser, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		try {
			testSetPassword(caller, theUser, generateSessionAlterApplication(UserData.getData(theUser)));
		} finally {
			Assert.assertFalse(theUser.checkPasswordPlain("newPass"));
		}
	}

	public void testSetPassword(APICaller inCaller, User inUser, String inSessionId) throws APIException {
		final Action theAction = new SetPassword();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final UserData theUserData = UserData.getData(inUser);
		final String userId = theUserData.getApiId(inCaller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		theParams.put("old_password", "12345");
		theParams.put("new_password", "newPass");
		setSessionParam(theParams, inSessionId);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
		Assert.assertTrue(inUser.checkPasswordPlain("newPass"));

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		theParams.put("old_password", "newPass");
		theParams.put("new_password", "12345");
		setSessionParam(theParams, inSessionId);

		final ActionParam theActionParam2 = new ActionParam(inCaller, theParams);
		final Object theResult2 = theAction.processRequest(theActionParam2);

		Assert.assertNull(theResult2);
		Assert.assertTrue(inUser.checkPasswordPlain("12345"));
	}
}
