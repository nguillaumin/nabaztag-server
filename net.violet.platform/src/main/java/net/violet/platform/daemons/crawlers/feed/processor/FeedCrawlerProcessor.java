package net.violet.platform.daemons.crawlers.feed.processor;

import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.platform.daemons.crawlers.processor.JobProcessor;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.concurrent.units.AbstractCrawlerProcessUnit;

public abstract class FeedCrawlerProcessor<T extends AbstractCrawlerProcessUnit> {

	private final JobProcessor<T, ? extends Worker<T>> mTTSJobProcessor;

	public FeedCrawlerProcessor(JobProcessor<T, ? extends Worker<T>> inJobProcessor) {
		this.mTTSJobProcessor = inJobProcessor;
	}

	public void processUnit(T inUnit) {
		this.mTTSJobProcessor.addJob(inUnit);
	}

	public abstract T getProcessUnit(String inTitle, String inLink, String inURI, Lang inLang, Integer inTTL);
}
