package net.violet.platform.util;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.datamodel.ObjectSleep.SleepAction;

import org.apache.log4j.Logger;

/**
 * Classe pour représenter les heures de couché/réveil.
 */
public class SleepTime {

	private static final Logger LOGGER = Logger.getLogger(SleepTime.class);

	private static final Time MIDNIGHT;
	private static final Time BEFORE_MIDNIGHT;
	static {
		Time theMidnightTime = null;
		Time theB4MidnightTime = null;
		try {

			theMidnightTime = CCalendar.getSQLTime(StringShop.MIDNIGHT);

			theB4MidnightTime = CCalendar.getSQLTime("23:59:59");
		} catch (final ParseException e) {
			SleepTime.LOGGER.fatal(e, e);
		} finally {
			MIDNIGHT = theMidnightTime;
			BEFORE_MIDNIGHT = theB4MidnightTime;
		}
	}

	public static final class SleepRun {

		private final Time mFrom;
		private final Time mTo;
		private final int mDay;
		private final SleepAction mAction;

		public Time getFrom() {
			return this.mFrom;
		}

		public Time getTo() {
			return this.mTo;
		}

		public int getDay() {
			return this.mDay;
		}

		public SleepAction getAction() {
			return this.mAction;
		}

		public SleepRun(Time inFrom, Time inTo, int inDay, SleepAction inAction) {
			this.mFrom = inFrom;
			this.mTo = inTo;
			this.mDay = inDay;
			this.mAction = inAction;
		}

		@Override
		public String toString() {
			return this.mDay + ":(" + this.mFrom + "-" + this.mTo + ")->" + this.mAction;
		}

		@Override
		public boolean equals(Object inAlter) {
			final boolean theResult;
			if (inAlter instanceof SleepRun) {
				final SleepRun alterAsSr = (SleepRun) inAlter;
				theResult = (this.mDay == alterAsSr.mDay) && (this.mAction == alterAsSr.mAction) && this.mFrom.equals(alterAsSr.mFrom) && this.mTo.equals(alterAsSr.mTo);
			} else {
				theResult = false;
			}
			return theResult;
		}

		@Override
		public int hashCode() {
			return this.mDay + this.mAction.hashCode() + this.mFrom.hashCode() + this.mTo.hashCode();
		}
	}

	static final class SleepSwitch implements Comparable<SleepSwitch> {

		private final Time mTime;
		private final int mDay;
		private final SleepAction mAction;

		public Time getTime() {
			return this.mTime;
		}

		public int getDay() {
			return this.mDay;
		}

		public SleepAction getAction() {
			return this.mAction;
		}

		SleepSwitch(Time inTime, int inDay, SleepAction inAction) {
			this.mTime = inTime;
			this.mDay = inDay;
			this.mAction = inAction;
		}

		public int compareTo(SleepSwitch inAlter) {
			int theResult;
			if (this.mDay < inAlter.mDay) {
				theResult = -1;
			} else if (this.mDay > inAlter.mDay) {
				theResult = 1;
			} else {
				theResult = this.mTime.compareTo(inAlter.mTime);
			}
			return theResult;
		}

		@Override
		public String toString() {
			return this.mDay + ":" + this.mTime + "->" + this.mAction;
		}

		@Override
		public boolean equals(Object inAlter) {
			final boolean theResult;
			if (inAlter instanceof SleepSwitch) {
				final SleepSwitch alterAsSw = (SleepSwitch) inAlter;
				theResult = (this.mDay == alterAsSw.mDay) && (this.mAction == alterAsSw.mAction) && this.mTime.equals(alterAsSw.mTime);
			} else {
				theResult = false;
			}
			return theResult;
		}

		@Override
		public int hashCode() {
			return this.mDay + this.mAction.hashCode() + this.mTime.hashCode();
		}
	}

	private final Time weekWake;
	private final Time weekSleep;
	private final Time weekEndWake;
	private final Time weekEndSleep;

	/**
	 * Constructeur à partir des heures de week-end et de semaine non égaux
	 * Attention à l'ordre!
	 */
	public SleepTime(int inBeforeH, int inBeforeM, int inAfterH, int inAfterM, int inWeBeforeH, int inWeBeforeM, int inWeAfterH, int inWeAfterM) {
		if (inAfterH != -1) {
			this.weekWake = CCalendar.getSQLTime(inBeforeH, inBeforeM);
			this.weekSleep = CCalendar.getSQLTime(inAfterH, inAfterM);
		} else {
			this.weekWake = null;
			this.weekSleep = null;
		}

		if (inWeAfterH != -1) {
			this.weekEndWake = CCalendar.getSQLTime(inWeBeforeH, inWeBeforeM);
			this.weekEndSleep = CCalendar.getSQLTime(inWeAfterH, inWeAfterM);
		} else {
			this.weekEndWake = null;
			this.weekEndSleep = null;
		}
	}

