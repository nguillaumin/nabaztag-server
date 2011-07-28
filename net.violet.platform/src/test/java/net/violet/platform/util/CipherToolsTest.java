package net.violet.platform.util;

import net.violet.common.utils.DigestTools;
import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

public class CipherToolsTest extends MockTestBase {

	/**
	 * Test de la somme MD5.
	 */
	@Test
	public void testComputeMD5Sum() {
		Assert.assertEquals("a1d0c6e83f027327d8461063f4ac58a6", DigestTools.digest("42", DigestTools.Algorithm.MD5));
	}

	/**
	 * Test de la somme SHA1.
	 */
	@Test
	public void testComputeSHA1Sum() {
		Assert.assertEquals("92cfceb39d57d914ed8b14d0e37643de0797ae56", DigestTools.digest("42", DigestTools.Algorithm.SHA1));
	}
}
