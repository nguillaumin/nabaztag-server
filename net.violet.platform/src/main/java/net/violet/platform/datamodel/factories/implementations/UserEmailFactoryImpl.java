package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserEmail;
import net.violet.platform.datamodel.UserEmailImpl;
import net.violet.platform.datamodel.factories.UserEmailFactory;

import org.apache.log4j.Logger;

public class UserEmailFactoryImpl extends RecordFactoryImpl<UserEmail, UserEmailImpl> implements UserEmailFactory {

	private static final Logger LOGGER = Logger.getLogger(UserEmailFactoryImpl.class);

	public UserEmailFactoryImpl() {
		super(UserEmailImpl.SPECIFICATION);
	}

	public List<UserEmail> findAllByUser(User inUser) {
		return findAll(" user_id = ? ", Collections.singletonList((Object) inUser.getId()));
	}

	public UserEmail findByAddress(String inAddress) {
		return find(" user_alternate_address = ? ", Collections.singletonList((Object) inAddress));
	}

	public UserEmail create(User inUser, String inAddress) {
		try {
			return new UserEmailImpl(inUser, inAddress);
		} catch (final SQLException e) {
			UserEmailFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

}
