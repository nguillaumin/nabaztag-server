package net.violet.platform.feeds.senders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Feed.Type;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.FeedItemData;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.feeds.processors.ProcessedEntry;

import org.junit.Assert;
import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class DBSenderTest extends MockTestBase {

	@Test
	public void addEntriesToEmptyFeed() {
		final FeedData theFeed = FeedData.getFeed("url", TtsLangData.findByISOCode("fr-FR"), Type.PODCAST);

		final DBSender sender = new DBSender(theFeed, 3);

		final List<ProcessedEntry> theEntries = new ArrayList<ProcessedEntry>();
		for (int i = 0; i < 2; i++) {
			theEntries.add(getProcessedEntry("myTitle" + i, "file" + i + "-", 3));
		}

		sender.performTreatment(theEntries);

		final List<FeedItemData> items = FeedItemData.findAllByFeed(theFeed);
		Assert.assertEquals(2, items.size());

		FeedItemData item = items.get(0);
		Assert.assertEquals("myTitle0", item.getTitle());
		Assert.assertNull(item.getLink());
		Assert.assertNull(item.getUri());
		List<Files> contents = item.getContents();
		Assert.assertEquals(3, contents.size());

		item = items.get(1);
		Assert.assertEquals("myTitle1", item.getTitle());
		Assert.assertNull(item.getLink());
		Assert.assertNull(item.getUri());
		contents = item.getContents();
		Assert.assertEquals(3, contents.size());
	}

	@Test
	public void addTooMuchEntriesToEmptyFeed() {
		final FeedData theFeed = FeedData.getFeed("url", TtsLangData.findByISOCode("fr-FR"), Type.PODCAST);

		final DBSender sender = new DBSender(theFeed, 2);

		final List<ProcessedEntry> theEntries = new ArrayList<ProcessedEntry>();
		for (int i = 0; i < 3; i++) {
			theEntries.add(getProcessedEntry("myTitle" + i, "file" + i + "-", 3));
		}

		sender.performTreatment(theEntries);

		final List<FeedItemData> items = FeedItemData.findAllByFeed(theFeed);
		Assert.assertEquals(2, items.size());

		FeedItemData item = items.get(0);
		Assert.assertEquals("myTitle1", item.getTitle());
		Assert.assertNull(item.getLink());
		Assert.assertNull(item.getUri());
		List<Files> contents = item.getContents();
		Assert.assertEquals(3, contents.size());

		item = items.get(1);
		Assert.assertEquals("myTitle2", item.getTitle());
		Assert.assertNull(item.getLink());
		Assert.assertNull(item.getUri());
		contents = item.getContents();
		Assert.assertEquals(3, contents.size());
	}

	@Test
	public void addEntriesToPopulatedFeed() {
		final FeedData theFeed = FeedData.getFeed("url", TtsLangData.findByISOCode("fr-FR"), Type.PODCAST);
		theFeed.addItem(Collections.<Files> emptyList(), "firstEntry", null, null, 1);

		final DBSender sender = new DBSender(theFeed, 3);

		final List<ProcessedEntry> theEntries = new ArrayList<ProcessedEntry>();
		for (int i = 0; i < 2; i++) {
			theEntries.add(getProcessedEntry("myTitle" + i, "file" + i + "-", 3));
		}

		sender.performTreatment(theEntries);

		final List<FeedItemData> items = FeedItemData.findAllByFeed(theFeed);
		Assert.assertEquals(3, items.size());

		FeedItemData item = items.get(0);
		Assert.assertEquals("firstEntry", item.getTitle());
		Assert.assertNull(item.getLink());
		Assert.assertNull(item.getUri());
		Assert.assertTrue(item.getContents().isEmpty());

		item = items.get(1);
		Assert.assertEquals("myTitle0", item.getTitle());
		Assert.assertNull(item.getLink());
		Assert.assertNull(item.getUri());
		List<Files> contents = item.getContents();
		Assert.assertEquals(3, contents.size());

		item = items.get(2);
		Assert.assertEquals("myTitle1", item.getTitle());
		Assert.assertNull(item.getLink());
		Assert.assertNull(item.getUri());
		contents = item.getContents();
		Assert.assertEquals(3, contents.size());
	}

	@Test
	public void addTooMuchEntriesToPopulatedFeed() {
		final FeedData theFeed = FeedData.getFeed("url", TtsLangData.findByISOCode("fr-FR"), Type.PODCAST);
		theFeed.addItem(Collections.<Files> emptyList(), "firstEntry", null, null, 1);

		final DBSender sender = new DBSender(theFeed, 2);

		final List<ProcessedEntry> theEntries = new ArrayList<ProcessedEntry>();
		for (int i = 0; i < 5; i++) {
			theEntries.add(getProcessedEntry("myTitle" + i, "file" + i + "-", 3));
		}

		sender.performTreatment(theEntries);

		final List<FeedItemData> items = FeedItemData.findAllByFeed(theFeed);
		Assert.assertEquals(2, items.size());

		FeedItemData item = items.get(0);
		Assert.assertEquals("myTitle3", item.getTitle());
		Assert.assertNull(item.getLink());
		Assert.assertNull(item.getUri());
		List<Files> contents = item.getContents();
		Assert.assertEquals(3, contents.size());

		item = items.get(1);
		Assert.assertEquals("myTitle4", item.getTitle());
		Assert.assertNull(item.getLink());
		Assert.assertNull(item.getUri());
		contents = item.getContents();
		Assert.assertEquals(3, contents.size());
	}

	private ProcessedEntry getProcessedEntry(String title, String fileRootPath, int nbFile) {
		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle(title);

		final List<Files> theFiles = new ArrayList<Files>();
		for (int i = 0; i < nbFile; i++) {
			theFiles.add(Factories.FILES.createFile(fileRootPath + i, MIME_TYPES.A_MPEG));
		}

		return new ProcessedEntry(entry, theFiles);
	}

}
