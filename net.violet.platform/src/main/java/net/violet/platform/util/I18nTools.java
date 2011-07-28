package net.violet.platform.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.content.converters.ContentType;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ConfigFilesImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;

public class I18nTools {

	private final String path;
	private final boolean isCreate;
	private final Lang objectLang;

	private I18nTools(String inPath, boolean isCreate, Lang objectLang) {
		this.path = inPath;
		this.isCreate = isCreate;
		this.objectLang = objectLang;
	}

	private enum CONFIG_SERVICE {
		comment(Application.NativeApplication.BILAN, null, "config/resume/ISOCODE/comment/([1-5])", " = 5") {

			@Override
			public String getIndexByConfig(String config) {
				return this.getIndex();
			}
		}, //5
		clockall(Application.NativeApplication.CLOCK, "CLOCK_GEN/ALL", "config/clockall/ISOCODE/HG.*", " > 0") {

			@Override
			public String getIndexByConfig(String config) {
				return this.getIndex();
			}
		}, //1*
		clock(Application.NativeApplication.CLOCK, "CLOCK_ALL/", "config/clock/ISOCODE/([0-9])?(1[0-9])?(2[0-3])?/1", " = 24") {

			@Override
			public String getIndexByConfig(String config) {
				String index = config.substring(0, config.lastIndexOf("/"));
				index = index.substring(index.lastIndexOf("/") + 1, index.length());
				return this.getIndex() + index;
			}
		}, //24
		clockfun(Application.NativeApplication.CLOCK, "CLOCK/", "config/clock/ISOCODE/([0-9])?(1[0-9])?(2[0-3])?/([2-9])?(1[0-9])?", " > 0") {

			@Override
			public String getIndexByConfig(String config) {
				String index = config.substring(0, config.lastIndexOf("/"));
				index = index.substring(index.lastIndexOf("/") + 1, index.length());
				return this.getIndex() + index;
			}
		}, //1*
		traffic(Application.NativeApplication.TRAFIC, null, "config/traffic/ISOCODE/delay/([1-9])?(1[0-1])?", " = 11"), //11
		mail(Application.NativeApplication.MAIL, null, "config/mail/ISOCODE/(new)", " = 1"), //1
		dialogue(Application.NativeApplication.EARS_COMMUNION, null, "config/dialogue/ISOCODE/(accept)?(ask)?(cancel)?(reject)?", " = 4"), //4
		moneyplus(Application.NativeApplication.BOURSE_FULL, null, "config/moneyplus/ISOCODE/trend/([0-4])", " = 5"), //5
		money(Application.NativeApplication.BOURSE_FREE, null, "config/money/ISOCODE/trend/([0-4])", " = 5"), //5
		weatherparam(Application.NativeApplication.WEATHER, null, "config/weather/ISOCODE/(today)?(tomorrow)?(farenheit)?(degree)?", " = 4"), //4
		weathersky(Application.NativeApplication.WEATHER, "SKY_", "config/weather/ISOCODE/sky/([0-5])", " = 6") {

			@Override
			public String getIndexByConfig(String config) {
				return this.getIndex() + super.getIndexByConfig(config);
			}
		}, //6
		weathertemp(Application.NativeApplication.WEATHER, "TEMP_", "config/weather/ISOCODE/temp/([0-9]?[0-9]?[0-9])?(-[1-9]?[0-9])?", " = 120") {

			@Override
			public String getIndexByConfig(String config) {
				return this.getIndex() + super.getIndexByConfig(config);
			}
		}, //120
		surprise(Application.NativeApplication.MOOD, net.violet.common.StringShop.EMPTY_STRING, "config/surprise/ISOCODE/.*", " > 0") {

			@Override
			public String getIndexByConfig(String config) {
				return this.getIndex();
			}
		}, //1*
		air(Application.NativeApplication.AIR, null, "config/air/ISOCODE/quality/(good)?(middle)?(bad)?", " = 3"), //3
		recoWeather(Application.NativeApplication.WEATHER, null, "config/reco/ISOCODE/(Meteo)", " = 1"), //1
		recoTrafic(Application.NativeApplication.TRAFIC, null, "config/reco/ISOCODE/(Traffic)", " = 1"), //1
		recoAir(Application.NativeApplication.AIR, null, "config/reco/ISOCODE/(Air)", " = 1"), //1
		recoMoney(Application.NativeApplication.BOURSE_FREE, null, "config/reco/ISOCODE/(Bourse)", " = 1"), //1
		recoCommunion(null, null, "config/reco/ISOCODE/(badcommunion)", " = 1"), //1
		badreco(null, null, "config/reco/ISOCODE/(Comprend[0-9]?[0-9]?)", " > 5") {

			@Override
			public String getIndexByConfig(String config) {
				return super.getIndexByConfig(config).replace("COMPREND", net.violet.common.StringShop.EMPTY_STRING);
			}
		}, //5
		welcomeNew(null, null, "config/welcome/ISOCODE/(new)", " = 1"), //1
		welcomeRecord(null, null, "config/welcome/ISOCODE/(record)", " = 1"), //1
		alarm(Application.NativeApplication.ALARM_CLOCK, null, "config/alarm/ISOCODE/([0-9])", " > 0"); //1*

