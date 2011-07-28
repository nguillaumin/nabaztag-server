package net.violet.db.records;

public interface DBCollection {

	/**
	 * Méthode pour indiquer que la collection est invalide. Lors du prochain
	 * accès, on recharge la collection depuis la base de données.
	 */
	void setModified();

	/**
	 * Méthode pour indiquer que la collection est invalide, y compris pour les
	 * process distants. Lors du prochain accès, on recharge la collection
	 * depuis la base de données.
	 */
	void setAndBroadcastModified();

	/**
	 * @return la clé (primaire) de l'élément parent.
	 */
	SQLKey getParentRecordKey();

	/**
	 * @return les valeurs pour la clé (primaire) de l'élément parent.
	 */
	Object[] getParentRecordKeyVals();

	/**
	 * @return le nom de la table d'association.
	 */
	String getAssociationTableName();

	/**
	 * @return le nom de la colonne avec l'id du parent dans la table
	 *         d'association.
	 */
	String getParentIdColName();

	/**
	 * @return le nom de la colonne avec l'id des enfants dans la table
	 *         d'association.
	 */
	String getChildIdColName();
}
