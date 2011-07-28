package net.violet.platform.api.maps.store;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.StoreData;

public class StoreInformationMap extends AbstractAPIMap {

	//private static final Logger LOGGER = Logger.getLogger(StoreData.class);

	public static String ID = "id";
	public static String NAME = "name";
	public static String TYPE = "type";
	public static String PICTURE = "picture";
	public static String ADDRESS = "address";
	public static String ZIPCODE = "zipcode";
	public static String CITY = "city";
	public static String COUNTRY = "country";
	public static String STATUS = "status";
	public static String URL = "url";
	public static String RANK = "rank";
	public static String COMMENT = "comment";

	public StoreInformationMap(APICaller inCaller, StoreData inStore) {
		super(12);

		put(StoreInformationMap.ID, inStore.getApiId(inCaller));
		put(StoreInformationMap.NAME, inStore.getName());
		put(StoreInformationMap.TYPE, inStore.getType());
		put(StoreInformationMap.PICTURE, inStore.getPicturePath());
		put(StoreInformationMap.ADDRESS, inStore.getAddress());
		put(StoreInformationMap.ZIPCODE, inStore.getZipCode());
		put(StoreInformationMap.CITY, inStore.getCityName());
		//TODO CountryInformationMap here
		put(StoreInformationMap.COUNTRY, inStore.getCountryName());
		put(StoreInformationMap.STATUS, inStore.getStatus());
		put(StoreInformationMap.URL, inStore.getUrl());
		put(StoreInformationMap.RANK, inStore.getRank().toString());
		put(StoreInformationMap.COMMENT, inStore.getComment());
	}
}
