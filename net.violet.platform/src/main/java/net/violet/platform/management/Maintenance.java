/**
 * 
 */
package net.violet.platform.management;

import java.util.Timer;
import java.util.TimerTask;

import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

/**
 * Class holding all the informations necessary to get the platform in
 * "Maintenance mode"
 */
public final class Maintenance {


	private static final Logger LOGGER = Logger.getLogger(Maintenance.class);

	private static enum STATE {
		ON,
		OFF
	};

	private static final String[] DEFAULT_MESSAGE = new String[] { "Our servers will be down for maintenance on", "for approximatively ", " hours." };
	private static final String HYPHEN = "-";
	private static final Timer maintenanceTimer = new Timer("Maintenance timer");
	private static final Maintenance mInstance = new Maintenance();
	private TimerTask startTask;
	private TimerTask endTask;
	private CCalendar startDate;
	private CCalendar endDate;
	private String mMessage;
	private STATE display;

	public static Maintenance getInstance() {
		return Maintenance.mInstance;
	}

	private Maintenance() {
		disActivate();
	}

	public String getDisplay() {
		return this.display.toString();
	}

	public void setupMaintenance(final String[] inMessage, final CCalendar inStart, final CCalendar inEnd, boolean displayPeriod) {
		final CCalendar now = new CCalendar(false);
		final String theMessage;

		final CCalendar theStartDate = (inStart == null) ? ((this.startDate == null) ? now : this.startDate) : inStart;

		if ((inMessage == null) || ((inMessage.length < 1) && (inMessage.length > 3))) {
			theMessage = Maintenance.generateMessage(Maintenance.DEFAULT_MESSAGE, theStartDate, inEnd, displayPeriod);
		} else {
			theMessage = Maintenance.generateMessage(inMessage, theStartDate, inEnd, displayPeriod);

		}

		if ((inStart != null) && inStart.after(now) && (this.display == STATE.OFF)) {

			if (this.startTask != null) {
				this.startTask.cancel();
			}

			this.startTask = new TimerTask() {

				@Override
				public void run() {
					activate(theMessage, theStartDate, inEnd);
				}

			};

			Maintenance.maintenanceTimer.schedule(this.startTask, theStartDate.getTime());

		} else if (this.display == STATE.OFF) {
			activate(theMessage, theStartDate, inEnd);
		} else if (this.display == STATE.ON) {
			refreshMessage(theMessage);
		}

		if ((inEnd != null) && inEnd.after(now)) {
			if (this.endTask != null) {
				this.endTask.cancel();
			}

			this.endTask = new TimerTask() {

				@Override
				public void run() {
					disActivate();
				}

			};

			Maintenance.maintenanceTimer.schedule(this.endTask, inEnd.getTime());
		} else if (this.display == STATE.ON) {
			disActivate();
		}
	}

	private static String generateMessage(String[] inMessage, CCalendar inStartDate, CCalendar inEndDate, boolean displayPeriod) {
		final int theMessagesLength = inMessage.length;
		final StringBuilder theMessage = new StringBuilder(inMessage[0]);
		theMessage.append(StringShop.SPACE);
		theMessage.append(inStartDate.getMaintenanceDateFormated(false));

		theMessage.append(StringShop.SPACE);
		if (displayPeriod && (theMessagesLength > 2)) {
			if (theMessagesLength > 0) {
				theMessage.append(inMessage[1]);
				theMessage.append(StringShop.SPACE);
			}
			final int thePeriodMS = (int) (inEndDate.getTimeInMillis() - inStartDate.getTimeInMillis());
			final CCalendar thePeriod = new CCalendar(true);
			thePeriod.setMillisecond(thePeriodMS);

			theMessage.append(thePeriod.getTimeFormated(true));
			theMessage.append(StringShop.SPACE);
			theMessage.append(inMessage[2]);
		} else if (theMessagesLength > 3) {
			theMessage.append(inMessage[2]);
			theMessage.append(inEndDate.getMaintenanceDateFormated(false));
		} else {
			theMessage.append(StringShop.SPACE);
			theMessage.append(Maintenance.HYPHEN);
			theMessage.append(inEndDate.getMaintenanceDateFormated(false));
		}
		theMessage.append(StringShop.SPACE);

		for (int i = 3; i < theMessagesLength; i++) {
			theMessage.append(inMessage[i]);
			if (i < (theMessagesLength - 1)) {
				theMessage.append(StringShop.SPACE);
			}
		}

		return theMessage.toString();
	}

	private void refreshMessage(final String inMessage) {
		this.mMessage = inMessage;
	}

	private void activate(final String inMessage, CCalendar inStart, CCalendar inEnd) {
		Maintenance.LOGGER.debug("Activating maintenance mode");
		this.display = STATE.ON;
		this.startDate = inStart;
		this.endDate = inEnd;
		refreshMessage(inMessage);
	}

	private void disActivate() {
		Maintenance.LOGGER.debug("Disactivating maintenance mode");
		this.startDate = null;
		this.endDate = null;
		this.display = STATE.OFF;
		this.mMessage = null;
		this.startTask = null;
		this.endTask = null;
		Maintenance.maintenanceTimer.purge();
	}

	/**
	 * @return the startMaintenance
	 */
	public CCalendar getStartMaintenance() {
		return this.startDate;
	}

	/**
	 * @return the endMaintenance
	 */
	public CCalendar getEndMaintenance() {
		return this.endDate;
	}

	/**
	 * Returns a String corresponding to this date formated.
	 * 
	 * @param isJS for the JS is true, for the message otherwise
	 * @return
	 */
	public String getEndMaintenance(boolean isJs) {
		return this.endDate.getMaintenanceDateFormated(isJs);
	}

	/**
	 * @return the mMessage
	 */
	public String getMessage() {
		return this.mMessage;
	}
}
