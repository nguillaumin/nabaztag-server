package net.violet.platform.daemons.crawlers.processor.downloader;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.platform.daemons.crawlers.processor.downloader.Downloader.SingleDownload;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.concurrent.units.DownloadProcessUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class DownloaderTest extends MockTestBase {

	// /usr/local/violet/rsc/192.168.1.11_8080/tests_silence/

	//http://192.168.1.14/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie12.mp3

	// On NabDev

	final List<Files> filesId = new LinkedList<Files>();

	// TODO: LAME inexistant
	@Test
	public void test1DownloadMulti() {
		final Downloader theDownloader = new Downloader(Constantes.MAX_DOWNLOADING_THREADS * Constantes.MAX_PROCESSING_ITEMS_DOWNLOAD, Constantes.MAX_DOWNLOADING_THREADS, true);
		final ThreadWatcher theLatch = new ThreadWatcher();
		final DownloadProcessUnit theProcessUnit = new DownloadProcessUnit("idXml", "the Title", "http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3", null);
		theProcessUnit.setThreadWatcher(theLatch);
		theProcessUnit.startWatching();
		theDownloader.process(theProcessUnit);

		theLatch.await();

		Assert.assertNotNull(theProcessUnit.getResult());
		Assert.assertTrue(FilesManagerFactory.FILE_MANAGER.fileExists(theProcessUnit.getResult().getPath()));
		this.filesId.add(theProcessUnit.getResult());
	}

	@Test
	//TODO en attente de Laurent pour un fall back
	public void test1DownloadMono() {
		final Downloader theDownloader = new Downloader(Constantes.MAX_DOWNLOADING_THREADS * Constantes.MAX_PROCESSING_ITEMS_DOWNLOAD, Constantes.MAX_DOWNLOADING_THREADS, true);
		final ThreadWatcher theLatch = new ThreadWatcher();
		final DownloadProcessUnit theProcessUnit = new DownloadProcessUnit("idXml", "the Title", "http://192.168.1.14/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie12.mp3", null);
		theProcessUnit.setThreadWatcher(theLatch);
		theProcessUnit.startWatching();
		theDownloader.process(theProcessUnit);

		theLatch.await();

		Assert.assertNotNull(theProcessUnit.getResult());
		Assert.assertTrue(FilesManagerFactory.FILE_MANAGER.fileExists(theProcessUnit.getResult().getPath()));
		this.filesId.add(theProcessUnit.getResult());
	}

	@Test
	public void testTooBigDownload() {
		final DownloadJobProcessor theProcessor = new DownloadJobProcessor(1, "Download Test", true);
		final ThreadWatcher theLatch = new ThreadWatcher();
		final DownloadProcessUnit theProcessUnit = new DownloadProcessUnit("idXml", "the Title", "http://192.168.1.11/tests_silence/DiamondCast_-_16_-_James_Diamond_Live_2008_05_15.m4a", null);
		theProcessUnit.setThreadWatcher(theLatch);
		theProcessUnit.startWatching();

		Assert.assertEquals(Constantes.MAX_DOWNLOADING_THREADS, theProcessor.getWorker().getPoolSize());

		theProcessor.addJob(theProcessUnit);

		theLatch.await();

		Assert.assertNull(theProcessUnit.getResult());
	}

	/**
	 * On fait 1 download et on obtient un 404
	 * 
	 */
	@Test
	public void test1Download404() {

		final Downloader theDownloader = new Downloader(Constantes.MAX_DOWNLOADING_THREADS * Constantes.MAX_PROCESSING_ITEMS_DOWNLOAD, Constantes.MAX_DOWNLOADING_THREADS, true);
		final ThreadWatcher theLatch = new ThreadWatcher();
		final DownloadProcessUnit theProcessUnit = new DownloadProcessUnit("idXml", "the Title", "http://192.168.1.11/tests_silence/404.mp3", null);
		theProcessUnit.setThreadWatcher(theLatch);
		theProcessUnit.startWatching();
		theDownloader.process(theProcessUnit);

		theLatch.await();

		Assert.assertNull(theProcessUnit.getResult());

	}

	@After
	public void tearDownDB() {
		for (final Files aFile : this.filesId) {
			aFile.delete();
		}
	}

	@Test
	public void addDownload() throws IOException {
		final TmpFile theFileName = FilesManager.TMP_MANAGER.new TmpFile();
		final Downloader theDownloader = new Downloader(Constantes.MAX_DOWNLOADING_THREADS * Constantes.MAX_PROCESSING_ITEMS_DOWNLOAD, Constantes.MAX_DOWNLOADING_THREADS, true);
		final DownloadProcessUnit theProcessUnit = new DownloadProcessUnit("idXml", "the Title", "http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3", null);
		// final RandomAccessFile theRandomAccessFile = new
		// RandomAccessFile(theFileName, "rw");
		final int MAX_BUFFER_SIZE = 203854 / 2;
		final ThreadWatcher theLatch = new ThreadWatcher();

		for (int i = 0; i < 2; i++) {
			theDownloader.addDownload(new SingleDownload(i, MAX_BUFFER_SIZE, MAX_BUFFER_SIZE, theLatch, theProcessUnit, new URL("http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3")), theFileName);
		}

		theLatch.await();

		Assert.assertEquals(203854, theFileName.length());
		theFileName.delete();
	}

	@Test
	public void addDownloadNoSize() throws IOException {
		final TmpFile theFileName = FilesManager.TMP_MANAGER.new TmpFile();
		final Downloader theDownloader = new Downloader(Constantes.MAX_DOWNLOADING_THREADS * Constantes.MAX_PROCESSING_ITEMS_DOWNLOAD, Constantes.MAX_DOWNLOADING_THREADS, true);
		final DownloadProcessUnit theProcessUnit = new DownloadProcessUnit("idXml", "the Title", "http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3", null);
		// final RandomAccessFile theRandomAccessFile = new
		// RandomAccessFile(theFileName, "rw");
		final ThreadWatcher theLatch = new ThreadWatcher();
		theDownloader.addDownload(new SingleDownload(0, 0, 0, theLatch, theProcessUnit, new URL("http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3")), theFileName);
		theLatch.await();

		Assert.assertEquals(203854, theFileName.length());
		theFileName.delete();
	}

}
