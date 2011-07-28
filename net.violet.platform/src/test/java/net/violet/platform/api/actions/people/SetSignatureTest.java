package net.violet.platform.api.actions.people;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractPeopleWithSessionBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.ItemInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Signature.ColorType;
import net.violet.platform.datamodel.mock.AnimMock;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.MusicStyleMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class SetSignatureTest extends AbstractPeopleWithSessionBase {

	@Override
	public void testNoSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setSignatureTest(caller, theOwner, null);
	}

	@Override
	public void testOtherApplicationSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setSignatureTest(caller, theOwner, generateSessionAlterApplication(UserData.getData(theOwner)));
	}

	@Override
	public void testOtherUserSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(422, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setSignatureTest(caller, theOwner, generateSessionAlterUser(caller));
	}

	@Override
	public void testValidSession() throws APIException {
		final Date now = new Date();
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, "myLogin", "myPass", "myEmail@gmail.com", frLang, "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);;
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		setSignatureTest(caller, theOwner, generateSession(UserData.getData(theOwner), caller));
	}

	public void setSignatureTest(APICaller inCaller, User inUser, String inSessionId) throws APIException {

		final Action theAction = new SetSignature();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		setSessionParam(theParams, inSessionId);

		final String userId = UserData.getData(inUser).getApiId(inCaller);

		theParams.put(ActionParam.MAIN_PARAM_KEY, userId);

		final AnimMock chaos = new AnimMock(1L, "LOC_anim/chaos");
		final FilesMock monFileMock = new FilesMock("peole/GetSignatureTest/2.mpeg", MimeType.MIME_TYPES.A_MPEG);
		new MusicStyleMock(MusicStyle.CATEGORIE_MP3_PERSO, "mp3 signature", false);
		final MusicMock theMusic = new MusicMock(2L, "MP3_SIGNATURE NABSHARE", monFileMock, inUser, MusicStyle.CATEGORIE_MP3_PERSO, 1, 1);

		final MusicData inMusicData = MusicData.getData(theMusic);
		final ItemInformationMap testInfo = new ItemInformationMap(inCaller, inMusicData);

		final Map<String, Object> theSignature = new HashMap<String, Object>();
		theSignature.put("anim", "LOC_anim/chaos");
		theSignature.put("item", testInfo.get("id"));
		theSignature.put("color", ColorType.RED.getLabel());
		theParams.put("signature", theSignature);

		final UserData inUserData = UserData.getData(inUser);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);

		Assert.assertEquals(inUser.getColor().getId(), chaos.getId());
		Assert.assertEquals(inUserData.getColorType().getValue(), ColorType.RED.getValue());
		Assert.assertEquals(inUser.getMusic().getMusic_name(), theMusic.getMusic_name());
		Assert.assertEquals(inUser.getMusic().getId(), theMusic.getId());
	}

	@Test
	public void dummytest() {

	}
}
