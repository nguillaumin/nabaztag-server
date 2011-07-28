package net.violet.db.records.associations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.violet.common.StringShop;
import net.violet.db.cache.Cache;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.DBCollection;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.Record;
import net.violet.db.records.RecordDeletionListener;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SQLSpecification;
import net.violet.db.records.SgbdConnection;
import net.violet.db.records.SgbdResult;

import org.apache.log4j.Logger;

/**
 * Classe pour représenter une association N-M avec décoration.
 *
 * @param P classe pour l'enregistrement SQL parent.
 * @param T classe pour l'enregistrement SQL fils.
 * @param D classe pour la décoration.
 */
public final class DecoratedAssociation<P extends AbstractSQLRecord<?, P>, RIntf extends Record<RIntf>, RImpl extends ObjectRecord<RIntf, RImpl>, DIntf extends AssoRecord<RIntf, DIntf>, DImpl extends AssociationRecord<RIntf, DIntf, DImpl>> /*extends AbstractDecoratedAssociation<P, RIntf, RImpl, DIntf, DIntf, DImpl> */extends AbstractMap<RIntf, DIntf> implements RecordDeletionListener<RIntf>, DBCollection {

	private static final Logger LOGGER = Logger.getLogger(DecoratedAssociation.class);

	/**
	 * Données.
	 */
	private Map<RIntf, DIntf> mData;

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
	private DecoratedAssociation(P inRecord, SQLObjectSpecification<RImpl> inChildSpec, String inChildIdColName, SQLAssociationSpecification<DImpl> inDecorationSpec, String inParentIdColName) throws SQLException {
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
		this.mData = Collections.<RIntf, DIntf> emptyMap();
		this.mIsModified = true;
		this.mDecorationListener = new RecordDeletionListener<DIntf>() {

			public void recordDeleted(DIntf inDecorationRecord) {
				decorationRecordDeleted(inDecorationRecord);
			}
		};
	}

	public static <P extends AbstractSQLRecord<?, P>, RIntf extends Record<RIntf>, RImpl extends ObjectRecord<RIntf, RImpl>, DIntf extends AssoRecord<RIntf, DIntf>, DImpl extends AssociationRecord<RIntf, DIntf, DImpl>> DecoratedAssociation<P, RIntf, RImpl, DIntf, DImpl> createDecoratedAssociation(P inRecord, SQLObjectSpecification<RImpl> inChildSpec, String inChildIdColName, SQLAssociationSpecification<DImpl> inDecorationSpec, String inParentIdColName) throws SQLException {

		final SQLKey thePrimaryKey = inRecord.getSpecification().getPrimaryKey();
		DecoratedAssociation<P, RIntf, RImpl, DIntf, DImpl> theResult = (DecoratedAssociation<P, RIntf, RImpl, DIntf, DImpl>) Cache.get(thePrimaryKey, inRecord.getValuesFromSQLKey(thePrimaryKey).toArray(), inDecorationSpec.getTableName(), inParentIdColName, inChildIdColName);

		if (theResult == null) {
			theResult = new DecoratedAssociation<P, RIntf, RImpl, DIntf, DImpl>(inRecord, inChildSpec, inChildIdColName, inDecorationSpec, inParentIdColName);
			Cache.add(theResult);
		}
		return theResult;

	}

	class DAEntryIterator implements Iterator<Entry<RIntf, DIntf>> {

		/**
		 * Référence sur l'implémentation.
		 */
		private final Iterator<Entry<RIntf, DIntf>> mIterator;

		/**
		 * Dernier objet retourné.
		 */
		private Entry<RIntf, DIntf> mLastObject;

		/**
		 * Constructeur à partir d'un itérateur.
		 */
		DAEntryIterator(Iterator<Entry<RIntf, DIntf>> inIterator) {
			this.mIterator = inIterator;
		}

		public boolean hasNext() {
			return this.mIterator.hasNext();
		}

		public Entry<RIntf, DIntf> next() {
			this.mLastObject = this.mIterator.next();
			if (this.mLastObject != null) {
				final RIntf theKey = this.mLastObject.getKey();
				if (theKey.isInvalid()) {
					((RImpl) theKey).updateFromDatabase();
				}

				if (this.mLastObject.getValue().isInvalid()) {
					((DImpl) this.mLastObject.getValue()).updateFromDatabase();
				}
			}
			return this.mLastObject;
		}

		public void remove() {
			if (this.mLastObject == null) {
				throw new IllegalStateException();
			}
			try {
				// Suppression dans la table.
				final DIntf theChildRecord = mLastObject.getValue();
				final SQLSpecification<DImpl> theDecorationSpec = getDecorationSpec();
				final SgbdConnection theConnection = new SgbdConnection();
				try {
					String theQuery = "delete from " + theDecorationSpec.getTableName() + " where " + getQualifiedParentIdColName() + " = ? ";
					final List<Object> theVals = new ArrayList<Object>();
					theVals.add(getParentId());
					final String theChildColName = getChildIdColName();
					theQuery += "and " + theDecorationSpec.getTableName() + "." + theChildColName + " = ? ";
					theVals.add(((AssociationRecord) theChildRecord).get(theChildColName));
					theConnection.doQueryUpdatePS(theQuery, theVals);

				} finally {
					theConnection.close();
				}

				((AssociationRecord) theChildRecord).setDeleted();
				removeFromDeletionListeners(theChildRecord);

				broadcastModified();
			} catch (final SQLException anException) {
				throw new RuntimeException(anException);
			} catch (final NoSuchFieldException anException) {
				throw new RuntimeException(anException);
			} catch (final IllegalAccessException anException) {
				throw new RuntimeException(anException);
			}
			this.mLastObject.getKey().removeDeletionListener(DecoratedAssociation.this);
			this.mIterator.remove();
		}

	}

