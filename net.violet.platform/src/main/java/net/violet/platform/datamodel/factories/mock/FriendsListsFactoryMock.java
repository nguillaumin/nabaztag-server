package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.FriendsListsFactory;
import net.violet.platform.datamodel.mock.FriendsListsMock;

public class FriendsListsFactoryMock extends RecordFactoryMock<FriendsLists, FriendsListsMock> implements FriendsListsFactory {

	public FriendsListsFactoryMock() {
		super(FriendsListsMock.class);
	}

	public FriendsLists findByUser(User inUser) {
		for (final FriendsLists f : findAll()) {
			if (f.getId().equals(inUser.getId())) {
				return f;
			}
		}

		return null;
	}

	public FriendsLists createDefault(User myUser) {
		return new FriendsListsMock(myUser.getId());
	}

}
