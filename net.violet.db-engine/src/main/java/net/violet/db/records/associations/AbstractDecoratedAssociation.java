package net.violet.db.records.associations;

import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.violet.db.cache.Cache;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.DBCollection;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.Record;
import net.violet.db.records.RecordDeletionListener;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SQLSpecification;

import org.apache.log4j.Logger;

/**
 * Classe pour représenter une association 1-N avec décoration.
 * 
 * @param P classe pour l'enregistrement SQL parent.
 * @param T classe pour l'enregistrement SQL fils.
 * @param U classe pour la décoration/la liste de décorations.
 * @param D classe pour la décoration.
 */
public abstract class AbstractDecoratedAssociation<P extends AbstractSQLRecord<?, P>, RIntf extends Record<RIntf>, RImpl extends ObjectRecord<RIntf, RImpl>, U, DIntf extends AssoRecord<RIntf, DIntf>, DImpl extends AssociationRecord<RIntf, DIntf, DImpl>> extends AbstractMap<RIntf, U> implements RecordDeletionListener<RIntf>, DBCollection {

	private static final Logger LOGGER = Logger.getLogger(AbstractDecoratedAssociation.class);

	class DAEntryIterator implements Iterator<Entry<RIntf, U>> {

		/**
		 * Référence sur l'implémentation.
		 */
		private final Iterator<Entry<RIntf, U>> mIterator;

		/**
		 * Dernier objet retourné.
		 */
		private Entry<RIntf, U> mLastObject;

		/**
		 * Constructeur à partir d'un itérateur.
		 */
		DAEntryIterator(Iterator<Entry<RIntf, U>> inIterator) {
			this.mIterator = inIterator;
		}

		public boolean hasNext() {
			return this.mIterator.hasNext();
		}

		public Entry<RIntf, U> next() {
			this.mLastObject = this.mIterator.next();
			if (this.mLastObject != null) {
				final RIntf theKey = this.mLastObject.getKey();
				if (theKey.isInvalid()) {
					theKey.updateFromDatabase();
				}
				updateDecorationFromDatabase(this.mLastObject.getValue());
			}
			return this.mLastObject;
		}

		public void remove() {
			if (this.mLastObject == null) {
				throw new IllegalStateException();
			}
			try {
				doRemove(this.mLastObject);
				broadcastModified();
			} catch (final SQLException anException) {
				throw new RuntimeException(anException);
			} catch (final NoSuchFieldException anException) {
				throw new RuntimeException(anException);
			} catch (final IllegalAccessException anException) {
				throw new RuntimeException(anException);
			}
			this.mLastObject.getKey().removeDeletionListener(AbstractDecoratedAssociation.this);
			this.mIterator.remove();
		}

	}

	private class DAEntrySet extends AbstractSet<Entry<RIntf, U>> {

		/**
		 * Ensemble des clés.
		 */
		private final Set<Entry<RIntf, U>> mSet;

		/**
		 * Constructeur à partir de l'implémentation.
		 * 
		 * @param inSet ensemble des clés.
		 */
		protected DAEntrySet(Set<Entry<RIntf, U>> inSet) {
			this.mSet = inSet;
		}

		@Override
		public Iterator<Entry<RIntf, U>> iterator() {
			return new DAEntryIterator(this.mSet.iterator());
		}

		@Override
		public int size() {
			return this.mSet.size();
		}

	}

	/**
	 * Données.
	 */
	private Map<RIntf, U> mData;

	/**
	 * Nom de la colonne pour le parent.
	 */
	private final String mParentIdColName;

	/**
	 * Nom de la colonne pour le parent (qualifié)
	 */
	private final String mQualifiedParentIdColName;

	/**
	 * Id du parent.
	 */
	private final long mParentId;

	/**
	 * Nom de la colonne pour la table fille.
	 */
	private final String mChildIdColName;

	/**
	 * Spécification de la table fille.
	 */
	private final SQLObjectSpecification<RImpl> mChildSpec;

	/**
	 * Table de l'association.
	 */
	private final SQLSpecification<DImpl> mDecorationSpec;

	/**
	 * Table de l'association.
	 */
	private final String mTableName;

	/**
	 * Clé primaire du parent.
	 */
	private final SQLKey mParentRecordKey;

	/**
	 * Valeurs pour la clé primaire du parent.
	 */
	private final Object[] mParentRecordKeyVals;

	/**
	 * Etat de l'enregistrement.
	 */
	private boolean mIsModified;

	/**
	 * Listener pour les décorations.
	 */
	private final RecordDeletionListener<DIntf> mDecorationListener;

