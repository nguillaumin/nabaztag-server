package net.violet.platform.message.application.factories;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.applications.WeatherHandler;
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

public class WeatherMessageFactoryTest extends MockTestBase {

	private static final Logger LOGGER = Logger.getLogger(WeatherMessageFactoryTest.class);

	@Test
	public void getMessageWithoutContentTest() {

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.weather"), getKowalskyObject());
		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.addMillis(3600000);

		final WeatherMessageFactory factory = (WeatherMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(WeatherMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.WEATHER_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			Assert.assertNull(message.getBody());

		} catch (final Exception e) {
			WeatherMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.getMessage());

		}
	}

	@Test
	public void getMessageWithTodayAndCelsiusTest() {

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.weather"), getKowalskyObject());

		final Source source = new SourceMock(8205, "Nmeteo.FRANCE.Orléans.weather", 3);
		new SourceMock(8206, "Nmeteo.FRANCE.Orléans.temp", 21);

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(WeatherHandler.LANGUAGE, "fr-FR");
		settings.put(WeatherHandler.UNIT, "1");
		settings.put(WeatherHandler.SOURCE, source.getSource_path());
		theSubscription.setSettings(settings);

		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.setTimeMYSQL("08:00:00");

		final WeatherMessageFactory factory = (WeatherMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(WeatherMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.WEATHER_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			Assert.assertFalse(message.isStream());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(4, theFiles.length);
			Assert.assertEquals("broadcast/broad/config/weather/fr/today.mp3", theFiles[0].getPath());
			checkCelsius(theFiles);

		} catch (final Exception e) {
			WeatherMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.toString());

		}
	}

	private void checkCelsius(Files[] inFiles) {
		checkSkyAndTemp(inFiles);
		Assert.assertEquals("broadcast/broad/config/weather/fr/degree.mp3", inFiles[3].getPath());
	}

	private void checkFahrenheit(Files[] inFiles) {
		checkSkyAndTemp(inFiles);
		Assert.assertEquals("broadcast/broad/config/weather/fr/farenheit.mp3", inFiles[3].getPath());
	}

	private void checkSkyAndTemp(Files[] inFiles) {
		Assert.assertEquals("broadcast/broad/config/weather/fr/sky/3.mp3", inFiles[1].getPath());
		Assert.assertEquals("broadcast/broad/config/weather/fr/temp/21.mp3", inFiles[2].getPath());
	}

	@Test
	public void getMessageWithTomorrowAndFahrenheitTest() {

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.weather"), getKowalskyObject());

		final Source source = new SourceMock(8205, "Nmeteo.FRANCE.Orléans.weather", 3);
		new SourceMock(8206, "Nmeteo.FRANCE.Orléans.temp", -6);

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(WeatherHandler.LANGUAGE, "fr-FR");
		settings.put(WeatherHandler.UNIT, "2");
		settings.put(WeatherHandler.SOURCE, source.getSource_path());
		theSubscription.setSettings(settings);

		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.setTimeMYSQL("16:00:00");

		final WeatherMessageFactory factory = (WeatherMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(WeatherMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.WEATHER_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(4, theFiles.length);
			Assert.assertEquals("broadcast/broad/config/weather/fr/tomorrow.mp3", theFiles[0].getPath());
			checkFahrenheit(theFiles);

			for (final Files aFile : message.getBody()) {
				WeatherMessageFactoryTest.LOGGER.info(aFile.getPath());
			}

		} catch (final Exception e) {
			WeatherMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.toString());

		}
	}

	@Test
	public void getMessageWithoutDeliveryTest() {

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.weather"), getKowalskyObject());

		final Source source = new SourceMock(8205, "Nmeteo.FRANCE.Orléans.weather", 3);
		new SourceMock(8206, "Nmeteo.FRANCE.Orléans.temp", 21);

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(WeatherHandler.LANGUAGE, "fr-FR");
		settings.put(WeatherHandler.UNIT, "1");
		settings.put(WeatherHandler.SOURCE, source.getSource_path());
		theSubscription.setSettings(settings);

		final CCalendar theDeliveryDate = null;

		final WeatherMessageFactory factory = (WeatherMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(WeatherMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.WEATHER_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(4, theFiles.length);
			checkCelsius(theFiles);

		} catch (final Exception e) {
			WeatherMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.toString());

		}
	}

