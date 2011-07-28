package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

public class ObjectSleepImpl extends ObjectRecord<ObjectSleep, ObjectSleepImpl> implements ObjectSleep {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<ObjectSleepImpl> SPECIFICATION = new SQLObjectSpecification<ObjectSleepImpl>("object_sleep", ObjectSleepImpl.class, new SQLKey("sleep_id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "sleep_object", "week_day", };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long sleep_id;
	protected long sleep_object;
	protected int week_day;
	protected Time time_from;
	protected Time time_to;
	protected String time_action;

	private final SingleAssociationNotNull<ObjectSleep, VObject, VObjectImpl> mObject;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected ObjectSleepImpl(long id) throws SQLException {
		this.mObject = new SingleAssociationNotNull<ObjectSleep, VObject, VObjectImpl>(this, "sleep_object", VObjectImpl.SPECIFICATION);
		init(id);
	}

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected ObjectSleepImpl() {
		this.mObject = new SingleAssociationNotNull<ObjectSleep, VObject, VObjectImpl>(this, "sleep_object", VObjectImpl.SPECIFICATION);
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 */
	public ObjectSleepImpl(VObject inObject, int inWeekDay) throws SQLException {

		this.sleep_object = inObject.getId();
		this.week_day = inWeekDay;
		this.mObject = new SingleAssociationNotNull<ObjectSleep, VObject, VObjectImpl>(this, "sleep_object", VObjectImpl.SPECIFICATION);
		init(ObjectSleepImpl.NEW_COLUMNS);
	}

	public ObjectSleepImpl(VObject inObject, int inWeekDay, Time inFrom, Time inTo, SleepAction inAction) throws SQLException {
		this(inObject, inWeekDay);

		setTimeInformation(inFrom, inTo, inAction);
	}

	@Override
	public SQLObjectSpecification<ObjectSleepImpl> getSpecification() {
		return ObjectSleepImpl.SPECIFICATION;
	}

	public VObject getObject() {
		return this.mObject.get(this.sleep_object);
	}

	public int getDayOfWeek() {
		return this.week_day;
	}

	public Time getTimeTo() {
		return this.time_to;
	}

	public Time getTimeFrom() {
		return this.time_from;
	}

	public void setTimeTo(Time inTimeTo) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setTime_to(theUpdateMap, inTimeTo);
		update(theUpdateMap);
	}

	public void setTimeFrom(Time inTimeFrom) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setTime_from(theUpdateMap, inTimeFrom);
		update(theUpdateMap);
	}

	public void setTimeInformation(Time inTimeFrom, Time inTimeTo, SleepAction inAction) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setTime_from(theUpdateMap, inTimeFrom);
		setTime_to(theUpdateMap, inTimeTo);
		setTime_action(theUpdateMap, inAction);
		update(theUpdateMap);
	}

	private void setTime_action(Map<String, Object> inUpdateMap, SleepAction inAction) {
		if ((inAction != null) && !inAction.toString().equals(this.time_action)) {
			this.time_action = inAction.toString();
			inUpdateMap.put("time_action", inAction.toString());
		}

	}

	private void setTime_to(Map<String, Object> inUpdateMap, Time inTimeTo) {
		if ((inTimeTo != null) && !inTimeTo.equals(this.time_to)) {
			this.time_to = inTimeTo;
			inUpdateMap.put("time_to", inTimeTo);
		}
	}

	private void setTime_from(Map<String, Object> inUpdateMap, Time inTimeFrom) {
		if ((inTimeFrom != null) && !inTimeFrom.equals(this.time_from)) {
			this.time_from = inTimeFrom;
			inUpdateMap.put("time_from", inTimeFrom);
		}
	}

	public Time getSleepTime() {
		if (this.time_action.equals(SleepAction.SLEEP.toString())) {
			return this.time_from;
		}

		return this.time_to;
	}

	public Time getWakeTime() {
		if (this.time_action.equals(SleepAction.SLEEP.toString())) {
			return this.time_to;
		}

		return this.time_from;
	}

	public String getTimeAction() {
		return this.time_action;
	}

}
