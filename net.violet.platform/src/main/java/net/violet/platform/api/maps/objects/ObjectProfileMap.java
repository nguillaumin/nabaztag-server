package net.violet.platform.api.maps.objects;

import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.api.maps.GPSInformationMap;
import net.violet.platform.api.maps.PictureInformationMap;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectProfileData;
import net.violet.platform.dataobjects.VObjectData;

public class ObjectProfileMap extends AbstractAPIMap {

	public static final String GPS_LOCALIZATION = "gps_localization";
	public static final String DESCRIPTION = "description";
	public static final String PICTURE = "picture";
	public static final String LABEL = "label";

	public ObjectProfileMap(VObjectData inObjectData) {
		super(2);

		final ObjectProfileData theObjectProfileData = inObjectData.getProfile();

		final FilesData thePictureFiles = theObjectProfileData.getPictureFiles();
		if ((thePictureFiles != null) && thePictureFiles.isValid()) {
			put(ObjectProfileMap.PICTURE, new PictureInformationMap(thePictureFiles));
		} else {
			putNullValue(ObjectProfileMap.PICTURE);
		}

		final String theLatitude = theObjectProfileData.getLatitudeGPS();

		if (theLatitude != null) {
			put(ObjectProfileMap.GPS_LOCALIZATION, new GPSInformationMap(theObjectProfileData));
		} else {
			putNullValue(ObjectProfileMap.GPS_LOCALIZATION);
		}

		put(ObjectProfileMap.DESCRIPTION, theObjectProfileData.getDescription());

		put(ObjectProfileMap.LABEL, theObjectProfileData.getLabel());

	}

}
