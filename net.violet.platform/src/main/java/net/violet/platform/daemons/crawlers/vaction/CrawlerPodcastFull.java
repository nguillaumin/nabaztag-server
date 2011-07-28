package net.violet.platform.daemons.crawlers.vaction;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.daemons.crawlers.feed.processor.FeedCrawlerDownloadProcessor;
import net.violet.platform.daemons.crawlers.feed.processor.FeedCrawlerProcessor;
import net.violet.platform.daemons.crawlers.feed.sender.FeedCrawlerDBSender;
import net.violet.platform.daemons.crawlers.feed.sender.FeedCrawlerSender;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.concurrent.units.DownloadProcessUnit;

import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;

public class CrawlerPodcastFull extends AbstractVActionCrawler<DownloadProcessUnit> {

	private static final Pattern EXTENSION_MATCHER = Pattern.compile(".*\\.m(?:p(?:3|4)|4a)$", Pattern.CASE_INSENSITIVE);
	static final Pattern FILE_PATTERN = Pattern.compile(".+/([^.]+\\.m(?:p(?:3|4)|4a))$", Pattern.CASE_INSENSITIVE);

	private final FeedCrawlerSender<DownloadProcessUnit> mSender;
	private final FeedCrawlerProcessor<DownloadProcessUnit> mProcessor;

	public CrawlerPodcastFull(String[] inArgs) {
		super(inArgs, false);
		this.mSender = new FeedCrawlerDBSender<DownloadProcessUnit>(inArgs) {

			@Override
			public int getTTL() {
				return getService().getTtl();
			}
		};
		this.mProcessor = new FeedCrawlerDownloadProcessor(Constantes.MAX_PROCESSING_ITEMS_DOWNLOAD, getService().getLabel() + "-" + SERVICE_ACCESSRIGHT.FULL.toString(), false);
	}

	public Service getService() {
		return ServiceFactory.SERVICE.PODCAST.getService();
	}

	@Override
	protected int getNbItems2Read() {
		return 1;
	}

	@Override
	protected String generateLink(SyndEntry inFeedEntry, boolean doStrip) {
		return CrawlerPodcastFull.doGenerateLink(inFeedEntry, doStrip);
	}

	static String doGenerateLink(SyndEntry inFeedEntry, boolean doStrip) {

		String link = inFeedEntry.getLink();
		for (final Iterator<SyndEnclosure> theEnclosureIterator = inFeedEntry.getEnclosures().iterator(); !CrawlerPodcastFull.isLinkValid(link) && theEnclosureIterator.hasNext();) {
			final SyndEnclosure anEnclosure = theEnclosureIterator.next();
			final String type = anEnclosure.getType();

			if ((type != null) && type.contains("audio")) {
				link = anEnclosure.getUrl();
			}
		}

		if (CrawlerPodcastFull.isLinkValid(link)) {

			if (doStrip) {
				return CrawlerPodcastFull.generateLink(link);
			}
			return link;
		}

		return null;
	}

	@Override
	protected String generateLink(Content inContent) {
		return CrawlerPodcastFull.generateLink(inContent.getLink());
	}

	static String generateLink(String inLink) {
		final Matcher theMatcher = CrawlerPodcastFull.FILE_PATTERN.matcher(inLink);

		if (theMatcher.matches()) {
			return theMatcher.group(1);
		}

		return inLink;

	}

	static boolean isLinkValid(String inLink) {
		return (inLink != null) && CrawlerPodcastFull.EXTENSION_MATCHER.matcher(inLink).matches();
	}

	public DownloadProcessUnit getProcessUnit(String inTitle, String inLink, String inUri, Lang inLang, Integer inTTL) {
		return this.mProcessor.getProcessUnit(inTitle, inLink, inUri, inLang, inTTL);
	}

	public void processNews(List<Files> inNewsFiles, List<DownloadProcessUnit> inNews, MessageSignature inMessageSignature, String inTitle, FeedUnit inFeedUnit) {
		this.mSender.processNews(inNewsFiles, inNews, inMessageSignature, inTitle, inFeedUnit);
	}

	public void processUnit(DownloadProcessUnit inUnit) {
		this.mProcessor.processUnit(inUnit);
	}
}
