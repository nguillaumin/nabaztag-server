package net.violet.platform.feeds.analyzers;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

public class UriBasedAnalyzerTest {

	@Test
	public void noNewEntryTest() {

		final SyndFeed feed = new SyndFeedImpl();
		feed.setEntries(Arrays.asList(createEntry("third", "ccc"), createEntry("second", "bbb"), createEntry("first", "aaa")));

		final UriBasedAnalyzer analyzer = new UriBasedAnalyzer(10, "ccc");
		final List<SyndEntry> entries = analyzer.getNewEntries(feed);
		Assert.assertTrue(entries.isEmpty());
	}

	@Test
	public void newEntriesTest() {

		final SyndFeed feed = new SyndFeedImpl();
		feed.setEntries(Arrays.asList(createEntry("third", "ccc"), createEntry("second", "bbb"), createEntry("first", "aaa")));

		final UriBasedAnalyzer analyzer = new UriBasedAnalyzer(10, "aaa");
		final List<SyndEntry> entries = analyzer.getNewEntries(feed);
		Assert.assertEquals(2, entries.size());

		Assert.assertEquals("second", entries.get(0).getTitle());
		Assert.assertEquals("third", entries.get(1).getTitle());
	}

	@Test
	public void aLotOfNewEntriesTest() {

		final SyndFeed feed = new SyndFeedImpl();
		feed.setEntries(Arrays.asList(createEntry("fourth", "ddd"), createEntry("third", "ccc"), createEntry("second", "bbb"), createEntry("first", "aaa")));

		final UriBasedAnalyzer analyzer = new UriBasedAnalyzer(2, "aaa");
		final List<SyndEntry> entries = analyzer.getNewEntries(feed);
		Assert.assertEquals(2, entries.size());

		Assert.assertEquals("third", entries.get(0).getTitle());
		Assert.assertEquals("fourth", entries.get(1).getTitle());
	}

	private SyndEntry createEntry(String title, String uri) {
		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle(title);
		entry.setUri(uri);
		return entry;
	}

}
