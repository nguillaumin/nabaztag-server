package net.violet.platform.daemons.crawlers.gestion;

import java.util.Calendar;
import java.util.TimeZone;

import net.violet.platform.datamodel.DBTest;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.SleepTime;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class CrawlerCheckStatusTest extends DBTest {


	private static final Logger LOGGER = Logger.getLogger(CrawlerCheckStatusTest.class);

	private static class MockCrawlerCheckStatus extends CrawlerCheckStatus {

		public MockCrawlerCheckStatus() {
			super(new String[0]);
		}

		/**
		 * Traitement pour un objet.
		 */
		@Override
		protected void processSleepObject(VObject inObject) {

			final int inState = inObject.getObject_state();
			CrawlerCheckStatusTest.LOGGER.info("Processing sleep for => " + inObject.getObject_serial() + " / " + inState);
			switch (inState) {

			case VObject.STATUS_ACTIF:
			case VObject.STATUS_WILLBE_VEILLE:
			case VObject.STATUS_WILLBE_FORCE_VEILLE:
			case VObject.STATUS_FORCE_VEILLE:
			case VObject.STATUS_WILLBE_ACTIF:
				inObject.setState(VObject.STATUS_VEILLE);
				break;
			case VObject.STATUS_WILLBE_FORCE_ACTIF:
				inObject.setState(VObject.STATUS_FORCE_ACTIF);
				break;
			}
		}

		/**
		 * Traitement pour un objet.
		 */
		@Override
		protected void processWakeUpObject(VObject inObject) {

			final int inState = inObject.getObject_state();
			CrawlerCheckStatusTest.LOGGER.info("Processing wakeup for => " + inObject.getObject_serial() + " / " + inState);
			switch (inState) {

			case VObject.STATUS_VEILLE:
			case VObject.STATUS_WILLBE_ACTIF:
			case VObject.STATUS_WILLBE_FORCE_ACTIF:
			case VObject.STATUS_FORCE_ACTIF:
			case VObject.STATUS_WILLBE_VEILLE:
				inObject.setState(VObject.STATUS_ACTIF);
				break;
			case VObject.STATUS_WILLBE_FORCE_VEILLE:
				inObject.setState(VObject.STATUS_FORCE_VEILLE);
				break;
			}
		}
	}

	private void testStatus(Timezone inTZ) {
		final Calendar now = Calendar.getInstance(TimeZone.getTimeZone(inTZ.getTimezone_javaId()));
		int nowHour = now.get(Calendar.HOUR_OF_DAY);
		int nowMinutes = now.get(Calendar.MINUTE);
		int nowHM = (nowHour * 60) + nowMinutes;

		// même heure et minute
		checkStatus(nowHour, nowHour, inTZ, nowMinutes, nowMinutes, VObject.STATUS_VEILLE, VObject.STATUS_VEILLE, 0, 0);
		checkStatus(nowHour, nowHour, inTZ, nowMinutes, nowMinutes, VObject.STATUS_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, 0, 0);
		checkStatus(nowHour, nowHour, inTZ, nowMinutes, nowMinutes, VObject.STATUS_ACTIF, VObject.STATUS_VEILLE, 0, 1);
		checkStatus(nowHour, nowHour, inTZ, nowMinutes, nowMinutes, VObject.STATUS_FORCE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
		checkStatus(nowHour, nowHour, inTZ, nowMinutes, nowMinutes, VObject.STATUS_WILLBE_ACTIF, VObject.STATUS_VEILLE, 0, 1);
		checkStatus(nowHour, nowHour, inTZ, nowMinutes, nowMinutes, VObject.STATUS_WILLBE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
		checkStatus(nowHour, nowHour, inTZ, nowMinutes, nowMinutes, VObject.STATUS_WILLBE_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, 0, 1);
		checkStatus(nowHour, nowHour, inTZ, nowMinutes, nowMinutes, VObject.STATUS_WILLBE_FORCE_VEILLE, VObject.STATUS_VEILLE, 0, 1);

		// veille la nuit
		if ((nowHM >= ((10 * 60) + 15)) || (nowHM < ((8 * 60) + 15))) {
			// Entre 10h et 8h.
			checkStatus(10, 8, inTZ, 15, 15, VObject.STATUS_ACTIF, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(10, 8, inTZ, 15, 15, VObject.STATUS_FORCE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(10, 8, inTZ, 15, 15, VObject.STATUS_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, 0, 0);
			checkStatus(10, 8, inTZ, 15, 15, VObject.STATUS_VEILLE, VObject.STATUS_VEILLE, 0, 0);
			checkStatus(10, 8, inTZ, 15, 15, VObject.STATUS_WILLBE_ACTIF, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(10, 8, inTZ, 15, 15, VObject.STATUS_WILLBE_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, 0, 1);
			checkStatus(10, 8, inTZ, 15, 15, VObject.STATUS_WILLBE_FORCE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(10, 8, inTZ, 15, 15, VObject.STATUS_WILLBE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
		}

		nowHour = now.get(Calendar.HOUR_OF_DAY);
		nowMinutes = now.get(Calendar.MINUTE);
		nowHM = (nowHour * 60) + nowMinutes;
		// Test des minutes veille de HHh20 à HHh10
		if ((nowMinutes < 10) || (nowMinutes > 20)) {
			checkStatus(nowHour, nowHour, inTZ, 10, 20, VObject.STATUS_ACTIF, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(nowHour, nowHour, inTZ, 10, 20, VObject.STATUS_FORCE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(nowHour, nowHour, inTZ, 10, 20, VObject.STATUS_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, 0, 0);
			checkStatus(nowHour, nowHour, inTZ, 10, 20, VObject.STATUS_VEILLE, VObject.STATUS_VEILLE, 0, 0);
			checkStatus(nowHour, nowHour, inTZ, 10, 20, VObject.STATUS_WILLBE_ACTIF, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(nowHour, nowHour, inTZ, 10, 20, VObject.STATUS_WILLBE_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, 0, 1);
			checkStatus(nowHour, nowHour, inTZ, 10, 20, VObject.STATUS_WILLBE_FORCE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(nowHour, nowHour, inTZ, 10, 20, VObject.STATUS_WILLBE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
		}

		// veille le jour
		if ((nowHM >= ((8 * 60) + 15)) && (nowHM < ((20 * 60) + 15))) {
			// Entre 8h et 20h.
			checkStatus(8, 20, inTZ, 15, 15, VObject.STATUS_ACTIF, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(8, 20, inTZ, 15, 15, VObject.STATUS_VEILLE, VObject.STATUS_VEILLE, 0, 0);
			checkStatus(8, 20, inTZ, 15, 15, VObject.STATUS_FORCE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(8, 20, inTZ, 15, 15, VObject.STATUS_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, 0, 0);
			checkStatus(8, 20, inTZ, 15, 15, VObject.STATUS_WILLBE_ACTIF, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(8, 20, inTZ, 15, 15, VObject.STATUS_WILLBE_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, 0, 1);
			checkStatus(8, 20, inTZ, 15, 15, VObject.STATUS_WILLBE_FORCE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(8, 20, inTZ, 15, 15, VObject.STATUS_WILLBE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
		}

		nowHour = now.get(Calendar.HOUR_OF_DAY);
		nowMinutes = now.get(Calendar.MINUTE);
		nowHM = (nowHour * 60) + nowMinutes;
		// Test des minutes. veille de quelque minutes entre HHh10 et HHh50
		if ((nowMinutes > 10) && (nowMinutes < 50)) {
			checkStatus(nowHour, nowHour, inTZ, 50, 10, VObject.STATUS_ACTIF, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(nowHour, nowHour, inTZ, 50, 10, VObject.STATUS_VEILLE, VObject.STATUS_VEILLE, 0, 0);
			checkStatus(nowHour, nowHour, inTZ, 50, 10, VObject.STATUS_FORCE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(nowHour, nowHour, inTZ, 50, 10, VObject.STATUS_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, 0, 0);
			checkStatus(nowHour, nowHour, inTZ, 50, 10, VObject.STATUS_WILLBE_ACTIF, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(nowHour, nowHour, inTZ, 50, 10, VObject.STATUS_WILLBE_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, 0, 1);
			checkStatus(nowHour, nowHour, inTZ, 50, 10, VObject.STATUS_WILLBE_FORCE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
			checkStatus(nowHour, nowHour, inTZ, 50, 10, VObject.STATUS_WILLBE_VEILLE, VObject.STATUS_VEILLE, 0, 1);
		}

		// active le jour
		if ((nowHM >= ((7 * 60) + 15)) && (nowHM < ((20 * 60) + 15))) {
			// Entre 7h et 20h.
			checkStatus(21, 7, inTZ, 15, 15, VObject.STATUS_ACTIF, VObject.STATUS_ACTIF, 0, 0);
			checkStatus(21, 7, inTZ, 15, 15, VObject.STATUS_FORCE_ACTIF, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(21, 7, inTZ, 15, 15, VObject.STATUS_VEILLE, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(21, 7, inTZ, 15, 15, VObject.STATUS_FORCE_VEILLE, VObject.STATUS_FORCE_VEILLE, 0, 0);
			checkStatus(21, 7, inTZ, 15, 15, VObject.STATUS_WILLBE_ACTIF, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(21, 7, inTZ, 15, 15, VObject.STATUS_WILLBE_FORCE_ACTIF, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(21, 7, inTZ, 15, 15, VObject.STATUS_WILLBE_FORCE_VEILLE, VObject.STATUS_FORCE_VEILLE, 1, 0);
			checkStatus(21, 7, inTZ, 15, 15, VObject.STATUS_WILLBE_VEILLE, VObject.STATUS_ACTIF, 1, 0);
		}

		nowHour = now.get(Calendar.HOUR_OF_DAY);
		nowMinutes = now.get(Calendar.MINUTE);
		nowHM = (nowHour * 60) + nowMinutes;
		// Test des minutes. active que quelque minutes
		if ((nowMinutes > 10) && (nowMinutes < 50)) {
			checkStatus(nowHour, nowHour, inTZ, 8, 55, VObject.STATUS_FORCE_ACTIF, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(nowHour, nowHour, inTZ, 8, 55, VObject.STATUS_VEILLE, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(nowHour, nowHour, inTZ, 8, 55, VObject.STATUS_FORCE_VEILLE, VObject.STATUS_FORCE_VEILLE, 0, 0);
			checkStatus(nowHour, nowHour, inTZ, 8, 55, VObject.STATUS_ACTIF, VObject.STATUS_ACTIF, 0, 0);
			checkStatus(nowHour, nowHour, inTZ, 8, 55, VObject.STATUS_WILLBE_ACTIF, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(nowHour, nowHour, inTZ, 8, 55, VObject.STATUS_WILLBE_FORCE_ACTIF, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(nowHour, nowHour, inTZ, 8, 55, VObject.STATUS_WILLBE_FORCE_VEILLE, VObject.STATUS_FORCE_VEILLE, 1, 0);
			checkStatus(nowHour, nowHour, inTZ, 8, 55, VObject.STATUS_WILLBE_VEILLE, VObject.STATUS_ACTIF, 1, 0);
		}

		// active dans la nuit
		if ((nowHM >= ((10 * 60))) || (nowHM < ((5 * 60)))) {
			// Entre 5h et 10h.
			checkStatus(5, 10, inTZ, 0, 0, VObject.STATUS_VEILLE, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(5, 10, inTZ, 0, 0, VObject.STATUS_FORCE_VEILLE, VObject.STATUS_FORCE_VEILLE, 0, 0);
			checkStatus(5, 10, inTZ, 0, 0, VObject.STATUS_FORCE_ACTIF, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(5, 10, inTZ, 0, 0, VObject.STATUS_ACTIF, VObject.STATUS_ACTIF, 0, 0);
			checkStatus(5, 10, inTZ, 0, 0, VObject.STATUS_WILLBE_ACTIF, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(5, 10, inTZ, 0, 0, VObject.STATUS_WILLBE_FORCE_ACTIF, VObject.STATUS_ACTIF, 1, 0);
			checkStatus(5, 10, inTZ, 0, 0, VObject.STATUS_WILLBE_FORCE_VEILLE, VObject.STATUS_FORCE_VEILLE, 1, 0);
			checkStatus(5, 10, inTZ, 0, 0, VObject.STATUS_WILLBE_VEILLE, VObject.STATUS_ACTIF, 1, 0);
		}
	}

	private void checkStatus(int after, int before, Timezone timezone, int beforemin, int aftermin, int inState, int inNewState, int inActiveProcessedCount, int inAsleepProcessedCount) {
		final SleepTime theSleepTime = new SleepTime(before, beforemin, after, aftermin, before, beforemin, after, aftermin);
		final VObject theObject = Factories.VOBJECT.find(31L);
		theObject.setSleepTime(theSleepTime, false);
		theObject.setState(inState);
		theObject.setTimeZone(timezone);
		final MockCrawlerCheckStatus theCrawler = new MockCrawlerCheckStatus();
		final int nbProcessedActive = theCrawler.process(timezone, true);
		final int nbProcessedAsleep = theCrawler.process(timezone, false);
		Assert.assertEquals(inNewState, theObject.getObject_state());

		Assert.assertEquals(inActiveProcessedCount, nbProcessedActive);
		Assert.assertEquals(inAsleepProcessedCount, nbProcessedAsleep);
	}

	@Test
	public void checkStatus() {
		final Timezone timeZone = Factories.TIMEZONE.findByJavaId("Europe/Paris");

//		Factories.TIMEZONE.walkDistincts(new Record.RecordWalker<Timezone>() {
//
//			public void process(Timezone timeZone) {
		testStatus(timeZone);
//			}
//		});
	}
}
