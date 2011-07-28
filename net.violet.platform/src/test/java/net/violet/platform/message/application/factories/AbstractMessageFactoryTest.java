package net.violet.platform.message.application.factories;

import java.util.Map;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Application.NativeApplication;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class AbstractMessageFactoryTest extends MockTestBase {

	private static final Logger LOGGER = Logger.getLogger(AbstractMessageFactoryTest.class);

	private static void initNativeApplication() {
		NativeApplication.values();
	}

	@Test
	public void checkMessageFactoryMap() {
		AbstractMessageFactoryTest.initNativeApplication();

		try {
			final Map<NativeApplication, AbstractMessageFactory> theMap = (Map<NativeApplication, AbstractMessageFactory>) AbstractMessageFactory.class.getDeclaredField("MESSAGE_FACTORIES").get(null);
			Assert.assertEquals(17, theMap.size());
		} catch (final Exception e) {
			AbstractMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void getMessageFactoryTest() {
		AbstractMessageFactoryTest.initNativeApplication();
		AbstractMessageFactory theFactory = null;

		try {
			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.WEATHER.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(WeatherMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.AIR.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(AirMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.RSS_FULL.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(VActionMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.PODCAST_FULL.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(VActionMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Factories.APPLICATION.findByName("net.violet.rss.bbc_technology"));
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(VActionMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Factories.APPLICATION.findByName("net.violet.podcast.this_american_lifedsdsffshdxfsdffssfdfsdfsxdf"));
			Assert.assertNull(theFactory);

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Factories.APPLICATION.findByName("net.violet.podcast.this_american_life"));
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(VActionMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.BILAN.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(BilanMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.BOURSE_FREE.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(TradeMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.BOURSE_FULL.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(TradeMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.MOOD.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(MoodMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.TRAFIC.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(TraficMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Factories.APPLICATION.findByName("net.violet.webradio.U105"));
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(WebRadioMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.ALARM_CLOCK.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(AlarmsMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.REMINDER.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(AlarmsMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.CLOCK.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(ClockMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.MAIL.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(MailMessageFactory.class, theFactory.getClass());

			theFactory = (AbstractMessageFactory) AbstractMessageFactory.class.getDeclaredMethod("getFactoryByApplication", Application.class).invoke(null, Application.NativeApplication.TAICHI.getApplication());
			Assert.assertNotNull(theFactory);
			Assert.assertEquals(TaichiMessageFactory.class, theFactory.getClass());

		} catch (final Exception e) {
			AbstractMessageFactoryTest.LOGGER.fatal(e, e);
			Assert.fail(e.getMessage());
		}
	}

}
