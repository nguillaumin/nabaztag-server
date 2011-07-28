package net.violet.platform.api.actions.people;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.ApiActionTestBase;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class ManageTokenTest extends ApiActionTestBase {

	@Test
	public void getTokenTest() throws APIException {
		final UserData theUser = UserData.getData(getKowalskyUser());
		theUser.generateToken();

		final Application theApplication = new ApplicationMock(42, "My first application", theUser.getReference(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Action theAction = new ManageToken();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, generateSession(theUser, caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertEquals(String.valueOf(theUser.getToken()), theResult);
	}

	@Test
	public void getNonExistingTokenTest() throws APIException {
		final UserData theUser = UserData.getData(getKowalskyUser());
		theUser.clearToken();

		final Application theApplication = new ApplicationMock(42, "My first application", theUser.getReference(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Action theAction = new ManageToken();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, generateSession(theUser, caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
	}

	@Test
	public void activateTokenTest() throws APIException {
		final UserData theUser = UserData.getData(getKowalskyUser());
		theUser.clearToken();

		final Application theApplication = new ApplicationMock(42, "My first application", theUser.getReference(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Action theAction = new ManageToken();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, generateSession(theUser, caller));
		theParams.put("activate", true);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertEquals(String.valueOf(theUser.getToken()), theResult);
	}

	@Test
	public void deactivateTokenTest() throws APIException {
		final UserData theUser = UserData.getData(getKowalskyUser());
		theUser.generateToken();

		final Application theApplication = new ApplicationMock(42, "My first application", theUser.getReference(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Action theAction = new ManageToken();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, generateSession(theUser, caller));
		theParams.put("activate", false);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
	}

}
