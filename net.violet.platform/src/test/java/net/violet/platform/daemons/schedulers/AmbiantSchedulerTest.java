package net.violet.platform.daemons.schedulers;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class AmbiantSchedulerTest extends MockTestBase {

	@Test
	public void ambiantAroundTheWorld() {

		final VObject[] theObjects = new VObject[] { getKowalskyObject(), getPrivateObject(), getBrewsterObject() };
		final Timezone[] theTimezones = { Factories.TIMEZONE.findByJavaId("Europe/Paris"), Factories.TIMEZONE.findByJavaId("Australia/Sydney"), Factories.TIMEZONE.findByJavaId("Europe/Paris") };

		final CCalendar theStartTime = new CCalendar(System.currentTimeMillis());
		final AtomicInteger amountOfObject2Check = new AtomicInteger(0);

		for (int i = 0; i < theObjects.length;) {
			final VObject theObject = theObjects[i];
			theObject.setTimeZone(theTimezones[i++]);

			final Subscription theSubscription = new SubscriptionMock(i, Factories.APPLICATION.findByName("net.violet.weather"), theObject);
			if ((i == 1) || (i == 2)) {
				Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.Ambiant);
			} else {
				Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword);
			}
		}

		final AtomicBoolean expectedObject = new AtomicBoolean(true);

		final AmbiantScheduler theScheduler = new AmbiantScheduler(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();

				if (object.getId().longValue() == getBrewsterObject().getId().longValue()) {
					expectedObject.set(false);
				}

				amountOfObject2Check.incrementAndGet();
			}
		};

		theScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(2, amountOfObject2Check.get());
		Assert.assertTrue(expectedObject.get());

		expectedObject.set(true);
		amountOfObject2Check.set(0);

		final AmbiantWithKeywordScheduler theOtherScheduler = new AmbiantWithKeywordScheduler(new String[] { "-p", "10" }) {

			@Override
			protected void sendMessage(MessageProcessUnit inMessageUnit) {
				final VObject object = inMessageUnit.get().getSubscription().getObject().getReference();

				if (object.getId().longValue() != getBrewsterObject().getId().longValue()) {
					expectedObject.set(false);
				}

				amountOfObject2Check.incrementAndGet();
			}
		};

		theOtherScheduler.process(theStartTime.getTimeInMillis());

		Assert.assertEquals(1, amountOfObject2Check.get());
		Assert.assertTrue(expectedObject.get());

	}

}
