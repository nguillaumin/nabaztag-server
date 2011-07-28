package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

import net.violet.db.connector.Connector.ConnectionMode;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringTools;

import org.apache.log4j.Logger;

public class MessageLogImpl extends ObjectRecord<MessageLog, MessageLogImpl> implements MessageLog {

	// Logger
	private static final Logger LOGGER = Logger.getLogger(MessageLogImpl.class);

	// Spécification
	public static final SQLObjectSpecification<MessageLogImpl> SPECIFICATION = new SQLObjectSpecification<MessageLogImpl>("message_log", MessageLogImpl.class, new SQLKey("id"), false, ConnectionMode.STATS);

	// Liste des colonnes pour le constructeur pour les nouveaux enregistrements
	private static final String[] NEW_COLUMNS = new String[] { "recipient", "body", "label", "timeOfDelivery", "nbItems" };

	private static final Timer TIMER = new Timer("MessageLog", true);
	private static final long DELAY = 0L;

	// Fields
	protected long id;
	protected long recipient;
	protected Long service_id;
	protected Long body;
	protected String label;
	protected Timestamp timeOfDelivery;
	protected long nbItems;

	/** constructeurs **/
	protected MessageLogImpl(long id) throws SQLException {
		init(id);
	}

	protected MessageLogImpl() {
	}

	public static void insert(VObject inObject, Files inBody, String inLabel, CCalendar inTimeOfDelivery, long inNbItems) throws IllegalArgumentException {
		final VObject object = inObject;
		final Files body = inBody;
		final String label = inLabel;
		final CCalendar timeOfDelivery = inTimeOfDelivery;
		final long nbItems = inNbItems;
		final TimerTask theTask = new TimerTask() {

			@Override
			public void run() {
				try {
					final MessageLogImpl theRecord = new MessageLogImpl((Long) AbstractSQLRecord.getObjectId((VObjectImpl) object), (Long) AbstractSQLRecord.getObjectId((FilesImpl) body), label, timeOfDelivery, nbItems);
					theRecord.insertRecord(MessageLogImpl.NEW_COLUMNS);
				} catch (final Throwable e) {
					MessageLogImpl.LOGGER.fatal(e, e);
				}
			}
		};

		MessageLogImpl.TIMER.schedule(theTask, MessageLogImpl.DELAY);
	}

	private MessageLogImpl(long inRecipient, long inBodyId, String inLabel, CCalendar inTimeOfDelivery, long inNbItems) {
		this.recipient = inRecipient;
		this.body = inBodyId;
		this.label = StringTools.truncate(inLabel, 255);
		this.timeOfDelivery = new Timestamp((inTimeOfDelivery != null) ? inTimeOfDelivery.getTimeInMillis() : System.currentTimeMillis());
		this.nbItems = inNbItems;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageLog#getId()
	 */

	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageLog#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<MessageLogImpl> getSpecification() {
		return MessageLogImpl.SPECIFICATION;
	}

	/**
	 * Finds a MessageImpl from the given id
	 * 
	 * @param inId id of the MessageLogImpl to look for
	 * @return the MessageLogImpl, null if given id has not been found
	 */
	public static MessageLog find(long inId) {
		MessageLog messageLog = null;
		try {
			messageLog = AbstractSQLRecord.findByKey(MessageLogImpl.SPECIFICATION, MessageLogImpl.SPECIFICATION.getTableKeys()[0], inId);
		} catch (final SQLException se) {
			MessageLogImpl.LOGGER.fatal(se, se);
		}
		return messageLog;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageLog#getBodyId()
	 */
	public long getBodyId() {
		return this.body;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageLog#getTimeOfDelivery()
	 */
	public CCalendar getTimeOfDelivery() {
		return new CCalendar(this.timeOfDelivery.getTime());
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageLog#getLabel()
	 */
	public String getLabel() {
		return this.label;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageLog#getNbItems()
	 */
	public long getNbItems() {
		return this.nbItems;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageLog#getRecipient()
	 */
	public long getRecipient() {
		return this.recipient;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageLog#getRecipientObject()
	 */
	public VObject getRecipientObject() {
		return Factories.VOBJECT.find(this.recipient);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageLog#getService_id()
	 */
	public long getService_id() {
		return this.service_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.MessageLog#getService()
	 */
	public Service getService() {
		if (this.service_id != null) {
			return ServiceImpl.find(this.service_id);
		}
		return null;
	}

}
