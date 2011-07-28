package net.violet.platform.admin.applications;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.MissingParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.applications.ApplicationPackageMap;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.ApplicationCredentialsImpl;
import net.violet.platform.datamodel.ApplicationPackage;
import net.violet.platform.datamodel.ApplicationProfile;
import net.violet.platform.datamodel.ApplicationProfileImpl;
import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationCategoryData;
import net.violet.platform.dataobjects.ApplicationContentData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationPackageData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.ApplicationData.ApplicationBuilder;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.StringTools;

import org.apache.commons.lang.StringUtils;
import org.csvtools.writers.CsvFileWriter;

/**
 * @author christophe - Violet
 */
public class PublishJavaScriptApplication {

	private static final Files.CATEGORIES FILE_CATEGORY = Files.CATEGORIES.JS_APP;

	// Expected params in the command line
	private static final String[] EXPECTED_PARAMS = new String[] { "sourceFile", "language", "import_path" };

	private static final String ENGLISH = "en";
	private static final String FRENCH = "fr";
	private static final List<Lang> LANG_2_TRANSLATE;
	private static final String[] LANG_TO_TRANSLATE = { PublishJavaScriptApplication.FRENCH, PublishJavaScriptApplication.ENGLISH, "de", "es", "it", "pt", "ja" };
	private static final String DICO_KEY = "Dico Key";

	static {
		final List<Lang> theLangs = new ArrayList<Lang>(PublishJavaScriptApplication.LANG_TO_TRANSLATE.length);
		for (final String aLang : PublishJavaScriptApplication.LANG_TO_TRANSLATE) {
			theLangs.add(Factories.LANG.findByIsoCode(aLang));
		}

		LANG_2_TRANSLATE = Collections.unmodifiableList(theLangs);
	}

	private static final String JS_APP_PREFIX = "net.violet.js."; // prefixe des noms des applications javascript

	private final CsvFileWriter csvDicoFile;
	private final String importPath;
	private final String appMainFileName;
	private String appName;
	private PojoMap config;
	private MimeType.MIME_TYPES configType;
	private UserData author;
	private AppLanguages language;
	private ApplicationCategory appCategory;
	private ApplicationClass appClass;
	private TmpFile tmpSourceFile;
	private TmpFile tmpConfFile;

	public PublishJavaScriptApplication(String inAppMainFileName, String importPath) throws IOException {
		this.appMainFileName = inAppMainFileName;
		this.importPath = importPath;
		this.csvDicoFile = new CsvFileWriter(this.importPath + File.separator + this.appMainFileName + "-dico.csv");

	}

	private final boolean validate(String languageCode) throws MissingParameterException, InternalErrorException, InvalidParameterException, IOException, ConversionException {
		final File srcFile = new File(this.importPath + File.separator + this.appMainFileName + MimeType.MIME_TYPES.JS.getFullExtension());

		if (!srcFile.exists() || srcFile.isDirectory()) {
			throw new InternalErrorException("Source file doesn't exist : " + srcFile.getAbsolutePath());
		}

		this.tmpSourceFile = FilesManager.TMP_MANAGER.new TmpFile(new FileInputStream(srcFile), this.appMainFileName + MimeType.MIME_TYPES.JS.getFullExtension());

		this.language = AppLanguages.findByLabel(languageCode);
		if (this.language == null) {
			throw new InvalidParameterException("language", languageCode);
		}

		// search the application source and config files

		// search a config file in xml or json format
		for (final MimeType.MIME_TYPES testExt : new MimeType.MIME_TYPES[] { MimeType.MIME_TYPES.XML, MimeType.MIME_TYPES.JSON }) {
			final String configFileName = this.appMainFileName + "-config" + testExt.getFullExtension();
			final File cnfgFile = new File(this.importPath, configFileName);
			if (cnfgFile.exists() && !cnfgFile.isDirectory()) {
				this.configType = testExt;
				this.tmpConfFile = FilesManager.TMP_MANAGER.new TmpFile(new FileInputStream(cnfgFile));
				this.config = new PojoMap(ConverterFactory.getConverter(testExt).<Map<String, Object>> convertFrom(new FileReader(cnfgFile)));
				break;
			}
		}

		if ((this.tmpConfFile == null)) {
			throw new FileNotFoundException("Config file in xml or json format doesn't exist : " + this.importPath);
		}

		this.appName = PublishJavaScriptApplication.JS_APP_PREFIX + this.appMainFileName.toLowerCase();
		if ((this.config == null) || !this.appName.equals(this.config.getString("app.name", true))) {
			throw new InvalidParameterException("Warning : the config file for application " + this.appName + " doesn't seem to match ! (check entry app.name in the configuration file)");
		}

		this.author = UserData.findByEmail(this.config.getString("app.author", true));

		if ((this.author == null) || !this.author.isValid()) {
			throw new InvalidParameterException(APIErrorMessage.NO_SUCH_PERSON, this.config.getString("app.author", true));
		}

		final String appClassName = this.config.getString("app.class", true);
		this.appClass = ApplicationClass.getByName(this.config.getString("app.class", true).toUpperCase());
		if (this.appClass == null) {
			throw new InvalidParameterException("app.class", appClassName);
		}

		this.appCategory = Factories.APPLICATION_CATEGORY.findByShortName(this.config.getString("app.category", true));

		if (this.appCategory == null) {
			throw new InvalidParameterException("app.category", this.config.getString("app.category", true));
		}

		return true;

	}

