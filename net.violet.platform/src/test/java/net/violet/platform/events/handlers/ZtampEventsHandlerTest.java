package net.violet.platform.events.handlers;

import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.datamodel.mock.SubscriptionSchedulingMock;
import net.violet.platform.datamodel.mock.SubscriptionSchedulingSettingsMock;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class ZtampEventsHandlerTest extends MockTestBase {

	@Test
	public void isInActivePeriodNoPeriodTest() {
		final Subscription theSubscription = new SubscriptionMock(0, getMyFirstApplication(), getKowalskyObject());
		final SubscriptionScheduling scheduling = new SubscriptionSchedulingMock(0, theSubscription, SCHEDULING_TYPE.InteractionTrigger);
		final SubscriptionSchedulingSettings aSetting = new SubscriptionSchedulingSettingsMock(0, scheduling, "event", "ztampdetection");

		final Map<String, SubscriptionSchedulingSettingsData> theSettings = Collections.singletonMap("event", SubscriptionSchedulingSettingsData.getData(aSetting));
		Assert.assertTrue(ZtampEventsHandler.isInActivePeriod(theSettings));

	}

	@Test
	public void isInActivePeriodWrongDayTest() {
		final Subscription theSubscription = new SubscriptionMock(0, getMyFirstApplication(), getKowalskyObject());
		final SubscriptionScheduling scheduling = new SubscriptionSchedulingMock(0, theSubscription, SCHEDULING_TYPE.InteractionTrigger);

		final CCalendar now = new CCalendar(getKowalskyObject().getTimeZone().getJavaTimeZone());
		now.add(Calendar.DAY_OF_WEEK, 1);
		final String key = DailyHandler.Weekday.findByCalendarId(now.get(Calendar.DAY_OF_WEEK)).getValue();

		final SubscriptionSchedulingSettings aSetting = new SubscriptionSchedulingSettingsMock(0, scheduling, key, "10:30-11:55");

		final Map<String, SubscriptionSchedulingSettingsData> theSettings = Collections.singletonMap(key, SubscriptionSchedulingSettingsData.getData(aSetting));
		Assert.assertFalse(ZtampEventsHandler.isInActivePeriod(theSettings));
	}

	@Test
	public void isInActivePeriodWrongHourTest() {
		final Subscription theSubscription = new SubscriptionMock(0, getMyFirstApplication(), getKowalskyObject());
		final SubscriptionScheduling scheduling = new SubscriptionSchedulingMock(0, theSubscription, SCHEDULING_TYPE.InteractionTrigger);

		final CCalendar now = new CCalendar(false, getKowalskyObject().getTimeZone().getJavaTimeZone());
		now.add(Calendar.HOUR_OF_DAY, 1);
		final String key = DailyHandler.Weekday.findByCalendarId(now.get(Calendar.DAY_OF_WEEK)).getValue();

		now.add(Calendar.MINUTE, -10);
		final String from = now.getHour() + ":" + now.getMinute();
		now.add(Calendar.MINUTE, 20);
		final String to = now.getHour() + ":" + now.getMinute();

		final SubscriptionSchedulingSettings aSetting = new SubscriptionSchedulingSettingsMock(0, scheduling, key, from + "-" + to);

		final Map<String, SubscriptionSchedulingSettingsData> theSettings = Collections.singletonMap(key, SubscriptionSchedulingSettingsData.getData(aSetting));
		Assert.assertFalse(ZtampEventsHandler.isInActivePeriod(theSettings));
	}

	@Test
	public void isInActivePeriodTest() {
		final Subscription theSubscription = new SubscriptionMock(0, getMyFirstApplication(), getKowalskyObject());
		final SubscriptionScheduling scheduling = new SubscriptionSchedulingMock(0, theSubscription, SCHEDULING_TYPE.InteractionTrigger);

		final CCalendar now = new CCalendar(getKowalskyObject().getTimeZone().getJavaTimeZone());
		final String key = DailyHandler.Weekday.findByCalendarId(now.get(Calendar.DAY_OF_WEEK)).getValue();
		now.add(Calendar.MINUTE, -5);
		String value = now.getHour() + ":" + now.getMinute() + "-";
		now.add(Calendar.MINUTE, 10);
		value += now.getTimeFormatedShort(true);

		final SubscriptionSchedulingSettings aSetting = new SubscriptionSchedulingSettingsMock(0, scheduling, key, value);

		final Map<String, SubscriptionSchedulingSettingsData> theSettings = Collections.singletonMap(key, SubscriptionSchedulingSettingsData.getData(aSetting));
		Assert.assertTrue(ZtampEventsHandler.isInActivePeriod(theSettings));
	}
}
