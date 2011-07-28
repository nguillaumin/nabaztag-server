package net.violet.platform.feeds.crawlers;

import net.violet.platform.datamodel.Feed;
import net.violet.platform.feeds.processors.EntryProcessor;
import net.violet.platform.feeds.processors.OnlyTitleProcessor;

public class RssFreeCrawler extends RssPodcastCrawler {

	public RssFreeCrawler(String[] inArgs) {
		super(inArgs, Feed.Type.RSS, Feed.AccessRight.FREE, 30);
	}

	@Override
	protected EntryProcessor getEntryProcessor(Feed feed) {
		return OnlyTitleProcessor.getProcessor(feed.getLanguage());
	}

}
