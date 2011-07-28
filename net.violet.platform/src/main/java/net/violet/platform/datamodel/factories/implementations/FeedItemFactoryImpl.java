package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.FeedItemImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.FeedItemFactory;

import org.apache.log4j.Logger;

public class FeedItemFactoryImpl extends RecordFactoryImpl<FeedItem, FeedItemImpl> implements FeedItemFactory {

	private static final Logger LOGGER = Logger.getLogger(FeedItemFactoryImpl.class);

	public FeedItemFactoryImpl() {
		super(FeedItemImpl.SPECIFICATION);
	}

	public FeedItem create(Feed feed, List<Files> contents, String title, String link, String uri) {
		try {
			return new FeedItemImpl(feed, contents, title, link, uri);
		} catch (final SQLException e) {
			FeedItemFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public List<FeedItem> findAllByFeed(Feed feed) {
		return findAllByFeed(feed, 0);
	}

	public List<FeedItem> findAllByFeed(Feed feed, int maxItemsAmount) {
		final List<FeedItem> theNewestItems = findAll("feed_id = ?", Collections.<Object> singletonList(feed.getId()), " id DESC", 0, maxItemsAmount);
		Collections.reverse(theNewestItems);
		return theNewestItems;
	}

	public boolean usesFiles(final Files inFile) {
/*		final AtomicInteger amount = new AtomicInteger(0);
		walk(" id > 0 ", Collections.emptyList(), null, 0, new RecordWalker<FeedItem>() {

			public void process(FeedItem item) {
				if (item.getContents().contains(inFile)) {
					amount.incrementAndGet();
				}
			}

		});

		return amount.get() > 0;*/
               final String theValue = "[" + inFile.getId() + "]"; //special case, in order to accelerate purge process
               return count(null, " contents = ? ", Collections.singletonList((Object) theValue), null) > 0;


	}

	public List<FeedItem> findAllNewer(FeedItem refItem) {
		return findAllNewer(refItem, 0);
	}

	public List<FeedItem> findAllNewer(FeedItem refItem, int maxItemsAmount) {
		final List<FeedItem> newestItems = findAll("id > ? AND feed_id = ? ", Arrays.asList(new Object[] { refItem.getId(), refItem.getFeed().getId() }), "id DESC", 0, maxItemsAmount);
		Collections.reverse(newestItems);
		return newestItems;
	}

}
