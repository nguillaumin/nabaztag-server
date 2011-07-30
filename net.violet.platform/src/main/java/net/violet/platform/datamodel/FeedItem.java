package net.violet.platform.datamodel;

import java.util.List;

import net.violet.db.records.Record;

/**
 * Stores RSS and Podcasts feeds
 * 
 *
 */
public interface FeedItem extends Record<FeedItem> {

	Feed getFeed();

	List<Files> getContents();

	String getTitle();

	String getLink();

	String getUri();

}
