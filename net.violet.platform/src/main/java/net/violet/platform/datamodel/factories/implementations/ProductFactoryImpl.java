package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.ProductImpl;
import net.violet.platform.datamodel.factories.ProductFactory;

public class ProductFactoryImpl extends RecordFactoryImpl<Product, ProductImpl> implements ProductFactory {

	ProductFactoryImpl() {
		super(ProductImpl.SPECIFICATION);
	}

	public List<Product> findAllproducts() {
		return findAll(null, null, "name");
	}

	public Product findByName(String inName) {
		return find(" name = ? ", Arrays.asList(new Object[] { inName }));
	}
}
