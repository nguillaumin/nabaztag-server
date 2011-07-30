package net.violet.platform.datamodel;

import net.violet.db.records.associations.AssoRecord;

/**
 * Link between objects and Podcast / RSS feeds.
 * 
 * Deprecated, see {@link FeedSubscription}
 * 
 *
 */
public interface ObjectHasReadContent extends AssoRecord<VAction, ObjectHasReadContent> {

	VAction getAction();

	void setContent(Content inContent);

	Content getContent();

}
