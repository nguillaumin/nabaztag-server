package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.FilesMock;

import org.junit.Assert;
import org.junit.Test;

public class FeedDataTest extends MockTestBase {

	@Test
	public void getExistingFeedTest() {
		final Feed feed = Factories.FEED.create("url", Feed.Type.PODCAST, Factories.LANG.findByIsoCode("fr-FR"), Feed.AccessRight.FREE);
		FeedData newFeed = FeedData.getFeed("url", TtsLangData.findByISOCode("fr-FR"), Feed.Type.PODCAST);
		Assert.assertNotNull(newFeed);
		Assert.assertTrue(newFeed.isValid());
		Assert.assertEquals(feed, newFeed.getRecord());
		Assert.assertEquals(FeedData.getData(feed), newFeed);

		newFeed = FeedData.getFeed("url", TtsLangData.findByISOCode("fr-FR"), Feed.Type.PODCAST, Feed.AccessRight.FULL);
		Assert.assertNotNull(newFeed);
		Assert.assertTrue(newFeed.isValid());
		Assert.assertEquals(feed, newFeed.getRecord());
		Assert.assertEquals(FeedData.getData(feed), newFeed);
	}

	@Test
	public void getNewFeedTest() {
		final Feed feed = Factories.FEED.create("url", Feed.Type.PODCAST, Factories.LANG.findByIsoCode("fr-FR"), Feed.AccessRight.FREE);
		final FeedData newFeed = FeedData.getFeed("url2", TtsLangData.findByISOCode("fr-FR"), Feed.Type.PODCAST);
		Assert.assertNotNull(newFeed);
		Assert.assertTrue(newFeed.isValid());

		Assert.assertFalse(feed.equals(newFeed.getRecord()));
	}

	@Test
	public void getNewFeedByTypeTest() {
		final Feed feed = Factories.FEED.create("url", Feed.Type.PODCAST, Factories.LANG.findByIsoCode("fr-FR"), Feed.AccessRight.FREE);
		final FeedData newFeed = FeedData.getFeed("url", TtsLangData.findByISOCode("fr-FR"), Feed.Type.RSS);
		Assert.assertNotNull(newFeed);
		Assert.assertTrue(newFeed.isValid());

		Assert.assertFalse(feed.equals(newFeed.getRecord()));
	}

	private static List<Files> generateFiles() {
		final List<Files> files = new ArrayList<Files>();
		for (int i = 1; i < 5; i++) {
			files.add(new FilesMock("pathToFile" + i, MIME_TYPES.A_MPEG));
		}

		return files;
	}

	@Test
	public void addItemsToEmptyFeedTest() {
		final FeedData feed = FeedData.getData(Factories.FEED.create("url", Feed.Type.PODCAST, Factories.LANG.findByIsoCode("fr-FR"), Feed.AccessRight.FREE));

		final FeedItemData theItem = feed.addItem(FeedDataTest.generateFiles(), "title1", "link1", "url1", 1);

		final List<FeedItemData> items = FeedItemData.findAllByFeed(feed);
		Assert.assertEquals(1, items.size());
		Assert.assertEquals(theItem, items.get(0));
	}

	@Test
	public void addItemsAndCrossBoundTest() {
		final FeedData feed = FeedData.getData(Factories.FEED.create("url", Feed.Type.PODCAST, Factories.LANG.findByIsoCode("fr-FR"), Feed.AccessRight.FREE));

		final FeedItemData theItem = feed.addItem(FeedDataTest.generateFiles(), "title1", "link1", "url1", 3);
		final FeedItemData theItem2 = feed.addItem(FeedDataTest.generateFiles(), "title2", "link2", "url2", 3);
		final FeedItemData theItem3 = feed.addItem(FeedDataTest.generateFiles(), "title3", "link3", "url3", 3);

		List<FeedItemData> items = FeedItemData.findAllByFeed(feed);
		Assert.assertEquals(3, items.size());
		Assert.assertEquals(theItem, items.get(0));
		Assert.assertEquals(theItem2, items.get(1));
		Assert.assertEquals(theItem3, items.get(2));

		final FeedItemData theItem4 = feed.addItem(FeedDataTest.generateFiles(), "title4", "link4", "url4", 2);
		items = FeedItemData.findAllByFeed(feed);
		Assert.assertEquals(2, items.size());
		Assert.assertEquals(theItem3, items.get(0));
		Assert.assertEquals(theItem4, items.get(1));
	}
}
