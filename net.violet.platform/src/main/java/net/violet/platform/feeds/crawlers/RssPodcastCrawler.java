package net.violet.platform.feeds.crawlers;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.violet.db.records.Record.RecordWalker;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.Feed.Type;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.feeds.FeedsTools;
import net.violet.platform.feeds.analyzers.FeedsAnalyzer;
import net.violet.platform.feeds.analyzers.HistoryAnalyzer;
import net.violet.platform.feeds.senders.DBSender;
import net.violet.platform.feeds.senders.EntrySender;
import net.violet.platform.httpclient.Connection;
import net.violet.platform.httpclient.ConnectionConfiguration;
import net.violet.platform.httpclient.ConnectionException;

import org.apache.log4j.Logger;

import com.sun.syndication.feed.synd.SyndEntry;

public abstract class RssPodcastCrawler extends FeedCrawler<Feed> {

	private final static Logger LOGGER = Logger.getLogger(RssPodcastCrawler.class);

	private final Feed.Type type;
	private final Feed.AccessRight accessRight;
	private final int nbNews;

	public RssPodcastCrawler(String[] inArgs, Feed.Type type, Feed.AccessRight accessRight, int nbNews) {
		super(inArgs, type);
		this.type = type;
		this.accessRight = accessRight;
		this.nbNews = nbNews;
	}

	@Override
	protected boolean isElementProcessable(Feed element) {
		return true;
	}

	@Override
	protected void process() {
		Factories.FEED.walkByTypeAndAccessRight(this.type, this.accessRight, new RecordWalker<Feed>() {

			public void process(Feed feed) {
				try {
					processElement(feed);
				} catch (final InterruptedException e) {
					RssPodcastCrawler.LOGGER.fatal(e, e);
				}
			}
		});

		try {
			waitTermination();
		} catch (final InterruptedException e) {
			RssPodcastCrawler.LOGGER.fatal(e, e);
		}

	}

	@Override
	protected ConnectionConfiguration getConnectionConfiguration(Feed element) throws ConnectionException {
		try {
			final ConnectionConfiguration config = new ConnectionConfiguration(element.getUrl());

			if (element.getETag() != null) {
				config.addEtag(element.getETag());
			}

			if (element.getLastModified() != null) {
				config.addLastModified(element.getLastModified());
			}

			return config;
		} catch (final URISyntaxException e) {
			throw new ConnectionException(e);
		}
	}

	@Override
	protected FeedsAnalyzer getFeedsAnalyzer(Feed element) {
		final List<FeedItem> knownItems = Factories.FEED_ITEM.findAllByFeed(element);
		Collections.reverse(knownItems);
		return new HistoryAnalyzer(this.nbNews, knownItems) {

			@Override
			// ugly method, but it is probably worth it to keep the url of the mp3 file instead of the link
			// describes in the xml file
			protected String getEntryLink(SyndEntry feedEntry) {
				if (RssPodcastCrawler.this.type == Type.RSS) {
					return super.getEntryLink(feedEntry);
				}
				return FeedsTools.extractEnclosureUrl(feedEntry);
			}
		};
	}

	@Override
	protected EntrySender getEntrySender(Feed element) {
		return new DBSender(FeedData.getData(element), this.nbNews);
	}

	@Override
	protected void updateElement(Feed element, List<SyndEntry> newEntries, Connection connection) {
		try {
			final Date lastModifiedFromConnection = connection.getLastModifiedAsDate();
			final Timestamp newLastModified = lastModifiedFromConnection == null ? null : new Timestamp(lastModifiedFromConnection.getTime());
			element.updateInformation(connection.getEtag(), newLastModified);
		} catch (final ParseException e) {
			RssPodcastCrawler.LOGGER.fatal(e, e);
			element.updateInformation(connection.getEtag(), null);
		}
	}

}
