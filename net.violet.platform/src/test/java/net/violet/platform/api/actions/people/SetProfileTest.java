package net.violet.platform.api.actions.people;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractPeopleWithSessionBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.CountryMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class SetProfileTest extends AbstractPeopleWithSessionBase {

	@Override
	public void testNoSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setProfileTest(caller, theOwner, null);
	}

	@Override
	public void testOtherApplicationSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setProfileTest(caller, theOwner, generateSessionAlterApplication(UserData.getData(theOwner)));
	}

	@Override
	public void testOtherUserSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(422, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setProfileTest(caller, theOwner, generateSessionAlterUser(caller));
	}

	@Override
	public void testValidSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setProfileTest(caller, theOwner, generateSession(UserData.getData(theOwner), caller));
	}

	public void setProfileTest(APICaller inCaller, User inUser, String inSessionId) throws APIException {

		final Action theAction = new SetProfile();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		setSessionParam(theParams, inSessionId);

		new CountryMock(1, "SE", "Sweden");
		final String userId = UserData.getData(inUser).getApiId(inCaller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);

		final Date theBirthDate = Calendar.getInstance().getTime();

		final Map<String, Object> theProfile = new HashMap<String, Object>();
		theProfile.put("first_name", "myFirstName2");
		theProfile.put("last_name", "myLastName2");
		theProfile.put("birth_date", theBirthDate);
		theProfile.put("gender", "F");
		theProfile.put("city", "Stockholm");
		theProfile.put("zip_code", "Zip2");
		theProfile.put("country", "SE");

		theParams.put("profile", theProfile);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);

		Assert.assertEquals("myFirstName2", inUser.getAnnu().getAnnu_prenom());
		Assert.assertEquals("myLastName2", inUser.getAnnu().getAnnu_nom());
		Assert.assertEquals(theBirthDate, inUser.getAnnu().getAnnu_datebirth());
		Assert.assertEquals("F", inUser.getAnnu().getAnnu_sexe());
		Assert.assertEquals("Stockholm", inUser.getAnnu().getAnnu_city());
		Assert.assertEquals("Zip2", inUser.getAnnu().getAnnu_cp());
		Assert.assertEquals("SE", inUser.getAnnu().getAnnu_country());
	}

	@Test(expected = InvalidParameterException.class)
	public void setInvalidProfileTest() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Action theAction = new SetProfile();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		final String userId = UserData.getData(theOwner).getApiId(caller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);

		final Map<String, Object> theProfile = new HashMap<String, Object>();
		theProfile.put("first_name", "myFirstName2");
		theProfile.put("last_name", "myLastName2");
		theProfile.put("birth_date", "invalidDate");
		theProfile.put("gender", "F");
		theProfile.put("city", "Stockholm");
		theProfile.put("zip_code", "Zip2");
		theProfile.put("country", "Sweden");

		theParams.put("profile", theProfile);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = NoSuchPersonException.class)
	public void testInexistingRecord() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new SetProfile();
		final Application theApplication = new ApplicationMock(43, "My first application", theOwner, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		theParams.put(ActionParam.MAIN_PARAM_KEY, "109d0O9770913a");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

	@Test
	public void changingCityCountryTest() throws APIException {
		final Date now = new Date();
		final UserData user = UserData.getData(getKowalskyUser());
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		new CountryMock(1, "FR", "France");

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		setSessionParam(theParams, generateSession(user, caller));

		theParams.put(ActionParam.MAIN_PARAM_KEY, user.getApiId(caller));

		final Map<String, Object> theProfile = new HashMap<String, Object>();
		theProfile.put("first_name", "myFirstName2");
		theProfile.put("last_name", "myLastName2");
		theProfile.put("birth_date", Calendar.getInstance().getTime());
		theProfile.put("gender", "F");
		theProfile.put("city", "Paris");
		theProfile.put("zip_code", "Zip2");
		theProfile.put("country", "FR");

		theParams.put("profile", theProfile);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = new SetProfile().processRequest(theActionParam);

		Assert.assertNull(theResult);

		Assert.assertEquals("Paris", user.getReference().getAnnu().getAnnu_city());
		Assert.assertEquals("FR", user.getReference().getAnnu().getAnnu_country());

		// Only check broadly, since the lower digits can change from time to time
		// depending on the location provider
		Assert.assertTrue(object.getProfile().getLatitudeGPS().startsWith("48.8566"));
		Assert.assertTrue(object.getProfile().getLongitudeGPS().startsWith("2.352"));
	}

}
