package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class CategImpl extends ObjectRecord<Categ, CategImpl> implements Categ {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<CategImpl> SPECIFICATION = new SQLObjectSpecification<CategImpl>("categ", CategImpl.class, new SQLKey("categ_id"));

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	public CategImpl(long id) throws SQLException {
		super();
		init(id);
	}

	protected CategImpl() {
		super();
	}

	/**
	 * Champs de l'enregistrement.
	 */
	protected long categ_id;
	protected String categ_type;

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Categ#getId()
	 */
	@Override
	public Long getId() {
		return this.categ_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Categ#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<CategImpl> getSpecification() {
		return CategImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Categ#getCateg_type()
	 */
	public String getName() {
		return this.categ_type;
	}

}
