package net.violet.db.records.associations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.common.StringShop;
import net.violet.db.cache.Cache;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.Record;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SQLSpecification;
import net.violet.db.records.SgbdConnection;
import net.violet.db.records.SgbdResult;

public final class MultipleDecoratedAssociation<P extends AbstractSQLRecord<?, P>, RIntf extends Record<RIntf>, RImpl extends ObjectRecord<RIntf, RImpl>, DIntf extends AssoRecord<RIntf, DIntf>, DImpl extends AssociationRecord<RIntf, DIntf, DImpl>> extends AbstractDecoratedAssociation<P, RIntf, RImpl, List<DIntf>, DIntf, DImpl> {

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
	private MultipleDecoratedAssociation(P inRecord, SQLObjectSpecification<RImpl> inChildSpec, String inChildIdColName, SQLAssociationSpecification<DImpl> inDecorationSpec, String inParentIdColName) throws SQLException {
		super(inRecord, inChildSpec, inChildIdColName, inDecorationSpec, inParentIdColName);
	}

	@Override
	protected Map<RIntf, List<DIntf>> getAllFromDatabase() throws SQLException {
		// On remplit les données depuis la table SQL.
		final List<Object> theConditionVals = Collections.singletonList((Object) getParentId());
		final String theCondition = getQualifiedParentIdColName() + " = ?";
		final Map<RIntf, List<DIntf>> theResult;
		final SgbdConnection theConnection = new SgbdConnection();
		try {
			final SgbdResult theSgbdResult = AssociationRecord.fetchRecords(theConnection, getDecorationSpec(), Collections.singletonMap(getChildIdColName(), getChildSpec()), theCondition, theConditionVals, AssociationRecord.NO_ORDER, AssociationRecord.NO_LIMIT);
			try {
				final ResultSet theResultSet = theSgbdResult.getResultSet();
				theResult = new HashMap<RIntf, List<DIntf>>();

				while (theResultSet.next()) {
					// On récupère la liste pour cet enfant.
					// Crée/récupère l'objet enfant
					final RIntf theObject = ObjectRecord.createObject(getChildSpec(), theResultSet);
					List<DIntf> theList = theResult.get(theObject);
					if (theList == null) {
						// Création de la liste.
						theList = new LinkedList<DIntf>();
						theResult.put(theObject, theList);
					}

					final DIntf theCreatedObject = AssociationRecord.createObject(getDecorationSpec(), theResultSet);
					theList.add( theCreatedObject );
				}

			} finally {
				theSgbdResult.close();
			}
		} finally {
			theConnection.close();
		}

		// On rend les listes immutable.
		// (pour le moment, les modifications se font par bloc).
		for (final RIntf key : theResult.keySet()) {
			theResult.put(key, Collections.unmodifiableList(theResult.get(key)));
		}

		return theResult;
	}

	public static <P extends AbstractSQLRecord<?, P>, RIntf extends Record<RIntf>, RImpl extends ObjectRecord<RIntf, RImpl>, DIntf extends AssoRecord<RIntf, DIntf>, DImpl extends AssociationRecord<RIntf, DIntf, DImpl>> MultipleDecoratedAssociation<P, RIntf, RImpl, DIntf, DImpl> createMultipleDecoratedAssociation(P inRecord, SQLObjectSpecification<RImpl> inChildSpec, String inChildIdColName, SQLAssociationSpecification<DImpl> inDecorationSpec, String inParentIdColName) throws SQLException {

		final SQLKey thePrimaryKey = inRecord.getSpecification().getPrimaryKey();
		MultipleDecoratedAssociation<P, RIntf, RImpl, DIntf, DImpl> theResult = (MultipleDecoratedAssociation<P, RIntf, RImpl, DIntf, DImpl>) Cache.get(thePrimaryKey, inRecord.getValuesFromSQLKey(thePrimaryKey).toArray(), inDecorationSpec.getTableName(), inParentIdColName, inChildIdColName);

		if (theResult == null) {
			theResult = new MultipleDecoratedAssociation<P, RIntf, RImpl, DIntf, DImpl>(inRecord, inChildSpec, inChildIdColName, inDecorationSpec, inParentIdColName);
			Cache.add(theResult);
		}
		return theResult;
	}

