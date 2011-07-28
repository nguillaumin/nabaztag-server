package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class CountryTest extends DBTest {

	@Test
	public void findAllTest() {
		final List<Country> result = Factories.COUNTRIES.findAll();
		Assert.assertEquals(5, result.size());
		Assert.assertEquals("Canada", result.get(0).getName());
		Assert.assertEquals("USA", result.get(4).getName());
	}

	@Test
	public void findByCodeTest() {
		final Country thePays = Factories.COUNTRIES.findByCode("DE");
		Assert.assertEquals("DE", thePays.getCode());
		Assert.assertEquals("Europe", thePays.getContinent().getName());//Albanie est en Europe!!!
		Assert.assertEquals("Germany", thePays.getName());

		Assert.assertNull(Factories.COUNTRIES.findByCode("XX"));
	}

	@Test
	public void findByContinent() {
		final List<Country> europe = Factories.COUNTRIES.findCountriesByContinent(Factories.CONTINENT.find(2));
		Assert.assertEquals(3, europe.size());
		Assert.assertEquals("France", europe.get(0).getName());
		Assert.assertEquals("Italy", europe.get(2).getName());
	}
}
