package net.violet.platform.feeds.analyzers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.mock.FeedItemMock;

import org.junit.Assert;
import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

public class HistoryAnalyzerTest extends MockTestBase {

	@Test
	public void noNewEntryTest() {

		final SyndFeed feed = new SyndFeedImpl();
		feed.setEntries(Arrays.asList(createEntry("third", "ccc"), createEntry("second", "bbb"), createEntry("first", "aaa")));

		final List<FeedItem> knownItems = new ArrayList<FeedItem>();
		knownItems.add(createItem("ccc", "third"));
		knownItems.add(createItem("bbb", "second"));
		knownItems.add(createItem("aaa", "first"));

		final HistoryAnalyzer analyzer = new HistoryAnalyzer(10, knownItems);
		final List<SyndEntry> entries = analyzer.getNewEntries(feed);
		Assert.assertTrue(entries.isEmpty());
	}

	@Test
	public void newEntriesTest() {

		final SyndFeed feed = new SyndFeedImpl();
		feed.setEntries(Arrays.asList(createEntry("third", "ccc"), createEntry("second", "bbb"), createEntry("first", "aaa")));

		final List<FeedItem> knownItems = new ArrayList<FeedItem>();
		knownItems.add(createItem("aaa", "first"));

		final HistoryAnalyzer analyzer = new HistoryAnalyzer(10, knownItems);
		final List<SyndEntry> entries = analyzer.getNewEntries(feed);
		Assert.assertEquals(2, entries.size());

		Assert.assertEquals("second", entries.get(0).getTitle());
		Assert.assertEquals("third", entries.get(1).getTitle());
	}

	@Test
	public void aLotOfNewEntriesTest() {

		final SyndFeed feed = new SyndFeedImpl();
		feed.setEntries(Arrays.asList(createEntry("fourth", "ddd"), createEntry("third", "ccc"), createEntry("second", "bbb"), createEntry("first", "aaa")));

		final List<FeedItem> knownItems = new ArrayList<FeedItem>();
		knownItems.add(createItem("aaa", "first"));

		final HistoryAnalyzer analyzer = new HistoryAnalyzer(2, knownItems);
		final List<SyndEntry> entries = analyzer.getNewEntries(feed);
		Assert.assertEquals(2, entries.size());

		Assert.assertEquals("third", entries.get(0).getTitle());
		Assert.assertEquals("fourth", entries.get(1).getTitle());
	}

	private SyndEntry createEntry(String title, String uri) {
		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle(title);
		entry.setUri(uri);
		entry.setLink(uri);
		return entry;
	}

	private FeedItem createItem(String uri, String title) {
		return new FeedItemMock(1, Collections.<Files> emptyList(), title, uri, uri);
	}

}
