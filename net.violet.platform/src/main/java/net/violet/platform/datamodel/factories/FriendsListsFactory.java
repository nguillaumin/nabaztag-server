package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.User;

public interface FriendsListsFactory extends RecordFactory<FriendsLists> {

	FriendsLists findByUser(User inUser);

	FriendsLists createDefault(User myUser);

}
