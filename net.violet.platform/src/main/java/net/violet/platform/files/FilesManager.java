package net.violet.platform.files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

import net.violet.common.utils.io.TmpFileManager;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.content.converters.ContentType;
import net.violet.content.manager.ConvertersManager;
import net.violet.platform.chor.DanceGenerator;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Files.CATEGORIES;
import net.violet.platform.datamodel.Files.FILE_DELETION_STATE;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.util.Constantes;

import org.apache.log4j.Logger;

/**
 * Nota : the methods of this class are all 'abstract' and will use automatically the default {@link FilesManager} defined in the
 * {@link FilesManagerFactory} unless specified otherwise Definitions :
 * <ul>
 * <li>Files' path : it is the path stored in the 'path' field of {@link Files}) Example : "broadcast/broad/...."</li>
 * <li>The path & CATEGORIES parameters : for methods using these parameters, the CATEGORIES.getPath()+path should be equivalent to aFile.getPath()'s
 * result</li>
 * </ul>
 * 
 * <pre>
 * Chemins des fichiers:
 * Sur le NAS (RscManager)
 * streaming:   /usr/local/violet/rsc/streaming/
 * byte_code:   /usr/local/violet/rsc/byte_code/
 * application: /usr/local/violet/rsc/application/
 * apilib:      /usr/local/violet/rsc/apilib/
 * broad:       /usr/local/violet/rsc/broadcast/broad/000-999/...
 * config:      /usr/local/violet/rsc/broadcast/broad/config/
 * admin:       /usr/local/violet/rsc/broadcast/broad/config/admin/
 * photo:       /usr/local/violet/rsc/broadcast/broad/photo/
 * finished:    /usr/local/violet/rsc/broadcast/broad/finished/
 * podcast:     /usr/local/violet/rsc/broadcast/broad/podcast/
 * Sur le Hadoop (HadoopManager)
 * streaming:   /streaming/
 * byte_code:   /byte_code/
 * application: /application/
 * apilib:      /apilib/
 * broad:       /broadcast/data/000-999/...
 * config:      /broadcast/data/config/
 * admin:       /broadcast/data/config/admin/
 * photo:       /broadcast/data/photo/
 * finished:    /broadcast/data/finished/
 * podcast:     /broadcast/data/podcast/
 * En écriture par HTTP (HttpManager) (POST et DELETE)
 * streaming:   http://broad.nabaztag.com/data/streaming/ (vérifier avec le booksplitter)
 * byte_code:   http://broad.nabaztag.com/data/byte_code/ (a priori inutilisé)
 * application: http://broad.nabaztag.com/data/application/
 * apilib:      http://broad.nabaztag.com/data/apilib/
 * broad:       http://broad.nabaztag.com/data/broadcast/data/000-999/...
 * config:      http://broad.nabaztag.com/data/broadcast/data/config/
 * admin:       http://broad.nabaztag.com/data/broadcast/data/config/admin/
 * photo:       http://broad.nabaztag.com/data/broadcast/data/photo/
 * finished:    http://broad.nabaztag.com/data/broadcast/data/finished/
 * podcast:     http://broad.nabaztag.com/data/broadcast/data/podcast/
 * URL (lapin, firefox)
 * streaming:   http://r.nabaztag.com/str/[md5]/
 * byte_code:   http://r.nabaztag.com/bc.jsp
 * application: via l'API
 * apilib:      via l'API
 * broad:       http://r.nabaztag.com/broad/000-999/...
 * config:      http://r.nabaztag.com/broad/config/
 * admin:       http://r.nabaztag.com/broad/config/admin/
 * photo:       http://r.nabaztag.com/broad/photo/
 * finished:    http://r.nabaztag.com/broad/finished/
 * podcast:     http://r.nabaztag.com/broad/podcast/
 * broad:       http://broad.nabaztag.com/data/000-999/...		une fois dans hadoop
 * config:      http://broad.nabaztag.com/data/config/			une fois dans hadoop
 * admin:       http://broad.nabaztag.com/data/config/admin/	une fois dans hadoop
 * photo:       http://broad.nabaztag.com/data/photo/			une fois dans hadoop
 * finished:    http://broad.nabaztag.com/data/finished/		une fois dans hadoop
 * podcast:     http://broad.nabaztag.com/data/podcast/			une fois dans hadoop
 * Base de données (files)
 * streaming:   pas dans la base
 * byte_code:   pas dans la base
 * application: application/
 * apilib:      apilib/
 * broad:       broadcast/broad/000-999/...
 * config:      broadcast/broad/config/
 * admin:       broadcast/broad/config/admin/
 * photo:       broadcast/broad/photo/
 * finished:    broadcast/broad/finished/
 * podcast:     broadcast/broad/podcast/
 * broad:       broadcast/data/000-999/...						une fois dans hadoop
 * config:      broadcast/data/config/							une fois dans hadoop
 * admin:       broadcast/data/config/admin/					une fois dans hadoop
 * photo:       broadcast/data/photo/							une fois dans hadoop
 * finished:    broadcast/data/finished/						une fois dans hadoop
 * podcast:     broadcast/data/podcast/							une fois dans hadoop
 * </pre>
 */
