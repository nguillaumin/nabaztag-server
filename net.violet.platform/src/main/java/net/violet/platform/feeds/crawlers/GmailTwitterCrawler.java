package net.violet.platform.feeds.crawlers;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.db.records.Record.RecordWalker;
import net.violet.platform.applications.GmailTwitterHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.analyzers.FeedsAnalyzer;
import net.violet.platform.feeds.analyzers.UriBasedAnalyzer;
import net.violet.platform.feeds.processors.EntryProcessor;
import net.violet.platform.feeds.processors.OnlyTitleProcessor;
import net.violet.platform.feeds.senders.EntrySender;
import net.violet.platform.feeds.senders.InstantSender;
import net.violet.platform.httpclient.Connection;
import net.violet.platform.httpclient.ConnectionConfiguration;
import net.violet.platform.httpclient.ConnectionException;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.CipherTools;

import org.apache.log4j.Logger;

import com.sun.syndication.feed.synd.SyndEntry;

public abstract class GmailTwitterCrawler extends FeedCrawler<Subscription> {

	private static final Logger LOGGER = Logger.getLogger(GmailTwitterCrawler.class);

	private final Application application;
	private final String url;
	private final MessageSignature signature;

	public GmailTwitterCrawler(String[] inArgs, Application application, String url, MessageSignature signature) {
		super(inArgs, Feed.Type.GMAIL_TWITTER);
		this.application = application;
		this.url = url;
		this.signature = signature;
	}

	@Override
	protected void process() {
		Factories.SUBSCRIPTION.walkByApplication(this.application, new RecordWalker<Subscription>() {

			public void process(Subscription subscription) {
				try {
					processElement(subscription);
				} catch (final InterruptedException e) {
					GmailTwitterCrawler.LOGGER.fatal(e, e);
				}
			}
		});

		try {
			waitTermination();
		} catch (final InterruptedException e) {
			GmailTwitterCrawler.LOGGER.fatal(e, e);
		}
	}

	@Override
	protected boolean isElementProcessable(Subscription element) {
		return SubscriptionData.getData(element).getObject().isCurrentlyRecipient();
	}

	@Override
	protected ConnectionConfiguration getConnectionConfiguration(Subscription element) throws ConnectionException {
		final Map<String, Object> settings = element.getSettings();
		if (settings.containsKey(GmailTwitterHandler.LOGIN) && settings.containsKey(GmailTwitterHandler.PASSWORD)) {
			try {
				final ConnectionConfiguration config = new ConnectionConfiguration(this.url);
				config.addCredentials(settings.get(GmailTwitterHandler.LOGIN).toString(), CipherTools.uncipher(settings.get(GmailTwitterHandler.PASSWORD).toString()));

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
		return new UriBasedAnalyzer(GmailTwitterHandler.NB_NEWS_TO_READ, String.valueOf(element.getSettings().get(GmailTwitterHandler.LAST_ENTRY_ID)));
	}

	@Override
	protected EntryProcessor getEntryProcessor(Subscription element) {
		return OnlyTitleProcessor.getProcessor(Factories.LANG.findByIsoCode(element.getSettings().get(GmailTwitterHandler.LANGUAGE).toString()));
	}

	@Override
	protected EntrySender getEntrySender(Subscription element) {
		return new InstantSender(Collections.singletonList(VObjectData.getData(element.getObject())), this.signature, this.application.getName()) {

			@Override
			protected void sendServiceMessage(List<Files> theContents, VObjectData member) {
				final List<Files> contents = new ArrayList<Files>(theContents);
				contents.add(0, GmailTwitterCrawler.this.application.getProfile().getAnnounceFile());
				super.sendServiceMessage(contents, member);
			}

		};
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
