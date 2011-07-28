package net.violet.db.records;

/**
 * Interface pour le callback lorsqu'un élément est rendu invalide.
 * 
 * @author paul
 */
public interface RecordDeletionListener<T extends Record<T>> {

	/**
	 * Méthode appelée lorsqu'un record devient invalide.
	 * 
	 * @param inRecord objet rendu invalide.
	 */
	void recordDeleted(T inRecord);
}
