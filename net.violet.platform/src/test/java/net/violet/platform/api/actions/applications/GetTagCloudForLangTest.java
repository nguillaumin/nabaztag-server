package net.violet.platform.api.actions.applications;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCategoryMock;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.ApplicationTagMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class GetTagCloudForLangTest extends MockTestBase {

	@Test
	public void getTagsCloudTest() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final Lang usLang = Factories.LANG.findByIsoCode("en");
		new ApplicationTagMock(1, "tagUn", frLang, 10);
		new ApplicationTagMock(2, "tagDeux", frLang, 5);
		new ApplicationTagMock(3, "tagTrois", usLang, 3);

		final ApplicationCategory applicationCategory1 = new ApplicationCategoryMock(1, "categ1");

		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Application theApplication1 = new ApplicationMock(0, "application1", theOwner, now, applicationCategory1);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication1);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, frLang.getIsoCode());

		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		final Action theAction = new GetTagCloudForLang();
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List<Object> theResultAsList = (List<Object>) theResult;
		Assert.assertEquals(2, theResultAsList.size());

		Map<String, Object> res = (Map<String, Object>) theResultAsList.get(0);
		Assert.assertEquals("tagUn", res.get("name"));
		res = (Map<String, Object>) theResultAsList.get(1);
		Assert.assertEquals("tagDeux", res.get("name"));
	}

	@Test(expected = InvalidParameterException.class)
	public void getCloudWithInvalidLang() throws APIException {
		final Lang frLang = getSiteFrLang();
		final Lang usLang = getSiteEnLang();
		new ApplicationTagMock(1, "tagUn", frLang, 10);
		new ApplicationTagMock(2, "tagDeux", frLang, 5);
		new ApplicationTagMock(3, "tagTrois", usLang, 3);

		final Application theApplication1 = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication1);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "unknown");

		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		final Action theAction = new GetTagCloudForLang();
		theAction.processRequest(theActionParam);
	}

}
