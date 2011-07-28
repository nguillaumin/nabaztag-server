package net.violet.platform.datamodel.factories.implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.violet.platform.api.exceptions.BadMimeTypeException;
import net.violet.platform.datamodel.DBTest;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MusicData.MimeTypes;

import org.junit.Assert;
import org.junit.Test;

public class MusicFactoryImplTest extends DBTest {

	@Test
	public void findByNabshareCategNameAndLang() {
		final Lang frLang = Factories.LANG.findByIsoCode("fr-FR");
		final Long frId = frLang.getId();
		List<Music> listMusic = Factories.MUSIC.findByNabshareCategName("clin bonjour", 0, 10);
		Assert.assertEquals(6, listMusic.size());
		for (final Music inMusic : listMusic) {
			Assert.assertEquals(frId, (Long) inMusic.getMusic_lang());
			Assert.assertTrue(inMusic.getMusic_name().startsWith("clin bonjour"));
		}
		listMusic = Factories.MUSIC.findByNabshareCategName("mp3 signature", 0, 10);
		Assert.assertEquals(2, listMusic.size());
		for (final Music inMusic : listMusic) {
			Assert.assertEquals(frId, (Long) inMusic.getMusic_lang());
			Assert.assertTrue(inMusic.getMusic_name().startsWith("mp3 signature"));
		}
	}

	@Test
	public void findAllStylesByTypeAndLangTest() {
		final Lang frLang = Factories.LANG.findByIsoCode("fr-FR");
		final Long frId = frLang.getId();
		final Lang plLang = Factories.LANG.findByIsoCode("pl-PL");
		final Long plId = plLang.getId();
		List<Music> listMusic = Factories.MUSIC.findAllStylesByTypeAndLang(Music.TYPE_MP3_LITTLE_WORDS, frLang, "tts perso", 0, 10);
		Assert.assertEquals(3, listMusic.size());
		for (final Music inMusic : listMusic) {
			Assert.assertEquals(frId, (Long) inMusic.getMusic_lang());
			Assert.assertEquals("tts perso", inMusic.getMusicStyle().getMusicstyle_name());
			Assert.assertTrue(inMusic.getMusic_name().startsWith("tts perso little words"));
		}
		listMusic = Factories.MUSIC.findAllStylesByTypeAndLang(Music.TYPE_MP3_LIBRARY, frLang, "mp3 perso", 0, 10);
		Assert.assertEquals(3, listMusic.size());
		for (final Music inMusic : listMusic) {
			Assert.assertEquals(frId, (Long) inMusic.getMusic_lang());
			Assert.assertEquals("mp3 perso", inMusic.getMusicStyle().getMusicstyle_name());
			Assert.assertTrue(inMusic.getMusic_name().startsWith("mp3 perso library"));
		}
		listMusic = Factories.MUSIC.findAllStylesByTypeAndLang(Music.TYPE_MP3_USER_LIBRARY, frLang, "mp3 signature", 0, 10);
		Assert.assertEquals(2, listMusic.size());
		for (final Music inMusic : listMusic) {
			Assert.assertEquals(frId, (Long) inMusic.getMusic_lang());
			Assert.assertEquals("mp3 signature", inMusic.getMusicStyle().getMusicstyle_name());
			Assert.assertTrue(inMusic.getMusic_name().startsWith("mp3 signature nabshare "));
		}
		listMusic = Factories.MUSIC.findAllStylesByTypeAndLang(Music.TYPE_MP3_LITTLE_WORDS, plLang, "reveil", 0, 10);
		Assert.assertEquals(4, listMusic.size());
		for (final Music inMusic : listMusic) {
			Assert.assertEquals(plId, (Long) inMusic.getMusic_lang());
			Assert.assertEquals("reveil", inMusic.getMusicStyle().getMusicstyle_name());
			Assert.assertTrue(inMusic.getMusic_name().startsWith("reveil little words"));
		}
	}

	@Test
	public void findAllByUserAndMimeTypeTest() {
		final User theOwner103 = Factories.USER.find(103L);
		final User theOwner104 = Factories.USER.find(104L);
		final String all = MimeTypes.all.getMimeType();
		final String audio = MimeTypes.audio.getMimeType();
		final String images = MimeTypes.images.getMimeType();
		Assert.assertEquals(3, Factories.MUSIC.findAllByUserIdAndType(theOwner103, all, 0, 10).size());
		Assert.assertEquals(2, Factories.MUSIC.findAllByUserIdAndType(theOwner103, all, 1, 10).size());
		Assert.assertEquals(1, Factories.MUSIC.findAllByUserIdAndType(theOwner103, all, 0, 1).size());
		Assert.assertEquals(2, Factories.MUSIC.findAllByUserIdAndType(theOwner103, audio, 0, 10).size());
		Assert.assertEquals(1, Factories.MUSIC.findAllByUserIdAndType(theOwner104, all, 0, 10).size());
		Assert.assertEquals(1, Factories.MUSIC.findAllByUserIdAndType(theOwner104, images, 0, 10).size());
	}

