package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface FeedItemFactory extends RecordFactory<FeedItem>, FilesAccessor {

	FeedItem create(Feed feed, List<Files> contents, String title, String link, String uri);

	/**
	 * The returned list is sorted from the eldest to the youngest item.
	 * @param feed
	 * @return
	 */
	List<FeedItem> findAllByFeed(Feed feed);

	/**
	 * The returned list is sorted fromt the eldest to the youngest item and does not contain more than maxItemsAmount items.
	 * 
	 * If maxItemsAmount is lower than or equal to 0 it is ignored.
	 * 
	 * @param feed
	 * @param maxItemsAmount
	 * @return
	 */
	List<FeedItem> findAllByFeed(Feed feed, int maxItemsAmount);

	/**
	 * Returns all the FeedItem objects which belong to the given item's feed and are more recent than it.
	 * 
	 * The given item MUST NOT be null !
	 * 
	 * The returned list is sorted from the eldest item to the newest one.
	 * 
	 * @param refItem
	 * @return
	 */
	List<FeedItem> findAllNewer(FeedItem refItem);

	List<FeedItem> findAllNewer(FeedItem refItem, int maxItemsAmount);

}
