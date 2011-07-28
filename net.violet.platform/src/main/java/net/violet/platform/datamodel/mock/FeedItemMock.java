package net.violet.platform.datamodel.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedItem;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.Factories;

public class FeedItemMock extends AbstractMockRecord<FeedItem, FeedItemMock> implements FeedItem {

	private final long feedId;
	private final List<Files> contents;
	private final String title;
	private final String link;
	private final String uri;

	public FeedItemMock(long feedId, List<Files> contents, String title, String link, String uri) {
		super(0);
		this.feedId = feedId;
		this.contents = contents;
		this.title = title;
		this.link = link;
		this.uri = uri;
	}

	public List<Files> getContents() {
		return new ArrayList<Files>(this.contents);
	}

	public Feed getFeed() {
		return Factories.FEED.find(this.feedId);
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
