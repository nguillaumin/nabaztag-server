package net.violet.platform.daemons.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.FilesImpl;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.log4j.Logger;

/**
 *The goal of this crawler is to copy all files from the rsc to the Hadoop
 * Distributed File System. It iterates on the Files table in the database,
 * copies the files from the rsc to the HDFS and edits the links in the Files
 * table. Files are not deleted from the rsc, instead the old rsc path (i.e. the
 * path containing 'broadcast/broad') is added to a specific file. The purpose
 * of this file is to be able to remove the files later.
 */
public class HadoopCrawler extends AbstractCrawler {

	public HadoopCrawler(String[] inArgs) {
		super(inArgs);
	}

	private static final Logger LOGGER = Logger.getLogger(HadoopCrawler.class);

	private static final String fileName;
	private static final BufferedWriter theWriter;
	static {
		final Calendar theCalendar = Calendar.getInstance();
		fileName = "toDelete-" + theCalendar.get(Calendar.YEAR) + (theCalendar.get(Calendar.MONTH) + 1) + theCalendar.get(Calendar.DATE);
		BufferedWriter theBWriter = null;
		try {
			theBWriter = new BufferedWriter(new FileWriter(HadoopCrawler.fileName, true));
		} catch (final IOException e) {
			HadoopCrawler.LOGGER.fatal(e, e);
		}

		theWriter = theBWriter;
	}
	private static final Executor THREAD_EXECUTOR = Executors.newFixedThreadPool(4);

	private static void processOnFile(Files file) {

		// copies the file
		if (!FilesManagerFactory.getRscManager().copyFilesToFilesManager(file, FilesManagerFactory.getHttpManager(), null)) {
			HadoopCrawler.LOGGER.fatal("Failed to copy from : " + file.getPath() + " fileId=" + file.getId());
		}

		if (file.getType() == MimeType.MIME_TYPES.A_MPEG) {
			if ((file.getPath2chor() != null) && !net.violet.common.StringShop.EMPTY_STRING.equals(file.getPath2chor()) && !FilesManagerFactory.getRscManager().copyFilesToFilesManager(file, FilesManagerFactory.getHttpManager(), MimeType.MIME_TYPES.CHOR)) {
				HadoopCrawler.LOGGER.fatal("Failed to copy from : " + file.getPath2chor() + " fileId=" + file.getId());
			} else {
				synchronized (HadoopCrawler.theWriter) {

					// adds the path to the file
					try {
						HadoopCrawler.theWriter.write(file.getPath2chor());
						HadoopCrawler.theWriter.newLine();
					} catch (final IOException e) {
						HadoopCrawler.LOGGER.fatal("Failed to open file " + HadoopCrawler.fileName);
					}
				}
			}
			if ((file.getPath2adp() != null) && !net.violet.common.StringShop.EMPTY_STRING.equals(file.getPath2adp()) && !FilesManagerFactory.getRscManager().copyFilesToFilesManager(file, FilesManagerFactory.getHttpManager(), MimeType.MIME_TYPES.ADP)) {
				HadoopCrawler.LOGGER.fatal("Failed to copy from : " + file.getPath2adp() + " fileId=" + file.getId());
			} else {
				synchronized (HadoopCrawler.theWriter) {

					// adds the path to the file
					try {
						HadoopCrawler.theWriter.write(file.getPath2adp());
						HadoopCrawler.theWriter.newLine();
					} catch (final IOException e) {
						HadoopCrawler.LOGGER.fatal("Failed to open file " + HadoopCrawler.fileName);
					}
				}
			}
		}

		file.setPath(FilesManagerFactory.FILE_MANAGER.getFilesPath(file.getPath(), FilesManagerFactory.getHttpManager()));

		synchronized (HadoopCrawler.theWriter) {

			// adds the path to the file
			try {
				HadoopCrawler.theWriter.write(file.getPath());
				HadoopCrawler.theWriter.newLine();
			} catch (final IOException e) {
				HadoopCrawler.LOGGER.fatal("Failed to open file " + HadoopCrawler.fileName);
			}
		}

	}

	private static void processFiles(Files inFile) {
		if ((inFile.getPath() != null) && !net.violet.common.StringShop.EMPTY_STRING.equals(inFile.getPath()) && !inFile.getPath().startsWith(Files.CATEGORIES.BROAD.getPath(FilesManagerFactory.getHttpManager()))) {
			HadoopCrawler.LOGGER.info(inFile.getPath());
			HadoopCrawler.processOnFile(inFile);

		}
	}

	@Override
	protected void process() {
		final ThreadWatcher theWatcher = new ThreadWatcher();
		FilesImpl.walkRsc(new FilesImpl.RecordWalker<Files>() {

			public void process(final Files file) {
				HadoopCrawler.THREAD_EXECUTOR.execute(new Runnable() {

					public void run() {
						theWatcher.incrementAmountWorkingThreads();
						HadoopCrawler.processFiles(file);
						theWatcher.countDown();
					}
				});
			}
		});

		theWatcher.await();
		HadoopCrawler.endWritingProcess();

	}

	private static void endWritingProcess() {
		try {
			HadoopCrawler.theWriter.close();
		} catch (final IOException e) {
			HadoopCrawler.LOGGER.fatal(e, e);
		}
	}

	@Override
	public void stopCrawler() {
		HadoopCrawler.endWritingProcess();
		super.stopCrawler();
	}

}
