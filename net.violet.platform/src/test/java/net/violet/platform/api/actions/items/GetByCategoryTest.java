package net.violet.platform.api.actions.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.libraries.GetByCategory;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.BadMimeTypeException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.ItemInformationMap;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.CategMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.MusicStyleMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.MusicData;

import org.junit.Assert;
import org.junit.Test;

public class GetByCategoryTest extends AbstractTestBase {

	@Test
	public void testGetByCategory() throws APIException {

		final List<Music> theMusic = init();
		final Action theAction = new GetByCategory();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, "tts perso");
		theParams.put("language", "fr-FR");
		theParams.put("type", "little_words");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List<ItemInformationMap> theResultAsList = (List<ItemInformationMap>) theResult;
		Assert.assertEquals(2, theResultAsList.size());

		final List<ItemInformationMap> itemInfo = new ArrayList<ItemInformationMap>();
		for (final Music inMusic : theMusic) {
			final MusicData inMusicData = MusicData.getData(inMusic);
			if ((inMusic.getMusic_lang() == 1) && "tts perso".equals(inMusic.getMusicStyle().getMusicstyle_name()) && (inMusic.getMusic_type() == Music.TYPE_MP3_LITTLE_WORDS)) {
				itemInfo.add(new ItemInformationMap(caller, inMusicData));
			}
		}
		Collections.sort(itemInfo, new Comparator<ItemInformationMap>() {

			public int compare(ItemInformationMap o1, ItemInformationMap o2) {
				return ((String) o2.get("id")).compareTo((String) o1.get("id"));
			}
		});
		Collections.sort(theResultAsList, new Comparator<ItemInformationMap>() {

			public int compare(ItemInformationMap o1, ItemInformationMap o2) {
				return ((String) o2.get("id")).compareTo((String) o1.get("id"));
			}
		});
		int i = 0;
		for (final ItemInformationMap item1 : itemInfo) {
			final Map<String, Object> theResultAsMap = theResultAsList.get(i);
			Assert.assertEquals("audio", theResultAsMap.get("mime_type"));
			Assert.assertNotNull(theResultAsMap.get("owner"));
			Assert.assertEquals(item1.get("url"), theResultAsMap.get("url"));
			Assert.assertEquals(item1.get("id"), theResultAsMap.get("id"));
			Assert.assertEquals(item1.get("creation_date"), theResultAsMap.get("creation_date"));
			i++;
		}
	}

	@Test
	public void testGetByCategory_Nabshare() throws APIException {

		init();
		final Action theAction = new GetByCategory();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, "clin bonjour");
		theParams.put("language", "fr-FR");
		theParams.put("type", "nabshare");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List theResultAsList = (List) theResult;
		Assert.assertEquals(2, theResultAsList.size());
	}

	public List<Music> init() throws BadMimeTypeException {
		final Lang frLang = getSiteFrLang();
		final User theOwner = new UserMock(142, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final List<Files> theFiles = new ArrayList<Files>();
		theFiles.add(new FilesMock("GetCategoryTest/1.mpeg", MimeType.MIME_TYPES.A_MPEG));
		theFiles.add(new FilesMock("GetCategoryTest/2.mpeg", MimeType.MIME_TYPES.A_MPEG));
		final Iterator<Files> it = theFiles.iterator();
		final List<Music> theMusic = new ArrayList<Music>();
		long i = 1;
		while (it.hasNext()) {
			final Files nextFile = it.next();
			theMusic.add(new MusicMock(i, "CLIN_BONJOUR LITTLE_WORDS " + i++, nextFile, theOwner, MusicStyle.CATEGORIE_CLIN_BONJOUR, MusicStyle.CATEGORIE_CLIN_BONJOUR, 1));
			theMusic.add(new MusicMock(i, "MP3_PERSO LIBRARY" + i++, nextFile, theOwner, MusicStyle.CATEGORIE_MP3_PERSO, 0, 1));
			theMusic.add(new MusicMock(i, "REVEIL LITTLE_WORDS" + i++, nextFile, theOwner, MusicStyle.CATEGORIE_REVEIL, 0, 1));
			theMusic.add(new MusicMock(i, "TTS_PERSO LITTLE_WORDS" + i++, nextFile, theOwner, MusicStyle.CATEGORIE_TTS_PERSO, 0, Music.TYPE_MP3_LITTLE_WORDS, 1));
			theMusic.add(new MusicMock(i, "CLIN_BONJOUR LITTLE_WORDS" + i++, nextFile, theOwner, MusicStyle.CATEGORIE_CLIN_BONJOUR, 0, Music.TYPE_MP3_LITTLE_WORDS, 1));
		}
		new MusicStyleMock(MusicStyle.CATEGORIE_CLIN_BONJOUR, "clin bonjour", false);
		new MusicStyleMock(MusicStyle.CATEGORIE_MP3_PERSO, "mp3 perso", false);
		new MusicStyleMock(MusicStyle.CATEGORIE_REVEIL, "reveil", false);
		new MusicStyleMock(MusicStyle.CATEGORIE_TTS_PERSO, "tts perso", false);

		new CategMock(MusicStyle.CATEGORIE_CLIN_BONJOUR, "clin bonjour");
		return theMusic;
	}

	@Test(expected = InvalidParameterException.class)
	public void testinvalidParam() throws APIException {
		final Action theAction = new GetByCategory();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "tts perso");
		theParams.put("language", "fr-FR");
		theParams.put("type", "little words");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	@Test(expected = InvalidParameterException.class)
	public void testinvalidParam1() throws APIException {
		final Action theAction = new GetByCategory();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "tts perso");
		theParams.put("language", "nipor-kwa");
		theParams.put("type", "little_words");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}

	@Test(expected = InvalidParameterException.class)
	public void testinvalidParam2() throws APIException {
		final Action theAction = new GetByCategory();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("language", "fr-FR");
		theParams.put("type", "little_words");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}
}
