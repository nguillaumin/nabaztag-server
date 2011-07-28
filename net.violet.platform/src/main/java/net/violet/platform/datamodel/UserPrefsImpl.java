package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class UserPrefsImpl extends ObjectRecord<UserPrefs, UserPrefsImpl> implements UserPrefs {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<UserPrefsImpl> SPECIFICATION = new SQLObjectSpecification<UserPrefsImpl>("userprefs", UserPrefsImpl.class, new SQLKey("userprefs_id"));

	/**
	 * Nouvelle colonnes.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "userprefs_id", "userprefs_layout" };

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected UserPrefsImpl(long id) throws SQLException {
		init(id);
	}

	protected UserPrefsImpl() {
		// This space for rent.
	}

	protected long userprefs_id;
	protected String userprefs_layout;

	public UserPrefsImpl(User user, String layout) throws SQLException {
		this.userprefs_id = user.getId();
		this.userprefs_layout = layout;

		init(UserPrefsImpl.NEW_COLUMNS);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.UserPrefs#getId()
	 */
	@Override
	public Long getId() {
		return this.userprefs_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.UserPrefs#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<UserPrefsImpl> getSpecification() {
		return UserPrefsImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.UserPrefs#getUserprefs_layout()
	 */
	public final String getUserprefs_layout() {
		return this.userprefs_layout;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.UserPrefs#setLayout(java.lang.String)
	 */
	public void setLayout(String layout) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUserprefs_layout(theUpdateMap, layout);
		update(theUpdateMap);
	}

	private void setUserprefs_layout(Map<String, Object> inUpdateMap, String inValue) {
		if ((inValue != this.userprefs_layout) && ((inValue == null) || !inValue.equals(this.userprefs_layout))) {
			this.userprefs_layout = inValue;
			inUpdateMap.put(UserPrefs.USER_PREFS_LAYOUT, inValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.UserPrefs#getUserPrefs()
	 */
	public Map<String, String> getUserPrefs() {
		final Map<String, String> userPrefs = new HashMap<String, String>();
		userPrefs.put("userprefs_layout", this.getUserprefs_layout());
		return userPrefs;
	}

}
