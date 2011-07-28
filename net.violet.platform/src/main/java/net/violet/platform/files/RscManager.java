package net.violet.platform.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.violet.common.utils.io.StreamTools;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.util.Constantes;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * The FilesManager designed to write to/read from the NAS. Used paths : streaming: [/usr/local/violet/rsc/]streaming/ byte_code:
 * [/usr/local/violet/rsc/]byte_code/ application: [/usr/local/violet/rsc/]application/ apilib: [/usr/local/violet/rsc/]apilib/ broad:
 * [/usr/local/violet/rsc/]broadcast/broad/000-999/... config: [/usr/local/violet/rsc/]broadcast/broad/config/ admin:
 * [/usr/local/violet/rsc/]broadcast/broad/config/admin/ photo: [/usr/local/violet/rsc/]broadcast/broad/photo/ finished:
 * [/usr/local/violet/rsc/]broadcast/broad/finished/ podcast: [/usr/local/violet/rsc/]broadcast/broad/podcast/
 */
public class RscManager extends AbstractFilesManager {

	private static final Logger LOGGER = Logger.getLogger(RscManager.class);

	private static final String RSC_IDENTIFIER_PREFIX = "broad/";

	// package visibility : for FilesManagerFactory usage only
	RscManager() {
	}

	@Deprecated
	// TODO : protected after the crawler
	public boolean copyFilesToFilesManager(Files inFiles, HttpManager inFilesManager, MimeType.MIME_TYPES inFileType) {
		final File theFile = getFileFromFiles(inFiles, inFileType);
		return inFilesManager.copyFile(theFile, inFiles.getPath() + ((inFileType == null) ? net.violet.common.StringShop.EMPTY_STRING : inFileType.getFullExtension()));
	}

	@Deprecated
	public boolean copyFilesToFilesManager(Files inFiles, HadoopManager inFilesManager, MimeType.MIME_TYPES inFileType) {
		final File theFile = getFileFromFiles(inFiles, inFileType);
		return inFilesManager.copyFile(theFile, inFiles.getPath() + ((inFileType == null) ? net.violet.common.StringShop.EMPTY_STRING : inFileType.getFullExtension()));
	}

	@Override
	protected boolean copyTmpFileToFiles(String inFilesPath, File inTmpFile) {
		final File theFile = getFileFromFilesPath(inFilesPath);
		RscManager.prepareDirectory(theFile, false);
		return RscManager.exec(new StringBuilder("cp ").append(inTmpFile.getAbsolutePath()).append(net.violet.common.StringShop.SPACE).append(theFile.getAbsolutePath()).toString());
	}

	protected boolean deleteFile(File inFile) {
		if (inFile.exists() && !inFile.isDirectory()) {
			return inFile.delete();
		}
		return false;
	}

	@Override
	protected boolean deleteFile(String inFilesPath) {
		final File theFile = getFileFromFilesPath(inFilesPath);
		return deleteFile(theFile);
	}

	@Override
	protected byte[] doGetFileContent(String inPath) {
		final File theFile = getFileFromFilesPath(inPath);
		try {
			return FileUtils.readFileToByteArray(theFile);
		} catch (final IOException e) {
			RscManager.LOGGER.fatal(e, e);
		}
		return null;
	}

	@Override
	public boolean fileExists(String inFilePath) {
		final File theFile = getFileFromFilesPath(inFilePath);

		return (theFile != null) && theFile.exists();
	}

	private File getFileFromFiles(Files inFiles, MimeType.MIME_TYPES inFileType) {
		return getFileFromFilesPath(inFiles.getPath() + ((inFileType == null) ? net.violet.common.StringShop.EMPTY_STRING : inFileType.getFullExtension()));
	}

	protected File getFileFromFilesPath(String inFilesPath) {
		final String thePath = getFilesPath(inFilesPath);

		if (thePath != null) {
			return new File(Constantes.RSC_PATH + thePath);
		}

		return null;
	}

	@Override
	public String getFilesManagerIdentifier() {
		return RscManager.RSC_IDENTIFIER_PREFIX;
	}