	/**
	 * Constructeur à partir d'un enregistrement parent et du nom de la table
	 * pour l'association.
	 * 
	 * @param P enregistrement parent.
	 * @param inZone zone où se trouve la table d'association et la table des
	 *            fils.
	 * @param inTableName nom de la table d'association.
	 * @param inParentIdColName nom de la colonne avec l'id des parents dans la
	 *            table d'association.
	 * @param inChildIdColName nom de la colonne avec l'id des enfants dans la
	 *            table d'association.
	 */
	AbstractDecoratedAssociation(P inRecord, SQLObjectSpecification<RImpl> inChildSpec, String inChildIdColName, SQLAssociationSpecification<DImpl> inDecorationSpec, String inParentIdColName) throws SQLException {
		this.mParentId = inRecord.getId();

		this.mDecorationSpec = inDecorationSpec;
		this.mTableName = inDecorationSpec.getTableName();

		this.mParentRecordKey = inRecord.getSpecification().getPrimaryKey();
		this.mParentRecordKeyVals = inRecord.getValuesFromSQLKey(this.mParentRecordKey).toArray();

		this.mChildSpec = inChildSpec;
		this.mChildIdColName = inChildIdColName;
		this.mParentIdColName = inParentIdColName;
		this.mQualifiedParentIdColName = inDecorationSpec.getTableName() + "." + inParentIdColName;

		// Lazy, on charge la collection au premier usage (avec
		// reloadFromDatabase).
		this.mData = Collections.<RIntf, U> emptyMap();
		this.mIsModified = true;
		this.mDecorationListener = new RecordDeletionListener<DIntf>() {

			public void recordDeleted(DIntf inDecorationRecord) {
				decorationRecordDeleted(inDecorationRecord);
			}
		};
	}

	/**
	 * Met la décoration/la liste des décorations à jour si nécessaire.
	 * 
	 * @param inDecoration décoration ou liste des décorations.
	 */
	protected abstract void updateDecorationFromDatabase(U inDecoration);

	/**
	 * Accesseur sur la spécification de l'association.
	 * 
	 * @return la spécification de la table d'association.
	 */
	protected SQLSpecification<DImpl> getDecorationSpec() {
		return this.mDecorationSpec;
	}

	/**
	 * @return the mData
	 */
	protected final Map<RIntf, U> getData() {
		return this.mData;
	}

	/**
	 * @return the mParentId
	 */
	protected final long getParentId() {
		return this.mParentId;
	}

	/**
	 * @return le nom de la colonne de jointure (dans la table d'association)
	 *         avec la table parente, qualifiée par le nom de la table de
	 *         jointure.
	 */
	protected final String getQualifiedParentIdColName() {
		return this.mQualifiedParentIdColName;
	}

	/**
	 * @return le nom de la colonne de jointure (dans la table d'association)
	 *         avec la table parente.
	 */
	public final String getParentIdColName() {
		return this.mParentIdColName;
	}

	/**
	 * @return le nom de la colonne pour la jointure sur la table fille.
	 */
	public final String getChildIdColName() {
		return this.mChildIdColName;
	}

	/**
	 * @return la spécification de la table fille.
	 */
	protected final SQLObjectSpecification<RImpl> getChildSpec() {
		return this.mChildSpec;
	}

	/**
	 * Enregistre le deletion listener pour T et U/les éléments de U.
	 * 
	 * @param key T (enregistrement fils)
	 * @param value U (décoration/liste de décorations)
	 */
	protected abstract void registerForDeletionEvent(RIntf key, U value);

	/**
	 * Désenregistre le deletion listener pour T et U/les éléments de U.
	 * 
	 * @param key T (enregistrement fils)
	 * @param value U (décoration/liste de décorations)
	 */
	protected abstract void unregisterForDeletionEvent(RIntf key, U value);

	/**
	 * S'enregistre auprès d'une décoration.
	 * 
	 * @param key T (enregistrement fils)
	 * @param value U (décoration/liste de décorations)
	 */
	protected void addToDeletionListeners(DIntf inRecord) {
		inRecord.addDeletionListener(this.mDecorationListener);
	}

	/**
	 * Se désenregistre auprès d'une décoration.
	 * 
	 * @param key T (enregistrement fils)
	 * @param value U (décoration/liste de décorations)
	 */
	protected void removeFromDeletionListeners(DIntf inRecord) {
		inRecord.removeDeletionListener(this.mDecorationListener);
	}

	protected abstract Map<RIntf, U> getAllFromDatabase() throws SQLException;

