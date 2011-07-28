package net.violet.platform.api.actions.site;

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
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.DicoMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class GetLocalizedStringTest extends AbstractTestBase {

	@Test
	public void testGetLocalizedString() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final Lang enLang = Factories.LANG.findByIsoCode("en");
		new DicoMock(1, "Service/title_weather", frLang, " c'est le service de la météo", net.violet.common.StringShop.EMPTY_STRING);
		new DicoMock(2, "Service/title_weather", enLang, " it's weather forecast service", net.violet.common.StringShop.EMPTY_STRING);
		final Action theAction = new GetLocalizedString();

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put("key", "Service/title_weather");
		theParams.put(ActionParam.MAIN_PARAM_KEY, frLang.getIsoCode());

		ActionParam theActionParam = new ActionParam(caller, theParams);
		Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertEquals(theResult, " c'est le service de la météo");

		theParams.put(ActionParam.MAIN_PARAM_KEY, enLang.getIsoCode());

		theActionParam = new ActionParam(caller, theParams);
		theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertEquals(theResult, " it's weather forecast service");
	}

	public void testNoFindDico() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		new DicoMock(1, "Service/title_weather", frLang, " c'est le service de la météo", net.violet.common.StringShop.EMPTY_STRING);
		final Action theAction = new GetLocalizedString();

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, frLang.getIsoCode());
		theParams.put("key", "Service/ti");

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final String result = theAction.processRequest(theActionParam).toString();
		Assert.assertEquals("Service/ti", result);
	}

	@Test
	public void testNotCleanMarkupInLocalizedString() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		new DicoMock(1, "User/myFirstRabbit", frLang, " l'utilisateur ${name} a un nouveau lapin ${rabbitname} !!", net.violet.common.StringShop.EMPTY_STRING);
		final Action theAction = new GetLocalizedString();

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, frLang.getIsoCode());
		theParams.put("key", "User/myFirstRabbit");

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertEquals(" l'utilisateur ${name} a un nouveau lapin ${rabbitname} !!", theResult);
	}

	@Test
	public void testReplaceParamInLocalizedString() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		new DicoMock(1, "User/myFirstRabbit", frLang, " l'utilisateur ${name} a un nouveau lapin ${rabbitname} !! Signé ${name}", net.violet.common.StringShop.EMPTY_STRING);
		final Action theAction = new GetLocalizedString();

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, frLang.getIsoCode());
		theParams.put("key", "User/myFirstRabbit");

		final Map<String, Object> theParameter = new HashMap<String, Object>();
		theParameter.put("name", "Private");
		theParameter.put("rabbitname", "Kowalsky");

		theParams.put("parameters", theParameter);

		ActionParam theActionParam = new ActionParam(caller, theParams);
		Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertEquals(theResult, " l'utilisateur Private a un nouveau lapin Kowalsky !! Signé Private");

		theParameter.remove("rabbitname");

		theActionParam = new ActionParam(caller, theParams);
		theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertEquals(theResult, " l'utilisateur Private a un nouveau lapin  !! Signé Private");

	}

	@Test
	public void testLOC_LocalizedString() throws APIException {
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		new DicoMock(1, "User/myFirstRabbit", frLang, " l'utilisateur ${name} a un nouveau lapin ${rabbitname} !!", net.violet.common.StringShop.EMPTY_STRING);
		final Action theAction = new GetLocalizedString();

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, frLang.getIsoCode());
		theParams.put("key", "LOC_User/myFirstRabbit");

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertEquals(" l'utilisateur ${name} a un nouveau lapin ${rabbitname} !!", theResult);
	}
}