	/**
	 * @throws SQLException
	 * @throws IOException
	 */
	protected Object process() throws InternalErrorException {

		try {

			final boolean isInteractive = this.config.getBoolean("app.interactive");
			final boolean isVisible = this.config.getBoolean("app.visible");
			final boolean isRemovable = this.config.getBoolean("app.removable", true);

			Application app = Factories.APPLICATION.findByName(this.appName);

			if (app == null) { // create new entry into application
				final ApplicationBuilder builder = new ApplicationData.ApplicationBuilder(this.author, this.appName, this.appClass, ApplicationCategoryData.getData(this.appCategory));
				if (isInteractive) {
					builder.setInteractive();
				}
				if (!isVisible) {
					builder.setInvisible();
				}
				if (!isRemovable) {
					builder.setUnRemovable();
				}
				app = builder.build(null).getReference();

			} else { // update
				app.update(this.appName, this.appClass, this.appCategory, isInteractive, isVisible, isRemovable);
			}

			final String appPublicKey = this.config.getString("app.credentials.public_key", true);
			final String appPrivateKey = this.config.getString("app.credentials.private_key", true);

			ApplicationCredentials cred = Factories.APPLICATION_CREDENTIALS.findByApplication(app);

			if (cred == null) {
				cred = new ApplicationCredentialsImpl(appPublicKey, appPrivateKey, app);

			} else { // update ?
				if (cred.getPublicKey().equals(appPublicKey)) {
					if (!cred.getPrivateKey().equals(appPrivateKey)) { // mise à jour de la clé privé
						cred.setPrivateKey(appPrivateKey);
					}

				} else { // changement de public key ???
					cred.delete();
					cred = new ApplicationCredentialsImpl(appPublicKey, appPrivateKey, app);
				}

			}

			ApplicationData.getData(app).addSupportedTypes(Collections.singletonList(ObjectType.RFID));

			this.csvDicoFile.writeFields(PublishJavaScriptApplication.DICO_KEY);
			this.csvDicoFile.writeFields(PublishJavaScriptApplication.LANG_TO_TRANSLATE);

			final String titleKey = DicoTools.LOC_PREFIX + net.violet.common.StringShop.SLASH + this.appName + "/title";
			final Map<String, String> titlesMap = this.config.getMap("app.title");

			PublishJavaScriptApplication.addDicoEntries(this.csvDicoFile, this.appName, titleKey, titlesMap);

			// title translations gives us which langs are supported by the application
			final List<Lang> supportedLangs = new ArrayList<Lang>(titlesMap.size());
			for (final String langKey : titlesMap.keySet()) {
				supportedLangs.add(Factories.LANG.findByIsoCode(langKey));
			}
			app.updateLangs(supportedLangs);
			final int appRank = this.config.getInt("app.rank", 0);
			app.updateRank(appRank);

			/*
			 * Read the translations of application description and add them to
			 * the CSV file
			 */
			final String descrKey = DicoTools.LOC_PREFIX + net.violet.common.StringShop.SLASH + this.appName + "/description";

			PublishJavaScriptApplication.addDicoEntries(this.csvDicoFile, this.appName, descrKey, this.config.<String> getMap("app.description"));

			final String instrKey = DicoTools.LOC_PREFIX + net.violet.common.StringShop.SLASH + this.appName + "/instructions";

			PublishJavaScriptApplication.addDicoEntries(this.csvDicoFile, this.appName, instrKey, this.config.<String> getMap("app.instructions"));

			/*
			 * Create or update the application profile
			 */
			final boolean isOpenSource = this.config.getBoolean("app.opensource", false); // default value : false

			final ApplicationProfile appProfile;

			if (app.getProfile() == null) {
				// insert new profile with a NULL scheduling file
				// File schdFile = new File(importDir, ZTAMP_SCHEDULING_FILE);
				// Files schedulingFile = FilesManagerFactory.FILE_MANAGER.postTextFile(schdFile, mCategory, ZTAMP_SCHEDULING_FILE, Files.MIME_TYPES.XML);

				final Files configFile = FilesManagerFactory.FILE_MANAGER.post(this.tmpConfFile, PublishJavaScriptApplication.FILE_CATEGORY, this.configType, this.tmpConfFile.getName());

				appProfile = new ApplicationProfileImpl(app, titleKey, descrKey, isOpenSource, configFile, null, null, null, null, instrKey, null);

			} else {
				// remember the current config file
				appProfile = app.getProfile();

				final Files oldConfigFile = appProfile.getConfigurationSettingFile();
				if (!this.tmpConfFile.getMD5Sum().equals(oldConfigFile.getMd5Sum())) {
					// Allways create new Files entry for the source and
					// configuration file.
					final Files configFile = FilesManagerFactory.FILE_MANAGER.post(this.tmpConfFile, PublishJavaScriptApplication.FILE_CATEGORY, this.configType, this.tmpConfFile.getName());
					appProfile.update(titleKey, descrKey, instrKey, isOpenSource, configFile, null, null, null, null);

					// Trick here : the old file points to the same path !
					// so removing it would delete the newly uploaded file !!!
					// >> we change the old file's path to avoid that !!
					oldConfigFile.setPath(oldConfigFile.getPath() + ".dummy");
					oldConfigFile.scheduleDeletion();

				}
			}

			/*
			 * Create or update application package
			 */
			final String apiVersion = this.config.getString("app.apiVersion", true);
			final ApplicationPackage appPackage;

			if (app.getPackage() == null) { // create entry
				final Files sourceFileRecord = FilesManagerFactory.FILE_MANAGER.post(this.tmpSourceFile, PublishJavaScriptApplication.FILE_CATEGORY, MimeType.MIME_TYPES.JS, this.tmpSourceFile.getName());
				appPackage = Factories.APPLICATION_PACKAGE.create(app, this.language, apiVersion, sourceFileRecord);

			} else { // update existing release
				appPackage = app.getPackage();
				final Files oldSourceFile = appPackage.getSourceFile();
				Files sourceFile = oldSourceFile;

				if (!this.tmpSourceFile.getMD5Sum().equals(oldSourceFile.getMd5Sum())) {
					sourceFile = FilesManagerFactory.FILE_MANAGER.post(this.tmpSourceFile, PublishJavaScriptApplication.FILE_CATEGORY, MimeType.MIME_TYPES.JS, this.tmpSourceFile.getName());

					// Trick here : the old source file has the same path !
					// so removing it would delete the newly uploaded file !!!
					// we change the old entry's path to avoid that !!
					oldSourceFile.setPath(oldSourceFile.getPath() + ".dummy");
					oldSourceFile.scheduleDeletion();
				}
				appPackage.updateSourceFile(sourceFile, apiVersion); // newSourceFile
			}

			/*
			 *  Update or create the images and signature file
			 */
			Files imageFiles = appProfile.getPictureFile();

			try {
				final TmpFile imageFile = FilesManager.TMP_MANAGER.new TmpFile(new FileInputStream(this.importPath + File.separator + this.appMainFileName + "-img.gif"), this.appMainFileName + "-img.gif");

				if ((imageFile.exists()) && ((imageFiles == null) || !imageFile.getMD5Sum().equals(imageFiles.getMd5Sum()))) {
					imageFiles = FilesManagerFactory.FILE_MANAGER.post(imageFile, Files.CATEGORIES.APPLICATION_PICTURE, MimeType.MIME_TYPES.GIF, imageFile.getName());
				}
			} catch (final IOException e) {}

			Files iconFile = appProfile.getIconFile();
			try {
				final TmpFile icoFile = FilesManager.TMP_MANAGER.new TmpFile(new FileInputStream(this.importPath + File.separator + this.appMainFileName + "-ico.gif"), this.appMainFileName + "-ico.gif");

				if (icoFile.exists() && ((iconFile == null) || !icoFile.getMD5Sum().equals(iconFile.getMd5Sum()))) {
					iconFile = FilesManagerFactory.FILE_MANAGER.post(icoFile, Files.CATEGORIES.APPLICATION_PICTURE, MimeType.MIME_TYPES.GIF, icoFile.getName());
				}
			} catch (final IOException e) {}

			Files mp3SignatureFile = appProfile.getAnnounceFile();
			try {
				final TmpFile mp3SigFile = FilesManager.TMP_MANAGER.new TmpFile(new FileInputStream(this.importPath + File.separator + this.appMainFileName + ".mp3"), this.appMainFileName + ".mp3");

				if ((mp3SigFile.exists()) && ((mp3SignatureFile == null) || !mp3SigFile.getMD5Sum().equals(mp3SignatureFile.getMd5Sum()))) {
					mp3SignatureFile = FilesManagerFactory.FILE_MANAGER.post(mp3SigFile, Files.CATEGORIES.SIGNATURE, MimeType.MIME_TYPES.A_MPEG, this.appMainFileName + ".mp3");
				}
			} catch (final IOException e) {}

			appProfile.update(imageFiles, iconFile, mp3SignatureFile);

			// Check if we have other image files to add as application content
			int idxPictures = 0;
			boolean quit;
			try {
				do {
					final TmpFile theFile = FilesManager.TMP_MANAGER.new TmpFile(new FileInputStream(this.importPath + File.separator + this.appMainFileName + "-img" + (idxPictures++) + ".gif"));

					if (!(quit = !theFile.exists())) {
						final FilesData theFileData = FilesData.getData(FilesManagerFactory.FILE_MANAGER.post(theFile, Files.CATEGORIES.APPLICATION_PICTURE, MimeType.MIME_TYPES.GIF, theFile.getName()));
						ApplicationContentData.create(ApplicationData.getData(app), theFileData);
					}

				} while (!quit);
			} catch (final IOException e) {}

			//Create dico entries for the settings
			final List<Object> appSettings = this.config.getList("settings");

			for (final Object settingItem : appSettings) {
				final PojoMap widgetMap = new PojoMap((Map<String, Object>) settingItem);
				PublishJavaScriptApplication.addWidgetDicoEntries(this.csvDicoFile, this.appName, null, widgetMap);
			}

			return new ApplicationPackageMap(ApplicationData.getData(app), new ApplicationPackageData(appPackage));
		} catch (final Exception unexpected) {
			throw new InternalErrorException(unexpected.getMessage());

		}

	}

