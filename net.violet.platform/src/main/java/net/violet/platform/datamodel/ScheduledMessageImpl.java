package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public class ScheduledMessageImpl extends ObjectRecord<ScheduledMessage, ScheduledMessageImpl> implements ScheduledMessage {


	private static final Logger LOGGER = Logger.getLogger(ScheduledMessageImpl.class);

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<ScheduledMessageImpl> SPECIFICATION = new SQLObjectSpecification<ScheduledMessageImpl>("scheduled_message", ScheduledMessageImpl.class, new SQLKey[] { new SQLKey("id"), new SQLKey("message_id") });

	protected long id;
	protected Timestamp date;
	protected String packet;
	protected Long message_id;

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ScheduledMessage#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<ScheduledMessageImpl> getSpecification() {
		return ScheduledMessageImpl.SPECIFICATION;
	}

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "date", "packet", "message_id" };

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ScheduledMessage#getPacket()
	 */
	public String getPacket() {
		return this.packet;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ScheduledMessage#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * Constructeur par défaut.
	 */
	protected ScheduledMessageImpl() {
		// This space for rent.
	}

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected ScheduledMessageImpl(long id) throws SQLException {
		init(id);
	}

	/**
	 * Constructeur pour un objet inséré immédiatement (et pour lequel on ne
	 * garde aucune référence).
	 * 
	 * @param inDate
	 * @param inPacket
	 * @param inMessageId
	 */
	private ScheduledMessageImpl(Timestamp inDate, String inPacket, Long inMessageId) {
		this.date = inDate;
		this.packet = inPacket;
		this.message_id = inMessageId;
	}

	public static void insert(Timestamp inDate, String inPacket, Long inMessageId) throws SQLException {
		final ScheduledMessageImpl theRecord = new ScheduledMessageImpl(inDate, inPacket, inMessageId);
		theRecord.insertRecord(ScheduledMessageImpl.NEW_COLUMNS);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ScheduledMessage#getMessage_id()
	 */
	public Long getMessage_id() {
		return this.message_id;
	}

	public static int walkScheduledMessage(RecordWalker<ScheduledMessage> inWalker) {
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(ScheduledMessageImpl.SPECIFICATION, "`date` <= NOW()", null, "`date` desc", 0 /* skip */, inWalker);
		} catch (final SQLException anException) {
			ScheduledMessageImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

}
