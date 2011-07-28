package net.violet.platform.datamodel;

import java.sql.Time;
import java.util.TimeZone;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;

/**
 * An Object used to represent time periods during which the rabbit must sleep
 * or not. TimeFrom and TimeTo are used to define the period and TimeAction the
 * associated action. Each day of the week has its own configuration. Time
 * objects stored in the database are not created according any timezone, e.g.
 * if a london citizen defines waking up time for 10:00 the Time object stored
 * in the DB will really be 10:00 (and not 11:00).
 */
public interface ObjectSleep extends Record<ObjectSleep> {

	/**
	 * An Enum containing all available actions for a time period.
	 */
	public enum SleepAction {

		WAKE {

			@Override
			public String toString() {
				return "WAKE";
			}
		},
		SLEEP {

			@Override
			public String toString() {
				return "SLEEP";
			}
		},
	}

	VObject getObject();

	/**
	 * Returns the id of the day defined by this ObjectSleep object. The 7 ids
	 * are the same as in the java.util.Calendar class (constantes such as
	 * Calendar.SUNDAY, Calendar.MONDAY, ...).
	 * 
	 * @return
	 */
	int getDayOfWeek();

	/**
	 * Returns the time starting the period.
	 * 
	 * @return
	 */
	Time getTimeFrom();

	/**
	 * Returns the time ending the period.
	 * 
	 * @return
	 */
	Time getTimeTo();

	/**
	 * Returns the wake up time (can be different from TimeFrom in the case of a
	 * nap).
	 * 
	 * @return
	 */
	Time getWakeTime();

	/**
	 * Returns the sleep time (can be different from TimeTo in the case of a
	 * nap).
	 * 
	 * @return
	 */
	Time getSleepTime();

	void setTimeFrom(Time inTimeFrom);

	void setTimeTo(Time inTimeTo);

	void setTimeInformation(Time inTimeFrom, Time inTimeTo, SleepAction inAction);

	String getTimeAction();

	class ObjectSleepCommon {

		/**
		 * Est-ce que le lapin est en veille.
		 */
		public static boolean asleep(VObject inObject) {
			final Timezone theTZ = inObject.getTimeZone();
			if (theTZ != null) {
				final CCalendar theCal = new CCalendar(false, TimeZone.getTimeZone(theTZ.getTimezone_javaId()));

				final ObjectSleep theObjectSleep = Factories.OBJECT_SLEEP.findCurrentByObjectAndDay(inObject, theCal);

				if ((theObjectSleep != null) && (theObjectSleep.getTimeAction() != null)) {
					return theObjectSleep.getTimeAction().equals(ObjectSleep.SleepAction.SLEEP.toString());
				}
			}
			return false;
		}
	}

}
