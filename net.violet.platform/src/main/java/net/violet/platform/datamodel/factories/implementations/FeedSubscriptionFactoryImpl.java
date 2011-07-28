package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.FeedSubscription;
import net.violet.platform.datamodel.FeedSubscriptionImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.FeedSubscriptionFactory;

import org.apache.log4j.Logger;

public class FeedSubscriptionFactoryImpl extends RecordFactoryImpl<FeedSubscription, FeedSubscriptionImpl> implements FeedSubscriptionFactory {

	private static final Logger LOGGER = Logger.getLogger(FeedSubscriptionFactoryImpl.class);

	public FeedSubscriptionFactoryImpl() {
		super(FeedSubscriptionImpl.SPECIFICATION);
	}

	public FeedSubscription create(Feed feed, VObject object, int nbNews) {
		final List<FeedItem> newestItemsInFeed = Factories.FEED_ITEM.findAllByFeed(feed, nbNews + 1);
		final FeedItem refItem = newestItemsInFeed.isEmpty() || (newestItemsInFeed.size() < nbNews + 1) ? null : newestItemsInFeed.get(0);

		try {
			return new FeedSubscriptionImpl(feed, object, refItem);
		} catch (final SQLException e) {
			FeedSubscriptionFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	public FeedSubscription findByObjectAndFeed(VObject object, Feed feed) {
		return find("object_id = ? and feed_id = ? ", Arrays.asList(new Object[] { object.getId(), feed.getId() }));
	}

	public List<FeedSubscription> findAllByFeed(Feed feed) {
		return findAll(" feed_id = ? ", Collections.<Object> singletonList(feed.getId()));
	}

}
