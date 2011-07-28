package net.violet.platform.files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.violet.common.utils.io.StreamTools;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.TmpFileWrapper;
import net.violet.content.converters.ContentType;
import net.violet.content.manager.ConvertersManager;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Files.CATEGORIES;
import net.violet.platform.datamodel.Files.FILE_DELETION_STATE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.LibBasic;
import net.violet.platform.util.PictureExtension;

import org.apache.log4j.Logger;

public abstract class AbstractFilesManager extends FilesManager {

	public static final Pattern BROAD_MATCHER = Pattern.compile("^(broadcast/(data/|broad/)([\\w\\d]+[^/]/))(.*)");

	private static final Logger LOGGER = Logger.getLogger(AbstractFilesManager.class);

	@Override
	public boolean copyTmpFileToFiles(Files inFiles, TmpFile inTmpFile, MimeType.MIME_TYPES inFileType) {
		if (copyTmpFileToFiles(inFiles.getPath() + ((inFileType == null) ? net.violet.common.StringShop.EMPTY_STRING : inFileType.getFullExtension()), inTmpFile.get())) {
			return true;
		}

		return false;
	}

	/**
	 * Returns a Reader for the content of the {@link Files} entry File content MUST be text to use this method !
	 * 
	 * @param inFiles the {@link Files}
	 * @return a valid InputStream on the {@link Files} content, throw an exception otherwise
	 */
	@Override
	public Reader getReaderFor(Files inFiles) throws IOException {
		return new InputStreamReader(getInputStreamFor(inFiles));
	}

	@Override
	public FILE_DELETION_STATE deleteFiles(Files inFiles2Delete) {
		return deleteFiles(inFiles2Delete, FilesManagerFactory.FILE_MANAGER);
	}

	protected FILE_DELETION_STATE deleteFiles(Files inFiles2Delete, FilesManager inFilesManager) {
		try {
			final String[] theFiles2Remove;

			if (inFiles2Delete.getType().equals(MimeType.MIME_TYPES.A_MPEG)) {
				theFiles2Remove = new String[] { inFiles2Delete.getPath2adp(), inFiles2Delete.getPath2chor(), inFiles2Delete.getPath() };
			} else if (inFiles2Delete.getType().equals(MimeType.MIME_TYPES.JPEG)) {
				final PictureExtension thePictureExtension = new PictureExtension(inFiles2Delete.getPath());
				theFiles2Remove = new String[] { thePictureExtension.getOriginalFilename(), thePictureExtension.getSmallFilename() };
			} else {
				theFiles2Remove = new String[] { inFiles2Delete.getPath() };
			}

			for (final String aPath2File : theFiles2Remove) {
				if ((aPath2File != null) && !net.violet.common.StringShop.EMPTY_STRING.equals(aPath2File)) {
					if (!inFilesManager.deleteFile(aPath2File)) {
						AbstractFilesManager.LOGGER.warn(aPath2File + " could not be removed.");
					}
				}
			}

		} catch (final Exception e) {
			AbstractFilesManager.LOGGER.fatal(e, e);
			return FILE_DELETION_STATE.ERROR;
		}

		return FILE_DELETION_STATE.DELETED;
	}

	@Override
	public TmpFile writeTmpFile(byte[] inFile) {
		try {
			return FilesManager.TMP_MANAGER.new TmpFile(inFile);
		} catch (final IOException e) {
			AbstractFilesManager.LOGGER.fatal(e, e);
		}
		return null;
	}

	@Override
	public boolean deleteTmpFile(String inFileName) {
		return FilesManager.TMP_MANAGER.delete(inFileName);
	}

	@Override
	public boolean fileExists(String inFilesPath, CATEGORIES inCateg) {
		return fileExists(inCateg.getPath() + inFilesPath);
	}

	@Override
	public TmpFile getTmpFile(String inFileName) throws IOException {
		return FilesManager.TMP_MANAGER.new TmpFile(inFileName);
	}

	/**
	 * Returns a {@link Files} path (with this {@link FilesManager} 's specifications) based on the given {@link Files} path (unknown specs).
	 * 
	 * @param inFilesPath
	 * @return
	 */
	protected String getFilesPath(String inFilesPath) {
		return getFilesPath(inFilesPath, this);
	}

