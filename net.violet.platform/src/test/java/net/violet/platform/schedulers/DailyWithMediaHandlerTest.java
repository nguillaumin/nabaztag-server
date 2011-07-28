package net.violet.platform.schedulers;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class DailyWithMediaHandlerTest extends MockTestBase {

	@Test(expected = InvalidSettingException.class)
	public void invalidAtomWithoutMediaTest() throws MissingSettingException, InvalidSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 48);
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);

		new DailyWithMediaHandler().checkSettings(theObject, settings, null);
	}

	@Test
	public void validTest() {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());
		final MusicData theMedia = MusicData.getData(new MusicMock(0, new FilesMock("path", MIME_TYPES.A_MPEG), net.violet.common.StringShop.EMPTY_STRING, getKowalskyUser(), 0));

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 4);
		atom.put(SchedulingAtom.MEDIA, theMedia.getId());
		final Map<String, Object> atom2 = new HashMap<String, Object>();
		atom2.put(SchedulingAtom.HOUR, 14);
		atom2.put(SchedulingAtom.MINUTE, 45);
		atom2.put(SchedulingAtom.MEDIA, theMedia.getId());
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);
		settings.put(DailyHandler.Weekday.WEDNESDAY.getValue(), atom2);

		try {
			new DailyWithMediaHandler().checkSettings(theObject, settings, null);
		} catch (final Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void getSettingsTest() {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());
		final MusicData theMedia = MusicData.getData(new MusicMock(0, new FilesMock("path", MIME_TYPES.A_MPEG), net.violet.common.StringShop.EMPTY_STRING, getKowalskyUser(), 0));

		final Map<String, Object> settings = new HashMap<String, Object>();
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 4);
		atom.put(SchedulingAtom.MEDIA, String.valueOf(theMedia.getId()));
		final Map<String, Object> atom2 = new HashMap<String, Object>();
		atom2.put(SchedulingAtom.HOUR, 14);
		atom2.put(SchedulingAtom.MINUTE, 45);
		atom2.put(SchedulingAtom.MEDIA, String.valueOf(theMedia.getId()));
		settings.put(DailyHandler.Weekday.MONDAY.getValue(), atom);
		settings.put(DailyHandler.Weekday.WEDNESDAY.getValue(), atom2);

		final Map<String, String> theSettings = new DailyWithMediaHandler().generateSettings(theObject, settings, null);
		Assert.assertEquals(4, theSettings.size());
		Assert.assertEquals("12:04:00", theSettings.get(DailyHandler.Weekday.MONDAY.getValue()));
		Assert.assertEquals(String.valueOf(theMedia.getId()), theSettings.get(DailyHandler.Weekday.MONDAY.getValue() + ".media"));
		Assert.assertEquals("14:45:00", theSettings.get(DailyHandler.Weekday.WEDNESDAY.getValue()));
		Assert.assertEquals(String.valueOf(theMedia.getId()), theSettings.get(DailyHandler.Weekday.WEDNESDAY.getValue() + ".media"));
	}

}
