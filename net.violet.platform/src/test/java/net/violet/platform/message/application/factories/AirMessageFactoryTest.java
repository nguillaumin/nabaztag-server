package net.violet.platform.message.application.factories;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.applications.AirHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.SourceMock;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.message.application.factories.AbstractMessageFactory.Message2Send;
import net.violet.platform.schedulers.AmbiantHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class AirMessageFactoryTest extends MockTestBase {

	private static final Logger LOGGER = Logger.getLogger(AirMessageFactoryTest.class);

	@Test
	public void getMessageWithoutContentTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.air"), getKowalskyObject());
		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.addMillis(3600000);

		final AirMessageFactory factory = (AirMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(AirMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.AIR_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			Assert.assertNull(message.getBody());

		} catch (final Exception e) {
			AirMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void getGoodMessageTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.air"), getKowalskyObject());

		final Source source = new SourceMock(8205, "air.paris.today", 1);

		// Creates the settings
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		theSettings.put(AirHandler.SOURCE_SETTING, source.getSource_path());
		theSubscription.setSettings(theSettings);

		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.setTimeMYSQL("08:00:00");

		final AirMessageFactory factory = (AirMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(AirMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.AIR_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			Assert.assertFalse(message.isStream());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(1, theFiles.length);
			Assert.assertEquals("broadcast/broad/config/air/fr/quality/good.mp3", theFiles[0].getPath());

			final MessageDraft messageDraft = message.generateMessageDraft();
			Assert.assertEquals(theDeliveryDate, messageDraft.getDeliveryDate());
		} catch (final Exception e) {
			AirMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.toString());
		}
	}

	@Test
	public void getMiddleMessageTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.air"), getKowalskyObject());

		final Source source = new SourceMock(8205, "air.paris.today", 6);

		// Creates the settings
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		theSettings.put(AirHandler.SOURCE_SETTING, source.getSource_path());
		theSubscription.setSettings(theSettings);

		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.setTimeMYSQL("08:00:00");

		final AirMessageFactory factory = (AirMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(AirMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.AIR_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(1, theFiles.length);
			Assert.assertEquals("broadcast/broad/config/air/fr/quality/middle.mp3", theFiles[0].getPath());

			final MessageDraft messageDraft = message.generateMessageDraft();
			Assert.assertEquals(theDeliveryDate, messageDraft.getDeliveryDate());

		} catch (final Exception e) {
			AirMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.toString());
		}
	}

	@Test
	public void getBadMessageTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.air"), getKowalskyObject());

		final Source source = new SourceMock(8205, "air.paris.today", 9);

		// Creates the settings
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		theSettings.put(AirHandler.SOURCE_SETTING, source.getSource_path());
		theSubscription.setSettings(theSettings);

		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.setTimeMYSQL("08:00:00");

		final AirMessageFactory factory = (AirMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(AirMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.AIR_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(1, theFiles.length);
			Assert.assertEquals("broadcast/broad/config/air/fr/quality/bad.mp3", theFiles[0].getPath());

			final MessageDraft messageDraft = message.generateMessageDraft();
			Assert.assertEquals(theDeliveryDate, messageDraft.getDeliveryDate());

		} catch (final Exception e) {
			AirMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.toString());
		}
	}

	@Test
	public void getSourceMessageNoLastTimeTest() {
		final Source source = new SourceMock(8205, "air.paris.today", 9, System.currentTimeMillis());
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.air"), getKowalskyObject());

		// Creates the settings
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		theSettings.put(AirHandler.SOURCE_SETTING, source.getSource_path());
		theSubscription.setSettings(theSettings);

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);

		final AirMessageFactory factory = (AirMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final long theLastTime = System.currentTimeMillis();
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNotNull(theMessage);
		Assert.assertTrue(theMessage.isSourceModeUpdate());
		Assert.assertEquals(new Integer((int) source.getSource_val()), theMessage.getSources().get(Integer.toString(factory.getSource().getId())));
		Assert.assertEquals(Constantes.QUEUE_TTL_SOURCES, theMessage.getTTLInSecond());
		final SubscriptionSchedulingSettingsData theLastTimeSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theScheduling, AmbiantHandler.LAST_TIME);
		Assert.assertNotNull(theLastTimeSetting);
		Assert.assertEquals(theLastTime, Long.parseLong(theLastTimeSetting.getValue()));
	}

	@Test
	public void getSourceMessageWithLastTimeNUTest() {
		final long sourceUpdateTime = System.currentTimeMillis() / 1000;
		final Source source = new SourceMock(8205, "air.paris.today", 9, sourceUpdateTime);
		final long theLastTime = sourceUpdateTime * 1000;
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.air"), getKowalskyObject());

		// Creates the settings
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		theSettings.put(AirHandler.SOURCE_SETTING, source.getSource_path());
		theSubscription.setSettings(theSettings);

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		theScheduling.createSetting(AmbiantHandler.LAST_TIME, Long.toString(theLastTime));

		final AirMessageFactory factory = (AirMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNull(theMessage);
	}

	@Test
	public void getSourceMessageWithLastTimeUTest() {
		final long sourceUpdateTime = System.currentTimeMillis() / 1000;
		final Source source = new SourceMock(8205, "air.paris.today", 9, sourceUpdateTime + 60);
		final long theLastTime = sourceUpdateTime * 1000;
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.air"), getKowalskyObject());

		// Creates the settings
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		theSettings.put(AirHandler.SOURCE_SETTING, source.getSource_path());
		theSubscription.setSettings(theSettings);

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		theScheduling.createSetting(AmbiantHandler.LAST_TIME, Long.toString(theLastTime));

		final AirMessageFactory factory = (AirMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNotNull(theMessage);
		Assert.assertTrue(theMessage.isSourceModeUpdate());
		Assert.assertEquals(new Integer((int) source.getSource_val()), theMessage.getSources().get(Integer.toString(factory.getSource().getId())));
		Assert.assertEquals(Constantes.QUEUE_TTL_SOURCES, theMessage.getTTLInSecond());
		final SubscriptionSchedulingSettingsData theLastTimeSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theScheduling, AmbiantHandler.LAST_TIME);
		Assert.assertNotNull(theLastTimeSetting);
		Assert.assertEquals(theLastTime, Long.parseLong(theLastTimeSetting.getValue()));
	}
}