public abstract class FilesManager {

	public static TmpFileManager TMP_MANAGER = null;
	private static final Logger LOGGER = Logger.getLogger(FilesManager.class);

	static {
		try {
			FilesManager.TMP_MANAGER = new TmpFileManager(Constantes.LOCAL_TMP_PATH);
		} catch (final FileNotFoundException e) {
			FilesManager.LOGGER.fatal(e, e);
		}

		ConvertersManager.register(new DanceGenerator());
	}

	/**
	 * Copies a temporary {@link File} to the location pointed by the {@link Files}. Only the file with the extension {@link MimeType.MIME_TYPES}
	 * will be copied
	 * 
	 * @param inFiles the {@link Files}
	 * @param inTmpFile the temporary {@link File}
	 * @param inFileType the {@link MimeType.MIME_TYPES} determining the extension of the file
	 * @return <code>true</code> if it workes, <code>false</code> otherwise
	 */
	public abstract boolean copyTmpFileToFiles(Files inFiles, TmpFile inTmpFile, MimeType.MIME_TYPES inFileType);

	/**
	 * Copies a temporary {@link File} to the location pointed by the {@link Files}' path
	 * 
	 * @see FilesManager's documentation's for more explanations about {@link Files}' path
	 * @param inFilesPath the {@link Files}' path
	 * @param inTmpFile the temporary {@link File}
	 * @return <code>true</code> if it workes, <code>false</code> otherwise
	 */
	protected abstract boolean copyTmpFileToFiles(String inFilesPath, File inTmpFile);

	/**
	 * Deletes a {@link File} referred to by the given {@link Files}' path
	 * 
	 * @see FilesManager's documentation's for more explanations about {@link Files}' path
	 * @param inFilesPath the path to {@link File} (not absolute)
	 * @return <code>true</code> if everything went smoothly <code>false</code> otherwise
	 */
	protected abstract boolean deleteFile(String inFilesPath);

	/**
	 * Deletes the {@link File}(s) indexed by the given {@link Files} (in the case of an audio content, it will remove the CHOR and the ADP (if
	 * necessary)
	 * 
	 * @param inFiles2Delete the {@link Files} to remove
	 * @return the result of the operation :
	 *         <ul>
	 *         <li><code>FILE_DELETION_STATE.ERROR</code> : if the file could not be remove for some reasons</li>
	 *         <li><code>FILE_DELETION_STATE.DELETED</code> : if the file has been removed</li>
	 *         </ul>
	 */
	public abstract FILE_DELETION_STATE deleteFiles(Files inFiles2Delete);

	/**
	 * Deletes a temporary {@link File} from its name using only the name garanties that we are not deleting anything else but a previoulsy created
	 * temporary {@link File} Independant from the {@link FilesManager}
	 * 
	 * @param inFileName the temporary {@link File}'s name
	 * @return <code>true</code> if everything went smoothly <code>false</code> otherwise
	 */
	public abstract boolean deleteTmpFile(String inFileName);

	/**
	 * Returns the content of a {@link File} as a byte array from the given {@link Files}' path
	 * 
	 * @see FilesManager's documentation's for more explanations about {@link Files}' path
	 * @param inFilesPath the {@link Files}' path
	 * @return the content of the {@link File}, <code>null</code> otherwise
	 */
	protected abstract byte[] doGetFileContent(String inFilesPath) throws IOException;

	/**
	 * Tests the existence of the {@link File} pointed by the given {@link Files}' path
	 * 
	 * @see FilesManager's documentation's for more explanations about {@link Files}' path
	 * @param inFilesPath the {@link Files}' path
	 * @return <code>true</code> if it exists, <code>false</code> otherwise
	 */
	public abstract boolean fileExists(String inFilesPath);

	/**
	 * Tests the existence of the {@link File} pointed by the given {@link Files}' path belonging to the given {@link Files.CATEGORIES}
	 * 
	 * @see FilesManager's documentation's for more explanations about {@link Files}' path
	 * @see FilesManager's documentation's for more explanations about the parameters
	 * @param inFilesPath the {@link Files}' path stripped from its {@link Files.CATEGORIES} information
	 * @param inCateg the {@link Files.CATEGORIES}
	 * @return <code>true</code> if it exists, <code>false</code> otherwise
	 */
	public abstract boolean fileExists(String inFilesPath, Files.CATEGORIES inCateg);

	/**
	 * Returns the content of a {@link File} as a byte array from the given {@link Files}. Only the field 'path' will b used
	 * 
	 * @param inFiles the {@link Files}
	 * @return the content of the {@link File}, <code>null</code> otherwise
	 */
	public abstract byte[] getFilesContent(Files inFiles) throws IOException;

	/**
	 * Returns the content of a {@link File} as a byte array from the given {@link Files}' path
	 * 
	 * @see FilesManager's documentation's for more explanations about {@link Files}' path
	 * @param inFilesPath the {@link Files}' path
	 * @return the content of the {@link File}, <code>null</code> otherwise
	 */
	public abstract byte[] getFilesContent(String inFilesPath) throws IOException;

