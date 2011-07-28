package net.violet.platform.daemons.crawlers.processor.downloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;

import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.common.utils.concurrent.units.AbstractProcessUnit;
import net.violet.common.utils.io.StreamTools;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.content.converters.ContentType;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.concurrent.units.DownloadProcessUnit;
import net.violet.probes.ProbesHandler;

import org.apache.log4j.Logger;

class Downloader implements Worker<DownloadProcessUnit> {

	private static final Logger LOGGER = Logger.getLogger(Downloader.class);
	private static final int MAX_FILE_SIZE = 100000000;
	private static final int BUFFER_SIZE = 65536;
	private static final Pattern FAAD_MATCHER = Pattern.compile(".*\\.m(4a|p4)$");

	private final Executor mDownloadingThreads; // Thread pool used for the many download processes (the actual downloads)
	private final int mMaxDownloadingThreads;
	private final boolean isFree;

	/**
	 * 
	 * @param inMaxNbDownloadProcesses amount of core downloads (amount of different files to get simultaneously)
	 * @param inMaxRunningDownloads amount of sub threads actually running the downloads (accelerator's feature, amount of threads per core download)
	 * @param isFree (used only for the probes 1 graph from free, 1 graph for full)
	 */
	Downloader(int inMaxNbDownloadProcesses, int inMaxRunningDownloads, boolean isFree) {
		this.mDownloadingThreads = Executors.newFixedThreadPool(inMaxRunningDownloads * inMaxNbDownloadProcesses);
		this.mMaxDownloadingThreads = inMaxRunningDownloads;
		this.isFree = isFree;
	}