	private class DAEntrySet extends AbstractSet<Entry<RIntf, DIntf>> {

		/**
		 * Ensemble des clés.
		 */
		private final Set<Entry<RIntf, DIntf>> mSet;

		/**
		 * Constructeur à partir de l'implémentation.
		 *
		 * @param inSet ensemble des clés.
		 */
		protected DAEntrySet(Set<Entry<RIntf, DIntf>> inSet) {
			this.mSet = inSet;
		}

		@Override
		public Iterator<Entry<RIntf, DIntf>> iterator() {
			return new DAEntryIterator(this.mSet.iterator());
		}

		@Override
		public int size() {
			return this.mSet.size();
		}

	}

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
	protected final Map<RIntf, DIntf> getData() {
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

	@Override
	public Set<java.util.Map.Entry<RIntf, DIntf>> entrySet() {
		try {
			synchronized (this) {

				if (this.mIsModified) {
					// Lecture des objets.
					for (final Map.Entry<RIntf, DIntf> oldRecords : this.mData.entrySet()) {
						oldRecords.getKey().removeDeletionListener(this);
						removeFromDeletionListeners(oldRecords.getValue());
					}

					// On remplit les données depuis la table SQL.
					final Map<RIntf, DIntf> theData = new HashMap<RIntf, DIntf>();
					final List<Object> theConditionVals = Collections.singletonList((Object) getParentId());
					final String theCondition = getQualifiedParentIdColName() + " = ?";
					final SgbdConnection theConnection = new SgbdConnection();
					try {
						final SgbdResult theSgbdResult = AssociationRecord.fetchRecords(theConnection, getDecorationSpec(), Collections.singletonMap(getChildIdColName(), getChildSpec()), theCondition, theConditionVals, AssociationRecord.NO_ORDER, AssociationRecord.NO_LIMIT);
						final ResultSet theResultSet = theSgbdResult.getResultSet();
						try {
							while (theResultSet.next()) {
								final RIntf theObject = ObjectRecord.createObject(getChildSpec(), theResultSet);
								final DIntf theDecoration = AssociationRecord.createObject(getDecorationSpec(), theResultSet);
								theData.put(theObject, theDecoration);
							}
						} finally {
							theSgbdResult.close();
						}
					} finally {
						theConnection.close();
					}

					this.mData = new ConcurrentHashMap<RIntf, DIntf>(theData);
					for (final Map.Entry<RIntf, DIntf> newRecords : this.mData.entrySet()) {
						newRecords.getKey().addDeletionListener(this);
						addToDeletionListeners(newRecords.getValue());
					}

					this.mIsModified = false;
				}
			}
		} catch (final SQLException anException) {
			LOGGER.fatal(anException, anException);
		}
		return new DAEntrySet(this.mData.entrySet());
	}

	@Override
	public DIntf put(RIntf t, DIntf d) {
		try {
			t.addDeletionListener(this);

			final SQLSpecification<DImpl> theDecorationSpec = getDecorationSpec();
			final String theChildIdColName = getChildIdColName();
			final String theParentIdColName = getParentIdColName();
			final SgbdConnection theConnection = new SgbdConnection();
			try {
				String listFields = StringShop.EMPTY_STRING;
				String listValues = StringShop.EMPTY_STRING;
				final List<Object> theVals = new ArrayList<Object>();
				for (final String fieldName : theDecorationSpec.getColumns()) {
					if (!listFields.equals(StringShop.EMPTY_STRING)) {
						listFields += ", ";
					}
					listFields += "`" + fieldName + "`";
					if (!listValues.equals(StringShop.EMPTY_STRING)) {
						listValues += ", ";
					}
					listValues += "?";

					Object theVal = null;
					if (theChildIdColName.equals(fieldName)) {
						// Jointure avec la table fille.
						theVal = t.getId();
					} else if (theParentIdColName.equals(fieldName)) {
						theVal = getParentId();
					}

					if (theVal == null) {
						// On récupère la valeur de décoration.
						theVal = ((AssociationRecord) d).get(fieldName);
					} else {
						// Mise à jour de la valeur de décoration.
						((AssociationRecord) d).set(fieldName, theVal);
					}
					theVals.add(theVal);
				}
				final String theQuery = "insert into " + theDecorationSpec.getTableName() + "(" + listFields + ") values(" + listValues + ")";
				theConnection.doQueryUpdatePS(theQuery, theVals);

			} finally {
				theConnection.close();
			}

			// la décoration est désormais dans la base.
			((AssociationRecord) d).initClean();
			addToDeletionListeners(d);

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

	public final void recordDeleted(RIntf inRecord) {
		// Suppression de l'élément dans la liste.
		// A priori, les clés étrangères sont en delete cascade, donc il n'est
		// pas nécessaire de supprimer l'enregistrement de l'association.
		final Iterator<Map.Entry<RIntf, DIntf>> theIter = this.mData.entrySet().iterator();
		while (theIter.hasNext()) {
			final Map.Entry<RIntf, DIntf> theEntry = theIter.next();
			final RIntf theKey = theEntry.getKey();
			if (theKey == inRecord) {
				theIter.remove();
			}
		}
	}

	private void decorationRecordDeleted(DIntf inDecorationRecord) {
		final Iterator<Map.Entry<RIntf, DIntf>> theIter = this.mData.entrySet().iterator();
		while (theIter.hasNext()) {
			final Map.Entry<RIntf, DIntf> theEntry = theIter.next();
			if (inDecorationRecord == theEntry.getValue()) {
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
