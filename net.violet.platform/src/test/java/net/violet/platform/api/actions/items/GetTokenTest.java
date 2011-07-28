package net.violet.platform.api.actions.items;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.libraries.GetToken;
import net.violet.platform.api.authentication.SessionManager;
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

public class GetTokenTest extends AbstractTestBase {

	@Test
	public void testGetToken() throws APIException {

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final UserData theUserData = UserData.getData(getPrivateUser());
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, theUserData, expiration));

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new GetToken();
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		//TODO Desactived for the moment!!waiting for the new cache
		//Assert.assertEquals(theResult.toString(), GetToken.TOKEN_CACHE.remove(theUserData));
	}

	//TODO Desactived for the moment!!waiting for the new cache
	//@Test(expected = InvalidSessionException.class)
	public void testInvalidSession() throws APIException {
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, "dfsdqf");

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new GetToken();
		theAction.processRequest(theActionParam);
	}
}
