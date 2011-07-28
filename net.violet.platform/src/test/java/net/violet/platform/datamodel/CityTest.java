package net.violet.platform.datamodel;

import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.dataobjects.TimezoneData;

import org.junit.Assert;
import org.junit.Test;

public class CityTest extends DBTest {

	private static final String LAUSANNE = "Lausanne";
	private static final String ZURICH = "Zurich";
	private static final String SUISSE = "CH";

	@Test
	public void annuSetCityTest() {

		final Lang theLang = Factories.LANG.findByIsoCode("fr-FR");
		final TimezoneData inTimeZone = TimezoneData.findByJavaId("Europe/Paris");
		final CountryData inCountry = CountryData.getDefaultCountryByJavaId(CityTest.SUISSE, theLang);
		final User theUser = Factories.USER.createNewUser("aeffacer@email.ae", "aa", theLang, inCountry.getPaysCode(), "inFirstName", "inLastName", inTimeZone.getId());

		// 1ere ville 1er pays
		theUser.getAnnu().setCity(CityTest.LAUSANNE);
		City theCity = theUser.getAnnu().getCity();
		final Long idVille1 = theCity.getId();
		Assert.assertEquals(CityTest.LAUSANNE, theUser.getAnnu().getAnnu_city());
		Assert.assertEquals(CityTest.LAUSANNE, theCity.getName());

		// 2eme ville 1er pays
		theUser.getAnnu().setCity(CityTest.ZURICH);
		theCity = theUser.getAnnu().getCity();
		Assert.assertEquals(CityTest.ZURICH, theUser.getAnnu().getAnnu_city());
		Assert.assertEquals(CityTest.ZURICH, theCity.getName());

		theUser.getAnnu().setCity(CityTest.LAUSANNE);
		theCity = theUser.getAnnu().getCity();
		Assert.assertEquals(CityTest.LAUSANNE, theUser.getAnnu().getAnnu_city());
		Assert.assertEquals(CityTest.LAUSANNE, theCity.getName());
		Assert.assertEquals(idVille1, theCity.getId());

		// 1ere ville 2em pays
		theUser.getAnnu().setAddress("CA", "123-67");
		theUser.getAnnu().setCity(CityTest.LAUSANNE);
		theCity = theUser.getAnnu().getCity();

		Assert.assertEquals(CityTest.LAUSANNE, theUser.getAnnu().getAnnu_city());
		Assert.assertEquals(CityTest.LAUSANNE, theCity.getName());
		Assert.assertEquals(theUser.getAnnu().getAnnu_country(), theCity.getCountryCode());
		Assert.assertTrue(!(idVille1 == theCity.getId()));
	}
}
