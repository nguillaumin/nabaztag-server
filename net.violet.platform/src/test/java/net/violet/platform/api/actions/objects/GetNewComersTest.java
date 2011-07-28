package net.violet.platform.api.actions.objects;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class GetNewComersTest extends AbstractTestBase {

	@Test
	public void testExistingRecord() throws APIException {
		final Date now = new Date();

		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final User theOwner2 = new UserMock(43, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final User theOwner3 = new UserMock(44, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, getParisTimezone(), getFrLang(), 1110907546);
		new VObjectMock(620010, "F00004000002", "private", theOwner2, HARDWARE.V2, getParisTimezone(), getFrLang(), 1178903133);
		new VObjectMock(620011, "F00004000003", "violet22", theOwner3, HARDWARE.V2, getParisTimezone(), getFrLang(), 1136281428);
		new VObjectMock(620012, "F00004000003", "rfid", theOwner3, HARDWARE.BOOK, getParisTimezone(), getFrLang(), 1186281428);

		final Action theAction = new GetNewComers();

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9");
		theParams.put("count", 2);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);

		final List theResultAsList = (List) theResult;
		Assert.assertEquals(2, theResultAsList.size());

		final Object mostRecent = theResultAsList.get(0);
		final Object secondRecent = theResultAsList.get(1);

		Assert.assertTrue(mostRecent instanceof Map);
		Assert.assertTrue(secondRecent instanceof Map);

		final Map mostRecentAsMap = (Map) mostRecent;
		final Map secondRecentAsMap = (Map) secondRecent;

		Assert.assertEquals("private", mostRecentAsMap.get("name"));
		Assert.assertEquals(null, mostRecentAsMap.get("serial_number"));
		Assert.assertEquals("violet22", secondRecentAsMap.get("name"));
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParam() throws APIException {
		final Date now = new Date();

		new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new GetNewComers();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9");
		theParams.put("count", "invalid");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

}
