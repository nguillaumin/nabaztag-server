package net.violet.db.records.associations;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.Record;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

/**
 * Classe pour une association 1-1, où l'id du fils est représenté par null
 * lorsque l'association est incorrecte.
 * 
 * @author paul
 * @param P classe pour l'enregistrement SQL parent.
 * @param T classe pour l'enregistrement SQL fils.
 */
public class SingleAssociationNull<P extends Record<P>, CIntf extends Record<CIntf>, CImpl extends ObjectRecord<CIntf, CImpl>> extends AbstractSingleAssociation<P, CIntf, CImpl> {

	/**
	 * Constructeur à partir d'un enregistrement parent, de la valeur de l'id de
	 * l'enregistrement fils et de la spécification de l'enregistrement fils.
	 * 
	 * @param inRecord enregistrement parent.
	 * @param inChildSpec spécification du fils.
	 * @param inChildSQLKey clé sur l'enregistrement fils
	 */
	public SingleAssociationNull(P inRecord, String inParentColName, SQLObjectSpecification<CImpl> inChildSpec, SQLKey inChildSQLKey) {
		super(inRecord, inParentColName, inChildSpec, inChildSQLKey);
	}

	/**
	 * Constructeur à partir d'un enregistrement parent, de la valeur de l'id de
	 * l'enregistrement fils et de la spécification de l'enregistrement fils.
	 * 
	 * @param inRecord enregistrement parent.
	 * @param inChildSpec spécification du fils.
	 * @param inChildSQLKey clé sur l'enregistrement fils
	 */
	public SingleAssociationNull(P inRecord, String inParentColName, SQLObjectSpecification<CImpl> inChildSpec) {
		super(inRecord, inParentColName, inChildSpec);
	}

	/**
	 * Sélecteur sur la valeur pour l'absence de lien.
	 */
	@Override
	protected Object getNullAssociationValue() {
		return null;
	}
}
