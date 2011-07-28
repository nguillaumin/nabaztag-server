package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserEmail;
import net.violet.platform.datamodel.factories.Factories;

public class UserEmailMock extends AbstractMockRecord<UserEmail, UserEmailMock> implements UserEmail {

	private String emailAddress;
	private final long userId;

	public UserEmailMock(long inId, User inUser, String inAddress) {
		this(inId, inUser.getId(), inAddress);
	}

	public UserEmailMock(long inId, long inUserId, String inAddress) {
		super(inId);
		this.userId = inUserId;
		this.emailAddress = inAddress;
	}

	public UserEmailMock(User inUser, String inAddress) {
		this(0, inUser, inAddress);
	}

	public String getAddress() {
		return this.emailAddress;
	}

	public User getUser() {
		return Factories.USER.find(this.userId);
	}

	public void setAddress(String inAddress) {
		this.emailAddress = inAddress;
	}

}
