package net.violet.platform.geocoding.geoip;

import net.violet.platform.util.Constantes;

public class GeoIpViolet {

	private static LookupService lookupService = new LookupService(Constantes.GEOIPDAT);

	public static String getCountryCodeByIp(String ip) {
		final Location location = GeoIpViolet.lookupService.getLocation(ip);
		if (location != null) {
			return location.countryCode.toLowerCase();
		}

		return "us";
	}

	public static String getVioletLocIdByIp(String ip) {
		final Location location = GeoIpViolet.lookupService.getLocation(ip);
		if (location != null) {
			return location.countryCode.toLowerCase() + "/" + location.city;
		}

		return "unknown/unknown";
	}
}
