package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserPwd;
import net.violet.platform.datamodel.factories.UserPwdFactory;
import net.violet.platform.datamodel.mock.UserPwdMock;

public class UserPwdFactoryMock extends RecordFactoryMock<UserPwd, UserPwdMock> implements UserPwdFactory {

	UserPwdFactoryMock() {
		super(UserPwdMock.class);
	}

	public List<UserPwd> getAllByUser(User inUser) {
		final List<UserPwd> theResult = new LinkedList<UserPwd>();
		for (final UserPwd aUserPwd : findAll()) {
			if (aUserPwd.getId() == inUser.getId()) {
				theResult.add(aUserPwd);
			}
		}
		return theResult;
	}

	public UserPwd getByPseudo(String inPseudo, User inUser) {
		for (final UserPwd aUserPwd : findAll()) {
			if ((aUserPwd.getId() == inUser.getId()) && aUserPwd.getPseudo().equals(inPseudo)) {
				return aUserPwd;
			}
		}
		return null;
	}

}
