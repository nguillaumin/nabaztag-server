package net.violet.platform.dataobjects;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.violet.common.StringShop;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.content.converters.ContentType;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidDataException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.exceptions.TTSGenerationFailedException;
import net.violet.platform.api.exceptions.VocalMessageConversionFailedException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.Constantes;
import net.violet.platform.voice.TTSServices;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class FilesData extends APIData<Files> {

	private static final Logger LOGGER = Logger.getLogger(FilesData.class);
	public static final String VOCAL_RECORDER = "http://" + Constantes.RED5_STREAM_SERVER + net.violet.common.StringShop.SLASH + Files.CATEGORIES.VOCAL_RECORDER.getPath().replace(Files.CATEGORIES.BROADCAST.getCategory(), net.violet.common.StringShop.EMPTY_STRING);

	private static final String PROGRESSIVE_JPEG = "image/pjpeg";

	public static FilesData getData(Files inFiles) {
		try {
			return RecordData.getData(inFiles, FilesData.class, Files.class);
		} catch (final InstantiationException e) {
			FilesData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			FilesData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			FilesData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			FilesData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Constructeur à partir d'un files
	 */
	protected FilesData(Files inFiles) {
		super(inFiles);
	}

	public String getPath() {
		final Files theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getPath();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getUrl() {
		return "http://" + Constantes.BROAD + getPath().replace("broadcast", StringShop.EMPTY_STRING);
	}

	public Files getReference() {
		return getRecord();
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.FILES;
	}

	public long getId() {
		final Files theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}
		return 0;
	}

	public MimeType.MIME_TYPES getMimeType() {
		final Files theRecord = getRecord();
		return theRecord != null ? theRecord.getType() : null;
	}

	public static FilesData getFilesData(String inFileAPIID, String inAPIKey) throws NoSuchFileException {

		final Files file = Factories.FILES.find(APIData.fromObjectID(inFileAPIID, ObjectClass.FILES, inAPIKey));
		if (file != null) {
			return FilesData.getData(file);
		}

		throw new NoSuchFileException();
	}

	/**
	 * 
	 * @param inUserData : l'utilisateur
	 * @param inText : le texte a synthétisé
	 * @param inLibelle : le libellé du file
	 * @param inTTSvoice : la voix utilisé
	 * @param save : enregistre dans les tts perso
	 * @param chor : créer une choré
	 * @param adp : créer adp pour les v1
	 * @return FilesData
	 * @throws VocalMessageConversionFailedException
	 */
	public static FilesData postTTS(UserData inUserData, String inText, String inLibelle, TtsVoiceData inTTSvoice, boolean save, boolean chor, boolean adp) throws TTSGenerationFailedException, InternalErrorException {

		final FilesData theFiles = FilesData.getData(TTSServices.getDefaultInstance().postTTS(inText, chor, adp, inTTSvoice.getRecord()));

		if (!theFiles.isValid()) {
			throw new TTSGenerationFailedException();
		}

		if (save && (!MusicData.createMp3(theFiles, inLibelle, inUserData.getRecord(), MusicStyle.CATEGORIE_TTS_PERSO, 0).isValid())) {
			throw new InternalErrorException("Error music creation");
		}
		return theFiles;
	}

	/**
	 * Créer a partir d'un flv un mp3 et l'enregistre dans les mp3 perso si besoin
	 * 
	 * @param inUserData : l'utilisateur afin d'enregistrer dans les mp3 perso
	 * @param inLibelle : le libellé du file
	 * @param inUrl : unique url file
	 * @return FilesData
	 * @throws VocalMessageConversionFailedException
	 */
	public static FilesData postFLV(UserData inUserData, String inLibelle, String inUrl) throws VocalMessageConversionFailedException, InternalErrorException {
		final Files theFiles;

		try {
			theFiles = FilesManagerFactory.FILE_MANAGER.post(FilesManager.TMP_MANAGER.new TmpFile(new URL(inUrl).openStream()), ContentType.FLV, ContentType.MP3_32, Files.CATEGORIES.BROAD, true, true, MimeType.MIME_TYPES.A_MPEG);
		} catch (final Exception e) {
			FilesData.LOGGER.fatal(e, e);
			throw new VocalMessageConversionFailedException(APIErrorMessage.VOCAL_MESSAGE_CONVERSION_FAILED);
		}

		if (theFiles == null) {
			FilesData.LOGGER.info("postFLV theFiles is null");
			throw new VocalMessageConversionFailedException(APIErrorMessage.VOCAL_MESSAGE_CONVERSION_FAILED);
		}

		final FilesData theResult = FilesData.getData(theFiles);
		if ((inUserData != null) && inUserData.isValid() && !MusicData.createMp3(theResult, inLibelle, inUserData.getRecord(), 0).isValid()) {
			throw new InternalErrorException("postFLV createNewMusic failed");
		}
		return theResult;
	}

	/**
	 * Créer un item à partir d'un inputstream et son content-type
	 * 
	 * @return FilesData
	 * @throws InvalidDataException
	 */
	public static FilesData postLibraryItem(String inContentType, InputStream inputStream) throws InternalErrorException, InvalidDataException {

		final Files fileToPost;

		FilesData.LOGGER.info("inContentType = " + inContentType);
		final MimeType.MIME_TYPES mimeType = MimeType.MIME_TYPES.findByLabel(inContentType);

		if (mimeType == MimeType.MIME_TYPES.CHOR) {
			try {
				final TmpFile theTmpFile = FilesManager.TMP_MANAGER.new TmpFile(inputStream);
				fileToPost = FilesManagerFactory.FILE_MANAGER.post(theTmpFile, Files.CATEGORIES.BROAD, MimeType.MIME_TYPES.CHOR);
				if (fileToPost == null) {
					throw new InternalErrorException();
				}
			} catch (final IOException e) {
				FilesData.LOGGER.fatal(e, e);
				throw new InternalErrorException(e.getMessage());
			}
		} else if (mimeType == MimeType.MIME_TYPES.A_MPEG) {

			try {
				final TmpFile theTmpFile = FilesManager.TMP_MANAGER.new TmpFile(inputStream);
				fileToPost = FilesManagerFactory.FILE_MANAGER.post(theTmpFile, ContentType.MP3, ContentType.MP3_32, Files.CATEGORIES.BROAD, true, true, MimeType.MIME_TYPES.A_MPEG);

				if (fileToPost == null) {
					throw new InternalErrorException();
				}
			} catch (final IOException e) {
				FilesData.LOGGER.fatal(e, e);
				throw new InternalErrorException(e.getMessage());
			}
		} else if ((mimeType == MimeType.MIME_TYPES.JPEG) || ((mimeType == null) && inContentType.equals(FilesData.PROGRESSIVE_JPEG))) {

			try {
				final BufferedImage theOriginalImage = ImageIO.read(inputStream);
				final int x = theOriginalImage.getWidth();
				final int y = theOriginalImage.getHeight();
				final BufferedImage theResizedImage;
				if ((x < 400) || (y < 400)) {
					theResizedImage = theOriginalImage;
				} else {
					final double scaleValue = 400.0 / Math.max(x, y);
					theResizedImage = new BufferedImage((int) (x * scaleValue), (int) (y * scaleValue), theOriginalImage.getType());
					final AffineTransform tx = new AffineTransform();
					tx.scale(scaleValue, scaleValue);

					final AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
					op.filter(theOriginalImage, theResizedImage);
				}

				fileToPost = FilesManagerFactory.FILE_MANAGER.savePhotoFile(theOriginalImage, theResizedImage);

			} catch (final IOException e) {
				throw new InternalErrorException(e.getMessage());
			} finally {
				IOUtils.closeQuietly(inputStream);
			}

		} else {
			throw new InvalidDataException(APIErrorMessage.CONTENT_TYPE_NOT_SUPPORTED, inContentType);
		}

		return FilesData.getData(fileToPost);
	}

	/**
	 * Créer un item à partir d'un inputstream et son content-type
	 * 
	 * @return FilesData
	 * @throws InvalidDataException
	 */
	public static FilesData post(TmpFile inContent, Files.CATEGORIES inCategory, MimeType.MIME_TYPES inMimeType) throws InternalErrorException {

		final Files postedFile = FilesManagerFactory.FILE_MANAGER.post(inContent, inCategory, inMimeType);
		if (postedFile == null) {
			throw new InternalErrorException();
		}

		return FilesData.getData(postedFile);
	}

	public void scheduleDeletion() {
		if (isValid()) {
			getRecord().scheduleDeletion();
		}

	}

	public static FilesData find(long id) {
		final Files file = Factories.FILES.find(id);
		if (file != null) {
			return FilesData.getData(file);
		}

		return null;
	}

	public static FilesData create(MimeType.MIME_TYPES type, Files.CATEGORIES categorie) {
		return FilesData.getData(Factories.FILES.createFile(type, categorie));
	}
}
