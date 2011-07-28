package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface Tag extends Record<Tag> {

	Lang getLang();

	String getTag_word();

	int getFrequency();

}
