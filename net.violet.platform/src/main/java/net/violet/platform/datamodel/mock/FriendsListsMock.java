package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;

public class FriendsListsMock extends AbstractMockRecord<FriendsLists, FriendsListsMock> implements FriendsLists {

	private long mConfirmationLevel;
	private long mFilter;
	private long mAntispam;

	public FriendsListsMock(long friendslists_user_id, long friendslists_confirmationlevel, long friendslists_filter, long friendslists_antispam) {
		super(friendslists_user_id);
		this.mConfirmationLevel = friendslists_confirmationlevel;
		this.mFilter = friendslists_filter;
		this.mAntispam = friendslists_antispam;
	}

	public FriendsListsMock(Long friendslists_user_id) {
		super(friendslists_user_id);
		this.mConfirmationLevel = 0;
		this.mFilter = 0;
		this.mAntispam = 0;
	}

	public long getFriendslists_antispam() {
		return this.mAntispam;
	}

	public long getFriendslists_confirmationlevel() {
		return this.mConfirmationLevel;
	}

	public long getFriendslists_filter() {
		return this.mFilter;
	}

	public User getUser() {
		return Factories.USER.find(getId());
	}

	public void setParameters(long confirmationLevel, long filter, long antispam) {
		this.mConfirmationLevel = confirmationLevel;
		this.mFilter = filter;
		this.mAntispam = antispam;
	}

	public void setConfirmationlevel(int inConfirmationLevel) {
		this.mConfirmationLevel = inConfirmationLevel;

	}

	public void setParentalFilter(boolean activate) {
		this.mFilter = (activate) ? 1 : 0;
	}

}
