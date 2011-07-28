package net.violet.platform.feeds.analyzers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * This analyzer is very simple and should only be used with trustable feeds. It supposes that the feeds are consistent
 * (older entries are not removed) and that the items' uri (also known as GUID) are correctly filled up and respect the
 * RRS standard.
 */
public class UriBasedAnalyzer extends FeedsAnalyzer {

	private final int maxEntriesAmount;
	private final String lastKnownUri;

	/**
	 * @param maxEntriesAmount the maximum amount of entry to retrieve
	 * @param lastKnownUri the uri of the last known entry
	 */
	public UriBasedAnalyzer(int maxEntriesAmount, String lastKnownUri) {
		this.maxEntriesAmount = maxEntriesAmount;
		this.lastKnownUri = lastKnownUri;
	}

	@Override
	public List<SyndEntry> getNewEntries(SyndFeed feed) {
		final List<SyndEntry> resultList = new ArrayList<SyndEntry>();

		final Iterator<SyndEntry> entriesIterator = feed.getEntries().iterator();

		while (entriesIterator.hasNext() && (resultList.size() < this.maxEntriesAmount)) {
			final SyndEntry aFeedEntry = entriesIterator.next();
			if (!aFeedEntry.getUri().equals(this.lastKnownUri)) {
				resultList.add(aFeedEntry);
			} else {
				break;
			}
		}

		Collections.reverse(resultList);
		return resultList;
	}

}
