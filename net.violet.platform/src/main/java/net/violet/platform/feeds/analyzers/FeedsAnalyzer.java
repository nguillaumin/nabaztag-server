package net.violet.platform.feeds.analyzers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * A Feed analyzer is able to parse a stream as a feed content and to extract the new entries according to a given method.
 */
public abstract class FeedsAnalyzer {

	/**
	 * Returns the new entries in a sorted list, the first item is the eldest unread entry.
	 * @param feed
	 * @return
	 * @throws FeedException if the feed cannot be parsed (probably an invalid feed)
	 */
	public List<SyndEntry> getNewEntries(InputStream input) throws FeedException {
		try {
			return getNewEntries(new SyndFeedInput().build(new XmlReader(input)));
		} catch (final IOException e) {
			throw new FeedException("Cannot create Reader with given input", e);
		}
	}

	/**
	 * Returns the new entries in a sorted list, the first item is the eldest unread entry.
	 * @param feed
	 * @return
	 * @throws IllegalArgumentException
	 * @throws FeedException
	 * @throws IOException
	 */
	public abstract List<SyndEntry> getNewEntries(SyndFeed feed);

}
