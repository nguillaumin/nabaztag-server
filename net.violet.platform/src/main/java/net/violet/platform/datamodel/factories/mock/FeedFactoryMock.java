package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Feed.Type;
import net.violet.platform.datamodel.factories.FeedFactory;
import net.violet.platform.datamodel.mock.FeedMock;

public class FeedFactoryMock extends RecordFactoryMock<Feed, FeedMock> implements FeedFactory {

	FeedFactoryMock() {
		super(FeedMock.class);
	}

	public Feed create(String url, Feed.Type type, Lang language, Feed.AccessRight accessRight) {
		return new FeedMock(url, language, type, accessRight);
	}

	public Feed findByUrlAndType(String url, Feed.Type type) {
		for (final Feed aFeed : findAll()) {
			if (aFeed.getUrl().equals(url) && (aFeed.getType() == type)) {
				return aFeed;
			}
		}

		return null;
	}

	public int walkByTypeAndAccessRight(Type type, Feed.AccessRight accessRight, RecordWalker<Feed> walker) {
		int amount = 0;

		for (final Feed aFeed : findAll()) {
			if ((aFeed.getType() == type) && (aFeed.getAccessRight() == accessRight)) {
				walker.process(aFeed);
				amount++;
			}
		}

		return amount;
	}

}
