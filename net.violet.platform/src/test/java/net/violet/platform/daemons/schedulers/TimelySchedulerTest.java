package net.violet.platform.daemons.schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import net.violet.db.records.Record;
import net.violet.platform.daemons.AbstractDaemon;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * The abstract class for Schedulers.
 */
public class TimelySchedulerTest extends MockTestBase {

	private static long getTimeOfDelivery(int inNbTimeFrame, int inSelectedTimeFrameIndex) {
		int theWindow = AbstractDaemon.DEFAULT_DELAY * (inNbTimeFrame - 1) / 1000;

		if ((theWindow % 2) == 0) {
			theWindow--;
		}

		return (theWindow / inSelectedTimeFrameIndex) * 1000;
	}

	@Test
	public void walkByTimeZone() {
		Assert.assertFalse(Factories.TIMEZONE.findAll().isEmpty());
		final AtomicInteger amountOfDiffTimeZone = new AtomicInteger(0);
		final CCalendar theStartTime = new CCalendar(System.currentTimeMillis());
		final DailyScheduler theScheduler = new DailyScheduler(new String[] { "-p", "10" }) {

			@Override
			protected void send2GatheringSchedulerExecutor(TimezoneProcessUnit inTimeZoneUnit) {
				final Timezone theTimezone = inTimeZoneUnit.get();
				amountOfDiffTimeZone.incrementAndGet();
				final CCalendar localTime = (CCalendar) theStartTime.clone();
				localTime.setTimeZone(theTimezone.getJavaTimeZone());
				Assert.assertEquals(localTime, inTimeZoneUnit.getProcessConditioner());
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());
		Assert.assertEquals(39, amountOfDiffTimeZone.get());

	}

	@Test
	public void deliveryDateTest() {
		final VObject theObject = getKowalskyObject();
		final CCalendar theStartTime = new CCalendar("10:00:00", theObject.getTimeZone().getJavaTimeZone());
		final String theRequestedTime = "10:12:00";

		final Subscription theSubscription = Factories.SUBSCRIPTION.create(Factories.APPLICATION.findByName("net.violet.weather"), theObject);
		final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.DailyWithDuration);
		for (final DailyHandler.Weekday aDay : DailyHandler.Weekday.values()) {
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, aDay.getValue(), theRequestedTime);
		}

		final AtomicBoolean expectedObject = new AtomicBoolean(true);
		final AtomicBoolean expectedDate = new AtomicBoolean(true);
		final AtomicInteger amount = new AtomicInteger(0);

		final DailyWithDuration theScheduler = new DailyWithDuration(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();
				if (!theObject.equals(object)) {
					expectedObject.set(false);
				}
				amount.incrementAndGet();
				final CCalendar deliveryDate = inMessageUnit.getProcessConditioner();
				if (!deliveryDate.getTimeFormated(true).equals(theRequestedTime)) {
					expectedDate.set(false);
				}
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(1, amount.get());
		Assert.assertTrue(expectedDate.get());
		Assert.assertTrue(expectedObject.get());

	}