	private void closeCsv() {
		this.csvDicoFile.close();
	}

	private void clean() {
		this.tmpConfFile.delete();
		this.tmpSourceFile.delete();
	}

	/**
	 * @param dico
	 * @param appName
	 * @param parentKey
	 * @param widgetMap
	 * @throws InvalidParameterException
	 */
	private static void addWidgetDicoEntries(CsvFileWriter dico, String appName, String parentKey, PojoMap widgetMap) throws InvalidParameterException {

		// each entry in the settings is a widget config
		final String widgetType = widgetMap.getString("type", net.violet.common.StringShop.EMPTY_STRING); // BUG FIX : PopupItems have no type.. :( 
		final String widgetKey = (parentKey == null) ? widgetMap.getString("key", net.violet.common.StringShop.EMPTY_STRING) : parentKey + widgetMap.getString("key", net.violet.common.StringShop.EMPTY_STRING);

		// Read the translations of setting label and description and add them to the CSV file
		try {
			// the label map may contain entries for different translations of the label
			final Map<String, String> labelsMap = widgetMap.getMap("label");
			final String settingLabelKey = DicoTools.LOC_PREFIX + net.violet.common.StringShop.SLASH + appName + "/settings/" + widgetKey;

			PublishJavaScriptApplication.addDicoEntries(dico, appName, settingLabelKey, labelsMap);
		} catch (final InvalidParameterException omg) {
			// ignore : label may be a single string
		}

		// the description map contains entry for different translations of the
		final String settingDescriptionKey = DicoTools.LOC_PREFIX + net.violet.common.StringShop.SLASH + appName + "/settings/" + widgetKey + "/description";
		final Map<String, String> descriptionsMap = widgetMap.getMap("description");

		PublishJavaScriptApplication.addDicoEntries(dico, appName, settingDescriptionKey, descriptionsMap);

		/*
		 * Some widgets like Group and RadioGroup have subitems..
		 */
		if (widgetMap.containsKey("items")) {
			final List<Object> subitems = widgetMap.getList("items", Collections.emptyList());
			int i = 0; // some sub items don't have keys : we index them

			for (final Object subItem : subitems) {
				final String newParentKey = (widgetType.equals("RadioGroup") || widgetType.equals("Popup")) ? (widgetKey + "[" + i++ + "]") : (widgetKey + ".");

				PublishJavaScriptApplication.addWidgetDicoEntries(dico, appName, newParentKey, new PojoMap((Map<String, Object>) subItem));
			}
		}
	}

