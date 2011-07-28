package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface UserEmail extends Record<UserEmail> {

	User getUser();

	String getAddress();

	void setAddress(String inAddress);
}
