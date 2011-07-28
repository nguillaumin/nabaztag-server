package net.violet.platform.api.authentication;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author christophe - Violet
 */
public class AuthenticationManagerTest {

	/**
	 * Test method for
	 * {@link net.violet.platform.api.authentication.AuthenticationManager#getDigestedKey(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetDigestedKey() {

		final String[] allNames = { "VAdmin", "Player", "VoiceMail", "PlayerNathan", "SimplePlayer", "AppResourcesLoader", "webui", "HelloWorld", "HelloWorld-v2", "HelloWorld-v3", "TwitterPost", "BookReader", "Cinderella", "MailPost", "testPierre" };

		for (final String name : allNames) {
			System.out.println("public key : " + name + ", digested key : " + AuthenticationManager.getDigestedKey(name, "private123"));
		}

		Assert.assertTrue(true);
	}

}
