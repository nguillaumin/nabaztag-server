package net.violet.platform.util;

import java.io.UnsupportedEncodingException;

import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

public class LibBasicTest extends MockTestBase {

	/**
	 * Test du cryptage.
	 * 
	 * @throws UnsupportedEncodingException
	 *             si on ne gère pas le ISO-8859-1.
	 */
	@Test
	public void cryptTest() {
		final byte[] theBytes = LibBasic.crypt8("nabaztag", 0x47, 47);
		Assert.assertEquals("nabaztag", LibBasic.uncrypt8(theBytes, 0x47, 47));
	}
}
