package net.violet.platform.vasm;

import java.io.IOException;

import net.violet.common.utils.DigestTools;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageSerializer;

import org.junit.Assert;
import org.junit.Test;

public class VasmTest extends VasmTestBase {

	private static final String POINT_BLEU = "; trame=18\n" + "; essai\n" + "\n" + "	clr	r4\n" + "	ld	r10,10\n" + "@st	ld	r0,0\n" + "	ld	r1,255\n" + "	palette	r0,r1\n" + "	\n" + "	ld	r5,5\n" + "	led	r4,r5\n" + "	\n" + "	dec	r4\n" + "	bge	@b1\n" + "	ld	r4,4\n" + "	dec	r10\n" + "	bne	@b1\n" + "	ld	r0,0\n" + "	ld	r1,2\n" + "	send	r0,r1\n" + "@b1\n" + "	ld	r0,6\n" + "	ld	r1,255\n" + "	palette	r0,r1\n" + "	\n" + "	ld	r5,2\n" + "	led	r4,r5\n" + "	\n" + "	wait	4\n" + "	bra	@st\n" + "	\n" + "	end\n";

	private static final int[] POINT_BLEU_BIN = new int[] { 0x82, // clr
			4, // r4
			0x0A, // ld r10
			10, // #10
			0x00, // ld r0
			0, // #0
			0x01, // ld r1
			255, // #255
			0xA8, // palette
			0 * 16 + 1, // r0, r1
			0x05, // ld r5
			5, // #5
			0xA7, // led
			4 * 16 + 5, // r4, r5
			0x80, // dec
			4, // r4
			0xA0, // bge
			0, 0x20, // #0x0020
			0x04, // ld r4
			4, // #4
			0x80, // dec
			10, // r10
			0x9E, // bne
			0, 0x20, // #0x0020
			0x00, // ld r0
			0, // #0
			0x01, // ld r1
			2, // #2
			0xC4, // send
			0 * 16 + 1, // r0, r1
			0x00, // ld r0
			6, // #6
			0x01, // ld r1
			255, // #255
			0xA8, // palette
			0 * 16 + 1, // r0, r1
			0x05, // ld r5
			2, // #2
			0xA7, // led
			4 * 16 + 5, // r4, r5
			0x7E, // wait_i
			4, // #4
			0x9C, // bra
			0, 4 // 0x0004
	};

	private static final int[] POINT_BLEU_TRAME_42_5 = new int[] { 5, // 5
			0, 0, 70, // SIZE
			97, 109, 98, 101, 114, // "amber"
			0, 0, 0, 42, // ID (= 42)
			5, // TIMEOUT (= 5)
			0, 0, 0, 47, // CODE SIZE
			-126, 4, 10, 10, 0, 0, 1, -1, -88, 1, 5, 5, -89, 69, -128, 4, -96, 0, 49, 4, 4, -128, 10, -98, 0, 49, 0, 0, 1, 2, -60, 1, 0, 6, 1, -1, -88, 1, 5, 2, -89, 69, 126, 4, -100, 0, 21, 0, 0, 0, 0, // DATA SIZE
			27, // CHECKSUM (= 27)
			109, 105, 110, 100, // "mind"
	};

	@Test
	public void testPointBleu() throws VasmException {
		final byte[] theOutput = Vasm.asmonly(VasmTest.POINT_BLEU, getVASM());
		VasmTestBase.assertSizeIsOk(theOutput);
		VasmTestBase.assertNoData(theOutput);
		VasmTestBase.assertVasmEquals(VasmTest.POINT_BLEU_BIN, theOutput);
	}

	@Test
	public void testPointBleuTrame() throws VasmException {
		final byte[] theOutput = Vasm.maketrame(getVASM().getBluePointFileName(), null, 42, 5, getVASM()).toByteArray();
		MockTestBase.assertBinaryEquals(VasmTest.POINT_BLEU_TRAME_42_5, theOutput);
	}

	@Test
	public void testPointBleuFile() throws VasmException, IOException {
		final String theSource = Vasm.getSource(getVASM().getBluePointFileName());
		final byte[] theOutput = Vasm.asmonly(theSource, getVASM());
		VasmTestBase.assertSizeIsOk(theOutput);
		VasmTestBase.assertNoData(theOutput);
		VasmTestBase.assertVasmEquals(VasmTest.POINT_BLEU_BIN, theOutput);
	}

	@Test
	public void testDemoTrame() throws VasmException, IOException {
		final byte[] theOutput = Vasm.maketrame(getVASM().getDemoFileName(), null, 42, 5, getVASM()).toByteArray();
		VasmTestBase.assertTrameSizeIsOk(theOutput);
		final String theMD5Sum = DigestTools.digest(new String(theOutput, "ISO-8859-1"), DigestTools.Algorithm.MD5);
		Assert.assertEquals("cbe66bd730815038cee40170835bf96a", theMD5Sum);
	}

