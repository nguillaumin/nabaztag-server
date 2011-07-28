package net.violet.platform.api.maps;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.dataobjects.DicoData;

public class DicoInformationMap extends AbstractAPIMap {

	public static String KEY = "key";
	public static String LANGUAGE = "language";
	public static String TEXT = "text";
	public static String PAGE = "page";
	public static String ID = "id";

	public DicoInformationMap(DicoData inDicoData, APICaller inCaller) {
		super();
		put(DicoInformationMap.ID, inDicoData.getApiId(inCaller));
		put(DicoInformationMap.KEY, inDicoData.getKey());
		put(DicoInformationMap.LANGUAGE, inDicoData.getLangISO());
		put(DicoInformationMap.TEXT, inDicoData.getText());
		put(DicoInformationMap.PAGE, inDicoData.getPage());
	}
}
