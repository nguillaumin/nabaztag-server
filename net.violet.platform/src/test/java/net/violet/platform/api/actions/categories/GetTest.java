package net.violet.platform.api.actions.categories;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;

import org.junit.Assert;
import org.junit.Test;

public class GetTest extends MockTestBase {

	@Test
	public void getCategTest() throws APIException {

		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Application theApplication = new ApplicationMock(42, "My first application", theOwner, now);
		theApplication.addHardware(HARDWARE.V2);

		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, ApplicationData.getData(theApplication).getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new Get();
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List<String> theResultAsList = (List<String>) theResult;

		Assert.assertEquals(12, theResultAsList.size());

		Assert.assertTrue(theResultAsList.contains("LOC_srv_category_music/title"));
		Assert.assertTrue(theResultAsList.contains("LOC_srv_category_perso/title"));

	}
}