	private synchronized void reloadAllFromDatabase() {
		if (this.mIsModified) {
			try {
				// Lecture des objets.
				for (final Map.Entry<RIntf, U> oldRecords : this.mData.entrySet()) {
					unregisterForDeletionEvent(oldRecords.getKey(), oldRecords.getValue());
				}
				final Map<RIntf, U> theData = getAllFromDatabase();
				this.mData = new ConcurrentHashMap<RIntf, U>(theData);
				for (final Map.Entry<RIntf, U> newRecords : this.mData.entrySet()) {
					registerForDeletionEvent(newRecords.getKey(), newRecords.getValue());
				}

				this.mIsModified = false;
			} catch (final SQLException anException) {
				AbstractDecoratedAssociation.LOGGER.fatal(anException, anException);
			}
		}
	}

	/**
	 * Suppression d'un élément. La clé (T) est déjà supprimée de la liste des
	 * éléments qui nous intéressent pour la suppression, mais pas la valeur
	 * (U).
	 * 
	 * @param inElement élément à supprimer.
	 */
	protected abstract void doRemove(Entry<RIntf, U> inElement) throws SQLException, NoSuchFieldException, IllegalAccessException;

	@Override
	public Set<java.util.Map.Entry<RIntf, U>> entrySet() {
		reloadAllFromDatabase();
		return new DAEntrySet(this.mData.entrySet());
	}

	/**
	 * Ajout d'un element. La clé (T) est déjà ajoutée dans la liste des
	 * éléments qui nous intéressent pour la suppression, mais pas la valeur
	 * (U).
	 * 
	 * @param object objet à ajouter (clé).
	 * @param decoration décoration à ajouter (valeur)
	 */
	protected abstract void doAdd(RIntf object, U decoration) throws SQLException, NoSuchFieldException, IllegalAccessException;

	@Override
	public U put(RIntf t, U d) {
		try {
			t.addDeletionListener(this);
			doAdd(t, d);
			broadcastModified();
			if (!this.mIsModified) {
				this.mData.put(t, d);
			}
		} catch (final SQLException anException) {
			throw new RuntimeException(anException);
		} catch (final NoSuchFieldException anException) {
			throw new RuntimeException(anException);
		} catch (final IllegalAccessException anException) {
			throw new RuntimeException(anException);
		}

		return null;
	}

	/**
	 * Méthode appelée lors de la suppression d'un objet.
	 * 
	 * @param inRecord objet supprimé
	 * @param inKey clé
	 * @param inValue valeur (entrée dans la liste pour tester).
	 * @return <code>true</code> s'il faut supprimmer l'association,
	 *         <code>false</code> sinon.
	 */
	protected abstract boolean doRecordDeleted(DIntf inRecord, RIntf inKey, U inValue);

	public final void recordDeleted(RIntf inRecord) {
		// Suppression de l'élément dans la liste.
		// A priori, les clés étrangères sont en delete cascade, donc il n'est
		// pas nécessaire de supprimer l'enregistrement de l'association.
		final Iterator<Map.Entry<RIntf, U>> theIter = this.mData.entrySet().iterator();
		while (theIter.hasNext()) {
			final Map.Entry<RIntf, U> theEntry = theIter.next();
			final RIntf theKey = theEntry.getKey();
			if (theKey == inRecord) {
				theIter.remove();
			}
		}
	}

	private void decorationRecordDeleted(DIntf inDecorationRecord) {
		final Iterator<Map.Entry<RIntf, U>> theIter = this.mData.entrySet().iterator();
		while (theIter.hasNext()) {
			final Map.Entry<RIntf, U> theEntry = theIter.next();
			if (doRecordDeleted(inDecorationRecord, theEntry.getKey(), theEntry.getValue())) {
				theIter.remove();
			}
		}
	}

	public String getAssociationTableName() {
		return this.mTableName;
	}

	public SQLKey getParentRecordKey() {
		return this.mParentRecordKey;
	}

	public Object[] getParentRecordKeyVals() {
		return this.mParentRecordKeyVals;
	}

	public synchronized void setModified() {
		this.mIsModified = true;
	}

	public void setAndBroadcastModified() {
		setModified();
		broadcastModified();
	}

	private void broadcastModified() {
		Cache.broadcastModification(this.mParentRecordKey, this.mParentRecordKeyVals, this.mTableName, this.mParentIdColName, this.mChildIdColName);
	}

	/**
	 * Equals: pour les collections, l'égalité est équivalente à l'identité.
	 * 
	 * @return <code>true</code> si this est égal à <code>inObject</code>.
	 */
	@Override
	public boolean equals(Object inObject) {
		return this == inObject;
	}

	/**
	 * hashCode: pour les collections, l'égalité est équivalente à l'identité.
	 * 
	 * @return la valeur de hachage correspondant à l'identité.
	 */
	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}
}
