package net.violet.platform.datamodel;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.common.utils.io.TmpFileManager;
import net.violet.db.records.Record;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.common.FilesAccessor;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

/**
 * Contains files stored on the NAS.
 * 
 *
 */
public interface Files extends Record<Files> {

	public static String UNSUPPORTED_CONTENT = "net.violet.application.messages.unsupported_content";

	/**
	 * Different states returned when trying to remove a file
	 */
	public static enum FILE_DELETION_STATE {
		DELETED, ERROR, REFERRED_TO
	}

	static enum CATEGORIES {
		BROADCAST("broadcast/"), STREAMING("streaming/"), BYTE_CODE("byte_code/"), APPLICATION("applications/"), JS_APP(APPLICATION, "javascript/"), APILIB("apilib/"), JS_APILIB(APILIB, "javascript/"), BROAD(BROADCAST, "broad/") {

			@Override
			public String getPath(FilesManager inFileManager) {
				final FilesManager theFileManager;
				if (inFileManager == null) {
					theFileManager = FilesManagerFactory.FILE_MANAGER;
				} else {
					theFileManager = inFileManager;
				}

				return BROADCAST.getPath(theFileManager) + theFileManager.getFilesManagerIdentifier();
			}
		},
		MESSAGES(BROAD, "messages/"), PUBLIC_APPLICATION(BROAD, APPLICATION.getCategory()), VOCAL_RECORDER(BROAD, "vocalRecorder/"), CONFIG(BROAD, "config/"), ADMIN(CONFIG, "admin/"), PHOTO(BROAD, "photo/"), SIGNATURE(CONFIG, "signature/"), // les signatures sonores
		NATHAN(BROAD, "finished/"), APPLICATION_PICTURE(PUBLIC_APPLICATION, "pictures/"), // les icones des applications natives
		PODCAST(BROAD, "podcast/");

		private final String mCateg;
		private final CATEGORIES mParentCateg;

		private static final Map<String, CATEGORIES> CACHE = new HashMap<String, CATEGORIES>();
		static {
			CATEGORIES.CACHE.put(BYTE_CODE.getPath(), BYTE_CODE);
		}

		private CATEGORIES(String inCateg) {
			this(null, inCateg);
		}

		private CATEGORIES(CATEGORIES inParentCateg, String inCateg) {
			this.mCateg = inCateg;
			this.mParentCateg = inParentCateg;
		}

		public String getCategory() {
			return this.mCateg;
		}

		public String getPath() {
			return getPath(null);
		}

		public String getPath(FilesManager inFileManager) {
			final FilesManager theFileManager;
			if (inFileManager == null) {
				theFileManager = FilesManagerFactory.FILE_MANAGER;
			} else {
				theFileManager = inFileManager;
			}

			final StringBuilder thePath = new StringBuilder((this.mParentCateg != null) ? this.mParentCateg.getPath(theFileManager) : net.violet.common.StringShop.EMPTY_STRING);

			return thePath.append(getCategory()).toString();
		}

		public boolean isCategMatching(String inCateg) {
			return getCategory().equals(inCateg);
		}
	}

	/**
	 * ID du fichier initial (new content).
	 */
	long NEW_CONTENT_FILE_ID = 1;

	String getPath();

	void setMp3Path(String mp3);

	void setPath(String inPath);

	void setChorPath(String chor);

	String getPath2chor();

	String getPath2adp();

	String getPath2midi();

	Timestamp getBestBefore();

	/**
	 * Tells that this {@link Files} should be deleted within the next 24 hours
	 * (if orphan : cf. Purge daemon)
	 */
	void scheduleDeletion();

	/**
	 * Prevents the {@link Files} from being deleted
	 */
	void unScheduleDeletion();

	boolean isOrphan();

	MimeType.MIME_TYPES getType();

	class FilesCommon {

		/**
		 * 24 heures de répis du FilesImpl avant destruction finale
		 */
		protected static final int TIME_BEFORE_DELETION = 86400000;

		static final Logger LOGGER = Logger.getLogger(FilesCommon.class);

		private static final List<FilesAccessor> CHECK_TABLES = new LinkedList<FilesAccessor>();

