package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.ObjectSleep;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.ObjectSleepFactory;
import net.violet.platform.datamodel.mock.ObjectSleepMock;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SleepTime;
import net.violet.platform.util.SleepTime.SleepRun;

public class ObjectSleepFactoryMock extends RecordFactoryMock<ObjectSleep, ObjectSleepMock> implements ObjectSleepFactory {

	public ObjectSleepFactoryMock() {
		super(ObjectSleepMock.class);
	}

	public List<ObjectSleep> findByObjectAndDay(VObject inObject, int inDay) {
		final List<ObjectSleep> theResult = new ArrayList<ObjectSleep>();
		for (final ObjectSleep theObjectSleep : findAllMapped().values()) {
			if (inObject.equals(theObjectSleep.getObject()) && (inDay == theObjectSleep.getDayOfWeek())) {
				theResult.add(theObjectSleep);
			}
		}
		return theResult;
	}

	public ObjectSleep findCurrentByObjectAndDay(VObject inObject, CCalendar inCalendar) {

		final int day = inCalendar.get(Calendar.DAY_OF_WEEK);
		ObjectSleep theResult = null;
		for (final ObjectSleep theObjectSleep : findAllMapped().values()) {
			if (inObject.equals(theObjectSleep.getObject()) && (day == theObjectSleep.getDayOfWeek()) && theObjectSleep.getTimeFrom().after(inCalendar.getTime()) && theObjectSleep.getTimeTo().before(inCalendar.getTime())) {
				theResult = theObjectSleep;
				break;
			}
		}
		return theResult;
	}

	/**
	 * Returns an ObjectSleep object containing the configuration for the
	 * provided day. This method is useful to display the user's choices, it has
	 * to be called with Calendar.TUESDAY to retrieve the week configuration and
	 * with Calendar.SUNDAY to retrieve the weekend configuration.
	 * Calendar.MONDAY and Calendar.SATURDAY must not be used.
	 * 
	 * @param inObject
	 * @param inDay
	 * @return
	 */
	public ObjectSleep findInfoByObjectAndDay(VObject inObject, int inDay) {
		final List<ObjectSleep> theResult = new ArrayList<ObjectSleep>();
		for (final ObjectSleep theObjectSleep : findAllMapped().values()) {
			if (inObject.equals(theObjectSleep.getObject()) && (inDay == theObjectSleep.getDayOfWeek())) {
				theResult.add(theObjectSleep);
			}
		}

		if (theResult.size() == 3) {
			if (theResult.get(0).getTimeAction().equals(theResult.get(1).getTimeAction())) {
				return theResult.get(2);
			} else if (theResult.get(0).getTimeAction().equals(theResult.get(2).getTimeAction())) {
				return theResult.get(1);
			} else {
				return theResult.get(0);
			}
		}

		if (theResult.size() == 1) {
			return theResult.get(0);
		}
		return null;
	}

	public void resetSleepTime(VObject inObject) {
		for (final ObjectSleep o : findAll()) {
			if (o.getObject().getId() == inObject.getId()) {
				o.delete();
			}
		}
	}

	public void setObjectSleepTime(VObject inObject, SleepTime inSleepTime) {
		// deletes all old rows
		resetSleepTime(inObject);

		final List<SleepRun> theListSleepRuns = inSleepTime.getSleepRuns();
		for (final SleepRun theSleepRun : theListSleepRuns) {
			new ObjectSleepMock(inObject, theSleepRun.getDay(), theSleepRun.getFrom(), theSleepRun.getTo(), theSleepRun.getAction());
		}
	}

}
