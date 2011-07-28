package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

//Inutilisé au 20070924.
public class ClockCategoriesImpl extends ObjectRecord<ClockCategories, ClockCategoriesImpl> implements ClockCategories {


	private static final Logger LOGGER = Logger.getLogger(ClockCategoriesImpl.class);

	/**
	 * Spécification
	 */
	static final SQLObjectSpecification<ClockCategoriesImpl> SPECIFICATION = new SQLObjectSpecification<ClockCategoriesImpl>("clockCategories", ClockCategoriesImpl.class, new SQLKey("idCategory"));

	protected long idCategory;
	protected Long idParent;
	protected String dico_key;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected ClockCategoriesImpl(long id) throws SQLException {
		init(id);
	}

	protected ClockCategoriesImpl() {
		// This space for rent.
	}

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param id id de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static ClockCategories find(long id) {
		ClockCategories clockCategories = null;
		try {
			clockCategories = AbstractSQLRecord.findByKey(ClockCategoriesImpl.SPECIFICATION, ClockCategoriesImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			ClockCategoriesImpl.LOGGER.fatal(se, se);
		}
		return clockCategories;
	}

	/**
	 * Finds all the root categories
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<ClockCategories> findAllRoot() throws SQLException {
		return new LinkedList<ClockCategories>(AbstractSQLRecord.findAll(ClockCategoriesImpl.SPECIFICATION, "idParent IS NULL", null));
	}

	public final String getDico_key() {
		return this.dico_key;
	}

	@Override
	public Long getId() {
		return this.idCategory;
	}

	@Override
	public SQLObjectSpecification<ClockCategoriesImpl> getSpecification() {
		return ClockCategoriesImpl.SPECIFICATION;
	}

}
