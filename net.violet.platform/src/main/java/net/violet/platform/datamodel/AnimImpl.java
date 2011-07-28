package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public class AnimImpl extends ObjectRecord<Anim, AnimImpl> implements Anim {

	private static final Logger LOGGER = Logger.getLogger(AnimImpl.class);

	public static final SQLObjectSpecification<AnimImpl> SPECIFICATION = new SQLObjectSpecification<AnimImpl>("anim", AnimImpl.class, new SQLKey[] { new SQLKey("anim_id"), new SQLKey("anim_url") });

	protected long anim_id;
	protected String anim_name;
	protected String anim_url;
	protected String anim_signature;

	protected AnimImpl(long id) throws SQLException {
		init(id);
	}

	protected AnimImpl() {
		// This space for rent.
	}

	/**
	 * Find an Anim object from its url
	 * 
	 * @param inUrl
	 * @return the Anim if found, <code>null</code> otherwise
	 */
	public static Anim findByUrl(String inUrl) {
		Anim anim = null;
		if (inUrl != null) {
			try {
				anim = AbstractSQLRecord.findByKey(AnimImpl.SPECIFICATION, AnimImpl.SPECIFICATION.getTableKeys()[1], inUrl);
			} catch (final SQLException se) {
				AnimImpl.LOGGER.fatal(se, se);
			}
		}
		return anim;
	}

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param id id de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static Anim find(long id) {
		Anim anim = null;
		try {
			anim = AbstractSQLRecord.findByKey(AnimImpl.SPECIFICATION, AnimImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			AnimImpl.LOGGER.fatal(se, se);
		}
		return anim;
	}

	@Override
	public Long getId() {
		return this.anim_id;
	}

	@Override
	public SQLObjectSpecification<AnimImpl> getSpecification() {
		return AnimImpl.SPECIFICATION;
	}

	public String getAnim_name() {
		return this.anim_name;
	}

	public String getAnim_signature() {
		return this.anim_signature;
	}

	public String getAnim_url() {
		return this.anim_url;
	}

}
