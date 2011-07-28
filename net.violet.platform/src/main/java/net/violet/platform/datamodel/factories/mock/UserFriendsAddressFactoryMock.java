package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserFriendsAddress;
import net.violet.platform.datamodel.factories.UserFriendsAddressFactory;
import net.violet.platform.datamodel.mock.UserFriendsAddressMock;

public class UserFriendsAddressFactoryMock extends RecordFactoryMock<UserFriendsAddress, UserFriendsAddressMock> implements UserFriendsAddressFactory {

	UserFriendsAddressFactoryMock() {
		super(UserFriendsAddressMock.class);
	}

	/**
	 * Sauvegarde l'adresse de l'ami de l'user
	 * 
	 * @param userId
	 * @param a public static void saveFriendAddress(int userId, String a) { try
	 *            {
	 *            UserFriendsAddressImpl.insert(Factories.USER.find(userId),a);
	 *            } catch(SQLException se) { LOGGER.fatal(se, se); } }
	 */

	public void saveFriendAddress(User inUser, String address) {
		new UserFriendsAddressMock(inUser, address);
	}

	public List<UserFriendsAddress> findFriendsAddressByUser(User user) {
		final List<UserFriendsAddress> theResult = new LinkedList<UserFriendsAddress>();
		for (final UserFriendsAddress aUserFA : findAll()) {
			if (aUserFA.getId().equals(user.getId())) {
				theResult.add(aUserFA);
			}
		}
		return theResult;
	}

}
