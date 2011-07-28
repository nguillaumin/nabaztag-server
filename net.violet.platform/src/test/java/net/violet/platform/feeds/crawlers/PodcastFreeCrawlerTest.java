package net.violet.platform.feeds.crawlers;

import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.FeedItemData;
import net.violet.platform.dataobjects.TtsLangData;

import org.junit.Assert;
import org.junit.Test;

public class PodcastFreeCrawlerTest extends MockTestBase {

	private static final String PATH_2_FILE = "http://192.168.1.11/tests_silence/podcast_free_sample.xml";

	@Test
	public void aNewFeedTest() {

		final FeedData theNewFeed = FeedData.getFeed(PodcastFreeCrawlerTest.PATH_2_FILE, TtsLangData.findByISOCode("fr-FR"), Feed.Type.PODCAST);

		final PodcastFreeCrawler crawler = new PodcastFreeCrawler(" -a 1 -c 1".split(" "));

		crawler.new FeedWorker().process(theNewFeed.getReference());

		final List<FeedItemData> items = FeedItemData.findAllByFeed(theNewFeed);
		Assert.assertEquals(1, items.size());
		final FeedItemData theItem = items.get(0);

		Assert.assertEquals("Les Inrocks Podcast vol. 81 ", theItem.getTitle());
		Assert.assertEquals("http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_81.mp3", theItem.getUri());
		Assert.assertEquals("http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_81.mp3", theItem.getLink());
	}

	@Test
	public void aYoungFeedTest() {

		final FeedData theNewFeed = FeedData.getFeed(PodcastFreeCrawlerTest.PATH_2_FILE, TtsLangData.findByISOCode("fr-FR"), Feed.Type.PODCAST);
		theNewFeed.addItem(Collections.<Files> emptyList(), "Les Inrocks Podcast vol. 78 ", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_78.mp3", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_78.mp3", 1);
		final List<FeedItemData> existingItems = FeedItemData.findAllByFeed(theNewFeed);
		Assert.assertEquals(1, existingItems.size());

		final PodcastFreeCrawler crawler = new PodcastFreeCrawler(" -a 1 -c 1".split(" "));

		crawler.new FeedWorker().process(theNewFeed.getReference());

		final List<FeedItemData> items = FeedItemData.findAllByFeed(theNewFeed);
		Assert.assertEquals(1, items.size());
		final FeedItemData theItem = items.get(0);
		Assert.assertFalse(existingItems.get(0).equals(theItem));

		Assert.assertEquals("Les Inrocks Podcast vol. 81 ", theItem.getTitle());
		Assert.assertEquals("http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_81.mp3", theItem.getUri());
		Assert.assertEquals("http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_81.mp3", theItem.getLink());
	}

	@Test
	public void anOldFeedTest() {

		final FeedData theNewFeed = FeedData.getFeed(PodcastFreeCrawlerTest.PATH_2_FILE, TtsLangData.findByISOCode("fr-FR"), Feed.Type.PODCAST);
		theNewFeed.addItem(Collections.<Files> emptyList(), "Les Inrocks Podcast vol. 81 ", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_81.mp3", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_81.mp3", 1);
		final List<FeedItemData> existingItems = FeedItemData.findAllByFeed(theNewFeed);
		Assert.assertEquals(1, existingItems.size());

		final PodcastFreeCrawler crawler = new PodcastFreeCrawler(" -a 1 -c 1".split(" "));

		crawler.new FeedWorker().process(theNewFeed.getReference());

		final List<FeedItemData> items = FeedItemData.findAllByFeed(theNewFeed);
		Assert.assertEquals(1, items.size());
		Assert.assertEquals(existingItems.get(0), items.get(0));
	}

}
