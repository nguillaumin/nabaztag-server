package net.violet.platform.datamodel;

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SleepTime;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class VeilleTest extends DBTest {

	private static final Logger LOGGER = Logger.getLogger(VeilleTest.class);

	@Test
	public void testVeilleAllTimezone() throws ParseException {
		// Pour que les tests passent plus vite.
		// for (Timezone timeZoneJavaId : Factories.TIMEZONE.findAll()){
		testVeille(Factories.TIMEZONE.findByJavaId("America/Los_Angeles"));
		// }
	}

	/**
	 * Test de la veille sur un fuseau donné.
	 * 
	 * @throws ParseException
	 */
	private void testVeille(Timezone inTZ) throws ParseException {

		CCalendar now = new CCalendar(false, TimeZone.getTimeZone(inTZ.getTimezone_javaId()));
		int nowHour = now.getHour();
		int nowMinutes = now.getMinute();
		int nowHM = (nowHour * 60) + nowMinutes;
		final VObject theObject = Factories.VOBJECT.find(31L);
		theObject.setTimeZone(inTZ);

		VeilleTest.LOGGER.info("NOW => timezone: " + inTZ + " h: " + nowHour + " - m: " + nowMinutes);
		// Cas où le lapin dort la nuit.

		if ((nowHM >= ((20 * 60) + 15)) || (nowHM < ((10 * 60) + 15))) {
			// Entre 20h et 10h.
			Assert.assertEquals(true, checkSleep(theObject, "20:15", "10:15"));
		} else {
			Assert.assertEquals(false, checkSleep(theObject, "20:15", "10:15"));
		}
		if ((nowHM >= ((13 * 60) + 15)) || (nowHM < ((12 * 60) + 15))) {
			// Entre 13h et 12h.
			Assert.assertEquals(true, checkSleep(theObject, "13:15", "12:15"));
		} else {
			Assert.assertEquals(false, checkSleep(theObject, "13:15", "12:15"));
		}
		if ((nowHM >= ((21 * 60) + 15)) || (nowHM < ((9 * 60) + 15))) {
			// Entre 21h et 9h.
			Assert.assertEquals(true, checkSleep(theObject, "21:15", "09:15"));
		} else {
			Assert.assertEquals(false, checkSleep(theObject, "21:15", "09:15"));
		}

		if ((nowHM >= ((19 * 60) + 15)) || (nowHM < ((11 * 60) + 15))) {
			// Entre 19h et 11h.
			Assert.assertEquals(true, checkSleep(theObject, "19:15", "11:15"));
		} else {
			Assert.assertEquals(false, checkSleep(theObject, "19:15", "11:15"));
		}

		now = new CCalendar(false, now.getTimeZone());
		nowHour = now.get(Calendar.HOUR_OF_DAY);
		nowMinutes = now.get(Calendar.MINUTE);
		final int nowSecondes = now.get(Calendar.SECOND);
		nowHM = (nowHour * 60) + nowMinutes;

		// Test des minutes.
		if ((15 < nowMinutes) && (50 > nowMinutes) && (nowSecondes < 10)) {
			Assert.assertEquals(true, checkSleep(theObject, nowHour + ":15", nowHour + ":50"));
		} else if (((nowMinutes < 15) || (nowMinutes > 50)) && (nowSecondes < 10)) {
			Assert.assertEquals(false, checkSleep(theObject, nowHour + ":15", nowHour + ":50"));
		}

		// Cas où le lapin dort le jour.
		if ((nowHM >= ((10 * 60) + 15)) && (nowHM < ((20 * 60) + 15))) {
			// Entre 10h et 20h.
			Assert.assertEquals(true, checkSleep(theObject, "10:15", "20:15"));
		} else {
			Assert.assertEquals(false, checkSleep(theObject, "10:15", "20:15"));
		}

		if ((nowHM >= ((12 * 60) + 15)) && (nowHM < ((13 * 60) + 15))) {
			// Entre 12h et 13h.
			Assert.assertEquals(true, checkSleep(theObject, "12:15", "13:15"));
		} else {
			Assert.assertEquals(false, checkSleep(theObject, "12:15", "13:15"));
		}

		if ((nowHM >= ((9 * 60) + 15)) && (nowHM < ((21 * 60) + 15))) {
			// Entre 9h et 21h.
			Assert.assertEquals(true, checkSleep(theObject, "09:15", "21:15"));
		} else {
			Assert.assertEquals(false, checkSleep(theObject, "09:15", "21:15"));
		}

		if ((nowHM >= ((11 * 60) + 15)) && (nowHM < ((19 * 60) + 15))) {
			// Entre 11h et 19h.
			Assert.assertEquals(true, checkSleep(theObject, "11:15", "19:15"));
		} else {
			Assert.assertEquals(false, checkSleep(theObject, "11:15", "19:15"));
		}

		now = new CCalendar(false, now.getTimeZone());
		nowHour = now.get(Calendar.HOUR_OF_DAY);
		nowMinutes = now.get(Calendar.MINUTE);
		nowHM = (nowHour * 60) + nowMinutes;

		// Sieste.
		if ((nowHM >= ((14 * 60) + 35)) && (nowHM < ((14 * 60) + 50))) {
			// Entre 14h35 et 14h50.
			Assert.assertEquals(true, checkSleep(theObject, "14:35", "14:50"));
		} else {
			Assert.assertEquals(false, checkSleep(theObject, "14:35", "14:50"));
		}

	}

	private boolean checkSleep(VObject inObject, String sleepW, String wakeW) throws ParseException {
		final SleepTime theSleepTime = new SleepTime(wakeW, sleepW, wakeW, sleepW);
		inObject.setSleepTime(theSleepTime);
		return ObjectSleep.ObjectSleepCommon.asleep(inObject);
	}
}
