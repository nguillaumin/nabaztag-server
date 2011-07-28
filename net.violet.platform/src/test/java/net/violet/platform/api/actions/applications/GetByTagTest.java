package net.violet.platform.api.actions.applications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NoSuchTagException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationTagMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationTagData;

import org.junit.Assert;
import org.junit.Test;

public class GetByTagTest extends MockTestBase {

	@Test
	public void getByTagTest() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr");

		final ApplicationTag t1 = new ApplicationTagMock(1, "tagUn", frLang, 10);
		final ApplicationTag t2 = new ApplicationTagMock(2, "tagDeux", frLang, 5);

		final Application theApplication1 = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication1);
		final Application theApplication2 = getMySecondApplication();

		theApplication1.getTags().add(t1);
		theApplication1.getTags().add(t2);
		theApplication2.getTags().add(t1);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, new ApplicationTagData(t1).getApiId(caller));
		theParams.put("language", frLang.getIsoCode());

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new GetByTag();
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);

		final List<Map<String, Object>> theResultAsList = (List<Map<String, Object>>) theResult;
		Assert.assertEquals(2, theResultAsList.size());

		for (final Map<String, Object> o : theResultAsList) {
			Assert.assertTrue(o.get("name").equals(theApplication1.getName()) || o.get("name").equals(theApplication2.getName()));
		}
	}

	@Test(expected = NoSuchTagException.class)
	public void unknownTagTest() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final Application theApplication1 = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication1);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "unknowntag");
		theParams.put("language", frLang.getIsoCode());

		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);

		final Action theAction = new GetByTag();
		theAction.processRequest(theActionParam);
	}

}
