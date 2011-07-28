package net.violet.db.records.associations;

import java.sql.SQLException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.violet.db.cache.Cache;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.DBCollection;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.Record;
import net.violet.db.records.RecordDeletionListener;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SgbdConnection;

import org.apache.log4j.Logger;

/**
 * Classe pour représenter une association 1-N. Usage: List<T> = new
 * ListAssociation<P, T>();
 * 
 * @param P classe pour l'enregistrement SQL parent.
 * @param T classe pour l'enregistrement SQL fils.
 */
public final class ListAssociation<P extends AbstractSQLRecord<?, P>, RIntf extends Record<RIntf>, RImpl extends ObjectRecord<RIntf, RImpl>> extends AbstractList<RIntf> implements RecordDeletionListener<RIntf>, DBCollection {

	private static final Logger LOGGER = Logger.getLogger(ListAssociation.class);

	class AssociationIterator implements Iterator<RIntf> {

		/**
		 * Référence sur l'implémentation.
		 */
		private final Iterator<RIntf> mIterator;

		/**
		 * Dernier objet retourné.
		 */
		private RIntf mLastObject;

		/**
		 * Constructeur à partir d'un itérateur.
		 */
		AssociationIterator(Iterator<RIntf> inIterator) {
			this.mIterator = inIterator;
		}

		public boolean hasNext() {
			return this.mIterator.hasNext();
		}

		public RIntf next() {
			this.mLastObject = this.mIterator.next();
			return this.mLastObject;
		}

		public void remove() {
			if (this.mLastObject == null) {
				throw new IllegalStateException();
			}
			try {
				doRemove(this.mLastObject);
			} catch (final SQLException anException) {
				throw new RuntimeException(anException);
			}
			this.mIterator.remove();
		}
	}

	/**
	 * Données.
	 */
	private List<RIntf> mRecordList;

	/**
	 * Table de l'association.
	 */
	private final String mTableName;

	/**
	 * Nom de la colonne pour le parent.
	 */
	private final String mParentIdColName;

	/**
	 * Nom de la colonne pour l'enfant.
	 */
	private final String mChildIdColName;

	/**
	 * Id du parent.
	 */
	private final long mParentId;

	/**
	 * Clé primaire du parent.
	 */
	private final SQLKey mParentRecordKey;

	/**
	 * Valeurs pour la clé primaire du parent.
	 */
	private final Object[] mParentRecordKeyVals;

	/**
	 * Référence sur la condition.
	 */
	private final SQLObjectSpecification<RImpl> mChildSpec;

	/**
	 * Etat de l'enregistrement.
	 */
	private boolean mIsModified;

	/**
	 * Constructeur à partir d'un enregistrement parent et du nom de la table
	 * pour l'association.
	 * 
	 * @param P enregistrement parent.
	 * @param inTableName nom de la table d'association.
	 * @param inParentIdColName nom de la colonne avec l'id des parents dans la
	 *            table d'association.
	 * @param inChildIdColName nom de la colonne avec l'id des enfants dans la
	 *            table d'association.
	 */
	private ListAssociation(P inRecord, SQLObjectSpecification<RImpl> inChildSpec, String inTableName, String inParentIdColName, String inChildIdColName) throws SQLException {

		this.mTableName = inTableName;
		this.mParentIdColName = inTableName + "." + inParentIdColName;
		this.mChildIdColName = inTableName + "." + inChildIdColName;
		this.mParentRecordKey = inRecord.getSpecification().getPrimaryKey();
		this.mParentRecordKeyVals = inRecord.getValuesFromSQLKey(this.mParentRecordKey).toArray();
		this.mChildSpec = inChildSpec;

		this.mParentId = inRecord.getId();

		// Lecture des objets.
		final List<RIntf> records = getAllFromDatabase();

		// On s'intéresse à la suppression de tous ces objets.
		for (final RIntf record : records) {
			record.addDeletionListener(this);
		}

		this.mRecordList = Collections.synchronizedList(records);
	}