	@Test
	public void testAsleepTrame() throws VasmException, IOException {
		final byte[] theOutput = Vasm.maketrame(getVASM().getSleepFileName(), null, 42, 5, getVASM()).toByteArray();
		VasmTestBase.assertTrameSizeIsOk(theOutput);
		final String theMD5Sum = DigestTools.digest(new String(theOutput, "ISO-8859-1"), DigestTools.Algorithm.MD5);
		Assert.assertEquals("7f3cf88deb62355fa138805ad0772e04", theMD5Sum);
	}

	@Test
	public void testWaitTrame() throws VasmException, IOException {
		final byte[] theOutput = Vasm.maketrame(getVASM().getWaitFileName(), null, 42, 5, getVASM()).toByteArray();
		VasmTestBase.assertTrameSizeIsOk(theOutput);
		final String theMD5Sum = DigestTools.digest(new String(theOutput, "ISO-8859-1"), DigestTools.Algorithm.MD5);
		Assert.assertEquals("7f0b1c8511281bb07b4b496841c8978c", theMD5Sum);
	}

	@Test
	public void testMessageTrame() throws VasmException, IOException {
		final byte[] theOutput = Vasm.maketrame(getVASM().getMsgFileName(), null, 42, 5, getVASM()).toByteArray();
		VasmTestBase.assertTrameSizeIsOk(theOutput);
		final String theMD5Sum = DigestTools.digest(new String(theOutput, "ISO-8859-1"), DigestTools.Algorithm.MD5);
		Assert.assertEquals("3dcafcd6541bcfce3448ce9450d79148", theMD5Sum);
	}

	@Test
	public void testMessageTrameEmptyMessage() throws IOException {
		final Message theMessage = new MessageDraft(getBrewsterObject());
		final byte[] theOutput = MessageSerializer.generatePingPacket(theMessage, 0, "20", 0, HARDWARE.V1);
		final String theMD5Sum = DigestTools.digest(new String(theOutput, "ISO-8859-1"), DigestTools.Algorithm.MD5);
		Assert.assertEquals("f2f42b1bd403696cae130c7db8383cb9", theMD5Sum);
	}

	@Test
	public void testCache() throws VasmException {
		Vasm.emptyCache();
		final long now = System.currentTimeMillis();
		Vasm.maketrame(getVASM().getDemoFileName(), null, 42, 5, getVASM()).toByteArray();
		final long after1 = System.currentTimeMillis();
		Vasm.maketrame(getVASM().getDemoFileName(), null, 42, 5, getVASM()).toByteArray();
		final long after2 = System.currentTimeMillis();
		final long elapsed1 = after1 - now;
		final long elapsed2 = after2 - after1;
		Assert.assertTrue("Cache is not efficient! cached = " + elapsed2 + "ms, uncached = " + elapsed1 + "ms", 5 * elapsed2 < elapsed1);
	}

	@Test
	public void testTypeOper() {
		final Vasm.AssoVasm theAsso = new Vasm.AssoVasm();
		Assert.assertEquals(Vasm.EAddrType.TYPE_o, Vasm.typeoper(Vasm.cutvirg(net.violet.common.StringShop.EMPTY_STRING, theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_o, Vasm.typeoper(Vasm.cutvirg("\t", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_r, Vasm.typeoper(Vasm.cutvirg("r1", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_w, Vasm.typeoper(Vasm.cutvirg("@1", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_ri, Vasm.typeoper(Vasm.cutvirg("r1,1", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_ri, Vasm.typeoper(Vasm.cutvirg("r1, 1", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_rr, Vasm.typeoper(Vasm.cutvirg("r1,r2", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_rr, Vasm.typeoper(Vasm.cutvirg("r1, r2", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_rir, Vasm.typeoper(Vasm.cutvirg("r1,2,r2", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_rir, Vasm.typeoper(Vasm.cutvirg("r1, 2, r2", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_rrw, Vasm.typeoper(Vasm.cutvirg("r1,r2,@3", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_rrw, Vasm.typeoper(Vasm.cutvirg("r1, r2, @3", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_rw, Vasm.typeoper(Vasm.cutvirg("r1,@3", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_rw, Vasm.typeoper(Vasm.cutvirg("r1, @3", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_i, Vasm.typeoper(Vasm.cutvirg("1", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_ii, Vasm.typeoper(Vasm.cutvirg("1,2", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_ii, Vasm.typeoper(Vasm.cutvirg("1, 2", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_iiii, Vasm.typeoper(Vasm.cutvirg("1,2,3,4", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_iiii, Vasm.typeoper(Vasm.cutvirg("1, 2, 3, 4", theAsso)));
		Assert.assertEquals(Vasm.EAddrType.TYPE_iiii, Vasm.typeoper(Vasm.cutvirg(" 1 , 2 , 3 , 4 ", theAsso)));
	}
}
