package net.violet.platform.api.actions.items;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.libraries.Delete;
import net.violet.platform.api.actions.libraries.Get;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchItemException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class DeleteTest extends AbstractTestBase {

	@Test
	public void testDelete() throws APIException {

		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Music theMusic = new MusicMock(1L, "name1", new FilesMock("$HOME/Desktop/DeleteMPEG/1", MimeType.MIME_TYPES.A_MPEG), theOwner, 42, 0, Music.TYPE_MP3_LIBRARY);

		final Action theAction = new Delete();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));
		theParams.put(ActionParam.MAIN_PARAM_KEY, MusicData.getData(theMusic).getApiId(caller));

		final Object theResult = theAction.processRequest(new ActionParam(caller, theParams));
		Assert.assertNull(theResult);
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParameter() throws APIException {

		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new Delete();
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	@Test(expected = NoSuchItemException.class)
	public void testNoSuchItemException() throws APIException {

		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));
		theParams.put(ActionParam.MAIN_PARAM_KEY, "n'importequoi");

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new Delete();
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	@Test(expected = NoSuchItemException.class)
	public void testDoubleDelete() throws APIException {

		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final FilesMock theFile = new FilesMock("$HOME/Desktop/DeleteMPEG/1", MimeType.MIME_TYPES.JPEG);
		new MusicMock(1L, "name1", theFile, theOwner, 42, 0, Music.TYPE_IMAGE_USER_LIBRARY, 0);

		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();

		final String itemId = setItemId(caller, theOwner, expiration);

		HashMap<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));
		theParams.put(ActionParam.MAIN_PARAM_KEY, itemId);

		ActionParam theActionParam = new ActionParam(caller, theParams);

		Delete theAction = new Delete();
		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);

		theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));
		theParams.put(ActionParam.MAIN_PARAM_KEY, itemId);
		theActionParam = new ActionParam(caller, theParams);

		theAction = new Delete();
		theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	private String setItemId(APICaller caller, User theOwner, Date expiration) throws APIException {
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));
		theParams.put(ActionParam.MAIN_PARAM_KEY, "images");
		theParams.put("skip", 0);
		theParams.put("count", 24);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new Get();
		final Object theResult = theAction.processRequest(theActionParam);
		final List theResultAsList = (List) theResult;
		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResultAsList.get(0);
		final String itemId = (String) theResultAsMap.get("id");
		return itemId;
	}

	@Test(expected = InvalidSessionException.class)
	public void testInvalidSession() throws APIException {
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final FilesMock theFile = new FilesMock("$HOME/Desktop/DeleteMPEG/1", MimeType.MIME_TYPES.JPEG);
		new MusicMock(1L, "name1", theFile, theOwner, 42, 0, Music.TYPE_IMAGE_USER_LIBRARY, 0);

		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();

		final String itemId = setItemId(caller, theOwner, expiration);

		final HashMap<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, "nimportequoi");
		theParams.put(ActionParam.MAIN_PARAM_KEY, itemId);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Delete theAction = new Delete();
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}
}
