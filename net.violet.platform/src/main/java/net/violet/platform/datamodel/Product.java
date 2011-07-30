package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * 
 * Violet products
 *
 */
public interface Product extends Record<Product> {

	String getName();

}
