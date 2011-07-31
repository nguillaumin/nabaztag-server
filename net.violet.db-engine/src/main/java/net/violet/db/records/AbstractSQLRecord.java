package net.violet.db.records;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.Semaphore;

import net.violet.common.StringShop;
import net.violet.db.cache.Cache;
import net.violet.db.records.SgbdConnection.ResultSetWorker;

import org.apache.log4j.Logger;

/**
 * Classe de base pour un enregistrement dans les bases SQL.
 */
public abstract class AbstractSQLRecord<Intf extends Record<Intf>, Impl extends AbstractSQLRecord<Intf, Impl>> implements Record<Intf> {

	private static final Logger LOGGER = Logger.getLogger(AbstractSQLRecord.class);

	/**
	 * Fetch size pour les walks, i.e. nombre de records en mémoire.
	 */
	private static final int WALK_FETCHSIZE = 10000;

	/**
	 * Indique qu'au moins une des clés non primaires à changer
	 */
	private boolean invalidKeys;

	/**
	 * SignatureImpl pour les contructeurs avec zone
	 */
	private static final Class[] CONSTRUCTOR_SIGNATURE = new Class[] {};

	/**
	 * Etat de l'enregistrement.
	 */
	private State mState;

	/**
	 * Liste des listeners à mettre au courant de la suppression.
	 */
	private final List<RecordDeletionListener<Intf>> mRecordDeletionListener;

	/**
	 * Constructeur à partir d'une zone.
	 *
	 * @param inZone Zone où se situe l'enregistrement.
	 */
	protected AbstractSQLRecord() {
		this.mState = State.NEW;
		this.mRecordDeletionListener = new LinkedList<RecordDeletionListener<Intf>>();
	}

	// ////////////////////// INITIALISATION ////////////////////////

	/**
	 * Initialisation pour un enregistrement qui n'est pas encore dans la base.
	 *
	 * @param inSpec Spécification SQL.
	 * @param inValues Valeurs pour l'enregistrement (si les valeurs ne sont pas
	 *            présentes, on utilise les valeurs par défaut dans le schéma
	 *            SQL).
	 * @throws SQLException en cas d'erreur SQL.
	 */

	protected void init() throws SQLException {
		final SgbdConnection theConnection = new SgbdConnection();
		try {
			insertRecord(theConnection);
		} finally {
			theConnection.close();
		}
		Cache.add((Impl) this);
		this.mState = State.CLEAN;
	}

	/**
	 * Initialisation d'un enregistrement à partir d'un result set (positionné
	 * sur l'enregistrement) et d'un index pour les noms des colonnes.
	 *
	 * @param inResultSet Résultat de la requête SQL.
	 * @param inPrefix prefix pour les noms des colonnes.
	 * @throws SQLException en cas d'erreur SQL.
	 */

	public void init(ResultSet inResultSet, String inPrefix) throws SQLException {
		doSetColumns(inResultSet, inPrefix);
		Cache.add((Impl) this);
		this.mState = State.CLEAN;
	}

	// ////////////////////// UTILITAIRES ////////////////////////

	/**
	 * Détermine les colonnes SQL à l'aide de reflect. Ce sont les champs
	 * d'instance qui sont protégés. Retourne ces champs sous forme d'un tableau
	 * de chaînes.
	 *
	 * @param inClass classe de l'objet.
	 * @return la liste des colonnes.
	 */
	public static String[] getColumns(Class<? extends Record> inClass) {
		final List<String> theResult = new LinkedList<String>();
		final Field[] theFields = inClass.getDeclaredFields();
		for (final Field theField : theFields) {
			final int theModifiers = theField.getModifiers();
			if (((theModifiers & Modifier.PROTECTED) == Modifier.PROTECTED) && ((theModifiers & Modifier.STATIC) == 0)) {
				theResult.add(theField.getName());
			}
		}

		return theResult.toArray(new String[theResult.size()]);
	}

