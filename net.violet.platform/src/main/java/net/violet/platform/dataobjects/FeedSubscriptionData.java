package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;

import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.FeedSubscription;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class FeedSubscriptionData extends RecordData<FeedSubscription> {

	private static final Logger LOGGER = Logger.getLogger(FeedSubscriptionData.class);

	public static FeedSubscriptionData getData(FeedSubscription FeedSubscription) {
		try {
			return RecordData.getData(FeedSubscription, FeedSubscriptionData.class, FeedSubscription.class);
		} catch (final InstantiationException e) {
			FeedSubscriptionData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			FeedSubscriptionData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			FeedSubscriptionData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			FeedSubscriptionData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected FeedSubscriptionData(FeedSubscription inRecord) {
		super(inRecord);
	}

	public static FeedSubscriptionData findByObjectAndFeed(VObjectData object, FeedData feed) {
		final FeedSubscription subscription = Factories.FEED_SUBSCRIPTION.findByObjectAndFeed(object.getRecord(), feed.getRecord());
		return subscription == null ? null : FeedSubscriptionData.getData(subscription);
	}

	public static FeedSubscriptionData create(FeedData feed, VObjectData object, int nbNews) {
		final FeedSubscription subscription = Factories.FEED_SUBSCRIPTION.create(feed.getRecord(), object.getRecord(), nbNews);
		return FeedSubscriptionData.getData(subscription);
	}

	public FeedData getFeed() {
		return FeedData.getData(getRecord().getFeed());
	}

	/**
	 * Updates the FeedSubscription with the given elements. if the given feedData already is the feed the subscription is linked to then
	 * NOTHING is done! The nbNews parameter is totally ignored. Only use this method to change the feed linked to the susbcription,
	 * the nbNews parameter is just given as an information to set the 'last read item' if the given feed is different.
	 * @param newFeed
	 * @param nbNews
	 */
	public void updateFeedSubscription(FeedData newFeed, int nbNews) {
		final FeedData currentFeed = getFeed();
		if (currentFeed.equals(newFeed)) {
			return;
		}

		getRecord().updateFeed(newFeed.getRecord(), nbNews);

		if (currentFeed.shouldBeDeleted()) {
			currentFeed.delete();
		}

	}

	public void setLastReadItem(FeedItemData item) {
		getRecord().setLastReadItem(item != null ? item.getRecord() : null);
	}

	public FeedItemData getLastReadItem() {
		final FeedItem item = getRecord().getLastReadItem();
		return item == null ? null : FeedItemData.getData(item);
	}

}