		private final Application.NativeApplication application;
		private final String index;
		private final String pattern;
		private final String result;
		private int count;

		private CONFIG_SERVICE(Application.NativeApplication application, String index, String inPattern, String inResult) {
			this.application = application;
			this.index = index;
			this.pattern = inPattern;
			this.count = 0;
			this.result = inResult;
		}

		public static CONFIG_SERVICE getByConfig(String inConfig, String isocode) {
			for (final CONFIG_SERVICE config : CONFIG_SERVICE.values()) {

				final Matcher theMatcher = config.getPattern(isocode).matcher(inConfig);
				if (theMatcher.matches()) {
					return config;
				}
			}
			return null;
		}

		public Application.NativeApplication getApplication() {
			return this.application;
		}

		public String getIndexByConfig(String config) {
			return config.substring(config.lastIndexOf("/") + 1, config.length()).toUpperCase();
		}

		public String getIndex() {
			return this.index;
		}

		public void add() {
			this.count++;
		}

		public Pattern getPattern(String isocode) {
			return Pattern.compile(this.pattern.replaceAll("ISOCODE", isocode));
		}

		public String getStats() {
			return this.name() + " => count : " + this.count + this.result;
		}
	}

	private void checkService() throws IOException {
		final String file = LibBasic.getStringFromBytes(FilesManagerFactory.FILE_MANAGER.getFilesContent(this.path + "service.csv"));

		final StringTokenizer line = new StringTokenizer(file, "\n");
		int nbrLine = 0;

		final String theIsocodePath = this.objectLang.getIsoCode();

		while (line.hasMoreTokens()) {
			nbrLine++;
			final StringTokenizer st_elem = new StringTokenizer(line.nextToken(), ";");
			if (st_elem.hasMoreTokens() && (st_elem.countTokens() == 2)) {

				final String mp3File = st_elem.nextToken().trim();
				final String configpath = st_elem.nextToken().trim(); // config/...
				final String destFilePath = configpath + MimeType.MIME_TYPES.A_MPEG.getFullExtension();

				final CONFIG_SERVICE theConfig_service = CONFIG_SERVICE.getByConfig(configpath, theIsocodePath);

				if (theConfig_service != null) {
					if (checkFile(mp3File)) {
						theConfig_service.add();
						if (this.isCreate) {
							System.out.println("INFO => Process : " + destFilePath);
							final Files theFiles = createFile(destFilePath, mp3File, theConfig_service.getApplication(), theConfig_service.getIndexByConfig(configpath), true);
							if (CONFIG_SERVICE.alarm.equals(theConfig_service)) {
								Factories.MUSIC.create(theFiles, this.objectLang, "Alarm" + theConfig_service.getIndexByConfig(configpath), null, MusicStyle.CATEGORIE_REVEIL, 0, Music.TYPE_MP3_USER_LIBRARY);
							}
						}
					}
				} else {
					System.out.println("ERROR => Invalid config path '" + configpath + "' at line : " + nbrLine);
				}
			} else {
				System.out.println("ERROR => Invalid service.csv at line : " + nbrLine);
			}
		}

		for (final CONFIG_SERVICE cs : CONFIG_SERVICE.values()) {
			System.out.println("INFO => " + cs.getStats());
		}
	}

