package net.violet.platform.admin.applications;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.applications.ApplicationApiLibMap;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.ApplicationApiLib;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;

/**
 * Deploy a new release of the JavaScript API lib in command line The source
 * file and release notes file must be present in a local directory for import
 * 
 * @author christophe - Violet
 */
public class PublishJavaScriptAPIRelease {

	private static final Files.CATEGORIES mCategory = Files.CATEGORIES.JS_APILIB;
	private static final String JS_API_FILE_PREFIX = "api-";

	// TODO : add gestion of the release notes file
	// private final static String[] mParams = new String[] {"language",
	// "version", "import_path", "release_notes", "source_file"};
	private static final String[] mParams = new String[] { "language", "version", "import_path", "source_file" };

	/**
	 * Entry point for the command line
	 * 
	 * @param args command line arguments
	 * @throws ConversionException
	 */
	public static void main(String[] args) throws ConversionException {

		final int len = PublishJavaScriptAPIRelease.mParams.length; // expected number of params

		if (args.length != len) {
			PublishJavaScriptAPIRelease.printHelp();
			return;
		}

		final PojoMap params = new PojoMap(len);

		for (int i = 0; i < len; i++) {
			final String paramName = PublishJavaScriptAPIRelease.mParams[i];
			final String paramValue = args[i];
			params.put(paramName, paramValue);
		}
		System.out.println("Publish JavaScript release : " + params);

		try {
			final Object apiReleaseMap = PublishJavaScriptAPIRelease.process(params);
			System.out.println("Publish process succeded :");
			System.out.println("==========================");
			System.out.println(ConverterFactory.XML.convertTo(apiReleaseMap));
			System.exit(0);

		} catch (final APIException e) {
			// write down the exception in xml format
			System.err.println(ConverterFactory.XML.convertTo(e));
			System.exit(1);
		}

	}

	/**
	 * @throws SQLException
	 * @throws IOException
	 */
	protected static Object process(PojoMap inParam) throws InvalidParameterException, InternalErrorException {

		final String languageCode = inParam.getString("language", true);
		final String version = inParam.getString("version", true);
		final String importPath = inParam.getString("import_path", true);
		// String releaseNotes = inParam.getString("release_notes", true);
		final String srcFileName = inParam.getString("source_file", true);

		final AppLanguages language = AppLanguages.findByLabel(languageCode);//
		if (language == null) {
			throw new InvalidParameterException("language", languageCode);
		}

		final String uploadPath = PublishJavaScriptAPIRelease.JS_API_FILE_PREFIX + version + ".js";

		Files newSourceFile;
		ApplicationApiLib apiRelease;

		final TmpFile theSrc;
		try {
			theSrc = FilesManager.TMP_MANAGER.new TmpFile(new FileInputStream(importPath + File.separator + srcFileName));
		} catch (final IOException e) {
			throw new InternalErrorException("Source file doesn't exist : " + importPath + File.separator + srcFileName);

		}

		// Allways create a new Files entry.
		newSourceFile = FilesManagerFactory.FILE_MANAGER.post(theSrc, PublishJavaScriptAPIRelease.mCategory, MimeType.MIME_TYPES.JS, uploadPath);

		apiRelease = Factories.APPLICATION_API_LIB.findByLanguageAndVersion(language, version);

		if (apiRelease == null) { // create entry
			apiRelease = Factories.APPLICATION_API_LIB.create(language, version, newSourceFile);

			if (apiRelease == null) {
				throw new InternalErrorException("COULD NOT CREATE API RELEASE");
			}

		} else { // update existing release
			// mark the current source file for deletion
			final Files oldSourceFile = apiRelease.getSourceFile();
			apiRelease.setSourceFile(newSourceFile, newSourceFile.getCreationDate());

			// Trick here : the old source entry points to the same path !
			// so removing it would delete the newly uploaded file !!!
			// we change the old entry's path to avoid that !!
			oldSourceFile.setPath(oldSourceFile.getPath() + ".dummy");
			oldSourceFile.delete();
		}

		return new ApplicationApiLibMap(apiRelease);
	}

	/**
	 * List the expected arguments for the command line
	 */
	private static void printHelp() {
		System.out.print("Usage : > java PublishJavaScriptAPIRelease ");
		for (int i = 0; i < PublishJavaScriptAPIRelease.mParams.length; i++) {
			System.out.print("%" + PublishJavaScriptAPIRelease.mParams[i] + " ");
		}
	}

}
