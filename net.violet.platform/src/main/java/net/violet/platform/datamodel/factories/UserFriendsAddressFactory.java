package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserFriendsAddress;

public interface UserFriendsAddressFactory extends RecordFactory<UserFriendsAddress> {

	List<UserFriendsAddress> findFriendsAddressByUser(User user);

	/**
	 * Sauvegarde l'adresse de l'ami de l'user
	 * 
	 * @param userId
	 * @param a
	 */
	public void saveFriendAddress(User inUser, String a);

}
