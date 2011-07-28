package net.violet.platform.api.maps;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.util.Constantes;

public class FilesInformationMap extends AbstractAPIMap {

	private static final String ID = "id";
	private static final String URL = "url";
	private static final String BROADCAST = "broadcast";

	public FilesInformationMap(APICaller inCaller, FilesData inFileData) {
		super(2);

		put(FilesInformationMap.ID, inFileData.getApiId(inCaller));
		put(FilesInformationMap.URL, Constantes.BROAD + inFileData.getPath().replaceAll(FilesInformationMap.BROADCAST, net.violet.common.StringShop.EMPTY_STRING));
	}
}
