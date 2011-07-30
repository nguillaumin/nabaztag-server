package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Manages preferences of a user for friends request and filters.
 * 
 * Does <b>not</b> contains friends list !
 * 
 *
 */
public interface FriendsLists extends Record<FriendsLists> {

	long getFriendslists_confirmationlevel();

	void setParameters(long confirmationLevel, long filter, long antispam);

	long getFriendslists_filter();

	long getFriendslists_antispam();

	User getUser();

	void setConfirmationlevel(int inConfirmationLevel);

	void setParentalFilter(boolean activate);

}
