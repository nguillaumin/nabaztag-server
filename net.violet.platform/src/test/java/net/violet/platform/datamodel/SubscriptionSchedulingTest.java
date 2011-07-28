package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class SubscriptionSchedulingTest extends DBTest {

	@Test
	public void findAllByObjectAndTypeTest() {
		final VObject object1 = Factories.VOBJECT.find(31);
		final List<SubscriptionScheduling> theScheds = Factories.SUBSCRIPTION_SCHEDULING.findAllByObjectAndType(object1, SCHEDULING_TYPE.Daily);
		Assert.assertEquals(2, theScheds.size());
		Assert.assertTrue(theScheds.contains(Factories.SUBSCRIPTION_SCHEDULING.find(1)));
		Assert.assertTrue(theScheds.contains(Factories.SUBSCRIPTION_SCHEDULING.find(3)));
	}
}
