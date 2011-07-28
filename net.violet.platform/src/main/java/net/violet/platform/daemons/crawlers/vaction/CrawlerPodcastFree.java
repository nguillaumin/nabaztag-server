package net.violet.platform.daemons.crawlers.vaction;

import java.util.List;

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

import com.sun.syndication.feed.synd.SyndEntry;

public class CrawlerPodcastFree extends AbstractVActionCrawler<DownloadProcessUnit> {

	private final FeedCrawlerSender<DownloadProcessUnit> mSender;
	private final FeedCrawlerProcessor<DownloadProcessUnit> mProcessor;

	public CrawlerPodcastFree(String[] inArgs) {
		super(inArgs, true);
		this.mSender = new FeedCrawlerDBSender<DownloadProcessUnit>(inArgs) {

			@Override
			public int getTTL() {
				return getService().getTtl();
			}
		};
		this.mProcessor = new FeedCrawlerDownloadProcessor(Constantes.MAX_PROCESSING_ITEMS_DOWNLOAD, getService().getLabel() + "-" + SERVICE_ACCESSRIGHT.FREE.toString(), true);
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

	@Override
	protected String generateLink(Content inContent) {
		return CrawlerPodcastFull.generateLink(inContent.getLink());
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
