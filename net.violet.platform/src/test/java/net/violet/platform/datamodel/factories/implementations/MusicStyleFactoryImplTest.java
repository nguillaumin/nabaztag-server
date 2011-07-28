package net.violet.platform.datamodel.factories.implementations;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.DBTest;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class MusicStyleFactoryImplTest extends DBTest {

	@Test
	public void findAllStylesByTypeTest() {
		List<MusicStyle> Categories = Factories.MUSIC_STYLE.findAllStylesByType(Music.TYPE_MP3_USER_LIBRARY);
		Assert.assertEquals(1, Categories.size());
		Assert.assertEquals(4, Categories.get(0).getMusicstyle_id());
		Assert.assertEquals("mp3 signature", Categories.get(0).getMusicstyle_name());
		Categories = Factories.MUSIC_STYLE.findAllStylesByType(Music.TYPE_MP3_LIBRARY);
		Assert.assertEquals(1, Categories.size());
		Assert.assertEquals(29, Categories.get(0).getMusicstyle_id());
		Assert.assertEquals("mp3 perso", Categories.get(0).getMusicstyle_name());
		Categories = Factories.MUSIC_STYLE.findAllStylesByType(Music.TYPE_MP3_LITTLE_WORDS);
		Assert.assertEquals(3, Categories.size());
		final List<String> namesCat = new ArrayList<String>();
		for (final MusicStyle inMusicStyle : Categories) {
			namesCat.add(inMusicStyle.getMusicstyle_name());
		}
		Assert.assertTrue(namesCat.contains("reveil"));
		Assert.assertTrue(namesCat.contains("tts perso"));
		Assert.assertTrue(namesCat.contains("clin bonjour"));

	}
}
