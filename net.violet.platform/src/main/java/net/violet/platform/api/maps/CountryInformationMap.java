package net.violet.platform.api.maps;

import net.violet.platform.dataobjects.CountryData;

public class CountryInformationMap extends AbstractAPIMap {

	public static String NAME = "name";
	public static String ISO = "iso";
	public static String CONTINENT = "continent";

	public CountryInformationMap(CountryData inCountryData) {
		super();

		put(CountryInformationMap.NAME, inCountryData.getLabel());
		put(CountryInformationMap.ISO, inCountryData.getPaysCode());
		put(CountryInformationMap.CONTINENT, inCountryData.getContinent().getName());

	}

}
