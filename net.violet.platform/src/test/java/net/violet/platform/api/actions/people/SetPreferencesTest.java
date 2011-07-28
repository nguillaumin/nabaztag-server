package net.violet.platform.api.actions.people;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import net.violet.platform.datamodel.UserEmail;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FriendsListsMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.FriendsListsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class SetPreferencesTest extends AbstractPeopleWithSessionBase {

	@Override
	public void testNoSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final User theOwner = new UserMock(422, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setPreferences(caller, theOwner, null);
	}

	@Override
	public void testOtherApplicationSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final User theOwner = new UserMock(422, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setPreferences(caller, theOwner, generateSessionAlterApplication(UserData.getData(theOwner)));
	}

	@Override
	public void testOtherUserSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final User theOwner = new UserMock(422, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setPreferences(caller, theOwner, generateSessionAlterUser(caller));
	}

	@Override
	public void testValidSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final User theOwner = new UserMock(422, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setPreferences(caller, theOwner, generateSession(UserData.getData(theOwner), caller));
	}

	@Test(expected = InvalidParameterException.class)
	public void setInvalidProfileTest() throws APIException {
		final Date now = new Date();
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Action theAction = new SetPreferences();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		setSessionParam(theParams, generateSession(UserData.getData(theOwner), caller));
		final String userId = UserData.getData(theOwner).getApiId(caller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);

		final Map<String, Object> thePreference = new HashMap<String, Object>();
		thePreference.put("timezone", "blabla");

		theParams.put("preferences", thePreference);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = NoSuchPersonException.class)
	public void testInexistingRecord() throws APIException {
		final Date now = new Date();
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new SetPreferences();
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
	public void testSetSpokenLanguages() throws APIException {
		final Date now = new Date();
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final User theOwner = new UserMock(422, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setPreferences(caller, theOwner, generateSession(UserData.getData(theOwner), caller));
		changerLangues(caller, theOwner, generateSession(UserData.getData(theOwner), caller));
	}

	public void changerLangues(APICaller inCaller, User inUser, String inSessionId) throws APIException {

		final Action theAction = new SetPreferences();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final UserData theUserData = UserData.getData(inUser);
		final String userId = theUserData.getApiId(inCaller);

		new FriendsListsMock(inUser.getId(), 3, 0, 0);
		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		setSessionParam(theParams, inSessionId);

		final List<String> spokenLangISO = new ArrayList<String>();
		spokenLangISO.add("fr-CA");
		spokenLangISO.add("pl-PL");
		spokenLangISO.add("gr-GR");
		spokenLangISO.add("en-GB"); // mauvais : uk

		final Map<String, Object> thePreference = new HashMap<String, Object>();

		thePreference.put("spoken_languages", spokenLangISO);

		theParams.put("preferences", thePreference);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
		final List<Lang> newLangs = inUser.getLangs();
		Assert.assertEquals(4, newLangs.size());
	}

	public void setPreferences(APICaller inCaller, User inUser, String inSessionId) throws APIException {

		final Action theAction = new SetPreferences();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final UserData theUserData = UserData.getData(inUser);
		final boolean notifyPlayed = theUserData.notifyIfPlayed();

		Factories.USER_EMAIL.create(inUser, "test3@email.com");
		Factories.USER_EMAIL.create(inUser, "test4@email.com");
		final String userId = theUserData.getApiId(inCaller);

		new FriendsListsMock(inUser.getId(), 3, 0, 0);
		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		setSessionParam(theParams, inSessionId);

		final List<String> spokenLangISO = new ArrayList<String>();
		spokenLangISO.add("fr-FR");
		spokenLangISO.add("pl-PL");
		spokenLangISO.add("de-DE");
		spokenLangISO.add("en-GB"); // mauvais : uk

		final Map<String, Object> thePreference = new HashMap<String, Object>();
		thePreference.put("timezone", "Europe/Paris");
		thePreference.put("time_format", new Integer(12));
		thePreference.put("language", "en");
		thePreference.put("spoken_languages", spokenLangISO);
		thePreference.put("newsletter", false);
		thePreference.put("visible", false);
		thePreference.put("notify_added_as_contact", false);
		thePreference.put("must_approve_contact_request", true);
		thePreference.put("notify_played", !notifyPlayed);
		thePreference.put("only_messages_from_friends", true);
		thePreference.put("emails", Arrays.asList("test@email.com"));

		theParams.put("preferences", thePreference);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);

		Assert.assertEquals(getParisTimezone(), inUser.getTimezone());
		Assert.assertEquals(0, inUser.getUser_24h());
		Assert.assertEquals("en", inUser.getAnnu().getLangPreferences().getIsoCode());
		Assert.assertEquals(0, inUser.getUser_newsletter().intValue());
		Assert.assertEquals(0, inUser.getAnnu().getAnnu_confirm());
		Assert.assertEquals(false, inUser.getNotifyMessagePlayed());
		final FriendsListsData fL = FriendsListsData.findByUser(theUserData);
		Assert.assertEquals(1, fL.getFriendslists_confirmationlevel());
		Assert.assertTrue(fL.getParentalFilter());
		final List<UserEmail> email = Factories.USER_EMAIL.findAllByUser(inUser);
		Assert.assertEquals(1, email.size());
		Assert.assertEquals(email.get(0).getAddress(), "test@email.com");
		if (notifyPlayed) {
			Assert.assertFalse(theUserData.notifyIfPlayed());
		} else {
			Assert.assertTrue(theUserData.notifyIfPlayed());
		}

	}
}
