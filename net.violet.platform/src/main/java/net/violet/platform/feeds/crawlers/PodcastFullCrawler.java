package net.violet.platform.feeds.crawlers;

import net.violet.platform.datamodel.Feed;
import net.violet.platform.feeds.processors.DownloadingProcessor;
import net.violet.platform.feeds.processors.EntryProcessor;

public class PodcastFullCrawler extends RssPodcastCrawler {

	public PodcastFullCrawler(String[] inArgs) {
		super(inArgs, Feed.Type.PODCAST, Feed.AccessRight.FULL, 1);
	}

	@Override
	protected EntryProcessor getEntryProcessor(Feed feed) {
		return new DownloadingProcessor(feed.getAccessRight() == Feed.AccessRight.FREE);
	}

}
