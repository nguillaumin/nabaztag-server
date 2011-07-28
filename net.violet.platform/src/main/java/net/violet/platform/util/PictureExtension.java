package net.violet.platform.util;

import net.violet.platform.datamodel.MimeType;

public class PictureExtension {

	private static final String jpgExtension = "(_B)?\\" + MimeType.MIME_TYPES.JPEG.getFullExtension();
	private static final String jpgSmallExtension = "_S" + MimeType.MIME_TYPES.JPEG.getFullExtension();

	private final String originalFilename;
	private final String smallFilename;

	public PictureExtension(String inName) {
		this.originalFilename = inName;
		this.smallFilename = inName.replaceAll(PictureExtension.jpgExtension, PictureExtension.jpgSmallExtension);
	}

	public String getOriginalFilename() {
		return this.originalFilename;
	}

	public String getSmallFilename() {
		return this.smallFilename;
	}

}
