package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.ObjectSleep;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SleepTime;

public interface ObjectSleepFactory extends RecordFactory<ObjectSleep> {

	/**
	 * Returns a list of all the defined ObjectSleep for the provided object and
	 * day.
	 * 
	 * @param inObject the object.
	 * @param inDay the day, see availble constantes in
	 *            {@link java.util.Calendar} .
	 * @return a list of ObjectSleep objects (can be empty).
	 */
	List<ObjectSleep> findByObjectAndDay(VObject inObject, int inDay);

	/**
	 * Returns the "most meaningful" ObjectSleep for the provided object and
	 * day. This method should mainly be used to display the user configuration,
	 * it returns the most significant time period. It is strongly recommended
	 * to use Calendar.TUESDAY as inDay value to retrieve week configuration and
	 * Calendar.SUNDAY to retrieve weekend configuration. Calendar.MONDAY and
	 * Calendar.SATURDAY must not be used because those days often have specific
	 * configuration.
	 * 
	 * @param inObject the object
	 * @param inDay the day (see the note above to know which values can be
	 *            used)
	 * @return the ObjectSleep, can be null.
	 */
	ObjectSleep findInfoByObjectAndDay(VObject inObject, int inDay);

	/**
	 * Returns the ObjectSleep object matching the moment represented by the
	 * provided Calendar object.
	 * 
	 * @param inObject the object.
	 * @param inCalendar the calendar, i.e. the current moment.
	 * @return the matching ObjectSleep, can be null.
	 */
	ObjectSleep findCurrentByObjectAndDay(VObject inObject, CCalendar inCalendar);

	/**
	 * Use this method to delete the configuration of the provided object. All
	 * rows are removed.
	 * 
	 * @param inObject the object.
	 */
	void resetSleepTime(VObject inObject);

	/**
	 * Uses the provided SleepTime object to set the object configuration.
	 * Previous configuration is deleted.
	 * 
	 * @param inObject the object.
	 * @param inSleepTime the SleepTime object containing the new configuration.
	 */
	void setObjectSleepTime(VObject inObject, SleepTime inSleepTime);

}
