package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

import org.apache.log4j.Logger;

public class FriendsListsImpl extends ObjectRecord<FriendsLists, FriendsListsImpl> implements FriendsLists {


	private static final Logger LOGGER = Logger.getLogger(FriendsListsImpl.class);

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<FriendsListsImpl> SPECIFICATION = new SQLObjectSpecification<FriendsListsImpl>("friendslists", FriendsListsImpl.class, new SQLKey("friendslists_user_id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "friendslists_user_id", "friendslists_confirmationlevel", "friendslists_filter", "friendslists_antispam", };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long friendslists_user_id;
	protected long friendslists_confirmationlevel;
	protected long friendslists_filter;
	protected long friendslists_antispam;

	private final SingleAssociationNotNull<FriendsLists, User, UserImpl> user;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	public FriendsListsImpl(long id) throws SQLException {
		init(id);
		this.user = new SingleAssociationNotNull<FriendsLists, User, UserImpl>(this, "friendslists_user_id", UserImpl.SPECIFICATION);
	}

	protected FriendsListsImpl() {
		this.user = new SingleAssociationNotNull<FriendsLists, User, UserImpl>(this, "friendslists_user_id", UserImpl.SPECIFICATION);
	}

	public FriendsListsImpl(long friendslists_user_id, long friendslists_confirmationlevel, long friendslists_filter, long friendslists_antispam) throws SQLException {
		this.friendslists_user_id = friendslists_user_id;
		this.friendslists_confirmationlevel = friendslists_confirmationlevel;
		this.friendslists_filter = friendslists_filter;
		this.friendslists_antispam = friendslists_antispam;
		init(FriendsListsImpl.NEW_COLUMNS);
		this.user = new SingleAssociationNotNull<FriendsLists, User, UserImpl>(this, "friendslists_user_id", UserImpl.SPECIFICATION);
	}

	/**
	 * Accesseur à partir d'un user.
	 * 
	 * @param user
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static FriendsLists findByUser(User user) {
		if (user == null) {
			return null;
		}
		FriendsLists friendsLists = null;
		try {
			friendsLists = AbstractSQLRecord.findByKey(FriendsListsImpl.SPECIFICATION, FriendsListsImpl.SPECIFICATION.getTableKeys()[0], user.getId());
		} catch (final SQLException se) {
			FriendsListsImpl.LOGGER.fatal(se, se);
		}
		return friendsLists;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.FriendsLists#getId()
	 */
	@Override
	public Long getId() {
		return getUser().getId();
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.FriendsLists#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<FriendsListsImpl> getSpecification() {
		return FriendsListsImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.FriendsLists#getFriendslists_confirmationlevel
	 * ()
	 */
	public final long getFriendslists_confirmationlevel() {
		return this.friendslists_confirmationlevel;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.FriendsLists#setParameters(long, long,
	 * long)
	 */
	public void setParameters(long confirmationLevel, long filter, long antispam) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setFriendslists_confirmationlevel(theUpdateMap, confirmationLevel);
		setFriendslists_filter(theUpdateMap, filter);
		setFriendslists_antispam(theUpdateMap, antispam);
		update(theUpdateMap);
	}

	private void setFriendslists_confirmationlevel(Map<String, Object> inUpdateMap, long inValue) {
		if (inValue != this.friendslists_confirmationlevel) {
			this.friendslists_confirmationlevel = inValue;
			inUpdateMap.put("friendslists_confirmationlevel", inValue);
		}
	}

	private void setFriendslists_filter(Map<String, Object> inUpdateMap, long inValue) {
		if (inValue != this.friendslists_filter) {
			this.friendslists_filter = inValue;
			inUpdateMap.put("friendslists_filter", inValue);
		}
	}

	private void setFriendslists_antispam(Map<String, Object> inUpdateMap, long inValue) {
		if (inValue != this.friendslists_antispam) {
			this.friendslists_antispam = inValue;
			inUpdateMap.put("friendslists_antispam", inValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.FriendsLists#getFriendslists_filter()
	 */
	public final long getFriendslists_filter() {
		return this.friendslists_filter;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.FriendsLists#getFriendslists_antispam()
	 */
	public final long getFriendslists_antispam() {
		return this.friendslists_antispam;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.FriendsLists#getUser()
	 */
	public final User getUser() {
		return this.user.get(this.friendslists_user_id);
	}

	public void setConfirmationlevel(int inConfirmationLevel) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setFriendslists_confirmationlevel(theUpdateMap, inConfirmationLevel);
		update(theUpdateMap);

	}

	public void setParentalFilter(boolean activate) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setFriendslists_filter(theUpdateMap, (activate) ? 1 : 0);
		update(theUpdateMap);
	}

}
