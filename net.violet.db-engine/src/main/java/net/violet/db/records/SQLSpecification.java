package net.violet.db.records;

import java.sql.SQLException;
import java.util.List;

import net.violet.common.StringShop;
import net.violet.db.connector.Connector;

/**
 * Classe pour spécifier la table SQL, les colonnes et les colonnes d'ID pour
 * les enregistrements.
 * 
 * @param Impl implémentation.
 * @param Intf interface.
 */
public abstract class SQLSpecification<Impl extends AbstractSQLRecord> {

	/**
	 * Nom de la table SQL.
	 */
	private final String mTableName;

	/**
	 * Référence sur les colonnes SQL.
	 */
	private final String[] mColumns;

	/**
	 * Référence sur la classe Java associée.
	 */
	private final Class<Impl> mRecordClass;

	/**
	 * Préfixe pour cette table.
	 */
	private final String mTablePrefix;

	private final SQLKey[] mKeys;

	private final String updateField;

	private final boolean mCacheable;

	private final Connector.ConnectionMode mConnexionMode;

	SQLSpecification(String inTableName, Class<Impl> inRecordClass, SQLKey[] inSQLKey) {
		this(inTableName, inRecordClass, inSQLKey, null);
	}

	/**
	 * Constructeur.
	 * 
	 * @param inTableName nom de la table.
	 * @param inRecordClass classe java associée.
	 */
	protected SQLSpecification(String inTableName, Class<Impl> inRecordClass, SQLKey[] inSQLKey, String inUpdateField) {
		this(inTableName, inRecordClass, inSQLKey, inUpdateField, true, SgbdConnection.CONNECTION_MODE);
	}

	SQLSpecification(String inTableName, Class<Impl> inRecordClass, SQLKey[] inSQLKey, String inUpdateField, boolean inCacheable, Connector.ConnectionMode inConnexionMode) {
		this.mTableName = inTableName;
		this.mRecordClass = inRecordClass;

		this.mColumns = AbstractSQLRecord.getColumns(inRecordClass);
		this.mTablePrefix = SQLSpecification.makeTablePrefix(this.mTableName);
		this.mKeys = inSQLKey;
		this.updateField = inUpdateField;
		this.mCacheable = inCacheable;
		this.mConnexionMode = inConnexionMode;
	}

	/**
	 * Accesseur sur les clés de la table
	 * 
	 * @return les clés de la table
	 */
	public final SQLKey[] getTableKeys() {
		return this.mKeys;
	}

	/**
	 * Accesseur sur le nom de la table.
	 * 
	 * @return le nom de la table.
	 */
	public final String getTableName() {
		return this.mTableName;
	}

	/**
	 * Accesseur sur le nom de la table comme préfixe.
	 * 
	 * @return le nom de la table comme préfixe.
	 */
	public final String getTablePrefix() {
		return this.mTablePrefix;
	}

	public String makeColAlias(String inCol) {
		return this.mTablePrefix + inCol;
	}

	/**
	 * Accesseur sur le nom des colonnes SQL.
	 * 
	 * @return le nom des colonnes SQL.
	 */
	public final String[] getColumns() {
		return this.mColumns;
	}

	/**
	 * Accesseur sur la classe java associée.
	 * 
	 * @return la classe java associée.
	 */
	public final Class<Impl> getRecordClass() {
		return this.mRecordClass;
	}

	private static String makeTablePrefix(String inTableName) {
		return inTableName + String.valueOf(inTableName.hashCode()).replace("-", "_") + "_";
	}

	/**
	 * Retourne la première clé du tableau de clé
	 * 
	 * @return
	 */
	public final SQLKey getPrimaryKey() {
		return this.mKeys[0];
	}

	/**
	 * Accesseur sur la condition (SQL) pour identifier un élément unique.
	 * 
	 * @return la condition pour l'objet.
	 */
	public final String getConditionClause() {
		String conditionClause = StringShop.EMPTY_STRING;
		final SQLKey thePrimaryKey = getPrimaryKey();
		for (final String key : thePrimaryKey.getKeys()) {
			if (!conditionClause.equals(StringShop.EMPTY_STRING)) {
				conditionClause += "AND ";
			}
			conditionClause += key + " = ? ";
		}
		return conditionClause;
	}

	/**
	 * Accesseur sur les valeurs à utiliser avec la condition.
	 * 
	 * @param inRecord enregistrement.
	 * @return les valeurs associées.
	 */

	public final List<Object> getConditionValues(Impl inRecord) throws SQLException {
		final SQLKey thePrimaryKey = getPrimaryKey();
		return inRecord.getValuesFromSQLKey(thePrimaryKey);
	}

	public String getUpdateColumn() {
		return this.updateField;
	}

	public final boolean isCacheable() {
		return this.mCacheable;
	}

	public final Connector.ConnectionMode getConnectionMode() {
		return this.mConnexionMode;
	}

}
