package net.violet.platform.geocoding.geoip;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.violet.platform.util.Constantes;

public class GeoIpViolet {

	private static final Log LOGGER = LogFactory.getLog(GeoIpViolet.class);
	
	private static LookupService lookupService = new LookupService(Constantes.GEOIPDAT);

	public static String getCountryCodeByIp(String ip) {
		try {
			final Location location = GeoIpViolet.lookupService.getLocation(ip);
			if (location != null) {
				return location.countryCode.toLowerCase();
			}
		} catch (Exception e) {
			LOGGER.warn("Country lookup failed for ip '" + ip + "'. Fall back to 'us'");
		}
		return "us";
		
	}

	public static String getVioletLocIdByIp(String ip) {
		try {
			final Location location = GeoIpViolet.lookupService.getLocation(ip);
			if (location != null) {
				return location.countryCode.toLowerCase() + "/" + location.city;
			}
		} catch (Exception e) {
			LOGGER.warn("Violet location lookup failed for ip '"+ip+"'. Fall back to 'unknown/unknown'");
		}

		return "unknown/unknown";
	}
}
