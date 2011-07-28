package net.violet.platform.daemons.crawlers.feed.processor;

import net.violet.platform.daemons.crawlers.processor.downloader.DownloadJobProcessor;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.concurrent.units.DownloadProcessUnit;

public class FeedCrawlerDownloadProcessor extends FeedCrawlerProcessor<DownloadProcessUnit> {

	// getService().getLabel()+"-"+(isFree ? SERVICE_ACCESSRIGHT.FREE.toString()
	// : SERVICE_ACCESSRIGHT.FULL.toString())
	public FeedCrawlerDownloadProcessor(int inNbProcessingThreads, String inThreadName, boolean isFree) {
		super(new DownloadJobProcessor(inNbProcessingThreads, inThreadName, isFree));
	}

	@Override
	public DownloadProcessUnit getProcessUnit(String inTitle, String inLink, String inURI, Lang inLang, Integer inTTL) {
		return (inLink != null) ? new DownloadProcessUnit(inURI, inTitle, inLink, inTTL) : null;
	}
}
