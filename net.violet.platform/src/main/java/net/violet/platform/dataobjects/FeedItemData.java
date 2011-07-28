package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class FeedItemData extends RecordData<FeedItem> {

	private static final Logger LOGGER = Logger.getLogger(FeedItemData.class);

	public static FeedItemData getData(FeedItem feedItem) {
		try {
			return RecordData.getData(feedItem, FeedItemData.class, FeedItem.class);
		} catch (final InstantiationException e) {
			FeedItemData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			FeedItemData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			FeedItemData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			FeedItemData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected FeedItemData(FeedItem inRecord) {
		super(inRecord);
	}

	/**
	 * Package visibility : the good way to call this method is from the FeedData class where the 'items maximum amount' mechanism takes place.
	 * @param feed
	 * @param contents
	 * @param title
	 * @param link
	 * @param uri
	 * @return
	 */
	static FeedItemData create(FeedData feed, List<Files> contents, String title, String link, String uri) {
		final FeedItem item = Factories.FEED_ITEM.create(feed.getRecord(), contents, title, link, uri);
		if (item == null) {
			return null;
		}

		return FeedItemData.getData(item);
	}

	/**
	 * Returns the existing entries for the given feed. The returned items are sorted : the eldest is the first one, the 
	 * newest is the last one.
	 * @param feedData
	 * @return
	 */
	public static List<FeedItemData> findAllByFeed(FeedData feedData) {
		return FeedItemData.findAllByFeed(feedData, 0);
	}

	/**
	 * The returned list is sorted : the eldest item is the first one, the newest is the last one
	 * @param feed
	 * @param maxNewsAmount
	 * @return
	 */
	public static List<FeedItemData> findAllByFeed(FeedData feed, int maxNewsAmount) {
		return FeedItemData.generateList(Factories.FEED_ITEM.findAllByFeed(feed.getRecord(), maxNewsAmount));
	}

	/**
	 * Returns the unread feed items for the given feed subscription. The items are returned sorted, from the eldest to 
	 * the newest.
	 * @param feedSubscription
	 * @param maxNbnews 
	 * @return
	 */
	public static List<FeedItemData> findUnreadForSubscription(FeedSubscriptionData feedSubscription, int maxNbNews) {
		List<FeedItem> items;
		if (feedSubscription.getRecord().getLastReadItem() != null) {
			items = Factories.FEED_ITEM.findAllNewer(feedSubscription.getRecord().getLastReadItem(), maxNbNews);
		} else {
			items = Factories.FEED_ITEM.findAllByFeed(feedSubscription.getRecord().getFeed(), maxNbNews);
		}

		return FeedItemData.generateList(items);
	}

	private static List<FeedItemData> generateList(List<FeedItem> list) {
		final List<FeedItemData> theList = new ArrayList<FeedItemData>();
		for (final FeedItem anItem : list) {
			theList.add(FeedItemData.getData(anItem));
		}
		return theList;
	}

	public List<Files> getContents() {
		return getRecord().getContents();
	}

	public String getTitle() {
		return getRecord().getTitle();
	}

	public String getUri() {
		return getRecord().getUri();
	}

	public String getLink() {
		return getRecord().getLink();
	}

	@Override
	protected boolean doDelete() {
		for (final Files content : getContents()) {
			content.scheduleDeletion();
		}
		return super.doDelete();
	}

}
