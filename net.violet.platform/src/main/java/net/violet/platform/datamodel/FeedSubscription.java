package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface FeedSubscription extends Record<FeedSubscription> {

	Feed getFeed();

	VObject getObject();

	/**
	 * The returned FeedItem may be null !
	 * @return
	 */
	FeedItem getLastReadItem();

	/**
	 * null is a valid value for the given item
	 * @param item
	 */
	void setLastReadItem(FeedItem item);

	/**
	 * Changes the feed associated to this subscription. The last read item is automatically computed according to the given
	 * newsAmount (of course, the new last read item may be null) 
	 */
	void updateFeed(Feed feed, int newsAmount);

}
