package net.violet.platform.web.include_jsp.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.datamodel.factories.mock.FactoryMock;

public class ClockHC {

	public static String clockHC(int from, int lenmax, boolean adp, boolean chor, String inPath, String inContent) {
		return ClockHC.clockHC("http://www.nabaztag.com/myadmin_v2/download/clock_tmp/", from, lenmax, adp, chor, inPath, inContent, false);
	}

	public static String clockHC(String inUrl, int from, int lenmax, boolean adp, boolean chor, String inPath, String inContent, boolean insertConfigFiles) {
		/*
				String display = net.violet.common.StringShop.EMPTY_STRING;
				final String content = inContent.replace("tmp/", net.violet.common.StringShop.EMPTY_STRING);
				String path = inPath.substring(inPath.indexOf("clock_HC"));
				display += "content = " + content + "\n";
				System.out.println(inUrl + content);
				byte[] theFileDownloaded = StreamHandler.downloadFileByURL(inUrl + content);
				if ((theFileDownloaded != null) && (theFileDownloaded.length > 0)) {
					display += "File downloaded\n";
				} else {
					display += "download failed = " + inUrl + content + "\n";
				}
				if (content.contains("wav")) {
					path = path.replace("wav", "mp3");
					if (((theFileDownloaded = LibFile.toMp3(theFileDownloaded, MimeType.FILE.PCM)) == null) || (theFileDownloaded.length <= 0)) {
						display += "Error file cannot be turned to MP3\n";
					}

				}

				try {
					final Files theGeneratedFile = FilesManagerFactory.FILE_MANAGER.postMP3(theFileDownloaded, null, Files.CATEGORIES.CONFIG, from, lenmax, adp, chor, inPath);

					if ((theGeneratedFile != null) && insertConfigFiles) {
						final String[] tmpArgs = path.split("/");
						// Path format: config/clock_HC/{LANG}/{HOUR}/{FILE};
						if (tmpArgs.length >= 5) {
							final Lang theLang = ConvertTools.switchLangByShortIsocode(tmpArgs[2]);
							if (theLang != null) {
								// final int theHour = LibBasic.atoi(tmpArgs[3]);
								// Ajout d'un enregistrement dans la table config_files
								// try {
								// new ConfigFilesImpl(theLang,
								// ConfigFiles.SERVICES.CLOCK_HC, theGeneratedFile,
								// "CLOCK_HC/" + theHour);
								// } catch (SQLException e) {
								// LOGGER.fatal(e, e);
								// }
							} else {
								display += "Invalid language\n";
							}
						} else {
							display += "Error in path\n";
						}
					}
				} catch (final SQLException e) {
					ClockHC.LOGGER.fatal(e, e);
				}

				return display;
				*/

		return null;
	}

	private static final Pattern HC_FILE = Pattern.compile(".*/(.*)\\.mp3$");

	public static void main(String[] args) {
		System.setProperty("net.violet.platform.datamodel.factories.impl", FactoryMock.class.getName());
		try {
			final BufferedReader theReader = new BufferedReader(new FileReader("/home/slorg1/HCRescue"));
			String aLine = null;
			while ((aLine = theReader.readLine()) != null) {
				final Matcher theMatcher = ClockHC.HC_FILE.matcher(aLine);
				System.out.println("Processing " + aLine);
				if (theMatcher.matches()) {
					ClockHC.clockHC("file:///home/slorg1/filesFound/", 0, 8, true, true, aLine, theMatcher.group(1) + ".wav", false);
				}

			}
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}
}
