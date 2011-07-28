package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.application.factories.AirMessageFactory;
import net.violet.vlisp.services.ManageMessageServices.WEATHER_OPTIONS;

import org.junit.Assert;
import org.junit.Test;

public class ConfigFilesTest extends DBTest {

	@Test
	public void cacheAirUS() {
		final Map<String, List<ConfigFiles>> theMap = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.AIR, Factories.LANG.find(2));

		Assert.assertTrue(theMap.containsKey(AirMessageFactory.STATES.GOOD.toString()));
		Assert.assertTrue(theMap.containsKey(AirMessageFactory.STATES.BAD.toString()));
		Assert.assertTrue(theMap.containsKey(AirMessageFactory.STATES.MIDDLE.toString()));
		Assert.assertTrue(theMap.containsKey(SERVICES.AIR.toString()));

		String path2mp3 = "broadcast/broad/config/air/us/quality/good.mp3";
		Assert.assertEquals(path2mp3, theMap.get(AirMessageFactory.STATES.GOOD.toString()).get(0).getFiles().getPath());
		path2mp3 = "broadcast/broad/config/reco/us/Air.mp3";
		Assert.assertEquals(path2mp3, theMap.get(SERVICES.AIR.toString()).get(0).getFiles().getPath());
	}

	@Test
	public void cacheTrafficUS() {
		final Map<String, List<ConfigFiles>> theMap = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.TRAFFIC, Factories.LANG.find(2));

		for (int i = 1; i <= 10; i++) {
			Assert.assertTrue(theMap.containsKey(String.valueOf(i)));
		}

		String path2mp3 = "broadcast/broad/config/traffic/us/delay/1.mp3";
		Assert.assertEquals(path2mp3, theMap.get("1").get(0).getFiles().getPath());
		path2mp3 = "broadcast/broad/config/reco/us/Trafic.mp3";
		Assert.assertEquals(path2mp3, theMap.get(SERVICES.TRAFFIC.toString()).get(0).getFiles().getPath());
	}

	@Test
	public void cacheMeteoUS() {
		final Map<String, List<ConfigFiles>> theMap = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.WEATHER, Factories.LANG.find(2));

		Assert.assertTrue(theMap.containsKey("TEMP"));
		Assert.assertTrue(theMap.containsKey("SKY"));

		String path2mp3 = "broadcast/broad/config/weather/us/today.mp3";
		Assert.assertEquals(path2mp3, theMap.get(WEATHER_OPTIONS.TODAY.toString()).get(0).getFiles().getPath());
		path2mp3 = "broadcast/broad/config/weather/us/temp/6.mp3";
		Assert.assertEquals(path2mp3, theMap.get(WEATHER_OPTIONS.TEMP.toString()).get(6 + (-(-9))).getFiles().getPath());
		path2mp3 = "broadcast/broad/config/weather/us/sky/2.mp3";
		Assert.assertEquals(path2mp3, theMap.get(WEATHER_OPTIONS.SKY.toString()).get(2).getFiles().getPath());
		path2mp3 = "broadcast/broad/config/reco/us/Meteo.mp3";
		Assert.assertEquals(path2mp3, theMap.get(WEATHER_OPTIONS.METEO.toString()).get(0).getFiles().getPath());
	}

	@Test
	public void cacheRecoWelcomeUS() {
		final Map<String, List<ConfigFiles>> theMap = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.RECO, Factories.LANG.find(2));

		Assert.assertTrue(theMap.containsKey("WELCOME"));

		final String path2mp3 = "broadcast/broad/config/welcome/us/record.mp3";
		Assert.assertEquals(path2mp3, theMap.get("WELCOME").get(0).getFiles().getPath());
	}

	@Test
	public void cacheRecoUS() {
		final Map<String, List<ConfigFiles>> theMap = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.RECO, Factories.LANG.find(2));

		Assert.assertTrue(theMap.containsKey("BADCOMMUNION"));
		Assert.assertTrue(theMap.containsKey("2"));

		String path2mp3 = "broadcast/broad/config/reco/us/Comprend2.mp3";
		Assert.assertEquals(path2mp3, theMap.get("2").get(0).getFiles().getPath());
		path2mp3 = "broadcast/broad/config/reco/us/badcommunion.mp3";
		Assert.assertEquals(path2mp3, theMap.get("BADCOMMUNION").get(0).getFiles().getPath());
	}

	@Test
	public void cacheBourseFreeUS() {
		final Map<String, List<ConfigFiles>> theMap = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.BOURSE_FREE, Factories.LANG.find(2));

		Assert.assertTrue(theMap.containsKey("BOURSE"));
		Assert.assertTrue(theMap.containsKey("2"));

		String path2mp3 = "broadcast/broad/config/money/us/trend/4.mp3";
		Assert.assertEquals(path2mp3, theMap.get("4").get(0).getFiles().getPath());
		path2mp3 = "broadcast/broad/config/reco/us/Bourse.mp3";
		Assert.assertEquals(path2mp3, theMap.get("BOURSE").get(0).getFiles().getPath());
	}

	@Test
	public void cacheMoodsUS() {
		final Map<String, List<ConfigFiles>> theMap = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.MOODS, Factories.LANG.find(2));

		Assert.assertTrue(theMap.containsKey(SERVICES.MOODS.name().toUpperCase()));
		Assert.assertEquals(295, theMap.get(SERVICES.MOODS.name().toUpperCase()).size());
		final String path2mp3 = "broadcast/broad/config/surprise/us/82.mp3";
		Assert.assertEquals(path2mp3, theMap.get(SERVICES.MOODS.name().toUpperCase()).get(0).getFiles().getPath());
	}

	@Test
	public void cacheBilanUS() {
		final Map<String, List<ConfigFiles>> theMap = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.BILAN, Factories.LANG.find(2));

		Assert.assertTrue(theMap.containsKey("2"));

		final String path2mp3 = "broadcast/broad/config/resume/us/comment/2.mp3";
		Assert.assertEquals(path2mp3, theMap.get("2").get(0).getFiles().getPath());
	}

	@Test
	public void cacheClockGenericUS() {
		final Map<String, List<ConfigFiles>> theMap = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.CLOCK_GENERIC, Factories.LANG.find(2));

		Assert.assertTrue(theMap.containsKey("ALL"));
		Assert.assertEquals(14, theMap.get("ALL").size());

		final String path2mp3 = "broadcast/broad/config/clockall/us/HG1us.mp3";
		Assert.assertEquals(path2mp3, theMap.get("ALL").get(0).getFiles().getPath());
	}

	@Test
	public void cacheClockAbnormalUS() {
		final Map<String, List<ConfigFiles>> theMap = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.CLOCK_ABNORMAL, Factories.LANG.find(2));

		Assert.assertTrue(theMap.containsKey("2"));
		Assert.assertEquals(5, theMap.get("2").size());

		final String path2mp3 = "broadcast/broad/config/clock/us/2/5.mp3";
		Assert.assertEquals(path2mp3, theMap.get("2").get(4).getFiles().getPath());
	}

	@Test
	public void insertConfigFile() {

		try {
			final Files theFile = Factories.FILES.createFile("broadcast/broad/config/testConfigFilesHC.mp3", MimeType.MIME_TYPES.A_MPEG);
			new ConfigFilesImpl(Factories.LANG.find(1), SERVICES.CLOCK_HC.getApplication(), theFile, "TESTHC");
			theFile.delete();
		} catch (final SQLException e) {
			Assert.assertTrue(false);
		}

	}

	@Test
	public void findDefaultPicturesTest() {

		List<ConfigFiles> thePictureFiles = Factories.CONFIG_FILES.findDefaultPicturesByHardware(HARDWARE.V1);
		Assert.assertEquals(2, thePictureFiles.size());
		Assert.assertEquals("v1/image/1.jpg", thePictureFiles.get(0).getFiles().getPath());
		Assert.assertEquals("v1/image/2.jpg", thePictureFiles.get(1).getFiles().getPath());

		thePictureFiles = Factories.CONFIG_FILES.findDefaultPicturesByHardware(HARDWARE.V2);
		Assert.assertEquals(2, thePictureFiles.size());
		Assert.assertEquals("v2/image/1.jpg", thePictureFiles.get(0).getFiles().getPath());
		Assert.assertEquals("v2/image/2.jpg", thePictureFiles.get(1).getFiles().getPath());

		thePictureFiles = Factories.CONFIG_FILES.findDefaultPicturesByHardware(HARDWARE.MIRROR);
		Assert.assertEquals(2, thePictureFiles.size());
		Assert.assertEquals("mirror/image/1.jpg", thePictureFiles.get(0).getFiles().getPath());
		Assert.assertEquals("mirror/image/2.jpg", thePictureFiles.get(1).getFiles().getPath());
	}
}