	public void process(DownloadProcessUnit inUnit) {
		ProbesHandler.DOWNLOAD.addProcessing(this.isFree);
		final ThreadWatcher theThreadWatcher = new ThreadWatcher();
		TmpFile theFile = null;
		int theFileSize = 0;
		try {
			URL theUrl;
			int nbTrials = 3;
			final URL verifiedUrl = Downloader.verifyUrl(inUnit.get());

			if (verifiedUrl != null) {
				theUrl = verifiedUrl;
			} else {
				inUnit.setError();
				return;
			}

			try {
				theFile = FilesManager.TMP_MANAGER.new TmpFile();
			} catch (final IOException e) {
				Downloader.LOGGER.fatal(e, e);
				inUnit.setError();
				return;
			}

			int timeOutCount = 0;
			HttpURLConnection uc = null;
			/*
			 * As long as we didnt get the size of the file or a time out we try
			 * to connect the server and get those informations If the file that
			 * we are trying to get isnt an audio MP3, we consider the download
			 * as failed and we abord the process.
			 */
			while (!inUnit.isError() && (theUrl != null) && (theFileSize == 0) && (nbTrials > 0)) {
				try {
					// We connect to the server
					uc = (HttpURLConnection) theUrl.openConnection();
					uc.setConnectTimeout(net.violet.common.Constantes.CONNECTION_TIMEOUT);
					uc.setReadTimeout(net.violet.common.Constantes.CONNECTION_TIMEOUT);
					// Make sure response code is in the 200 range.
					if (uc.getResponseCode() / 100 != 2) {
						Downloader.LOGGER.debug("[File length] ERROR : " + uc.getResponseCode());
						inUnit.setError();
						return;
					}

					// We get the size of the file that we want to download
					theFileSize = uc.getContentLength();

					nbTrials--;

					// We get the type of the file that we want to download
					Downloader.LOGGER.debug("type : " + uc.getContentType() + " length: " + theFileSize);

					/*
					 * If the HttpURLConnection object contains a different
					 * URL than the one we used to get the file, it means
					 * that a rerouting / redirection is needed
					 */
					if (!theUrl.equals(uc.getURL())) {
						Downloader.LOGGER.debug("rerouting for " + inUnit.get());
						theUrl = uc.getURL();
					} else if ("video".contains(uc.getContentType())) {
						Downloader.LOGGER.error("We cannot handle videos : " + inUnit.get());
						inUnit.setError();
					}
				} catch (final SocketTimeoutException e) {
					Downloader.LOGGER.debug("timeout : " + inUnit.get());

					// In case of time out we try 3 times to get the files and
					// wait between retrials
					if (timeOutCount > 3) {
						inUnit.setError();
					} else {
						timeOutCount++;
						try {
							Downloader.LOGGER.debug("hum... " + inUnit.get() + " gave me a timeout as I tried to know the length of the file, I shall sleep a little and try again later");
							Thread.sleep(Constantes.TIMEOUT_SLEEP_CYCLE);
						} catch (final InterruptedException e1) {
							Downloader.LOGGER.debug(e1, e1);
						}
					}
				} finally {
					// We make sure we are disconnected
					if (null != uc) {
						uc.disconnect();
					}
				}

			}

			// If we read the size of the file and it is within the range
			if (!inUnit.isError()) {

				if (theFileSize > Downloader.MAX_FILE_SIZE) {
					inUnit.setError();
					Downloader.LOGGER.warn("File size : " + theFileSize + " over or under the arbitrary limit " + inUnit.get());
					return;
				} else if (theFileSize <= 0) {
					inUnit.setError();
					Downloader.LOGGER.warn("File size : " + theFileSize + " over or under the arbitrary limit " + inUnit.get());
				} else if (theUrl == null) {
					inUnit.setError();
					Downloader.LOGGER.warn("Faulty redirection" + inUnit.get());
				} else if (nbTrials <= 0) {
					inUnit.setError();
					Downloader.LOGGER.warn("Too many trials : " + inUnit.get());
				}

			}

			if (!inUnit.isError()) {
				// we prepare to slice the file in 5 chunks to better use the
				// bandwidth :
				// download acceleration concept: we get 5 small files -> 5
				// connections at once instead of 1 big -> 1 connection)
				final int MAX_BUFFER_SIZE = (int) Math.ceil(theFileSize / ((double) this.mMaxDownloadingThreads));
				int fileSizeLeft = theFileSize;
				int fileChunk2Download = MAX_BUFFER_SIZE;

				int threadNumber = 0;
				Downloader.LOGGER.debug("downloading " + inUnit.get() + " (" + theFileSize + ")");

				// As long as we still have pieces of the file to get
				while (fileSizeLeft != 0) {
					// If the file could not be devided in 5 evenly, we get the
					// last piece of it.
					if (fileChunk2Download > fileSizeLeft) {
						fileChunk2Download = fileSizeLeft;
					}

					addDownload(new SingleDownload(threadNumber, MAX_BUFFER_SIZE, fileChunk2Download, theThreadWatcher, inUnit, theUrl), theFile);
					// We update how much of the file needs to be downloaded
					fileSizeLeft -= fileChunk2Download;
					threadNumber++;
				}
				Downloader.LOGGER.debug("Start waiting for end of downloads");
				// As long as all the threads are not finished we wait and rest
				theThreadWatcher.await();
				Downloader.LOGGER.debug("No more waiting");

			}

			if (inUnit.isError() && (theUrl != null)) { // if (fileSize == 0) -> unknown size or something went wrong with the multiple download : download with a single connection.

				Thread.sleep(Constantes.TIMEOUT_ONEMINUTE); //Sleep 1 min just in case we are temporary  on the watch list.
//				the unit goes back to the processing state hoping for more luck this time.
				inUnit.processing();
				addDownload(new SingleDownload(0, 0, 0, theThreadWatcher, inUnit, theUrl), theFile);
				theThreadWatcher.await();
			}

			if (!inUnit.isError()) {
				final ContentType theFileType;
				if (Downloader.FAAD_MATCHER.matcher(inUnit.get()).matches()) {
					theFileType = ContentType.AAC;
				} else {
					theFileType = ContentType.MP3;
				}

				final Files theFiles = FilesManagerFactory.FILE_MANAGER.post(theFile, theFileType, ContentType.MP3_32, Files.CATEGORIES.PODCAST, false, false, MimeType.MIME_TYPES.A_MPEG);

				if (theFiles != null) {
					inUnit.setResult(theFiles);
				} else {
					inUnit.setError();
				}
			}

		} catch (final SocketTimeoutException e) {
			Downloader.LOGGER.debug("timeout : " + inUnit.get());
			inUnit.setError();
		} catch (final Exception e) {
			if (!"cannot process file".equals(e.getMessage())) {
				Downloader.LOGGER.fatal(e, e);
			}
			inUnit.setError();
		} finally {
			Downloader.LOGGER.info("FINALLY!");
			if (inUnit.isError()) {
				Downloader.LOGGER.info("ERROR");
				if (inUnit.getResult() != null) {
					inUnit.getResult().scheduleDeletion();
				}
				Downloader.LOGGER.info("Url not downloaded : " + inUnit.get() + " size : " + theFileSize);
			} else {
				inUnit.processed();
				Downloader.LOGGER.info("Url downloaded : " + inUnit.get() + " size : " + theFileSize);
			}

			inUnit.stopWatching();
			ProbesHandler.DOWNLOAD.addProcessed(this.isFree);
			if (theFile != null) {
				theFile.delete();
			}
		}
	}