		public boolean isFileOrphan(Files inFile) {

			if (FilesCommon.CHECK_TABLES.isEmpty()) {
				synchronized (FilesCommon.CHECK_TABLES) {
					if (FilesCommon.CHECK_TABLES.isEmpty()) {
						final Class<Factories> mainClass = Factories.class;
						for (final Field aField : mainClass.getFields()) {
							try {
								final Object aFactory = aField.get(null);
								if (Arrays.asList(aFactory.getClass().getInterfaces()[0].getInterfaces()).contains(FilesAccessor.class)) {
									FilesCommon.LOGGER.info("ADD in CHECK_TABLES : " + ((FilesAccessor) aFactory).toString());
									FilesCommon.CHECK_TABLES.add((FilesAccessor) aFactory);
								}
							} catch (final IllegalArgumentException e) {
								FilesCommon.LOGGER.fatal(e, e);
							} catch (final IllegalAccessException e) {
								FilesCommon.LOGGER.fatal(e, e);
							}
						}
					}
				}
			}

			for (final FilesAccessor theFilesAccessor : FilesCommon.CHECK_TABLES) {
				if (theFilesAccessor.usesFiles(inFile)) {
					return false;
				}
			}

			return true;

//			return !((0 < Factories.MESSAGE.countByFiles(inFile)) || (0 < Factories.FEED_ITEM.countByFiles(inFile)) || (0 < Factories.GROUP.countByFiles(inFile)) || (0 < Factories.SUBSCRIPTION_LOG.countByFiles(inFile)) || (0 < Factories.CONTENT.countByFiles(inFile)) || (0 < Factories.APPLICATION_CONTENT.countByFiles(inFile)) || (0 < Factories.CONFIG_FILES.countByFiles(inFile)) || (0 < Factories.MUSIC.countByFiles(inFile)) || (0 < Factories.NATHAN_MP3.countByFiles(inFile)) || (0 < Factories.APPLICATION_PACKAGE.countByFiles(inFile)) || (0 < Factories.OBJECT_PROFILE.countByFiles(inFile)) || (0 < Factories.ANNU.countByFiles(inFile)) || (0 < Factories.APPLICATION_PROFILE.countByFiles(inFile))
//					|| (0 < Factories.SUBSCRIPTION.countSettingsByKeyAndValue(VActionFullHandler.FILE, inFile.getId().toString())) || (0 < Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.countByFiles(inFile)) || (0 < Factories.HARDWARE.countByFiles(inFile)) || (0 < Factories.ZTAMP.countByFiles(inFile)));
		}

		public String getMd5Sum(Files inFiles) {
			return TmpFileManager.getMd5Sum(Constantes.RSC_PATH + inFiles.getPath());
		}

		public CCalendar generateBestBefore() {
			final CCalendar theCCalendar = new CCalendar(false);
			return theCCalendar.addMillis(Files.FilesCommon.TIME_BEFORE_DELETION);
		}

		public final String getPath(Files inFile) {
			return getPath2(inFile, MimeType.MIME_TYPES.A_MPEG, StringShop.MP3_EXT);
		}

		public final String getPath2chor(Files inFile) {
			return getPath2(inFile, MimeType.MIME_TYPES.CHOR, StringShop.CHOR_EXT);
		}

		public final String getPath2adp(Files inFile) {
			return getPath2(inFile, null, StringShop.ADP_EXT);
		}

		public final String getPath2midi(Files inFile) {
			return getPath2(inFile, MimeType.MIME_TYPES.A_MIDI, net.violet.common.StringShop.EMPTY_STRING);
		}

		private String getPath2(Files inFiles, MimeType.MIME_TYPES inTypeSought, String inExt) {
			final MimeType.MIME_TYPES theType = inFiles.getType();

			if (theType == inTypeSought) {
				return inFiles.getPath();
			}

			final String path2FileSought = inFiles.getPath() + inExt;

			if (((theType == MimeType.MIME_TYPES.A_MPEG) || (theType == MimeType.MIME_TYPES.A_MIDI) || (theType == MimeType.MIME_TYPES.A_MPEG)) && (FilesManagerFactory.FILE_MANAGER != null) && FilesManagerFactory.FILE_MANAGER.fileExists(path2FileSought)) {
				return path2FileSought;
			}

			if (((theType == MimeType.MIME_TYPES.A_MPEG) || (theType == MimeType.MIME_TYPES.A_MIDI) || (theType == MimeType.MIME_TYPES.A_MPEG)) && (FilesManagerFactory.ALTERNATE_FILE_MANAGER != null) && FilesManagerFactory.ALTERNATE_FILE_MANAGER.fileExists(path2FileSought)) {
				return path2FileSought;
			}

			return null;
		}

		public static Files createFiles(Files inFile, MimeType.MIME_TYPES inFileType, CATEGORIES inCategory, String inDestPath) {

			if ((inFile == null) || !net.violet.common.StringShop.EMPTY_STRING.equals(inFile.getPath())) {
				FilesCommon.LOGGER.fatal("!(inFile == null || !StringShop.EMPTY_STRING.equals(inFile.getPath()))");
				return null;
			}

			final String path2file;

			if (inDestPath == null) {
				path2file = FilesCommon.getPath(inFile.getId()) + inFileType.getFullExtension();
			} else {
				path2file = inDestPath;
			}

			final String thePath = inCategory.getPath() + path2file;

			if ((MIME_TYPES.A_MPEG != inFile.getType()) && (MIME_TYPES.CHOR == inFile.getType())) {
				inFile.setChorPath(thePath);
			} else {
				inFile.setPath(thePath);
			}

			return inFile;

		}

		/**
		 * Generates a path from the given inValue
		 * 
		 * @param inValue
		 * @return
		 */
		static String getPath(long inValue) {
			long i = inValue;
			i *= 123456789;
			i &= 0x7fffffff;

			String v = String.valueOf(i);
			final int j = v.length() % 3;
			if (j == 1) {
				v = "00" + v;
			} else if (j == 2) {
				v = "0" + v;
			}

			String path = net.violet.common.StringShop.EMPTY_STRING;

			for (int indexPath = 0; indexPath < v.length(); indexPath += 3) {
				if (indexPath != 0) {
					path += "/";
				}

				path += v.substring(indexPath, indexPath + 3);
			}
			return path + "/" + inValue;
		}

	}

	Timestamp getCreationDate();

	String getMd5Sum();
}
