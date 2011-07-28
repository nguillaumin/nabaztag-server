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
import net.violet.platform.api.actions.libraries.GetRecent;
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

public class GetRecentTest extends AbstractTestBase {

	@Test
	public void testItemInformationMap() throws APIException {

		final Lang frLang = getSiteFrLang();
		final User theOwner423 = new UserMock(423, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final User theOwner422 = new UserMock(422, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 10);
		Date inDate = theCal.getTime();
		final List<Files> theFiles = new ArrayList<Files>();
		theFiles.add(new FilesMock(1001L, "$HOME/Desktop/GetRecent/audio/1", MimeType.MIME_TYPES.A_MPEG, inDate));
		theCal.add(Calendar.DAY_OF_MONTH, -1);
		inDate = theCal.getTime();
		theFiles.add(new FilesMock(1002L, "$HOME/Desktop/GetRecent/audio/2", MimeType.MIME_TYPES.A_MPEG, inDate));
		theCal.add(Calendar.DAY_OF_MONTH, -1);
		inDate = theCal.getTime();
		theFiles.add(new FilesMock(1003L, "$HOME/Desktop/GetRecent/audio/3", MimeType.MIME_TYPES.A_MPEG, inDate));
		theCal.add(Calendar.DAY_OF_MONTH, -1);
		inDate = theCal.getTime();
		theFiles.add(new FilesMock(1004L, "$HOME/Desktop/GetRecent/images/4", MimeType.MIME_TYPES.JPEG, inDate));
		theCal.add(Calendar.DAY_OF_MONTH, -1);
		inDate = theCal.getTime();
		theFiles.add(new FilesMock(1005L, "$HOME/Desktop/GetRecent/video/5", MimeType.MIME_TYPES.A_MPEG, inDate));

		final Iterator<Files> it = theFiles.iterator();
		final List<Music> theMusic = new ArrayList<Music>();
		theMusic.add(new MusicMock(101L, net.violet.common.StringShop.EMPTY_STRING, it.next(), theOwner422, 0, 0, 0));
		theMusic.add(new MusicMock(102L, net.violet.common.StringShop.EMPTY_STRING, it.next(), theOwner422, 0, 0, 0));
		theMusic.add(new MusicMock(103L, net.violet.common.StringShop.EMPTY_STRING, it.next(), theOwner423, 0, 0, 0));
		theMusic.add(new MusicMock(104L, net.violet.common.StringShop.EMPTY_STRING, it.next(), theOwner423, 0, 0, 0));
		theMusic.add(new MusicMock(105L, net.violet.common.StringShop.EMPTY_STRING, it.next(), theOwner422, 0, 0, 0));
		theMusic.add(new MusicMock(106L, "Bad Mime Type", theFiles.get(1), theOwner423, 0, 0, 0));
		final Action theAction = new GetRecent();
		final APICaller caller = getPublicApplicationAPICaller();
		Map<String, Object> theParams = new HashMap<String, Object>();
		final Date expiration = theCal.getTime();

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner423), expiration));
		theParams.put(ActionParam.MAIN_PARAM_KEY, "images");

		ActionParam theActionParam = new ActionParam(caller, theParams);
		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		List theResultAsList = (List) theResult;
		Assert.assertEquals(theResultAsList.size(), 1);
		Assert.assertTrue(theResultAsList.get(0) instanceof Map);
		final Map<String, Object> theResultAsMap = (Map<String, Object>) theResultAsList.get(0);
		Assert.assertEquals("images", theResultAsMap.get("mime_type"));
		Assert.assertEquals(UserData.getData(theOwner423).getApiId(caller), theResultAsMap.get("owner"));
		final MusicData mdata = MusicData.getData(theMusic.get(3));
		Assert.assertEquals(mdata.getMusic_url(), theResultAsMap.get("url"));
		Assert.assertEquals(mdata.getApiId(caller), theResultAsMap.get("id"));
		Assert.assertEquals(mdata.getCreationDate(), theResultAsMap.get("creation_date"));
		theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theOwner423), expiration));
		theParams.put(ActionParam.MAIN_PARAM_KEY, "all");
		theParams.put("count", 3);
		theActionParam = new ActionParam(caller, theParams);
		theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		theResultAsList = (List) theResult;
		Assert.assertEquals(theResultAsList.size(), 3);
	}

	@Test(expected = InvalidParameterException.class)
	public void testinvalidParam() throws APIException {
		final Action theAction = new GetRecent();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "all");
		theParams.put("count", -4);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

}