	@Test
	public void deliveryDateOverTwoDaysTest() {
		final VObject theObject = getKowalskyObject();
		final VObject theSecondObject = getPrivateObject();
		final CCalendar theStartTime = new CCalendar("23:50:00", theObject.getTimeZone().getJavaTimeZone());
		final String theRequestedTime = "23:55:00";
		final String theOtherRequestedTime = "00:05:00";

		final Subscription theSubscription = Factories.SUBSCRIPTION.create(Factories.APPLICATION.findByName("net.violet.weather"), theObject);
		final Subscription theOtherSubscription = Factories.SUBSCRIPTION.create(Factories.APPLICATION.findByName("net.violet.weather"), theSecondObject);
		final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.DailyWithDuration);
		final SubscriptionScheduling theOtherScheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theOtherSubscription, SchedulingType.SCHEDULING_TYPE.DailyWithDuration);
		for (final DailyHandler.Weekday aDay : DailyHandler.Weekday.values()) {
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, aDay.getValue(), theRequestedTime);
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theOtherScheduling, aDay.getValue(), theOtherRequestedTime);
		}

		final AtomicBoolean expectedObject = new AtomicBoolean(true);
		final AtomicBoolean expectedDate = new AtomicBoolean(true);
		final AtomicInteger amount = new AtomicInteger(0);

		final DailyWithDuration theScheduler = new DailyWithDuration(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();
				final CCalendar deliveryDate = inMessageUnit.getProcessConditioner();
				if (theObject.equals(object)) {
					if (!deliveryDate.getTimeFormated(true).equals(theRequestedTime)) {
						expectedDate.set(false);
					}
				} else if (theSecondObject.equals(object)) {
					if (!deliveryDate.getTimeFormated(true).equals(theOtherRequestedTime)) {
						expectedDate.set(false);
					}

				} else {
					expectedObject.set(false);
				}

				amount.incrementAndGet();
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(2, amount.get());
		Assert.assertTrue(expectedDate.get());
		Assert.assertTrue(expectedObject.get());

	}

	@Test
	public void oneOnEachBound() {
		final VObject theObject = getKowalskyObject();
		final VObject theSecondObject = getPrivateObject();
		final CCalendar theStartTime = new CCalendar("10:00:00", theObject.getTimeZone().getJavaTimeZone());
		final String theRequestedTime = "10:00:00";
		final String theOtherRequestedTime = "10:30:00";

		final Subscription theSubscription = Factories.SUBSCRIPTION.create(Factories.APPLICATION.findByName("net.violet.weather"), theObject);
		final Subscription theOtherSubscription = Factories.SUBSCRIPTION.create(Factories.APPLICATION.findByName("net.violet.weather"), theSecondObject);
		final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.DailyWithMedia);
		final SubscriptionScheduling theOtherScheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theOtherSubscription, SchedulingType.SCHEDULING_TYPE.DailyWithMedia);
		for (final DailyHandler.Weekday aDay : DailyHandler.Weekday.values()) {
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, aDay.getValue(), theRequestedTime);
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theOtherScheduling, aDay.getValue(), theOtherRequestedTime);
		}

		final AtomicBoolean expectedObject = new AtomicBoolean(true);
		final AtomicBoolean expectedDate = new AtomicBoolean(true);
		final AtomicInteger amount = new AtomicInteger(0);

		final DailyWithMedia theScheduler = new DailyWithMedia(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();
				final CCalendar deliveryDate = inMessageUnit.getProcessConditioner();
				if (theObject.equals(object)) {
					if (!deliveryDate.getTimeFormated(true).equals(theRequestedTime)) {
						expectedDate.set(false);
					}
				} else {
					expectedObject.set(false);
				}

				amount.incrementAndGet();
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(1, amount.get());
		Assert.assertTrue(expectedObject.get());
		Assert.assertTrue(expectedDate.get());

	}

	@Test
	public void oneOutOfThreeOverWindow() {

		final List<VObject> theObjects = Arrays.asList(new VObject[] { getKowalskyObject(), getPrivateObject(), getBrewsterObject() });
		final CCalendar theStartTime = new CCalendar("07:37:15", TimeZone.getTimeZone("UTC"));
		final AtomicInteger amountOfObject2Check = new AtomicInteger(theObjects.size());

		for (int i = 0; i < theObjects.size();) {
			final VObject theObject = theObjects.get(i++);
			final CCalendar theCalendar = (CCalendar) theStartTime.clone();
			theCalendar.setTimeZone(theObject.getTimeZone().getJavaTimeZone());
			theCalendar.addMillis(TimelySchedulerTest.getTimeOfDelivery(theObjects.size(), i));

			final Subscription theSubscription = new SubscriptionMock(i, Factories.APPLICATION.findByName("net.violet.weather"), theObject);
			final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.Daily);

			for (final DailyHandler.Weekday aDay : DailyHandler.Weekday.values()) {
				Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, aDay.getValue(), theCalendar.getTimeFormated(true));
			}
		}

		final DailyScheduler theScheduler = new DailyScheduler(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();
				Assert.assertTrue(getPrivateObject().equals(object) || getBrewsterObject().equals(object));
				amountOfObject2Check.decrementAndGet();
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(1, amountOfObject2Check.get());
	}

	@Test
	public void allSleepyButOne() {

		final List<VObject> theObjects = Arrays.asList(new VObject[] { getKowalskyObject(), getPrivateObject(), getBrewsterObject() });
		final CCalendar theStartTime = new CCalendar(System.currentTimeMillis());
		final AtomicInteger amountOfObject2Check = new AtomicInteger(theObjects.size());

		for (int i = 0; i < theObjects.size();) {
			final VObject theObject = theObjects.get(i++);
			theObject.setMode(VObject.STATUS_VEILLE);
			final CCalendar theCalendar = (CCalendar) theStartTime.clone();
			theCalendar.setTimeZone(theObject.getTimeZone().getJavaTimeZone());
			theCalendar.addMillis(TimelySchedulerTest.getTimeOfDelivery(theObjects.size(), i));

			final Subscription theSubscription = new SubscriptionMock(i, Factories.APPLICATION.findByName("net.violet.weather"), theObject);
			final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.Daily);

			for (final DailyHandler.Weekday aDay : DailyHandler.Weekday.values()) {
				Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, aDay.getValue(), theCalendar.getTimeFormated(true));
			}
		}

		getBrewsterObject().setMode(VObject.MODE_PING);

		final DailyScheduler theScheduler = new DailyScheduler(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();
				Assert.assertEquals(getBrewsterObject(), object);
				amountOfObject2Check.decrementAndGet();
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(2, amountOfObject2Check.get());

	}

	@Test
	public void allDispatchedOnTheEarth() {

		final List<VObject> theObjects = Arrays.asList(new VObject[] { getKowalskyObject(), getPrivateObject(), getBrewsterObject() });
		final Timezone[] theTimezones = { Factories.TIMEZONE.findByJavaId("Europe/Paris"), Factories.TIMEZONE.findByJavaId("America/Chicago"), Factories.TIMEZONE.findByJavaId("Asia/Tokyo") };

		final CCalendar theStartTime = new CCalendar(System.currentTimeMillis());
		final AtomicInteger amountOfObject2Check = new AtomicInteger(theTimezones.length);

		for (int i = 0; i < theObjects.size();) {
			final VObject theObject = theObjects.get(i);
			theObject.setTimeZone(theTimezones[i++]);

			Factories.TIMEZONE.walkDistincts(new Record.RecordWalker<Timezone>() {

				public void process(Timezone inTimeZone) {
					final Timezone theTimezone = theObject.getTimeZone();

					if (Factories.TIMEZONE.findAllFriends(inTimeZone).contains(theTimezone)) {
						amountOfObject2Check.decrementAndGet();
					}
				}
			});

			final CCalendar theCalendar = (CCalendar) theStartTime.clone();
			theCalendar.setTimeZone(theObject.getTimeZone().getJavaTimeZone());
			theCalendar.addMillis(TimelySchedulerTest.getTimeOfDelivery(theObjects.size(), i));

			final Subscription theSubscription = new SubscriptionMock(i, Factories.APPLICATION.findByName("net.violet.weather"), theObject);
			final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.Daily);

			for (final DailyHandler.Weekday aDay : DailyHandler.Weekday.values()) {
				Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, aDay.getValue(), theCalendar.getTimeFormated(true));
			}

		}

		Assert.assertEquals(0, amountOfObject2Check.get());

		amountOfObject2Check.set(theObjects.size());
		final DailyScheduler theScheduler = new DailyScheduler(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();
				Assert.assertTrue(getPrivateObject().equals(object) || getBrewsterObject().equals(object));
				amountOfObject2Check.decrementAndGet();
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(1, amountOfObject2Check.get());

	}
}
