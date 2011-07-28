package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface StoreCity extends Record<StoreCity> {

	Long getId();

	String getName();

	public Country getCountry();

}
