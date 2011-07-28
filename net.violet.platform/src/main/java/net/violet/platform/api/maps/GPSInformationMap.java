package net.violet.platform.api.maps;

import net.violet.platform.dataobjects.ObjectProfileData;

public class GPSInformationMap extends AbstractAPIMap {

	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";

	public GPSInformationMap(ObjectProfileData inObjectProfileData) {
		super(2);
		put(GPSInformationMap.LONGITUDE, inObjectProfileData.getLongitudeGPS());
		put(GPSInformationMap.LATITUDE, inObjectProfileData.getLatitudeGPS());
	}
}
