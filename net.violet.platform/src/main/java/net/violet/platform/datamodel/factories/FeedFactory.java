package net.violet.platform.datamodel.factories;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Feed.AccessRight;
import net.violet.platform.datamodel.Feed.Type;

public interface FeedFactory extends RecordFactory<Feed> {

	Feed findByUrlAndType(String url, Feed.Type type);

	Feed create(String url, Feed.Type type, Lang language, Feed.AccessRight accessRight);

	int walkByTypeAndAccessRight(Type type, AccessRight accessRight, RecordWalker<Feed> walker);

}
