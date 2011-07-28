package net.violet.platform.datamodel;

import net.violet.db.records.associations.AssoRecord;

public interface Black extends AssoRecord<User, Black> {

	User getUser();

	User getBlacked();

	String getComment();

	void setComment(String comment);
}
