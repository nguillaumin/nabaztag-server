package net.violet.platform.api.actions.people;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class FindByEmailAddressTest extends AbstractTestBase {

	@Test
	public void findByNameTest() throws APIException {
		final String theEmailAddress = "myEmail@gmail.com";
		final Date now = new Date();
		final User theUser = new UserMock(42, "myLogin", "myPassword", theEmailAddress, getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Action theAction = new FindByEmailAddress();
		final Application theApplication = new ApplicationMock(42, "My first application", theUser, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, theEmailAddress);
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof Map);

		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;
		final UserData theUserFound = UserData.findByAPIId((String) theResultAsMap.get("id"), theActionParam.getCallerAPIKey());
		Assert.assertEquals(UserData.getData(theUser), theUserFound);
	}

	@Test(expected = NoSuchPersonException.class)
	public void findNonExistingTest() throws APIException {
		final String theEmailAddress = "anotherEmailAddress@gmail.com";
		final Date now = new Date();
		final User theUser = new UserMock(42, "myLogin", "myPassword", "myEmail@gmail.com", getSiteFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Action theAction = new FindByEmailAddress();
		final Application theApplication = new ApplicationMock(42, "My first application", theUser, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, theEmailAddress);
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}
}
