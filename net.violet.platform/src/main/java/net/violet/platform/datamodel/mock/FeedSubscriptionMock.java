package net.violet.platform.datamodel.mock;

import java.util.List;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.FeedSubscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;

public class FeedSubscriptionMock extends AbstractMockRecord<FeedSubscription, FeedSubscriptionMock> implements FeedSubscription {

	private long feedId;
	private final long objectId;
	private Long lastReadItemId;

	public FeedSubscriptionMock(long feedId, long objectId, Long lastItemId) {
		super(0);
		this.feedId = feedId;
		this.objectId = objectId;
		this.lastReadItemId = lastItemId;
	}

	public FeedSubscriptionMock(Feed feed, VObject object, Long lastItemId) {
		this(feed.getId(), object.getId(), lastItemId);
	}

	public FeedSubscriptionMock(Feed feed, VObject object, FeedItem refItem) {
		this(feed.getId(), object.getId(), refItem == null ? null : refItem.getId());
	}

	public Feed getFeed() {
		return Factories.FEED.find(this.feedId);
	}

	public FeedItem getLastReadItem() {
		if (this.lastReadItemId == null) {
			return null;
		}

		return Factories.FEED_ITEM.find(this.lastReadItemId);
	}

	public VObject getObject() {
		return Factories.VOBJECT.find(this.objectId);
	}

	public void updateFeed(Feed newFeed, int newsAmount) {
		if (getFeed().equals(newFeed)) {
			return;
		}

		final List<FeedItem> theNewestItems = Factories.FEED_ITEM.findAllByFeed(newFeed, newsAmount + 1);
		final FeedItem newRefItem = theNewestItems.isEmpty() ? null : theNewestItems.get(0);

		this.feedId = newFeed.getId();
		this.lastReadItemId = newRefItem == null ? null : newRefItem.getId();
	}

	public void setLastReadItem(FeedItem item) {
		this.lastReadItemId = item == null ? null : item.getId();
	}

}
