package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserFriendsAddress;

public class UserFriendsAddressMock extends AbstractMockRecord<UserFriendsAddress, UserFriendsAddressMock> implements UserFriendsAddress {

	private String address;
	private User theUser;
	private Long userId;

	public UserFriendsAddressMock(long inId) {
		super(inId);
	}

	public UserFriendsAddressMock(User inUser, String email) {
		super(inUser.getId());
		this.address = email;
		this.theUser = inUser;
		this.userId = inUser.getId();
	}

	public String getUserFriendsAddress_address() {
		return this.address;
	}

	public User getUserFriendsAddress_User() {
		return this.theUser;
	}

	public Long getUserFriendsAddress_UserId() {
		return this.userId;
	}

}
