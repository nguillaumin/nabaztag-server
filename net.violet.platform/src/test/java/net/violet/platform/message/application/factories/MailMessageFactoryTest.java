package net.violet.platform.message.application.factories;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.applications.AirHandler;
import net.violet.platform.daemons.crawlers.source.CrawlerSourceMail;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.SourceMock;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
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

public class MailMessageFactoryTest extends MockTestBase {

	private static final Logger LOGGER = Logger.getLogger(MailMessageFactoryTest.class);

	@Test
	public void getMessageWithoutContentTest() {

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.mail"), getKowalskyObject());
		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.addMillis(3600000L);

		final MailMessageFactory factory = (MailMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		try {
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
			Assert.assertEquals(MailMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(MailMessageFactory.TITLE, message.getTitle());
			Assert.assertEquals(CrawlerSourceMail.MAIL_COLOR_PAL, message.getColorPal());
			Assert.assertEquals(theSubscriptionData, message.getSubscription());
			Assert.assertEquals(MessageSignature.MAIL_SIGNATURE, message.getSignature());
			Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
			Assert.assertNull(message.getBody());
			Assert.assertTrue(message.isStream());

		} catch (final Exception e) {
			MailMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.getMessage());

		}
	}

	// FIXME
	public void getMessageWithSourceTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.mail"), getKowalskyObject());
		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.addMillis(3600000L);

		final MailMessageFactory factory = (MailMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		MusicMock theMusic = null;
		try {
			theMusic = new MusicMock(0, new FilesMock("thePath2Mp3", MimeType.MIME_TYPES.A_MPEG), "TheMusicTest", getKowalskyUser(), MusicData.MimeTypes.getMusicTypeFromFileMimeType(MimeType.MIME_TYPES.A_MPEG));
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			// SubscriptionSchedulingSettingsData.create(theScheduling,
			// SubscriptionSchedulingSettings.Mail.NEW_CONTENT_MEDIA,
			// theMusic.getId().toString());
			final Message2Send theMessage = factory.getMessage(new MessageProcessUnit(theScheduling, theDeliveryDate, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, theMessage.getTTL());
			Assert.assertEquals(MailMessageFactory.TITLE, theMessage.getTitle());
			Assert.assertEquals(MailMessageFactory.TITLE, theMessage.getTitle());
			Assert.assertEquals(CrawlerSourceMail.MAIL_COLOR_PAL, theMessage.getColorPal());
			Assert.assertEquals(theSubscriptionData, theMessage.getSubscription());
			Assert.assertEquals(MessageSignature.MAIL_SIGNATURE, theMessage.getSignature());
			Assert.assertEquals(theSubscription.getObject(), theMessage.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, theMessage.getMode());
			Assert.assertEquals(theDeliveryDate, theMessage.getDeliveryDate());
			Assert.assertTrue(theMessage.isStream());
			final Files[] theBody = theMessage.getBody();
			Assert.assertNotNull(theBody);
			Assert.assertEquals(1, theBody.length);
			Assert.assertEquals(theMusic.getFile(), theBody[0]);

		} catch (final Exception e) {
			MailMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.getMessage());
		} finally {
			if (theMusic != null) {
				theMusic.delete();
			}
		}
	}

	// FIXME
	public void getMessageWithoutDeliveryTest() {
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.mail"), getKowalskyObject());

		final MailMessageFactory factory = (MailMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		MusicMock theMusic = null;
		try {
			theMusic = new MusicMock(0, new FilesMock("thePath2Mp3", MimeType.MIME_TYPES.A_MPEG), "TheMusicTest", getKowalskyUser(), MusicData.MimeTypes.getMusicTypeFromFileMimeType(MimeType.MIME_TYPES.A_MPEG));
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
			// SubscriptionSchedulingSettingsData.create(theScheduling,
			// SubscriptionSchedulingSettings.Mail.NEW_CONTENT_MEDIA,
			// theMusic.getId().toString());
			final Message2Send theMessage = factory.getMessage(new MessageProcessUnit(theScheduling, null, null) {

				@Override
				public void runWhenSuccessful() {
				}
			}).get(0);

			Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, theMessage.getTTL());
			Assert.assertEquals(MailMessageFactory.TITLE, theMessage.getTitle());
			Assert.assertEquals(MailMessageFactory.TITLE, theMessage.getTitle());
			Assert.assertEquals(CrawlerSourceMail.MAIL_COLOR_PAL, theMessage.getColorPal());
			Assert.assertEquals(theSubscriptionData, theMessage.getSubscription());
			Assert.assertEquals(MessageSignature.MAIL_SIGNATURE, theMessage.getSignature());
			Assert.assertEquals(theSubscription.getObject(), theMessage.getRecipient());
			Assert.assertEquals(JabberMessageFactory.IDLE_MODE, theMessage.getMode());
			Assert.assertTrue(theMessage.isStream());
			Assert.assertNull(theMessage.getDeliveryDate());
			final Files[] theBody = theMessage.getBody();
			Assert.assertNotNull(theBody);
			Assert.assertEquals(1, theBody.length);
			Assert.assertEquals(theMusic.getFile(), theBody[0]);

		} catch (final Exception e) {
			MailMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.getMessage());
		} finally {
			if (theMusic != null) {
				theMusic.delete();
			}
		}
	}

	@Test
	public void getSourceMessageWithLastTimeNUTest() {
		final long sourceUpdateTime = System.currentTimeMillis() / 1000;
		final Source source = new SourceMock(3, "$83020.pop", 3, sourceUpdateTime);

		final long theLastTime = sourceUpdateTime * 1000;
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.mail"), getKowalskyObject());

		// Creates the settings
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		theSettings.put(AirHandler.SOURCE_SETTING, source.getSource_path());
		theSubscription.setSettings(theSettings);

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		theScheduling.createSetting(AmbiantHandler.LAST_TIME, Long.toString(theLastTime));

		final MailMessageFactory factory = (MailMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNull(theMessage);

		source.delete();

	}

	@Test
	public void getSourceMessageWithLastTimeUTest() {
		final long sourceUpdateTime = System.currentTimeMillis() / 1000;
		final Source source = new SourceMock(8205, "$83020.pop", 3, sourceUpdateTime + 60);

		final long theLastTime = sourceUpdateTime * 1000;
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.mail"), getKowalskyObject());

		// Creates the settings
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		theSettings.put(AirHandler.SOURCE_SETTING, source.getSource_path());
		theSubscription.setSettings(theSettings);

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		theScheduling.createSetting(AmbiantHandler.LAST_TIME, Long.toString(theLastTime));

		final MailMessageFactory factory = (MailMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
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
