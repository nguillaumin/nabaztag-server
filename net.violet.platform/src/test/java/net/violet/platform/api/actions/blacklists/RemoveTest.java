package net.violet.platform.api.actions.blacklists;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCategoryMock;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class RemoveTest extends MockTestBase {

	@Test
	public void removeTest() throws APIException {
		final ApplicationCategory categ = new ApplicationCategoryMock(1, "firstCateg");

		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();

		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(38, "owner", net.violet.common.StringShop.EMPTY_STRING, "owner", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final User theBlacked1 = new UserMock(43, "blacked1", net.violet.common.StringShop.EMPTY_STRING, "blacked1", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final User theBlacked2 = new UserMock(44, "blacked2", net.violet.common.StringShop.EMPTY_STRING, "blacked2", frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		Factories.BLACK.createNewBlack(theOwner, theBlacked1);
		Factories.BLACK.createNewBlack(theOwner, theBlacked2);

		final Application theApplication = new ApplicationMock(0, "My first application", theOwner, now, categ);
		theApplication.addHardware(HARDWARE.V2);

		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));
		theParams.put(ActionParam.MAIN_PARAM_KEY, UserData.getData(theBlacked1).getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new Remove();

		Assert.assertEquals(2, theOwner.getBlackList().size());
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);

		Assert.assertEquals(1, theOwner.getBlackList().size());
		Assert.assertTrue(theOwner.getBlackList().containsKey(theBlacked2));
		Assert.assertFalse(theOwner.getBlackList().containsKey(theBlacked1));
	}
}
