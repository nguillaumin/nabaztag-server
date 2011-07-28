package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.applications.TradeFreeHandler;
import net.violet.platform.applications.TradeFullHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.MusicMock;
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

public class TradeMessageFactoryTest extends MockTestBase {

	private static final Logger LOGGER = Logger.getLogger(TradeMessageFactoryTest.class);

	@Test
	public void getMessageWithoutContentTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Application.NativeApplication.BOURSE_FREE.getApplication(), getKowalskyObject());
		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.addMillis(3600000);

		final TradeMessageFactory factory = (TradeMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(TradeMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.BOURSE_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			Assert.assertNull(message.getBody());

		} catch (final Exception e) {
			Assert.fail(e.getMessage());

		}
	}

	@Test
	public void getAMessageTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Application.NativeApplication.BOURSE_FREE.getApplication(), getKowalskyObject());

		final Source source = new SourceMock(8205, "money.cac 40", 5);

		// Creates the settings
		theSubscription.setSettings(Collections.singletonMap(TradeFreeHandler.SOURCE, source.getSource_path()));

		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.setTimeMYSQL("08:00:00");

		final TradeMessageFactory factory = (TradeMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(TradeMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.BOURSE_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			Assert.assertFalse(message.isStream());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(1, theFiles.length);
			Assert.assertEquals("broadcast/broad/config/money/fr/trend/4.mp3", theFiles[0].getPath());
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());

		}
	}

	@Test
	public void getAnOtherMessageTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Application.NativeApplication.BOURSE_FREE.getApplication(), getKowalskyObject());

		getKowalskyObject().getPreferences().setLangPreferences(getUsLang());

		final Source source = new SourceMock(8205, "money.cac 40", 4);

		// Creates the settings
		theSubscription.setSettings(Collections.singletonMap(TradeFreeHandler.SOURCE, source.getSource_path()));

		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.setTimeMYSQL("08:00:00");

		final TradeMessageFactory factory = (TradeMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(TradeMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.BOURSE_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			Assert.assertFalse(message.isStream());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(1, theFiles.length);
			Assert.assertEquals("broadcast/broad/config/money/us/trend/3.mp3", theFiles[0].getPath());
		} catch (final Exception e) {
			Assert.fail(e.toString());

			getKowalskyObject().getPreferences().setLangPreferences(getFrLang());
		}
	}

	@Test
	public void bourseFullTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Application.NativeApplication.BOURSE_FULL.getApplication(), getKowalskyObject());

		final Source source = new SourceMock(8205, "$15.money", 5);
		//(3305279,broadcast/broad/config/weather/uk/temp/57.mp3,audio/mpeg,2008
		// -07-10 11:46:16,NULL)
		final Files files = Factories.FILES.find(3305279);
		final Music music = new MusicMock(1, files, net.violet.common.StringShop.EMPTY_STRING, getKowalskyUser(), 0);

		// Creates the settings
		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(TradeFreeHandler.SOURCE, source.getSource_path());
		settings.put(TradeFullHandler.MUSIC, music.getId().toString());
		theSubscription.setSettings(settings);

		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.setTimeMYSQL("08:00:00");

		final TradeMessageFactory factory = (TradeMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(TradeMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.BOURSE_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			Assert.assertFalse(message.isStream());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(2, theFiles.length);
			Assert.assertEquals(music.getFile().getPath(), theFiles[0].getPath());
			Assert.assertEquals("broadcast/broad/config/moneyplus/fr/trend/4.mp3", theFiles[1].getPath());
		} catch (final Exception e) {
			TradeMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.toString());

		}
	}

	@Test
	public void getSourceMessageNoLastTimeTest() {

		final Source source = new SourceMock(8205, "money.cac 40", 4, System.currentTimeMillis());

		final Subscription theSubscription = new SubscriptionMock(1, Application.NativeApplication.BOURSE_FREE.getApplication(), getKowalskyObject());

		// Creates the settings
		theSubscription.setSettings(Collections.singletonMap(TradeFreeHandler.SOURCE, source.getSource_path()));

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);

		final TradeMessageFactory factory = (TradeMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final long theLastTime = System.currentTimeMillis();
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNotNull(theMessage);
		Assert.assertTrue(theMessage.isSourceModeUpdate());
		Assert.assertEquals(new Integer((int) source.getSource_val()), theMessage.getSources().get(Integer.toString(factory.getSource().getId())));
		Assert.assertEquals(Constantes.QUEUE_TTL_SOURCES, theMessage.getTTLInSecond());
		final SubscriptionSchedulingSettingsData theLastTimeSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theScheduling, AmbiantHandler.LAST_TIME);
		Assert.assertNotNull(theLastTimeSetting);
		Assert.assertEquals(theLastTime, Long.parseLong(theLastTimeSetting.getValue()));

		source.delete();
	}

	@Test
	public void getSourceMessageWithLastTimeNUTest() {
		final long sourceUpdateTime = System.currentTimeMillis() / 1000;
		final Source source = new SourceMock(8205, "money.cac 40", 4, sourceUpdateTime);
		final long theLastTime = sourceUpdateTime * 1000;
		final Subscription theSubscription = new SubscriptionMock(1, Application.NativeApplication.BOURSE_FREE.getApplication(), getKowalskyObject());

		// Creates the settings
		theSubscription.setSettings(Collections.singletonMap(TradeFreeHandler.SOURCE, source.getSource_path()));

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		theScheduling.createSetting(AmbiantHandler.LAST_TIME, Long.toString(theLastTime));

		final TradeMessageFactory factory = (TradeMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNull(theMessage);

		source.delete();
	}

	@Test
	public void getSourceMessageWithLastTimeUTest() {
		final long sourceUpdateTime = System.currentTimeMillis() / 1000;
		final Source source = new SourceMock(8205, "money.cac 40", 4, sourceUpdateTime + 60);
		final long theLastTime = sourceUpdateTime * 1000;
		final Subscription theSubscription = new SubscriptionMock(1, Application.NativeApplication.BOURSE_FREE.getApplication(), getKowalskyObject());

		// Creates the settings
		theSubscription.setSettings(Collections.singletonMap(TradeFreeHandler.SOURCE, source.getSource_path()));

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		theScheduling.createSetting(AmbiantHandler.LAST_TIME, Long.toString(theLastTime));

		final TradeMessageFactory factory = (TradeMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNotNull(theMessage);
		Assert.assertTrue(theMessage.isSourceModeUpdate());
		Assert.assertEquals(new Integer((int) source.getSource_val()), theMessage.getSources().get(Integer.toString(factory.getSource().getId())));
		Assert.assertEquals(Constantes.QUEUE_TTL_SOURCES, theMessage.getTTLInSecond());
		final SubscriptionSchedulingSettingsData theLastTimeSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theScheduling, AmbiantHandler.LAST_TIME);
		Assert.assertNotNull(theLastTimeSetting);
		Assert.assertEquals(theLastTime, Long.parseLong(theLastTimeSetting.getValue()));

		source.delete();
	}

}
