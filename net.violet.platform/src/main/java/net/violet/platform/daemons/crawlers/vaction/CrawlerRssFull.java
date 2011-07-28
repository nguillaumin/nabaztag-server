package net.violet.platform.daemons.crawlers.vaction;

import java.util.List;

import net.violet.platform.daemons.crawlers.feed.processor.FeedCrawlerProcessor;
import net.violet.platform.daemons.crawlers.feed.processor.FeedCrawlerTTSProcessor;
import net.violet.platform.daemons.crawlers.feed.sender.FeedCrawlerDBSender;
import net.violet.platform.daemons.crawlers.feed.sender.FeedCrawlerSender;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.concurrent.units.TTSProcessUnit;

public class CrawlerRssFull extends AbstractVActionCrawler<TTSProcessUnit> {

	private final FeedCrawlerSender<TTSProcessUnit> mSender;
	private final FeedCrawlerProcessor<TTSProcessUnit> mProcessor;

	public CrawlerRssFull(String[] inArgs) {
		super(inArgs, false);
		this.mSender = new FeedCrawlerDBSender<TTSProcessUnit>(inArgs) {

			@Override
			public int getTTL() {
				return getService().getTtl();
			}
		};
		this.mProcessor = new FeedCrawlerTTSProcessor(Constantes.MAX_PROCESSING_ITEMS_TTS, getService().getLabel() + "-" + SERVICE_ACCESSRIGHT.FULL.toString());
	}

	public Service getService() {
		return ServiceFactory.SERVICE.RSS.getService();
	}

	@Override
	protected int getNbItems2Read() {
		return 30;
	}

	public TTSProcessUnit getProcessUnit(String inTitle, String inLink, String inUri, Lang inLang, Integer inTTL) {
		return this.mProcessor.getProcessUnit(inTitle, inLink, inUri, inLang, inTTL);
	}

	public void processNews(List<Files> inNewsFiles, List<TTSProcessUnit> inNews, MessageSignature inMessageSignature, String inTitle, FeedUnit inFeedUnit) {
		this.mSender.processNews(inNewsFiles, inNews, inMessageSignature, inTitle, inFeedUnit);
	}

	public void processUnit(TTSProcessUnit inUnit) {
		this.mProcessor.processUnit(inUnit);
	}
}
