package net.violet.db.records;

import java.util.Map;

public interface Record<T extends Record<T>> {

	/**
	 * Interface pour les itérateurs.
	 */
	public interface RecordWalker<T> {

		/**
		 * Entry point
		 * 
		 * @param inObject
		 */
		void process(T inObject);
	}

	/**
	 * Interface pour les itérateurs avec deux enregistrements.
	 */
	public interface JoinRecordsWalker<T extends Record, U extends Record> {

		/**
		 * Point d'entrée de l'itérateur.
		 */
		void process(T inObject1, U inObject2);
	}

	/**
	 * Enum avec l'état de l'enregistrement.
	 */
	public enum State {
		/**
		 * L'objet n'a pas été initialisé.
		 */
		NEW,

		/**
		 * Les données correspondent à ce qui est dans la base.
		 */
		CLEAN,

		/**
		 * L'objet a été supprimé.
		 */
		DELETED,

		/**
		 * L'objet est invalide (doit être rechargé).
		 */
		INVALID,
	}

	/**
	 * Accesseur sur l'id.
	 * 
	 * @return l'id de l'objet.
	 */
	Long getId();

	boolean isInvalid();

	boolean isDeleted();

	void update(Map<String, Object> inUpdateMap);

	void updateFromDatabase();

	boolean delete();

	void addDeletionListener(RecordDeletionListener<T> inListener);

	void removeDeletionListener(RecordDeletionListener<T> inListener);

}
