package net.violet.platform.daemons.crawlers.feed.processor;

import net.violet.platform.daemons.crawlers.processor.TTSGenerator.TTSJobProcessor;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.concurrent.units.TTSProcessUnit;

public class FeedCrawlerTTSProcessor extends FeedCrawlerProcessor<TTSProcessUnit> {

	//	
	public FeedCrawlerTTSProcessor(int inNbProcessingThreads, String inThreadName) {
		super(new TTSJobProcessor(inNbProcessingThreads, inThreadName));
	}

	@Override
	public TTSProcessUnit getProcessUnit(String inTitle, String inLink, String inURI, Lang inLang, Integer inTTL) {
		return new TTSProcessUnit(inURI, inTitle, inLink, inLang, inTTL);
	}
}
