package net.violet.platform.datamodel;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class ProductImpl extends ObjectRecord<Product, ProductImpl> implements Product {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<ProductImpl> SPECIFICATION = new SQLObjectSpecification<ProductImpl>("product", ProductImpl.class, new SQLKey("id"));

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected String name;

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected ProductImpl() {
		// This space for rent.
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<ProductImpl> getSpecification() {
		return ProductImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getCity_city()
	 */
	public String getName() {
		return this.name;
	}
}
