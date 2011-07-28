package net.violet.platform.api.actions.items;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.libraries.Put;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.BadMimeTypeException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

//compare resultat de Count() avec lonqueur de liste rendue par Get() < "all"

public class PutTest extends AbstractTestBase {

	@Test
	public void testPut() throws APIException {

		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Files inFileMPEG = new FilesMock("$HOME/Desktop/PutTest/2", MimeType.MIME_TYPES.A_MPEG);
		final Files inFileJPEG = new FilesMock("$HOME/Desktop/PutTest/3", MimeType.MIME_TYPES.JPEG);

		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));

		FilesData mdata = FilesData.getData(inFileJPEG);
		theParams.put("file", mdata.getApiId(caller));

		ActionParam theActionParam = new ActionParam(caller, theParams);
		Action theAction = new Put();
		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof Map);
		Map<String, Object> theResultAsMap = (Map<String, Object>) theResult;
		Assert.assertEquals("images", theResultAsMap.get("mime_type"));
		Assert.assertEquals(UserData.getData(theOwner).getApiId(caller), theResultAsMap.get("owner"));
		Assert.assertEquals(inFileJPEG.getPath(), theResultAsMap.get("url"));
		Assert.assertNotNull(theResultAsMap.get("id"));

		mdata = FilesData.getData(inFileMPEG);
		theParams.put("file", mdata.getApiId(caller));

		theActionParam = new ActionParam(caller, theParams);
		theAction = new Put();
		theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof Map);
		theResultAsMap = (Map<String, Object>) theResult;
		Assert.assertEquals("audio", theResultAsMap.get("mime_type"));
		Assert.assertEquals(UserData.getData(theOwner).getApiId(caller), theResultAsMap.get("owner"));
		Assert.assertEquals(inFileMPEG.getPath(), theResultAsMap.get("url"));
		Assert.assertNotNull(theResultAsMap.get("id"));
		inFileJPEG.delete();
		inFileMPEG.delete();
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
		final Action theAction = new Put();
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	@Test(expected = BadMimeTypeException.class)
	public void testBadMimeType() throws APIException {

		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Files inFileXML = new FilesMock("$HOME/DesktopPutTest/1", MimeType.MIME_TYPES.XML);

		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));

		final FilesData mdata = FilesData.getData(inFileXML);
		theParams.put("file", mdata.getApiId(caller));
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new Put();
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	@Test(expected = NoSuchFileException.class)
	public void testNoSuchFile() throws APIException {

		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final Files inFileXML = new FilesMock("$HOME/Desktop/PutTest/1", MimeType.MIME_TYPES.XML);

		final Date now = new Date();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));

		final FilesData mdata = FilesData.getData(inFileXML);
		final String APIId = mdata.getApiId(caller);
		inFileXML.delete();
		theParams.put("file", APIId);
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new Put();
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	@Test(expected = InvalidSessionException.class)
	public void testInvalidSession() throws APIException {
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		theParams.put(ActionParam.SESSION_PARAM_KEY, "nimportequoi");

		final Files inFileMPEG = new FilesMock("$HOME/Desktop/PutTest/1", MimeType.MIME_TYPES.A_MPEG);
		final FilesData mdata = FilesData.getData(inFileMPEG);
		theParams.put("file", mdata.getApiId(caller));

		final Action theAction = new Put();
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}
}
