package net.violet.platform.daemons.crawlers.processor.downloader;

import net.violet.platform.daemons.crawlers.processor.JobProcessor;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.concurrent.units.DownloadProcessUnit;
import net.violet.probes.ProbesHandler;

public class DownloadJobProcessor extends JobProcessor<DownloadProcessUnit, Downloader> {

	private final boolean isFree;

	// Constantes.MAX_PROCESSING_ITEMS_DOWNLOAD
	public DownloadJobProcessor(int inNbProcessingThreads, String inService, boolean isFree) {
		super(inNbProcessingThreads, inService, new Downloader(inNbProcessingThreads, Constantes.MAX_DOWNLOADING_THREADS, isFree));
		this.isFree = isFree;
	}

	@Override
	protected void runWhenAddingElement( DownloadProcessUnit inUnit) {
		ProbesHandler.DOWNLOAD.addNewDownload(this.isFree);
	}

	@Override
	protected Downloader getWorker() {
		return super.getWorker();
	}
}
