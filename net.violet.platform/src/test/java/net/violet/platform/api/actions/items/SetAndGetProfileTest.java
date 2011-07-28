package net.violet.platform.api.actions.items;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.libraries.Get;
import net.violet.platform.api.actions.libraries.GetProfile;
import net.violet.platform.api.actions.libraries.Put;
import net.violet.platform.api.actions.libraries.SetProfile;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.CategMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

//compare resultat de Count() avec lonqueur de liste rendue par Get() < "all"

public class SetAndGetProfileTest extends AbstractTestBase {

	@Test
	public void testPutAndGet() throws APIException {

		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		// Files inFileMPEG= new FilesMock("$HOME/Desktop/sons/2",
		// MIME_TYPES.A_MPEG);
		final Files inFileVIDEO = new FilesMock("$HOME/Desktop/SendAndGetProfile/3", MimeType.MIME_TYPES.A_MPEG);
		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();

		final CategMock theCategMock = new CategMock(12, "nabshareVideo");

		Action theAction = new Put();
		Map<String, Object> theParams = new HashMap<String, Object>();
		final String session = SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration);
		theParams.put(ActionParam.SESSION_PARAM_KEY, session);
		final FilesData mdata = FilesData.getData(inFileVIDEO);
		theParams.put("file", mdata.getApiId(caller));

		ActionParam theActionParam = new ActionParam(caller, theParams);
		Object theResult = theAction.processRequest(theActionParam);
		Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;
		final String itemId = (String) theResultAsMap.get("id");

		theAction = new SetProfile();
		theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, session);
		theParams.put(ActionParam.MAIN_PARAM_KEY, itemId);

		final Map<String, Object> theProfile = new HashMap<String, Object>();
		theProfile.put("name", "blabla");
		theProfile.put("nabshare", theCategMock.getName());

		theParams.put("profile", theProfile);
		theActionParam = new ActionParam(caller, theParams);
		theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);

		theAction = new GetProfile();
		theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, session);
		theParams.put(ActionParam.MAIN_PARAM_KEY, itemId);
		theActionParam = new ActionParam(caller, theParams);
		theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof Map);
		theResultAsMap = (Map<String, Object>) theResult;
		Assert.assertEquals("blabla", theResultAsMap.get("name"));
		Assert.assertEquals(theCategMock.getName(), theResultAsMap.get("nabshare"));
		inFileVIDEO.delete();
	}

	@Test
	public void GetProfile() throws APIException {
		final Files theMidi = new FilesMock("/blabla/toto.mid", MimeType.MIME_TYPES.A_MIDI);
		final Files theMp3 = new FilesMock("/blabla/tutu.mp3", MimeType.MIME_TYPES.A_MPEG);
		final APICaller caller = getPublicApplicationAPICaller();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Action theAction = new Get();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final String session = SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration);
		theParams.put(ActionParam.SESSION_PARAM_KEY, session);
		final MusicData mdata = MusicData.getData(new MusicMock(0L, theMidi, "name1", theOwner, Music.TYPE_MP3_LIBRARY));
		theParams.put("id", mdata.getApiId(caller));

		ActionParam theActionParam = new ActionParam(caller, theParams);
		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		List theResultAsList = (List) theResult;
		Map<String, Object> theResultAsMap = (Map<String, Object>) theResultAsList.get(0);
		Assert.assertEquals("/blabla/toto.mp3", theResultAsMap.get("url"));

		final MusicData mp3data = MusicData.getData(new MusicMock(0L, theMp3, "name2", theOwner, Music.TYPE_MP3_LIBRARY));
		theParams.put("id", mp3data.getApiId(caller));

		theActionParam = new ActionParam(caller, theParams);
		theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		theResultAsList = (List) theResult;
		theResultAsMap = (Map<String, Object>) theResultAsList.get(0);
		Assert.assertEquals("/blabla/tutu.mp3", theResultAsMap.get("url"));
	}
}