	/**
	 * Add a line in the CSV Dico file corresponding to the found translations
	 * in the givent translationMap
	 * 
	 * @param dicoKey
	 * @param translationsMap A map where the keys are language keys and the
	 *            values translations to insert into the dico file
	 * @param dico
	 * @see LANG_TO_TRANSLATE
	 */
	private static void addDicoEntries(CsvFileWriter dico, String inAppName, String dicoKey, Map<String, String> translationsMap) {

		if (translationsMap != null) {
			final List<String> csvLine = new ArrayList<String>(PublishJavaScriptApplication.LANG_2_TRANSLATE.size());
			csvLine.add(dicoKey);

			for (final Lang lang : PublishJavaScriptApplication.LANG_2_TRANSLATE) {
				String translation = translationsMap.get(lang.getIETFCode());

				// When we have no translation, take the english or the french text!
				if (StringUtils.isBlank(translation)) {
					translation = translationsMap.get(PublishJavaScriptApplication.ENGLISH);
				}
				if (StringUtils.isBlank(translation)) {
					translation = translationsMap.get(PublishJavaScriptApplication.FRENCH);
				}

				// Clean the translated String or else YAML will have some troubles
				translation = StringTools.cleanControlChars(translation, true, false);

				csvLine.add(translation);

				if (translation != null) {
					System.out.print("Adding dico key : " + dicoKey + " in lang " + lang.getIETFCode());
					Dico dicoEntry = Factories.DICO.findByKeyAndLang(dicoKey, lang);

					if (dicoEntry == null) {
						dicoEntry = Factories.DICO.create(dicoKey, lang, translation, inAppName);
					} else {
						dicoEntry.setText(translation);
					}
				}
			}

			try {
				dico.writeFields(csvLine);
			} catch (final IOException e) {
				System.err.print("Unable to write dico entry in CSV file : " + csvLine);
			}

		}
	}

