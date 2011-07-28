package net.violet.platform.datamodel;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

import org.junit.Assert;
import org.junit.Test;

public class PressTest extends DBTest {

	@Test
	public void GetPress() {

		final Lang lang = Factories.LANG.findByIsoCode("de");
		final Product product = Factories.PRODUCT.find(1L);
		final List<Press> ThePressList = Factories.PRESS.getPressByLangAndProduct(lang, product, 0, 30);

		Assert.assertNotNull(ThePressList);
		Assert.assertEquals(ThePressList.size(), 2);
		final List<String> stores = new ArrayList<String>();
		for (final Press thePress : ThePressList) {
			final String aux = thePress.getTitle();
			if ((aux != null) && !StringShop.EMPTY_STRING.equals(aux) && !stores.contains(aux)) {
				stores.add(aux);
			}
			Assert.assertEquals("de", thePress.getPressLang().getIsoCode());
			Assert.assertEquals("Corporate", thePress.getPressProduct().getName());
		}
		Assert.assertTrue(stores.contains("Title1"));
		Assert.assertTrue(stores.contains("Title2"));
	}
}
