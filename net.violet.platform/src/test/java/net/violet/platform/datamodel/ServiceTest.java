package net.violet.platform.datamodel;

import net.violet.platform.datamodel.factories.ServiceFactory;

import org.junit.Assert;
import org.junit.Test;

public class ServiceTest extends DBTest {

	@Test
	public void testInstances() {
		Assert.assertNotNull(ServiceFactory.SERVICE.RSS);
		Assert.assertNotNull(ServiceFactory.SERVICE.PODCAST);
		Assert.assertNotNull(ServiceFactory.SERVICE.CALLBACK);
		Assert.assertNotNull(ServiceFactory.SERVICE.TWITTER);
	}
}