	void addDownload(final SingleDownload inSingleDownload, final TmpFile inFile) {
		if (inSingleDownload != null) {
			this.mDownloadingThreads.execute(new Runnable() {

				public void run() {
					// We write the file in a temporary location
					int errorCount = 0;
					boolean fileDownloaded = false;
					final AbstractProcessUnit theProcessUnit = inSingleDownload.getProcessUnit();
					URL url = inSingleDownload.getUrl();
					final String link = (String) theProcessUnit.get();
					try {
						final RandomAccessFile theRandomAccessFile = new RandomAccessFile(inFile.getPath(), "rw");
						try {
							// Do the downloading here
							// We calculate the range of bits we are getting
							// with this thread
							final int size = inSingleDownload.getSize();
							final int startingPoint = inSingleDownload.getId() * inSingleDownload.getStep();
							final int endPoint = startingPoint + size;
							while (!fileDownloaded && !theProcessUnit.isError()) {
								InputStream bufferedStream = null;

								// We connect to the server, tell it the range
								// of bytes we want to get
								final HttpURLConnection theUC = (HttpURLConnection) url.openConnection();
								try {

									if (endPoint != 0) {
										Downloader.LOGGER.debug(inSingleDownload.getId() + " bytes=" + startingPoint + "-" + endPoint);
										theUC.setRequestProperty("Range", "bytes=" + startingPoint + "-" + endPoint);
									}
									theUC.setReadTimeout(net.violet.common.Constantes.CONNECTION_TIMEOUT);

									// Make sure response code is in the 200
									// range.
									if (theUC.getResponseCode() / 100 != 2) {
										Downloader.LOGGER.debug("[File download] ERROR : " + theUC.getResponseCode());

										if (406 == theUC.getResponseCode()) {
											/*
											 * If the HttpURLConnection object
											 * contains a different URL than the
											 * one we used to get the file, it
											 * means that a rerouting /
											 * redirection is needed
											 */
											if (!theUC.getURL().equals(url)) {
												Downloader.LOGGER.debug("rerouting for " + link);
												url = theUC.getURL();
											} else if ("video".contains(theUC.getContentType())) {
												throw new Exception("Not an MP3");
											} else {
												Downloader.LOGGER.error("Unknown 406 for : " + theProcessUnit.get());
												theProcessUnit.setError();
											}
										} else {
											// In case of time out we try 3
											// times to get the files and wait
											// between retrials
											if (errorCount > 3) {
												throw new IOException("ERROR downloading " + link);
											}

											errorCount++;
											try {
												Downloader.LOGGER.debug(inSingleDownload.getId() + " : hum... " + url + " returned me an error, I shall sleep a little and try again later");
												Thread.sleep(Constantes.TIMEOUT_SLEEP_CYCLE);
											} catch (final InterruptedException e1) {
												Downloader.LOGGER.debug(e1, e1);
											}
										}

									} else {
										// We bufferize the input stream for smoothness
										bufferedStream = new BufferedInputStream(theUC.getInputStream(), Downloader.BUFFER_SIZE);

										int bytesRead = 0;
										final byte[] data = new byte[Downloader.BUFFER_SIZE]; // Bigger buffer so we iterate less .
										int step = 0;
										theRandomAccessFile.seek(startingPoint);
										while (((size == 0) || (step < size)) && !theProcessUnit.isError()) {
											// We prepare an array of bytes to
											// stock the file in memory as we
											// build it
											bytesRead = StreamTools.readBytes(bufferedStream, data);

											// If EOF
											if (bytesRead < 0) {
												break;
											}

											theRandomAccessFile.write(data, 0, bytesRead);

											// 1 step forward
											step += bytesRead;
										}
										// Close the streams, add the downloaded
										// chunk in the array holding all the
										// chunk
										fileDownloaded = true;
									}
								} finally {
									theUC.disconnect();
									if (bufferedStream != null) {
										bufferedStream.close();
									}
								}
							} // while
						} finally {
							try {
								theRandomAccessFile.close();
							} catch (final IOException e) {
								Downloader.LOGGER.fatal(e, e);
							}
						}
					} catch (final Exception e) {
						Downloader.LOGGER.fatal(e, e);
						theProcessUnit.setError();
					} finally {
						// We make sure we are disconnected
						inSingleDownload.countDown();
					}
				}
			});
		} else {
			Downloader.LOGGER.error("SingleDownload null");
		}
	}

