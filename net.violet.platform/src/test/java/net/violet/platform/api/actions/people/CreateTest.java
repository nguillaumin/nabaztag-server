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
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.PersonAlreadyExistsException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.CountryMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class CreateTest extends AbstractTestBase {

	@Test
	public void createTest() throws APIException {
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("email", "newEmail@gmail.com");
		theParams.put("password", "myPassword");
		theParams.put("first_name", "myFirstName");
		theParams.put("last_name", "myLastName");
		theParams.put("city", "Paris");
		theParams.put("country", "FR");
		theParams.put("lang", "fr");
		theParams.put("timezone", "Europe/Paris");

		final UserMock theUser = new UserMock(42, "myLogin", "myPassword", "myEmail2@gmail.com", getFrLang(), "EN", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Date now = new Date();
		new CountryMock(1, "FR", "France");
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new Create();
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof Map);

		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;
		Assert.assertEquals("newEmail@gmail.com", theResultAsMap.get("email"));
		Assert.assertEquals("fr-FR", theUser.getAnnu().getLangPreferences().getIsoCode());
		Assert.assertNull(theResultAsMap.get("password"));
	}

	@Test(expected = PersonAlreadyExistsException.class)
	public void alreadyExistsTest() throws APIException {

		new UserMock(42, "myLogin", "myPassword", "myEmail@gmail.com", getFrLang(), "FR", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("email", "myEmail@gmail.com");
		theParams.put("password", "myPassword");
		theParams.put("first_name", "myFirstName");
		theParams.put("last_name", "myLastName");
		theParams.put("city", "Paris");
		theParams.put("country", "FR");
		theParams.put("lang", "fr-FR");
		theParams.put("timezone", "Europe/France");

		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new Create();

		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidParameterException.class)
	public void invalidParameterTest() throws APIException {

		new UserMock(42, "myLogin", "myPassword", "myEmail@gmail.com", getFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("password", "myPassword");
		theParams.put("first_name", "myFirstName");
		theParams.put("last_name", "myLastName");
		theParams.put("city", "Paris");
		theParams.put("lang", "fr-FR");
		theParams.put("timezone", "Europe/France");

		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new Create();

		theAction.processRequest(theActionParam);
	}

}
