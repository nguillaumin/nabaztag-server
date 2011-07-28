package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class UserEmailTest extends DBTest {

	@Test
	public void findAddressesTest() {
		final User theUser = Factories.USER.find(84887);
		Assert.assertNotNull(theUser);

		final List<UserEmail> theEmails = Factories.USER_EMAIL.findAllByUser(theUser);

		Assert.assertEquals(2, theEmails.size());
		for (final UserEmail anEmail : theEmails) {
			Assert.assertTrue(anEmail.getAddress().equals("toto@violet.net") || anEmail.getAddress().equals("titi@violet.net"));
		}
	}

	@Test
	public void findByAddressTest() {
		UserEmail theEmail = Factories.USER_EMAIL.findByAddress("toto@violet.net");
		Assert.assertNotNull(theEmail);
		Assert.assertEquals("toto@violet.net", theEmail.getAddress());

		theEmail = Factories.USER_EMAIL.findByAddress("tutu@violet.net");
		Assert.assertNull(theEmail);

	}
}
