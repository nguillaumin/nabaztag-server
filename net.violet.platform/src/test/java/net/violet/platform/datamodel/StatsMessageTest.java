package net.violet.platform.datamodel;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class StatsMessageTest extends DBTest {

	@Test
	public void testExistingRecords() {
		final StatsMessage myRecord = Factories.STATS.find(1);
		Assert.assertNotNull(myRecord);
	}

}
