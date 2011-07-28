package net.violet.platform.api.actions.nabcasts;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.applications.ApplicationProfileMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Signature.ColorType;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.dataobjects.ApplicationCategoryData;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.UserData;

import org.junit.Test;

public class UpdateTest extends AbstractTestBase {

	private Map<String, Object> initParams(APICaller inCaller) {
		final UserData theOwner = UserData.getData(getKowalskyUser());

		SessionManager.generateSessionId(inCaller, theOwner, new Date(System.currentTimeMillis() + 120000));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(net.violet.platform.api.actions.applications.Create.CATEGORY, "LOC_srv_category_finance/title");
		theParams.put(net.violet.platform.api.actions.applications.Create.LANGUAGES, Collections.singletonList("fr"));
		theParams.put(net.violet.platform.api.actions.applications.Create.HARDWARES, Arrays.asList(ObjectType.MIRROR.getTypeName(), ObjectType.NABAZTAG_V2.getTypeName()));
		theParams.put(Create.MODE_KEY, Create.MODE.RANDOM.toString());

		final Map<String, Object> signature = new HashMap<String, Object>();
		signature.put("anim", AnimData.findByName("LOC_anim/chaos").getApiId(inCaller));
		signature.put("color", ColorType.BLUE.toString());
		signature.put("item", FilesData.find(1).getApiId(inCaller));

		theParams.put("signature", signature);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(inCaller, theOwner, new Date(System.currentTimeMillis() + 120000)));

		final Map<String, Object> profile = new HashMap<String, Object>();
		profile.put(ApplicationProfileMap.TITLE, "My Cute Nabcast");
		profile.put(ApplicationProfileMap.DESCRIPTION, "This nabcast is so cute.");

		theParams.put(net.violet.platform.api.actions.applications.Create.PROFILE_MAP, profile);

		return theParams;

	}

	@Test
	public void updateTest() throws APIException {

		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final ActionParam theActionParam = new ActionParam(caller, initParams(caller));

		final Action theAction = new net.violet.platform.api.actions.nabcasts.Create();
		theAction.processRequest(theActionParam);

		final ApplicationData theNabcast = ApplicationData.findByName(Create.NABCAST_PREFIX + getKowalskyUser().getId() + ".My Cute Nabcast");

		final Map<String, Object> params = initParams(caller);
		params.put(net.violet.platform.api.actions.applications.Create.CATEGORY, "LOC_srv_category_news/title");
		params.put(net.violet.platform.api.actions.applications.Create.LANGUAGES, Arrays.asList("fr", "en"));
		params.put(net.violet.platform.api.actions.applications.Create.HARDWARES, Arrays.asList(ObjectType.MIRROR.getTypeName()));
		((Map<String, Object>) params.get(net.violet.platform.api.actions.applications.Create.PROFILE_MAP)).put(ApplicationProfileMap.TITLE, "new title");
		params.put(ActionParam.MAIN_PARAM_KEY, theNabcast.getApiId(caller));

		new Update().doProcessRequest(new ActionParam(caller, params));

		Assert.assertEquals(ApplicationCategoryData.findByName("LOC_srv_category_news/title"), theNabcast.getApplicationCategory());
		Assert.assertEquals(2, theNabcast.getLangs().size());
		Assert.assertEquals(2, theNabcast.getSupportedTypes().size());
		Assert.assertEquals("new title", theNabcast.getProfile().getTitle());
		Assert.assertEquals("net.violet.nabcast." + getKowalskyUser().getId() + ".new title", theNabcast.getName());

	}
}