	/**
	 * Returns the text content of a {@link Files} entry Only the field 'path' will be used
	 * 
	 * @param inFiles the {@link Files}
	 * @return the content of the {@link File}, <code>null</code> otherwise
	 */
	public abstract String getTextContent(Files inFiles);

	/**
	 * Returns an InputStream for the content of the {@link Files} entry
	 * 
	 * @param inFiles the {@link Files}
	 * @return a valid InputStream on the {@link Files} content, throw an exception otherwise
	 */
	public abstract InputStream getInputStreamFor(Files inFiles) throws IOException;

	/**
	 * Returns a Reader for the content of the {@link Files} entry File content MUST be text to use this method !
	 * 
	 * @param inFiles the {@link Files}
	 * @return a valid InputStream on the {@link Files} content, throw an exception otherwise
	 */
	public abstract Reader getReaderFor(Files inFiles) throws IOException;

	/**
	 * Gets the specific part of the path for a {@link FilesManager} (used for {@link Files.CATEGORIES}
	 * 
	 * @return
	 */
	public abstract String getFilesManagerIdentifier();

	/**
	 * Returns a {@link Files}' path (with the given {@link FilesManager} 's specifications) based on the given {@link Files}' path (unknown specs).
	 * 
	 * @param inFilesPath the {@link Files}' path
	 * @param inFilesManager the {@link FilesManager}
	 * @return the context valid {@link Files}' path.
	 */
	@Deprecated
	// TODO: change to protected after Hadoop crawler's run
	public abstract String getFilesPath(String inFilesPath, FilesManager inFilesManager);

	/**
	 * Returns the content of the given mail template's name as a {@link String}
	 * 
	 * @param inMailTemplateName the mail template's name
	 * @return the content of the {@link File}, <code>null</code> otherwise
	 */
	public abstract String getMailTemplate(String inMailTemplateName);

	/**
	 * Returns the {@link File} associated with the given temporary file name
	 * 
	 * @param inFileName
	 * @return
	 * @throws IOException 
	 */
	public abstract TmpFile getTmpFile(String inFileName) throws IOException;

	public abstract Files post(byte[] content, ContentType input, ContentType output, Files.CATEGORIES inCategory, boolean adp, boolean chor, MimeType.MIME_TYPES inType);

	public abstract Files post(TmpFile content, ContentType input, ContentType output, CATEGORIES inCategory, boolean adp, boolean chor, MIME_TYPES inType);

	/**
	 * if filename is null, generates automatically path, otherwise create path with concatenating CATEGORIES and filename    
	 * @param content
	 * @param input
	 * @param output
	 * @param inCategory
	 * @param adp
	 * @param chor
	 * @param inType
	 * @param inFileName
	 * @return
	 */
	public abstract Files post(TmpFile content, ContentType input, ContentType output, Files.CATEGORIES inCategory, boolean adp, boolean chor, MimeType.MIME_TYPES inType, String inFileName);

	public abstract Files post(TmpFile content, CATEGORIES inCategory, MIME_TYPES inType);

	/**
	 * Saves an audio with the given name
	 * 
	 * @param inFileName
	 * @param inContent
	 * @return <code>true</code> if it worked, <code>false</code> otherwise
	 */
	@Deprecated
	public abstract boolean saveHCFile(String inFileName, byte[] inContent);

	/**
	 * Saves an Image for the given user
	 * @param inBigImage
	 * @param inSmallImage
	 * 
	 * @return Files pointing to the Big Image if it worked, <code>null</code> otherwise
	 */
	public abstract Files savePhotoFile(BufferedImage inBigImage, BufferedImage inSmallImage);

	/**
	 * Saves a byte array (which will be turned to PCM) from TELISMA to a {@link File} with the given name. (The WAV extension will be appended to the
	 * {@link File}'s name within the method.
	 * 
	 * @param inFileName
	 * @param inContent
	 * @return the {@link File} generated
	 */
	@Deprecated
	public abstract File saveTelismaFile(String inFileName, byte[] inContent);

	protected abstract void writeInputStream2Files(Files inFiles, InputStream inFileContent, MimeType.MIME_TYPES inFileType) throws IOException;

	/**
	 * Copy the InputStream content into the {@link Files} underlying store
	 * 
	 * @param inContent
	 * @param inDestFile
	 * @return the number of bytes copied
	 * @throws IOException
	 */
	public abstract int writeContentTo(InputStream inContent, Files inDestFile) throws IOException;

	/**
	 * Writes the content of the given byte array to a temporary {@link File} and return a reference to the {@link File}
	 * 
	 * @param inFile the bytes to write
	 * @return the {@link File} is everything went smoothly, <code>null</code> otherwise
	 */
	public abstract TmpFile writeTmpFile(byte[] inFile);

	public abstract LinkedHashMap<Files, Integer> joinAndSplit(Map<TmpFile, ContentType> inInputFiles, int[] inOffsets, Long inSize, CATEGORIES inCateg, ContentType output, MimeType.MIME_TYPES inType, String inPrefix, int inPreviewLength);

	public abstract Files post(TmpFile content, CATEGORIES inCategory, MIME_TYPES inType, String inFileName);

}
