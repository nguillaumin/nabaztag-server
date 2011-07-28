package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedSubscription;
import net.violet.platform.datamodel.VObject;

public interface FeedSubscriptionFactory extends RecordFactory<FeedSubscription> {

	FeedSubscription findByObjectAndFeed(VObject object, Feed feed);

	/**
	 * Creates a new FeedSubscription and computes the lastReadItem according to the given news amount.
	 * 
	 * If the amount is 0 the most recent item will be the ref item : this subscription does not have any news.
	 * If the amount is negative it is ignored and the ref item is the eldest known.
	 * 
	 * @param feed
	 * @param object
	 * @param nbNews
	 * @return
	 */
	FeedSubscription create(Feed feed, VObject object, int newsAmount);

	List<FeedSubscription> findAllByFeed(Feed feed);

}
