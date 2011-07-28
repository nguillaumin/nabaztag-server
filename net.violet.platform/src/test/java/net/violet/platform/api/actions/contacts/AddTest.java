package net.violet.platform.api.actions.contacts;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.ContactMock;
import net.violet.platform.datamodel.mock.FriendsListsMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class AddTest extends AbstractTestBase {

	@Test
	public void testAddPending() throws APIException {
		final Date now = new Date();

		final User theUser1 = new UserMock(1, "blabla", "12345", "toto@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		final User theUser3 = new UserMock(3, "blabla3", "12345", "test2@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);

		new FriendsListsMock(1, 3, 0, 0);

		new ContactMock(100, getPrivateUser(), getKowalskyUser(), Contact.STATUS.ACCEPTED);
		new ContactMock(102, getPrivateUser(), theUser3, Contact.STATUS.REJECTED);
		new ContactMock(103, theUser1, theUser3, Contact.STATUS.REJECTED);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);

		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final Map<String, Object> theParams = new HashMap<String, Object>();

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final String userId = UserData.getData(theUser1).getApiId(caller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(getPrivateUser()), theCalendar.getTime()));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		// Test add Pending
		final Action theAction = new Add();

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof Map);

		final Map contactOne = (Map) theResult;

		Assert.assertEquals(contactOne.get("status"), Contact.STATUS.PENDING.toString().toLowerCase());
		Assert.assertEquals(contactOne.get("person_id"), UserData.getData(getPrivateUser()).getApiId(caller));
		Assert.assertEquals(contactOne.get("contact_id"), UserData.getData(theUser1).getApiId(caller));

	}

	@Test
	public void testAddAuto() throws APIException {
		final Date now = new Date();

		final User theUser1 = new UserMock(1, "blabla", "12345", "toto@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		final User theUser2 = new UserMock(2, "blabla2", "12345", "test1@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		final User theUser3 = new UserMock(3, "blabla3", "12345", "test2@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);

		new FriendsListsMock(1, 3, 0, 0);

		new ContactMock(100, getPrivateUser(), getKowalskyUser(), Contact.STATUS.ACCEPTED);
		new ContactMock(102, getPrivateUser(), theUser3, Contact.STATUS.REJECTED);
		new ContactMock(103, theUser1, theUser3, Contact.STATUS.REJECTED);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);

		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final Map<String, Object> theParams = new HashMap<String, Object>();

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		String userId = UserData.getData(getPrivateUser()).getApiId(caller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theUser1), theCalendar.getTime()));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		// Test add auto

		userId = UserData.getData(theUser2).getApiId(caller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);

		new FriendsListsMock(2, 2, 0, 0);

		final Object theResult = new Add().processRequest(theActionParam);

		Assert.assertNull(theResult);

		final List<Contact> listContact = Factories.CONTACT.findAllContactByUser(theUser1, 0, 10);
		Assert.assertEquals(listContact.size(), 1);
		Assert.assertEquals(listContact.get(0).getStatus().toLowerCase(), Contact.STATUS.AUTOMATICALLY_ACCEPTED.toString().toLowerCase());

	}

	@Test(expected = NoSuchPersonException.class)
	public void testNoContact() throws APIException {
		final Date now = new Date();

		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new Add();
		final Application theApplication = new ApplicationMock(42, "My first application", theOwner, now);

		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9");

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime()));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalid() throws APIException {
		final Date now = new Date();

		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new Add();
		final Application theApplication = new ApplicationMock(42, "My first application", theOwner, now);

		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidSessionException.class)
	public void testInvalidSession() throws APIException {
		final Date now = new Date();

		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new Add();
		final Application theApplication = new ApplicationMock(42, "My first application", theOwner, now);

		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.SESSION_PARAM_KEY, "ee51O935b3af9");

		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		theAction.processRequest(theActionParam);
	}
}