	public SleepTime(String inWeekWake, String inWeekSleep, String inWeekEndWake, String inWeekEndSleep) throws ParseException {
		if (net.violet.common.StringShop.EMPTY_STRING.equals(inWeekWake)) {
			this.weekWake = null;
		} else {
			this.weekWake = CCalendar.getSQLTime(inWeekWake);
		}
		if (net.violet.common.StringShop.EMPTY_STRING.equals(inWeekSleep)) {
			this.weekSleep = null;
		} else {
			this.weekSleep = CCalendar.getSQLTime(inWeekSleep);
		}
		if (net.violet.common.StringShop.EMPTY_STRING.equals(inWeekEndWake)) {
			this.weekEndWake = null;
		} else {
			this.weekEndWake = CCalendar.getSQLTime(inWeekEndWake);
		}
		if (net.violet.common.StringShop.EMPTY_STRING.equals(inWeekEndSleep)) {
			this.weekEndSleep = null;
		} else {
			this.weekEndSleep = CCalendar.getSQLTime(inWeekEndSleep);
		}
	}

	public Time getWeekWake() {
		return this.weekWake;
	}

	public Time getWeekSleep() {
		return this.weekSleep;
	}

	public Time getWeekEndWake() {
		return this.weekEndWake;
	}

	public Time getWeekEndSleep() {
		return this.weekEndSleep;
	}

	List<SleepSwitch> getSleepSwitches() {
		final List<SleepSwitch> theSwitches = new ArrayList<SleepSwitch>();

		// Ajout des switch (weekend/semaine).
		if (this.weekWake != null) {
			if (this.weekWake.equals(this.weekSleep)) { // même heure dans la semaine, veille toute la semaine
				theSwitches.add(new SleepSwitch(SleepTime.MIDNIGHT, Calendar.MONDAY, SleepAction.SLEEP));

				if (this.weekEndWake != null) {
					if (!this.weekEndWake.equals(this.weekEndSleep)) {
						theSwitches.add(new SleepSwitch(this.weekEndWake, Calendar.SATURDAY, SleepAction.WAKE));
					} else {// même heure dans le week end, veille tout le week
						// end
						theSwitches.add(new SleepSwitch(SleepTime.MIDNIGHT, Calendar.SATURDAY, SleepAction.SLEEP));
						theSwitches.add(new SleepSwitch(SleepTime.MIDNIGHT, Calendar.SUNDAY, SleepAction.SLEEP));
					}
				} else {
					theSwitches.add(new SleepSwitch(this.weekWake, Calendar.SATURDAY, SleepAction.WAKE));
				}
			} else {
				for (int theDay = Calendar.MONDAY; theDay <= Calendar.FRIDAY; theDay++) {
					if (this.weekWake.compareTo(this.weekSleep) > 0) {
						// wake >= sleep -> cas sieste/couche-tard
						final int theNextDay = theDay + 1;
						if ((theNextDay != Calendar.SATURDAY) || ((this.weekEndWake != null) && (this.weekEndWake.compareTo(this.weekSleep) > 0))) {
							theSwitches.add(new SleepSwitch(this.weekSleep, theDay + 1, SleepAction.SLEEP));
						}
						// else: rien le week-end + couche-tard: on ne va pas se
						// coucher dans la nuit
						// du vendredi à samedi.
					} else {
						// wake <= sleep -> cas normal.
						theSwitches.add(new SleepSwitch(this.weekSleep, theDay, SleepAction.SLEEP));
					}
				}

				for (int theDay = Calendar.MONDAY; theDay <= Calendar.FRIDAY; theDay++) {
					theSwitches.add(new SleepSwitch(this.weekWake, theDay, SleepAction.WAKE));
				}
			}
		} else if (this.weekEndWake != null) {
			// Rien la semaine, debout du lundi minuit à vendredi 23:59.
			if (this.weekEndWake.compareTo(this.weekEndSleep) <= 0) {
				theSwitches.add(new SleepSwitch(SleepTime.MIDNIGHT, Calendar.MONDAY, SleepAction.WAKE));
			}
			theSwitches.add(new SleepSwitch(SleepTime.BEFORE_MIDNIGHT, Calendar.FRIDAY, SleepAction.SLEEP));
		}

		if (this.weekEndWake != null) {
			if (this.weekEndWake.equals(this.weekEndSleep)) { // même heure dans le week end, veille toute le week end
				if (this.weekWake != null) {
					if (!this.weekWake.equals(this.weekSleep)) {
						theSwitches.add(new SleepSwitch(this.weekSleep, Calendar.SATURDAY, SleepAction.SLEEP));
						theSwitches.add(new SleepSwitch(SleepTime.MIDNIGHT, Calendar.SUNDAY, SleepAction.SLEEP));
						theSwitches.add(new SleepSwitch(this.weekWake, Calendar.MONDAY, SleepAction.WAKE));
					} // else : même heure dans la semaine, veille toute la semaine
				} else {
					theSwitches.add(new SleepSwitch(SleepTime.MIDNIGHT, Calendar.SATURDAY, SleepAction.SLEEP));
					theSwitches.add(new SleepSwitch(this.weekEndWake, Calendar.MONDAY, SleepAction.WAKE));
				}
			} else {
				if (this.weekEndWake.compareTo(this.weekEndSleep) > 0) {
					// wake >= sleep -> cas sieste/couche-tard
					theSwitches.add(new SleepSwitch(this.weekEndSleep, Calendar.SUNDAY, SleepAction.SLEEP));
					if ((this.weekWake != null) && (this.weekWake.compareTo(this.weekEndSleep) > 0)) {
						theSwitches.add(new SleepSwitch(this.weekEndSleep, Calendar.MONDAY, SleepAction.SLEEP));
					}
				} else {
					// wake <= sleep -> cas normal.
					theSwitches.add(new SleepSwitch(this.weekEndSleep, Calendar.SATURDAY, SleepAction.SLEEP));
					theSwitches.add(new SleepSwitch(this.weekEndSleep, Calendar.SUNDAY, SleepAction.SLEEP));
				}

				theSwitches.add(new SleepSwitch(this.weekEndWake, Calendar.SATURDAY, SleepAction.WAKE));
				theSwitches.add(new SleepSwitch(this.weekEndWake, Calendar.SUNDAY, SleepAction.WAKE));
			}

		} else if (this.weekWake != null) {
			// Rien la semaine, debout du samedi minuit à dimanche 23:59.
			if (this.weekWake.compareTo(this.weekSleep) <= 0) {
				theSwitches.add(new SleepSwitch(SleepTime.MIDNIGHT, Calendar.SATURDAY, SleepAction.WAKE));
			}
			theSwitches.add(new SleepSwitch(SleepTime.BEFORE_MIDNIGHT, Calendar.SUNDAY, SleepAction.SLEEP));
		}

		Collections.sort(theSwitches);

		return theSwitches;
	}

