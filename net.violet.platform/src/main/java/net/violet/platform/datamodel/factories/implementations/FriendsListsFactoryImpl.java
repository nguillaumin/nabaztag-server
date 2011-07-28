package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.FriendsListsImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.FriendsListsFactory;

import org.apache.log4j.Logger;

public class FriendsListsFactoryImpl extends RecordFactoryImpl<FriendsLists, FriendsListsImpl> implements FriendsListsFactory {

	private static final Logger LOGGER = Logger.getLogger(FriendsListsFactoryImpl.class);

	public FriendsListsFactoryImpl() {
		super(FriendsListsImpl.SPECIFICATION);
	}

	public FriendsLists findByUser(User inUser) {
		return findByKey(0, inUser.getId());
	}

	public FriendsLists createDefault(User myUser) {
		try {
			return new FriendsListsImpl(myUser.getId(), 0, 0, 0);
		} catch (final SQLException e) {
			FriendsListsFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}
}
