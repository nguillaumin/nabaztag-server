package net.violet.db.records.associations;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.common.StringShop;
import net.violet.db.cache.Cache;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.Record;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SQLSpecification;
import net.violet.db.records.SgbdConnection;
import net.violet.db.records.SgbdResult;

import org.apache.log4j.Logger;

/**
 * Classe pour les associations.
 * 
 * @param <RIntf> classe de l'objet sur lequel pointe l'association.
 * @param <AIntf> classe finale.
 */
public abstract class AssociationRecord<RIntf extends Record<RIntf>, AIntf extends AssoRecord<RIntf, AIntf>, AImpl extends AssociationRecord<RIntf, AIntf, AImpl>> extends AbstractSQLRecord<AIntf, AImpl> implements AssoRecord<RIntf, AIntf> {

	public static final String NO_ORDER = StringShop.EMPTY_STRING;

	public static final int NO_LIMIT = -1;

	private static final Logger LOGGER = Logger.getLogger(AssociationRecord.class);

	/**
	 * Constructeur par défaut.
	 */
	protected AssociationRecord() {
		// This space for rent.
	}

	/**
	 * Accesseur sur la spécification.
	 * 
	 * @return la spécification pour cet enregistrement.
	 */
	@Override
	public abstract SQLAssociationSpecification<AImpl> getSpecification();

	Object get(String fieldName) throws NoSuchFieldException, IllegalAccessException {
		final Field field = this.getClass().getDeclaredField(fieldName);
		return getFieldValue(field);
	}

	void set(String fieldName, Object inValue) throws NoSuchFieldException, IllegalAccessException {
		final Field field = this.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(this, inValue);
		field.setAccessible(false);
	}

	protected static <T extends ObjectRecord, D extends AssociationRecord> SgbdResult fetchRecords(SgbdConnection inConnection, SQLSpecification<D> inParentTableSpecification, Map<String, SQLObjectSpecification<T>> inChildTablesSpecification, String condition, List<Object> theConditionVals, String order, int limit) throws SQLException {
		String joinWhere = StringShop.EMPTY_STRING;
		String selectedParentFields = StringShop.EMPTY_STRING;
		String fromTables = " FROM " + inParentTableSpecification.getTableName();
		for (final String parentColumn : inParentTableSpecification.getColumns()) {
			if (!selectedParentFields.equals(StringShop.EMPTY_STRING)) {
				selectedParentFields += ", ";
			}
			selectedParentFields += inParentTableSpecification.getTableName() + "." + parentColumn + " AS " + inParentTableSpecification.makeColAlias(parentColumn);
		}
		String selectedChildFields = StringShop.EMPTY_STRING;
		for (final Map.Entry<String, SQLObjectSpecification<T>> joinEntry : inChildTablesSpecification.entrySet()) {
			final String joinOn = joinEntry.getKey();
			final SQLObjectSpecification<T> oneChild = joinEntry.getValue();
			final String childTableName = oneChild.getTableName();
			final String[] childColumns = oneChild.getColumns();
			final String childIdColName = oneChild.getIdColName();
			for (final String column : childColumns) {
				if (!selectedChildFields.equals(StringShop.EMPTY_STRING)) {
					selectedChildFields += ", ";
				}
				selectedChildFields += childTableName + "." + column + " AS " + oneChild.makeColAlias(column);
			}
			if (!joinWhere.equals(StringShop.EMPTY_STRING)) {
				joinWhere += " AND ";
			}
			joinWhere += inParentTableSpecification.getTableName() + "." + joinOn + "=" + childTableName + "." + childIdColName;
			if (!fromTables.equals(StringShop.EMPTY_STRING)) {
				fromTables += ", ";
			}
			fromTables += childTableName;
		}
		String query = null;
		query = "SELECT ";
		query += selectedParentFields;
		query += (selectedChildFields.equals(StringShop.EMPTY_STRING)) ? StringShop.EMPTY_STRING : ", " + selectedChildFields;
		query += fromTables + " WHERE " + condition;

		if (!joinWhere.equals(StringShop.EMPTY_STRING)) {
			query += " AND ";
		}

		query += joinWhere;

		if (!order.equals(StringShop.EMPTY_STRING)) {
			query += " ORDER BY " + order;
		}

		if (limit > 0) {
			query += " LIMIT " + limit;
		}

		AssociationRecord.LOGGER.debug("Query : " + query);

		return inConnection.doQueryPS(query, theConditionVals, 0, false);
	}

