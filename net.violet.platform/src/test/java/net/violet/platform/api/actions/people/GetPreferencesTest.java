package net.violet.platform.api.actions.people;

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
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FriendsListsMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class GetPreferencesTest extends AbstractPeopleWithSessionBase {

	@Test
	@Override
	public void testValidSession() throws APIException {
		final Date now = new Date();
		final Lang enLang = Factories.LANG.findByIsoCode("en");
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, enLang, "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 1);
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		getPreferencesTest(caller, theOwner, generateSession(UserData.getData(theOwner), caller));
		getPreferencesTest2(caller, theOwner, generateSession(UserData.getData(theOwner), caller));
	}

	@Override
	public void testNoSession() throws APIException {
		final Date now = new Date();
		final User theOwner = getPrivateUser();

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		getPreferencesTest(caller, theOwner, null);
	}

	@Override
	public void testOtherUserSession() throws APIException {
		final Date now = new Date();
		final User theOwner = getPrivateUser();

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		getPreferencesTest(caller, theOwner, generateSessionAlterUser(caller));
	}

	@Override
	public void testOtherApplicationSession() throws APIException {
		final Date now = new Date();
		final User theOwner = getPrivateUser();

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		getPreferencesTest(caller, theOwner, generateSessionAlterApplication(UserData.getData(theOwner)));
	}

	public void getPreferencesTest(APICaller inCaller, User inUser, String inSessionId) throws APIException {

		Factories.USER_EMAIL.create(inUser, "alternate@gmail.com");
		new FriendsListsMock(inUser.getId(), 3, 1, 1);

		final Lang enLang = Factories.LANG.findByIsoCode("en");
		final Lang frLang = Factories.LANG.findByIsoCode("fr-FR");
		final Lang plLang = Factories.LANG.findByIsoCode("pl-PL");
		inUser.setTTSLang(frLang);
		inUser.setTTSLang(enLang);
		inUser.setTTSLang(plLang);

		final Action theAction = new GetPreferences();

		final String userId = UserData.getData(inUser).getApiId(inCaller);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		setSessionParam(theParams, inSessionId);
		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof Map);

		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;

		Assert.assertTrue(theResultAsMap.get("emails") instanceof List);
		Assert.assertEquals(1, ((List) theResultAsMap.get("emails")).size());
		Assert.assertEquals("alternate@gmail.com", ((List) theResultAsMap.get("emails")).get(0));

		Assert.assertEquals("Europe/Paris", theResultAsMap.get("timezone"));

		Assert.assertEquals("24", theResultAsMap.get("time_format"));
		Assert.assertEquals(enLang.getIsoCode(), theResultAsMap.get("language"));
		Assert.assertFalse((Boolean) theResultAsMap.get("newsletter"));
		Assert.assertTrue((Boolean) theResultAsMap.get("notify_added_as_contact"));
		Assert.assertTrue((Boolean) theResultAsMap.get("must_approve_contact_request"));
		Assert.assertTrue((Boolean) theResultAsMap.get("visible"));
		Assert.assertTrue((Boolean) theResultAsMap.get("notify_played"));
		Assert.assertTrue((Boolean) theResultAsMap.get("only_messages_from_friends"));
		final List<Lang> spokenLanguages = (List<Lang>) theResultAsMap.get("spoken_languages");
		Assert.assertEquals(3, spokenLanguages.size());
		Assert.assertTrue(spokenLanguages.contains(frLang.getIsoCode()));
		Assert.assertTrue(spokenLanguages.contains(enLang.getIsoCode()));
		Assert.assertTrue(spokenLanguages.contains(plLang.getIsoCode()));
	}

	public void getPreferencesTest2(APICaller inCaller, User inUser, String inSessionId) throws APIException {

		new FriendsListsMock(inUser.getId(), 3, 0, 1);

		final Action theAction = new GetPreferences();

		final String userId = UserData.getData(inUser).getApiId(inCaller);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		setSessionParam(theParams, inSessionId);
		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof Map);

		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;

		Assert.assertFalse((Boolean) theResultAsMap.get("only_messages_from_friends"));
		final List<Lang> spokenLanguages = (List<Lang>) theResultAsMap.get("spoken_languages");
		Assert.assertEquals(3, spokenLanguages.size());
	}
}
