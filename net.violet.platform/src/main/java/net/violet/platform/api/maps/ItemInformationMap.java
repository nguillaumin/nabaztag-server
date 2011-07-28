package net.violet.platform.api.maps;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.dataobjects.MusicData;

public class ItemInformationMap extends AbstractAPIMap {

	public ItemInformationMap(APICaller inCaller, MusicData inMusic) {
		super(5);

		put("id", inMusic.getApiId(inCaller));
		if (inMusic.getOwner() != null) {
			put("owner", inMusic.getOwner().getApiId(inCaller));
		}
		put("url", inMusic.getListenPath());
		put("mime_type", inMusic.getMusicMimeType());
		put("creation_date", inMusic.getCreationDate());
	}
}
