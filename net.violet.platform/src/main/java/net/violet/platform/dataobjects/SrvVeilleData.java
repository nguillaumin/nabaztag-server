package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.violet.platform.datamodel.ObjectSleep;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

public class SrvVeilleData {

	private final VObject objectRef;
	private final String title = "veille";
	private final boolean is24;
	private final List<CCalendar> time = new ArrayList<CCalendar>();

	public SrvVeilleData() {
		this.objectRef = null;
		this.is24 = false;

	}

	private SrvVeilleData(User inUser, VObject inObject) {
		if ((inUser != null) && inUser.getUserHasObject()) {
			this.objectRef = inObject;
			this.is24 = inUser.use24();
			CCalendar myCalendar = new CCalendar(true);
			ObjectSleep sleepObject = Factories.OBJECT_SLEEP.findInfoByObjectAndDay(this.objectRef, Calendar.TUESDAY);
			myCalendar.setTime(sleepObject.getTimeTo());
			this.time.add(myCalendar);

			myCalendar = new CCalendar(true);
			myCalendar.setTime(sleepObject.getTimeFrom());
			this.time.add(myCalendar);

			sleepObject = Factories.OBJECT_SLEEP.findInfoByObjectAndDay(this.objectRef, Calendar.SATURDAY);
			myCalendar = new CCalendar(true);
			myCalendar.setTime(sleepObject.getTimeTo());
			this.time.add(myCalendar);

			myCalendar = new CCalendar(true);
			myCalendar.setTime(sleepObject.getTimeFrom());
			this.time.add(myCalendar);
		} else {
			this.objectRef = null;
			this.is24 = false;
		}

	}

	public static SrvVeilleData generate(User inUser, VObject inObject) {
		return new SrvVeilleData(inUser, inObject);

	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return getTitle();
	}

	/**
	 * @return the attribute srvVeille_time 1
	 */
	public String getTime1() {
		if (this.time.size() > 0) {
			return this.time.get(0).getTimeFormatedShort(this.is24);
		}
		return StringShop.MIDNIGHT;
	}

	/**
	 * @return the attribute srvVeille_time 2
	 */
	public String getTime2() {
		if (this.time.size() > 1) {
			return this.time.get(1).getTimeFormatedShort(this.is24);
		}
		return StringShop.MIDNIGHT;
	}

	/**
	 * @return the attribute srvVeille_time 3
	 */
	public String getTime3() {
		if (this.time.size() > 2) {
			return this.time.get(2).getTimeFormatedShort(this.is24);
		}
		return StringShop.MIDNIGHT;
	}

	/**
	 * @return the attribute srvVeille_time 4
	 */
	public String getTime4() {
		if (this.time.size() > 3) {
			return this.time.get(3).getTimeFormatedShort(this.is24);
		}
		return StringShop.MIDNIGHT;
	}
}
