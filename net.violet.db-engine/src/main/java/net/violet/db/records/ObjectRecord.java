package net.violet.db.records;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import net.violet.db.cache.Cache;
import net.violet.db.records.SgbdConnection.SGBD_ACCESS;

import org.apache.log4j.Logger;

/**
 * Classe de base pour un enregistrement dans les bases SQL. Chaque instance
 * représente un tuple.
 */
public abstract class ObjectRecord<Intf extends Record<Intf>, Impl extends ObjectRecord<Intf, Impl>> extends AbstractSQLRecord<Intf, Impl> implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(ObjectRecord.class);

	/**
	 * Constructeur par défaut.
	 */
	protected ObjectRecord() {
		// This space for rent.
	}

	/**
	 * Initialisation à partir d'un enregistrement dans la base avec un ID
	 * donné.
	 *
	 * @param inSpec Spécification SQL.
	 * @param inId ID de l'enregistrement.
	 * @throws SQLException en cas d'erreur SQL.
	 * @throws NoSuchElementException s'il n'y a pas d'enregistrement avec cet
	 *             ID.
	 */
	protected void init(long inId) throws SQLException, NoSuchElementException {
		final SgbdConnection theConnection = new SgbdConnection();
		try {
			final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecord(theConnection, getSpecification(), null, getSpecification().getConditionClause(), Arrays.asList((Object) inId));
			try {
				final ResultSet theResultSet = theSgbdResult.getResultSet();
				setColumns(theResultSet, getSpecification().getTablePrefix());
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
		setState(State.CLEAN);
	}

	/**
	 * Initialisation pour un enregistrement qui n'est pas encore dans la base.
	 *
	 * @param inSpec Spécification SQL.
	 * @param inValues Valeurs pour l'enregistrement (si les valeurs ne sont pas
	 *            présentes, on utilise les valeurs par défaut dans le schéma
	 *            SQL).
	 * @throws SQLException en cas d'erreur SQL.
	 */

	protected void init(String[] inInitalizedCols) throws SQLException {
		final long theId = insertRecord(inInitalizedCols);
		// On relit.
		final SQLObjectSpecification<Impl> theSpec = getSpecification();
		final SgbdConnection theConnection = new SgbdConnection();
		try {
			final SgbdResult theSgbdResult;
			if ((theSpec.getPrimaryKey().getKeys().length == 1) && (theId != 0)) {
				// Ici c'est un vrai ObjectRecord avec une clé unique sur un
				// champ
				theSgbdResult = fetchRecord(theConnection, theSpec, theSpec.getConditionClause(), theId);
			} else {
				theSgbdResult = fetchRecord(theConnection, theSpec, theSpec.getConditionClause(), theSpec.getConditionValues((Impl) this));
			}
			try {
				final ResultSet theResultSet = theSgbdResult.getResultSet();
				setColumns(theResultSet, theSpec.getTablePrefix());
				Cache.add((Impl) this);
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
		setState(State.CLEAN);
	}

	/**
	 * Récupère le result set à partir d'information de connexion, de l'ID, du
	 * nom de la table et des colonnes de l'objet.
	 *
	 * @param inConnection Connexion avec la base de données.
	 * @param inTableName Nom de la table SQL.
	 * @param inColumns Liste des colonnes SQL.
	 * @param inWhereClause Clause where pour la sélection.
	 * @return un ResultSet (éventuellement vide).
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected static SgbdResult fetchRecord(SgbdConnection inConnection, SQLSpecification inSpec, String inWhereClause, Object inValue) throws SQLException {
		return AbstractSQLRecord.fetchRecords(inConnection, new SQLSpecification[] { inSpec }, null, inWhereClause, Arrays.asList(new Object[] { inValue }), 0, 1, null, null, null, null, 0, true);
	}

	/**
	 * Récupère le result set à partir d'information de connexion, de la clé
	 * composite, du nom de la table et des colonAbstractRecordnes de l'objet.
	 *
	 * @param inConnection Connexion avec la base de données.
	 * @param inTableName Nom de la table SQL.
	 * @param inColumns Liste des colonnes SQL.
	 * @param inWhereClause Clause where pour la sélection.
	 * @param inValues Listes des valeurs de la clé composite
	 * @return un SgbdResult (éventuellement vide).
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected static SgbdResult fetchRecord(SgbdConnection inConnection, SQLSpecification inSpec, String inWhereClause, List<Object> inValues) throws SQLException {
		return AbstractSQLRecord.fetchRecords(inConnection, new SQLSpecification[] { inSpec }, null, inWhereClause, inValues, 0, 1, null, null, null, null, 0, true);
	}

	// ////////////////////// UTILITAIRES ////////////////////////

	/**
	 * Insère un enregistrement dans la base.
	 *
	 * @param inInitializedCols Colonnes avec valeurs d'initialisation.
	 * @return l'id de l'enregistrement.
	 * @throws SQLException en cas d'erreur SQL.
	 */
	public long insertRecord(String[] inInitializedCols) throws SQLException {
		final long theResult;
		final SgbdConnection theConnection = new SgbdConnection(getSpecification().getConnectionMode(), SGBD_ACCESS.READWRITE);
		try {
			theResult = insertRecord(theConnection, inInitializedCols, null);
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	/**
	 * Insère un enregistrement dans la base.
	 *
	 * @param inInitializedCols Colonnes avec valeurs d'initialisation.
	 * @param inVals Valeurs qui remplacent celles de l'objet.
	 * @return l'id de l'enregistrement.
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected long insertRecord(String[] inInitializedCols, Map<String, Object> inVals) throws SQLException {
		final SgbdConnection theConnection = new SgbdConnection();
		final long theResult;
		try {
			theResult = insertRecord(theConnection, inInitializedCols, inVals);
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	/**
	 * Insère un enregistrement dans la base.
	 *
	 * @param inConnection connexion sur la base (de la bonne zone).
	 * @param inInitializedCols Colonnes avec valeurs d'initialisation.
	 * @param inVals valeurs qui remplacent celles de l'objet (ou
	 *            <code>null</code> )
	 * @return l'id de l'enregistrement.
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected long insertRecord(SgbdConnection inConnection, String[] inInitializedCols, Map<String, Object> inVals) throws SQLException {
		return insertRecord(inConnection, inInitializedCols, inVals, getSpecification().getIdColName());
	}

	/**
	 * Met à jour le tuple dans une base de données.
	 *
	 * @throws SQLException en cas d'erreur SQL.
	 */

	protected void doUpdate(String[] inColumns) throws SQLException {
		final SQLObjectSpecification<Impl> theSpec = getSpecification();
		final String theCondition = theSpec.getConditionClause();
		final List<Object> theConditionVals = theSpec.getConditionValues((Impl) this);
		updateRecord(inColumns, theCondition, theConditionVals);
	}

	/**
	 * Met à jour le tuple dans une base de données.
	 *
	 * @throws SQLException en cas d'erreur SQL.
	 */

	@Override
	protected void doUpdate(Map<String, Object> inUpdateMap) throws SQLException {
		final SQLObjectSpecification<Impl> theSpec = getSpecification();
		final String theCondition = theSpec.getConditionClause();
		final List<Object> theConditionVals = theSpec.getConditionValues((Impl) this);
		updateRecord(inUpdateMap, theCondition, theConditionVals);
	}

	// ////////////////////// ACCESSEURS ////////////////////////

	/**
	 * Accesseur sur la spécification.
	 *
	 * @return la spécification pour cet enregistrement.
	 */
	@Override
	public abstract SQLObjectSpecification<Impl> getSpecification();

	// ////////////////////// INTERFACE PUBLIQUE ////////////////////////

	/**
	 * Supprime l'élément de la base de données.
	 *
	 * @throws SQLException si une erreur est survenue.
	 */

	@Override
	protected void doDelete() throws SQLException {
		final SQLObjectSpecification<Impl> theSpec = getSpecification();
		final String theCondition = theSpec.getConditionClause();
		final List<Object> theConditionVals = theSpec.getConditionValues((Impl) this);
		deleteRecord(theCondition, theConditionVals);
	}

	/**
	 * Accesseur sur l'id de l'enregistrement.
	 *
	 * @return l'id de l'enregistrement.
	 */
	// TODO : à vérifier le Long vs Object
	public Long getId() {
		try {
			final Field theField = this.getClass().getDeclaredField(getSpecification().getTableKeys()[0].getKeys()[0]);
			return (Long) getFieldValue(theField);
		} catch (final SecurityException e) {
			ObjectRecord.LOGGER.fatal(e, e);
		} catch (final NoSuchFieldException e) {
			ObjectRecord.LOGGER.fatal(e, e);
		} catch (final IllegalArgumentException e) {
			ObjectRecord.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			ObjectRecord.LOGGER.fatal(e, e);
		}
		return null;
	}

	/**
	 * Normalement, avec le cache, on n'aura qu'un objet avec un id donné. Pour
	 * le moment, cette méthode compare la classe et l'id.
	 *
	 * @param inAlter objet à comparer avec <code>this</code>.
	 * @return <code>true</code> si l'objet est égal à this.
	 */
	@Override
	public boolean equals(Object inAlter) {
		if (inAlter == null || !inAlter.getClass().equals(getClass())) {
			return false;
		}

		if (inAlter == this) {
			return true;
		}

		final ObjectRecord theRecord = (ObjectRecord) inAlter;
		return (!isInvalid() && !theRecord.isInvalid()) && (theRecord.getId().equals(getId()));
	}

	/**
	 * Calcul de la valeur de hachage. Compatible avec la fonction equals.
	 *
	 * @return la valeur de hachage pour this.
	 */
	@Override
	public int hashCode() {
		return (getClass().hashCode() * 17) + getId().hashCode();
	}

	/**
	 * Creer l'objet avec initialisation par resultSet
	 *
	 * @param inSpec spécification SQL de l'objet
	 * @param inResultSet résultat de la requête select.
	 * @throws SQLException en cas d'erreur SQL ou d'accès java.
	 */
	static <Intf extends Record<Intf>, T extends ObjectRecord<Intf, T>> Intf createObject(SQLObjectSpecification<T> inSpec, ResultSet inResultSet) throws SQLException {
		return AbstractSQLRecord.createObject(inSpec, inResultSet);
	}
}