	@Override
	public String getFilesPath(String inFilesPath, FilesManager inFilesManager) {

		final Matcher theMatcher = AbstractFilesManager.BROAD_MATCHER.matcher(inFilesPath);

		if (theMatcher.matches()) { // this is either a podcast or a config file or a Nathan file or an admin file or a pure broad

			if (Files.CATEGORIES.PODCAST.isCategMatching(theMatcher.group(3))) { // it is a podcast
				return Files.CATEGORIES.PODCAST.getPath(inFilesManager) + theMatcher.group(4);
			}
			if (Files.CATEGORIES.CONFIG.isCategMatching(theMatcher.group(3))) { // it is a config file
				return Files.CATEGORIES.CONFIG.getPath(inFilesManager) + theMatcher.group(4);
			}
			if (Files.CATEGORIES.NATHAN.isCategMatching(theMatcher.group(3))) { // it is a nathan file
				return Files.CATEGORIES.NATHAN.getPath(inFilesManager) + theMatcher.group(4);
			}
			if (Files.CATEGORIES.ADMIN.isCategMatching(theMatcher.group(3))) { // it is an admin file
				return Files.CATEGORIES.ADMIN.getPath(inFilesManager) + theMatcher.group(4);
			}

			// it is pure broad
			return Files.CATEGORIES.BROAD.getPath(inFilesManager) + theMatcher.group(3) + theMatcher.group(4);
		}

		return inFilesPath;
	}

	@Override
	public String getTextContent(Files inFiles) {
		if (inFiles == null) {
			return net.violet.common.StringShop.EMPTY_STRING;
		}

		byte[] theFileContent;
		try {
			theFileContent = getFilesContent(inFiles);
		} catch (final IOException e) {
			AbstractFilesManager.LOGGER.fatal(e, e);
			theFileContent = null;
		}

		return LibBasic.getStringFromBytes(theFileContent, "UTF-8");
	}

	@Override
	public byte[] getFilesContent(Files inFiles) throws IOException {
		if (inFiles == null) {
			return new byte[0];
		}
		return getFilesContent(inFiles.getPath(), FilesManagerFactory.FILE_MANAGER, FilesManagerFactory.ALTERNATE_FILE_MANAGER);
	}

	@Override
	public byte[] getFilesContent(String inFilesPath) throws IOException {
		return getFilesContent(inFilesPath, FilesManagerFactory.FILE_MANAGER, FilesManagerFactory.ALTERNATE_FILE_MANAGER);
	}

	protected byte[] getFilesContent(String inFilesPath, FilesManager inPrimaryFilesManager, FilesManager inSecondaryFilesManager) throws IOException {
		if ((inPrimaryFilesManager != null) && inPrimaryFilesManager.fileExists(inFilesPath)) {
			return inPrimaryFilesManager.doGetFileContent(inFilesPath);
		}

		if ((inSecondaryFilesManager != null) && inSecondaryFilesManager.fileExists(inFilesPath)) {
			return inSecondaryFilesManager.doGetFileContent(inFilesPath);
		}

		return null;
	}

	@Override
	public String getMailTemplate(String inMailTemplateName) {
		final StringBuilder theBuilder = new StringBuilder(Constantes.MAIL_PATH).append(net.violet.common.StringShop.SLASH).append(inMailTemplateName);
		return LibBasic.getStringFromBytes(StreamTools.readBytes(theBuilder.toString()), "UTF-8");
	}

	@Override
	public Files post(TmpFile content, CATEGORIES inCategory, MimeType.MIME_TYPES inType) {
		return post(content, inCategory, inType, null);
	}

	@Override
	public Files post(TmpFile content, CATEGORIES inCategory, MimeType.MIME_TYPES inType, String inFileName) {
		if ((content == null) || !content.exists()) {
			return null;
		}

		final Files theFiles;

		if (inFileName == null) {
			theFiles = Factories.FILES.createFile(inType, inCategory);
		} else {
			theFiles = Factories.FILES.createFile(inCategory.getPath() + inFileName, inType);
		}

		if (theFiles == null) {
			return null;
		}

		FilesManagerFactory.FILE_MANAGER.copyTmpFileToFiles(theFiles, content, null);
		content.delete();
		return theFiles;

	}

	@Override
	public Files post(byte[] content, ContentType input, ContentType output, CATEGORIES inCategory, boolean adp, boolean chor, MimeType.MIME_TYPES inType) {
		try {
			return post(FilesManager.TMP_MANAGER.new TmpFile(content), input, output, inCategory, adp, chor, inType);
		} catch (final IOException e) {
			AbstractFilesManager.LOGGER.fatal(e, e);
		}
		return null;
	}

