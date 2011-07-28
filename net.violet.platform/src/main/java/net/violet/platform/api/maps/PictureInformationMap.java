package net.violet.platform.api.maps;

import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.util.PictureExtension;

public class PictureInformationMap extends AbstractAPIMap {

	public PictureInformationMap(FilesData inFilePictureData) {
		super(2);

		final PictureExtension thePictureExtension = new PictureExtension(inFilePictureData.getPath());
		put("url", thePictureExtension.getOriginalFilename());
		put("thumb_url", thePictureExtension.getSmallFilename());
	}
}
