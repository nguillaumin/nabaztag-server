package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;

import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Feed.AccessRight;
import net.violet.platform.datamodel.Feed.Type;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class FeedData extends RecordData<Feed> {

	private static final Logger LOGGER = Logger.getLogger(FeedData.class);

	public static FeedData getData(Feed Feed) {
		try {
			return RecordData.getData(Feed, FeedData.class, Feed.class);
		} catch (final InstantiationException e) {
			FeedData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			FeedData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			FeedData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			FeedData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected FeedData(Feed inRecord) {
		super(inRecord);
	}

	public Feed getReference() {
		return getRecord();
	}

	/**
	 * This method returns the feed object identified by the given url and language.
	 * If the feed does not exist it is created as Full feed and returned.
	 * This method only returns null if an error happend (we failed creating the feed).
	 * @param url
	 * @param language
	 * @return
	 */
	public static FeedData getFeed(String url, TtsLangData language, Feed.Type type) {
		return FeedData.getFeed(url, language, type, Feed.AccessRight.FULL);
	}

	/**
	 * This method returns the feed object identified by the given url and language.
	 * This method only returns null if an error happend (we failed creating the feed).
	 * @param url
	 * @param language
	 * @return
	 */
	public static FeedData getFeed(String url, TtsLangData language, Feed.Type type, Feed.AccessRight accessRight) {
		Feed theFeed = Factories.FEED.findByUrlAndType(url, type);
		if (theFeed == null) {
			theFeed = Factories.FEED.create(url, type, language.getRecord(), accessRight);
			if (theFeed == null) {
				return null;
			}
		}

		return FeedData.getData(theFeed);
	}

	public static FeedData findByUrlAndType(String url, Type type) {
		final Feed theFeed = Factories.FEED.findByUrlAndType(url, type);
		return theFeed == null ? null : FeedData.getData(theFeed);
	}

	public String getUrl() {
		return getRecord().getUrl();
	}

	public TtsLangData getLanguage() {
		return TtsLangData.findByISOCode(getRecord().getLanguage().getIsoCode());
	}

	/**
	 * Adds an item to the given feed. The new item is created using the given information.
	 * The maxItemsAmount parameter is used to remove the eldest entries if this amount has been reached.
	 * @param contents
	 * @param title
	 * @param link
	 * @param uri
	 * @param maxItemsAmount
	 */
	public FeedItemData addItem(List<Files> contents, String title, String link, String uri, int maxItemsAmount) {
		final FeedItemData theItem = FeedItemData.create(this, contents, title, link, uri);;
		final List<FeedItemData> items = FeedItemData.findAllByFeed(this);

		//the maximum amount has been crossed
		if ((items.size() > maxItemsAmount) && (maxItemsAmount > 0)) {
			int itemsToDelete = items.size() - maxItemsAmount;
			while (itemsToDelete > 0) {
				items.remove(0).delete();
				itemsToDelete--;
			}
		}

		return theItem;

	}

	public Timestamp getLastModified() {
		return getRecord().getLastModified();
	}

	public String getEtag() {
		return getRecord().getETag();
	}

	public static FeedData findById(long id) {
		final Feed feed = Factories.FEED.find(id);
		return feed == null ? null : FeedData.getData(feed);
	}

	public Long getId() {
		return getRecord().getId();
	}

	@Override
	protected boolean doDelete() {
		for (final FeedItemData item : FeedItemData.findAllByFeed(this)) {
			item.delete();
		}

		return super.doDelete();
	}

	/**
	 * Returns true if the feed is full and orphan
	 * @return
	 */
	public boolean shouldBeDeleted() {
		return (getRecord().getAccessRight() == AccessRight.FULL) && Factories.FEED_SUBSCRIPTION.findAllByFeed(getRecord()).isEmpty();
	}

	public Type getType() {
		return getRecord().getType();
	}

}
