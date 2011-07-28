package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.platform.message.Sequence;

public class EvSeqImpl extends ObjectRecord<EvSeq, EvSeqImpl> implements Sequence, EvSeq {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<EvSeqImpl> SPECIFICATION = new SQLObjectSpecification<EvSeqImpl>("evseq", EvSeqImpl.class, new SQLKey("evseq_id"));

	/**
	 * Champs de l'enregistrement.
	 */
	protected long evseq_id;
	protected long evseq_event;
	protected int evseq_order;
	protected int evseq_type;
	protected String evseq_val;

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "evseq_event", "evseq_order", "evseq_type", "evseq_val", };

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected EvSeqImpl(long id) throws SQLException {
		init(id);
	}

	protected EvSeqImpl() {
		// This space for rent.
	}

	/**
	 * Constructeur publique
	 * 
	 * @param event
	 * @param order
	 * @param type
	 * @param val
	 * @throws SQLException
	 */
	private EvSeqImpl(long eventId, int order, int type, String val) {
		this.evseq_event = eventId;
		this.evseq_order = order;
		this.evseq_type = type;
		this.evseq_val = val;
	}

	@Override
	public Long getId() {
		return this.evseq_id;
	}

	@Override
	public SQLObjectSpecification<EvSeqImpl> getSpecification() {
		return EvSeqImpl.SPECIFICATION;
	}

	public String getData() {
		return this.evseq_val;
	}

	public int getType() {
		return this.evseq_type;
	}

	public static void insert(long eventId, int order, int type, String val) throws SQLException {
		final EvSeqImpl theRecord = new EvSeqImpl(eventId, order, type, val);
		theRecord.insertRecord(EvSeqImpl.NEW_COLUMNS);
	}

}
