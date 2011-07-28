package net.violet.platform.feeds.crawlers;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.IOException;
import java.util.List;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.feeds.analyzers.FeedsAnalyzer;
import net.violet.platform.feeds.processors.ConcurrentProcessor;
import net.violet.platform.feeds.processors.EntryProcessor;
import net.violet.platform.feeds.processors.ProcessedEntry;
import net.violet.platform.feeds.senders.EntrySender;
import net.violet.platform.httpclient.Connection;
import net.violet.platform.httpclient.ConnectionConfiguration;
import net.violet.platform.httpclient.ConnectionException;
import net.violet.platform.httpclient.ConnectionsManager;

import org.apache.log4j.Logger;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;

/**
 * A FeedCrawler is designed to walk through elements that can be treated as Feed objects, it retrieve the current feed's state
 * (through URL basically), extracts the new entries, processes and stores/sends them.
 * 
 * @param <E> the kind of element to process (can be a Feed object but may be something else, that's the beauty of this class !)
 */
public abstract class FeedCrawler<E> extends AbstractCrawler {

	private static final Logger LOGGER = Logger.getLogger(FeedCrawler.class);

	private final ConnectionsManager manager;
	private final BlockingExecutor<E> executor;

	/**
	 * Creates a FeedCrawler
	 * @param inArgs
	 * @param type
	 */
	protected FeedCrawler(String[] inArgs, Feed.Type type) {
		super(inArgs.clone());

		// Analyse des paramètres
		final LongOpt[] theLongOpts = new LongOpt[] { new LongOpt("process", LongOpt.REQUIRED_ARGUMENT, null, 'a') };

		final Getopt theGetOpt = new Getopt(getClass().getSimpleName(), inArgs, "a:c:", theLongOpts);
		int nbProcesses = 0;
		int nbConnection = 0;
		int theOption = 0;

		while ((theOption = theGetOpt.getopt()) != -1) {
			switch (theOption) {
			case 'a':
				nbProcesses = Integer.parseInt(theGetOpt.getOptarg());
				break;
			case 'c':
				nbConnection = Integer.parseInt(theGetOpt.getOptarg());
				break;
			}
		}

		this.executor = new BlockingExecutor<E>(nbProcesses, new FeedWorker(), type.toString());
		this.manager = new ConnectionsManager(nbConnection);
	}

	protected void processElement(E element) throws InterruptedException {
		this.executor.put(element);
	}

	protected void waitTermination() throws InterruptedException {
		this.executor.waitTermination();
	}

	/**
	 * Generates a ConnectionConfiguration object based on the element to analyze.
	 * @param element
	 * @return
	 * @throws ConnectionException
	 */
	protected abstract ConnectionConfiguration getConnectionConfiguration(E element) throws ConnectionException;

	/**
	 * Returns the FeedAnalyzer which is in charge of finding the new entries in the feed.
	 * @param element
	 * @return
	 */
	protected abstract FeedsAnalyzer getFeedsAnalyzer(E element);

	/**
	 * Returns the EntryProcessor in charge of processing and transforming the new entries.
	 * @param element
	 * @return
	 */
	protected abstract EntryProcessor getEntryProcessor(E element);

	/**
	 * Returns the EntrySender in charge of sending/persisting/... the processed entries
	 * @param element
	 * @return
	 */
	protected abstract EntrySender getEntrySender(E element);

	/**
	 * Updates the element according to the new information.
	 */
	protected abstract void updateElement(E element, List<SyndEntry> newEntries, Connection connection);

	/**
	 * Returns true if the element is currently processable
	 * 
	 * NB: this is actually just a trick to avoid processing sleeping objects' account in the GmailTwitterCrawler.
	 * 
	 * @param element
	 * @return
	 */
	protected abstract boolean isElementProcessable(E element);

	class FeedWorker implements Worker<E> {

		public void process(E element) {

			if (!isElementProcessable(element)) {
				return;
			}

			Connection connection = null;
			try {
				connection = FeedCrawler.this.manager.openConnection(getConnectionConfiguration(element));
			} catch (final ConnectionException e) {
				FeedCrawler.LOGGER.error("Cannot open a valid connection", e);
			}

			if (connection != null) {

				try {
					final FeedsAnalyzer analyzer = getFeedsAnalyzer(element);
					final List<SyndEntry> newEntries = analyzer.getNewEntries(connection.getInputStream());

					if (!newEntries.isEmpty()) {

						final ConcurrentProcessor processor = new ConcurrentProcessor(getEntryProcessor(element));
						final List<ProcessedEntry> processedEntries = processor.processEntries(newEntries);

						final EntrySender sender = getEntrySender(element);
						sender.performTreatment(processedEntries);

						updateElement(element, newEntries, connection);
					}

				} catch (final FeedException e) {
					FeedCrawler.LOGGER.error("Exception occured when parsing feed", e);
				} catch (final IOException e) {
					FeedCrawler.LOGGER.error("Exception occured when accessing feed through network", e);
				} finally {
					connection.close();
				}
			}
		}

	}

}
