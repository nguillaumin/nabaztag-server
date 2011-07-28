package net.violet.platform.api.actions.people;

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
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
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

public class GetObjectsTest extends AbstractTestBase {

	@Test
	public void getObjectsTest() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr-FR");
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1110907546);
		new VObjectMock(620010, "F00004000002", "private", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1178903133);
		new VObjectMock(620011, "F00004000003", "violet22", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1136281428);
		new VObjectMock(620012, "F00004000004", "violet23", theOwner, HARDWARE.MIRROR, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1136281428);

		final Action theAction = new GetObjects();

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final String userId = UserData.getData(theOwner).getApiId(caller);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof List);

		final List resultAsList = (List) theResult;
		Assert.assertEquals(4, resultAsList.size());
		for (int i = 0; i < resultAsList.size(); i++) {
			Assert.assertTrue(resultAsList.get(i) instanceof Map);
			final Map object = (Map) resultAsList.get(i);
			final String name = (String) object.get("name");
			Assert.assertTrue(name.equals("test42") || name.equals("private") || name.equals("violet22") || name.equals("violet23"));
			Assert.assertEquals(null, object.get("serial_number"));
		}

	}

	@Test
	public void getAdressableObjectsTest() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr-FR");
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1110907546);
		new VObjectMock(620010, "F00004000002", "private", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1178903133);
		new VObjectMock(620011, "F00004000003", "violet22", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1136281428);
		new VObjectMock(620012, "F00004000004", "violet23", theOwner, HARDWARE.MIRROR, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1136281428);

		final Action theAction = new GetObjects();

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final String userId = UserData.getData(theOwner).getApiId(caller);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		theParams.put("adressable", true);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof List);

		final List resultAsList = (List) theResult;
		Assert.assertEquals(4, resultAsList.size());
		for (int i = 0; i < resultAsList.size(); i++) {
			Assert.assertTrue(resultAsList.get(i) instanceof Map);
			final Map object = (Map) resultAsList.get(i);
			final String name = (String) object.get("name");
			Assert.assertTrue(name.equals("test42") || name.equals("private") || name.equals("violet22") || name.equals("violet23"));
			Assert.assertEquals(null, object.get("serial_number"));
		}

	}

	@Test
	public void getObjectsSerialVisibilityTest() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr-FR");
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1110907546);
		new VObjectMock(620010, "F00004000002", "private", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1178903133);
		new VObjectMock(620011, "F00004000003", "violet22", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, 1136281428);

		final Action theAction = new GetObjects();

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final String userId = UserData.getData(theOwner).getApiId(caller);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), theCalendar.getTime()));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertTrue(theResult instanceof List);

		final List resultAsList = (List) theResult;
		Assert.assertEquals(3, resultAsList.size());

		for (int i = 0; i < resultAsList.size(); i++) {
			Assert.assertTrue(resultAsList.get(i) instanceof Map);
			final Map object = (Map) resultAsList.get(i);
			final String name = (String) object.get("name");
			Assert.assertTrue(name.equals("test42") || name.equals("private") || name.equals("violet22"));
			Assert.assertTrue("F00004000001".equals(object.get("serial_number")) || "F00004000002".equals(object.get("serial_number")) || "F00004000003".equals(object.get("serial_number")));
		}

	}

}
