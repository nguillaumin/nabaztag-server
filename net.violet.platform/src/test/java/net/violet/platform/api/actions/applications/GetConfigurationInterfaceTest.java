package net.violet.platform.api.actions.applications;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;

import org.junit.Assert;
import org.junit.Test;

public class GetConfigurationInterfaceTest extends MockTestBase {

	@Test
	public void GetConfigurationTest() throws APIException {
		final Application theApplication1 = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication1);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Application weatherApplication = Factories.APPLICATION.findByName("net.violet.weather");
		theParams.put(ActionParam.MAIN_PARAM_KEY, ApplicationData.getData(weatherApplication).getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new GetConfigurationInterface();
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
	}
}
