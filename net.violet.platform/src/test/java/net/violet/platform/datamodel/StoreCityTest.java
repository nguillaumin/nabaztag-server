package net.violet.platform.datamodel;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class StoreCityTest extends DBTest {

	@Test
	public void GetgetCountryStoreCities() {

		final List<StoreCity> TheStoreCities = Factories.STORE_CITY.getCountryStoreCities(77L);

		Assert.assertNotNull(TheStoreCities);
		Assert.assertEquals(TheStoreCities.size(), 3);
		final List<String> cities = new ArrayList<String>();
		for (final StoreCity theStoreCity : TheStoreCities) {
			cities.add(theStoreCity.getName());
		}
		Assert.assertTrue(cities.contains("Paris"));
	}
}