	/**
	 * List the expected arguments for the command line
	 */
	private static void printHelp() {
		System.out.print("Usage : > java PublishJavaScriptApplication ");
		for (int i = 0; i < PublishJavaScriptApplication.EXPECTED_PARAMS.length; i++) {
			System.out.print("%" + PublishJavaScriptApplication.EXPECTED_PARAMS[i] + " ");
		}
	}

	/**
	 * Entry point for the command line
	 * 
	 * @param args command line arguments
	 * @throws ConversionException
	 */
	public static void main(String[] args) throws ConversionException {

		if (args.length != PublishJavaScriptApplication.EXPECTED_PARAMS.length) {
			PublishJavaScriptApplication.printHelp();
			return;
		}
		System.out.println("Publish JavaScript Application release : " + Arrays.toString(args));

		final String appMainFileName = args[0];
		final String languageCode = args[1];
		final String importPath = args[2];

		if ((appMainFileName == null) || (importPath == null) || (languageCode == null)) {
			System.err.println(ConverterFactory.XML.convertTo("sourceFile = " + appMainFileName + " import_path = " + importPath + " language = " + languageCode));
			System.exit(1);
		}

		final File importDir = new File(importPath);
		if (!importDir.exists() || !importDir.isDirectory()) {
			System.err.println(ConverterFactory.XML.convertTo("Import directory doesn't exist : " + importPath));
			System.exit(1);
		}

		PublishJavaScriptApplication thePublisher = null;

		try {
			thePublisher = new PublishJavaScriptApplication(appMainFileName, importPath);
			if (thePublisher.validate(languageCode)) {
				final Object apiReleaseMap = thePublisher.process();
				System.out.println("Publish process succeded :");
				System.out.println(ConverterFactory.XML.convertTo(apiReleaseMap));
				System.exit(0);
			}
		} catch (final APIException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (thePublisher != null) {
				thePublisher.closeCsv();
				thePublisher.clean();
			}
		}

		System.exit(1);
	}

}
