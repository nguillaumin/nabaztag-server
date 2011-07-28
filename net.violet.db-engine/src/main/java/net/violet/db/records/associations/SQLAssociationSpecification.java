package net.violet.db.records.associations;

import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLSpecification;

public final class SQLAssociationSpecification<AImpl extends AssociationRecord> extends SQLSpecification<AImpl> {

	/**
	 * Constructeur sans clé complexe (juste l'id du parent).
	 * 
	 * @param inTableName nom de la table.
	 * @param inRecordClass classe java associée.
	 * @param inParentIdColName nom de la colonne avec l'id du parent.
	 * @param inIsReplicated si la table d'association est répliquée.
	 */
	public SQLAssociationSpecification(String inTableName, Class<AImpl> inRecordClass, SQLKey inSQLKey) {
		this(inTableName, inRecordClass, new SQLKey[] { inSQLKey }, null);
	}

	public SQLAssociationSpecification(String inTableName, Class<AImpl> inRecordClass, SQLKey inSQLKey, String inUpdateField) {
		this(inTableName, inRecordClass, new SQLKey[] { inSQLKey }, inUpdateField);
	}

	/**
	 * Constructeur avec plusieurs clés.
	 * 
	 * @param inTableName nom de la table.
	 * @param inRecordClass classe java associée.
	 * @param inParentIdColName nom de la colonne avec l'id du parent.
	 * @param inKeyColumns les autres colonnes utilisées pour la clé.
	 * @param inIsReplicated si la table d'association est répliquée.
	 */
	public SQLAssociationSpecification(String inTableName, Class<AImpl> inRecordClass, SQLKey[] inSQLKeys, String inUpdateField) {
		super(inTableName, inRecordClass, inSQLKeys, inUpdateField);
	}
}
