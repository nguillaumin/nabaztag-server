package net.violet.platform.api.maps.store;

import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.StoreCityData;

public class StoreCityInformationMap extends AbstractAPIMap {

	//private static final Logger LOGGER = Logger.getLogger(StoreCityData.class);

	public static String PAYS = "pays";
	public static String CODEPAYS = "codepays";
	public static String NAME = "name";

	public StoreCityInformationMap(StoreCityData inStore) {
		super(3);
		put(StoreCityInformationMap.PAYS, inStore.getCountry().getName());
		put(StoreCityInformationMap.CODEPAYS, inStore.getCountry().getCode());
		put(StoreCityInformationMap.NAME, inStore.getName());
	}
}
