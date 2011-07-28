package net.violet.platform.datamodel.mock;

import java.sql.Time;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.ObjectSleep;
import net.violet.platform.datamodel.VObject;

public class ObjectSleepMock extends AbstractMockRecord<ObjectSleep, ObjectSleepMock> implements ObjectSleep {

	private static long ID = 1;

	private final int week_day;
	private Time time_from;
	private Time time_to;
	private String time_action;
	private final VObject object;

	public ObjectSleepMock(long inId, VObject inObject, int weekDay, Time inTimeFrom, Time inTimeTo, String inTimeAction) {
		super(inId);
		this.week_day = weekDay;
		this.time_from = inTimeFrom;
		this.time_to = inTimeTo;
		this.time_action = inTimeAction;
		this.object = inObject;
	}

	public ObjectSleepMock(VObject inObject, int day, Time from, Time to, SleepAction action) {
		this(ObjectSleepMock.ID++, inObject, day, from, to, action.toString());
	}

	public int getDayOfWeek() {
		return this.week_day;
	}

	public Time getSleepTime() {
		if (this.time_action.equals(SleepAction.SLEEP.toString())) {
			return this.time_from;
		}

		return this.time_to;
	}

	public String getTimeAction() {
		return this.time_action;
	}

	public Time getTimeFrom() {
		return this.time_from;
	}

	public Time getTimeTo() {
		return this.time_to;
	}

	public Time getWakeTime() {
		if (this.time_action.equals(SleepAction.SLEEP.toString())) {
			return this.time_to;
		}

		return this.time_from;
	}

	public void setTimeFrom(Time inTimeFrom) {
		this.time_from = inTimeFrom;
	}

	public void setTimeInformation(Time inTimeFrom, Time inTimeTo, SleepAction inAction) {
		this.time_from = inTimeFrom;
		this.time_to = inTimeTo;
		this.time_action = inAction.toString();
	}

	public void setTimeTo(Time inTimeTo) {
		this.time_to = inTimeTo;
	}

	public VObject getObject() {
		return this.object;
	}

}
