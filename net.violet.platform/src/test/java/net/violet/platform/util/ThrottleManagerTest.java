package net.violet.platform.util;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.util.ThrottleManager.Throttle;
import net.violet.platform.util.ThrottleManager.ThrottleProfile;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class ThrottleManagerTest extends MockTestBase {


	private static final Logger LOGGER = Logger.getLogger(ThrottleManagerTest.class);

	@Test
	public void testThrottle() {
		// set up a ThrottleProfile for 5 ops per 10 seconds
		final ThrottleProfile theProfile = new ThrottleProfile(5, 10000);

		final Throttle throttle = new Throttle(theProfile);

		// try doing one operation per second and we should hit the throttle on
		// the sixth operation
		// and then kick in again on the eleventh, only to stop again on the
		// fifteenth
		for (int i = 0; i < 20; i++) {

			if (((i >= 0) && (i < 5)) || ((i >= 10) && (i < 15))) {
				Assert.assertTrue(!throttle.throttleOp(theProfile.getPeriod()));
			} else {
				Assert.assertFalse(!throttle.throttleOp(theProfile.getPeriod()));
			}
			// pause for a sec
			try {
				Thread.sleep(1000L);
			} catch (final InterruptedException e) {
				ThrottleManagerTest.LOGGER.fatal(e, e);
			}
		}
	}

	@Test
	public void testThrottleManager() {
		// set up a ThrottleProfile for 5 ops per 10 seconds
		final ThrottleProfile theProfile = new ThrottleProfile(5, 10000);
		final String theClient1 = "client1";
		final ThrottleManager<String> throttleManager = ThrottleManager.getRessourcesThrottle(theProfile);

		// try doing one operation per second and we should hit the throttle on
		// the sixth operation
		// and then kick in again on the eleventh, only to stop again on the
		// fifteenth
		for (int i = 0; i < 20; i++) {

			if (((i >= 0) && (i < 5)) || ((i >= 10) && (i < 15))) {
				Assert.assertTrue(throttleManager.isOperationAllowed(theClient1, theProfile));
			} else {
				Assert.assertFalse(throttleManager.isOperationAllowed(theClient1, theProfile));
			}
			// pause for a sec
			try {
				Thread.sleep(1000L);
			} catch (final InterruptedException e) {
				ThrottleManagerTest.LOGGER.fatal(e, e);
			}
		}
	}
}
