package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.List;

import net.violet.db.records.SQLSpecification;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserPwd;
import net.violet.platform.datamodel.UserPwdImpl;
import net.violet.platform.datamodel.factories.UserPwdFactory;

public class UserPwdFactoryImpl extends RecordFactoryImpl<UserPwd, UserPwdImpl> implements UserPwdFactory {

	UserPwdFactoryImpl(SQLSpecification<UserPwdImpl> inSpecification) {
		super(inSpecification);
	}

	public UserPwdFactoryImpl() {
		super(UserPwdImpl.SPECIFICATION);
	}

	public List<UserPwd> getAllByUser(User inUser) {
		final List<Object> inValues = Arrays.asList(new Object[] { inUser.getId() });
		final String inCondition = "user_id = ?";
		return findAll(inCondition, inValues);
	}

	public UserPwd getByPseudo(String inPseudo, User inUser) {
		final List<Object> theValues = Arrays.asList(new Object[] { inPseudo, inUser.getId() });
		final String inCondition = "pseudo = ? AND user_id = ?";
		return find(inCondition, theValues);
	}

}
