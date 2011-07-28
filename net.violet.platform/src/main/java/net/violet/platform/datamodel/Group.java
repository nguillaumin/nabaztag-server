package net.violet.platform.datamodel;

import java.util.List;

import net.violet.db.records.Record;

public interface Group extends Record<Group> {

	String getName();

	String getDescription();

	VObject getCreator();

	Files getPicture();

	Files getStream();

	Lang getLanguage();

	List<VObject> getMembers();

	Feed getFeed();
}
