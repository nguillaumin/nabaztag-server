package net.violet.platform.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.util.Constantes;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

/**
 * A FilesManager implementation specially designed to add/delete files to/from
 * the Hadoop File System through an HTTP Request. Some methods are totally
 * useless with this FilesManager, one can't : - retrieve a file content - check
 * if a file exists - retrieve an InputStream on a file - creates directories
 * Uses paths : streaming: /streaming/ byte_code: /byte_code/ application:
 * /application/ apilib: /apilib/ broad: /broadcast/data/000-999/... config:
 * /broadcast/data/config/ admin: /broadcast/data/config/admin/ photo:
 * /broadcast/data/photo/ finished: /broadcast/data/finished/ podcast:
 * /broadcast/data/podcast/
 */
public class HttpManager extends AbstractFilesManager {

	private static final String URL_PREFIX = Constantes.HADOOP_URI + "/data/";

	private static final String HTTP_IDENTIFIER_PREFIX = "data/";

	private final HttpClient theClient = new HttpClient();

	// package visibility, for FilesManagerFactory usage only
	HttpManager() {
	}

	@Override
	protected boolean deleteFile(String inFilesPath) {
		try {
			final DeleteMethod theMethod = new DeleteMethod(HttpManager.URL_PREFIX + getFilesPath(inFilesPath));
			this.theClient.executeMethod(theMethod);
			theMethod.releaseConnection();
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	@Override
	protected byte[] doGetFileContent(String inFilesPath) {
		return null;
	}

	@Override
	public boolean fileExists(String inFilesPath) {
		return false;
	}

	@Override
	public String getFilesManagerIdentifier() {
		return HttpManager.HTTP_IDENTIFIER_PREFIX;
	}

	@Override
	public InputStream getInputStreamFor(Files inFiles) {
		return null;
	}

	public boolean copyFile(File inFile, String inPath) {
		try {
			final RequestEntity content = new InputStreamRequestEntity(new FileInputStream(inFile));
			postEntity(content, getFilesPath(inPath));
			return true;

		} catch (final Exception e) {
			return false;
		}
	}

	@Override
	protected boolean copyTmpFileToFiles(String inFilesPath, File inTmpFile) {
		return copyFile(inTmpFile, inFilesPath);
	}

	@Override
	protected void writeInputStream2Files(Files inFiles, InputStream inFileContent, MimeType.MIME_TYPES inFileType) throws IOException {
		String thePath = inFiles.getPath();
		if ((inFiles.getType() == MimeType.MIME_TYPES.A_MPEG) && (inFileType != MimeType.MIME_TYPES.A_MPEG)) {
			thePath += inFileType.getFullExtension();
		}

		final RequestEntity content = new InputStreamRequestEntity(inFileContent);
		postEntity(content, getFilesPath(thePath));
	}

	@Override
	public int writeContentTo(InputStream inContent, Files inDestFile) throws IOException {
		final RequestEntity content = new InputStreamRequestEntity(inContent);
		postEntity(content, getFilesPath(inDestFile.getPath()));
		return 0;
	}

	private void postEntity(RequestEntity entity, String path) throws HttpException, IOException {
		final PostMethod theMethod = new PostMethod(HttpManager.URL_PREFIX + path);
		theMethod.setRequestEntity(entity);
		this.theClient.executeMethod(theMethod);
		theMethod.releaseConnection();
	}

	public static void main(String[] args) throws HttpException, IOException {
		final HttpClient client = new HttpClient();
		final InputStream input = new FileInputStream("/home/vincent/text.txt");
		final RequestEntity content = new InputStreamRequestEntity(input);

		final PostMethod theMethod = new PostMethod("http://localhost:8080/hadoop/tsung/text.txt");
		theMethod.setRequestEntity(content);
		client.executeMethod(theMethod);
		theMethod.releaseConnection();
	}

}
