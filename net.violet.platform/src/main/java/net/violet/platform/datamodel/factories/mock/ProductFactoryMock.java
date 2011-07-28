package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.factories.ProductFactory;
import net.violet.platform.datamodel.mock.ProductMock;

public class ProductFactoryMock extends RecordFactoryMock<Product, ProductMock> implements ProductFactory {

	ProductFactoryMock() {
		super(ProductMock.class);
	}

	public List<Product> findAllproducts() {
		final List<Product> theProducts = new LinkedList<Product>();
		for (final Product aProduct : findAll()) {
			theProducts.add(aProduct);
		}
		return theProducts;
	}

	public Product findByName(String inName) {
		for (final Product aProduct : findAll()) {
			if (aProduct.getName().equals(inName)) {
				return aProduct;
			}
		}
		return null;
	}

}
