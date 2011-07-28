package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Product;

public interface ProductFactory extends RecordFactory<Product> {

	List<Product> findAllproducts();

	Product findByName(String inName);
}
