package net.violet.platform.api.actions.people;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class GetProfileTest extends AbstractTestBase {

	@Test
	public void getProfileTest() throws APIException {

		final Timezone theTimezone = getParisTimezone();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", net.violet.common.StringShop.EMPTY_STRING, "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", theTimezone, "F", "Zip", "Paris", 1);

		final CCalendar birthCalendar = new CCalendar(theTimezone.getJavaTimeZone());
		birthCalendar.set(1980, 5, 12, 0, 0, 0);
		final CCalendar UTCCalendar = new CCalendar(TimeZone.getTimeZone("UTC"));
		UTCCalendar.set(1980, 5, 12, 0, 0, 0);

		theOwner.getAnnu().setDateBirth(new Date(birthCalendar.getTimeInMillis()));

		final Action theAction = new GetProfile();
		final Application theApplication = new ApplicationMock(42, "My first application", theOwner, new java.util.Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String userId = UserData.getData(theOwner).getApiId(caller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof Map);

		final Map resultAsMap = (Map) theResult;

		Assert.assertEquals("myFirstName", resultAsMap.get("first_name"));
		Assert.assertEquals("myLastName", resultAsMap.get("last_name"));

		birthCalendar.setTime((Date) resultAsMap.get("birth_date"));
		Assert.assertEquals(1980, birthCalendar.getYear());
		Assert.assertEquals(5, birthCalendar.getMonth());
		Assert.assertEquals(12, birthCalendar.getDay());

		Assert.assertEquals("F", resultAsMap.get("gender"));
		Assert.assertEquals("Paris", resultAsMap.get("city"));
		Assert.assertEquals("Zip", resultAsMap.get("zip_code"));
		Assert.assertEquals("France", resultAsMap.get("country"));
	}

	@Test(expected = NoSuchPersonException.class)
	public void getNonExistingProfileTest() throws APIException {
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", net.violet.common.StringShop.EMPTY_STRING, "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Action theAction = new GetProfile();
		final Application theApplication = new ApplicationMock(42, "My first application", theOwner, new java.util.Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String userId = "ghost";

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

}
