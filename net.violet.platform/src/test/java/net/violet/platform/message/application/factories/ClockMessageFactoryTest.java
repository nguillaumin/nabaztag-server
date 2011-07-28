package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.TimeZone;

import net.violet.platform.applications.ClockHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.message.application.factories.AbstractMessageFactory.Message2Send;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class ClockMessageFactoryTest extends MockTestBase {

	private static final Logger LOGGER = Logger.getLogger(ClockMessageFactoryTest.class);

	@Test
	public void normalClockTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Application.NativeApplication.CLOCK.getApplication(), getKowalskyObject());
		final CCalendar theDeliveryDate = new CCalendar("17:00:00", TimeZone.getTimeZone("Europe/Paris"));

		final ClockMessageFactory factory = (ClockMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Frequency);

			theSubscriptionData.setSetting(ClockHandler.LANGUAGES, Collections.singletonList("fr-FR"));
			theSubscriptionData.setSetting(ClockHandler.TYPES, Collections.singletonList("1"));

			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_CLOCK, message.getTTL());
			Assert.assertEquals(ClockMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.HORLOGE_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			final Files[] files = message.getBody();
			Assert.assertEquals(1, files.length);
			Assert.assertEquals("broadcast/broad/config/clock/fr/17/1.mp3", files[0].getPath());

		} catch (final Exception e) {
			ClockMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.getMessage());
		} finally {

		}
	}

	@Test
	public void sharedClockTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Application.NativeApplication.CLOCK.getApplication(), getKowalskyObject());
		final CCalendar theDeliveryDate = new CCalendar("17:00:00", TimeZone.getTimeZone("Europe/Paris"));

		final ClockMessageFactory factory = (ClockMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Frequency);

			theSubscriptionData.setSetting(ClockHandler.LANGUAGES, Collections.singletonList("fr-FR"));
			theSubscriptionData.setSetting(ClockHandler.TYPES, Collections.singletonList("3"));

			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_CLOCK, message.getTTL());
			Assert.assertEquals(ClockMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.HORLOGE_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			final Files[] files = message.getBody();
			Assert.assertEquals(1, files.length);
			Assert.assertEquals("broadcast/broad/config/clock/fr/17/1.mp3", files[0].getPath());

		} catch (final Exception e) {
			ClockMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.getMessage());
		} finally {

		}
	}

}
