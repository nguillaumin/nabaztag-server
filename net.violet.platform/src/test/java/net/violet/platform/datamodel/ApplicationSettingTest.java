package net.violet.platform.datamodel;

import java.util.List;

import junit.framework.Assert;
import net.violet.platform.datamodel.factories.Factories;

import org.junit.Test;

public class ApplicationSettingTest extends DBTest {

	@Test
	public void addSettingsTest() {
		final Application theApplication = Factories.APPLICATION.find(1);
		final String key = "key";
		final String value = "value";

		final ApplicationSetting aSetting = Factories.APPLICATION_SETTING.create(theApplication, key, value);
		Assert.assertNotNull(aSetting);
		Assert.assertEquals(key, aSetting.getKey());
		Assert.assertEquals(value, aSetting.getValue());
		Assert.assertEquals(theApplication, aSetting.getApplication());

		aSetting.delete();
	}

	@Test
	public void findSettingsTest() {
		final Application theApplication = Factories.APPLICATION.find(1);
		final List<ApplicationSetting> settings = Factories.APPLICATION_SETTING.findAllByApplication(theApplication);

		Assert.assertEquals(2, settings.size());
		Assert.assertEquals("key1", settings.get(0).getKey());
		Assert.assertEquals("key2", settings.get(1).getKey());
	}

	@Test
	public void findSettingTest() {
		final Application theApplication = Factories.APPLICATION.find(1);
		final ApplicationSetting setting = Factories.APPLICATION_SETTING.findByApplicationAndKey(theApplication, "key1");
		Assert.assertNotNull(setting);
		Assert.assertEquals("value1", setting.getValue());

		final ApplicationSetting setting2 = Factories.APPLICATION_SETTING.findByApplicationAndKey(theApplication, "invalid");
		Assert.assertNull(setting2);
	}

	@Test
	public void insertDuplicateTest() {
		final Application theApplication = Factories.APPLICATION.find(1);
		final ApplicationSetting theSetting = Factories.APPLICATION_SETTING.create(theApplication, "key1", "aValue");
		Assert.assertNull(theSetting);
	}

}
