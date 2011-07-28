package net.violet.platform.datamodel;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class ProductTest extends DBTest {

	@Test
	public void findAllProductsTest() {
		final List<Product> TheProducts = Factories.PRODUCT.findAllproducts();

		Assert.assertNotNull(TheProducts);
		Assert.assertEquals(TheProducts.size(), 8);
		final List<String> products = new ArrayList<String>();
		for (final Product theProduct : TheProducts) {
			products.add(theProduct.getName());
		}
		Assert.assertTrue(products.contains("Bookz"));

	}
}
