package net.violet.platform.daemons.crawlers.application;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.daemons.crawlers.feed.processor.FeedCrawlerProcessor;
import net.violet.platform.daemons.crawlers.feed.processor.FeedCrawlerTTSProcessor;
import net.violet.platform.daemons.crawlers.feed.sender.FeedCrawlerInstantSender;
import net.violet.platform.daemons.crawlers.feed.sender.FeedCrawlerSender;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.concurrent.units.TTSProcessUnit;

public class CrawlerTwitter extends AbstractSubscriptionCrawler<TTSProcessUnit> {

	public static final int NB_NEWS_2_READ = 10;
	private static final Application APPLICATION = Application.NativeApplication.TWITTER.getApplication();
	private static final String URL = ApplicationSettingData.findByApplicationAndKey(ApplicationData.getData(CrawlerTwitter.APPLICATION), ApplicationSetting.FeedHandler.URL).getValue();

	private final FeedCrawlerSender<TTSProcessUnit> mSender;
	private final FeedCrawlerProcessor<TTSProcessUnit> mProcessor;

	public CrawlerTwitter(String[] inArgs) {
		super(inArgs, true);
		this.mSender = new FeedCrawlerInstantSender<TTSProcessUnit>(inArgs) {

			@Override
			public int getTTL() {
				return getService().getTtl();
			}
		};
		this.mProcessor = new FeedCrawlerTTSProcessor(Constantes.MAX_PROCESSING_ITEMS_TTS + 2, getService().getLabel() + "-" + SERVICE_ACCESSRIGHT.FREE.toString());
	}

	public Service getService() {
		return ServiceFactory.SERVICE.TWITTER.getService();
	}

	@Override
	protected int getNbItems2Read() {
		return CrawlerTwitter.NB_NEWS_2_READ;
	}

	public TTSProcessUnit getProcessUnit(String inTitle, String inLink, String inUri, Lang inLang, Integer inTTL) {
		return this.mProcessor.getProcessUnit(inTitle, inLink, inUri, inLang, inTTL);
	}

	public void processNews(List<Files> inNewsFiles, List<TTSProcessUnit> inNews, MessageSignature inMessageSignature, String inTitle, FeedUnit inFeedUnit) {
		final List<Files> theNews = new LinkedList<Files>(inNewsFiles);
		Collections.reverse(theNews);

		if (inFeedUnit instanceof AbstractSubscriptionCrawler.SubscriptionUnit) {
			final SubscriptionUnit theUnit = (SubscriptionUnit) inFeedUnit;
			final Object theAnnounceSetting = SubscriptionData.getData(theUnit.getSubscription()).getSettings().get(VActionFullHandler.FILE);
			final long fileId;
			if ((theAnnounceSetting == null) || theAnnounceSetting.toString().equals(net.violet.common.StringShop.EMPTY_STRING)) {
				fileId = CrawlerTwitter.APPLICATION.getProfile().getAnnounceFile().getId();
			} else {
				fileId = Long.parseLong(theAnnounceSetting.toString());
			}

			final Files theFile = Factories.FILES.find(fileId);
			if (theFile != null) {
				theNews.add(0, theFile);
			}
		}

		this.mSender.processNews(theNews, null, inMessageSignature, getMsgTitle(inTitle), inFeedUnit);
	}

	public void processUnit(TTSProcessUnit inUnit) {
		this.mProcessor.processUnit(inUnit);
	}

	@Override
	protected long getApplicationId() {
		return CrawlerTwitter.APPLICATION.getId();
	}

	@Override
	protected String getUrl() {
		return CrawlerTwitter.URL;
	}
}
