package net.violet.platform.vasm;

import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;

/**
 * Classe de base pour les tests relatifs à Vasm.
 */
public class VasmTestBase extends MockTestBase {

	protected static void assertSizeIsOk(byte[] inBinary) {
		final int theSize = ((inBinary[0] << 24) & 0xFF000000) | ((inBinary[1] << 16) & 0x00FF0000) | ((inBinary[2] << 8) & 0x0000FF00) | (inBinary[3] & 0x000000FF);
		final int theBinarySize = inBinary.length - 8;

		Assert.assertEquals(theSize, theBinarySize);
	}

	protected static void assertTrameSizeIsOk(byte[] inBinary) {
		Assert.assertEquals(5, inBinary[0]);
		final int theSize = ((inBinary[1] << 16) & 0x00FF0000) | ((inBinary[2] << 8) & 0x0000FF00) | (inBinary[3] & 0x000000FF);
		Assert.assertEquals(theSize, inBinary.length - 4);
	}

	protected static void assertNoData(byte[] inBinary) {
		final int theSize = inBinary.length - 4;
		Assert.assertEquals(0, inBinary[theSize]);
		Assert.assertEquals(0, inBinary[theSize + 1]);
		Assert.assertEquals(0, inBinary[theSize + 2]);
		Assert.assertEquals(0, inBinary[theSize + 3]);
	}

	public static void assertVasmEquals(int[] inTargetVasm, byte[] inGeneratedVasm) {
		VasmTestBase.assertSizeIsOk(inGeneratedVasm);
		VasmTestBase.assertNoData(inGeneratedVasm);
		MockTestBase.assertBinaryEquals(4, 4, inTargetVasm, inGeneratedVasm);
	}
}
