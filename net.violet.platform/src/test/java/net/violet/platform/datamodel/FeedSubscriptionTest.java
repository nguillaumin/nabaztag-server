package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class FeedSubscriptionTest extends DBTest {

	@Test
	public void createWithoutRefTest() {
		final Feed theFeed = Factories.FEED.find(2);
		final VObject theObject = Factories.VOBJECT.find(3);

		final FeedSubscription subscription = Factories.FEED_SUBSCRIPTION.create(theFeed, theObject, 0);
		Assert.assertNotNull(subscription);
		Assert.assertEquals(theFeed, subscription.getFeed());
		Assert.assertNull(subscription.getLastReadItem());
		Assert.assertEquals(theObject, subscription.getObject());
	}

	@Test
	public void createWithNegativeRefTest() {
		final Feed theFeed = Factories.FEED.find(1);
		final VObject theObject = Factories.VOBJECT.find(1);
		final FeedItem item = Factories.FEED_ITEM.find(1);

		final FeedSubscription subscription = Factories.FEED_SUBSCRIPTION.create(theFeed, theObject, -1);
		Assert.assertNotNull(subscription);
		Assert.assertEquals(theFeed, subscription.getFeed());
		Assert.assertEquals(item, subscription.getLastReadItem());
		Assert.assertEquals(theObject, subscription.getObject());
	}

	@Test
	public void createWith0Test() {
		final Feed theFeed = Factories.FEED.find(1);
		final VObject theObject = Factories.VOBJECT.find(1);
		final FeedItem item = Factories.FEED_ITEM.find(4);

		final FeedSubscription subscription = Factories.FEED_SUBSCRIPTION.create(theFeed, theObject, 0);
		Assert.assertNotNull(subscription);
		Assert.assertEquals(theFeed, subscription.getFeed());
		Assert.assertEquals(item, subscription.getLastReadItem());
		Assert.assertEquals(theObject, subscription.getObject());
	}

	@Test
	public void createWithRefTest() {
		final Feed theFeed = Factories.FEED.find(1);
		final VObject theObject = Factories.VOBJECT.find(1);
		final FeedItem item = Factories.FEED_ITEM.find(2);

		final FeedSubscription subscription = Factories.FEED_SUBSCRIPTION.create(theFeed, theObject, 2);
		Assert.assertNotNull(subscription);
		Assert.assertEquals(theFeed, subscription.getFeed());
		Assert.assertEquals(item, subscription.getLastReadItem());
		Assert.assertEquals(theObject, subscription.getObject());
	}

	@Test
	public void createWithRefAndALotOfNewsTest() {
		final Feed theFeed = Factories.FEED.find(1);
		final VObject theObject = Factories.VOBJECT.find(1);

		final FeedSubscription subscription = Factories.FEED_SUBSCRIPTION.create(theFeed, theObject, 10);
		Assert.assertNotNull(subscription);
		Assert.assertEquals(theFeed, subscription.getFeed());
		Assert.assertNull(subscription.getLastReadItem());
		Assert.assertEquals(theObject, subscription.getObject());
	}

	@Test
	public void findByObjectAndFeedTest() {
		final Feed theFeed = Factories.FEED.find(2);
		final VObject theObject = Factories.VOBJECT.find(1);
		final FeedSubscription foundSubscription = Factories.FEED_SUBSCRIPTION.findByObjectAndFeed(theObject, theFeed);
		Assert.assertEquals(Long.valueOf(1), foundSubscription.getId());
		Assert.assertEquals(theFeed, foundSubscription.getFeed());
		Assert.assertEquals(theObject, foundSubscription.getObject());
		Assert.assertNull(foundSubscription.getLastReadItem());
	}

	@Test
	public void findAllByFeedTest() {
		final Feed theFeed = Factories.FEED.find(3);

		final List<FeedSubscription> foundSubscriptions = Factories.FEED_SUBSCRIPTION.findAllByFeed(theFeed);
		Assert.assertEquals(3, foundSubscriptions.size());

		Assert.assertTrue(foundSubscriptions.contains(Factories.FEED_SUBSCRIPTION.find(2)));
		Assert.assertTrue(foundSubscriptions.contains(Factories.FEED_SUBSCRIPTION.find(3)));
		Assert.assertTrue(foundSubscriptions.contains(Factories.FEED_SUBSCRIPTION.find(4)));
	}

	@Test
	public void updateWithNegativeSubscriptionTest() {
		final FeedSubscription theSubscription = Factories.FEED_SUBSCRIPTION.find(5);
		final Feed theFeed = Factories.FEED.find(1);
		final FeedItem theRefItem = Factories.FEED_ITEM.find(1);

		theSubscription.updateFeed(theFeed, -1);
		Assert.assertEquals(theFeed, theSubscription.getFeed());
		Assert.assertEquals(theRefItem, theSubscription.getLastReadItem());
	}

	@Test
	public void updateWithValueSubscriptionTest() {
		final FeedSubscription theSubscription = Factories.FEED_SUBSCRIPTION.find(5);
		final Feed theFeed = Factories.FEED.find(1);
		final FeedItem theRefItem = Factories.FEED_ITEM.find(2);

		theSubscription.updateFeed(theFeed, 2);
		Assert.assertEquals(theFeed, theSubscription.getFeed());
		Assert.assertEquals(theRefItem, theSubscription.getLastReadItem());
	}

	@Test
	public void updateWithZeroSubscriptionTest() {
		final FeedSubscription theSubscription = Factories.FEED_SUBSCRIPTION.find(5);
		final Feed theFeed = Factories.FEED.find(1);
		final FeedItem theRefItem = Factories.FEED_ITEM.find(4);

		theSubscription.updateFeed(theFeed, 0);
		Assert.assertEquals(theFeed, theSubscription.getFeed());
		Assert.assertEquals(theRefItem, theSubscription.getLastReadItem());
	}
}
