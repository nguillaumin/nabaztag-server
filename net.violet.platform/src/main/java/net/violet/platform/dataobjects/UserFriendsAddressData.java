package net.violet.platform.dataobjects;

import net.violet.platform.datamodel.UserFriendsAddress;
import net.violet.platform.datamodel.factories.Factories;

public class UserFriendsAddressData extends RecordData<UserFriendsAddress> {

	/**
	 * Construct a UserFriendsAddressData container
	 */
	protected UserFriendsAddressData(UserFriendsAddress inRecord) {
		super(inRecord);
	}

	/**
	 * Finds a UserImpl from his/her id
	 * 
	 * @param inId
	 * @return
	 */
	public static UserFriendsAddressData find(long inId) {
		return new UserFriendsAddressData(Factories.USER_FRIENDS_ADDRESS.find(inId));
	}

	public static void saveAddress(UserData theSessionUser, String email) {
		Factories.USER_FRIENDS_ADDRESS.saveFriendAddress(theSessionUser.getReference(), email);
	}

}
