package net.violet.platform.xmpp.packet;

import java.io.Reader;
import java.io.StringReader;

import net.violet.platform.message.Message;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.KeyValueImpl;

import org.junit.Assert;
import org.junit.Test;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParser;

public class PingPacketTest {

	@Test
	public void testGetPacketInfoWithTTL() throws Exception {
		final XmlPullParser theParser = new MXParser();
		final Reader theReader = new StringReader("<packet xmlns='violet:packet' format='1.0' ttl='600'>fwoAACoAHltP4QxrzjunEomzHM32u0+XY4MfqcJgQ7YsxlPXbhybhuJ/wpWIJEUDAAABeP8=</packet>");
		theParser.setInput(theReader);
		final KeyValueImpl<Message, String> theResult = PingPacket.getPacketInfo(theParser);
		final Message theMessage = theResult.getKey();
		Assert.assertNotNull(theMessage);
		Assert.assertEquals(600, theMessage.getTTLInSecond());
	}

	@Test
	public void testGetPacketInfoWithoutTTL() throws Exception {
		final XmlPullParser theParser = new MXParser();
		final Reader theReader = new StringReader("<packet xmlns='violet:packet' format='1.0'>fwoAACoAHltP4QxrzjunEomzHM32u0+XY4MfqcJgQ7YsxlPXbhybhuJ/wpWIJEUDAAABeP8=</packet>");
		theParser.setInput(theReader);
		final KeyValueImpl<Message, String> theResult = PingPacket.getPacketInfo(theParser);
		final Message theMessage = theResult.getKey();
		Assert.assertNotNull(theMessage);
		Assert.assertEquals(Constantes.QUEUE_TTL_DEFAULT, theMessage.getTTLInSecond());
	}
}
