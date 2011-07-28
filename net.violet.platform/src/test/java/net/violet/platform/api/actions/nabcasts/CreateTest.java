package net.violet.platform.api.actions.nabcasts;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SignatureData;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class CreateTest extends AbstractTestBase {

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
	public void createVisibleTest() throws APIException {

		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final ActionParam theActionParam = new ActionParam(caller, initParams(caller));

		final Action theAction = new net.violet.platform.api.actions.nabcasts.Create();
		theAction.processRequest(theActionParam);

		final ApplicationData newAppli = ApplicationData.findByName(Create.NABCAST_PREFIX + getKowalskyUser().getId() + ".My Cute Nabcast");
		Assert.assertNotNull(newAppli);
		Assert.assertTrue(newAppli.isValid());
		Assert.assertFalse(newAppli.isVisible());

		Assert.assertEquals(SiteLangData.getByISOCode("fr"), newAppli.getLangs().get(0));
		Assert.assertEquals(2, newAppli.getSupportedTypes().size());

		Assert.assertEquals(getKowalskyUser(), newAppli.getOwner().getReference());

		final List<ApplicationSettingData> theSettings = ApplicationSettingData.findAllByApplication(newAppli);
		Assert.assertEquals(2, theSettings.size());

		final SignatureData signature = SignatureData.get(FilesData.find(1), AnimData.findByName("LOC_anim/chaos"), ColorType.BLUE);

		Assert.assertEquals(signature.getId().toString(), ApplicationSettingData.findByApplicationAndKey(newAppli, Create.SIGNATURE).getValue());
		Assert.assertEquals(Create.MODE.RANDOM.toString(), ApplicationSettingData.findByApplicationAndKey(newAppli, Create.MODE_KEY).getValue());

	}
}
