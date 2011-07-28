package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public class UserFriendsAddressImpl extends ObjectRecord<UserFriendsAddress, UserFriendsAddressImpl> implements UserFriendsAddress {


	private static final Logger LOGGER = Logger.getLogger(UserFriendsAddressImpl.class);

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<UserFriendsAddressImpl> SPECIFICATION = new SQLObjectSpecification<UserFriendsAddressImpl>("userFriendsAddress", UserFriendsAddressImpl.class, new SQLKey(new String[] { "userFriendsAddress_userId", "userFriendsAddress_address" }));

	/**
	 * Champs de l'enregistrement.
	 */
	protected long userFriendsAddress_userId;
	protected String userFriendsAddress_address;

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "userFriendsAddress_userId", "userFriendsAddress_address", };

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.UserFriendsAddress#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<UserFriendsAddressImpl> getSpecification() {
		return UserFriendsAddressImpl.SPECIFICATION;
	}

	protected UserFriendsAddressImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @seenet.violet.platform.datamodel.UserFriendsAddress#
	 * getUserFriendsAddress_address()
	 */
	public String getUserFriendsAddress_address() {
		return this.userFriendsAddress_address;
	}

	/**
	 * @param inUser
	 * @return
	 */
	public static List<UserFriendsAddress> findByUser(User inUser) {
		final List<UserFriendsAddress> list = new ArrayList<UserFriendsAddress>();
		try {
			list.addAll(AbstractSQLRecord.findAll(UserFriendsAddressImpl.SPECIFICATION, "userFriendsAddress_userId=?", Collections.singletonList((Object) inUser.getId())));
		} catch (final SQLException se) {
			UserFriendsAddressImpl.LOGGER.fatal(se, se);
		}
		return list;
	}

	private UserFriendsAddressImpl(User inUser, String address) {
		this.userFriendsAddress_userId = inUser.getId();
		this.userFriendsAddress_address = address;
	}

	public static void insert(User inUser, String address) throws SQLException {
		final UserFriendsAddressImpl theRecord = new UserFriendsAddressImpl(inUser, address);
		theRecord.insertRecord(UserFriendsAddressImpl.NEW_COLUMNS);
	}

	@Override
	public boolean equals(Object inAlter) {
		return inAlter == this;
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}
}
