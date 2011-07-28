package net.violet.platform.schedulers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class DailyHandlerTest extends MockTestBase {

	@Test(expected = MissingSettingException.class)
	public void emptySettingsTest() throws MissingSettingException, InvalidSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		new DailyHandler().checkSettings(theObject, Collections.<String, Object> emptyMap(), null);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidDayTest() throws MissingSettingException, InvalidSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("invalid", null);

		new DailyHandler().checkSettings(theObject, settings, null);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidAtomTest() throws MissingSettingException, InvalidSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put("toto", "toto");
		atom.put("titi", "titi");
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);

		new DailyHandler().checkSettings(theObject, settings, null);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidAtomAgainTest() throws MissingSettingException, InvalidSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 28);
		atom.put(SchedulingAtom.MINUTE, -4);
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);

		new DailyHandler().checkSettings(theObject, settings, null);
	}

	@Test
	public void validTest() {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 4);
		final Map<String, Object> atom2 = new HashMap<String, Object>();
		atom2.put(SchedulingAtom.HOUR, 14);
		atom2.put(SchedulingAtom.MINUTE, 45);
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);
		settings.put(DailyHandler.Weekday.WEDNESDAY.getValue(), atom2);

		try {
			new DailyHandler().checkSettings(theObject, settings, null);
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
		final Map<String, Object> atom2 = new HashMap<String, Object>();
		atom2.put(SchedulingAtom.HOUR, 14);
		atom2.put(SchedulingAtom.MINUTE, 45);
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);
		settings.put(DailyHandler.Weekday.WEDNESDAY.getValue(), atom2);

		final Map<String, String> theSettings = new DailyHandler().generateSettings(theObject, settings, null);
		Assert.assertEquals(2, theSettings.size());
		Assert.assertEquals("12:04:00", theSettings.get(DailyHandler.Weekday.MONDAY.getValue()));
		Assert.assertEquals("14:45:00", theSettings.get(DailyHandler.Weekday.WEDNESDAY.getValue()));
	}

}
