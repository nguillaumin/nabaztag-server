package net.violet.platform.datamodel;

import java.util.concurrent.atomic.AtomicBoolean;

import net.violet.db.records.Record.RecordWalker;
import net.violet.platform.datamodel.Feed.AccessRight;
import net.violet.platform.datamodel.Feed.Type;
import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class FeedTest extends DBTest {

	@Test
	public void createTest() {
		final Lang theLang = Factories.LANG.find(1);
		final Feed theNewFeed = Factories.FEED.create("theUrl", Type.PODCAST, theLang, AccessRight.FREE);
		Assert.assertNotNull(theNewFeed);
		Assert.assertNull(theNewFeed.getETag());
		Assert.assertNull(theNewFeed.getLastModified());
		Assert.assertEquals("theUrl", theNewFeed.getUrl());
		Assert.assertEquals(theLang, theNewFeed.getLanguage());
		Assert.assertEquals(Type.PODCAST, theNewFeed.getType());
		Assert.assertEquals(AccessRight.FREE, theNewFeed.getAccessRight());
	}

	@Test
	public void findByUrlAndTypeTest() {
		Feed theFeed = Factories.FEED.findByUrlAndType("http://www.google.com/feed", Type.RSS);
		Assert.assertEquals(Long.valueOf(1), theFeed.getId());

		theFeed = Factories.FEED.findByUrlAndType("http://www.google.com/feed2", Type.RSS);
		Assert.assertEquals(Long.valueOf(2), theFeed.getId());

		theFeed = Factories.FEED.findByUrlAndType("http://www.google.com/feed", Type.PODCAST);
		Assert.assertEquals(Long.valueOf(3), theFeed.getId());

		theFeed = Factories.FEED.findByUrlAndType("http://www.google.com/feed2", Type.PODCAST);
		Assert.assertEquals(Long.valueOf(4), theFeed.getId());
	}

	@Test
	public void walkTest() {

		final AtomicBoolean feed1 = new AtomicBoolean(false);
		final AtomicBoolean feed2 = new AtomicBoolean(false);
		final int amount = Factories.FEED.walkByTypeAndAccessRight(Type.RSS, AccessRight.FREE, new RecordWalker<Feed>() {

			public void process(Feed feed) {
				if (feed.getId() == 1) {
					feed1.set(true);
				} else if (feed.getId() == 2) {
					feed2.set(true);
				} else {
					Assert.fail("This feed shouldn't have been walked here ! " + feed.getUrl() + " " + feed.getType());
				}
			}
		});

		Assert.assertEquals(2, amount);
		Assert.assertTrue(feed1.get());
		Assert.assertTrue(feed2.get());
	}
}
