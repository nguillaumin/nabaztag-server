package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.ListAssociation;

import org.apache.log4j.Logger;

public class ContextImpl extends ObjectRecord<Context, ContextImpl> implements Context {


	private static final Logger LOGGER = Logger.getLogger(ContextImpl.class);

	/**
	 * Specification.
	 */
	public static final SQLObjectSpecification<ContextImpl> SPECIFICATION = new SQLObjectSpecification<ContextImpl>("context", ContextImpl.class, new SQLKey[] { new SQLKey("id") });

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "name", };

	protected long id;
	protected String name;

	/**
	 * Liste des hints du contexte.
	 */
	private List<Hint> hintList;

	protected ContextImpl() {
	}

	protected ContextImpl(long id) throws NoSuchElementException, SQLException {
		init(id);
	}

	public ContextImpl(String inName) throws SQLException {

		this.name = inName;

		init(ContextImpl.NEW_COLUMNS);
	}

	@Override
	public SQLObjectSpecification<ContextImpl> getSpecification() {
		return ContextImpl.SPECIFICATION;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void addHint(Hint inHint) {
		final List<Hint> theHints = getAllHints();
		if (!theHints.contains(inHint)) {
			theHints.add(inHint);
		}
	}

	public void removeHint(Hint inHint) {
		final List<Hint> theHints = getAllHints();
		if (theHints.contains(inHint)) {
			theHints.remove(inHint);
		}
	}

	public List<Hint> getAllHints() {
		if (this.hintList == null) {
			try {
				this.hintList = ListAssociation.createListAssociation(this, HintImpl.SPECIFICATION, "hint_has_context", "context_id", "hint_id");
			} catch (final SQLException e) {
				ContextImpl.LOGGER.fatal(e, e);
				this.hintList = new ArrayList<Hint>();
			}
		}
		return this.hintList;
	}

}