	/**
	 * Crée l'objet avec initialisation par resultSet.
	 * 
	 * @param inMasterZone zone principale de l'objet association.
	 * @param inReplicatedZone zone répliquée, ou <code>null</code>.
	 * @param inParentReplicatedId id du parent, dans la zone répliquée.
	 * @param inSpec classe de l'objet
	 * @param inResultSet résultat de la requête select.
	 * @throws SQLException en cas d'erreur SQL ou d'accès java.
	 */
	static <RIntf extends Record<RIntf>, AIntf extends AssoRecord<RIntf, AIntf>, T extends AssociationRecord<RIntf, AIntf, T>> AIntf createObject(SQLSpecification<T> inSpec, ResultSet inResultSet) throws SQLException {
		final AIntf theObject = AbstractSQLRecord.createObject(inSpec, inResultSet);
		return theObject;
	}

	/**
	 * Retourne les Field des colonnes à partir de la liste des noms des
	 * colonnes.
	 * 
	 * @param inRecordClass classe pour l'enregistrement.
	 * @param inColNames noms des colonnes
	 * @return un tableau de Field.
	 */
	static Field[] getFields(Class<? extends AssociationRecord> inRecordClass, String[] inKeyColumns) {
		final int nbKeys = inKeyColumns.length;
		final Field[] theResult = new Field[nbKeys];
		for (int theIndex = 0; theIndex < nbKeys; theIndex++) {
			final String theKey = inKeyColumns[theIndex];
			try {
				theResult[theIndex] = inRecordClass.getDeclaredField(theKey);
			} catch (final SecurityException anException) {
				final RuntimeException theException = new IllegalArgumentException("Impossible d'accéder à la colonne " + theKey);
				theException.initCause(anException);
				throw theException;
			} catch (final NoSuchFieldException anException) {
				final RuntimeException theException = new IllegalArgumentException("Impossible d'accéder à la colonne " + theKey);
				theException.initCause(anException);
				throw theException;
			}
		}
		return theResult;
	}

	List<Object> getConditionValues(Field[] inKeyColumns) {
		return getConditionValues(inKeyColumns, null, 0);
	}

	List<Object> getConditionValues(Field[] inKeyColumns, String inParentIdColName, long inParentId) {
		final List<Object> theValues = new LinkedList<Object>();
		for (final Field theField : inKeyColumns) {
			if (theField.getName().equals(inParentIdColName)) {
				theValues.add(inParentId);
			} else {
				try {
					theValues.add(getFieldValue(theField));
				} catch (final IllegalAccessException anException) {
					final RuntimeException theException = new IllegalArgumentException("Impossible d'accéder à la colonne " + theField);
					theException.initCause(anException);
					throw theException;
				}
			}
		}
		return theValues;
	}

	@Override
	protected final void doDelete() throws SQLException {
		final SQLAssociationSpecification<AImpl> theSpec = getSpecification();
		final String theCondition = theSpec.getConditionClause();
		final List<Object> theConditionVals = theSpec.getConditionValues((AImpl) this);
		deleteRecord(theCondition, theConditionVals);
	}

	@Override
	protected final void doUpdate(Map<String, Object> inUpdateMap) throws SQLException {
		final SQLAssociationSpecification<AImpl> theSpec = getSpecification();
		final String theCondition = theSpec.getConditionClause();
		final List<Object> theConditionVals = theSpec.getConditionValues((AImpl) this);
		updateRecord(inUpdateMap, theCondition, theConditionVals);
	}

	/**
	 * Initialisation du AssociationRecord. Pendant de la fonction
	 * <code>init</code> de AbstractRecord.
	 * 
	 * @see AbstractSQLRecord#init()
	 */
	void initClean() {
		Cache.add((AImpl) this);
		setClean();
	}
}