	void removeFile(String inDirectoryPath) {
		final File theFile = getFileFromFilesPath(inDirectoryPath);
		if (theFile.exists()) {
			theFile.delete();
		}

	}

	/**
	 * Writes the give content to the given file
	 * 
	 * @param inFile
	 * @param inFileContent
	 * @param inOffSet
	 * @throws IOException
	 */
	void writeByteArray2File(File inFile, byte[] inFileContent, int inOffSet) throws IOException {
		OutputStream output = null;

		try {
			output = new FileOutputStream(inFile, false);
			StreamTools.writeTo(output, inFileContent, inOffSet);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

	@Override
	public void writeInputStream2Files(Files inFiles, InputStream inFileContent, MimeType.MIME_TYPES inFileType) throws IOException {

		final File theOutputFile;
		if (inFiles.getType() == inFileType) {
			theOutputFile = getFileFromFiles(inFiles, null);
		} else {
			theOutputFile = getFileFromFiles(inFiles, inFileType);
		}

		RscManager.prepareDirectory(theOutputFile, false);

		OutputStream theStream = null;
		try {
			theStream = new FileOutputStream(theOutputFile);
			IOUtils.copy(inFileContent, theStream);
		} finally {
			IOUtils.closeQuietly(theStream);
		}
	}

	File writeByteArray2TempFile(String inPrefix, String inExtension, byte[] inFileContent) {
		File theFile = null;
		try {
			theFile = File.createTempFile(inPrefix, inExtension, new File(Constantes.LOCAL_TMP_PATH));
			writeByteArray2File(theFile, inFileContent, 0);
		} catch (final IOException e) {
			RscManager.LOGGER.fatal(e, e);
			if (theFile != null) {
				theFile.delete();
			}
		}
		return theFile;
	}

	/**
	 * Copy the InputStream content into the local filesystem using the path pointed by inDestFile {@link Files} (existing entry may be overwriten)
	 * NOTE : the given InputStream is not closed > charge is leaved to its creator to close it using IOUtils.closeQuietly(in);
	 * 
	 * @param inContent
	 * @param inDestFile provide the path
	 * @return the number of bytes copied
	 * @throws IOException
	 */
	@Override
	public int writeContentTo(InputStream inContent, Files inDestFile) throws IOException {

		final File destFile = getFileFromFilesPath(inDestFile.getPath());
		OutputStream out = null;

		try {
			out = FileUtils.openOutputStream(destFile);
			return IOUtils.copy(inContent, out);

		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Returns an InputStream for the content of the {@link Files} entry
	 * 
	 * @param inFiles the {@link Files}
	 * @return a valid InputStream on the {@link Files} content, throw an exception otherwise
	 */
	@Override
	public InputStream getInputStreamFor(Files inFiles) throws IOException {
		final File file = getFileFromFilesPath(inFiles.getPath());
		return FileUtils.openInputStream(file);
	}

	/**
	 * Permet d'executer une commande
	 * 
	 * @param cmd commande
	 * @return <code>true</code> si la commande s'est bien passé sinon
	 *         <code>false</code>
	 */
	private static boolean exec(String cmd) {
		try {
			RscManager.LOGGER.debug("EXEC : " + cmd);
			final Process proc = Runtime.getRuntime().exec(cmd);
			proc.getOutputStream().close();
			proc.waitFor();
			proc.getInputStream().close();
			proc.getErrorStream().close();
			return true;
		} catch (final IOException e) {
			RscManager.LOGGER.fatal(e, e);
		} catch (final InterruptedException ie) {
			RscManager.LOGGER.fatal(ie, ie);
		}
		return false;
	}

	/**
	 * Creates the directories
	 * 
	 * @param path to the directory which needs to be created
	 * @return
	 */
	private static boolean prepareDirectory(File inFile, boolean isDirectory) {
		try {
			final File theFile;
			if (!isDirectory) {
				theFile = inFile.getParentFile();
			} else {
				theFile = inFile;
			}

			return theFile.mkdirs();
		} catch (final Throwable t) {
			RscManager.LOGGER.fatal(t, t);
			return false;
		}

	}

}
