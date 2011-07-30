package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Allows to store alternate email addresses for a user.
 * 
 *
 */
public interface UserEmail extends Record<UserEmail> {

	User getUser();

	String getAddress();

	void setAddress(String inAddress);
}
