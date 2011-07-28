package net.violet.platform.httpclient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.violet.common.Constantes;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.platform.files.FilesManager;
import net.violet.probes.ProbesHandler;

import org.apache.commons.io.IOUtils;

/**
 * A DownloadTask is a process which is in charge of downloading a resource through a given url, to store it as a file and to return it.
 */
public class DownloadTask {

	private static final int MAX_FILE_SIZE = 100000000;
	private static final int MAX_TRIALS = 3;

	private final URL url;
	private final int maxTrials;
	private final int maxFileSize;
	private final boolean probesGroup;

	/**
	 * Creates a download task.
	 * @param probesGroup used by logs
	 * @param url the url to locate the file to download
	 * @param maxTrials maximum amount of trials to retrieve the file
	 * @param maxFileSize maximum allowed size for the file to download
	 */
	public DownloadTask(boolean probesGroup, URL url, int maxTrials, int maxFileSize) {
		this.url = url;
		this.maxTrials = maxTrials;
		this.maxFileSize = maxFileSize;
		this.probesGroup = probesGroup;
	}

	/**
	 * Creates a download task. The given url is parsed to generate an URL object, an exception is thrown if the operation failed.
	 * @param probesGroup
	 * @param url
	 * @param maxTrials
	 * @param maxFileSize
	 * @throws MalformedURLException
	 */
	public DownloadTask(boolean probesGroup, String url, int maxTrials, int maxFileSize) throws MalformedURLException {
		this(probesGroup, DownloadTask.generateURL(url), maxTrials, maxFileSize);
	}

	public DownloadTask(boolean probesGroup, URL url) {
		this(probesGroup, url, DownloadTask.MAX_TRIALS, DownloadTask.MAX_FILE_SIZE);
	}

	public DownloadTask(boolean probesGroup, String url) throws MalformedURLException {
		this(probesGroup, url, DownloadTask.MAX_TRIALS, DownloadTask.MAX_FILE_SIZE);
	}

	/**
	 * Executes the task and tries to download the requested file.
	 * 
	 * The process is able to follow the redirection BUT a redirection is considered as a trial, 
	 * so if the given url leads to four redirections in a row until finding the actual file the download process
	 * will fail and throw an UnreachableUrlException.
	 * 
	 * @return a tmpFile containing downloaded content.
	 * 
	 * @throws ErrorCodeException if the server did not send back '200' 
	 * @throws InvalidContentException if the file to download is a video
	 * @throws FileSizeOutOfRangeException if the file's size is greater than the maximum authorized size
	 * @throws UnreachableUrlException if the amount of trials is reached without retrieving the file
	 */
	public TmpFile execute() throws ErrorCodeException, InvalidContentException, FileSizeOutOfRangeException, UnreachableUrlException {

		ProbesHandler.DOWNLOAD.addProcessing(this.probesGroup);

		URL currentUrl = this.url;
		HttpURLConnection connection = null;
		TmpFile theFile = null;
		int i;
		for (i = 0; i < this.maxTrials; i++) {

			try {
				connection = (HttpURLConnection) currentUrl.openConnection();
				connection.setConnectTimeout(Constantes.CONNECTION_TIMEOUT);
				connection.setReadTimeout(Constantes.CONNECTION_TIMEOUT);

				if (connection.getResponseCode() / 100 != 2) {
					throw new ErrorCodeException(connection.getResponseCode());
				} else if ("video".contains(connection.getContentType())) {
					throw new InvalidContentException(connection.getContentType());
				} else if (!currentUrl.equals(connection.getURL())) {
					currentUrl = connection.getURL();
				} else {

					final int fileSize = connection.getContentLength();
					if ((fileSize > this.maxFileSize) || (fileSize <= 0)) {
						throw new FileSizeOutOfRangeException(fileSize);
					}

					theFile = FilesManager.TMP_MANAGER.new TmpFile();
					IOUtils.copy(connection.getInputStream(), new FileOutputStream(theFile.get()));

					break;
				}
			} catch (final IOException e) {

			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		}

		if ((theFile == null) && (i == this.maxTrials)) {
			throw new UnreachableUrlException();
		}

		ProbesHandler.DOWNLOAD.addProcessed(this.probesGroup);

		return theFile;
	}

	/**
	 * A convenient method to build a valid URL object based on a give string.

	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	private static URL generateURL(String url) throws MalformedURLException {
		// Only allow HTTP URLs.
		if (!url.toLowerCase().startsWith("http://")) {
			return null;
		}

		final URL verifiedUrl = new URL(url.replaceAll(" ", "%20"));

		// TODO don't see the point of it, maybe useless
		if (verifiedUrl.getFile().length() < 2) {
			throw new MalformedURLException("getFile() returned <2 ");
		}

		return verifiedUrl;
	}

}
