package net.violet.platform.datamodel;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class FeedItemTest extends DBTest {

	@Test
	public void createTest() {
		final Feed theFeed = Factories.FEED.find(1);

		final List<Files> theFiles = new ArrayList<Files>();
		for (int i = 1; i <= 3; i++) {
			theFiles.add(Factories.FILES.find(i));
		}

		final FeedItem item = Factories.FEED_ITEM.create(theFeed, theFiles, "myTitle", "myLink", "myURI");
		Assert.assertNotNull(item);
		Assert.assertEquals(item.getFeed(), theFeed);
		Assert.assertEquals(theFiles, item.getContents());
	}

	@Test
	public void findTest() {
		final Feed theFeed = Factories.FEED.find(2);

		final List<FeedItem> items = Factories.FEED_ITEM.findAllByFeed(theFeed);
		Assert.assertEquals(4, items.size());
		Assert.assertEquals("title1", items.get(0).getTitle());
		Assert.assertEquals("title2", items.get(1).getTitle());
		Assert.assertEquals("title3", items.get(2).getTitle());
		Assert.assertEquals("title4", items.get(3).getTitle());
	}

	@Test
	public void findAllNewerTest() {
		{
			final FeedItem refItem = Factories.FEED_ITEM.find(2);

			final List<FeedItem> newerItems = Factories.FEED_ITEM.findAllNewer(refItem);
			Assert.assertEquals(2, newerItems.size());
			Assert.assertEquals("title3", newerItems.get(0).getTitle());
			Assert.assertEquals("title4", newerItems.get(1).getTitle());
		}
		{
			final FeedItem refItem = Factories.FEED_ITEM.find(1);

			final List<FeedItem> newerItems = Factories.FEED_ITEM.findAllNewer(refItem);
			Assert.assertEquals(3, newerItems.size());
			Assert.assertEquals("title2", newerItems.get(0).getTitle());
			Assert.assertEquals("title3", newerItems.get(1).getTitle());
			Assert.assertEquals("title4", newerItems.get(2).getTitle());
		}
		{
			final FeedItem refItem = Factories.FEED_ITEM.find(4);

			final List<FeedItem> newerItems = Factories.FEED_ITEM.findAllNewer(refItem);
			Assert.assertTrue(newerItems.isEmpty());
		}
	}

	@Test
	public void findAllNewerWithAmountTest() {
		{
			final FeedItem refItem = Factories.FEED_ITEM.find(1);

			final List<FeedItem> newerItems = Factories.FEED_ITEM.findAllNewer(refItem, 0);
			Assert.assertEquals(3, newerItems.size());
			Assert.assertEquals("title2", newerItems.get(0).getTitle());
			Assert.assertEquals("title3", newerItems.get(1).getTitle());
			Assert.assertEquals("title4", newerItems.get(2).getTitle());
		}
		{
			final FeedItem refItem = Factories.FEED_ITEM.find(1);

			final List<FeedItem> newerItems = Factories.FEED_ITEM.findAllNewer(refItem, 2);
			Assert.assertEquals(2, newerItems.size());
			Assert.assertEquals("title3", newerItems.get(0).getTitle());
			Assert.assertEquals("title4", newerItems.get(1).getTitle());
		}
		{
			final FeedItem refItem = Factories.FEED_ITEM.find(1);

			final List<FeedItem> newerItems = Factories.FEED_ITEM.findAllNewer(refItem, 1);
			Assert.assertEquals(1, newerItems.size());
			Assert.assertEquals("title4", newerItems.get(0).getTitle());
		}

	}

	@Test
	public void findWithAmountTest() {
		{
			final Feed feed = Factories.FEED.find(2);

			final List<FeedItem> newerItems = Factories.FEED_ITEM.findAllByFeed(feed, 1);
			Assert.assertEquals(1, newerItems.size());
			Assert.assertEquals("title4", newerItems.get(0).getTitle());
		}
		{
			final Feed feed = Factories.FEED.find(2);

			final List<FeedItem> newerItems = Factories.FEED_ITEM.findAllByFeed(feed, 2);
			Assert.assertEquals(2, newerItems.size());
			Assert.assertEquals("title3", newerItems.get(0).getTitle());
			Assert.assertEquals("title4", newerItems.get(1).getTitle());
		}
		{
			final Feed feed = Factories.FEED.find(2);

			final List<FeedItem> newerItems = Factories.FEED_ITEM.findAllByFeed(feed, 3);
			Assert.assertEquals(3, newerItems.size());
			Assert.assertEquals("title2", newerItems.get(0).getTitle());
			Assert.assertEquals("title3", newerItems.get(1).getTitle());
			Assert.assertEquals("title4", newerItems.get(2).getTitle());
		}
	}

	@Test
	public void usesFilesTest() {
		final Files usedFiles = Factories.FILES.find(5);
		Assert.assertTrue(Factories.FEED_ITEM.usesFiles(usedFiles));

		final Files unusedFiles = Factories.FILES.find(1);
		Assert.assertFalse(Factories.FEED_ITEM.usesFiles(unusedFiles));
	}

}
