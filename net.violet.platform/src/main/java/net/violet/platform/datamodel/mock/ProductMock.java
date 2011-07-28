package net.violet.platform.datamodel.mock;

import java.util.List;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Product;

public class ProductMock extends AbstractMockRecord<Product, ProductMock> implements Product {

	private final String productName;

	protected ProductMock(long inId) {
		super(inId);
		this.productName = "Vide";
	}

	public ProductMock(long inId, String inName) {
		super(inId);
		this.productName = inName;
	}

	public List<Product> getAllProducts() {
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return this.productName;
	}
}