	public List<SleepRun> getSleepRuns() {
		final List<SleepRun> theResult = new LinkedList<SleepRun>();
		final List<SleepSwitch> theSwitches = getSleepSwitches();

		if (!theSwitches.isEmpty()) {
			// Etat initial -> inverse du premier switch (du dimanche matin,
			// sans doute).
			SleepAction theCurrentState;
			int theCurrentDay = Calendar.SUNDAY;
			Time theCurrentTime = SleepTime.MIDNIGHT;
			if (theSwitches.get(0).getAction() == SleepAction.SLEEP) {
				theCurrentState = SleepAction.WAKE;
			} else {
				theCurrentState = SleepAction.SLEEP;
			}

			for (final SleepSwitch theSwitch : theSwitches) {
				final int theSwitchDay = theSwitch.getDay();
				final Time theSwitchTime = theSwitch.getTime();
				final SleepAction theSwitchState = theSwitch.getAction();
				while (theSwitchDay > theCurrentDay) {
					// Ce n'est pas le même jour, on remplit la journée.
					if (theCurrentTime != SleepTime.BEFORE_MIDNIGHT) {
						theResult.add(new SleepRun(theCurrentTime, SleepTime.BEFORE_MIDNIGHT, theCurrentDay, theCurrentState));
					}
					theCurrentDay++;
					theCurrentTime = SleepTime.MIDNIGHT;
				}

				if (theCurrentState != theSwitchState) {
					// Changement d'état.
					if (theCurrentTime != theSwitchTime) {
						if (!(SleepTime.MIDNIGHT.equals(theCurrentTime) && SleepTime.MIDNIGHT.equals(theSwitchTime))) {
							theResult.add(new SleepRun(theCurrentTime, theSwitchTime, theCurrentDay, theCurrentState));
						}
						theCurrentTime = theSwitchTime;
					}
					theCurrentState = theSwitchState;
				}
			}

			// On remplit les derniers jours.
			while (theCurrentDay <= Calendar.SATURDAY) {
				if (theCurrentTime != SleepTime.BEFORE_MIDNIGHT) {
					theResult.add(new SleepRun(theCurrentTime, SleepTime.BEFORE_MIDNIGHT, theCurrentDay, theCurrentState));
				}
				theCurrentDay++;
				theCurrentTime = SleepTime.MIDNIGHT;
			}
		}

		return theResult;
	}
}
