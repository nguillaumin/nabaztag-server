package net.violet.platform.feeds.senders;

import java.util.List;

import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.feeds.processors.ProcessedEntry;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * The DBSender is used to persist feed entries in the database.
 */
public class DBSender implements EntrySender {

	private final int maxEntriesPerFeed;
	private final FeedData feed;

	/**
	 * Creates a DBSender which will add the ProcessedEntry as items of the provided FeedData.
	 * MaxEntriesPerFeed indicates the maximum amount of entries which can be stored for this feed, if this amount is 
	 * reached, the eldest entries are removed.
	 * @param maxEntriesPerFeed
	 */
	public DBSender(FeedData feed, int maxEntriesPerFeed) {
		this.maxEntriesPerFeed = maxEntriesPerFeed;
		this.feed = feed;
	}

	/**
	 * Stores the given entries in the DB as nested entries of the provided feed. It the feed has too many nested entries the 
	 * eldest ones are removed (based on the provided maxEntriesPerFeed parameter, see the constructor above).
	 */
	public void performTreatment(List<ProcessedEntry> entries) {
		for (final ProcessedEntry anEntry : entries) {
			final SyndEntry theOriginalEntry = anEntry.getEntry();
			this.feed.addItem(anEntry.getContents(), theOriginalEntry.getTitle(), anEntry.getConcretLink(), theOriginalEntry.getUri(), this.maxEntriesPerFeed);
		}
	}

}
