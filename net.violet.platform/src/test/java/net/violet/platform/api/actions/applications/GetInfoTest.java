package net.violet.platform.api.actions.applications;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class GetInfoTest extends MockTestBase {

	@Test
	public void testExistingRecord() throws APIException {

		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, Factories.TIMEZONE.findByJavaId("Europe/Paris"));
		new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());

		final Action theAction = new GetInfo();
		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		theApplication.addHardware(HARDWARE.V2);

		final ApplicationData appliId = ApplicationData.getData(theApplication);
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		theParams.put(ActionParam.MAIN_PARAM_KEY, appliId.getApiId(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred))));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);

		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;

		Assert.assertEquals("My first application", theResultAsMap.get("name"));
		Assert.assertEquals(true, theResultAsMap.get("is_removable"));
		Assert.assertEquals(UserData.getData(getKowalskyUser()).getApiId(caller), theResultAsMap.get("owner"));
		final List theSupportedHardware = (List) theResultAsMap.get("supported_hardware");
		Assert.assertEquals(theSupportedHardware.size(), 1);
		Assert.assertEquals(theSupportedHardware.get(0), ObjectType.NABAZTAG_V2.getTypeName());
	}

	@Test(expected = NoSuchApplicationException.class)
	public void testInvalidAPIId() throws APIException {
		final Action theAction = new GetInfo();
		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9zozo");
		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);
		theAction.processRequest(theActionParam);
	}

	@Test(expected = NoSuchApplicationException.class)
	public void testInexistingRecord() throws APIException {
		final Action theAction = new GetInfo();
		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "109d0O9770913a");
		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);
		theAction.processRequest(theActionParam);
	}
}
