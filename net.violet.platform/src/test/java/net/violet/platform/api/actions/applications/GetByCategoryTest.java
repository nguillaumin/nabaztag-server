package net.violet.platform.api.actions.applications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NoSuchCategoryException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class GetByCategoryTest extends MockTestBase {

	@Test
	public void getApplicationsTest() throws APIException {

		final Application theApplication1 = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication1);
		final Application theApplication2 = getMySecondApplication();
		final Lang frLang = getSiteFrLang();
		final String theTestCategoryName = theApplication1.getCategory().getName();
		Assert.assertEquals(theTestCategoryName, theApplication2.getCategory().getName());
		theApplication1.addLang(getFrLang());
		theApplication1.addLang(frLang);
		theApplication1.addLang(getUsLang());
		theApplication2.addLang(getFrLang());
		Assert.assertNotNull(theTestCategoryName);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, theApplication1.getCategory().getName());
		theParams.put("language", frLang.getIsoCode());

		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		final Action theAction = new GetByCategory();
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List<Object> theResultAsList = (List<Object>) theResult;
		Assert.assertEquals(1, theResultAsList.size());

		for (final Object o : theResultAsList) {
			Assert.assertTrue(o instanceof Map);
			final Map<String, Object> theAppli = (Map<String, Object>) o;
			Assert.assertTrue((theAppli.get("name") == theApplication1.getName()) || (theAppli.get("name") == theApplication2.getName()));
		}
	}

	@Test(expected = NoSuchCategoryException.class)
	public void unexistingCategoryTest() throws APIException {
		final Application theApplication1 = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication1);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "hello you!");
		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		final Action theAction = new GetByCategory();
		theAction.processRequest(theActionParam);
	}
}
