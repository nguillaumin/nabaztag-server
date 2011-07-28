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
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class GetApplicationsTest extends MockTestBase {

	@Test
	public void getApplicationsTest() throws APIException {

		final Date now = new Date();
		final Application theApplication1 = new ApplicationMock(1L, "net.violet.rss.Nasa Spot", getKowalskyUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication1);
		final Application theApplication2 = new ApplicationMock(2L, "net.violet.podcast.Dupki", getKowalskyUser(), now);
		final Application theApplication3 = new ApplicationMock(3L, "net.violet.rss.Game Spot", getKowalskyUser(), now);
		final Lang frLang = getSiteFrLang();
		final String theTestCategoryName = theApplication1.getCategory().getName();
		Assert.assertEquals(theTestCategoryName, theApplication2.getCategory().getName());
		theApplication1.addLang(getFrLang());
		theApplication1.addLang(frLang);
		theApplication1.addLang(getUsLang());
		theApplication2.addLang(getFrLang());
		theApplication3.addLang(frLang);
		Assert.assertNotNull(theTestCategoryName);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "net.violet.rss.");
		theParams.put("language", frLang.getIsoCode());

		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		final Action theAction = new GetApplications();
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List<Object> theResultAsList = (List<Object>) theResult;
		Assert.assertEquals(2, theResultAsList.size());

		for (final Object o : theResultAsList) {
			Assert.assertTrue(o instanceof Map);
			final Map<String, Object> theAppli = (Map<String, Object>) o;

			//((Map<String, Object>) theAppli.get("name"));
			Assert.assertTrue((theAppli.get("name").equals(theApplication1.getName())) || (theAppli.get("name").equals(theApplication3.getName())));
		}
	}

	@Test(expected = InvalidParameterException.class)
	public void badStart() throws APIException {
		final Application theApplication1 = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication1);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "net.violet.oups.");
		theParams.put("language", "fr");
		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		final Action theAction = new GetApplications();
		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidParameterException.class)
	public void badLanguage() throws APIException {
		final Application theApplication1 = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication1);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "net.violet.podcast.");
		theParams.put("language", "fr-UK");
		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		final Action theAction = new GetApplications();
		theAction.processRequest(theActionParam);
	}
}