	@Test
	public void getMessageWithHighestTempTest() {

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.weather"), getKowalskyObject());

		final Source source = new SourceMock(8205, "Nmeteo.FRANCE.Orléans.weather", 3);
		new SourceMock(8206, "Nmeteo.FRANCE.Orléans.temp", WeatherMessageFactory.TEMP_MAX + 10);

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(WeatherHandler.LANGUAGE, "fr-FR");
		settings.put(WeatherHandler.UNIT, "1");
		settings.put(WeatherHandler.SOURCE, source.getSource_path());
		theSubscription.setSettings(settings);

		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.setTimeMYSQL("16:00:00");

		final WeatherMessageFactory factory = (WeatherMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(WeatherMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.WEATHER_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(4, theFiles.length);
			Assert.assertEquals("broadcast/broad/config/weather/fr/tomorrow.mp3", theFiles[0].getPath());
			Assert.assertEquals("broadcast/broad/config/weather/fr/temp/" + WeatherMessageFactory.TEMP_MAX + ".mp3", theFiles[2].getPath());

			for (final Files aFile : message.getBody()) {
				WeatherMessageFactoryTest.LOGGER.info(aFile.getPath());
			}

		} catch (final Exception e) {
			WeatherMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.toString());

		}
	}

	@Test
	public void getMessageWithLowestTempTest() {

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.weather"), getKowalskyObject());

		final Source source = new SourceMock(8205, "Nmeteo.FRANCE.Orléans.weather", 3);
		new SourceMock(8206, "Nmeteo.FRANCE.Orléans.temp", WeatherMessageFactory.TEMP_MIN - 10);

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(WeatherHandler.LANGUAGE, "fr-FR");
		settings.put(WeatherHandler.UNIT, "1");
		settings.put(WeatherHandler.SOURCE, source.getSource_path());
		theSubscription.setSettings(settings);

		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.setTimeMYSQL("16:00:00");

		final WeatherMessageFactory factory = (WeatherMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(WeatherMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(Palette.RANDOM, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.WEATHER_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			final Files[] theFiles = message.getBody();
			Assert.assertEquals(4, theFiles.length);
			Assert.assertEquals("broadcast/broad/config/weather/fr/tomorrow.mp3", theFiles[0].getPath());
			Assert.assertEquals("broadcast/broad/config/weather/fr/temp/" + WeatherMessageFactory.TEMP_MIN + ".mp3", theFiles[2].getPath());

			for (final Files aFile : message.getBody()) {
				WeatherMessageFactoryTest.LOGGER.info(aFile.getPath());
			}

		} catch (final Exception e) {
			WeatherMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.toString());

		}
	}

	@Test
	public void getSourceMessageNoLastTimeTest() {
		final Source source = new SourceMock(8205, "Nmeteo.FRANCE.Orléans.weather", 3, System.currentTimeMillis());
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.weather"), getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(WeatherHandler.LANGUAGE, "fr-FR");
		settings.put(WeatherHandler.SOURCE, source.getSource_path());
		theSubscription.setSettings(settings);

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);

		final WeatherMessageFactory factory = (WeatherMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
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
		final Source source = new SourceMock(3, "Nmeteo.FRANCE.Orléans.weather", 3, sourceUpdateTime);
		final long theLastTime = sourceUpdateTime * 1000;
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.weather"), getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(WeatherHandler.LANGUAGE, "fr-FR");
		settings.put(WeatherHandler.SOURCE, source.getSource_path());
		theSubscription.setSettings(settings);

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		theScheduling.createSetting(AmbiantHandler.LAST_TIME, Long.toString(theLastTime));

		final WeatherMessageFactory factory = (WeatherMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNull(theMessage);

		source.delete();
	}

	@Test
	public void getSourceMessageWithLastTimeUTest() {
		final long sourceUpdateTime = System.currentTimeMillis() / 1000;
		final Source source = new SourceMock(8205, "Nmeteo.FRANCE.Orléans.weather", 3, sourceUpdateTime + 60);
		final long theLastTime = sourceUpdateTime * 1000;
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.weather"), getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(WeatherHandler.LANGUAGE, "fr-FR");
		settings.put(WeatherHandler.SOURCE, source.getSource_path());
		theSubscription.setSettings(settings);

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		theScheduling.createSetting(AmbiantHandler.LAST_TIME, Long.toString(theLastTime));

		final WeatherMessageFactory factory = (WeatherMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
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
