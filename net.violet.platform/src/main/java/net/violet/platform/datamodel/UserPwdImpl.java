package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.common.utils.DigestTools;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public final class UserPwdImpl extends ObjectRecord<UserPwd, UserPwdImpl> implements UserPwd {

	private static final Logger LOGGER = Logger.getLogger(UserPwdImpl.class);

	/**
	 * Spécification.
	 */
	public static final SQLObjectSpecification<UserPwdImpl> SPECIFICATION = new SQLObjectSpecification<UserPwdImpl>("user_pwd", UserPwdImpl.class, new SQLKey[] { new SQLKey("user_id"), new SQLKey("pseudo") });

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "user_id", "pseudo", "pwd", };

	// / Champs de l'enregistrement.
	protected long user_id;
	protected String pseudo;
	protected String pwd;

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	public UserPwdImpl() {
		// This space for rent.
	}

	@Deprecated
	public UserPwdImpl(long inUserId, String inPseudo, String pwd) throws SQLException {
		this(inUserId, inPseudo, pwd, true);
	}

	public long getUser_id() {
		return this.user_id;
	}

	@Deprecated
	public UserPwdImpl(long inUserId, String inPseudo, String pwd, boolean encodePassword) throws SQLException {
		this.user_id = inUserId;
		this.pseudo = inPseudo;
		this.pwd = (encodePassword) ? DigestTools.digest(pwd, DigestTools.Algorithm.MD5) : pwd;
		init(UserPwdImpl.NEW_COLUMNS);
	}

	public void setUser_id(long user_id) {
		if (user_id != this.user_id) {
			final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
			this.user_id = user_id;
			theUpdateMap.put("user_id", this.user_id);
			update(theUpdateMap);
		}
	}

	public void setPseudo(String inPseudo) {
		if ((inPseudo != null) && !inPseudo.equals(this.pseudo)) {
			final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
			this.pseudo = inPseudo;
			theUpdateMap.put("pseudo", this.pseudo);
			update(theUpdateMap);
		}
	}

	public String getPwd() {
		return this.pwd;
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public void setPwd(String pwd) {
		final String thePwd = DigestTools.digest(pwd, DigestTools.Algorithm.MD5);

		if ((thePwd != null) && !thePwd.equals(this.pwd)) {
			final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
			this.pwd = thePwd;
			theUpdateMap.put("pwd", this.pwd);
			update(theUpdateMap);
		}
	}

	@Override
	public SQLObjectSpecification<UserPwdImpl> getSpecification() {
		return UserPwdImpl.SPECIFICATION;
	}

	public static UserPwd findByPseudoAndUser(String inPseudo, User inUser) {
		final List<Object> theList = Arrays.asList(new Object[] { inPseudo, inUser.getId() });

		try {
			return AbstractSQLRecord.find(UserPwdImpl.SPECIFICATION, null, "pseudo = ? AND user_id = ?", theList, null);
		} catch (final SQLException e) {
			UserPwdImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	public static List<UserPwd> findAllByPseudo(String inPseudo) {
		final List<Object> theList = Arrays.asList(new Object[] { inPseudo });

		try {
			return AbstractSQLRecord.findAll(UserPwdImpl.SPECIFICATION, null, "pseudo = ?", theList, null);
		} catch (final SQLException e) {
			UserPwdImpl.LOGGER.fatal(e, e);
		}

		return Collections.emptyList();
	}

	public static UserPwd findUserId(long user_id) {
		final List<Object> theList = Arrays.asList(new Object[] { user_id });

		try {
			return AbstractSQLRecord.find(UserPwdImpl.SPECIFICATION, null, "user_id = ?", theList, null);
		} catch (final SQLException e) {
			UserPwdImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

}