	@Override
	public Files post(TmpFile content, ContentType input, ContentType output, CATEGORIES inCategory, boolean adp, boolean chor, MimeType.MIME_TYPES inType) {
		return post(content, input, output, inCategory, adp, chor, inType, null);
	}

	@Override
	public Files post(TmpFile content, ContentType input, ContentType output, CATEGORIES inCategory, boolean adp, boolean chor, MimeType.MIME_TYPES inType, String inFileName) {

		if ((content == null) || !content.exists()) {
			return null;
		}

		final Files theFiles;
		if (inFileName == null) {
			theFiles = Factories.FILES.createFile(inType, inCategory);
		} else {
			theFiles = Factories.FILES.createFile(inCategory.getPath() + inFileName, inType);
		}

		if (theFiles == null) {
			return null;
		}
		theFiles.scheduleDeletion();

		final TmpFileWrapper theWrapper = new TmpFileWrapper();
		theWrapper.add(content);
		final TmpFile theSavedFile;
		try {
			theSavedFile = FilesManager.TMP_MANAGER.new TmpFile(content);
		} catch (final IOException e) {
			AbstractFilesManager.LOGGER.fatal(e, e);
			return null;
		}
		try {

			final TmpFile theResult = ConvertersManager.convert(input, output, theWrapper);
			TmpFile theChor = null;
			TmpFile theAdp = null;
			if (theResult == null) {
				return null;
			}

			FilesManagerFactory.FILE_MANAGER.copyTmpFileToFiles(theFiles, theResult, null);

			try {

				if ((output != ContentType.CHOR) && chor) {
					theWrapper.add(FilesManager.TMP_MANAGER.new TmpFile(theSavedFile));
					theChor = ConvertersManager.convert(input, ContentType.CHOR, theWrapper);
					if (theChor != null) {
						FilesManagerFactory.FILE_MANAGER.copyTmpFileToFiles(theFiles, theChor, MimeType.MIME_TYPES.CHOR);
					}
				}

				if ((output != ContentType.ADP) && adp) {
					theWrapper.add(FilesManager.TMP_MANAGER.new TmpFile(theSavedFile));
					theAdp = ConvertersManager.convert(input, ContentType.ADP, theWrapper);
					if (theAdp != null) {
						FilesManagerFactory.FILE_MANAGER.copyTmpFileToFiles(theFiles, theAdp, MimeType.MIME_TYPES.ADP);
					}
				}

				return theFiles;

			} catch (final IOException e) {
				AbstractFilesManager.LOGGER.fatal(e, e);
			} finally {
				if (theChor != null) {
					theChor.delete();
				}
				if (theAdp != null) {
					theAdp.delete();
				}
				theResult.delete();
			}

		} finally {
			theWrapper.clean();
			theSavedFile.delete();
		}

		return null;
	}

	@Override
	public LinkedHashMap<Files, Integer> joinAndSplit(Map<TmpFile, ContentType> inInputFiles, int[] inOffsets, Long inSize, CATEGORIES inCateg, ContentType output, MimeType.MIME_TYPES inType, String inPrefix, int inPreviewLength) {
		final TmpFile theFile;
		if (inInputFiles.size() == 1) {
			final Entry<TmpFile, ContentType> theEntry = inInputFiles.entrySet().iterator().next();
			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(theEntry.getKey());
			try {
				theFile = ConvertersManager.convert(theEntry.getValue(), output, theWrapper);
			} finally {
				theWrapper.clean();
			}
		} else if (inInputFiles.size() > 1) {
			theFile = ConvertersManager.join(output, inInputFiles);
		} else {
			theFile = null;
		}

		final long fileSize;
		if ((theFile == null) || !theFile.exists() || ((fileSize = theFile.length()) <= 0)) {
			return null;
		}

		final LinkedHashMap<Files, Integer> listFiles = new LinkedHashMap<Files, Integer>();
		boolean error = false;
		final double byteRate = (output.getBitRate()) * 1000 / 8D;
		final int totalLength = (int) (fileSize / byteRate);
		final TmpFileWrapper theWrapper = new TmpFileWrapper();

		for (final int anOffset : inOffsets) {
			// calcule des nouveaux offsets pour la version utilisateur
			final int theOffSet = (int) ((anOffset * theFile.length()) / inSize);
			theWrapper.add(theFile);
			final int from = (int) (theOffSet / byteRate);
			final int length = totalLength - from;
			AbstractFilesManager.LOGGER.info("full size " + theFile.length() + " | from " + from + " length " + length);
			final TmpFile theResult = ConvertersManager.split(theWrapper, output, output, from, length);

			final Files theFiles = post(theResult, inCateg, inType, inPrefix + theOffSet + inType.getFullExtension());
			if (theFiles == null) {
				error = true;
				break;
			}

			listFiles.put(theFiles, anOffset);
		}

		if (!error) {
			if (!listFiles.isEmpty() && (inPreviewLength > 0)) {
				theWrapper.add(theFile);
				final TmpFile theResult = ConvertersManager.split(theWrapper, output, output, 0, inPreviewLength);
				final Files theFiles = post(theResult, inCateg, inType, inPrefix + inPreviewLength + "s" + inType.getFullExtension());
				if (theFiles != null) {
					listFiles.put(theFiles, inPreviewLength);
					return listFiles;
				}
			} else {
				return listFiles;
			}
		}

		for (final Files aFiles : listFiles.keySet()) {
			aFiles.scheduleDeletion();
		}

		return null;
	}

