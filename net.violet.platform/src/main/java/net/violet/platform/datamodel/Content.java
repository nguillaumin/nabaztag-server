package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * 
 * Apparently deprecated. Was used for RSS feeds, podcast.
 * Now the table {@link FeedItem} is used.
 *
 */
public interface Content extends Record<Content> {

	VAction getAction();

	Files getFile();

	String getTitle();

	String getLink();

	String getId_xml();

	long countObjectHasReadContent();

}
