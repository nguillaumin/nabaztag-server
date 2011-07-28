package net.violet.platform.datamodel;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class UserPrefsTest extends DBTest {

	@Test
	public void testExistingRecords() {
		final UserPrefs myRecord = Factories.USER_PREFS.find(27852);
		Assert.assertEquals(27852, myRecord.getId().intValue());
		Assert.assertEquals("layout_violet", myRecord.getUserprefs_layout());
	}

}
