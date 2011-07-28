package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class ProductData extends RecordData<Product> {

	private static final Logger LOGGER = Logger.getLogger(ProductData.class);

	public static ProductData getData(Product inProduct) {
		try {
			return RecordData.getData(inProduct, ProductData.class, Product.class);
		} catch (final InstantiationException e) {
			ProductData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			ProductData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			ProductData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			ProductData.LOGGER.fatal(e, e);
		}

		return null;
	}

	public static ProductData findByName(String inName) {
		return ProductData.getData(Factories.PRODUCT.findByName(inName));
	}

	protected ProductData(Product inRecord) {
		super(inRecord);
	}

	public String getName() {
		final Product record = getRecord();
		if (record != null) {
			return record.getName();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static List<String> getAllProductNames() {
		final List<String> namesList = new ArrayList<String>();
		for (final Product inProduct : Factories.PRODUCT.findAllproducts()) {
			namesList.add(inProduct.getName());
		}
		return namesList;
	}

	public Product getReference() {
		return getRecord();
	}
}
