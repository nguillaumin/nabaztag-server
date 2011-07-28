package net.violet.platform.api.actions.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.libraries.Get;
import net.violet.platform.api.actions.libraries.GetCategories;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.CategMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.MusicStyleMock;
import net.violet.platform.datamodel.mock.UserMock;

import org.junit.Assert;
import org.junit.Test;

public class GetCategoriesTest extends AbstractTestBase {

	public void init() {
		final Lang frLang = Factories.LANG.findByIsoCode("fr-FR");
		final User theOwner = new UserMock(142, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, frLang, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());

		final List<Files> theFiles = new ArrayList<Files>();
		theFiles.add(new FilesMock("GetCategoryTest/1.mpeg", MimeType.MIME_TYPES.A_MPEG));
		theFiles.add(new FilesMock("GetCategoryTest/2.mpeg", MimeType.MIME_TYPES.A_MPEG));
		final Iterator<Files> it = theFiles.iterator();
		final List<Music> theMusic = new ArrayList<Music>();
		long i = 1;
		while (it.hasNext()) {
			final Files nextFile = it.next();
			theMusic.add(new MusicMock(i, "CLIN_BONJOUR LITTLE_WORDS " + i++, nextFile, theOwner, MusicStyle.CATEGORIE_CLIN_BONJOUR, 0, Music.TYPE_MP3_LITTLE_WORDS, 1));
			theMusic.add(new MusicMock(i, "MP3_PERSO LIBRARY" + i++, nextFile, theOwner, MusicStyle.CATEGORIE_MP3_PERSO, 0, Music.TYPE_MP3_LIBRARY, 1));
			theMusic.add(new MusicMock(i, "REVEIL LITTLE_WORDS" + i++, nextFile, theOwner, MusicStyle.CATEGORIE_REVEIL, 0, Music.TYPE_MP3_LITTLE_WORDS, 1));
			theMusic.add(new MusicMock(i, "TTS_PERSO LITTLE_WORDS" + i++, nextFile, theOwner, MusicStyle.CATEGORIE_TTS_PERSO, 0, Music.TYPE_MP3_LITTLE_WORDS, 1));
			theMusic.add(new MusicMock(i, "CLIN_BONJOUR LITTLE_WORDS" + i++, nextFile, theOwner, MusicStyle.CATEGORIE_CLIN_BONJOUR, 0, Music.TYPE_MP3_LITTLE_WORDS, 1));
		}
		new MusicStyleMock(MusicStyle.CATEGORIE_CLIN_BONJOUR, "clin bonjour", false);
		new MusicStyleMock(MusicStyle.CATEGORIE_MP3_PERSO, "mp3 perso", false);
		new MusicStyleMock(MusicStyle.CATEGORIE_REVEIL, "reveil", false);
		new MusicStyleMock(MusicStyle.CATEGORIE_TTS_PERSO, "tts perso", false);

		new CategMock(MusicStyle.CATEGORIE_CLIN_BONJOUR, "clin bonjour");
	}

	@Test
	public void testGetCategories() throws APIException {
		init();

		final Action theAction = new GetCategories();
		final APICaller caller = getPublicApplicationAPICaller();
		Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, "little_words");
		ActionParam theActionParam = new ActionParam(caller, theParams);
		Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		List theResultAsList = (List) theResult;
		Assert.assertEquals(theResultAsList.size(), 3);
		Assert.assertTrue(theResultAsList.contains("reveil"));
		Assert.assertTrue(theResultAsList.contains("clin bonjour"));

		theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "nabshare");
		theActionParam = new ActionParam(caller, theParams);
		theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		theResultAsList = (List) theResult;
		Assert.assertEquals(1, theResultAsList.size());

	}

	@Test(expected = InvalidParameterException.class)
	public void testinvalidParam() throws APIException {
		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "reveil");
		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNull(theResult);
	}
}
