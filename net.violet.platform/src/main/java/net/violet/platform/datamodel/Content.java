package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface Content extends Record<Content> {

	VAction getAction();

	Files getFile();

	String getTitle();

	String getLink();

	String getId_xml();

	long countObjectHasReadContent();

}
