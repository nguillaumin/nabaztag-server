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
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.ContactMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class GetSentContactRequestsTest extends AbstractTestBase {

	@Test
	public void testFind() throws APIException {
		final Date now = new Date();

		final User theUser1 = new UserMock(1, "blabla", "12345", "toto@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		final User theUser2 = new UserMock(2, "blabla2", "12345", "test2@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		final User theUser3 = new UserMock(3, "blabla3", "12345", "test3@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		final User theUser4 = new UserMock(4, "blabla4", "12345", "test4@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);

		new ContactMock(1, getPrivateUser(), getKowalskyUser(), Contact.STATUS.ACCEPTED);
		new ContactMock(2, getPrivateUser(), theUser2, Contact.STATUS.AUTOMATICALLY_ACCEPTED);
		new ContactMock(4, getPrivateUser(), theUser3, Contact.STATUS.REJECTED);
		new ContactMock(5, theUser1, getPrivateUser(), Contact.STATUS.AUTOMATICALLY_ACCEPTED);
		new ContactMock(6, theUser1, theUser3, Contact.STATUS.REJECTED);
		new ContactMock(7, theUser3, getPrivateUser(), Contact.STATUS.PENDING);
		new ContactMock(8, theUser4, getPrivateUser(), Contact.STATUS.PENDING);
		new ContactMock(9, getPrivateUser(), theUser4, Contact.STATUS.PENDING);

		final Action theAction = new GetSentContactRequests();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);

		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(getPrivateUser()), theCalendar.getTime()));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);

		final List theResultAsList = (List) theResult;
		Assert.assertEquals(2, theResultAsList.size());

		final Object firstContactObject = theResultAsList.get(0);
		final Object secondContactObject = theResultAsList.get(1);

		Assert.assertTrue(firstContactObject instanceof Map);
		Assert.assertTrue(secondContactObject instanceof Map);

		final Map contactOne = (Map) firstContactObject;
		final Map contactTwo = (Map) secondContactObject;

		final String apiID = UserData.getData(getPrivateUser()).getApiId(caller);
		Assert.assertEquals(contactOne.get("contact_id"), UserData.getData(theUser3).getApiId(caller));
		Assert.assertEquals(contactOne.get("person_id"), apiID);
		Assert.assertEquals(contactOne.get("status"), Contact.STATUS.REJECTED.toString().toLowerCase());
		Assert.assertEquals(contactTwo.get("contact_id"), UserData.getData(theUser4).getApiId(caller));
		Assert.assertEquals(contactTwo.get("person_id"), apiID);
		Assert.assertEquals(contactTwo.get("status"), Contact.STATUS.PENDING.toString().toLowerCase());

	}

	@Test(expected = InvalidSessionException.class)
	public void testNoContext() throws APIException {
		final Date now = new Date();

		new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new GetSentContactRequests();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, "ee51O935b3af9");
		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalid() throws APIException {
		final Date now = new Date();

		new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new GetSentContactRequests();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		theAction.processRequest(theActionParam);
	}
}
