package net.violet.platform.api.actions.sessions;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractPeopleWithSessionBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;

public class GetUserTest extends AbstractPeopleWithSessionBase {

	@Override
	public void testNoSession() throws APIException {
		final User theUser = getKowalskyUser();
		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		getUserTest(caller, theUser, null);
	}

	@Override
	public void testOtherApplicationSession() throws APIException {
		final User theUser = getKowalskyUser();
		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		getUserTest(caller, theUser, generateSessionAlterApplication(UserData.getData(theUser)));
	}

	@Override
	public void testOtherUserSession() throws APIException {
		// Nothing to do here
		throw new ForbiddenException();
	}

	@Override
	public void testValidSession() throws APIException {
		final User theUser = getKowalskyUser();
		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		getUserTest(caller, theUser, generateSession(UserData.getData(theUser), caller));
	}

	public void getUserTest(APICaller inCaller, User inUser, String inSessionId) throws APIException {
		final Action theAction = new GetUser();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, inSessionId);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof Map);

		final Map resultAsMap = (Map) theResult;

		final String userId = UserData.getData(inUser).getApiId(inCaller);
		Assert.assertEquals(userId, resultAsMap.get("id"));
		Assert.assertFalse(resultAsMap.containsKey("password"));
		Assert.assertEquals("gerard@violet.net", resultAsMap.get("email"));
	}
}
