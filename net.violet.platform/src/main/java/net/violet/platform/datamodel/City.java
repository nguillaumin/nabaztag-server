package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * List of cities, as contributed by the users.
 * (Doesn't seem to be a reference table)
 * 
 *
 */
public interface City extends Record<City> {

	String getName();

	String getCountryCode();

	void setAllInformation(String cityCountryCode, String cityName);

}
