package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public class ZtampBatchImpl extends ObjectRecord<ZtampBatch, ZtampBatchImpl> implements ZtampBatch {


	private static final Logger LOGGER = Logger.getLogger(ZtampBatchImpl.class);

	/**
	 * Spécification
	 */
	static final SQLObjectSpecification<ZtampBatchImpl> SPECIFICATION = new SQLObjectSpecification<ZtampBatchImpl>("ztamp_batch", ZtampBatchImpl.class, new SQLKey("id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "default_applet_id", "name_prefix", };

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected ZtampBatchImpl(long id) throws SQLException {
		super();
		init(id);
	}

	protected ZtampBatchImpl() {
		super();
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 */
	public ZtampBatchImpl(long inDefaultAppletId, String inDefaultAppletParam, String inNamePrefix) throws SQLException {
		super();

		this.default_applet_id = inDefaultAppletId;
		this.default_applet_param = inDefaultAppletParam;
		this.name_prefix = inNamePrefix;

		init(ZtampBatchImpl.NEW_COLUMNS);
	}

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id; // r/o
	protected Long default_applet_id; // r/o
	protected String default_applet_param; // r/o
	protected String name_prefix; // r/o

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param id id de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static ZtampBatch find(long id) {
		try {
			return AbstractSQLRecord.findByKey(ZtampBatchImpl.SPECIFICATION, ZtampBatchImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException e) {
			ZtampBatchImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ZtampBatch#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ZtampBatch#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<ZtampBatchImpl> getSpecification() {
		return ZtampBatchImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ZtampBatch#getDefaultAppletId()
	 */
	public Long getDefaultAppletId() {
		return this.default_applet_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ZtampBatch#getDefaultAppletParam()
	 */
	public String getDefaultAppletParam() {
		return this.default_applet_param;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ZtampBatch#getNamePrefix()
	 */
	public String getNamePrefix() {
		return this.name_prefix;
	}

}
