package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface City extends Record<City> {

	String getName();

	String getCountryCode();

	void setAllInformation(String cityCountryCode, String cityName);

}