	static class SingleDownload {

		private final int id;
		private final int step;
		private final int size;
		private final AbstractProcessUnit mProcessUnit;
		private final URL mUrl;
		private final ThreadWatcher mThreadWatcher;

		public SingleDownload(int inId, int inStep, int inSize, ThreadWatcher inThreadWatcher, AbstractProcessUnit inProcessUnit, URL inUrl) {
			this.id = inId;
			this.step = inStep;
			this.size = inSize;
			this.mProcessUnit = inProcessUnit;
			this.mUrl = inUrl;
			this.mThreadWatcher = inThreadWatcher;
			this.mThreadWatcher.incrementAmountWorkingThreads();
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return this.id;
		}

		/**
		 * @return the size
		 */
		public int getSize() {
			return this.size;
		}

		/**
		 * @return the step
		 */
		public int getStep() {
			return this.step;
		}

		/**
		 * count down the mThreadWatcher
		 */
		public void countDown() {
			if (this.mThreadWatcher != null) {
				this.mThreadWatcher.countDown();
			}
		}

		/**
		 * @return the mProcessUnit
		 */
		public AbstractProcessUnit getProcessUnit() {
			return this.mProcessUnit;
		}

		/**
		 * @return the mUrl
		 */
		public URL getUrl() {
			return this.mUrl;
		}

	}

	/**
	 * Quick verification that the given URL is possible
	 * 
	 * @param url a URL (http://.....)
	 * @return an URL object generated with the given url
	 */
	private static URL verifyUrl(String url) {
		// Only allow HTTP URLs.
		if (!url.toLowerCase().startsWith("http://")) {
			return null;
		}

		// Verify format of URL.
		URL verifiedUrl = null;
		try {
			verifiedUrl = new URL(url.replaceAll(" ", "%20"));
		} catch (final Exception e) {
			return null;
		}

		// Make sure URL specifies a file.
		if (verifiedUrl.getFile().length() < 2) {
			return null;
		}

		return verifiedUrl;
	}

	public Object getPoolSize() {
		return ((ThreadPoolExecutor) this.mDownloadingThreads).getMaximumPoolSize();
	}
}
