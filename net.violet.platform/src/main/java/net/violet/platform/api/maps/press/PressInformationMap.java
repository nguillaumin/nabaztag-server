package net.violet.platform.api.maps.press;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.PressData;

public class PressInformationMap extends AbstractAPIMap {

	public static String ID = "id";
	public static String TITLE = "title";
	public static String PRODUCT = "product";
	public static String LANGUAGE = "language";
	public static String ABSTRACT = "abstract";
	public static String URL = "url";
	public static String PICTURE = "picture";

	public PressInformationMap(APICaller inCaller, PressData inPressData) {

		put(PressInformationMap.ID, inPressData.getApiId(inCaller));
		put(PressInformationMap.TITLE, inPressData.getTitle());
		put(PressInformationMap.PRODUCT, inPressData.getProduct());
		put(PressInformationMap.LANGUAGE, inPressData.getLanguage());
		put(PressInformationMap.ABSTRACT, inPressData.getAbstract());
		put(PressInformationMap.URL, inPressData.getUrl());
		put(PressInformationMap.PICTURE, inPressData.getPicture());
	}

}
