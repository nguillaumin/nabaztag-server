package net.violet.platform.api.actions.objects;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
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
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class GetStatusTest extends AbstractTestBase {

	@Test
	public void getStatusTest() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang, now.getTime());
		theObject.setState(VObject.STATUS_VEILLE);

		final Action theAction = new GetStatus();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String objectAPIId = VObjectData.getData(theObject).getApiId(caller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, objectAPIId);
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof String);
		String theResultAsString = (String) theResult;

		Assert.assertEquals("asleep", theResultAsString);

		theObject.setState(VObject.STATUS_ACTIF);

		theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof String);
		theResultAsString = (String) theResult;

		Assert.assertEquals("awaken", theResultAsString);

	}
}
