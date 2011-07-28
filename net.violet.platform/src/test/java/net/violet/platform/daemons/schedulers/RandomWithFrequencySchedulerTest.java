package net.violet.platform.daemons.schedulers;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * The abstract class for Schedulers.
 */
public class RandomWithFrequencySchedulerTest extends MockTestBase {

	@Test
	public void walkOnSubscriptionsTest() {
		final List<VObject> theObjects = Arrays.asList(new VObject[] { getKowalskyObject(), getPrivateObject(), getBrewsterObject() });
		final FrequencyHandler.Frequency[] theFrequencies = { FrequencyHandler.Frequency.SOMETIMES, FrequencyHandler.Frequency.OFTEN, FrequencyHandler.Frequency.RARELY };
		final AtomicInteger amountOfObject2Check = new AtomicInteger();

		for (int i = 0; i < theObjects.size(); i++) {
			final VObject theObject = theObjects.get(i);

			final Subscription theSubscription = new SubscriptionMock(i, Factories.APPLICATION.findByName("net.violet.rss.actustar"), theObject);
			final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency);

			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, FrequencyHandler.FREQUENCY, theFrequencies[i].getLabel());
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, FrequencyHandler.LAST_TIME, "2008-09-02 10:15:00");
		}
		final TimeZone timeZoneId = getParisTimezone().getJavaTimeZone();
		final CCalendar start = CCalendar.parseTimestamp("2008-09-02 12:00:00", timeZoneId.getID());
		final CCalendar end = CCalendar.parseTimestamp("2008-09-02 12:30:00", timeZoneId.getID());

		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.NewContentWithFrequency, FrequencyHandler.Frequency.SOMETIMES, getParisTimezone(), start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inObject1, SubscriptionScheduling inObject2) {
				amountOfObject2Check.incrementAndGet();
			}
		});

		Assert.assertEquals(1, amountOfObject2Check.get());

		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.NewContentWithFrequency, FrequencyHandler.Frequency.OFTEN, getParisTimezone(), start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inObject1, SubscriptionScheduling inObject2) {
				amountOfObject2Check.incrementAndGet();
			}
		});

		Assert.assertEquals(2, amountOfObject2Check.get());

		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.NewContentWithFrequency, FrequencyHandler.Frequency.VERY_OFTEN, getParisTimezone(), start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inObject1, SubscriptionScheduling inObject2) {
				amountOfObject2Check.incrementAndGet();
			}
		});

		Assert.assertEquals(2, amountOfObject2Check.get());

	}

	@Test
	public void oneOutOfThreeOverWindow() {

		final List<VObject> theObjects = Arrays.asList(new VObject[] { getKowalskyObject(), getPrivateObject(), getBrewsterObject() });
		final FrequencyHandler.Frequency[] theFrequencies = { FrequencyHandler.Frequency.SOMETIMES, FrequencyHandler.Frequency.OFTEN, FrequencyHandler.Frequency.VERY_OFTEN };
		final AtomicInteger amountOfObject2Check = new AtomicInteger();

		final CCalendar startTime = new CCalendar((System.currentTimeMillis() / 1000) * 1000, getParisTimezone().getJavaTimeZone());
		final CCalendar subscriptionTime = (CCalendar) startTime.clone();
		subscriptionTime.add(java.util.Calendar.DAY_OF_YEAR, -10);

		for (int i = 0; i < theObjects.size(); i++) {
			final VObject theObject = theObjects.get(i);

			final Subscription theSubscription = new SubscriptionMock(i, Factories.APPLICATION.findByName("net.violet.rss.actustar"), theObject);
			final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency);

			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, FrequencyHandler.FREQUENCY, theFrequencies[i].getLabel());

			final CCalendar theCalendar;

			if (i != 0) {
				theCalendar = new CCalendar((subscriptionTime.getTimeInMillis() - theFrequencies[i].getTimeInMillis() / i), getParisTimezone().getJavaTimeZone());
			} else {
				theCalendar = new CCalendar(subscriptionTime.getTimeInMillis() - 60000, getParisTimezone().getJavaTimeZone());
			}

			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, FrequencyHandler.LAST_TIME, theCalendar.getTimestamp());

		}

		final NewContentWithFrequencyScheduler theScheduler = new NewContentWithFrequencyScheduler(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				amountOfObject2Check.incrementAndGet();
				final VObject theObject = getPrivateObject();

				if (theObject.equals(inMessageUnit.get().getSubscription().getObject().getReference())) {
					Assert.assertNull(inMessageUnit.getProcessConditioner());
				} else if (getBrewsterObject().equals(inMessageUnit.get().getSubscription().getObject().getReference())) {
					inMessageUnit.runWhenSuccessful();
				}
			}
		};

		theScheduler.process(startTime.getTimeInMillis());

		Assert.assertEquals(2, amountOfObject2Check.get());

		final Subscription theSubscription = Factories.SUBSCRIPTION.find(2);
		final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(theSubscription, SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency).get(0);

		final SubscriptionSchedulingSettings setting = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findBySubscriptionSchedulingAndKey(scheduling, FrequencyHandler.LAST_TIME);

		final CCalendar theCalendar = new CCalendar((startTime.getTimeInMillis() + theFrequencies[2].getTimeInMillis() / 2), getParisTimezone().getJavaTimeZone());
		Assert.assertEquals(theCalendar.getTimestamp(), setting.getValue());

	}

	@Test
	public void allSleepyButOne() {
		final List<VObject> theObjects = Arrays.asList(new VObject[] { getKowalskyObject(), getPrivateObject(), getBrewsterObject() });
		final CCalendar theStartTime = new CCalendar((System.currentTimeMillis() / 1000) * 1000);
		final AtomicInteger amountOfObject2Check = new AtomicInteger();
		final CCalendar theCalendar = new CCalendar((theStartTime.getTimeInMillis() - FrequencyHandler.Frequency.VERY_OFTEN.getTimeInMillis()), TimeZone.getTimeZone("UTC"));

		for (int i = 0; i < theObjects.size();) {
			final VObject theObject = theObjects.get(i++);
			theObject.setMode(VObject.STATUS_VEILLE);
			theCalendar.setTimeZone(theObject.getTimeZone().getJavaTimeZone());

			final Subscription theSubscription = new SubscriptionMock(i, Factories.APPLICATION.findByName("net.violet.rss.actustar"), theObject);
			final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency);

			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, FrequencyHandler.FREQUENCY, FrequencyHandler.Frequency.VERY_OFTEN.getLabel());
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, FrequencyHandler.LAST_TIME, theCalendar.getTimestampUTC());
		}

		getBrewsterObject().setMode(VObject.MODE_PING);

		final NewContentWithFrequencyScheduler theScheduler = new NewContentWithFrequencyScheduler(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();
				Assert.assertEquals(getBrewsterObject(), object);
				amountOfObject2Check.incrementAndGet();
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(1, amountOfObject2Check.get());

	}

	@Test
	public void over2Days() {

		final CCalendar theStartTime = new CCalendar("23:40:00", getParisTimezone().getJavaTimeZone());
		final CCalendar lastTime = (CCalendar) theStartTime.clone();
		lastTime.addMillis((-FrequencyHandler.Frequency.VERY_OFTEN.getTimeInMillis() / 2) - (FrequencyHandler.Frequency.VERY_OFTEN.getTimeInMillis()) * 101);

		final AtomicInteger amountOfObject2Check = new AtomicInteger();

		final VObject theObject = getKowalskyObject();

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.rss.actustar"), theObject);
		final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency);

		Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, FrequencyHandler.FREQUENCY, FrequencyHandler.Frequency.VERY_OFTEN.getLabel());
		Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, FrequencyHandler.LAST_TIME, lastTime.getTimestamp());

		final NewContentWithFrequencyScheduler theScheduler = new NewContentWithFrequencyScheduler(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();
				Assert.assertEquals(getKowalskyObject(), object);
				Assert.assertNotNull(inMessageUnit.getProcessConditioner());
				inMessageUnit.runWhenSuccessful();
				amountOfObject2Check.incrementAndGet();
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(1, amountOfObject2Check.get());

		final SubscriptionSchedulingSettings setting = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findBySubscriptionSchedulingAndKey(scheduling, FrequencyHandler.LAST_TIME);
		Assert.assertNotNull(setting);

		final CCalendar theCalendar = new CCalendar((theStartTime.getTimeInMillis() + FrequencyHandler.Frequency.VERY_OFTEN.getTimeInMillis() / 2), theObject.getTimeZone().getJavaTimeZone());

		Assert.assertEquals(theCalendar.getTimestamp(), setting.getValue());
		theCalendar.setTimeZone(getParisTimezone().getJavaTimeZone());
		Assert.assertEquals(theCalendar.getDay(), theStartTime.getDay());

	}

	@Test
	public void over2DaysAgain() {

		final CCalendar theStartTime = new CCalendar("23:55:00", getParisTimezone().getJavaTimeZone());
		final CCalendar lastTime = (CCalendar) theStartTime.clone();
		lastTime.addMillis((-FrequencyHandler.Frequency.VERY_OFTEN.getTimeInMillis() / 2) - (FrequencyHandler.Frequency.VERY_OFTEN.getTimeInMillis()) * 101);

		final AtomicInteger amountOfObject2Check = new AtomicInteger();

		final VObject theObject = getKowalskyObject();

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.rss.actustar"), theObject);
		final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency);

		Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, FrequencyHandler.FREQUENCY, FrequencyHandler.Frequency.VERY_OFTEN.getLabel());
		Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(scheduling, FrequencyHandler.LAST_TIME, lastTime.getTimestamp());

		final NewContentWithFrequencyScheduler theScheduler = new NewContentWithFrequencyScheduler(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();
				Assert.assertEquals(theObject, object);
				Assert.assertNotNull(inMessageUnit.getProcessConditioner());
				inMessageUnit.runWhenSuccessful();
				amountOfObject2Check.incrementAndGet();
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(1, amountOfObject2Check.get());

		final SubscriptionSchedulingSettings setting = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findBySubscriptionSchedulingAndKey(scheduling, FrequencyHandler.LAST_TIME);
		Assert.assertNotNull(setting);

		final CCalendar theCalendar = new CCalendar((theStartTime.getTimeInMillis() + FrequencyHandler.Frequency.VERY_OFTEN.getTimeInMillis() / 2), theObject.getTimeZone().getJavaTimeZone());

		Assert.assertEquals(theCalendar.getTimestamp(), setting.getValue());
		theCalendar.setTimeZone(getParisTimezone().getJavaTimeZone());
		theStartTime.add(Calendar.DAY_OF_YEAR, 1);
		Assert.assertEquals(theStartTime.getDay(), theCalendar.getDay());

	}
}
