package net.violet.platform.daemons.crawlers.processor;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;

import org.apache.log4j.Logger;

public abstract class JobProcessor<T, W extends Worker<T>> {

	private static final Logger LOGGER = Logger.getLogger(JobProcessor.class);

	private final BlockingExecutor<T> mJobBlockingExecutor;
	private final W mWorker;

	public JobProcessor(int inNbProcessingThreads, String inService, W inWorker) {
		this.mJobBlockingExecutor = new BlockingExecutor<T>(inNbProcessingThreads, (this.mWorker = inWorker), inService) {

			@Override
			public void put(T element) throws InterruptedException {
				runWhenAddingElement(element);
				super.put(element);
			}
		};
	}

	/**
	 * @throws InterruptedException
	 */
	public void addJob(T inJob) {
		try {
			this.mJobBlockingExecutor.put(inJob);
		} catch (final InterruptedException e) {
			JobProcessor.LOGGER.fatal(e, e);
		}
	}

	protected abstract void runWhenAddingElement(T inUnit);

	protected W getWorker() {
		return this.mWorker;
	}

}
