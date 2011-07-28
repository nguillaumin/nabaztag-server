package net.violet.platform.feeds.crawlers;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.db.records.Record.RecordWalker;
import net.violet.platform.applications.FacebookHandler;
import net.violet.platform.applications.GmailTwitterHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory.SERVICE;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.analyzers.FeedsAnalyzer;
import net.violet.platform.feeds.analyzers.UriBasedAnalyzer;
import net.violet.platform.feeds.processors.DetectTitleProcessor;
import net.violet.platform.feeds.processors.EntryProcessor;
import net.violet.platform.feeds.senders.EntrySender;
import net.violet.platform.feeds.senders.InstantSender;
import net.violet.platform.httpclient.Connection;
import net.violet.platform.httpclient.ConnectionConfiguration;
import net.violet.platform.httpclient.ConnectionException;
import net.violet.platform.message.MessageSignature;

import org.apache.log4j.Logger;

import com.sun.syndication.feed.synd.SyndEntry;

public class FacebookCrawler extends FeedCrawler<Subscription> {

	private static final Logger LOGGER = Logger.getLogger(FacebookCrawler.class);

	private static final Application APPLICATION = Application.NativeApplication.FACEBOOK.getApplication();
	private static final MessageSignature SIGNATURE = new MessageSignature(SERVICE.FACEBOOK.getService());

	public FacebookCrawler(String[] inArgs) {
		super(inArgs, Feed.Type.FACEBOOK);
	}

	@Override
	protected void process() {
		Factories.SUBSCRIPTION.walkByApplication(FacebookCrawler.APPLICATION, new RecordWalker<Subscription>() {

			public void process(Subscription subscription) {
				try {
					processElement(subscription);
				} catch (final InterruptedException e) {
					FacebookCrawler.LOGGER.fatal(e, e);
				}
			}
		});

		try {
			waitTermination();
		} catch (final InterruptedException e) {
			FacebookCrawler.LOGGER.fatal(e, e);
		}
	}

	@Override
	protected boolean isElementProcessable(Subscription element) {
		return SubscriptionData.getData(element).getObject().isCurrentlyRecipient();
	}

	@Override
	protected ConnectionConfiguration getConnectionConfiguration(Subscription element) throws ConnectionException {
		final Map<String, Object> settings = element.getSettings();
		if (settings.containsKey(FacebookHandler.URL)) {
			try {
				final ConnectionConfiguration config = new ConnectionConfiguration(settings.get(FacebookHandler.URL).toString());

				if (settings.get(GmailTwitterHandler.ETAG) != null) {
					config.addEtag(settings.get(GmailTwitterHandler.ETAG).toString());
				}

				if (settings.get(GmailTwitterHandler.LAST_MODIFIED) != null) {
					config.addLastModified(settings.get(GmailTwitterHandler.LAST_MODIFIED).toString());
				}

				return config;
			} catch (final URISyntaxException e) {
				throw new ConnectionException(e);
			} catch (final ParseException e) {
				// failed to parse the date, no need to keep this invalid setting anymore
				settings.remove(GmailTwitterHandler.LAST_MODIFIED);
				element.setSettings(settings);
				throw new ConnectionException(e);
			}
		}

		return null;
	}

	@Override
	protected FeedsAnalyzer getFeedsAnalyzer(Subscription element) {
		return new UriBasedAnalyzer(FacebookHandler.NB_NEWS_TO_READ, String.valueOf(element.getSettings().get(GmailTwitterHandler.LAST_ENTRY_ID)));
	}

	@Override
	protected EntryProcessor getEntryProcessor(Subscription element) {
		return DetectTitleProcessor.getInstance();
	}

	@Override
	protected EntrySender getEntrySender(Subscription element) {
		return new InstantSender(Collections.singletonList(VObjectData.getData(element.getObject())), FacebookCrawler.SIGNATURE,
				FacebookCrawler.APPLICATION.getName());
	}

	@Override
	protected void updateElement(Subscription element, List<SyndEntry> newEntries, Connection connection) {
		final Map<String, Object> settings = element.getSettings();
		settings.put(GmailTwitterHandler.LAST_MODIFIED, connection.getLastModified());
		settings.put(GmailTwitterHandler.ETAG, connection.getEtag());
		settings.put(GmailTwitterHandler.LAST_ENTRY_ID, newEntries.get(newEntries.size() - 1).getUri());
		element.setSettings(settings);
	}

}
