package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.SQLSpecification;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserFriendsAddress;
import net.violet.platform.datamodel.UserFriendsAddressImpl;
import net.violet.platform.datamodel.factories.UserFriendsAddressFactory;

import org.apache.log4j.Logger;

public class UserFriendsAddressFactoryImpl extends RecordFactoryImpl<UserFriendsAddress, UserFriendsAddressImpl> implements UserFriendsAddressFactory {

	UserFriendsAddressFactoryImpl(SQLSpecification<UserFriendsAddressImpl> inSpecification) {
		super(inSpecification);
		// TODO Auto-generated constructor stub
	}

	public UserFriendsAddressFactoryImpl() {
		super(UserFriendsAddressImpl.SPECIFICATION);
	}


	private static final Logger LOGGER = Logger.getLogger(UserFriendsAddressFactoryImpl.class);

	/**
	 * Sauvegarde l'adresse de l'ami de l'user
	 * 
	 * @param userId
	 * @param a
	 */
	public void saveFriendAddress(User inUser, String a) {
		try {
			UserFriendsAddressImpl.insert(inUser, a);
		} catch (final SQLException se) {
			UserFriendsAddressFactoryImpl.LOGGER.fatal(se, se);
		}
	}

	public List<UserFriendsAddress> findFriendsAddressByUser(User user) {
		return new ArrayList<UserFriendsAddress>(findAll("userFriendsAddress_userId = ?", Collections.singletonList((Object) user.getId()), null));
	}

}
