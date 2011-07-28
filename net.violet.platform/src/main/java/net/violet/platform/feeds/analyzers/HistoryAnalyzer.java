package net.violet.platform.feeds.analyzers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.violet.platform.datamodel.FeedItem;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * This analyzer is not only based on the entries uri. A lot of rss feed are just so messy, so we generate our own uri which is :
 * uri + title + link
 * We also used all the known items to analyze the feed. 
 */
public class HistoryAnalyzer extends FeedsAnalyzer {

	private final int maxEntriesAmount;
	private final List<FeedItem> knownItems;

	public HistoryAnalyzer(int maxEntriesAmount, List<FeedItem> knownItems) {
		this.maxEntriesAmount = maxEntriesAmount;
		this.knownItems = knownItems;
	}

	@Override
	public List<SyndEntry> getNewEntries(SyndFeed feed) {
		final List<SyndEntry> resultList = new ArrayList<SyndEntry>();

		final Iterator<SyndEntry> entriesIterator = feed.getEntries().iterator();

		while (entriesIterator.hasNext() && (resultList.size() < this.maxEntriesAmount)) {
			final SyndEntry aFeedEntry = entriesIterator.next();
			if (aFeedEntry.getTitle() != null) {
				if (isItemNew(aFeedEntry)) {
					resultList.add(aFeedEntry);
				} else {
					break;
				}
			}
		}

		Collections.reverse(resultList);
		return resultList;
	}

	private boolean isItemNew(SyndEntry feedEntry) {
		for (final FeedItem anOldEntry : this.knownItems) {
			final String entryId = feedEntry.getUri() + feedEntry.getTitle() + getEntryLink(feedEntry);
			final String oldEntryId = anOldEntry.getUri() + anOldEntry.getTitle() + anOldEntry.getLink();

			if (entryId.equals(oldEntryId)) {
				return false;
			}
		}

		return true;
	}

	protected String getEntryLink(SyndEntry feedEntry) {
		return feedEntry.getLink();
	}

}