	/**
	 * Récupère le result set à partir de la condition.
	 *
	 * @param inJoinTables liste des tables avec lesquelles on fait une
	 *            jointure, ou <code>null</code>.
	 * @param oneRow true récupère un row sinon tout
	 * @return un ResultSet (éventuellement vide).
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected static SgbdResult fetchRecord(SgbdConnection inConnection, SQLSpecification inSpec, String[] inJoinTables, String condition, List<Object> values) throws SQLException {
		return AbstractSQLRecord.fetchRecords(inConnection, new SQLSpecification[] { inSpec }, inJoinTables, condition, values, 0, 1, null, null, null, null, 0, false);
	}

	/**
	 * Récupère le result set à partir de la condition.
	 *
	 * @param inJoinTables liste des tables avec lesquelles on fait une
	 *            jointure, ou <code>null</code>.
	 * @param nbRows nombre de rangs souhaités, 0 pour tous.
	 * @param inOrderBy clause pour l'ordre (e.g. nom de la colonne), ou
	 *            <code>null</code>.
	 * @param inTools si on fait un DISTINCT dans la requête.
	 * @return un ResultSet (éventuellement vide).
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected static SgbdResult fetchRecords(SgbdConnection inConnection, SQLSpecification inSpec, String[] inJoinTables, String condition, List<Object> values, int indexRows, int nbRows, String inOrderBy, SQL_TOOLS inTools, List<String> inDistinctKeys, String inGroupBy, int inFetchSize) throws SQLException {
		return AbstractSQLRecord.fetchRecords(inConnection, new SQLSpecification[] { inSpec }, inJoinTables, condition, values, indexRows, nbRows, inOrderBy, inTools, inDistinctKeys, inGroupBy, inFetchSize, false);
	}

	public enum SQL_TOOLS {
		DISTINCT {

			@Override
			public String toString() {
				return super.toString() + " ";
			}
		},
		MIN,
		MAX;

		@Override
		public String toString() {
			return name();
		}
	}

	/**
	 * Récupère le result set à partir de la condition.
	 *
	 * @param inJoinTables liste des tables avec lesquelles on fait une
	 *            jointure, ou <code>null</code>.
	 * @param nbRows nombre de rangs souhaités, 0 pour tous.
	 * @param inOrderBy clause pour l'ordre (e.g. nom de la colonne), ou
	 *            <code>null</code>.
	 * @param inTool si on fait un DISTINCT dans la requête.
	 * @param inDistinctKeys les clefs sur lesquelles la distinction est fait
	 *            (si nécessaire, <code>null</code> pour désigner tout la row).
	 *            Les clefs doivent être préfixées comme il se doit
	 * @return un ResultSet (éventuellement vide).
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected static SgbdResult fetchRecords(SgbdConnection inConnection, SQLSpecification[] inSpecs, String[] inJoinTables, String condition, List<Object> values, int indexRows, int nbRows, String inOrderBy, SQL_TOOLS inTool, List<String> inDistinctKeys, String inGroupBy, int inFetchSize, boolean readOnMasterDB) throws SQLException {

		StringBuilder theSubQuery = null;
		final StringBuilder theTables = new StringBuilder();

		for (int i = 0; i < inSpecs.length; i++) {
			final SQLSpecification aSpec = inSpecs[i];
			final String theTableName = aSpec.getTableName();

			for (final String theCol : aSpec.getColumns()) {
				final StringBuilder theColQueryStr = new StringBuilder(theTableName).append(".").append(theCol).append(" as ").append(aSpec.makeColAlias(theCol));
				if (theSubQuery == null) {
					theSubQuery = new StringBuilder(theColQueryStr);
				} else {
					theSubQuery.append(", ").append(theColQueryStr);
				}
			}

			if (i != 0) {
				theTables.append(", ");
			}
			theTables.append(theTableName);
		}

		final StringBuilder theBuilder = new StringBuilder();
		if (inTool != null) {
			theBuilder.append(inTool.toString());
			if ((inDistinctKeys != null) && !inDistinctKeys.isEmpty()) {
				final Iterator<String> iterator = inDistinctKeys.iterator();

				if (iterator.hasNext()) {
					theBuilder.append("(").append(iterator.next());
				}

				while (iterator.hasNext()) {
					theBuilder.append(", ").append(iterator.next());
				}

				theBuilder.append("), ");
			}
		}

		if (inJoinTables != null) {
			for (final String theTable : inJoinTables) {
				theTables.append(", ").append(theTable);
			}
		}

		final StringBuilder theQuery = new StringBuilder("select ");
		theQuery.append(((inTool != null) ? theBuilder.toString() : StringShop.EMPTY_STRING));
		theQuery.append((theSubQuery != null) ? theSubQuery : StringShop.EMPTY_STRING);
		theQuery.append(" from ").append(theTables);
		if (condition != null) {
			theQuery.append(" where ").append(condition);
		}

		if (inGroupBy != null) {
			theQuery.append(" group by ").append(inGroupBy);
		}

		if (inOrderBy != null) {
			theQuery.append(" order by ").append(inOrderBy);
		}
		if (nbRows > 0) {
			theQuery.append(" limit ");
			if (indexRows > 0) {
				theQuery.append(indexRows).append(StringShop.COMMA);
			}
			theQuery.append(nbRows);
		}

		AbstractSQLRecord.LOGGER.debug("Query : " + theQuery);
		AbstractSQLRecord.LOGGER.debug("Values : " + values);

		final int theFetchSize;
		if ((nbRows > 0) && (inFetchSize > nbRows)) {
			theFetchSize = 0;
		} else {
			theFetchSize = inFetchSize;
		}

		return inConnection.doQueryPS(theQuery.toString(), values, theFetchSize, readOnMasterDB);
	}

	/**
	 * Récupère le result set à partir de la condition.
	 *
	 * @param inJoinTables liste des tables avec lesquelles on fait une
	 *            jointure, ou <code>null</code>.
	 * @param nbRows nombre de rangs souhaités, 0 pour tous.
	 * @param inOrderBy clause pour l'ordre (e.g. nom de la colonne), ou
	 *            <code>null</code>.
	 * @return un ResultSet (éventuellement vide).
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected static <T extends AbstractSQLRecord> long countRecords(SgbdConnection inConnection, SQLSpecification<T> inSpec, String[] inJoinTables, String condition, List<Object> values, String inGroupBy) throws SQLException {
		final StringBuilder theTables = new StringBuilder(inSpec.getTableName());

		if (inJoinTables != null) {
			for (final String theTable : inJoinTables) {
				theTables.append(", ").append(theTable);
			}
		}
		final StringBuilder theQuery = new StringBuilder("select count(*) from ").append(theTables);
		if (condition != null) {
			theQuery.append(" where ").append(condition);
		}
		if (inGroupBy != null) {
			theQuery.append(" order by ").append(inGroupBy);
		}

		final ResultSetWorker theWorker = new ResultSetWorker() {

			public Object process(ResultSet inResultSet) throws SQLException {
				return (inResultSet.next()) ? inResultSet.getLong(1) : 0L;
			}
		};

		return (Long) inConnection.doQueryPS(theQuery.toString(), values, theWorker, 0);
	}

	/**
	 * Supprime un enregistrement à partir de son id.
	 *
	 * @param inId Id de l'enregistrement à supprimer.
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected void deleteRecord(String condition, List<Object> theConditionVals) {
		final SgbdConnection theConnection = new SgbdConnection();
		try {
			final SQLSpecification<? extends Record> theSpec = getSpecification();
			theConnection.doQueryInsertPS("delete from " + theSpec.getTableName() + " where " + condition, theConditionVals);
		} finally {
			theConnection.close();
		}
	}

	/**
	 * Met à jour le tuple dans une base de données. Version avec une table de
	 * valeurs à changer (utilisée pour les données répliquées).
	 *
	 * @param condition condition SQL pour faire l'update
	 * @param theConditionVals valeurs pour satisfaire la condition SQL
	 * @param inValues valeurs qui remplacent les valeurs de l'objet, ou null si
	 *            on ignore.
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected void updateRecord(String[] inColumns, String condition, List<Object> theConditionVals) throws SQLException {
		final Class<? extends Record> theClass = getSpecification().getRecordClass();
		final List<Object> theVals = new LinkedList<Object>();
		String theKeyValStr = null;
		final SQLSpecification<? extends Record> theSpec = getSpecification();
		// Préparation du statement.
		for (final String theCol : inColumns) {
			final Object theVal;
			// Récupération de la valeur par reflect.
			Field theField;
			try {
				theField = theClass.getDeclaredField(theCol);
			} catch (final NoSuchFieldException anException) {
				// On lance ça comme si c'était une erreur SQL, mais
				// en pratique c'est une erreur dans le code.
				final SQLException myException = new SQLException("Unknown field: " + theCol);
				myException.initCause(anException);
				throw myException;
			}
			try {
				theVal = getFieldValue(theField);
			} catch (final IllegalAccessException anException) {
				final SQLException myException = new SQLException("Illegal Access: " + theCol);
				myException.initCause(anException);
				throw myException;
			}
			if (theKeyValStr == null) {
				theKeyValStr = theCol + "= ?";
			} else {
				theKeyValStr += ", " + theCol + "= ?";
			}
			theVals.add(theVal);
		}

		theVals.addAll(theConditionVals);

		doUpdateRecord(theSpec, theKeyValStr, condition, theVals);
	}

	private void doUpdateRecord(SQLSpecification<? extends Record> inSpec, String inKeyVal, String inCondition, List<Object> inConditionVals) throws SQLException {
		final String theQuery = "update " + inSpec.getTableName() + " set " + inKeyVal + " where " + inCondition;

		final SgbdConnection theConnection = new SgbdConnection();
		try {
			theConnection.doQueryUpdatePS(theQuery, inConditionVals);
		} finally {
			theConnection.close();
		}
	}

	/**
	 * Met à jour le tuple dans une base de données. Version avec une table de
	 * valeurs à changer (utilisée pour les données répliquées).
	 *
	 * @param inValues valeurs à mettre à jour.
	 * @param condition condition SQL pour faire l'update
	 * @param theConditionVals valeurs pour satisfaire la condition SQL
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected void updateRecord(Map<String, Object> inValues, String condition, List<Object> theConditionVals) throws SQLException {
		if (!inValues.isEmpty()) {
			final List<Object> theVals = new LinkedList<Object>();
			String theKeyValStr = null;
			final SQLSpecification<? extends Record> theSpec = getSpecification();

			final Map<String, Object> theUpdateMap;
			// Gestion de la colonne timestamp a mettre a jour
			if (theSpec.getUpdateColumn() != null) {
				theUpdateMap = new HashMap<String, Object>(inValues);
				final String fieldName = theSpec.getUpdateColumn();
				final Class<? extends Record> theClass = theSpec.getRecordClass();
				Field theField = null;
				try {
					theField = theClass.getDeclaredField(fieldName);
				} catch (final NoSuchFieldException anException) {
					final SQLException theException = new SQLException("Cannot find field " + fieldName);
					theException.initCause(anException);
					throw theException;
				}

				final Timestamp theTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
				try {
					theField.setAccessible(true);
					theField.set(this, theTimestamp);
				} catch (final Exception e) {
					final SQLException theException = new SQLException("Cannot update value for field " + fieldName);
					theException.initCause(e);
					throw theException;
				} finally {
					theField.setAccessible(false);
				}

				theUpdateMap.put(fieldName, theTimestamp);
			} else {
				theUpdateMap = inValues;
			}

			// Préparation du statement.
			for (final Map.Entry<String, Object> theEntry : theUpdateMap.entrySet()) {
				final Object theVal = theEntry.getValue();
				final String theCol = theEntry.getKey();
				if (theKeyValStr == null) {
					theKeyValStr = theCol + "= ?";
				} else {
					theKeyValStr += ", " + theCol + "= ?";
				}
				theVals.add(theVal);
			}

			theVals.addAll(theConditionVals);

			doUpdateRecord(theSpec, theKeyValStr, condition, theVals);
		}
	}

	/**
	 * Insère un enregistrement dans la base.
	 *
	 * @param inConnection connexion sur la base (de la bonne zone).
	 * @param inValues noms des colonnes avec valeurs d'initialisation.
	 * @param inVals valeurs qui remplacent celles de l'objet (ou
	 *            <code>null</code> )
	 * @param inIdColName
	 * @return l'id de l'enregistrement.
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected void insertRecord(SgbdConnection inConnection) throws SQLException {
		final SQLSpecification<? extends Record> theSpec = getSpecification();
		insertRecord(inConnection, theSpec.getColumns(), null, null);
	}

	/**
	 * Insère un enregistrement dans la base. Méthode générique qui permet
	 * d'insérer un AbstractRecord comme un ObjectRecord (avec id).
	 *
	 * @param inConnection connexion sur la base (de la bonne zone).
	 * @param inInitializedCols noms des colonnes avec valeurs d'initialisation.
	 * @param inVals valeurs qui remplacent celles de l'objet (ou
	 *            <code>null</code> )
	 * @param inIdColName nom de la colonne avec id ou <code>null</code> pour un
	 *            AbstractRecord.
	 * @return l'id de l'enregistrement.
	 * @throws SQLException en cas d'erreur SQL.
	 */
	protected long insertRecord(SgbdConnection inConnection, String[] inInitializedCols, Map<String, Object> inVals, String inIdColName) throws SQLException {
		// Préparation du statement.
		StringBuilder theColsStr = null;
		final List<Object> theVals = new LinkedList<Object>();
		StringBuilder theValsStr = null;
		final SQLSpecification<? extends Record> theSpec = getSpecification();
		final Class<? extends Record> theClass = theSpec.getRecordClass();
		boolean idIsInValues = false;
		long theResult = 0;
		for (final String theColName : inInitializedCols) {
			Object theVal = null;
			if (inVals != null) {
				theVal = inVals.get(theColName);
			}
			if (theVal == null) {
				Field theField = null;
				try {
					theField = theClass.getDeclaredField(theColName);
				} catch (final NoSuchFieldException anException) {
					final SQLException theException = new SQLException("Cannot find field " + theColName);
					theException.initCause(anException);
					throw theException;
				}
				try {
					theVal = getFieldValue(theField);
				} catch (final IllegalAccessException anException) {
					final SQLException theException = new SQLException("Cannot access field " + theColName);
					theException.initCause(anException);
					throw theException;
				}
			}
			if (theColsStr == null || theValsStr == null) {
				theColsStr = new StringBuilder("`" + theColName + "`");
				theValsStr = new StringBuilder("?");
			} else {
				theColsStr.append(", `" + theColName + "`");
				theValsStr.append(", ?");
			}
			theVals.add(theVal);

			if ((inIdColName != null) && !idIsInValues) {
				if (theColName.equals(inIdColName)) {
					idIsInValues = true;
					if (theVal instanceof Long) {
						theResult = (Long) theVal;
					} else if (theVal instanceof Integer) {
						theResult = (Integer) theVal;
					}
				}
			}
		}

		final String theQuery = "insert into " + theSpec.getTableName() + "(" + theColsStr + ") values(" + theValsStr + ")";
		AbstractSQLRecord.LOGGER.debug(theQuery);
		AbstractSQLRecord.LOGGER.debug("insert vals : " + theVals);
		if (!idIsInValues && (inIdColName != null)) {
			// Pas d'Id, on récupère celui qui a été auto-généré.
			final SgbdResult theSgbdResult = inConnection.doQueryInsertAutoGeneratedKeysPS(theQuery, theVals);
			try {
				final ResultSet theInsertResult = theSgbdResult.getResultSet();
				if (theInsertResult == null) {
					throw new SQLException("Error while inserting the record, table " + theSpec.getTableName());
				}
				if (!theInsertResult.next()) {
					throw new SQLException("Error while inserting the record (no key was returned!), " + "table " + theSpec.getTableName());
				}
				theResult = theInsertResult.getLong(1);
			} finally {
				theSgbdResult.close();
			}
		} else {
			final int nbRecords = inConnection.doQueryInsertPS(theQuery, theVals);
			if (nbRecords != 1) {
				throw new SQLException("Error while inserting the record");
			}
		}
		return theResult;
	}

