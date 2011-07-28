package net.violet.platform.datamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class TimezoneTest extends DBTest {

	@Test
	public void testExistingRecords() {
		final Timezone myRecord = Factories.TIMEZONE.find(1);
		Assert.assertEquals(1, myRecord.getTimezone_id());
		Assert.assertEquals("Etc/GMT+12", myRecord.getTimezone_javaId());
		Assert.assertEquals("LOC_timezone/dateInternationale", myRecord.getTimezone_name());
	}

	@Test
	public void testFindByJavaId() {
		Timezone theTimezone = Factories.TIMEZONE.findByJavaId("America/Los_Angeles");
		Assert.assertEquals(5L, theTimezone.getId().longValue());
		theTimezone = Factories.TIMEZONE.findByJavaId("Europe/Paris");
		Assert.assertEquals(30L, theTimezone.getId().longValue());
		theTimezone = Factories.TIMEZONE.findByJavaId("Africa/Lusaka");
		Assert.assertNotNull(theTimezone);
	}

	@Test
	public void testFindAllCodes() {
		final List<String> names = Factories.TIMEZONE.findAllNames();
		final List<String> namesIBM = Arrays.asList(com.ibm.icu.util.TimeZone.getAvailableIDs());

		Collections.sort(namesIBM, new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.toLowerCase().compareTo(o2.toLowerCase());
			}
		});

		Assert.assertEquals(namesIBM.get(0), names.get(0));
		Assert.assertEquals(namesIBM.get(1), names.get(1));
		Assert.assertEquals(namesIBM.get(2), names.get(2));
		Assert.assertEquals(namesIBM.get(3), names.get(3));
		Assert.assertEquals(namesIBM.get(4), names.get(4));
		Assert.assertEquals(namesIBM.get(5), names.get(5));
		Assert.assertEquals(namesIBM.get(6), names.get(6));
		int i = 0;
		for (final String nomZone : names) { // certaines zones ne sont pas assignées à n'aucun pays
			while (namesIBM.get(i).equals("CET") || namesIBM.get(i).equals("CST6CDT") || namesIBM.get(i).equals("EET") || namesIBM.get(i).equals("EST") || namesIBM.get(i).equals("EST5EDT") || namesIBM.get(i).equals("Etc/GMT+11") || namesIBM.get(i).equals("Factory") || namesIBM.get(i).equals("Greenwich") || namesIBM.get(i).equals("HST") || namesIBM.get(i).equals("MET") || namesIBM.get(i).equals("MST") || namesIBM.get(i).equals("PST8PDT") || namesIBM.get(i).equals("SystemV/AST4") || namesIBM.get(i).equals("SystemV/CST6") || namesIBM.get(i).equals("SystemV/EST5") || namesIBM.get(i).equals("SystemV/HST10") || namesIBM.get(i).equals("SystemV/MST7") || namesIBM.get(i).equals("SystemV/PST8") || namesIBM.get(i).equals("SystemV/YST9") || namesIBM.get(i).equals("UCT")
					|| namesIBM.get(i).equals("Universal") || namesIBM.get(i).equals("UTC")) {
				i++;
			}
			while (namesIBM.get(i).startsWith("GMT") || (namesIBM.get(i).startsWith("Etc/") && !namesIBM.get(i).equals("Etc/GMT+12")) || namesIBM.get(i).startsWith("Greenwich") || namesIBM.get(i).startsWith("MST")) {
				i++;
			}
			while (namesIBM.get(i).endsWith("Riyadh87") || namesIBM.get(i).endsWith("Riyadh88") || namesIBM.get(i).endsWith("Riyadh89")) {
				i++;
			}
			if (!(namesIBM.get(i).equals("EST5EDT") || namesIBM.get(i).equals("EST5EDT") || namesIBM.get(i).equals("SystemV/AST4ADT"))) {
				Assert.assertEquals(namesIBM.get(i), nomZone);
			}
			i++;
		}

	}

	@Test
	public void testTimeZoneOffSetAndDST() {

		final CCalendar theCalendar1 = new CCalendar(1230562137491L, TimeZone.getTimeZone("Europe/Paris"));//Winter (29th of December) GMT+1 uses DST

		final CCalendar theCalendar2 = new CCalendar(1220017737491L, TimeZone.getTimeZone("Europe/Paris"));//Summer (29th of August) GMT+1 uses DST

		Assert.assertEquals(3600000, theCalendar2.getTimeZone().getRawOffset());
		Assert.assertEquals(theCalendar1.getTimeZone().getRawOffset(), theCalendar2.getTimeZone().getRawOffset());

		Assert.assertTrue(theCalendar1.getTimeZone().useDaylightTime());
		Assert.assertEquals(theCalendar1.getTimeZone().useDaylightTime(), theCalendar2.getTimeZone().useDaylightTime());
	}
}
