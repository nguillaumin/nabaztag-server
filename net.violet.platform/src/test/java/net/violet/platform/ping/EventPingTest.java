package net.violet.platform.ping;

import java.io.IOException;
import java.util.Date;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.message.MessageDump;
import net.violet.platform.message.MessageDumper;

import org.junit.Assert;
import org.junit.Test;

public class EventPingTest extends MockTestBase {

	@Test
	public void fixMeTest() {
		Assert.assertTrue(true);
	}

	// @Test TODO FIXME test
	public void testPingV1() throws IOException, InterruptedException {
		// Test du ping pour un V1.
		// Test du ping pour un V2.
		String theBinaryPacket;
		MessageDump theDumpedPacket;
		byte[] theFrame;
		theBinaryPacket = new String(EventPing.solve("127.0.0.1", 80, "EVENTPING1", "1", 0, 0, 0, 0, 0, HARDWARE.V1), "ISO-8859-1");
		theDumpedPacket = MessageDumper.dump(theBinaryPacket);
		// System.out.println(theDumpedPacket.toString());
		/* Frame timeout length 1 */
		theFrame = theDumpedPacket.getFrame(3);
		Assert.assertNotNull(theFrame);
		Assert.assertEquals(1, theFrame.length);
		/* Frame source length 25 */
		theFrame = theDumpedPacket.getFrame(4);
		Assert.assertNotNull(theFrame);
		Assert.assertEquals(25, theFrame.length);
		/* Frame kill not exist */
		Assert.assertNull(theDumpedPacket.getFrame(6));
		/* Frame bytecode v1 length 33743 */
		theFrame = theDumpedPacket.getFrame(5);
		Assert.assertNotNull(theFrame);
		Assert.assertEquals(33743, theFrame.length);
		theBinaryPacket = new String(EventPing.solve("127.0.0.1", 80, "EVENTPING1", "1", 0, 0, 0, 2147483647, 0, HARDWARE.V1), "ISO-8859-1");
		theDumpedPacket = MessageDumper.dump(theBinaryPacket);
		/* Frame timeout length 1 */
		theFrame = theDumpedPacket.getFrame(3);
		Assert.assertNotNull(theFrame);
		Assert.assertEquals(1, theFrame.length);
		/* Frame source length 25 */
		theFrame = theDumpedPacket.getFrame(4);
		Assert.assertNotNull(theFrame);
		Assert.assertEquals(25, theFrame.length);
		/* Frame kill not exist */
		Assert.assertTrue(theDumpedPacket.getFrame(6) == null);
		/* Frame bytecode v1 length 94968 */
		theFrame = theDumpedPacket.getFrame(5);
		Assert.assertNotNull(theFrame);
		Assert.assertEquals(94968, theFrame.length);
	}

	// @Test TODO FIXME test
	public void testPingTime() throws InterruptedException {
		final Date before = new Date();
		// Test de 40 générations de paquets ping (deux MAC).
		for (int index = 0; index < 100; index++) {
			EventPing.solve("127.0.0.1", 80, "EVENTPING1", "1", 0, 0, 0, 0, 0, HARDWARE.V1);
			EventPing.solve("127.0.0.1", 80, "EVENTPING1", "1", 0, 0, 0, 2147483647, 0, HARDWARE.V1);
		}
		final Date after = new Date();
		final long theDelta = after.getTime() - before.getTime();
		System.out.println(theDelta);
	}
}
