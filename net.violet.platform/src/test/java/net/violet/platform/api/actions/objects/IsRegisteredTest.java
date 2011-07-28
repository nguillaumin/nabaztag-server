package net.violet.platform.api.actions.objects;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class IsRegisteredTest extends MockTestBase {

	@Test
	public void doesNotExistTest() throws APIException {
		Factories.VOBJECT.createObject(HARDWARE.V2, "serial1", "name", "name", getKowalskyUser(), "Paris", 0, 0, null);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> params = Collections.singletonMap(ActionParam.MAIN_PARAM_KEY, (Object) "serial2");

		final ActionParam theParam = new ActionParam(caller, params);

		final Object res = new IsRegistered().processRequest(theParam);

		Assert.assertFalse((Boolean) res);
	}

	@Test
	public void doesExistTest() throws APIException {
		Factories.VOBJECT.createObject(HARDWARE.V2, "serial1", "name", "name", getKowalskyUser(), "Paris", 0, 0, null);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> params = Collections.singletonMap(ActionParam.MAIN_PARAM_KEY, (Object) "serial1");

		final ActionParam theParam = new ActionParam(caller, params);

		// response is a map with the id of the registered object
		final Map<String, Object> res = (Map<String, Object>) new IsRegistered().processRequest(theParam);

		Assert.assertTrue(res.containsKey("id"));
	}

}