	private List<RIntf> getAllFromDatabase() throws SQLException {
		final List<RIntf> theResult = new LinkedList<RIntf>();
		// Lecture des objets avec findAll.
		final String[] theTables = new String[] { this.mTableName, };
		final List<Object> theVals = Collections.singletonList((Object) this.mParentId);
		final String theCondition = this.mParentIdColName + " = ? and " + this.mChildIdColName + " = " + this.mChildSpec.getTableName() + "." + this.mChildSpec.getIdColName();
		AbstractSQLRecord.findAll(this.mChildSpec, theTables, theCondition, theVals, null, theResult);
		return theResult;
	}

	private synchronized void reloadAllFromDatabase() {
		if (this.mIsModified) {
			try {
				this.mIsModified = false;
				// Lecture des objets.
				final List<RIntf> theObjects = getAllFromDatabase();

				// On se désabonne de ceux qui ont disparu.
				final Set<RIntf> theRemovedObjects = new HashSet<RIntf>(this.mRecordList);
				theRemovedObjects.removeAll(theObjects);
				for (final RIntf theObject : theRemovedObjects) {
					theObject.removeDeletionListener(this);
				}

				// On s'abonne à tous les nouveaux.
				final Set<RIntf> theNewObjects = new HashSet<RIntf>(theObjects);
				theRemovedObjects.removeAll(this.mRecordList);
				for (final RIntf theObject : theNewObjects) {
					theObject.addDeletionListener(this);
				}

				this.mRecordList = Collections.synchronizedList(theObjects);
			} catch (final SQLException anException) {
				ListAssociation.LOGGER.fatal(anException, anException);
			}
		}
	}

	public static <P extends AbstractSQLRecord<?, P>, RIntf extends Record<RIntf>, RImpl extends ObjectRecord<RIntf, RImpl>> ListAssociation<P, RIntf, RImpl> createListAssociation(P inRecord, SQLObjectSpecification<RImpl> inChildSpec, String inTableName, String inParentIdColName, String inChildIdColName) throws SQLException {
		final SQLKey thePrimaryKey = inRecord.getSpecification().getPrimaryKey();
		ListAssociation<P, RIntf, RImpl> theResult = (ListAssociation<P, RIntf, RImpl>) Cache.get(thePrimaryKey, inRecord.getValuesFromSQLKey(thePrimaryKey).toArray(), inTableName, inParentIdColName, inChildIdColName);
		if (theResult == null) {
			theResult = new ListAssociation<P, RIntf, RImpl>(inRecord, inChildSpec, inTableName, inParentIdColName, inChildIdColName);
			Cache.add(theResult);
		}
		return theResult;
	}

	@Override
	public Iterator<RIntf> iterator() {
		// On recharge mData si nécessaire.
		reloadAllFromDatabase();
		return new AssociationIterator(this.mRecordList.iterator());
	}

	@Override
	public int size() {
		// On recharge mData si nécessaire.
		reloadAllFromDatabase();
		return this.mRecordList.size();
	}

	@Override
	public RIntf get(int inArg) {
		// On recharge mData si nécessaire.
		reloadAllFromDatabase();
		return this.mRecordList.get(inArg);
	}

