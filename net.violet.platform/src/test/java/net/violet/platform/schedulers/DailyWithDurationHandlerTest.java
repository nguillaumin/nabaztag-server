package net.violet.platform.schedulers;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class DailyWithDurationHandlerTest extends MockTestBase {

	@Test(expected = InvalidSettingException.class)
	public void invalidAtomTest() throws MissingSettingException, InvalidSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 48);
		atom.put(SchedulingAtom.DURATION, "io");
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);

		new DailyWithDurationHandler().checkSettings(theObject, settings, null);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidAtomAgainTest() throws MissingSettingException, InvalidSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 48);
		atom.put(SchedulingAtom.DURATION, -4);
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);

		new DailyWithDurationHandler().checkSettings(theObject, settings, null);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidAtomWithoutDurationTest() throws MissingSettingException, InvalidSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 48);
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);

		new DailyWithDurationHandler().checkSettings(theObject, settings, null);
	}

	@Test
	public void validTest() {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 4);
		atom.put(SchedulingAtom.DURATION, 50);
		final Map<String, Object> atom2 = new HashMap<String, Object>();
		atom2.put(SchedulingAtom.HOUR, 14);
		atom2.put(SchedulingAtom.MINUTE, 45);
		atom2.put(SchedulingAtom.DURATION, 28);
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);
		settings.put(DailyHandler.Weekday.WEDNESDAY.getValue(), atom2);

		try {
			new DailyWithDurationHandler().checkSettings(theObject, settings, null);
		} catch (final Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void getSettingsTest() {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 4);
		atom.put(SchedulingAtom.DURATION, 50);
		final Map<String, Object> atom2 = new HashMap<String, Object>();
		atom2.put(SchedulingAtom.HOUR, 14);
		atom2.put(SchedulingAtom.MINUTE, 45);
		atom2.put(SchedulingAtom.DURATION, 28);
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);
		settings.put(DailyHandler.Weekday.WEDNESDAY.getValue(), atom2);

		final Map<String, String> theSettings = new DailyWithDurationHandler().generateSettings(theObject, settings, null);
		Assert.assertEquals(4, theSettings.size());
		Assert.assertEquals("12:04:00", theSettings.get(DailyHandler.Weekday.MONDAY.getValue()));
		Assert.assertEquals("50", theSettings.get(DailyHandler.Weekday.MONDAY.getValue() + ".duration"));
		Assert.assertEquals("14:45:00", theSettings.get(DailyHandler.Weekday.WEDNESDAY.getValue()));
		Assert.assertEquals("28", theSettings.get(DailyHandler.Weekday.WEDNESDAY.getValue() + ".duration"));
	}

}
