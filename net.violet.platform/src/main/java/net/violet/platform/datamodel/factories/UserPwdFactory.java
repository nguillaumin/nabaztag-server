package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserPwd;

public interface UserPwdFactory extends RecordFactory<UserPwd> {

	List<UserPwd> getAllByUser(User inUser);

	UserPwd getByPseudo(String inPseudo, User inUser);
}
