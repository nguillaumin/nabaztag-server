package net.violet.platform.datamodel;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class SubscriptionTest extends DBTest {

	@Test
	public void OneOutOfThreeTest() {

		final Timezone theTZ = Factories.TIMEZONE.findByJavaId("Europe/Paris");

		final CCalendar start = new CCalendar("09:45:00", theTZ.getJavaTimeZone());
		final CCalendar end = (CCalendar) start.clone();
		end.add(Calendar.MINUTE, 30);

		final AtomicInteger amount = new AtomicInteger(0);

		Factories.SUBSCRIPTION.walkTimelySubscription(theTZ, SchedulingType.SCHEDULING_TYPE.Daily, DailyHandler.Weekday.FRIDAY, start, end, new JoinRecordsWalker<Subscription, SubscriptionSchedulingSettings>() {

			public void process(Subscription inSubscription, SubscriptionSchedulingSettings inSetting) {
				Assert.assertEquals(31L, inSubscription.getObject().getId().longValue());
				amount.incrementAndGet();
			}

		});

		Assert.assertEquals(1, amount.get());
	}

	@Test
	public void multipleTimeZones() {

		final AtomicInteger amount = new AtomicInteger(0);

		final Timezone[] theTimezones = { Factories.TIMEZONE.findByJavaId("Europe/Paris"), Factories.TIMEZONE.findByJavaId("America/Chicago"), Factories.TIMEZONE.findByJavaId("Asia/Tokyo") };
		final long[] objectsId = { 31L, 57520L, 16951L };
		final long[] theSettingsId = { 5L, 13L, 19L };
		final String[] theSettings = { "23:55:00", "00:02:00", "00:16:00" };

		for (int i = 0; i < objectsId.length; i++) {
			final VObject theObject = Factories.VOBJECT.find(objectsId[i]);
			theObject.setTimeZone(theTimezones[i]);
			final CCalendar start = new CCalendar("23:45:00", theTimezones[i].getJavaTimeZone());
			final CCalendar end = (CCalendar) start.clone();
			end.add(Calendar.MINUTE, 30);
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.find(theSettingsId[i]).setValue(theSettings[i]);

			Factories.SUBSCRIPTION.walkTimelySubscription(theTimezones[i], SchedulingType.SCHEDULING_TYPE.Daily, DailyHandler.Weekday.FRIDAY, start, end, new JoinRecordsWalker<Subscription, SubscriptionSchedulingSettings>() {

				public void process(Subscription inSubscription, SubscriptionSchedulingSettings inSetting) {
					Assert.assertEquals(theObject.getId(), inSubscription.getObject().getId());
					amount.incrementAndGet();
				}
			});
		}

		Assert.assertEquals(2, amount.get());

	}

	@Test
	public void oneOnEachBoundTest() {
		final AtomicInteger amount = new AtomicInteger(0);

		final long[] objectsId = { 31L, 57520L, 16951L };
		final long[] theSettingsId = { 5L, 12L, 19L };
		final String[] theSettings = { "10:00:00", "10:30:00", "11:00:00" };
		final Timezone theTZ = Factories.TIMEZONE.findByJavaId("Europe/Paris");

		for (int i = 0; i < objectsId.length; i++) {
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.find(theSettingsId[i]).setValue(theSettings[i]);
		}

		final CCalendar start = new CCalendar("10:00:00", theTZ.getJavaTimeZone());
		final CCalendar end = (CCalendar) start.clone();
		end.add(Calendar.MINUTE, 30);

		final AtomicBoolean expectedObject = new AtomicBoolean(true);

		Factories.SUBSCRIPTION.walkTimelySubscription(theTZ, SchedulingType.SCHEDULING_TYPE.Daily, DailyHandler.Weekday.FRIDAY, start, end, new JoinRecordsWalker<Subscription, SubscriptionSchedulingSettings>() {

			public void process(Subscription inSubscription, SubscriptionSchedulingSettings inSetting) {
				if (inSubscription.getObject().getId().longValue() != 31) {
					expectedObject.set(false);
				}
				amount.incrementAndGet();
			}
		});

		Assert.assertEquals(1, amount.get());
		Assert.assertTrue(expectedObject.get());

	}

	@Test
	public void windowBetweenTodayAndTomorrow() {

		final Timezone theTZ = Factories.TIMEZONE.findByJavaId("Europe/Paris");

		final CCalendar start = new CCalendar("23:45:00", theTZ.getJavaTimeZone());
		final CCalendar end = (CCalendar) start.clone();
		end.add(Calendar.MINUTE, 30);

		Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.find(5).setValue("23:55:00");
		Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.find(13).setValue("00:02:00");
		Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.find(19).setValue("00:16:00");

		final AtomicInteger amount = new AtomicInteger(0);

		Factories.SUBSCRIPTION.walkTimelySubscription(theTZ, SchedulingType.SCHEDULING_TYPE.Daily, DailyHandler.Weekday.FRIDAY, start, end, new JoinRecordsWalker<Subscription, SubscriptionSchedulingSettings>() {

			public void process(Subscription inSubscription, SubscriptionSchedulingSettings inSetting) {
				amount.incrementAndGet();
			}
		});

		Assert.assertEquals(2, amount.get());

	}

	@Test
	public void walkByApplicationIdWithDistinctSettingValueTest() {

		final AtomicInteger amount = new AtomicInteger(0);

		for (final Subscription aSubscription : Factories.SUBSCRIPTION.findAllByApplication(Factories.APPLICATION.find(1119))) {
			if (aSubscription.getSettings().containsKey("key")) {
				amount.incrementAndGet();
			}

		}

		Assert.assertEquals(2, amount.get());
	}

	@Test
	public void walkFrequencySubscriptionByTimezoneTest() {

		final Timezone tzParis = Factories.TIMEZONE.findByJavaId("Europe/Paris");
		final Timezone tzNewYork = Factories.TIMEZONE.findByJavaId("America/New_York");

		CCalendar start = new CCalendar("12:00:00", tzParis.getJavaTimeZone());
		CCalendar end = (CCalendar) start.clone();
		end.add(Calendar.MINUTE, 30);

		final AtomicInteger amount = new AtomicInteger(0);
		final AtomicBoolean expectedObject = new AtomicBoolean(true);

		// First instance
		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.Frequency, FrequencyHandler.Frequency.HOURLY, tzParis, start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inSubscription, SubscriptionScheduling inScheduling) {
				amount.incrementAndGet();
				if (inSubscription.getObject().getId() != 54321) {
					expectedObject.set(false);
				}
			}
		});

		Assert.assertEquals(1, amount.get());
		Assert.assertTrue(expectedObject.get());

		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.NewContentWithFrequency, FrequencyHandler.Frequency.OFTEN, tzParis, start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inSubscription, SubscriptionScheduling inScheduling) {
				amount.incrementAndGet();
				if (inSubscription.getObject().getId() != 54321) {
					expectedObject.set(false);
				}
			}
		});

		Assert.assertEquals(2, amount.get());
		Assert.assertTrue(expectedObject.get());

		start.setTimeZone(tzNewYork.getJavaTimeZone());
		end.setTimeZone(tzNewYork.getJavaTimeZone());
		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.Frequency, FrequencyHandler.Frequency.HOURLY, tzNewYork, start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inSubscription, SubscriptionScheduling inScheduling) {
				amount.incrementAndGet();
			}
		});

		Assert.assertEquals(2, amount.get());

		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.RandomWithFrequency, FrequencyHandler.Frequency.SOMETIMES, tzNewYork, start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inSubscription, SubscriptionScheduling inScheduling) {
				amount.incrementAndGet();
			}
		});

		Assert.assertEquals(2, amount.get());
		// End first instance

		// Second instance
		amount.set(0);
		start = new CCalendar("12:30:00", tzParis.getJavaTimeZone());
		end = (CCalendar) start.clone();
		end.add(Calendar.MINUTE, 30);

		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.Frequency, FrequencyHandler.Frequency.HOURLY, tzParis, start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inSubscription, SubscriptionScheduling inScheduling) {
				amount.incrementAndGet();
			}
		});

		Assert.assertEquals(0, amount.get());

		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.NewContentWithFrequency, FrequencyHandler.Frequency.OFTEN, tzParis, start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inSubscription, SubscriptionScheduling inScheduling) {
				amount.incrementAndGet();
			}
		});

		Assert.assertEquals(0, amount.get());

		start.setTimeZone(tzNewYork.getJavaTimeZone());
		end.setTimeZone(tzNewYork.getJavaTimeZone());
		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.Frequency, FrequencyHandler.Frequency.HOURLY, tzNewYork, start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inSubscription, SubscriptionScheduling inScheduling) {
				amount.incrementAndGet();
				if (inSubscription.getObject().getId() != 654321) {
					expectedObject.set(false);
				}
			}
		});

		Assert.assertEquals(1, amount.get());
		Assert.assertTrue(expectedObject.get());

		Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE.RandomWithFrequency, FrequencyHandler.Frequency.SOMETIMES, tzNewYork, start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inSubscription, SubscriptionScheduling inScheduling) {
				amount.incrementAndGet();
				if (inSubscription.getObject().getId() != 654321) {
					expectedObject.set(false);
				}
			}
		});

		Assert.assertEquals(2, amount.get());
		Assert.assertTrue(expectedObject.get());

	}

	@Test
	public void walkSubscriptionByTimezoneAndNewContentTest() {
		final Timezone theTZ = Factories.TIMEZONE.findByJavaId("Europe/Paris");

		final AtomicInteger amount = new AtomicInteger(0);
		final AtomicBoolean schedulingType = new AtomicBoolean(true);
		final AtomicBoolean expectedObjectsOnly = new AtomicBoolean(true);

		Factories.SUBSCRIPTION.walkSubscriptionByTimezoneAndNewContent(SCHEDULING_TYPE.NewContentWithKeywordAndMedia, theTZ, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

			public void process(Subscription inSubscription, SubscriptionScheduling inScheduling) {
				amount.incrementAndGet();
				if (inScheduling.getType() != SCHEDULING_TYPE.NewContentWithKeywordAndMedia) {
					schedulingType.set(false);
				}

				final VObject theObject = inSubscription.getObject();
				if ((theObject.getId() != 57520) && (theObject.getId() != 31)) {
					expectedObjectsOnly.set(false);
				}
			}
		});

		Assert.assertEquals(2, amount.get());
		Assert.assertTrue(schedulingType.get());
		Assert.assertTrue(expectedObjectsOnly.get());
	}

	@Test
	public void creationTest() {
		final Subscription theSub = Factories.SUBSCRIPTION.create(Factories.APPLICATION.find(757), Factories.VOBJECT.find(31));
		Assert.assertEquals(Collections.emptyMap(), theSub.getSettings());
	}

	@Test
	public void editSettingsTest() {
		final Subscription theSub = Factories.SUBSCRIPTION.create(Factories.APPLICATION.find(757), Factories.VOBJECT.find(31));
		Assert.assertEquals(Collections.emptyMap(), theSub.getSettings());
		final Map<String, Integer> settings = new HashMap<String, Integer>();
		settings.put("one", 1);
		settings.put("two", 2);
		theSub.setSettings(settings);
		Assert.assertEquals(settings, theSub.getSettings());
	}

}
