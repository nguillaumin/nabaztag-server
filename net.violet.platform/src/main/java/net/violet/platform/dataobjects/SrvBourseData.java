package net.violet.platform.dataobjects;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.violet.platform.applications.TradeFullHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.SubscriptionSchedulingData.SchedulingAtomData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.util.CCalendar;

import org.apache.log4j.Logger;

public class SrvBourseData {

	private static final Logger LOGGER = Logger.getLogger(SrvBourseData.class);

	private boolean is24;

	private long id = 0;
	private String key = net.violet.common.StringShop.EMPTY_STRING;
	private String source = net.violet.common.StringShop.EMPTY_STRING;
	private final List<CCalendar> time = new ArrayList<CCalendar>();
	private long weekend = 0;
	private String name = net.violet.common.StringShop.EMPTY_STRING;
	private int passive = 0;

	public SrvBourseData(User inUser) {
		this.is24 = inUser.use24();
	}

	public static SrvBourseData getSrvBourseDataFromSubscription(SubscriptionData theSubscription) {
		final User theUser = theSubscription.getObject().getReference().getOwner();
		final SrvBourseData srvBourse = new SrvBourseData(theUser);
		final long id = theSubscription.getId();

		final Map<String, Object> settings = theSubscription.getSettings();

		srvBourse.name = settings.containsKey(TradeFullHandler.ALERT_NAME) ? settings.get(TradeFullHandler.ALERT_NAME).toString() : net.violet.common.StringShop.EMPTY_STRING;
		srvBourse.source = settings.containsKey(TradeFullHandler.SOURCE) ? settings.get(TradeFullHandler.SOURCE).toString() : net.violet.common.StringShop.EMPTY_STRING;
		srvBourse.id = id;

		srvBourse.is24 = theUser.use24();
		boolean firstFlash = true;
		for (final SubscriptionSchedulingData scheduling : SubscriptionSchedulingData.findAllBySubscription(theSubscription)) {
			if (SchedulingType.SCHEDULING_TYPE.Ambiant == scheduling.getType()) {
				srvBourse.passive = 1;
			}
			if (SchedulingType.SCHEDULING_TYPE.Daily == scheduling.getType()) {

				final SchedulingAtomData atom = scheduling.getSchedulingAtom(DailyHandler.Weekday.MONDAY, theUser.getTimezone().getJavaTimeZone());
				if (firstFlash) {
					srvBourse.key = "FLASH_BOURSEFULL_ONE";
					firstFlash = false;
				} else {
					srvBourse.key = "FLASH_BOURSEFULL_TWO";
				}

				final CCalendar calendar = new CCalendar(true);
				calendar.setTime(CCalendar.getSQLTime(atom.getTimeH(), atom.getTimeM()));
				srvBourse.time.add(calendar);

				final SchedulingAtomData weekEndAtom = scheduling.getSchedulingAtom(DailyHandler.Weekday.SATURDAY, theUser.getTimezone().getJavaTimeZone());
				if (weekEndAtom == null) {
					srvBourse.weekend = 1;
				}

			}
		}
		return srvBourse;
	}

	public static List<SrvBourseData> getSupervisedInfoBourse(VObject inObject) {
		final List<SrvBourseData> listBData = new ArrayList<SrvBourseData>();

		final ApplicationData bourse = ApplicationData.getData(Application.NativeApplication.BOURSE_FULL.getApplication());

		final List<SubscriptionData> subscriptions = SubscriptionData.findByApplicationAndObject(bourse.getReference(), inObject);

		for (final SubscriptionData theSubscription : subscriptions) {
			final long id = theSubscription.getId();

			final User theUser = inObject.getOwner();
			final SrvBourseData srvBourse = new SrvBourseData(theUser);

			final Map<String, Object> settings = theSubscription.getSettings();

			srvBourse.name = settings.containsKey(TradeFullHandler.ALERT_NAME) ? settings.get(TradeFullHandler.ALERT_NAME).toString() : net.violet.common.StringShop.EMPTY_STRING;
			srvBourse.source = settings.containsKey(TradeFullHandler.SOURCE) ? settings.get(TradeFullHandler.SOURCE).toString() : net.violet.common.StringShop.EMPTY_STRING;
			srvBourse.id = id;

			boolean firstFlash = true;
			for (final SubscriptionSchedulingData scheduling : SubscriptionSchedulingData.findAllBySubscription(theSubscription)) {
				if (SchedulingType.SCHEDULING_TYPE.Ambiant == scheduling.getType()) {
					srvBourse.passive = 1;
				}
				if (SchedulingType.SCHEDULING_TYPE.Daily == scheduling.getType()) {

					final SchedulingAtomData atom = scheduling.getSchedulingAtom(DailyHandler.Weekday.MONDAY, theUser.getTimezone().getJavaTimeZone());
					if (firstFlash) {
						srvBourse.key = "FLASH_BOURSEFULL_ONE";
						firstFlash = false;
					} else {
						srvBourse.key = "FLASH_BOURSEFULL_TWO";
					}

					final CCalendar calendar = new CCalendar(true);
					calendar.setTime(CCalendar.getSQLTime(atom.getTimeH(), atom.getTimeM()));
					srvBourse.time.add(calendar);

					final SchedulingAtomData weekEndAtom = scheduling.getSchedulingAtom(DailyHandler.Weekday.SATURDAY, theUser.getTimezone().getJavaTimeZone());
					if (weekEndAtom == null) {
						srvBourse.weekend = 1;
					}
				}
			}

			listBData.add(srvBourse);
		}

		return listBData;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return the attribute srvBourse_key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * @return the attribute srvBourse_nexttime
	 */
	public long getNexttime() {
		return 0;
	}

	/**
	 * @return the attribute srvBourse_code
	 */
	public String getSource() {
		return this.source;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.name;
	}

	/**
	 * @return the attribute srvBourse_time
	 */
	public Time getTime() {

		return new Time(Calendar.getInstance().getTimeInMillis());
	}

	/**
	 * @return the attribute srvBourse_time 1
	 */
	public String getTime1() {
		if (this.time.size() > 0) {
			return this.time.get(0).getTimeFormatedShort(this.is24);
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute srvBourse_time 2
	 */
	public String getTime2() {
		if (this.time.size() > 1) {
			return this.time.get(1).getTimeFormatedShort(this.is24);
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return how many times are set
	 */
	public int getTimeListSize() {
		return this.time.size();
	}

	/**
	 * @return the attribute srvBourse_weekend
	 */
	public long getWeekend() {
		return this.weekend;
	}

	/**
	 * @return the attribute now (always 0)
	 */
	public int getNow() {
		return 0;
	}

	/**
	 * @return the nbr attribute
	 */
	public int getNbr() {
		return this.time.size();
	}

	public int getSrv_passive() {
		return this.passive;
	}

	public String getSrv_name() {
		if (!this.name.equals(net.violet.common.StringShop.EMPTY_STRING) && (this.name.lastIndexOf("$") < this.name.length())) {
			return this.name.substring(0, this.name.lastIndexOf("$"));
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getSrv_src() {
		return this.source;
	}

	public String getSrv_val_name() {
		SrvBourseData.LOGGER.debug(this.name + " lastIndexof $ " + this.name.lastIndexOf("$") + " length = " + this.name.length());
		if (!this.name.equals(net.violet.common.StringShop.EMPTY_STRING) && (this.name.lastIndexOf("$") + 1 < this.name.length())) {
			return this.name.substring(this.name.lastIndexOf("$") + 1, this.name.length());
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public long getSrv_id() {
		return this.id;
	}

	public int getSrv_lumiere() {
		return getSrv_passive();
	}
}
