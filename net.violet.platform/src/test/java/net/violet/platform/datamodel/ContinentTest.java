package net.violet.platform.datamodel;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class ContinentTest extends DBTest {

	@Test
	public void GetAllContinentsTest() {

		final List<Continent> TheContinents = Factories.CONTINENT.findAllContinents();

		Assert.assertNotNull(TheContinents);
		Assert.assertEquals(TheContinents.size(), 7);
		final List<String> continents = new ArrayList<String>();
		for (final Continent theContinent : TheContinents) {
			continents.add(theContinent.getName());
		}
		Assert.assertTrue(continents.contains("Europe"));

	}
}
