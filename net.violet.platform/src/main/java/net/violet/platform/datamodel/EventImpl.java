package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.util.CCalendar;

import org.apache.log4j.Logger;

public class EventImpl extends ObjectRecord<Event, EventImpl> implements Event {


	private static final Logger LOGGER = Logger.getLogger(EventImpl.class);

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<EventImpl> SPECIFICATION = new SQLObjectSpecification<EventImpl>("event", EventImpl.class, new SQLKey("event_id"));

	/**
	 * Champs de l'enregistrement.
	 */
	protected int event_id;
	protected int event_obj;
	protected int event_creation;
	protected int event_purge;
	protected int event_mode;

	@Override
	public SQLObjectSpecification<EventImpl> getSpecification() {
		return EventImpl.SPECIFICATION;
	}

	private final SingleAssociationNotNull<Event, VObject, VObjectImpl> eventObject;

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	public static final String[] NEW_COLUMNS = new String[] { "event_obj", "event_creation", "event_purge", "event_mode", };

	protected EventImpl() {
		this.eventObject = new SingleAssociationNotNull<Event, VObject, VObjectImpl>(this, "event_obj", VObjectImpl.SPECIFICATION);
	}

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected EventImpl(long id) throws SQLException {
		init(id);

		this.eventObject = new SingleAssociationNotNull<Event, VObject, VObjectImpl>(this, "event_obj", VObjectImpl.SPECIFICATION);
	}

	/**
	 * Contructeur public
	 * 
	 * @param obj destinataire du message
	 * @param creation date de création du message
	 * @param purge date de purge ou 0
	 * @param mode mode du message
	 * @throws SQLException
	 */
	public EventImpl(int obj, int creation, int purge, int mode) {
		this.event_obj = obj;
		this.event_creation = creation;
		this.event_purge = purge;
		this.event_mode = mode;
		this.eventObject = new SingleAssociationNotNull<Event, VObject, VObjectImpl>(this, "event_obj", VObjectImpl.SPECIFICATION);
	}

	/**
	 * Détermine si on a un message en attente.
	 */
	public static boolean hasEvent(VObject inObject) {
		final List<Object> values = Collections.singletonList((Object) inObject.getId());
		List<Event> theList = null;
		try {
			theList = new ArrayList<Event>(AbstractSQLRecord.findAll(EventImpl.SPECIFICATION, "event_obj = ?", values, null, 0, 1));
		} catch (final SQLException anException) {
			EventImpl.LOGGER.fatal(anException, anException);
		}
		return (theList != null) && !theList.isEmpty();
	}

	/**
	 * Récupère les deux derniers messages reçus d'un objet.
	 */
	public static List<Event> findTwoLastReceivedMessages(VObject inObject) {
		List<Event> theResult = null;
		final List<Object> values = Collections.singletonList((Object) inObject.getId());
		try {
			theResult = new ArrayList<Event>(AbstractSQLRecord.findAll(EventImpl.SPECIFICATION, "event_obj = ?", values, " event_creation, event_id ", 0, 2));
		} catch (final SQLException anException) {
			EventImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition.
	 * 
	 * @param inWalker itérateur
	 * @return le nombre d'événements traités.
	 */
	public static int walkDeferredMessages(RecordWalker<Event> inWalker) {

		final List<Object> theValues = Collections.singletonList((Object) CCalendar.getCurrentTimeInSecond());
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(EventImpl.SPECIFICATION, "event_obj < 0 and event_creation < ?", theValues, null /*
																																									 * order
																																									 * by
																																									 */, 0 /* skip */, inWalker);

		} catch (final SQLException anException) {
			EventImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition.
	 * 
	 * @param inWalker itérateur
	 * @return le nombre d'événements traités.
	 */
	public static int walkPurgeable(RecordWalker<Event> inWalker) {
		final List<Object> theValues = Collections.singletonList((Object) CCalendar.getCurrentTimeInSecond());
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(EventImpl.SPECIFICATION, "event_purge > 0 and event_purge < ?", theValues, null /*
																																							 * order
																																							 * by
																																							 */, 0 /* skip */, inWalker);

		} catch (final SQLException anException) {
			EventImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param zone zone de la table
	 * @param id id de la table.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static Event find(long id) {
		Event event = null;
		try {
			event = AbstractSQLRecord.findByKey(EventImpl.SPECIFICATION, EventImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			EventImpl.LOGGER.fatal(se, se);
		}
		return event;
	}

	@Override
	public Long getId() {
		return new Long(this.event_id);
	}

	public int getEvent_obj() {
		return this.event_obj;
	}

	public void setObject(int object) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setEvent_obj(theUpdateMap, object);
		update(theUpdateMap);
	}

	private void setEvent_obj(Map<String, Object> inUpdateMap, int event_obj) {
		if (this.event_obj != event_obj) {
			this.event_obj = event_obj;
			inUpdateMap.put("event_obj", event_obj);
		}
	}

	public VObject getEventObject() {
		return this.eventObject.get(this.event_obj);
	}

}
