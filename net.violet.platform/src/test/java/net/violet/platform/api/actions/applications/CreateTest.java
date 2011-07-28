package net.violet.platform.api.actions.applications;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NameAlreadyExistsException;
import net.violet.platform.api.exceptions.NoSuchCategoryException;
import net.violet.platform.api.maps.applications.ApplicationProfileMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Test;

public class CreateTest extends MockTestBase {

	private Map<String, Object> initParams(APICaller inCaller) {
		final UserData theOwner = UserData.getData(getKowalskyUser());
		final FilesData settingFiles = FilesData.find(1);
		final FilesData schedulingFiles = FilesData.find(1);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(inCaller, theOwner, new Date(System.currentTimeMillis() + 12000000)));
		theParams.put(Create.NAME, "myCuteApplication");
		theParams.put(Create.CLASS, ApplicationClass.EXTERNAL.toString());
		theParams.put(Create.INTERACTIVE, true);
		theParams.put(Create.REMOVABLE, true);
		theParams.put(Create.VISIBLE, true);
		theParams.put(Create.CATEGORY, "LOC_srv_category_finance/title");
		theParams.put(Create.LANGUAGES, Collections.singletonList("fr"));
		theParams.put(Create.HARDWARES, Arrays.asList(ObjectType.MIRROR.getTypeName(), ObjectType.NABAZTAG_V2.getTypeName()));

		final Map<String, Object> profile = new HashMap<String, Object>();
		profile.put(ApplicationProfileMap.TITLE, "My Cute Application");
		profile.put(ApplicationProfileMap.DESCRIPTION, "This application is so cute.");
		profile.put(ApplicationProfileMap.OPEN_SOURCE, true);
		profile.put(ApplicationProfileMap.SETTING_FILE, settingFiles.getApiId(inCaller));
		profile.put(ApplicationProfileMap.SCHEDULING_FILE, schedulingFiles.getApiId(inCaller));

		theParams.put(Create.PROFILE_MAP, profile);

		return theParams;

	}

	@Test
	public void createVisibleTest() throws APIException {

		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final ActionParam theActionParam = new ActionParam(caller, initParams(caller));

		final Action theAction = new Create();
		theAction.processRequest(theActionParam);

		final ApplicationData newAppli = ApplicationData.findByName("myCuteApplication");
		Assert.assertNotNull(newAppli);
		Assert.assertTrue(newAppli.isValid());
		Assert.assertTrue(newAppli.isVisible());

		Assert.assertEquals(SiteLangData.getByISOCode("fr"), newAppli.getLangs().get(0));
		Assert.assertEquals(2, newAppli.getSupportedTypes().size());

		Assert.assertEquals(getKowalskyUser(), newAppli.getOwner().getReference());
	}

	@Test
	public void createNotVisibleTest() throws APIException {

		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = initParams(caller);
		theParams.remove(Create.VISIBLE);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new Create();
		theAction.processRequest(theActionParam);

		final ApplicationData newAppli = ApplicationData.findByName("myCuteApplication");
		Assert.assertNotNull(newAppli);
		Assert.assertFalse(newAppli.isVisible());
		Assert.assertEquals(getKowalskyUser(), newAppli.getOwner().getReference());
	}

	@Test(expected = NameAlreadyExistsException.class)
	public void createNameAlreadyExistsTest() throws APIException {

		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = initParams(caller);
		theParams.put(Create.NAME, theApplication.getName());
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new Create();
		theAction.processRequest(theActionParam);

	}

	@Test(expected = NoSuchCategoryException.class)
	public void createWrongCategoryTest() throws APIException {

		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = initParams(caller);
		theParams.put(Create.CATEGORY, theApplication.getName());
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new Create();
		theAction.processRequest(theActionParam);

	}

}
