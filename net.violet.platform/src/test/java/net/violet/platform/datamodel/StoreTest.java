package net.violet.platform.datamodel;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

import org.junit.Assert;
import org.junit.Test;

public class StoreTest extends DBTest {

	@Test
	public void GetStoreByCountry() {

		final List<Store> theStores = Factories.STORE.getStoreByCountry("FR");

		Assert.assertNotNull(theStores);
		Assert.assertEquals(theStores.size(), 6);
		final List<String> stores = new ArrayList<String>();
		for (final Store aStore : theStores) {
			String aux = aStore.getStoreCity().getName();
			if ((aux != null) && !StringShop.EMPTY_STRING.equals(aux) && !stores.contains(aux)) {
				stores.add(aux);
			}
			aux = aStore.getUrl();
			if ((aux != null) && !StringShop.EMPTY_STRING.equals(aux) && !stores.contains(aux)) {
				stores.add(aux);
			}
			aux = aStore.getName();
			if ((aux != null) && !StringShop.EMPTY_STRING.equals(aux) && !stores.contains(aux)) {
				stores.add(aux);
			}
			aux = aStore.getAddress();
			if ((aux != null) && !StringShop.EMPTY_STRING.equals(aux) && !stores.contains(aux)) {
				stores.add(aux);
			}
			aux = aStore.getZipCode();
			if ((aux != null) && !StringShop.EMPTY_STRING.equals(aux) && !stores.contains(aux)) {
				stores.add(aux);
			}
			aux = aStore.getType();
			if ((aux != null) && !StringShop.EMPTY_STRING.equals(aux) && !stores.contains(aux)) {
				stores.add(aux);
			}
			Assert.assertEquals("FR", aStore.getStoreCity().getCountry().getCode());
			Assert.assertEquals("mock/picture", aStore.getPicture().getPath());
		}
		//	Assert.assertEquals(15, stores.size());
		Assert.assertTrue(stores.contains("http://www.fnac.es"));
		Assert.assertTrue(stores.contains("http:/recherche.fnac.com/m51193/Violet"));
		Assert.assertTrue(stores.contains("http:/www.fnac.be/fr/"));
		Assert.assertTrue(stores.contains("Paris"));
		Assert.assertTrue(stores.contains("Antibes"));
		Assert.assertTrue(stores.contains("Fnac.com"));
		Assert.assertTrue(stores.contains("FNAC"));
		Assert.assertTrue(stores.contains("Espace SFR - Alencon"));
		Assert.assertTrue(stores.contains("Espace SFR - Antibes"));
		Assert.assertTrue(stores.contains("2015 Route de Grasse"));
		Assert.assertTrue(stores.contains("63 Grande rue"));
		Assert.assertTrue(stores.contains("61000"));
		Assert.assertTrue(stores.contains("06600"));
		Assert.assertTrue(stores.contains("physical"));
		Assert.assertTrue(stores.contains("online"));
		Assert.assertEquals(15, stores.size());

	}
}
