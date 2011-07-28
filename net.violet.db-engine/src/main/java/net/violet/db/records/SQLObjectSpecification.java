package net.violet.db.records;

import net.violet.db.connector.Connector;

/**
 * Classe pour spécifier la table SQL, les colonnes et les colonnes d'ID pour
 * les enregistrements.
 */
public class SQLObjectSpecification<T extends ObjectRecord> extends SQLSpecification<T> {

	public SQLObjectSpecification(String inTableName, Class<T> inRecordClass, SQLKey inKey) {
		super(inTableName, inRecordClass, new SQLKey[] { inKey });
	}

	public SQLObjectSpecification(String inTableName, Class<T> inRecordClass, SQLKey inKey, boolean inCacheable, Connector.ConnectionMode inConnectionMode) {
		super(inTableName, inRecordClass, new SQLKey[] { inKey }, null, inCacheable, inConnectionMode);
	}

	/**
	 * UpdateField : this field is supposed to contain a Timestamp and to be
	 * updated at each update on the row.
	 * 
	 * @param inTableName
	 * @param inRecordClass
	 * @param inKey
	 * @param updateField
	 */
	public SQLObjectSpecification(String inTableName, Class<T> inRecordClass, SQLKey inKey, String updateField) {
		super(inTableName, inRecordClass, new SQLKey[] { inKey }, updateField);
	}

	public SQLObjectSpecification(String inTableName, Class<T> inRecordClass, SQLKey[] inKeys) {
		super(inTableName, inRecordClass, inKeys);
	}

	public SQLObjectSpecification(String inTableName, Class<T> inRecordClass, SQLKey[] inKeys, String updateField) {
		super(inTableName, inRecordClass, inKeys, updateField);
	}

	/**
	 * Accesseur sur le nom de la colonne contenant l'id.
	 * 
	 * @return le nom de la colonne avec l'id.
	 */
	public final String getIdColName() {
		return getTableKeys()[0].getKeys()[0]; // Ici on a une clé unique
	}
}
