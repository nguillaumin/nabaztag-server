package net.violet.platform.api.actions.applications;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;

import org.junit.Test;

public class SetSettingsTest extends MockTestBase {

	@Test
	public void createSettingsTest() throws APIException {

		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, theApplication.getApiId(caller));

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, theApplication.getOwner(), new Date(System.currentTimeMillis() + 120000)));

		final Map<String, String> settings = new HashMap<String, String>();
		settings.put("setting1", "value1");
		settings.put("setting2", "value2");
		theParams.put("settings", settings);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new SetSettings();
		theAction.processRequest(theActionParam);

		final List<ApplicationSettingData> theSettings = ApplicationSettingData.findAllByApplication(theApplication);
		Assert.assertEquals(2, theSettings.size());
	}

}
