package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserEmail;

public interface UserEmailFactory extends RecordFactory<UserEmail> {

	List<UserEmail> findAllByUser(User inUser);

	UserEmail findByAddress(String inAddress);

	UserEmail create(User inUser, String inAddress);

}
