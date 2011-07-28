package net.violet.platform.feeds.crawlers;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.factories.ServiceFactory.SERVICE;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.message.MessageSignature;

public class TwitterCrawler extends GmailTwitterCrawler {

	private static final Application APPLICATION = Application.NativeApplication.TWITTER.getApplication();
	private static final String URL = ApplicationSettingData.findByApplicationAndKey(ApplicationData.getData(TwitterCrawler.APPLICATION), ApplicationSetting.FeedHandler.URL).getValue();
	private static final MessageSignature SIGNATURE = new MessageSignature(SERVICE.TWITTER.getService());

	public TwitterCrawler(String[] inArgs) {
		super(inArgs, TwitterCrawler.APPLICATION, TwitterCrawler.URL, TwitterCrawler.SIGNATURE);
	}

}
