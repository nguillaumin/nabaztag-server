package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserEmail;
import net.violet.platform.datamodel.factories.UserEmailFactory;
import net.violet.platform.datamodel.mock.UserEmailMock;

public class UserEmailFactoryMock extends RecordFactoryMock<UserEmail, UserEmailMock> implements UserEmailFactory {

	UserEmailFactoryMock() {
		super(UserEmailMock.class);
	}

	public List<UserEmail> findAllByUser(User inUser) {
		final List<UserEmail> theEmails = new ArrayList<UserEmail>();
		for (final UserEmail anEmail : findAll()) {
			if (anEmail.getUser().equals(inUser)) {
				theEmails.add(anEmail);
			}
		}
		return theEmails;
	}

	public UserEmail findByAddress(String inAddress) {
		for (final UserEmail anEmail : findAll()) {
			if (anEmail.getAddress().equals(inAddress)) {
				return anEmail;
			}
		}

		return null;
	}

	public UserEmail create(User inUser, String inAddress) {
		return new UserEmailMock(0, inUser, inAddress);
	}
}
