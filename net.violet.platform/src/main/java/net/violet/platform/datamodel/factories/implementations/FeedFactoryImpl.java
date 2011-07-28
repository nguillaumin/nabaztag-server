package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Feed.AccessRight;
import net.violet.platform.datamodel.Feed.Type;
import net.violet.platform.datamodel.factories.FeedFactory;

import org.apache.log4j.Logger;

public class FeedFactoryImpl extends RecordFactoryImpl<Feed, FeedImpl> implements FeedFactory {

	private static final Logger LOGGER = Logger.getLogger(FeedFactoryImpl.class);

	public FeedFactoryImpl() {
		super(FeedImpl.SPECIFICATION);
	}

	public Feed create(String url, Feed.Type type, Lang language, Feed.AccessRight accessRight) {
		try {
			return new FeedImpl(url, language, type, accessRight);
		} catch (final SQLException e) {
			FeedFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	public Feed findByUrlAndType(String url, Feed.Type type) {
		return find("url = ? AND type = ? ", Arrays.asList(new Object[] { url, type.toString() }));
	}

	public int walkByTypeAndAccessRight(Type type, AccessRight accessRight, RecordWalker<Feed> walker) {
		return walk("feeds.type = ? AND feeds.access_right = ?", Arrays.asList(new Object[] { type.toString(), accessRight.toString() }), null, 0, walker);
	}

}
