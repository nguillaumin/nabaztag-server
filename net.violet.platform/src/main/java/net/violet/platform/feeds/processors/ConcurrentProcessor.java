package net.violet.platform.feeds.processors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * The ConcurrentProcessor is not an EntryProcessor on its own. It actually acts as an EntryProcessor wrapper. It implements a 
 * processEntries method able to handle several entries at the same time, to concurrently process them and returned a sorted list of
 * ProcessedEntry.
 */
public class ConcurrentProcessor {

	private static final Logger LOGGER = Logger.getLogger(ConcurrentProcessor.class);

	private final EntryProcessor processor;

	/**
	 * Wraps the given EntryProcessor in a new ConcurrentProcessor.
	 * @param processor
	 */
	public ConcurrentProcessor(EntryProcessor processor) {
		this.processor = processor;
	}

	/**
	 * Processes the given entries. If one of the provided entries cannot be processed (processing it threw an Exception), the 
	 * returned list of ProcessedEntry objects will be shorter than expected.
	 * @param entries
	 * @return
	 */
	public List<ProcessedEntry> processEntries(final List<SyndEntry> entries) {

		if (!entries.isEmpty()) {

			final ProcessedEntry[] processedEntries = new ProcessedEntry[entries.size()];
			final ExecutorService entriesExecutor = Executors.newFixedThreadPool(entries.size());

			for (int i = 0; i < entries.size(); i++) {
				final int index = i;

				entriesExecutor.execute(new Runnable() {

					public void run() {
						try {
							processedEntries[index] = ConcurrentProcessor.this.processor.processEntry(entries.get(index));
						} catch (final ProcessingException e) {
							ConcurrentProcessor.LOGGER.error("Failed to process a feed entry", e);
						}
					}
				});
			}

			waitForTermination(entriesExecutor);

			final List<ProcessedEntry> resultList = new ArrayList<ProcessedEntry>();
			for (int i = 0; i < processedEntries.length; i++) {
				if (processedEntries[i] != null) {
					resultList.add(processedEntries[i]);
				}
			}

			return resultList;
		}

		return Collections.emptyList();
	}

	private void waitForTermination(ExecutorService executor) {

		executor.shutdown();
		while (!executor.isTerminated()) {
			try {
				executor.awaitTermination(1, TimeUnit.SECONDS);
			} catch (final InterruptedException e) {
				ConcurrentProcessor.LOGGER.fatal(e, e);
			}
		}
	}
}
