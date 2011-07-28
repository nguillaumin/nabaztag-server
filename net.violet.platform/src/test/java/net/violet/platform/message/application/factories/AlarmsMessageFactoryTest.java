package net.violet.platform.message.application.factories;

import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageNSettingProcessUnit;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.message.application.factories.AbstractMessageFactory.Message2Send;
import net.violet.platform.schedulers.DailyWithMediaHandler;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.junit.Assert;
import org.junit.Test;

public class AlarmsMessageFactoryTest extends MockTestBase {

	@Test
	public void getAnAlarmMessageTest() {

		final Application theApplication = Application.NativeApplication.ALARM_CLOCK.getApplication();

		final Files files = Factories.FILES.find(3305279);
		final Music music = new MusicMock(1, files, net.violet.common.StringShop.EMPTY_STRING, getKowalskyUser(), 0);

		final Subscription theSubscription = Factories.SUBSCRIPTION.create(theApplication, getKowalskyObject());
		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);

		final SubscriptionScheduling theScheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SCHEDULING_TYPE.DailyWithMedia);
		final SubscriptionSchedulingSettings theTimeSetting = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theScheduling, "Monday", "10:00:00");
		Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theScheduling, "Monday" + DailyWithMediaHandler.MEDIA_SUFFIXE, music.getId().toString());

		final AlarmsMessageFactory theFactory = (AlarmsMessageFactory) AbstractMessageFactory.getFactoryByApplication(theApplication);

		final Message2Send theMessage = theFactory.getMessage(new MessageNSettingProcessUnit(SubscriptionSchedulingSettingsData.getData(theTimeSetting), SubscriptionSchedulingData.getData(theScheduling), null, null) {

			@Override
			public void runWhenSuccessful() {
			}
		}).get(0);

		Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, theMessage.getTTL());
		Assert.assertEquals(AlarmsMessageFactory.TITLE, theMessage.getTitle());
		Assert.assertEquals(Palette.RANDOM, theMessage.getColorPal());
		Assert.assertEquals(theSubscriptionData, theMessage.getSubscription());
		Assert.assertEquals(MessageSignature.EMPTY_SIGNATURE, theMessage.getSignature());
		Assert.assertEquals(theSubscription.getObject(), theMessage.getRecipient());
		Assert.assertEquals(JabberMessageFactory.IDLE_MODE, theMessage.getMode());
		Assert.assertFalse(theMessage.isStream());
		final Files[] theFiles = theMessage.getBody();
		Assert.assertEquals(1, theFiles.length);
		Assert.assertEquals(music.getFile().getPath(), theFiles[0].getPath());

	}

	@Test
	public void getAReminderMessageTest() {
		final Application theApplication = Application.NativeApplication.REMINDER.getApplication();

		final Files files = Factories.FILES.find(3305279);
		final Music music = new MusicMock(1, files, net.violet.common.StringShop.EMPTY_STRING, getKowalskyUser(), 0);

		final Subscription theSubscription = Factories.SUBSCRIPTION.create(theApplication, getKowalskyObject());
		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);

		final SubscriptionScheduling theScheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SCHEDULING_TYPE.DailyWithMedia);
		final SubscriptionSchedulingSettings theTimeSetting = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theScheduling, "Monday", "10:00:00");
		Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theScheduling, "Monday" + DailyWithMediaHandler.MEDIA_SUFFIXE, music.getId().toString());

		final AlarmsMessageFactory theFactory = (AlarmsMessageFactory) AbstractMessageFactory.getFactoryByApplication(theApplication);

		final Message2Send theMessage = theFactory.getMessage(new MessageNSettingProcessUnit(SubscriptionSchedulingSettingsData.getData(theTimeSetting), SubscriptionSchedulingData.getData(theScheduling), null, null) {

			@Override
			public void runWhenSuccessful() {
			}
		}).get(0);

		Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, theMessage.getTTL());
		Assert.assertEquals(AlarmsMessageFactory.TITLE, theMessage.getTitle());
		Assert.assertEquals(Palette.RANDOM, theMessage.getColorPal());
		Assert.assertEquals(theSubscriptionData, theMessage.getSubscription());
		Assert.assertEquals(MessageSignature.EMPTY_SIGNATURE, theMessage.getSignature());
		Assert.assertEquals(theSubscription.getObject(), theMessage.getRecipient());
		Assert.assertEquals(JabberMessageFactory.IDLE_MODE, theMessage.getMode());
		Assert.assertFalse(theMessage.isStream());
		final Files[] theFiles = theMessage.getBody();
		Assert.assertEquals(1, theFiles.length);
		Assert.assertEquals(music.getFile().getPath(), theFiles[0].getPath());

	}
}
