package net.violet.platform.daemons.crawlers.vaction;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import net.violet.platform.daemons.crawlers.feedHandler.ConnectionHandler.ConnectionSettings;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.datamodel.mock.ContentMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.VActionMock;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.concurrent.units.AbstractCrawlerProcessUnit;

import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class AbstractVActionCrawlerTest extends MockTestBase {

	private static final String PATH_2_FILES = "test/net/violet/platform/daemons/crawlers/vaction/";

	private static final AbstractFeedCrawler<AbstractCrawlerProcessUnit> THE_GENERIC_INSTANCE = new AbstractFeedCrawler<AbstractCrawlerProcessUnit>() {

		@Override
		protected void doProcess() {
		}

		@Override
		protected List<Content> getMostRecents(net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler.FeedUnit inFeedUnit) {
			return null;
		}

		@Override
		protected int getNbItems2Read() {
			return 0;
		}

		@Override
		protected void updateConnectionSettingAndIdXMl(net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler.FeedUnit inUnit, ConnectionSettings inSettings, String inIdXml) {
		}

		public AbstractCrawlerProcessUnit getProcessUnit(String inTitle, String inLink, String inUri, Lang inLang, Integer inTTL) {
			return null;
		}

		public Service getService() {
			return null;
		}

		public void processNews(List<Files> inNewsFiles, List<AbstractCrawlerProcessUnit> inNews, MessageSignature inMessageSignature, String inTitle, net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler.FeedUnit inFeedUnit) {
		}

		public void processUnit(AbstractCrawlerProcessUnit inUnit) {
		}

	};

	private static final AbstractFeedCrawler<AbstractCrawlerProcessUnit> THE_PODCAST_INSTANCE = new AbstractFeedCrawler<AbstractCrawlerProcessUnit>() {

		@Override
		protected void doProcess() {
		}

		@Override
		protected List<Content> getMostRecents(net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler.FeedUnit inFeedUnit) {
			return null;
		}

		@Override
		protected int getNbItems2Read() {
			return 0;
		}

		@Override
		protected void updateConnectionSettingAndIdXMl(net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler.FeedUnit inUnit, ConnectionSettings inSettings, String inIdXml) {
		}

		public AbstractCrawlerProcessUnit getProcessUnit(String inTitle, String inLink, String inUri, Lang inLang, Integer inTTL) {
			return null;
		}

		public Service getService() {
			return null;
		}

		@Override
		protected String generateLink(SyndEntry inFeedEntry, boolean doStrip) {
			return CrawlerPodcastFull.doGenerateLink(inFeedEntry, doStrip);
		}

		@Override
		protected String generateLink(Content inContent) {
			return CrawlerPodcastFull.generateLink(inContent.getLink());
		}

		public void processNews(List<Files> inNewsFiles, List<AbstractCrawlerProcessUnit> inNews, MessageSignature inMessageSignature, String inTitle, net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler.FeedUnit inFeedUnit) {
		}

		public void processUnit(AbstractCrawlerProcessUnit inUnit) {
		}

	};

	public static List<Content> initInrock() {
		return ContentMock.initOldAction80(AbstractVActionCrawlerTest.PATH_2_FILES, "contentInrockInit", true);
	}

	public static void init() {
		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 10);
		final Date inDate = theCal.getTime();
		new FilesMock(1L, "", MimeType.MIME_TYPES.A_MPEG, inDate);
		new FilesMock(8460L, "blabla", MimeType.MIME_TYPES.A_MPEG, inDate);
		final VAction theAction = new VActionMock(80, 1, "http://www.lesechos.fr/rss/rss_une.xml", ServiceFactory.SERVICE.RSS, "FREE", Factories.APPLICATION.find(1020));
		ContentMock.initOldAction80(AbstractVActionCrawlerTest.PATH_2_FILES, "contentsInit", false);

		new ContentMock(8785, theAction, Factories.FILES.find(8460), "L'illettrisme concernerait 8 % des actifs.", "", "idXML1");
		new ContentMock(8786, theAction, Factories.FILES.find(8460), "Mulhouse : une refonte urbaine pour la fonderie.", "", "idXML1");
		new ContentMock(8787, theAction, Factories.FILES.find(8460), "Nicolas Sarkozy va annoncer une nouvelle mesure pour les propriétaires.", "", null);
	}

	private static Iterator<SyndEntry> getEntryIterator(String inFileName) throws IllegalArgumentException, FeedException, IOException {
		final SyndFeedInput input = new SyndFeedInput();
		final SyndFeed feed = input.build(new XmlReader(new File(AbstractVActionCrawlerTest.PATH_2_FILES + inFileName)));

		return feed.getEntries().iterator();
	}

	@Test
	public void isNewContentWithIdsTest() throws IllegalArgumentException, FeedException, IOException {
		final VAction theAction = new VActionMock(80, 1, "http://www.lesechos.fr/rss/rss_une.xml", ServiceFactory.SERVICE.RSS, "FREE", Factories.APPLICATION.find(1020));
		final Files theFiles = new FilesMock(8460L, "blabla", MimeType.MIME_TYPES.A_MPEG);

		Iterator<SyndEntry> theIterator = AbstractVActionCrawlerTest.getEntryIterator("inrocks.xml");

		final Content content1 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 79 ", "http://www.lesinrocks.com", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_79.mp3");
		final Content content2 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 80 : invite Midnight Juggernauts ", "http://www.lesinrocks.com", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/podcastMJ.mp3");

		Assert.assertTrue(AbstractVActionCrawlerTest.THE_GENERIC_INSTANCE.isItemNew(Arrays.asList(content2, content1), theIterator.next()));
		Assert.assertFalse(AbstractVActionCrawlerTest.THE_GENERIC_INSTANCE.isItemNew(Arrays.asList(content2, content1), theIterator.next()));

		final Content content3 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 79 ", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_79.mp3", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_79.mp3");
		final Content content4 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 80 : invite Midnight Juggernauts ", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/podcastMJ.mp3", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/podcastMJ.mp3");

		theIterator = AbstractVActionCrawlerTest.getEntryIterator("inrocks.xml");
		Assert.assertTrue(AbstractVActionCrawlerTest.THE_PODCAST_INSTANCE.isItemNew(Arrays.asList(content4, content3), theIterator.next()));
		Assert.assertFalse(AbstractVActionCrawlerTest.THE_PODCAST_INSTANCE.isItemNew(Arrays.asList(content4, content3), theIterator.next()));
	}

	@Test
	public void isNewContentNoContentIdTest() throws IllegalArgumentException, FeedException, IOException {
		final VAction theAction = new VActionMock(80, 1, "http://www.lesechos.fr/rss/rss_une.xml", ServiceFactory.SERVICE.RSS, "FREE", Factories.APPLICATION.find(1020));
		final Files theFiles = new FilesMock(8460L, "blabla", MimeType.MIME_TYPES.A_MPEG);

		Iterator<SyndEntry> theIterator = AbstractVActionCrawlerTest.getEntryIterator("inrocks.xml");

		final Content content1 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 79 ", "http://www.lesinrocks.com", null);
		final Content content2 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 80 : invite Midnight Juggernauts ", "http://www.lesinrocks.com", null);

		Assert.assertTrue(AbstractVActionCrawlerTest.THE_GENERIC_INSTANCE.isItemNew(Arrays.asList(content2, content1), theIterator.next()));
		Assert.assertFalse(AbstractVActionCrawlerTest.THE_GENERIC_INSTANCE.isItemNew(Arrays.asList(content2, content1), theIterator.next()));

		final Content content3 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 79 ", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_79.mp3", null);
		final Content content4 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 80 : invite Midnight Juggernauts ", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/podcastMJ.mp3", null);
		theIterator = AbstractVActionCrawlerTest.getEntryIterator("inrocks.xml");
		Assert.assertTrue(AbstractVActionCrawlerTest.THE_PODCAST_INSTANCE.isItemNew(Arrays.asList(content4, content3), theIterator.next()));
		Assert.assertFalse(AbstractVActionCrawlerTest.THE_PODCAST_INSTANCE.isItemNew(Arrays.asList(content4, content3), theIterator.next()));
	}

	@Test
	public void isNewContentNoEntryIdTest() throws IllegalArgumentException, FeedException, IOException {
		final VAction theAction = new VActionMock(80, 1, "http://www.lesechos.fr/rss/rss_une.xml", ServiceFactory.SERVICE.RSS, "FREE", Factories.APPLICATION.find(1020));
		final Files theFiles = new FilesMock(8460L, "blabla", MimeType.MIME_TYPES.A_MPEG);

		Iterator<SyndEntry> theIterator = AbstractVActionCrawlerTest.getEntryIterator("inrocks2.xml");

		final Content content1 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 79 ", "http://www.lesinrocks.com", "http://www.lesinrocks.com");
		final Content content2 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 80 : invite Midnight Juggernauts ", "http://www.lesinrocks.com", "http://www.lesinrocks.com");

		Assert.assertTrue(AbstractVActionCrawlerTest.THE_GENERIC_INSTANCE.isItemNew(Arrays.asList(content2, content1), theIterator.next()));
		Assert.assertFalse(AbstractVActionCrawlerTest.THE_GENERIC_INSTANCE.isItemNew(Arrays.asList(content2, content1), theIterator.next()));

		final Content content3 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 79 ", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_79.mp3", "http://www.lesinrocks.com");
		final Content content4 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 80 : invite Midnight Juggernauts ", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/podcastMJ.mp3", "http://www.lesinrocks.com");
		theIterator = AbstractVActionCrawlerTest.getEntryIterator("inrocks2.xml");
		Assert.assertTrue(AbstractVActionCrawlerTest.THE_PODCAST_INSTANCE.isItemNew(Arrays.asList(content4, content3), theIterator.next()));
		Assert.assertFalse(AbstractVActionCrawlerTest.THE_PODCAST_INSTANCE.isItemNew(Arrays.asList(content4, content3), theIterator.next()));
	}

	@Test
	public void isNewContentNoIdTest() throws IllegalArgumentException, FeedException, IOException {
		final VAction theAction = new VActionMock(80, 1, "http://www.lesechos.fr/rss/rss_une.xml", ServiceFactory.SERVICE.RSS, "FREE", Factories.APPLICATION.find(1020));
		final Files theFiles = new FilesMock(8460L, "blabla", MimeType.MIME_TYPES.A_MPEG);

		Iterator<SyndEntry> theIterator = AbstractVActionCrawlerTest.getEntryIterator("inrocks2.xml");

		final Content content1 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 79 ", "http://www.lesinrocks.com", null);
		final Content content2 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 80 : invite Midnight Juggernauts ", "http://www.lesinrocks.com", null);

		Assert.assertTrue(AbstractVActionCrawlerTest.THE_GENERIC_INSTANCE.isItemNew(Arrays.asList(content2, content1), theIterator.next()));
		Assert.assertFalse(AbstractVActionCrawlerTest.THE_GENERIC_INSTANCE.isItemNew(Arrays.asList(content2, content1), theIterator.next()));

		final Content content3 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 79 ", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_79.mp3", null);
		final Content content4 = Factories.CONTENT.createNewContent(theAction, theFiles, "Les Inrocks Podcast vol. 80 : invite Midnight Juggernauts ", "http://podcasts.lesinrocks.com/inrocks/podcast/jd/podcastMJ.mp3", null);
		theIterator = AbstractVActionCrawlerTest.getEntryIterator("inrocks2.xml");
		Assert.assertTrue(AbstractVActionCrawlerTest.THE_PODCAST_INSTANCE.isItemNew(Arrays.asList(content4, content3), theIterator.next()));
		Assert.assertFalse(AbstractVActionCrawlerTest.THE_PODCAST_INSTANCE.isItemNew(Arrays.asList(content4, content3), theIterator.next()));
	}

}
