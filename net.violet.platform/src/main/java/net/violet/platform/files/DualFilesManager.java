package net.violet.platform.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Files.FILE_DELETION_STATE;

/**
 * This FilesManager does actually nothing by itself. It is just a convenient way to simultaly use two different FilesManager. Each side-effects
 * operation (such as copy or delete) is performed with the two FilesManager (e.g. a delete operation will delete the file from the two FS).
 */
public class DualFilesManager extends AbstractFilesManager {

	private final RscManager rscManager = FilesManagerFactory.getRscManager();
	private final HttpManager httpManager = FilesManagerFactory.getHttpManager();

	// package visibility, for FilesManagerFactory usage only
	DualFilesManager() {
	}

	@Override
	protected byte[] doGetFileContent(String inPath) throws IOException {
		return getFilesContent(inPath, this.rscManager, this.httpManager);
	}

	@Override
	public boolean fileExists(String filePath) {
		return this.rscManager.fileExists(filePath) || this.httpManager.fileExists(filePath);
	}

	@Override
	public FILE_DELETION_STATE deleteFiles(Files inFiles2Delete) {
		final FILE_DELETION_STATE theState1 = super.deleteFiles(inFiles2Delete, this.rscManager);
		final FILE_DELETION_STATE theState2 = super.deleteFiles(inFiles2Delete, this.httpManager);

		if (FILE_DELETION_STATE.DELETED.equals(theState1)) {
			return theState2;
		}

		return theState1;
	}

	@Override
	public void writeInputStream2Files(Files inFiles, InputStream inFileContent, MimeType.MIME_TYPES inFileType) throws IOException {
		this.rscManager.writeInputStream2Files(inFiles, inFileContent, inFileType);
		// we insert the file in the other file system
		if (this.rscManager.copyFilesToFilesManager(inFiles, this.httpManager, inFileType)) {
			inFiles.setPath(getFilesPath(inFiles.getPath(), this.httpManager));
		}

	}

	@Override
	protected boolean deleteFile(String inFilesPath) {
		return this.rscManager.deleteFile(inFilesPath) & this.httpManager.deleteFile(inFilesPath);
	}

	@Override
	public String getFilesManagerIdentifier() {
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	@Override
	protected boolean copyTmpFileToFiles(String inFilesPath, File inTmpFile) {
		return this.httpManager.copyTmpFileToFiles(inFilesPath, inTmpFile) | this.rscManager.copyTmpFileToFiles(inFilesPath, inTmpFile);
	}

	/**
	 * Copy the InputStream content into the both FilesManagers using the path pointed by inDestFile {@link Files} (existing entry may be overwriten)
	 * NOTE : the given InputStream is not closed > charge is leaved to its creator to close it using IOUtils.closeQuietly(in);
	 * 
	 * @param inContent
	 * @param inDestFile provides the path
	 * @return the number of bytes copied
	 * @throws IOException
	 */
	@Override
	public int writeContentTo(InputStream inContent, Files inDestFile) throws IOException {

		final String path = inDestFile.getPath();

		// let the RscManager begin..
		final int written = this.rscManager.writeContentTo(inContent, inDestFile);

		// that's the file created by RscManager
		final File rscDestFile = this.rscManager.getFileFromFilesPath(path);

		// the local file content has been created, let's copy it in hadoop..
		if (!this.httpManager.copyFile(rscDestFile, path)) {
			// TODO delete the Rsc file and throw an Exception ??

		}

		return written;

		/*
		 * risky alternative : try to copy the input in both outputstream at the same time..
		 */
		// OutputStream out2 = theFileSystem.create(hadoopPath);
		//
		// byte[] buffer = new byte[1024];
		// int len;
		//
		// while ((len = in.read(buffer)) >= 0) {
		// out1.write(buffer, 0, len);
		// out2.write(buffer, 0, len);
		// }
	}

	/**
	 * Try first then second FileManager to have an InputStream
	 */
	@Override
	public InputStream getInputStreamFor(Files inFiles) {
		try {
			return this.rscManager.getInputStreamFor(inFiles);
		} catch (final IOException ignore) {}

		return null;
	}

}
