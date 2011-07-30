package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Store users tags for shared sounds.
 * 
 *
 */
public interface Tag extends Record<Tag> {

	Lang getLang();

	String getTag_word();

	int getFrequency();

}
