package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class FeedItemImpl extends ObjectRecord<FeedItem, FeedItemImpl> implements FeedItem {

	private static final Logger LOGGER = Logger.getLogger(FeedItemImpl.class);

	public static final SQLObjectSpecification<FeedItemImpl> SPECIFICATION = new SQLObjectSpecification<FeedItemImpl>("feed_items", FeedItemImpl.class, new SQLKey[] { new SQLKey("id") });

	private static final String[] NEW_COLUMNS = new String[] { "feed_id", "contents", "title", "link", "uri" };

	protected long id;
	protected long feed_id;
	protected String contents;
	protected String title;
	protected String link;
	protected String uri;

	private final SingleAssociationNotNull<FeedItem, Feed, FeedImpl> feed;

	protected FeedItemImpl(long id) throws SQLException {
		init(id);
		this.feed = new SingleAssociationNotNull<FeedItem, Feed, FeedImpl>(this, "feed_id", FeedImpl.SPECIFICATION);
	}

	protected FeedItemImpl() {
		this.feed = new SingleAssociationNotNull<FeedItem, Feed, FeedImpl>(this, "feed_id", FeedImpl.SPECIFICATION);
	}

	public FeedItemImpl(Feed feed, List<Files> contents, String title, String link, String uri) throws SQLException {
		this.feed_id = feed.getId();
		this.contents = FeedItemImpl.convertToContents(contents);
		this.title = title;
		this.link = String.valueOf(link); // link and uri may be null, we need to store the string representation for comparisons.
		this.uri = String.valueOf(uri);

		init(FeedItemImpl.NEW_COLUMNS);

		this.feed = new SingleAssociationNotNull<FeedItem, Feed, FeedImpl>(this, "feed_id", FeedImpl.SPECIFICATION);
	}

	private static String convertToContents(List<Files> contents) {
		final List<Integer> theContents = new ArrayList<Integer>();
		for (final Files aFile : contents) {
			theContents.add(aFile.getId().intValue());
		}
		return ConverterFactory.JSON.convertTo(theContents);
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public SQLObjectSpecification<FeedItemImpl> getSpecification() {
		return FeedItemImpl.SPECIFICATION;
	}

	public List<Files> getContents() {
		final List<Files> theContents = new ArrayList<Files>();
		try {
			for (final long anId : (List<Integer>) ConverterFactory.JSON.convertFrom(this.contents)) {
				theContents.add(Factories.FILES.find(anId));
			}
		} catch (final ConversionException e) {
			FeedItemImpl.LOGGER.fatal(e, e);
		}

		return theContents;
	}

	@Override
	protected void doDelete() throws SQLException {
		for (final Files aFile : getContents()) {
			aFile.scheduleDeletion();
		}

		super.doDelete();
	}

	public Feed getFeed() {
		return this.feed.get(this.feed_id);
	}

	public String getLink() {
		return this.link;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUri() {
		return this.uri;
	}

}
