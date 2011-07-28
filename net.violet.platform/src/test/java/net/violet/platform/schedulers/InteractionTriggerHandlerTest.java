package net.violet.platform.schedulers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class InteractionTriggerHandlerTest extends MockTestBase {

	@Test(expected = MissingSettingException.class)
	public void missingEventTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("aSetting", "niceValue");
		settings.put("anotherOne", "prettyValue");

		new InteractionTriggerHandler().checkSettings(theObject, settings, null);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidTargetTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(InteractionTriggerHandler.EVENT, "anEvent");
		settings.put(InteractionTriggerHandler.TARGET, "42");
		new InteractionTriggerHandler().checkSettings(theObject, settings, null);
	}

	@Test(expected = InvalidSettingException.class)
	public void notOwningTheTargetTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());
		final VObjectData theTarget = VObjectData.getData(getPrivateObject());

		Assert.assertFalse(theObject.getOwner().equals(theTarget.getOwner()));

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(InteractionTriggerHandler.EVENT, "anEvent");
		settings.put(InteractionTriggerHandler.TARGET, theTarget.getId());
		new InteractionTriggerHandler().checkSettings(theObject, settings, null);
	}

	@Test(expected = InvalidSettingException.class)
	public void validityWithInvalidDayTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(InteractionTriggerHandler.EVENT, "anEvent");

		final Map<String, Object> theValidity = new HashMap<String, Object>();
		theValidity.put("invalidDay", Collections.emptyList());
		settings.put(InteractionTriggerHandler.VALIDITY, theValidity);

		new InteractionTriggerHandler().checkSettings(theObject, settings, null);
	}

	@Test(expected = InvalidSettingException.class)
	public void validityWithMissingToTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(InteractionTriggerHandler.EVENT, "anEvent");

		final Map<String, Object> theValidity = new HashMap<String, Object>();
		final Map<String, Object> mondayValidity = new HashMap<String, Object>();
		mondayValidity.put("titi", Collections.emptyList());
		mondayValidity.put("from", Collections.emptyList());
		theValidity.put(DailyHandler.Weekday.MONDAY.getValue(), mondayValidity);
		settings.put(InteractionTriggerHandler.VALIDITY, theValidity);

		new InteractionTriggerHandler().checkSettings(theObject, settings, null);
	}

	@Test(expected = InvalidSettingException.class)
	public void validityWithInvalidFromToTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(InteractionTriggerHandler.EVENT, "anEvent");

		final Map<String, Object> theValidity = new HashMap<String, Object>();
		final Map<String, Object> mondayValidity = new HashMap<String, Object>();
		mondayValidity.put("titi", Collections.emptyList());
		mondayValidity.put("from", Collections.emptyList());
		theValidity.put(DailyHandler.Weekday.MONDAY.getValue(), mondayValidity);
		settings.put(InteractionTriggerHandler.VALIDITY, theValidity);

		new InteractionTriggerHandler().checkSettings(theObject, settings, null);
	}

	private static final Map<String, Object> getFromToMap(int fromHour, int fromMinute, int toHour, int toMinute) {
		final Map<String, Object> validity = new HashMap<String, Object>();

		final Map<String, Object> fromAtom = new HashMap<String, Object>();
		fromAtom.put(SchedulingAtom.HOUR, fromHour);
		fromAtom.put(SchedulingAtom.MINUTE, fromMinute);

		final Map<String, Object> toAtom = new HashMap<String, Object>();
		toAtom.put(SchedulingAtom.HOUR, toHour);
		toAtom.put(SchedulingAtom.MINUTE, toMinute);

		validity.put(InteractionTriggerHandler.VALIDITY_FROM, fromAtom);
		validity.put(InteractionTriggerHandler.VALIDITY_TO, toAtom);
		return validity;
	}

	@Test(expected = InvalidSettingException.class)
	public void validityWithFromAfterToTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(InteractionTriggerHandler.EVENT, "anEvent");

		final Map<String, Object> theValidity = new HashMap<String, Object>();
		theValidity.put(DailyHandler.Weekday.MONDAY.getValue(), InteractionTriggerHandlerTest.getFromToMap(10, 30, 9, 45));
		settings.put(InteractionTriggerHandler.VALIDITY, theValidity);

		new InteractionTriggerHandler().checkSettings(theObject, settings, null);
	}

	public void validTest() {
		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(InteractionTriggerHandler.EVENT, "anEvent");

		final Map<String, Object> theValidity = new HashMap<String, Object>();
		theValidity.put(DailyHandler.Weekday.MONDAY.getValue(), InteractionTriggerHandlerTest.getFromToMap(10, 30, 15, 45));
		settings.put(InteractionTriggerHandler.VALIDITY, theValidity);

		try {
			new InteractionTriggerHandler().checkSettings(theObject, settings, null);
		} catch (final Exception e) {
			Assert.fail();
		}

	}

	@Test
	public void fullSubscriptionTest() {

		final VObjectData theObject = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(InteractionTriggerHandler.EVENT, "anEvent");

		final VObjectData targetObject = VObjectData.createObject(ObjectType.NABAZTAG_V2, "serial", "label", theObject.getOwner(), "location");
		settings.put(InteractionTriggerHandler.TARGET, targetObject.getId());

		final Map<String, Object> theValidity = new HashMap<String, Object>();
		theValidity.put(DailyHandler.Weekday.MONDAY.getValue(), InteractionTriggerHandlerTest.getFromToMap(8, 30, 15, 45));
		theValidity.put(DailyHandler.Weekday.TUESDAY.getValue(), InteractionTriggerHandlerTest.getFromToMap(10, 30, 22, 45));
		theValidity.put(DailyHandler.Weekday.SATURDAY.getValue(), InteractionTriggerHandlerTest.getFromToMap(12, 20, 18, 33));
		settings.put(InteractionTriggerHandler.VALIDITY, theValidity);

		final Map<String, String> theSettings = new InteractionTriggerHandler().generateSettings(theObject, settings, null);
		Assert.assertEquals(5, theSettings.size());
		Assert.assertEquals("anEvent", theSettings.get(InteractionTriggerHandler.EVENT));
		Assert.assertEquals(String.valueOf(targetObject.getId()), theSettings.get(InteractionTriggerHandler.TARGET));
		Assert.assertEquals("8:30-15:45", theSettings.get(DailyHandler.Weekday.MONDAY.getValue()));
		Assert.assertEquals("10:30-22:45", theSettings.get(DailyHandler.Weekday.TUESDAY.getValue()));
		Assert.assertEquals("12:20-18:33", theSettings.get(DailyHandler.Weekday.SATURDAY.getValue()));
	}
}