	/**
	 * Ajout d'un element
	 */
	@Override
	protected void doAdd(RIntf object, List<DIntf> decorations) throws SQLException, NoSuchFieldException, IllegalAccessException {
		final SgbdConnection theConnection = new SgbdConnection();
		final String theChildIdColName = getChildIdColName();
		final String theParentIdColName = getParentIdColName();

		try {
			for (final DIntf decoration : decorations) {
				String listFields = StringShop.EMPTY_STRING;
				String listValues = StringShop.EMPTY_STRING;
				final List<Object> theVals = new ArrayList<Object>();
				final SQLSpecification<DImpl> decorationSpec = getDecorationSpec();
				for (final String fieldName : decorationSpec.getColumns()) {
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
						theVal = object.getId();
					} else if (theParentIdColName.equals(fieldName)) {
						theVal = getParentId();
					}

					if (theVal == null) {
						// On récupère la valeur de décoration.
						theVal = ((AssociationRecord) decoration).get(fieldName);
					} else {
						// Mise à jour de la valeur de décoration.
						((AssociationRecord) decoration).set(fieldName, theVal);
					}
					theVals.add(theVal);
				}
				final String theQuery = "insert into " + decorationSpec.getTableName() + "(" + listFields + ") values(" + listValues + ")";
				theConnection.doQueryUpdatePS(theQuery, theVals);

				// la décoration est désormais dans la base.
				((AssociationRecord) decoration).initClean();
			}

		} finally {
			theConnection.close();
		}

		object.addDeletionListener(this);
		// On s'intéresse à la suppression des décorations.
		for (final DIntf theDecoration : decorations) {
			addToDeletionListeners(theDecoration);
		}
	}

	/**
	 * Suppression d'un élément.
	 * 
	 * @param inElement élément à supprimer.
	 */
	@Override
	protected void doRemove(Entry<RIntf, List<DIntf>> inElement) throws SQLException, NoSuchFieldException, IllegalAccessException {
		final SgbdConnection theConnection = new SgbdConnection();

		try {
			inElement.getKey().removeDeletionListener(this);
			for (final DIntf theChildRecord : inElement.getValue()) {
				final SQLSpecification<DImpl> decorationSpec = getDecorationSpec();
				String theQuery = "delete from " + decorationSpec.getTableName() + " where " + getQualifiedParentIdColName() + " = ? ";
				final List<Object> theVals = new ArrayList<Object>();
				theVals.add(getParentId());
				final String theChildColName = getChildIdColName();
				theQuery += "and " + decorationSpec.getTableName() + "." + theChildColName + " = ? ";
				theVals.add(((AssociationRecord) theChildRecord).get(theChildColName));
				theConnection.doQueryUpdatePS(theQuery, theVals);
				removeFromDeletionListeners(theChildRecord);
			}
		} finally {
			theConnection.close();
		}
	}

	/**
	 * Méthode appelée lors de la suppression d'un objet.
	 * 
	 * @param inRecord objet supprimé
	 * @param inValue entrée dans la liste pour tester.
	 * @return <code>true</code> s'il faut supprimmer l'association,
	 *         <code>false</code> sinon.
	 */
	@Override
	protected boolean doRecordDeleted(DIntf inRecord, RIntf inKey, List<DIntf> inValue) {
		// La liste n'est pas modifiable, on en crée une nouvelle.
		boolean theResult = false;
		if (inValue.contains(inRecord)) {
			final List<DIntf> theNewList = new LinkedList<DIntf>(inValue);
			theNewList.remove(inRecord);
			getData().put(inKey, Collections.unmodifiableList(theNewList));
			theResult = theNewList.isEmpty();
		}
		return theResult;
	}

	@Override
	protected void registerForDeletionEvent(RIntf inRecord, List<DIntf> inDecorations) {
		inRecord.addDeletionListener(this);
		// On s'intéresse à la suppression des décorations.
		for (final DIntf theDecoration : inDecorations) {
			addToDeletionListeners(theDecoration);
		}
	}

	@Override
	protected void unregisterForDeletionEvent(RIntf inRecord, List<DIntf> inDecorations) {
		inRecord.removeDeletionListener(this);
		// On s'intéresse à la suppression des décorations.
		for (final DIntf theDecoration : inDecorations) {
			removeFromDeletionListeners(theDecoration);
		}
	}

	@Override
	protected void updateDecorationFromDatabase(List<DIntf> inDecoration) {
		// TODO Auto-generated method stub
		for (final DIntf inDecorationElement : inDecoration) {
			if (inDecorationElement.isInvalid()) {
				inDecorationElement.updateFromDatabase();
			}
		}
	}

}