	@Test
	public void countAllItemsOfUserTest() {
		final User theOwner103 = Factories.USER.find(103L);
		final User theOwner104 = Factories.USER.find(104L);
		final int count = 100;
		final String[] LibrarySelector = { MimeTypes.all.getMimeType(), MimeTypes.audio.getMimeType(), MimeTypes.images.getMimeType() };
		for (int j = 0; j < LibrarySelector.length; j++) {
			Assert.assertEquals(Factories.MUSIC.countItemsOfUserByMimeType(theOwner103, LibrarySelector[j]), Factories.MUSIC.findAllByUserIdAndType(theOwner103, LibrarySelector[j], 0, count).size());
			Assert.assertEquals(Factories.MUSIC.countItemsOfUserByMimeType(theOwner104, LibrarySelector[j]), Factories.MUSIC.findAllByUserIdAndType(theOwner104, LibrarySelector[j], 0, count).size());
		}
	}

	@Test
	public void createNewMusicTest() throws BadMimeTypeException {
		final User theOwner103 = Factories.USER.find(103L);
		final User theOwner104 = Factories.USER.find(104L);
		final User theOwner42 = Factories.USER.find(42L);
		final int count = 100;
		final String all = MimeTypes.all.getMimeType();
		final long oldSum = Factories.MUSIC.countItemsOfUserByMimeType(theOwner103, all) + Factories.MUSIC.countItemsOfUserByMimeType(theOwner104, all) + Factories.MUSIC.countItemsOfUserByMimeType(theOwner42, all);
		int added = 0;
		final List<Files> listFiles = new ArrayList<Files>();
		for (long i = 1; i <= 4; i++) {
			listFiles.add(Factories.FILES.find(i));
		}
		final Iterator<Files> it = listFiles.iterator();
		final List<Music> listMusic = new ArrayList<Music>();
		while (it.hasNext()) {
			final Files inFile = it.next();
			listMusic.add(Factories.MUSIC.createNewMusic(theOwner103, inFile));
			listMusic.add(Factories.MUSIC.createNewMusic(theOwner104, inFile));
			added += 2;
		}
		final long tout = Factories.MUSIC.countItemsOfUserByMimeType(theOwner103, all) + Factories.MUSIC.countItemsOfUserByMimeType(theOwner104, all) + Factories.MUSIC.countItemsOfUserByMimeType(theOwner42, all);
		final List<Music> recents = Factories.MUSIC.findRecentsByType(all, count);
		Assert.assertEquals(recents.size(), tout);
		Assert.assertEquals(recents.size(), oldSum + added);
	}

	@Test
	public void findRecentsByTypeTest() {
		final User theOwner103 = Factories.USER.find(103L);
		final User theOwner104 = Factories.USER.find(104L);
		final User theOwner42 = Factories.USER.find(42L);
		final int count = 100;
		final String[] LibrarySelector = { MimeTypes.all.getMimeType(), MimeTypes.audio.getMimeType(), MimeTypes.images.getMimeType() };
		for (int j = 0; j < LibrarySelector.length; j++) {
			Assert.assertEquals(Factories.MUSIC.findRecentsByType(LibrarySelector[j], count).size(), Factories.MUSIC.findAllByUserIdAndType(theOwner103, LibrarySelector[j], 0, count).size() + Factories.MUSIC.findAllByUserIdAndType(theOwner104, LibrarySelector[j], 0, count).size() + Factories.MUSIC.findAllByUserIdAndType(theOwner42, LibrarySelector[j], 0, count).size());
		}
		Assert.assertEquals(Factories.MUSIC.findRecentsByType(LibrarySelector[0], count).get(0).getFile().getCreationDate().toString(), "2008-07-25 00:00:00.0");
	}

	//
	@Test
	public void findAllForSignatureTest() {
		final User theOwner103 = Factories.USER.find(103L);

		final List<Music> theResult = Factories.MUSIC.findAllForSignature(theOwner103);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult.size() > 0);
	}

	@Test
	public void putTest() throws BadMimeTypeException {
		final User theOwner103 = Factories.USER.find(103L);
		final User theOwner104 = Factories.USER.find(104L);
		final Files theFileMPEG = Factories.FILES.find(2L);
		final Files theFileJPEG = Factories.FILES.find(4L);
		final Music inMusicMPEG = Factories.MUSIC.createNewMusic(theOwner104, theFileMPEG);
		final Music inMusicJPEG = Factories.MUSIC.createNewMusic(theOwner103, theFileJPEG);

		Assert.assertEquals(inMusicMPEG.getMusic_type(), Music.TYPE_MP3_USER_LIBRARY);
		Assert.assertEquals(inMusicJPEG.getMusic_type(), Music.TYPE_IMAGE_USER_LIBRARY);
	}

	@Test(expected = BadMimeTypeException.class)
	public void InvalidParameterExceptionTest() throws BadMimeTypeException {
		final User theOwner103 = Factories.USER.find(103L);
		final Files theFileJSON = Factories.FILES.find(5L);
		Factories.MUSIC.createNewMusic(theOwner103, theFileJSON);
	}

}
