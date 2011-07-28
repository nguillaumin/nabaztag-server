package net.violet.platform.api.actions.items;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.libraries.Count;
import net.violet.platform.api.actions.libraries.Get;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

//compare resultat de Count() avec lonqueur de liste rendue par Get() < "all"

public class CountTest extends AbstractTestBase {

	@Test
	public void testCount() throws APIException {

		final Lang frLang = getSiteFrLang();
		final List<User> theOwner = new ArrayList<User>();
		theOwner.add(new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone()));
		theOwner.add(new UserMock(43, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone()));
		theOwner.add(new UserMock(44, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone()));
		theOwner.add(new UserMock(45, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone()));

		final List<Files> theFiles = new ArrayList<Files>();
		theFiles.add(new FilesMock("$HOME/Desktop/CountTestMPEG/1", MimeType.MIME_TYPES.A_MPEG));
		theFiles.add(new FilesMock("$HOME/Desktop/CountTestMPEG/2", MimeType.MIME_TYPES.A_MPEG));
		theFiles.add(new FilesMock("$HOME/Desktop/CountTestFLASH/3", MimeType.MIME_TYPES.A_MPEG));

		final Iterator<Files> it = theFiles.iterator();
		final List<Music> theMusic = new ArrayList<Music>();
		theMusic.add(new MusicMock(1L, "name", it.next(), null, 42, 0, 7, 0));
		theMusic.add(new MusicMock(2L, "name", it.next(), null, 42, 0, 2, 0));
		theMusic.add(new MusicMock(3L, "name", it.next(), null, 42, 0, 5, 0));
		theMusic.add(new MusicMock(4L, "name", theFiles.get(0), null, 42, 0, 4));
		theMusic.add(new MusicMock(5L, "name", theFiles.get(1), null, 43, 0, 1));
		theMusic.add(new MusicMock(6L, "name", theFiles.get(1), null, 43, 0, 2));
		theMusic.add(new MusicMock(7L, "name", theFiles.get(1), null, 43, 0, 3));
		theMusic.add(new MusicMock(8L, "name", theFiles.get(1), null, 43, 0, 4));
		theMusic.add(new MusicMock(9L, "name", theFiles.get(1), null, 43, 0, 5));
		theMusic.add(new MusicMock(10L, "name", theFiles.get(1), null, 43, 0, 6));
		theMusic.add(new MusicMock(10L, "name", theFiles.get(1), null, 44, 0, 4));
		theMusic.add(new MusicMock(10L, "name", theFiles.get(1), null, 44, 0, 4));
		theMusic.add(new MusicMock(10L, "name", theFiles.get(1), null, 44, 0, 4));
		theMusic.add(new MusicMock(10L, "name", theFiles.get(1), null, 44, 0, 5));

		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();

		final String[] LibrarySelector = { "all", "audio", "images" };
		for (int j = 0; j < LibrarySelector.length; j++) {
			for (int i = 0; i < theOwner.size(); i++) {

				Action theAction = new Get();
				APICaller caller = getPublicApplicationAPICaller();
				Map<String, Object> theParams = new HashMap<String, Object>();

				theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner.get(i)), expiration));
				theParams.put(ActionParam.MAIN_PARAM_KEY, LibrarySelector[j]);
				ActionParam theActionParam = new ActionParam(caller, theParams);
				Object theResult = theAction.processRequest(theActionParam);
				Assert.assertNotNull(theResult);
				Assert.assertTrue(theResult instanceof List);
				final List theResultAsList = (List) theResult;
				final long allItemsByGet = theResultAsList.size();

				theAction = new Count();
				caller = getPublicApplicationAPICaller();
				theParams = new HashMap<String, Object>();

				theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner.get(i)), expiration));
				theParams.put(ActionParam.MAIN_PARAM_KEY, LibrarySelector[j]);
				theActionParam = new ActionParam(caller, theParams);
				theResult = theAction.processRequest(theActionParam);
				Assert.assertNotNull(theResult);
				Assert.assertTrue(theResult instanceof Long);
				final Long theResultAsLong = (Long) theResult;
				Assert.assertEquals(allItemsByGet, theResultAsLong.longValue());
			}
		}
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParam() throws APIException {
		final Action theAction = new Count();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "flash");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	@Test(expected = InvalidSessionException.class)
	public void testInvalidSession() throws APIException {
		final Action theAction = new Count();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "all");
		theParams.put(ActionParam.SESSION_PARAM_KEY, "ee51O935b3af9");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}
}
