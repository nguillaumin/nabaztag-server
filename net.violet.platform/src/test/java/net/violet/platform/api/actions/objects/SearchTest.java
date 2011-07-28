package net.violet.platform.api.actions.objects;

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

import org.junit.Assert;
import org.junit.Test;

public class SearchTest extends AbstractTestBase {

	@Test
	public void searchSerialVisibilityTest() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();

		final User user1 = new UserMock(1, "user1", net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, "FR", "toto", net.violet.common.StringShop.EMPTY_STRING, getParisTimezone(), "H", net.violet.common.StringShop.EMPTY_STRING, "Paris", 1);
		final User user2 = new UserMock(2, "user2", net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, "FR", "tutu", net.violet.common.StringShop.EMPTY_STRING, getParisTimezone(), "H", net.violet.common.StringShop.EMPTY_STRING, "Paris", 0);

		new VObjectMock(61009, "F00004000001", "object1", user1, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObject object2 = new VObjectMock(61010, "F00004000002", "object2", user1, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		new VObjectMock(61011, "F00004000003", "object3", user2, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		new VObjectMock(61012, "F00004000004", "object4", user2, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObject object5 = new VObjectMock(61013, "F00004000005", "object5", user2, HARDWARE.BOOK, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		object2.getPreferences().setVisible(false);
		object5.getPreferences().setVisible(false);

		final Action theAction = new Search();
		final Application theApplication = new ApplicationMock(42, "My first application", user1, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9");

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(user1), theCalendar.getTime()));

		final Map<String, Object> theQuery = new HashMap<String, Object>();
		theQuery.put("country", "FR");
		theQuery.put("hw_type", "violet.nabaztag.tagtag");
		theParams.put("query", theQuery);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List<Object> theResultAsList = (List<Object>) theResult;

		Assert.assertEquals(3, theResultAsList.size());
	}

	@Test
	public void searchPaginationTest() throws APIException {
		final Date now = new Date();
		final Lang frLang = getFrLang();

		final User user1 = new UserMock(1, "user1", net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, "FR", "toto", net.violet.common.StringShop.EMPTY_STRING, getParisTimezone(), "H", net.violet.common.StringShop.EMPTY_STRING, "Paris", 1);
		final User user2 = new UserMock(2, "user2", net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, "FR", "tutu", net.violet.common.StringShop.EMPTY_STRING, getParisTimezone(), "H", net.violet.common.StringShop.EMPTY_STRING, "Paris", 0);

		new VObjectMock(61009, "F00004000001", "object1", user1, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObject object2 = new VObjectMock(61010, "F00004000002", "object2", user1, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		new VObjectMock(61011, "F00004000003", "object3", user2, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		new VObjectMock(61012, "F00004000004", "object4", user2, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		final VObject object5 = new VObjectMock(61013, "F00004000005", "object5", user2, HARDWARE.BOOK, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		object2.getPreferences().setVisible(false);
		object5.getPreferences().setVisible(false);

		final Action theAction = new Search();
		final Application theApplication = new ApplicationMock(42, "My first application", user1, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9");

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(user1), theCalendar.getTime()));

		final Map<String, Object> theQuery = new HashMap<String, Object>();
		theQuery.put("country", "FR");
		theParams.put("query", theQuery);
		theParams.put("skip", 1);
		theParams.put("count", 1);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List<Object> theResultAsList = (List<Object>) theResult;

		Assert.assertEquals(1, theResultAsList.size());
		Assert.assertTrue(theResultAsList.get(0) instanceof Map);
		final Map<String, Object> theMap = (Map<String, Object>) theResultAsList.get(0);
		Assert.assertEquals("object3", theMap.get("name"));
	}

	@Test(expected = InvalidParameterException.class)
	public void emptyQueryTest() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();

		final User user1 = new UserMock(1, "user1", net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, "FR", "toto", net.violet.common.StringShop.EMPTY_STRING, getParisTimezone(), "H", net.violet.common.StringShop.EMPTY_STRING, "Paris", 1);
		final User user2 = new UserMock(2, "user2", net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, "FR", "tutu", net.violet.common.StringShop.EMPTY_STRING, getParisTimezone(), "H", net.violet.common.StringShop.EMPTY_STRING, "Paris", 0);

		new VObjectMock(61009, "F00004000001", "object1", user1, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		new VObjectMock(61010, "F00004000002", "object2", user1, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		new VObjectMock(61011, "F00004000003", "object3", user2, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		new VObjectMock(61012, "F00004000004", "object4", user2, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		new VObjectMock(61013, "F00004000005", "object5", user2, HARDWARE.BOOK, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		final Action theAction = new Search();
		final Application theApplication = new ApplicationMock(42, "My first application", user1, now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9");

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(user1), theCalendar.getTime()));

		final Map<String, Object> theQuery = new HashMap<String, Object>();
		theQuery.put("sillyParameter", "FR");
		theParams.put("query", theQuery);
		theParams.put("skip", 1);
		theParams.put("count", 1);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

}
