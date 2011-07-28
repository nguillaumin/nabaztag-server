package net.violet.platform.daemons.crawlers.processor.downloader;

import java.util.LinkedList;
import java.util.List;

import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.concurrent.units.AbstractCrawlerProcessUnit;
import net.violet.platform.util.concurrent.units.DownloadProcessUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class DownloadJobProcessorTest extends MockTestBase {

	final List<Files> filesId = new LinkedList<Files>();

	@Test
	public void test1Download() {
		final DownloadJobProcessor theDownloadJobProcessor = new DownloadJobProcessor(Constantes.MAX_PROCESSING_ITEMS_DOWNLOAD, "testDownloader", true);
		final ThreadWatcher theLatch = new ThreadWatcher();
		final List<DownloadProcessUnit> theProcessUnits = new LinkedList<DownloadProcessUnit>();

		for (int nbProcessingUnit = 1; nbProcessingUnit > 0; nbProcessingUnit--) {
			final DownloadProcessUnit theProcessUnit = new DownloadProcessUnit("idXml", "the Title", "http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3", null);
			theProcessUnit.setThreadWatcher(theLatch);
			theProcessUnit.startWatching();
			theProcessUnits.add(theProcessUnit);
			theDownloadJobProcessor.addJob(theProcessUnit);
		}

		theLatch.await();

		for (final AbstractCrawlerProcessUnit aProcessUnit : theProcessUnits) {
			if (aProcessUnit.getResult() != null) {
				this.filesId.add(aProcessUnit.getResult());
			}
		}

		Assert.assertEquals(this.filesId.size(), theProcessUnits.size());

		for (final AbstractCrawlerProcessUnit aProcessUnit : theProcessUnits) {
			Assert.assertTrue(FilesManagerFactory.FILE_MANAGER.fileExists(aProcessUnit.getResult().getPath()));
		}
	}

	/**
	 * On fait 5 download
	 * 
	 */
	@Test
	public void test5Downloads() {

		final DownloadJobProcessor theDownloadJobProcessor = new DownloadJobProcessor(Constantes.MAX_PROCESSING_ITEMS_DOWNLOAD, "testDownloader", true);
		final ThreadWatcher theLatch = new ThreadWatcher();
		final List<DownloadProcessUnit> theProcessUnits = new LinkedList<DownloadProcessUnit>();

		for (int nbProcessingUnit = 5; nbProcessingUnit > 0; nbProcessingUnit--) {
			final DownloadProcessUnit theProcessUnit = new DownloadProcessUnit("idXml", "the Title", "http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3", null);
			theProcessUnit.setThreadWatcher(theLatch);
			theProcessUnit.startWatching();
			theProcessUnits.add(theProcessUnit);
			theDownloadJobProcessor.addJob(theProcessUnit);
		}
		theLatch.await();

		for (final AbstractCrawlerProcessUnit aProcessUnit : theProcessUnits) {
			if (aProcessUnit.getResult() != null) {
				this.filesId.add(aProcessUnit.getResult());
			}
		}

		Assert.assertEquals(this.filesId.size(), theProcessUnits.size());

		for (final AbstractCrawlerProcessUnit aProcessUnit : theProcessUnits) {
			Assert.assertTrue(FilesManagerFactory.FILE_MANAGER.fileExists(aProcessUnit.getResult().getPath()));
		}

	}

	@After
	public void purgeTmp() {
		for (final Files aFile : this.filesId) {
			aFile.delete();
		}
		// LibFile.exec("rm -rf /tmp/podcast/");
	}
}
