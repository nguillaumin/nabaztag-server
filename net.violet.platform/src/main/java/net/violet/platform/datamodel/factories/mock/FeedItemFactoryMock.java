package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.FeedItemFactory;
import net.violet.platform.datamodel.mock.FeedItemMock;

public class FeedItemFactoryMock extends RecordFactoryMock<FeedItem, FeedItemMock> implements FeedItemFactory {

	FeedItemFactoryMock() {
		super(FeedItemMock.class);
	}

	public FeedItem create(Feed feed, List<Files> contents, String title, String link, String uri) {
		return new FeedItemMock(feed.getId(), contents, title, link, uri);
	}

	public List<FeedItem> findAllByFeed(Feed feed) {
		return findAllByFeed(feed, 0);
	}

	public List<FeedItem> findAllByFeed(Feed feed, int maxItemsAmount) {
		List<FeedItem> items = new ArrayList<FeedItem>();
		for (final FeedItem anItem : findAll()) {
			if (anItem.getFeed().equals(feed)) {
				items.add(anItem);
			}
		}

		Collections.sort(items, new Comparator<FeedItem>() {

			public int compare(FeedItem o1, FeedItem o2) {
				return (int) (o1.getId() - o2.getId());
			}
		});

		if ((maxItemsAmount > 0) && (items.size() > maxItemsAmount)) {
			items = items.subList(items.size() - maxItemsAmount, items.size());
		}

		return items;
	}

	public boolean usesFiles(Files inFile) {
		for (final FeedItem item : findAll()) {
			if (item.getContents().contains(inFile)) {
				return true;
			}
		}

		return false;
	}

	public List<FeedItem> findAllNewer(FeedItem refItem) {
		return findAllNewer(refItem, 0);
	}

	public List<FeedItem> findAllNewer(FeedItem refItem, int maxItemsAmount) {
		final List<FeedItem> theNewers = new ArrayList<FeedItem>();
		for (final FeedItem f : findAllByFeed(refItem.getFeed(), maxItemsAmount)) {
			if (f.getId() > refItem.getId()) {
				theNewers.add(f);
			}
		}
		return theNewers;
	}

}
