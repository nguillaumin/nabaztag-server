package net.violet.platform.interactif;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.util.StringShop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CookieHelperTest extends MockTestBase {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testGetPropertyValue() {
		final String strCookie = "sn:007;town:napoli;planet:mars;";

		// get property test
		final String planet = CookieHelper.getPropertyValue(strCookie, "planet");
		Assert.assertEquals(planet, "mars");

		// case-insensitive property test
		final String town = CookieHelper.getPropertyValue(strCookie, "Town");
		Assert.assertEquals(town, "napoli");
	}

	@Test
	public void testUpdatePropertyValue() {

		// cookie creation test
		String strCookie = CookieHelper.updatePropertyValue(StringShop.EMPTY_STRING, "sn", "007");
		strCookie = CookieHelper.updatePropertyValue(strCookie, "town", "napoli");
		strCookie = CookieHelper.updatePropertyValue(strCookie, "planet", "venus");

		Assert.assertEquals("sn:007;town:napoli;planet:venus;", strCookie);

		// update property case-insensitive test
		strCookie = CookieHelper.updatePropertyValue(strCookie, "PLANET", "mars");
		Assert.assertEquals("sn:007;town:napoli;planet:mars;", strCookie);
	}

}