	@Override
	public RIntf remove(int index) {
		// On recharge mData si nécessaire.
		reloadAllFromDatabase();
		final RIntf elt = this.mRecordList.remove(index);
		try {
			doRemove(elt);
		} catch (final SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return elt;
	}

	/**
	 * Suppression de élément en base.
	 * 
	 * @param inElement élément à supprimer.
	 */
	protected void doRemove(RIntf inElement) throws SQLException {

		// Suppression dans la table.
		final SgbdConnection cnn = new SgbdConnection();
		try {
			final String theQuery = "delete from " + this.mTableName + " where " + this.mParentIdColName + " = ? and " + this.mChildIdColName + " = ?;";
			final List<Object> theVals = Arrays.asList((Object) this.mParentId, inElement.getId());
			cnn.doQueryUpdatePS(theQuery, theVals);

		} finally {
			cnn.close();
		}

		// On ne s'intéresse plus à la suppression de cet objet.
		inElement.removeDeletionListener(this);
		Cache.broadcastModification(getParentRecordKey(), getParentRecordKeyVals(), getAssociationTableName(), getParentIdColName(), getChildIdColName());
	}

	/**
	 * Ajout d'un élément.
	 * 
	 * @param inElement élément à supprimer.
	 * @return TRUE si l'élémet a réellement été ajouté
	 */
	@Override
	public boolean add(RIntf inElement) {

		if ((inElement == null) || this.mRecordList.contains(inElement)) {
			return false; // ne rien faire
		}

		try {
			// Ajout dans la table.
			final SgbdConnection theConnection = new SgbdConnection();
			try {
				final String theQuery = "insert into " + this.mTableName + " (" + this.mParentIdColName + ", " + this.mChildIdColName + ") values (?, ?);";
				final List<Object> theVals = Arrays.asList(new Object[] { this.mParentId, inElement.getId(), });
				theConnection.doQueryUpdatePS(theQuery, theVals);

			} finally {
				theConnection.close();
			}

		} catch (final SQLException anException) {
			throw new IllegalArgumentException(anException);
		}

		this.mRecordList.add(inElement);
		// On s'intéresse à la suppression de cet objet.
		inElement.addDeletionListener(this);
		Cache.broadcastModification(getParentRecordKey(), getParentRecordKeyVals(), getAssociationTableName(), getParentIdColName(), getChildIdColName());

		return true; // succès
	}

	public void recordDeleted(RIntf inRecord) {
		// Suppression de l'élément dans la liste.
		// A priori, les clés étrangères sont en delete cascade, donc il n'est
		// pas nécessaire de supprimer l'enregistrement de l'association.
		final Iterator<RIntf> theIter = this.mRecordList.iterator();
		while (theIter.hasNext()) {
			if (theIter.next() == inRecord) {
				theIter.remove();
			}
		}
	}

	@Override
	public RIntf set(int position, RIntf inElement) {
		RIntf toRemoveElement = null;
		if (position < this.mRecordList.size()) {
			toRemoveElement = this.mRecordList.get(position);
			try {
				final SgbdConnection theConnection = new SgbdConnection();
				try {

					// Supression de la base
					final String theQueryDelete = "delete from " + this.mTableName + " where " + this.mParentIdColName + " = ? and " + this.mChildIdColName + " = ?;";
					final List<Object> theValsDelete = Arrays.asList(new Object[] { this.mParentId, toRemoveElement.getId(), });
					theConnection.doQueryUpdatePS(theQueryDelete, theValsDelete);

					// On ne s'intéresse plus à la suppression de cet objet.
					toRemoveElement.removeDeletionListener(this);

					// Ajout dans la table.
					final String theQueryInsert = "insert into " + this.mTableName + " (" + this.mParentIdColName + ", " + this.mChildIdColName + ") values (?, ?);";
					final List<Object> theValsInsert = Arrays.asList(new Object[] { this.mParentId, inElement.getId(), });
					theConnection.doQueryUpdatePS(theQueryInsert, theValsInsert);

				} finally {
					theConnection.close();
				}
			} catch (final SQLException anException) {
				throw new IllegalArgumentException(anException);
			}
			this.mRecordList.set(position, inElement);
			broadcastModified();
		}
		return toRemoveElement;
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

	public String getAssociationTableName() {
		return this.mTableName;
	}

	public String getChildIdColName() {
		return this.mChildIdColName;
	}

	public String getParentIdColName() {
		return this.mParentIdColName;
	}

	public SQLKey getParentRecordKey() {
		return this.mParentRecordKey;
	}

	public Object[] getParentRecordKeyVals() {
		return this.mParentRecordKeyVals;
	}

	/**
	 * Take a new record list and update this one to reflect the new elements in
	 * list
	 * 
	 * @param inUpdatedRecordList
	 */
	public void update(List<RIntf> inUpdatedRecordList) {

		// supprime les éléments qui ne sont plus dans la nouvelle liste
		for (int i = this.mRecordList.size() - 1; i >= 0; i--) { // reverse loop
			if (!inUpdatedRecordList.contains(this.mRecordList.get(i))) {
				remove(i);
			}
		}

		// ajoute les nouveaux elements (si nécessaire)
		for (final RIntf newElt : inUpdatedRecordList) {
			add(newElt);
		}
	}
}
