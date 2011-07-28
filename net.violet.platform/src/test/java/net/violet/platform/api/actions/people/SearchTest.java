package net.violet.platform.api.actions.people;

import java.util.ArrayList;
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
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class SearchTest extends AbstractTestBase {

	@Test
	public void searchTest() throws APIException {
		final Date now = new Date();
		final Lang frLang = getFrLang();
		final Timezone theTimezone = getParisTimezone();

		final CCalendar theCal = new CCalendar(true);
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();

		final User user1 = new UserMock(1, "user1", "pass", "email1", frLang, "FR", "firstName1", "lastName1", theTimezone, "H", "Zip", "Paris", 1);
		theCal.set(1980, 05, 12);
		user1.getAnnu().setDateBirth(new java.sql.Date(theCal.getTimeInMillis()));

		final User user2 = new UserMock(2, "user2", "pass", "email2", frLang, "UK", "firstName2", "lastName2", theTimezone, "H", "Zip", "Paris", 1);
		theCal.set(1982, 05, 12);
		user2.getAnnu().setDateBirth(new java.sql.Date(theCal.getTimeInMillis()));

		final User user3 = new UserMock(3, "user3", "pass", "email3", frLang, "FR", "firstName3", "lastName3", theTimezone, "F", "Zip", "Paris", 1);
		theCal.set(1977, 05, 12);
		user3.getAnnu().setDateBirth(new java.sql.Date(theCal.getTimeInMillis()));

		final User user4 = new UserMock(4, "user4", "pass", "email4", frLang, "SE", "firstName4", "lastName4", theTimezone, "F", "Zip", "Stockholm", 1);
		theCal.set(1985, 05, 12);
		user4.getAnnu().setDateBirth(new java.sql.Date(theCal.getTimeInMillis()));

		final VObject theObject1 = new VObjectMock(61001, "F00004000001", "test1", user1, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObject theObject2 = new VObjectMock(61002, "F00004000002", "test2", user2, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObject theObject3 = new VObjectMock(61003, "F00004000003", "test3", user3, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObject theObject4 = new VObjectMock(61004, "F00004000004", "test4", user4, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		theObject1.getPreferences().setPreferences(true, true, null);
		theObject2.getPreferences().setPreferences(true, true, null);
		theObject3.getPreferences().setPreferences(true, true, null);
		theObject4.getPreferences().setPreferences(true, true, null);

		final Action theAction = new Search();
		final Application theApplication = new ApplicationMock(42, "My first application", user1, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(user1), expiration));

		final Map<String, Object> theQuery = new HashMap<String, Object>();
		theQuery.put("older_than", 25);
		theQuery.put("younger_than", 29);
		theParams.put("query", theQuery);

		ActionParam theActionParam = new ActionParam(caller, theParams);
		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		List<Object> theResultAsList = (List<Object>) theResult;

		Assert.assertEquals(2, theResultAsList.size());
		final List<String> idList = new ArrayList<String>();
		for (final Object o : theResultAsList) {
			Assert.assertTrue(o instanceof Map);
			final Map<String, Object> m = (Map<String, Object>) o;
			idList.add(UserData.getData(user1).getApiId(caller));
			Assert.assertTrue(m.get("id").equals(idList.get(idList.size() - 1)) || m.get("id").equals(UserData.getData(user2).getApiId(caller)));
		}

		theQuery.clear();
		theQuery.put("older_than", 23);
		theQuery.put("country", "UK");
		theParams.put("query", theQuery);

		theActionParam = new ActionParam(caller, theParams);
		theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		theResultAsList = (List<Object>) theResult;

		Assert.assertEquals(1, theResultAsList.size());
		Object aResult = theResultAsList.get(0);
		Assert.assertTrue(aResult instanceof Map);
		Map<String, Object> aResultAsMap = (Map<String, Object>) aResult;
		Assert.assertEquals(null, aResultAsMap.get("email"));
		// TODO: assertEquals(idList.get(1), aResultAsMap.get("id"));

		theQuery.clear();
		theQuery.put("younger_than", 25);
		theParams.put("query", theQuery);

		theActionParam = new ActionParam(caller, theParams);
		theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		theResultAsList = (List<Object>) theResult;

		Assert.assertEquals(1, theResultAsList.size());
		aResult = theResultAsList.get(0);
		Assert.assertTrue(aResult instanceof Map);
		aResultAsMap = (Map<String, Object>) aResult;
		Assert.assertEquals(null, aResultAsMap.get("email"));
		// TODO: assertEquals(UserData.getData(user2).getAPIIdFor(caller),
		// aResultAsMap.get("id"));
	}

	@Test(expected = InvalidParameterException.class)
	public void emptyQueryTest() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final Timezone theTimezone = getParisTimezone();

		final CCalendar theCal = new CCalendar(true);

		final User user1 = new UserMock(1, "user1", "pass", "email1", frLang, "FR", "firstName1", "lastName1", theTimezone, "H", "Zip", "Paris", 1);
		theCal.set(1980, 05, 12);
		user1.getAnnu().setDateBirth(new java.sql.Date(theCal.getTimeInMillis()));

		final User user2 = new UserMock(2, "user2", "pass", "email2", frLang, "UK", "firstName2", "lastName2", theTimezone, "H", "Zip", "Paris", 1);
		theCal.set(1982, 05, 12);
		user2.getAnnu().setDateBirth(new java.sql.Date(theCal.getTimeInMillis()));

		final User user3 = new UserMock(3, "user3", "pass", "email3", frLang, "FR", "firstName3", "lastName3", theTimezone, "F", "Zip", "Paris", 1);
		theCal.set(1977, 05, 12);
		user3.getAnnu().setDateBirth(new java.sql.Date(theCal.getTimeInMillis()));

		final User user4 = new UserMock(4, "user4", "pass", "email4", frLang, "SE", "firstName4", "lastName4", theTimezone, "F", "Zip", "Stockholm", 1);
		theCal.set(1985, 05, 12);
		user4.getAnnu().setDateBirth(new java.sql.Date(theCal.getTimeInMillis()));

		final VObject theObject1 = new VObjectMock(61001, "F00004000001", "test1", user1, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObject theObject2 = new VObjectMock(61002, "F00004000002", "test2", user2, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObject theObject3 = new VObjectMock(61003, "F00004000003", "test3", user3, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObject theObject4 = new VObjectMock(61004, "F00004000004", "test4", user4, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		theObject1.getPreferences().setPreferences(true, true, null);
		theObject2.getPreferences().setPreferences(true, true, null);
		theObject3.getPreferences().setPreferences(true, true, null);
		theObject4.getPreferences().setPreferences(true, true, null);

		final Action theAction = new Search();
		final Application theApplication = new ApplicationMock(42, "My first application", user1, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(user1), theCal.getTime()));

		final Map<String, Object> theQuery = new HashMap<String, Object>();
		theQuery.put("sillyParameter", 29);
		theParams.put("query", theQuery);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

}
