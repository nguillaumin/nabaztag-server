package net.violet.platform.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import net.violet.common.utils.io.StreamTools;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.util.Constantes;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.dfs.DistributedFileSystem;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

/**
 * A FilesManager implementation specially designed to add/remove files to/from the Hadoop File System. Used paths : streaming: /streaming/ byte_code:
 * /byte_code/ application: /application/ apilib: /apilib/ broad: /broadcast/data/000-999/... config: /broadcast/data/config/ admin:
 * /broadcast/data/config/admin/ photo: /broadcast/data/photo/ finished: /broadcast/data/finished/ podcast: /broadcast/broad/podcast/
 */
public class HadoopManager extends AbstractFilesManager {

	private static final String HADOOP_HDFS_PREFIX = net.violet.common.StringShop.SLASH;

	private static final String HADOOP_IDENTIFIER_PREFIX = "data/";

	private static final short REPLICATION = 2;

	private static final Logger LOGGER = Logger.getLogger(HadoopManager.class);

	private final FileSystem theFileSystem;

	// package visibility : for FilesManagerFactory usage only
	HadoopManager() throws IOException, URISyntaxException {
		this.theFileSystem = new DistributedFileSystem();
		this.theFileSystem.initialize(new URI(Constantes.HADOOP_URI), new Configuration());
	}

	/**
	 * @param inSrc
	 * @param inDestPath the path as described in {@link Files}
	 * @return
	 */
	public boolean copyFile(File inSrc, String inDestPath) {
		try {
			this.theFileSystem.copyFromLocalFile(new Path(inSrc.getAbsolutePath()), getHadoopPathFromFilesPath(inDestPath));
		} catch (final IOException e) {
			HadoopManager.LOGGER.fatal(e, e);
			return false;
		}
		return true;
	}

	@Override
	protected boolean copyTmpFileToFiles(String inFilesPath, File inTmpFile) {
		return copyFile(inTmpFile, inFilesPath);
	}

	@Override
	protected boolean deleteFile(String inFilesPath) {
		final Path thePath = getHadoopPathFromFilesPath(inFilesPath);

		try {
			return this.theFileSystem.delete(thePath, false);
		} catch (final IOException e) {
			HadoopManager.LOGGER.fatal(e, e);
		}

		return false;
	}

	@Override
	protected byte[] doGetFileContent(String filesPath) {
		InputStream theInput = null;
		try {
			final Path thePath = getHadoopPathFromFilesPath(filesPath);
			final FileStatus theStatus = this.theFileSystem.getFileStatus(thePath);
			final byte[] resultBuffer = new byte[(int) theStatus.getLen()];
			theInput = this.theFileSystem.open(thePath);
			StreamTools.readBytes(theInput, resultBuffer);
			return resultBuffer;
		} catch (final IOException e) {
			HadoopManager.LOGGER.fatal(e, e);
			return null;
		} finally {
			IOUtils.closeQuietly(theInput);
		}
	}

	@Override
	public boolean fileExists(String filePath) {
		try {
			return this.theFileSystem.exists(getHadoopPathFromFilesPath(filePath));
		} catch (final IOException e) {
			HadoopManager.LOGGER.fatal(e, e);
			return false;
		}

	}

	protected Path getHadoopPathFromFilesPath(String inFilesPath) {
		final String thePath = HadoopManager.HADOOP_HDFS_PREFIX + getFilesPath(inFilesPath);
		return new Path(thePath);
	}

	@Override
	public String getFilesManagerIdentifier() {
		return HadoopManager.HADOOP_IDENTIFIER_PREFIX;
	}

	@Override
	public void writeInputStream2Files(Files inFiles, InputStream inFileContent, MimeType.MIME_TYPES inFileType) throws IOException {
		String thePath = inFiles.getPath();

		if ((inFiles.getType() == MIME_TYPES.A_MPEG) && (inFileType != MIME_TYPES.A_MPEG)) {
			thePath += inFileType.getFullExtension();
		}

		OutputStream out = null;
		try {
			out = this.theFileSystem.create(getHadoopPathFromFilesPath(thePath), HadoopManager.REPLICATION);
			IOUtils.copy(inFileContent, out);

		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Copy the InputStream content into a new distributed file store using the path pointed by inDestFile {@link Files} (existing entry may be
	 * overwriten) NOTE : the given InputStream is not closed > charge is leaved to its creator to close it using IOUtils.closeQuietly(in);
	 * 
	 * @param inContent an InputStream
	 * @param inDestFile provide the path
	 * @return the number of bytes copied
	 * @throws IOException
	 */
	@Override
	public int writeContentTo(InputStream inContent, Files inDestFile) throws IOException {

		final Path hadoopPath = getHadoopPathFromFilesPath(inDestFile.getPath());
		OutputStream out = null;

		try {
			out = this.theFileSystem.create(hadoopPath, HadoopManager.REPLICATION);
			return IOUtils.copy(inContent, out);

		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.files.FilesManager#getInputStream(net.violet.platform .datamodel.Files)
	 */
	@Override
	public InputStream getInputStreamFor(Files inFiles) throws IOException {
		final Path hadoopPath = getHadoopPathFromFilesPath(inFiles.getPath());
		return this.theFileSystem.open(hadoopPath);

	}
}
