package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.ObjectSleep;
import net.violet.platform.datamodel.ObjectSleepImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.ObjectSleep.SleepAction;
import net.violet.platform.datamodel.factories.ObjectSleepFactory;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SleepTime;
import net.violet.platform.util.SleepTime.SleepRun;

import org.apache.log4j.Logger;

public class ObjectSleepFactoryImpl extends RecordFactoryImpl<ObjectSleep, ObjectSleepImpl> implements ObjectSleepFactory {

	private static final Logger LOGGER = Logger.getLogger(ObjectSleepFactoryImpl.class);

	ObjectSleepFactoryImpl() {
		super(ObjectSleepImpl.SPECIFICATION);
	}

	public List<ObjectSleep> findByObjectAndDay(VObject inObject, int inDay) {
		return findAll(" sleep_object = ? AND week_day = ? ", Arrays.asList(new Object[] { inObject.getId(), inDay }), " time_from ");
	}

	public ObjectSleep findCurrentByObjectAndDay(VObject inObject, CCalendar inCalendar) {
		final Time now = CCalendar.getSQLTime(inCalendar.getHour(), inCalendar.getMinute());
		final int day = inCalendar.get(Calendar.DAY_OF_WEEK);

		return find(" sleep_object = ? AND week_day = ? AND time_from <= ? and time_to > ? ", Arrays.asList(new Object[] { inObject.getId(), day, now, now }));
	}

	public ObjectSleep findInfoByObjectAndDay(VObject inObject, int inDay) {
		final List<ObjectSleep> list = findByObjectAndDay(inObject, inDay);

		final int size = list.size();

		if (size == 3) {
			return list.get(1);
		} else if ((size == 2) || ((size == 1) && list.get(0).getTimeAction().equals(SleepAction.SLEEP.toString()))) {
			return list.get(0);
		}
		return null;
	}

	public void resetSleepTime(VObject inObject) {
		// deletes each involved row for this object
		for (final ObjectSleep sleep : findAll(" sleep_object = ? ", Arrays.asList((Object) (inObject.getId())), null)) {
			sleep.delete();
		}
	}

	public void setObjectSleepTime(VObject inObject, SleepTime inSleepTime) {
		// deletes all old rows
		resetSleepTime(inObject);
		try {
			final List<SleepRun> theListSleepRuns = inSleepTime.getSleepRuns();
			for (final SleepRun theSleepRun : theListSleepRuns) {
				new ObjectSleepImpl(inObject, theSleepRun.getDay(), theSleepRun.getFrom(), theSleepRun.getTo(), theSleepRun.getAction());
			}
		} catch (final SQLException e) {
			ObjectSleepFactoryImpl.LOGGER.fatal(e, e);
		}

	}

}
