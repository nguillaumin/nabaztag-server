package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.List;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.util.UpdateMap;

public class FeedSubscriptionImpl extends ObjectRecord<FeedSubscription, FeedSubscriptionImpl> implements FeedSubscription {

	public static final SQLObjectSpecification<FeedSubscriptionImpl> SPECIFICATION = new SQLObjectSpecification<FeedSubscriptionImpl>("feed_subscriptions", FeedSubscriptionImpl.class, new SQLKey[] { new SQLKey("id") });

	private static final String[] NEW_COLUMNS = new String[] { "feed_id", "object_id", "last_read_item" };

	protected long id;
	protected long feed_id;
	protected long object_id;
	protected Long last_read_item;

	private final SingleAssociationNotNull<FeedSubscription, Feed, FeedImpl> feed;
	private final SingleAssociationNotNull<FeedSubscription, VObject, VObjectImpl> object;
	private final SingleAssociationNull<FeedSubscription, FeedItem, FeedItemImpl> lastReadItem;

	protected FeedSubscriptionImpl(long id) throws SQLException {
		init(id);
		this.feed = new SingleAssociationNotNull<FeedSubscription, Feed, FeedImpl>(this, "feed_id", FeedImpl.SPECIFICATION);
		this.object = new SingleAssociationNotNull<FeedSubscription, VObject, VObjectImpl>(this, "object_id", VObjectImpl.SPECIFICATION);
		this.lastReadItem = new SingleAssociationNull<FeedSubscription, FeedItem, FeedItemImpl>(this, "last_read_item", FeedItemImpl.SPECIFICATION);
	}

	protected FeedSubscriptionImpl() {
		this.feed = new SingleAssociationNotNull<FeedSubscription, Feed, FeedImpl>(this, "feed_id", FeedImpl.SPECIFICATION);
		this.object = new SingleAssociationNotNull<FeedSubscription, VObject, VObjectImpl>(this, "object_id", VObjectImpl.SPECIFICATION);
		this.lastReadItem = new SingleAssociationNull<FeedSubscription, FeedItem, FeedItemImpl>(this, "last_read_item", FeedItemImpl.SPECIFICATION);
	}

	public FeedSubscriptionImpl(Feed feed, VObject object, FeedItem refItem) throws SQLException {
		this.feed_id = feed.getId();
		this.object_id = object.getId();
		this.last_read_item = refItem == null ? null : refItem.getId();

		init(FeedSubscriptionImpl.NEW_COLUMNS);

		this.feed = new SingleAssociationNotNull<FeedSubscription, Feed, FeedImpl>(this, "feed_id", FeedImpl.SPECIFICATION);
		this.object = new SingleAssociationNotNull<FeedSubscription, VObject, VObjectImpl>(this, "object_id", VObjectImpl.SPECIFICATION);
		this.lastReadItem = new SingleAssociationNull<FeedSubscription, FeedItem, FeedItemImpl>(this, "last_read_item", FeedItemImpl.SPECIFICATION);
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public SQLObjectSpecification<FeedSubscriptionImpl> getSpecification() {
		return FeedSubscriptionImpl.SPECIFICATION;
	}

	public Feed getFeed() {
		return this.feed.get(this.feed_id);
	}

	public FeedItem getLastReadItem() {
		return this.lastReadItem.get(this.last_read_item);
	}

	public VObject getObject() {
		return this.object.get(this.object_id);
	}

	public void updateFeed(Feed newFeed, int newsAmount) {
		if (getFeed().equals(newFeed)) {
			return;
		}

		final List<FeedItem> theNewestItems = Factories.FEED_ITEM.findAllByFeed(newFeed, newsAmount + 1);
		final FeedItem newRefItem = theNewestItems.isEmpty() ? null : theNewestItems.get(0);

		final UpdateMap map = new UpdateMap();
		this.feed_id = map.updateField("feed_id", this.feed_id, newFeed.getId());
		this.last_read_item = map.updateField("last_read_item", this.last_read_item, newRefItem == null ? null : newRefItem.getId());
		update(map);
	}

	public void setLastReadItem(FeedItem item) {
		final UpdateMap map = new UpdateMap();
		this.last_read_item = map.updateField("last_read_item", this.last_read_item, item == null ? null : item.getId());
		update(map);
	}

}