	// ////////////////////// ACCESSEURS STATIQUES ////////////////////////

	/**
	 * Accesseur (statique) sur un objet déjà dans la base, à partir d'une
	 * condition (1 seul row)
	 *
	 * @param inSpec
	 * @param condition ex: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> Intf find(SQLSpecification<T> inSpec, String condition, List<Object> values) throws SQLException {
		Intf theResult = null;
		final SgbdConnection theConnection = new SgbdConnection(inSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
		try {
			final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecord(theConnection, inSpec, null, condition, values);
			try {
				final ResultSet theResultSet = theSgbdResult.getResultSet();
				if (theResultSet.next()) {
					theResult = AbstractSQLRecord.createObject(inSpec, theResultSet);
				}
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	/**
	 * Accesseur (statique) sur un objet déjà dans la base, à partir d'une
	 * condition (1 seul row)
	 *
	 * @param inSpec
	 * @param condition ex: path = ? and sign= ?
	 * @param inJoinTables tables pour la jointure.
	 * @param values les valeurs pour la condition
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> Intf find(SQLSpecification<T> inSpec, String[] inJoinTables, String condition, List<Object> values) throws SQLException {
		Intf theResult = null;
		final SgbdConnection theConnection = new SgbdConnection(inSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
		try {
			final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecord(theConnection, inSpec, inJoinTables, condition, values);
			try {
				final ResultSet theResultSet = theSgbdResult.getResultSet();
				if (theResultSet.next()) {
					theResult = AbstractSQLRecord.createObject(inSpec, theResultSet);
				}
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	/**
	 * Accesseur (statique) sur un objet déjà dans la base, à partir d'une
	 * condition (1 seul row)
	 *
	 * @param inSpec
	 * @param condition ex: path = ? and sign= ?
	 * @param inJoinTables tables pour la jointure.
	 * @param values les valeurs pour la condition
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> Intf find(SQLSpecification<T> inSpec, String[] inJoinTables, String condition, List<Object> values, String inOrderBy) throws SQLException {
		Intf theResult = null;
		final SgbdConnection theConnection = new SgbdConnection(inSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
		try {
			final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecords(theConnection, inSpec, inJoinTables, condition, values, 0, 1, inOrderBy, null, null, null, 0 /*
																																																																																																																																																																																																	 * fetch
																																																																																																																																																																																																	 * size
																																																																																																																																																																																																	 */);
			try {
				final ResultSet theResultSet = theSgbdResult.getResultSet();
				if (theResultSet.next()) {
					theResult = AbstractSQLRecord.createObject(inSpec, theResultSet);
				}
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	/**
	 * Accesseur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition.
	 *
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	protected static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> List<Intf> findAll(SQLSpecification<T> inSpec, String condition, List<Object> values) throws SQLException {
		return AbstractSQLRecord.findAll(inSpec, null, condition, values);
	}

	/**
	 * Accesseur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition.
	 *
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> List<Intf> findAll(SQLSpecification<T> inSpec, String condition, List<Object> values, String inOrderBy) throws SQLException {
		return AbstractSQLRecord.findAll(inSpec, null, condition, values, inOrderBy);
	}

	/**
	 * Accesseur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition.
	 *
	 * @param inZone sur quelle base on effectue la requête
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> List<Intf> findAll(SQLSpecification<T> inSpec, String condition, List<Object> values, String inOrderBy, int inIndexRows, int inNbRows) throws SQLException {
		final List<Intf> theResult = new LinkedList<Intf>();
		final SgbdConnection theConnection = new SgbdConnection(inSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
		try {
			final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecords(theConnection, inSpec, null, condition, values, inIndexRows, inNbRows, inOrderBy, null, null, null, 0);
			try {
				final ResultSet theResultSet = theSgbdResult.getResultSet();
				while (theResultSet.next()) {
					theResult.add(AbstractSQLRecord.createObject(inSpec, theResultSet));
				}
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	/**
	 * Accesseur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition.
	 *
	 * @param inZone sur quelle base on effectue la requête
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> List<Intf> findAll(SQLSpecification<T> inSpec, String[] inJoinTables, String condition, List<Object> values, String inOrderBy, int inIndexRows, int inNbRows) throws SQLException {
		final List<Intf> theResult = new LinkedList<Intf>();
		final SgbdConnection theConnection = new SgbdConnection(inSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
		try {
			final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecords(theConnection, inSpec, inJoinTables, condition, values, inIndexRows, inNbRows, inOrderBy, null, null, null, 0);
			try {
				final ResultSet theResultSet = theSgbdResult.getResultSet();
				while (theResultSet.next()) {
					theResult.add(AbstractSQLRecord.createObject(inSpec, theResultSet));
				}
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	/**
	 * Accesseur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition, avec jointure.
	 *
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	protected static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> List<Intf> findAll(SQLSpecification<T> inSpec, String[] inJoinTables, String condition, List<Object> values) throws SQLException {
		return AbstractSQLRecord.findAll(inSpec, inJoinTables, condition, values, null);
	}

	/**
	 * Accesseur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition, avec jointure.
	 *
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> List<Intf> findAllDistinct(SQLSpecification<T> inSpec, String[] inJoinTables, String condition, List<Object> values, String inOrderBy) throws SQLException {
		final List<Intf> theResult = new LinkedList<Intf>();
		AbstractSQLRecord.findAll(inSpec, inJoinTables, condition, values, inOrderBy, theResult, SQL_TOOLS.DISTINCT);
		return theResult;
	}

	/**
	 * Accesseur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition, avec jointure.
	 *
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> List<Intf> findAll(SQLSpecification<T> inSpec, String[] inJoinTables, String condition, List<Object> values, String inOrderBy) throws SQLException {
		final List<Intf> theResult = new LinkedList<Intf>();

		AbstractSQLRecord.findAll(inSpec, inJoinTables, condition, values, inOrderBy, theResult, null);

		return theResult;
	}

	/**
	 * Accesseur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition, avec jointure.
	 *
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inOrderBy clause pour ordonner les enregistrements, ou
	 *            <code>null</code> .
	 * @parem outObjects collection qui contient les objets en sortie.
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> void findAll(SQLSpecification<T> inSpec, String[] inJoinTables, String condition, List<Object> values, String inOrderBy, Collection<Intf> outObjects) throws SQLException {
		AbstractSQLRecord.findAll(inSpec, inJoinTables, condition, values, inOrderBy, outObjects, null);
	}

	/**
	 * Accesseur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition, avec jointure.
	 *
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inOrderBy clause pour ordonner les enregistrements, ou
	 *            <code>null</code> .
	 * @parem outObjects collection qui contient les objets en sortie.
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	private static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> void findAll(SQLSpecification<T> inSpec, String[] inJoinTables, String condition, List<Object> values, String inOrderBy, Collection<Intf> outObjects, SQL_TOOLS inTool) throws SQLException {
		final SgbdConnection theConnection = new SgbdConnection(inSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
		try {
			final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecords(theConnection, inSpec, inJoinTables, condition, values, 0, 0, inOrderBy, inTool, null, null, 0);
			try {
				final ResultSet theResultSet = theSgbdResult.getResultSet();
				while (theResultSet.next()) {
					outObjects.add(AbstractSQLRecord.createObject(inSpec, theResultSet));
				}
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition.
	 *
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @return le nombre d'enregistrements traités.
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> int walk(SQLSpecification<T> inSpec, String condition, List<Object> values, String inOrderBy, int inSkip, RecordWalker<Intf> inWalker) throws SQLException {
		return AbstractSQLRecord.walk(inSpec, condition, values, null, inOrderBy, inSkip, inWalker);
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition.
	 *
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @return le nombre d'enregistrements traités.
	 */
	static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> int walk(SQLSpecification<T> inSpec, String condition, List<Object> values, String inOrderBy, int inSkip, int indexRows, int nbRows, RecordWalker<Intf> inWalker) throws SQLException {
		return AbstractSQLRecord.walk(inSpec, condition, values, null, inOrderBy, inSkip, indexRows, nbRows, inWalker);
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition ( avec jointure).
	 *
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @param inSkip nombre de lignes que l'on omet
	 * @return le nombre d'enregistrements traités.
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> int walk(SQLSpecification<T> inSpec, String condition, List<Object> values, String[] inJoinTables, String inOrderBy, int inSkip, RecordWalker<Intf> inWalker) throws SQLException {
		return AbstractSQLRecord.walk(inSpec, condition, values, inJoinTables, inOrderBy, inSkip, null, null, inWalker);
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition ( avec jointure).
	 *
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @param inSkip nombre de lignes que l'on omet
	 * @return le nombre d'enregistrements traités.
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> int walk(SQLSpecification<T> inSpec, String condition, List<Object> values, String[] inJoinTables, String inOrderBy, int inSkip, SQL_TOOLS inTools, List<String> inDistinctKeys, RecordWalker<Intf> inWalker) throws SQLException {
		int theResult = 0;
		final SgbdConnection theConnection = new SgbdConnection(inSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
		try {
			final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecords(theConnection, inSpec, inJoinTables, condition, values, 0, 0, inOrderBy, inTools, inDistinctKeys, null, AbstractSQLRecord.WALK_FETCHSIZE);
			final ResultSet theResultSet = theSgbdResult.getResultSet();
			try {
				int nbSkip = inSkip;
				while (theResultSet.next()) {
					if (nbSkip == 0) {
						final Intf theObject = AbstractSQLRecord.createObject(inSpec, theResultSet);
						inWalker.process(theObject);
						theResult++;
					} else {
						nbSkip--;
					}
				}
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition ( avec jointure).
	 *
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @param inSkip nombre de lignes que l'on omet
	 * @return le nombre d'enregistrements traités.
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> int walk(SQLSpecification<T> inSpec, String condition, List<Object> values, String[] inJoinTables, String inOrderBy, int inSkip, SQL_TOOLS inTools, List<String> inDistinctKeys, String inGroupBy, RecordWalker<Intf> inWalker) throws SQLException {
		int theResult = 0;
		final SgbdConnection theConnection = new SgbdConnection(inSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
		try {
			final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecords(theConnection, inSpec, inJoinTables, condition, values, 0, 0, inOrderBy, inTools, inDistinctKeys, inGroupBy, AbstractSQLRecord.WALK_FETCHSIZE);
			final ResultSet theResultSet = theSgbdResult.getResultSet();
			try {
				int nbSkip = inSkip;
				while (theResultSet.next()) {
					if (nbSkip == 0) {
						final Intf theObject = AbstractSQLRecord.createObject(inSpec, theResultSet);
						inWalker.process(theObject);
						theResult++;
					} else {
						nbSkip--;
					}
				}
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition ( avec jointure).
	 *
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @return le nombre d'enregistrements traités.
	 */
	static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> int walk(SQLSpecification<T> inSpec, String condition, List<Object> values, String[] inJoinTables, String inOrderBy, int inSkip, int indexRows, int nbRows, RecordWalker<Intf> inWalker) throws SQLException {
		int theResult = 0;
		final SgbdConnection theConnection = new SgbdConnection(inSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
		try {
			final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecords(theConnection, inSpec, inJoinTables, condition, values, indexRows, nbRows, inOrderBy, null, null, null, AbstractSQLRecord.WALK_FETCHSIZE);
			final ResultSet theResultSet = theSgbdResult.getResultSet();
			try {
				int nbSkip = inSkip;
				while (theResultSet.next()) {
					if (nbSkip == 0) {
						final Intf theObject = AbstractSQLRecord.createObject(inSpec, theResultSet);
						inWalker.process(theObject);
						theResult++;
					} else {
						nbSkip--;
					}
				}
			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition. Version avec deux enregistrements joints.
	 *
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @return le nombre d'enregistrements traités.
	 */
	public static <Intf1 extends Record<Intf1>, Intf2 extends Record<Intf2>, T extends AbstractSQLRecord<Intf1, T>, U extends AbstractSQLRecord<Intf2, U>> int walk(SQLSpecification<T> inSpec1, SQLSpecification<U> inSpec2, String condition, List<Object> values, String inOrderBy, int inSkip, JoinRecordsWalker<Intf1, Intf2> inWalker) throws SQLException {
		return AbstractSQLRecord.walk(inSpec1, inSpec2, condition, values, null, inOrderBy, inSkip, 0, 0, null, null, inWalker);
	}

	static <Intf1 extends Record<Intf1>, Intf2 extends Record<Intf2>, T extends AbstractSQLRecord<Intf1, T>, U extends AbstractSQLRecord<Intf2, U>> int walk(SQLSpecification<T> inSpec1, SQLSpecification<U> inSpec2, String condition, List<Object> values, String inOrderBy, int inSkip, int indexRows, int nbRows, JoinRecordsWalker<Intf1, Intf2> inWalker) throws SQLException {
		return AbstractSQLRecord.walk(inSpec1, inSpec2, condition, values, null, inOrderBy, inSkip, indexRows, nbRows, null, null, inWalker);
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition. Version avec deux enregistrements joints et jointure sur
	 * d'autres tables.
	 *
	 * @param inSpec spécification SQL.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param inOrderBy clause pour l'ordre (ou <code>null</code>).
	 * @return le nombre d'enregistrements traités.
	 */

	private static final Semaphore DB_GUARDIAN = new Semaphore(15);

	public static <Intf1 extends Record<Intf1>, Intf2 extends Record<Intf2>, T extends AbstractSQLRecord<Intf1, T>, U extends AbstractSQLRecord<Intf2, U>> int walk(SQLSpecification<T> inSpec1, SQLSpecification<U> inSpec2, String condition, List<Object> values, String[] inJoinTables, String inOrderBy, int inSkip, int indexRows, int nbRows, SQL_TOOLS inTool, List<String> inDistinctKeys, JoinRecordsWalker<Intf1, Intf2> inWalker) throws SQLException {
		int theResult = 0;
		boolean semaphoreMustBeReleased = false;
		try {
			AbstractSQLRecord.DB_GUARDIAN.acquire();
			semaphoreMustBeReleased = true;

			final SgbdConnection theConnection = new SgbdConnection(inSpec1.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
			try {
				final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecords(theConnection, new SQLSpecification[] { inSpec1, inSpec2 }, inJoinTables, condition, values, indexRows, nbRows, inOrderBy, inTool, inDistinctKeys, null, AbstractSQLRecord.WALK_FETCHSIZE, false);
				final ResultSet theResultSet = theSgbdResult.getResultSet();

				AbstractSQLRecord.DB_GUARDIAN.release();
				semaphoreMustBeReleased = false;

				try {
					int nbSkip = inSkip;
					while (theResultSet.next()) {
						if (nbSkip == 0) {
							final Intf1 theObject1 = AbstractSQLRecord.createObject(inSpec1, theResultSet);
							final Intf2 theObject2 = AbstractSQLRecord.createObject(inSpec2, theResultSet);
							inWalker.process(theObject1, theObject2);
							theResult++;
						} else {
							nbSkip--;
						}
					}
				} finally {
					theSgbdResult.close();
				}
			} finally {
				theConnection.close();
			}
		} catch (final InterruptedException e) {
			AbstractSQLRecord.LOGGER.fatal(e, e);
		} finally {
			if (semaphoreMustBeReleased) {
				AbstractSQLRecord.DB_GUARDIAN.release();
			}
		}

		return theResult;
	}

	static <Intf1 extends Record<Intf1>, Intf2 extends Record<Intf2>, T extends AbstractSQLRecord<Intf1, T>, U extends AbstractSQLRecord<Intf2, U>> int walk(SQLSpecification<T> inSpec1, SQLSpecification<U> inSpec2, String condition, List<Object> values, String[] inJoinTables, String inOrderBy, int inSkip, JoinRecordsWalker<Intf1, Intf2> inWalker) throws SQLException {
		return AbstractSQLRecord.walk(inSpec1, inSpec2, condition, values, inJoinTables, inOrderBy, inSkip, 0, 0, null, null, inWalker);
	}

	/**
	 * Accesseur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition, avec jointure.
	 *
	 * @param inSpec spécification SQL.
	 * @param inJoinTables tables sur lesquelles on fait la jointure.
	 * @param condition par exemple: path = ? and sign= ?
	 * @param values les valeurs pour la condition
	 * @param inGroupBy clause pour grouper les enregistrements, ou
	 *            <code>null</code>.
	 * @return une instance pour la condition correspondant (celle dans le cache
	 *         ou une nouvelle si nécessaire).
	 */
	public static <T extends AbstractSQLRecord> long count(SQLSpecification<T> inSpec, String[] inJoinTables, String condition, List<Object> values, String inGroupBy) throws SQLException {
		final SgbdConnection theConnection = new SgbdConnection(inSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
		final long theResult;
		try {
			theResult = AbstractSQLRecord.countRecords(theConnection, inSpec, inJoinTables, condition, values, inGroupBy);
		} finally {
			theConnection.close();
		}
		return theResult;
	}

	// ////////////////////// ACCESSEURS ////////////////////////

	/**
	 * Si on n'a pas besoin de recharger l'élément depuis la base.
	 *
	 * @return <code>true</code> si l'élément n'est pas INVALID ni DELETED.
	 */
	public final boolean isValid() {
		return (this.mState != State.DELETED) && (this.mState != State.INVALID);
	}

	public final boolean isDeleted() {
		return this.mState == State.DELETED;
	}

	public final boolean isInvalid() {
		return this.mState == State.INVALID;
	}

	/**
	 * Accesseur sur l'état.
	 *
	 * @return l'état de l'enregistrement.
	 */
	public final State getState() {
		return this.mState;
	}

	/**
	 * Sélecteur sur l'état.
	 *
	 * @param inState nouvel état de l'enregistrement.
	 */
	public final void setState(State inState) {
		this.mState = inState;
	}

	/**
	 * Accesseur sur la spécification.
	 *
	 * @return la spécification pour cet enregistrement.
	 */
	public abstract SQLSpecification<Impl> getSpecification();

	/**
	 * Déclare l'objet comme propre.
	 */
	protected final void setClean() {
		this.mState = State.CLEAN;
	}

	/**
	 * Déclare l'objet comme supprimé.
	 */

	public final void setDeleted() {
		this.mState = State.DELETED;

		// On prévient les listeners.
		for (final RecordDeletionListener<Intf> theListener : this.mRecordDeletionListener) {
			theListener.recordDeleted((Intf) this);
		}
	}

	/**
	 * Enregistre le tuple dans la/les bases de données.
	 *
	 * @param inColumns colonnes à mettre à jour.
	 * @throws SQLException si une erreur est survenue dans l'enregistrement.
	 */
	public final void update(Map<String, Object> inUpdateMap) {
		if (!inUpdateMap.isEmpty()) {
			try {
				doUpdate(inUpdateMap);
				Cache.broadcastModification(this);
			} catch (final SQLException anException) {
				AbstractSQLRecord.LOGGER.fatal(anException, anException);
			}
		}
	}

	protected final void updateKey(String field) {
		final SQLKey[] tableKeys = getSpecification().getTableKeys();
		// On commence à 1 car l'on exclu la clé primaire
		for (int i = 1; i < tableKeys.length; i++) {
			final List<String> listkeyField = Arrays.asList(tableKeys[i].getKeys());
			if (listkeyField.contains(field)) {
				invalidKeys();
				Cache.broadcastModification(this);
				break;
			}
		}
		this.invalidKeys = false;
	}

	/**
	 * Enregistre le tuple dans la/les bases de données.
	 *
	 * @param inColumns colonnes à mettre à jour.
	 * @throws SQLException si une erreur est survenue dans l'enregistrement.
	 */
	protected abstract void doUpdate(Map<String, Object> inObjectMap) throws SQLException;

	/**
	 * Supprime l'élément de la base de données.
	 *
	 * @return <code>true</code> si l'élément a bien été supprimé.
	 */
	public final boolean delete() {
		boolean theResult = false;
		if ((this.mState != State.NEW) && (this.mState != State.DELETED)) {
			try {
				doDelete();
				theResult = true;
				Cache.broadcastDelete(this);
			} catch (final SQLException anException) {
				AbstractSQLRecord.LOGGER.fatal(anException, anException);
			}

			setDeleted();
		}
		return theResult;
	}

	/**
	 * Supprime l'élément de la base de données.
	 *
	 * @throws SQLException si une erreur est survenue.
	 */
	protected abstract void doDelete() throws SQLException;

	// ////////////////////// FONCTIONS PRIVEES ////////////////////////

	/**
	 * check si le resultSet n'est pas vide puis récupère les valeurs de
	 * l'enregistrement dans l'objet java.
	 *
	 * @param inResultSet résultat de la requête select.
	 * @throws SQLException en cas d'erreur SQL ou d'accès java.
	 * @throws NoSuchElementException s'il n'y a pas d'élémént dans le résultat.
	 */
	protected void setColumns(ResultSet inResultSet, String inPrefix) throws SQLException, NoSuchElementException {
		if (!inResultSet.next()) {
			throw new NoSuchElementException();
		}
		doSetColumns(inResultSet, inPrefix);
	}

	/**
	 * Récupère les valeurs de l'enregistrement dans l'objet java.
	 *
	 * @param inResultSet résultat de la requête select.
	 * @throws SQLException en cas d'erreur SQL ou d'accès java.
	 */
	protected void doSetColumns(ResultSet inResultSet, String inPrefix) throws SQLException {
		final int theSuffixLen = inPrefix.length();

		final Class<? extends Record> theClass = getSpecification().getRecordClass();
		final ResultSetMetaData theMD = inResultSet.getMetaData();
		final int nbCols = theMD.getColumnCount();

		for (int indexCol = 1; indexCol <= nbCols; indexCol++) {
			final String theColName = theMD.getColumnName(indexCol);
			if (theColName.startsWith(inPrefix)) {
				final String theFieldName = theColName.substring(theSuffixLen);

				Field theField;
				try {
					theField = theClass.getDeclaredField(theFieldName);
				} catch (final NoSuchFieldException anException) {
					// On lance ça comme si c'était une erreur SQL, mais en
					// pratique c'est une erreur dans le code.
					final SQLException myException = new SQLException("Unknown field: " + theFieldName);
					myException.initCause(anException);
					throw myException;
				}

				try {
					theField.setAccessible(true);
					theField.set(this, inResultSet.getObject(theColName));
					/*if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Setting field value from resultset : " + theFieldName + "=" + inResultSet.getObject(theColName));
					}*/

				} catch (final ClassCastException anException) {
					LOGGER.error("Error setting field '"+theFieldName+"' on class '"+theClass.getName()+"'", anException);
					final String theClassName = theMD.getColumnClassName(indexCol);
					final Class<?> theType = theField.getType();
					final SQLException myException = new SQLException("Class cast exception for column " + theColName + " java=" + theType.getName() + " sql=" + theClassName);
					myException.initCause(anException);
					throw myException;
				} catch (final IllegalArgumentException anException) {
					LOGGER.error("Error setting field '"+theFieldName+"' on class '"+theClass.getName()+"'", anException);
					final String theClassName = theMD.getColumnClassName(indexCol);
					final Class<?> theType = theField.getType();
					final SQLException myException = new SQLException("Illegal argument exception for column " + theColName + " java=" + theType.getName() + " sql=" + theClassName);
					myException.initCause(anException);
					throw myException;
				} catch (final IllegalAccessException anException) {
					LOGGER.error("Error setting field '"+theFieldName+"' on class '"+theClass.getName()+"'", anException);
					final SQLException myException = new SQLException("Illegal Access: " + theFieldName);
					myException.initCause(anException);
					throw myException;
				} finally {
					theField.setAccessible(false);
				}
			}
		}
	}

	/**
	 * Crée l'objet avec initialisation par resultSet.
	 *
	 * @param inZone zone de l'objet.
	 * @param inSpec classe de l'objet
	 * @param inResultSet résultat de la requête select.
	 * @throws SQLException en cas d'erreur SQL ou d'accès java.
	 */

	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> Intf createObject(SQLSpecification<T> inSpec, ResultSet inResultSet) throws SQLException {
		final String inPrefix = inSpec.getTablePrefix();
		final Class<T> theClass = inSpec.getRecordClass();

		// Vérification que l'objet n'est pas déjà dans le cache.
		T theResult = Cache.get(inSpec, inResultSet);

		if (theResult == null) {
			Constructor<T> theConstructor = null;
			try {
				theConstructor = theClass.getDeclaredConstructor(AbstractSQLRecord.CONSTRUCTOR_SIGNATURE);
			} catch (final SecurityException anException) {
				AbstractSQLRecord.LOGGER.fatal(anException, anException);
			} catch (final NoSuchMethodException anException) {
				try {
					theConstructor = theClass.getDeclaredConstructor();
				} catch (final SecurityException e) {
					AbstractSQLRecord.LOGGER.fatal(e, e);
				} catch (final NoSuchMethodException e) {
					AbstractSQLRecord.LOGGER.fatal("Class " + theClass.getCanonicalName() + " should provide an empty constructor !", e);
				}
			}

			try {
				if (theConstructor != null) {
					try {
						theConstructor.setAccessible(true);
						theResult = theConstructor.newInstance();
					} finally {
						theConstructor.setAccessible(false);
					}
				}

			} catch (final IllegalArgumentException anException) {
				AbstractSQLRecord.LOGGER.fatal(anException, anException);
			} catch (final InstantiationException anException) {
				AbstractSQLRecord.LOGGER.fatal(anException, anException);
			} catch (final IllegalAccessException anException) {
				AbstractSQLRecord.LOGGER.fatal(anException, anException);
			} catch (final InvocationTargetException anException) {
				if (!(anException.getCause() instanceof NoSuchElementException)) {
					AbstractSQLRecord.LOGGER.fatal(anException, anException);
				}
			}
			if (theResult != null) {
				theResult.init(inResultSet, inPrefix);
			}
		} else {
			Cache.add(theResult);
		}
		return (Intf) theResult;
	}

	/**
	 * Ajoute un listener pour la suppression.
	 *
	 * @param inListener listener à ajouter.
	 */
	public void addDeletionListener(RecordDeletionListener<Intf> inListener) {
		this.mRecordDeletionListener.add(inListener);
	}

	/**
	 * Supprime un listener pour la suppression.
	 *
	 * @param inListener listener à supprimer.
	 */
	public void removeDeletionListener(RecordDeletionListener<Intf> inListener) {
		this.mRecordDeletionListener.remove(inListener);
	}

	@Override
	public String toString() {
		String abstractRecordString = "------------------------/\n";
		abstractRecordString += this.getClass().getName() + "\n";
		final Field[] theFields = this.getClass().getDeclaredFields();
		for (final Field theField : theFields) {
			final int theModifiers = theField.getModifiers();
			if (((theModifiers & Modifier.PROTECTED) == Modifier.PROTECTED) && ((theModifiers & Modifier.STATIC) == 0)) {
				try {
					abstractRecordString += theField.getName() + " [" + getFieldValue(theField) + "]\n";
				} catch (final IllegalAccessException iae) {

				}
			}
		}
		abstractRecordString += "------------------------/\n";
		return abstractRecordString;
	}

	/**
	 * Returns the id of the given object
	 *
	 * @param inObject
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Object getObjectId(ObjectRecord inObject) throws IllegalArgumentException {
		if (inObject == null) {
			throw new IllegalArgumentException("The given object or its id is null");
		}

		return inObject.getId();
	}

	/**
	 * Return a random element from the given {@link List} of {@link Record}
	 *
	 * @param inList
	 * @return
	 */
	private static final Random randomGenerator = new Random(System.currentTimeMillis());

	public static <T extends Record> T randomElementFromList(List<T> inList) {
		final int theConfigFileId = AbstractSQLRecord.randomGenerator.nextInt(inList.size());
		return inList.get(theConfigFileId);
	}

	/**
	 * Accesseur (statique) sur un objet déjà dans la base à partir de sa clé
	 * primaire (celle qui occupe la première position dans le tableau
	 * <code>keys</code> de l'object SQLKey).
	 *
	 * @param <T> classe de l'objet.
	 * @param theSpec
	 * @param val valeur de la clé primaire
	 * @return une instance de l'objet ou <code>NULL</code> si non trouvé
	 * @throws SQLException
	 */
	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> Intf findByKey(SQLSpecification<T> theSpec, SQLKey theSQLKey, Object val) throws SQLException {
		return AbstractSQLRecord.findByKey(theSpec, theSQLKey, new Object[] { val });
	}

	/**
	 * Accesseur (statique) sur un objet déjà dans la base à partir d'une ou
	 * plusieurs de ces clés (voir SQLKey)
	 *
	 * @param <T> classe de l'objet.
	 * @param theSpec spécification de l'enregistrement
	 * @param theSQLKey définition des clés de cet enregistrement
	 * @param vals tableau contenant les valeurs de recherche sur chaque clé de
	 *            l'enregistrement en base attention : 1-) l'ordre doit
	 *            respecter celui utiliser lors de l'appel du constructeur de
	 *            l'objet SQLKey 2-) toutes il doit y avoir une entrée pour
	 *            chaque clé, si cette entrée vaut <code>NULL</code> elle est
	 *            ignorée dans les critères de recherche
	 * @return une instance de l'objet ou <code>NULL</code> si non trouvé
	 * @throws SQLException
	 */

	public static <Intf extends Record<Intf>, T extends AbstractSQLRecord<Intf, T>> Intf findByKey(SQLSpecification<T> theSpec, SQLKey theSQLKey, Object[] vals) throws SQLException {
		Intf theObject = (Intf) Cache.get(theSpec.getRecordClass(), theSQLKey, vals);
		if (theObject == null) {
			String condition = StringShop.EMPTY_STRING;
			final List<Object> values = new ArrayList<Object>();
			int i = 0;
			for (final String key : theSQLKey.getKeys()) {
				if (vals[i] != null) {
					if (!condition.equals(StringShop.EMPTY_STRING)) {
						condition += "AND ";
					}
					condition += key + " = ? ";
					values.add(vals[i]);
				}
				i++;
			}
			if (condition.equals(StringShop.EMPTY_STRING) || values.isEmpty()) {
				throw new SQLException("SQL syntax error");
			}
			final SgbdConnection theConnection = new SgbdConnection(theSpec.getConnectionMode(), SgbdConnection.SGBD_ACCESS.READONLY);
			try {
				final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecord(theConnection, theSpec, null, condition, values);
				try {
					final ResultSet theResultSet = theSgbdResult.getResultSet();
					if (theResultSet.next()) {
						theObject = AbstractSQLRecord.createObject(theSpec, theResultSet);
					}
				} finally {
					theSgbdResult.close();
				}
			} finally {
				theConnection.close();
			}
		}
		return theObject;
	}

	public List<Object> getValuesFromSQLKey(SQLKey theKey) throws SQLException {
		final List<Object> values = new ArrayList<Object>();
		for (final String key : theKey.getKeys()) {
			try {
				final Field theField = this.getClass().getDeclaredField(key);
				values.add(getFieldValue(theField));
			} catch (final SecurityException anException) {
				final SQLException myException = new SQLException("Unaccessible field: " + key);
				myException.initCause(anException);
				throw myException;
			} catch (final NoSuchFieldException anException) {
				// On lance ça comme si c'était une erreur SQL, mais
				// en pratique c'est une erreur dans le code.
				final SQLException myException = new SQLException("Unknown field: " + key);
				myException.initCause(anException);
				throw myException;
			} catch (final IllegalArgumentException anException) {
				final SQLException myException = new SQLException("IllegalArgumentException with key: " + key);
				myException.initCause(anException);
				throw myException;
			} catch (final IllegalAccessException anException) {
				final SQLException myException = new SQLException("Unaccessible field: " + key);
				myException.initCause(anException);
				throw myException;
			}
		}
		return values;
	}

	public void updateFromDatabase() {
		try {
			final SQLSpecification<Impl> theSpecification = getSpecification();
			final String theCondition = theSpecification.getConditionClause();
			final List<Object> theValues = theSpecification.getConditionValues((Impl) this);
			final SgbdConnection theConnection = new SgbdConnection();
			try {
				final SgbdResult theSgbdResult = AbstractSQLRecord.fetchRecord(theConnection, getSpecification(), null, theCondition, theValues);
				try {
					final ResultSet theResultSet = theSgbdResult.getResultSet();
					if (theResultSet.next()) {
						doSetColumns(theResultSet, getSpecification().getTablePrefix());
					}
				} finally {
					theSgbdResult.close();
				}
			} finally {
				theConnection.close();
			}
		} catch (final SQLException e) {
			AbstractSQLRecord.LOGGER.fatal(e, e);
		}
	}

	public void invalidKeys() {
		this.invalidKeys = true;
	}

	public boolean haveInvalidKeys() {
		return this.invalidKeys;
	}

	public Object getFieldValue(Field inField) throws IllegalArgumentException, IllegalAccessException {
		try {
			inField.setAccessible(true);
			return inField.get(this);
		} finally {
			inField.setAccessible(false);
		}
	}

}
