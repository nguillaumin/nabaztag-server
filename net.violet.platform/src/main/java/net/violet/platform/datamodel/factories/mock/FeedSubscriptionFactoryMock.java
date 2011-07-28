package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.FeedSubscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.FeedSubscriptionFactory;
import net.violet.platform.datamodel.mock.FeedSubscriptionMock;

public class FeedSubscriptionFactoryMock extends RecordFactoryMock<FeedSubscription, FeedSubscriptionMock> implements FeedSubscriptionFactory {

	FeedSubscriptionFactoryMock() {
		super(FeedSubscriptionMock.class);
	}

	public FeedSubscription create(Feed feed, VObject object, int nbNews) {
		final List<FeedItem> newestItemsInFeed = Factories.FEED_ITEM.findAllByFeed(feed, nbNews + 1);
		final FeedItem refItem = newestItemsInFeed.isEmpty() || (newestItemsInFeed.size() < nbNews + 1) ? null : newestItemsInFeed.get(0);

		return new FeedSubscriptionMock(feed, object, refItem);
	}

	public FeedSubscription findByObjectAndFeed(VObject object, Feed feed) {
		for (final FeedSubscription aSubscription : findAll()) {
			if (aSubscription.getObject().equals(object) && aSubscription.getFeed().equals(feed)) {
				return aSubscription;
			}
		}
		return null;
	}

	public List<FeedSubscription> findAllByFeed(Feed record) {
		final List<FeedSubscription> subscriptions = new ArrayList<FeedSubscription>();
		for (final FeedSubscription aS : findAll()) {
			if (aS.getFeed().equals(record)) {
				subscriptions.add(aS);
			}
		}
		return subscriptions;
	}

}
