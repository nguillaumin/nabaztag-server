package net.violet.platform.datamodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public class NabcastCategImpl extends ObjectRecord<NabcastCateg, NabcastCategImpl> implements NabcastCateg {

	private static final Logger LOGGER = Logger.getLogger(NabcastCategImpl.class);

	public static final SQLObjectSpecification<NabcastCategImpl> SPECIFICATION = new SQLObjectSpecification<NabcastCategImpl>("nabcastcateg", NabcastCategImpl.class, new SQLKey[] { new SQLKey("nabcastcateg_id"), new SQLKey("nabcastcateg_val") });

	protected long nabcastcateg_id;
	protected String nabcastcateg_val;
	protected Long nabcastcateg_lang;
	protected String nabcastcateg_desc;

	protected NabcastCategImpl(long id) throws SQLException {
		init(id);
	}

	protected NabcastCategImpl() {
		// This space for rent.
	}

	public static NabcastCateg find(long id) {
		NabcastCateg result = null;
		try {
			result = AbstractSQLRecord.findByKey(NabcastCategImpl.SPECIFICATION, NabcastCategImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			NabcastCategImpl.LOGGER.fatal(se, se);
		}
		return result;
	}

	public static List<NabcastCateg> doFindAllByLang(Lang inLang) throws SQLException {
		return AbstractSQLRecord.findAll(NabcastCategImpl.SPECIFICATION, null, "nabcastcateg.nabcastcateg_lang = ?", Collections.singletonList((Object) inLang.getId()));
	}

	@Override
	public Long getId() {
		return getNabcastcateg_id();
	}

	@Override
	public SQLObjectSpecification<NabcastCategImpl> getSpecification() {
		return NabcastCategImpl.SPECIFICATION;
	}

	public final long getNabcastcateg_id() {
		return this.nabcastcateg_id;
	}

	public final String getNabcastcateg_val() {
		return this.nabcastcateg_val;
	}

	public final long getNabcastcateg_lang() {
		return this.nabcastcateg_lang;
	}

	public final String getNabcastcateg_desc() {
		return this.nabcastcateg_desc;
	}

	public static NabcastCateg doFindByName(String name) {
		NabcastCateg result = null;
		try {
			result = AbstractSQLRecord.findByKey(NabcastCategImpl.SPECIFICATION, NabcastCategImpl.SPECIFICATION.getTableKeys()[1], name);
		} catch (final SQLException se) {
			NabcastCategImpl.LOGGER.fatal(se, se);
		}
		return result;
	}

	public static NabcastCateg doCreateObject(ResultSet inResultSet) throws SQLException {
		return AbstractSQLRecord.createObject(NabcastCategImpl.SPECIFICATION, inResultSet);
	}
}
