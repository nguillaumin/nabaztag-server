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
import net.violet.platform.api.actions.libraries.Get;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;

import org.junit.Assert;
import org.junit.Test;

public class GetTest extends AbstractTestBase {

	@Test
	public void testItemInformationMap() throws APIException {

		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final User theAnotherOwner = new UserMock(43, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final List<Files> theFiles = new ArrayList<Files>();
		theFiles.add(new FilesMock("/home/agata/Desktop/sons/1", MimeType.MIME_TYPES.A_MPEG));
		theFiles.add(new FilesMock("/home/agata/Desktop/sons/2", MimeType.MIME_TYPES.A_MPEG));
		theFiles.add(new FilesMock("/home/agata/Desktop/images/2", MimeType.MIME_TYPES.JPEG));

		final Iterator<Files> it = theFiles.iterator();
		final List<Music> theMusic = new ArrayList<Music>();
		theMusic.add(new MusicMock(1L, "name", it.next(), theOwner, 42, 0, 2, 0));
		theMusic.add(new MusicMock(2L, "name", it.next(), theOwner, 42, 0, 2, 0));
		theMusic.add(new MusicMock(3L, "name", it.next(), theOwner, 42, 0, 5, 0));
		theMusic.add(new MusicMock(4L, "name", theFiles.get(0), theOwner, 42, 0, 4, 0));
		theMusic.add(new MusicMock(5L, "name", theFiles.get(1), theAnotherOwner, 43, 0, 2, 0));
		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);
		final Date expiration = theCal.getTime();

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner), expiration));
		theParams.put(ActionParam.MAIN_PARAM_KEY, "all");
		theParams.put("skip", 1);
		theParams.put("count", 4);
		ActionParam theActionParam = new ActionParam(caller, theParams);
		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		List theResultAsList = (List) theResult;
		Assert.assertEquals(theResultAsList.size(), 3);

		theParams.put(ActionParam.MAIN_PARAM_KEY, "images");
		theParams.put("skip", 0);
		theParams.put("count", 24);
		theActionParam = new ActionParam(caller, theParams);
		theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		theResultAsList = (List) theResult;
		Assert.assertEquals(theResultAsList.size(), 1);

		Assert.assertTrue(theResultAsList.get(0) instanceof Map);
		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResultAsList.get(0);
		Assert.assertEquals("images", theResultAsMap.get("mime_type"));
		Assert.assertEquals(UserData.getData(theOwner).getApiId(caller), theResultAsMap.get("owner"));
		final MusicData mdata = MusicData.getData(theMusic.get(2));
		Assert.assertEquals(mdata.getMusic_url(), theResultAsMap.get("url"));
		Assert.assertEquals(mdata.getApiId(caller), theResultAsMap.get("id"));
		Assert.assertEquals(mdata.getCreationDate(), theResultAsMap.get("creation_date"));

	}

	@Test(expected = InvalidParameterException.class)
	public void testinvalidParam() throws APIException {
		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "flash");
		theParams.put("skip", "supa");
		theParams.put("count", 4);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

}