	@Override
	public boolean saveHCFile(String inFileName, byte[] inContent) {
		try {
			final StringBuilder theBuilder = new StringBuilder(Constantes.COM_CLOCK_PATH).append(inFileName);
			final File theFile = new File(theBuilder.toString());
			FilesManagerFactory.getRscManager().writeByteArray2File(theFile, inContent, 0);
			return true;
		} catch (final IOException e) {
			AbstractFilesManager.LOGGER.fatal(e, e);
		}

		return false;
	}

	@Override
	public Files savePhotoFile(BufferedImage inBigImage, BufferedImage inSmallImage) {
		File theTempImage = null;
		try {
			theTempImage = FilesManager.TMP_MANAGER.new TmpFile().get();
			ImageIO.write(inBigImage, "jpg", theTempImage);

			final Files theFiles = writeInputStream2Files(new FileInputStream(theTempImage), MimeType.MIME_TYPES.JPEG, Files.CATEGORIES.PHOTO);
			ImageIO.write(inSmallImage, "jpg", theTempImage);

			final PictureExtension thePictureExtension = new PictureExtension(theFiles.getPath());

			copyTmpFileToFiles(getFilesPath(thePictureExtension.getSmallFilename()), theTempImage);

			return theFiles;
		} catch (final IOException e) {
			AbstractFilesManager.LOGGER.fatal(e, e);
		} catch (final SQLException e) {
			AbstractFilesManager.LOGGER.fatal(e, e);
		} finally {
			if (theTempImage != null) {
				deleteTmpFile(theTempImage.getName());
			}
		}

		return null;
	}

	@Override
	public File saveTelismaFile(String inToken, byte[] inContent) {
		final TmpFile theTmpFile;
		try {
			theTmpFile = FilesManager.TMP_MANAGER.new TmpFile(inContent);
		} catch (final IOException e1) {
			AbstractFilesManager.LOGGER.fatal(e1, e1);
			return null;
		}

		try {
			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(theTmpFile);
			final StringBuilder theBuilder = new StringBuilder(Constantes.TELISMA_SOUND_PATH).append(inToken);
			theBuilder.append(net.violet.common.StringShop.HYPHEN).append(CCalendar.getCurrentTimeInSecond()).append(net.violet.common.StringShop.WAV_EXT);
			final File theFile = new File(theBuilder.toString());
			final TmpFile theResult = ConvertersManager.convert(ContentType.WAV, ContentType.WAV_8, theWrapper);

			if (theResult != null) {
				try {
					FilesManagerFactory.getRscManager().writeByteArray2File(theFile, theResult.getContent(), 0);
				} finally {
					theResult.delete();
				}
			}
			return theFile;
		} catch (final IOException e) {
			AbstractFilesManager.LOGGER.fatal(e, e);
		} finally {
			theTmpFile.delete();
		}

		return null;
	}

	private Files writeInputStream2Files(InputStream inInputStream, MimeType.MIME_TYPES inFileType, Files.CATEGORIES inCateg) throws SQLException {
		final Files theFile = Factories.FILES.createFile(inFileType, inCateg);

		if (theFile == null) {
			throw new SQLException("Unable to create new Files entry for JPG files !");
		}

		try {
			writeInputStream2Files(theFile, inInputStream, inFileType);
			return theFile;
		} catch (final IOException e) {
			AbstractFilesManager.LOGGER.fatal(e, e);
			theFile.scheduleDeletion();
		}

		return null;
	}

}
