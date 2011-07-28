package net.violet.platform.api.maps.news;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.NewsData;

public class NewsInformationMap extends AbstractAPIMap {

	public static String ID = "id";
	public static String TITLE = "title";
	public static String PRODUCT = "product";
	public static String LANGUAGE = "language";
	public static String ABSTRACT = "abstract";
	public static String INTRO = "intro";
	public static String BODY = "body";
	public static String DATE_PUB = "date_pub";
	public static String DATE_EXP = "date_exp";
	public static String PICTURE_SMALL = "picture_small";
	public static String PICTURE_BIG = "picture_big";

	public NewsInformationMap(APICaller inCaller, NewsData inNewsData) {
		super();

		put(NewsInformationMap.ID, inNewsData.getApiId(inCaller));
		put(NewsInformationMap.TITLE, inNewsData.getTitle());
		put(NewsInformationMap.PRODUCT, inNewsData.getProduct());
		put(NewsInformationMap.LANGUAGE, inNewsData.getLanguage());
		put(NewsInformationMap.ABSTRACT, inNewsData.getAbstract());
		put(NewsInformationMap.INTRO, inNewsData.getIntro());
		put(NewsInformationMap.BODY, inNewsData.getBody());
		put(NewsInformationMap.DATE_PUB, inNewsData.getDatePub());
		put(NewsInformationMap.DATE_EXP, inNewsData.getDateExp());
		put(NewsInformationMap.PICTURE_SMALL, inNewsData.getPictureSmall());
		put(NewsInformationMap.PICTURE_BIG, inNewsData.getPictureBig());
	}

}
