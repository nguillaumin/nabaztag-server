package net.violet.platform.feeds.crawlers;

import net.violet.platform.datamodel.Feed;
import net.violet.platform.feeds.processors.DownloadingProcessor;
import net.violet.platform.feeds.processors.EntryProcessor;

public class PodcastFreeCrawler extends RssPodcastCrawler {

	public PodcastFreeCrawler(String[] inArgs) {
		super(inArgs, Feed.Type.PODCAST, Feed.AccessRight.FREE, 1);
	}

	@Override
	protected EntryProcessor getEntryProcessor(Feed feed) {
		return new DownloadingProcessor(feed.getAccessRight() == Feed.AccessRight.FREE);
	}

}
