package net.violet.platform.util;

import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

public class CCalendarTest extends MockTestBase {

	@Test
	public void testMYSQLFormat() {
		final CCalendar theCCalendar = new CCalendar(false);
		theCCalendar.setTimeMYSQL(StringShop.MIDNIGHT);
		Assert.assertEquals(StringShop.MIDNIGHT, theCCalendar.getTimeFormated(true));
		theCCalendar.setTimeMYSQL("24:00:00");
		Assert.assertEquals(StringShop.MIDNIGHT, theCCalendar.getTimeFormated(true));
		theCCalendar.setTimeMYSQL("168:00:00");
		Assert.assertEquals(StringShop.MIDNIGHT, theCCalendar.getTimeFormated(true));
		theCCalendar.setTimeMYSQL("00:20:00");
		Assert.assertEquals("00:20:00", theCCalendar.getTimeFormated(true));
	}

}
