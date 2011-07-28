package net.violet.db.records.associations;

import java.sql.SQLException;
import java.util.Collections;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.Record;
import net.violet.db.records.RecordDeletionListener;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SQLSpecification;

import org.apache.log4j.Logger;

/**
 * Classe de base pour une association 1-1.
 * 
 * @author paul
 * @param P classe pour l'enregistrement SQL parent.
 * @param T classe pour l'enregistrement SQL fils.
 */
abstract class AbstractSingleAssociation<P extends Record<P>, Intf extends Record<Intf>, Impl extends ObjectRecord<Intf, Impl>> implements RecordDeletionListener<Intf> {

	private static final Logger LOGGER = Logger.getLogger(AbstractSingleAssociation.class);

	static private class StrongLazyRef<RIntf extends Record<RIntf>, RImpl extends ObjectRecord<RIntf, RImpl>> {

		private RIntf mRecord;

		private SQLSpecification<RImpl> mSpec;

		private SQLKey mKey;

		private Object mKeyValue;

		public StrongLazyRef(SQLSpecification<RImpl> inSpec, SQLKey inKey, Object inKeyValue) {
			this(null);
			this.mSpec = inSpec;
			this.mKey = inKey;
			this.mKeyValue = inKeyValue;
		}

		public StrongLazyRef(RIntf inRecord) {
			this.mRecord = inRecord;
		}

		public RIntf get() {
			if (this.mRecord != null) {
				if (this.mRecord.isDeleted()) {
					this.mRecord = null;
				} else if (this.mRecord.isInvalid()) {
					((RImpl) this.mRecord).updateFromDatabase();
				}
			} else if ((this.mRecord == null) && (this.mSpec != null) && (this.mKey != null) && (this.mKeyValue != null)) {
				// On initialise
				try {
					this.mRecord = AbstractSQLRecord.findByKey(this.mSpec, this.mKey, this.mKeyValue);
				} catch (final SQLException e) {
					LOGGER.fatal(e, e);
				}
				this.mSpec = null;
				this.mKey = null;
				this.mKeyValue = null;
			}
			return this.mRecord;
		}

	}

	/**
	 * Référence sur l'enregistrement parent.
	 */
	private final P mParentRecord;

	/**
	 * Référence sur l'enregistrement fils.
	 */
	private Intf mChildRecord;

	private StrongLazyRef<Intf, Impl> mChildStrongRecordRef;

	/**
	 * id de l'enregistrement fils.
	 */
	private Object mChildId;

	/**
	 * Spec de l'enregistrement fils.
	 */
	private final SQLSpecification<Impl> mChildSpec;

	/**
	 * Nom de la colonne dans l'objet parent.
	 */
	private final String mParentColName;

	/**
	 * Clé sur l'enregistrement fils
	 */
	private final SQLKey mChildKey;

	/**
	 * Constructeur à partir d'un enregistrement parent, du nom de la colonne et
	 * de l'enregistrement fils.
	 * 
	 * @param inRecord enregistrement parent.
	 * @param inChildSpec spécification du fils.
	 * @param inChild enregistrement fils.
	 */

	AbstractSingleAssociation(P inRecord, String inParentColName, Impl inChild) {
		this(inRecord, inParentColName, inChild.getSpecification());
		this.mChildRecord = (Intf) inChild;
	}

	/**
	 * Constructeur à partir d'un enregistrement parent, du nom de la colonne et
	 * de la spécification de l'enregistrement fils.
	 * 
	 * @param inRecord enregistrement parent.
	 * @param inChildSpec spécification du fils.
	 * @param inChildSQLKey clé sur l'enregistrement fils.
	 */
	AbstractSingleAssociation(P inRecord, String inParentColName, SQLObjectSpecification<Impl> inChildSpec) {
		this(inRecord, inParentColName, inChildSpec, inChildSpec.getPrimaryKey());
	}

	/**
	 * Constructeur à partir d'un enregistrement parent, de la valeur de l'id de
	 * l'enregistrement fils et de la spécification de l'enregistrement fils.
	 * 
	 * @param inRecord enregistrement parent.
	 * @param inChildSpec spécification du fils.
	 * @param inChildSQLKey clé sur l'enregistrement fils
	 */
	AbstractSingleAssociation(P inRecord, String inParentColName, SQLObjectSpecification<Impl> inChildSpec, SQLKey inChildSQLKey) {
		this.mParentRecord = inRecord;
		this.mChildRecord = null;
		this.mChildStrongRecordRef = null;
		this.mChildId = 0;
		this.mChildSpec = inChildSpec;
		this.mParentColName = inParentColName;
		this.mChildKey = inChildSQLKey;
	}

	/**
	 * Sélecteur sur la valeur pour l'absence de lien.
	 */
	protected abstract Object getNullAssociationValue();

	/**
	 * Accesseur sur la valeur.
	 * 
	 * @param id de l'enregistrement fils.
	 * @return la référence sur l'enregistrement fils ou <code>null</code>.
	 */
	public Intf get(Object inChildId) {

		if (((inChildId == null) && (this.mChildId != null)) || ((inChildId != null) && !inChildId.equals(this.mChildId)) || (mChildRecord != null && mChildRecord.isInvalid())) {
			synchronized (this) {
				if (((inChildId == null) && (this.mChildId != null)) || ((inChildId != null) && !inChildId.equals(this.mChildId)) || (mChildRecord != null && mChildRecord.isInvalid())) {

					// On récupère l'enregistrement.
					this.mChildId = inChildId;
					this.mChildStrongRecordRef = new StrongLazyRef<Intf, Impl>(this.mChildSpec, this.mChildKey, this.mChildId);
					this.mChildRecord = this.mChildStrongRecordRef.get();
					if (this.mChildRecord != null) {
						this.mChildRecord.addDeletionListener(this);
					}
				}
			}
		}
		return this.mChildRecord;
	}

	/**
	 * Sélecteur sur la valeur.
	 * 
	 * @param inChild nouvel enfant.
	 * @return le nouvel id pour l'objet parent.
	 */
	public Object set(Intf inChild) {
		return doSet(inChild);
	}

	/**
	 * Sélecteur sur la valeur.
	 * 
	 * @param inChild nouvel enfant.
	 * @return le nouvel id pour l'objet parent.
	 */
	synchronized Object doSet(Intf inChild) {
		Object theResult = null;
		if (inChild == null) {
			if (this.mChildRecord != null) {
				this.mChildRecord.removeDeletionListener(this);
				this.mChildRecord = null;
				this.mChildStrongRecordRef = null;
				this.mParentRecord.update(Collections.singletonMap(this.mParentColName, getNullAssociationValue()));
			}
		} else {
			theResult = inChild.getId();
			if (!inChild.equals(this.mChildRecord)) {
				if (this.mChildRecord != null) {
					this.mChildRecord.removeDeletionListener(this);
				}
				this.mChildRecord = inChild;
				this.mChildStrongRecordRef = new StrongLazyRef<Intf, Impl>(this.mChildRecord);
				inChild.addDeletionListener(this);
				this.mParentRecord.update(Collections.singletonMap(this.mParentColName, theResult));
			}
		}
		return theResult;
	}

	/**
	 * Notification de la suppression. On met la référence sur le parent à 0.
	 */
	public void recordDeleted(Intf inRecord) {
		if (inRecord == this.mChildRecord) {
			this.mChildRecord = null;
			// La clé sur l'enregistrement parent en revanche
			// est foireuse...
		}
	}

}
