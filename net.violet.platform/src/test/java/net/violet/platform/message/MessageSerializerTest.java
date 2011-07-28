package net.violet.platform.message;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.xmpp.serialization.V1Serializer;

import org.junit.Test;

public class MessageSerializerTest extends MockTestBase {

	private static final int[] EMPTY_PACKET = { 0x7F, // preamble
			0xFF // final byte
	};

	@Test
	public void testEmptyMessage() {
		final VObject theObject = getPrivateObject();
		final Message theMessage = new MessageDraft(theObject);
		final byte[] thePacket = V1Serializer.getContentAsBytes(theMessage);
		MockTestBase.assertBinaryEquals(MessageSerializerTest.EMPTY_PACKET, thePacket);
	}

	private static final int[] EAR_POSITIONS_NEW_FORMAT_PACKET = { 0x7F, // preamble
			0x04, // source
			0x00, 0x00, 0x08, // frame size
			0x7F, 0xFF, 0xFF, 0xFE, // source, new format
			0x04, 0x0A, // left ear
			0x05, 0x03, // right ear
			0xFF };

	private static final int[] EAR_POSITIONS_OLD_FORMAT_PACKET = { 0x7F, // preamble
			0x04, // source
			0x00, 0x00, 0x17, // frame size
			0x7F, 0xFF, 0xFF, 0xFF, // source, new format
			0x00, 0x00, // dummy data....
			0x00, 0x00, // dummy data....
			0x00, 0x00, // dummy data....
			0x00, 0x00, // dummy data....
			0x00, 0x00, // dummy data....
			0x00, 0x00, // dummy data....
			0x00, 0x00, // dummy data....
			0x00, 0x00, // dummy data....
			0x0A, 0x03, // ears (left, right)
			0x00, // messages
			0xFF };

	@Test
	public void testEarPositionsNewFormatMessage() {
		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);
		theMessage.setSourceUpdate(true);
		theMessage.setLeftEarPosition(10);
		theMessage.setRightEarPosition(3);
		final byte[] thePacket = V1Serializer.getContentAsBytes(theMessage);
		MockTestBase.assertBinaryEquals(MessageSerializerTest.EAR_POSITIONS_NEW_FORMAT_PACKET, thePacket);
	}

	@Test
	public void testEarPositionsOldFormatMessage() {
		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);
		// theMessage.setSourceUpdate(false);
		theMessage.setLeftEarPosition(10);
		theMessage.setRightEarPosition(3);
		final byte[] thePacket = V1Serializer.getContentAsBytes(theMessage);
		MockTestBase.assertBinaryEquals(MessageSerializerTest.EAR_POSITIONS_OLD_FORMAT_PACKET, thePacket);
	}
}