	private Files createFile(String destPathFile, String mp3File, Application.NativeApplication inApplication, String inIndex, boolean isConfig) throws IOException {
		final byte[] content = FilesManagerFactory.FILE_MANAGER.getFilesContent(this.path + mp3File);
		final Files theFiles = FilesManagerFactory.FILE_MANAGER.post(FilesManager.TMP_MANAGER.new TmpFile(content), ContentType.MP3, ContentType.MP3_32, Files.CATEGORIES.BROAD, true, true, MimeType.MIME_TYPES.A_MPEG, destPathFile);
		if (isConfig) {
			try {
				if (theFiles != null) {
					new ConfigFilesImpl(this.objectLang, (inApplication != null) ? inApplication.getApplication() : null, theFiles, inIndex);
				} else {
					System.out.println("ERROR => file not create in " + destPathFile);
				}
			} catch (final SQLException e) {
				System.out.println("ERROR => " + e.getStackTrace());
			}
		}
		return theFiles;
	}

	private boolean checkFile(String mp3File) {
		final boolean result;
		if (FilesManagerFactory.FILE_MANAGER.fileExists(this.path + mp3File)) {
			result = true;
		} else {
			result = false;
			System.out.println("ERROR => file " + mp3File + " does not exist!!!!");
		}
		return result;
	}

	private enum CATEGORY {
		pratique(59), animaux(60), amour(61), reunion(62), bonjour(63), invitation(64), pardon(65), seul(66), amis(67), accord(68), enfant(69), temps(70), souhait(71), service(72), sybillin(73), merci(74), Felicitations(94);

		private final int id;

		private CATEGORY(int id) {
			this.id = id;
		}

		public static CATEGORY getByName(String inName) {
			for (final CATEGORY categ : CATEGORY.values()) {
				if (categ.toString().equals(inName)) {
					return categ;
				}
			}
			return null;
		}

		public int getId() {
			return this.id;
		}
	}

	private void checkLittleWord() throws IOException {

		final String file = LibBasic.getStringFromBytes(FilesManagerFactory.FILE_MANAGER.getFilesContent(this.path + "clin.csv"), "UTF-8");

		final StringTokenizer line = new StringTokenizer(file, "\n");
		final String theIsocodePath = this.objectLang.getIsoCode();
		int count = 0;
		int nbrLine = 0;
		while (line.hasMoreTokens()) {
			nbrLine++;
			String title = net.violet.common.StringShop.EMPTY_STRING;
			String style = net.violet.common.StringShop.EMPTY_STRING;
			String mp3 = net.violet.common.StringShop.EMPTY_STRING;

			final StringTokenizer st_elem = new StringTokenizer(line.nextToken(), ";");

			if (st_elem.hasMoreTokens() && (st_elem.countTokens() == 3)) {
				title = st_elem.nextToken().trim();
				mp3 = st_elem.nextToken().trim();
				style = st_elem.nextToken().trim();

				if (checkFile(mp3)) {
					final CATEGORY theCategory = CATEGORY.getByName(style);
					if (theCategory != null) {
						count++;
						if (this.isCreate) {
							System.out.println("INFO => " + title + " : " + mp3);
							final Files theFiles = createFile("config/clin/" + theIsocodePath + "/" + mp3, mp3, null, null, false);
							Factories.MUSIC.create(theFiles, this.objectLang, title, null, theCategory.getId(), 0, Music.TYPE_MP3_LITTLE_WORDS);
						}
					} else {
						System.out.println("ERROR => Bad category name : " + style);
					}
				}
			} else {
				System.out.println("ERROR => Invalid clin.csv at line : " + nbrLine);
			}
		}
		System.out.println("INFO => nbr little word :" + count);
	}

	/**
	 * Mettre les fichiers service.csv et clin.csv dans le meme repertoire que
	 * les sons
	 * 
	 * @param args
	 * @throws IOException 
	 */

	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			System.out.println("Syntaxe: Please specify args!! 1 => '/usr/local/violet/rsc/'path | 2 => isocode | 3 => [create]");
			return;
		}

		final Lang objectLang = Factories.LANG.findByIsoCode(args[1]);

		if (objectLang == null) {
			System.out.println("ERROR => isocode unknown : " + args[1]);
			return;
		}

		final boolean value = (args[2] != null) && args[2].equals("create");

		System.out.println("INFO => path : /usr/local/violet/rsc/" + args[0]);
		System.out.println("INFO => create : " + value);
		System.out.println("INFO => isocode : " + args[1]);

		final I18nTools theI18nTools = new I18nTools(args[0], value, objectLang);

		//test.mp3;config/weather/8
		theI18nTools.checkService();
		//title;test.mp3;category
		theI18nTools.checkLittleWord();
	}
}
