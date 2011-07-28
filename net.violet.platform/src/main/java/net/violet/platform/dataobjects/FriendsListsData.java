package net.violet.platform.dataobjects;

import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.factories.Factories;

public class FriendsListsData extends RecordData<FriendsLists> {

	public FriendsListsData(FriendsLists inRecord) {
		super(inRecord);
	}

	public static FriendsListsData findByUser(UserData inUser) {
		final FriendsLists theFriendsLists = Factories.FRIENDS_LISTS.findByUser(inUser.getRecord());
		if (theFriendsLists != null) {
			return new FriendsListsData(theFriendsLists);
		}
		return new FriendsListsData(Factories.FRIENDS_LISTS.createDefault(inUser.getRecord()));
	}

	public long getFriendslists_confirmationlevel() {
		final FriendsLists theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getFriendslists_confirmationlevel();
		}

		return 0;
	}

	public void setFriendslists_confirmationlevel(int inConfirmationLevel) {
		final FriendsLists theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setConfirmationlevel(inConfirmationLevel);
		}

	}

	public FriendsLists getReference() {
		return getRecord();
	}

	public void setParentalFilter(boolean activate) {
		final FriendsLists theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setParentalFilter(activate);
		}
	}

	public boolean getParentalFilter() {
		final FriendsLists theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getFriendslists_filter() == 1L;
		}

		return false;

	}
}
