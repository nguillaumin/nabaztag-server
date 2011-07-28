package net.violet.platform.api.actions.people;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.MusicStyleMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class GetSignatureTest extends AbstractTestBase {

	@Test
	public void getSignatureTest() throws APIException {
		final Date now = new Date();
		final Lang enLang = Factories.LANG.findByIsoCode("en");
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, "noreply@violet.net", enLang, "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 1);
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Anim chaos = Factories.ANIM.findByName("LOC_anim/chaos");
		final FilesMock monFileMock = new FilesMock("peole/GetSignatureTest/1.mpeg", MimeType.MIME_TYPES.A_MPEG);
		new MusicStyleMock(MusicStyle.CATEGORIE_MP3_PERSO, "mp3 signature", false);
		final MusicMock theMusic = new MusicMock(1L, "MP3_SIGNATURE NABSHARE", monFileMock, theOwner, MusicStyle.CATEGORIE_MP3_PERSO, 1, 1);
		theOwner.setSignatureInformation(chaos.getId(), "ff0000", theMusic.getId());

		final Action theAction = new GetSignature();

		final String userId = UserData.getData(theOwner).getApiId(caller);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);
		// setSessionParam(theParams);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof Map);

		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;
		Assert.assertNotNull(theResultAsMap.get("item"));

		final UserData inUserData = UserData.getData(theOwner);
		Assert.assertEquals(inUserData.getColorType().getLabel(), theResultAsMap.get("color"));
		final MusicData inMusicData = MusicData.getData(theMusic);
		Assert.assertEquals(inMusicData.getApiId(caller), theResultAsMap.get("item"));

		final AnimData inAnimData = AnimData.getData(chaos);
		Assert.assertEquals(inAnimData.getName(), theResultAsMap.get("anim"));

		chaos.delete();
		theMusic.delete();
		monFileMock.delete();
	}
}
