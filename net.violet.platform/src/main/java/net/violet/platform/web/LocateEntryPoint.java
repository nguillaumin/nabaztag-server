package net.violet.platform.web;

import java.sql.SQLException;

import net.violet.common.utils.RegexTools;
import net.violet.platform.datamodel.TagTmpSite;
import net.violet.platform.datamodel.TagTmpSiteImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.geocoding.geoip.GeoIpViolet;
import net.violet.platform.object.Provisionning;
import net.violet.platform.util.Constantes;

import org.apache.log4j.Logger;

/**
 * Classe permettant de savoir si un objet a contacté la platforme.
 */
public class LocateEntryPoint {

	private static final Logger LOGGER = Logger.getLogger(LocateEntryPoint.class);

	private static String CONFIG = "ping " + Constantes.ZONE_PING + "\n" + "broad " + Constantes.BROAD + "\n" + "xmpp_domain " + Constantes.XMPP_NABAZTAG_DOMAIN + "\n";

	/**
	 * Utilisé dans la jsp locate.jsp, indique où pinguer et où broadcaster pour
	 * les V2 Il sert aussi a dire que l'objet a contacter la platforme Page
	 * utilisé juste pour les mirrors et les v2
	 * 
	 * @param inSerial : serial de l'objet
	 * @param inHardware : hardware de l'objet
	 * @param inVersion : version de l'objet
	 * @param inIp : ip
	 * @return
	 */
	public static String findConfigBySerial(String inSerial, String inHardware, String inVersion, String inIp) {
		// TODO Lorsque l'ancien site my.nabaztag.com disparaitra, changer le
		// code pour centraliser le provisionning dans Hardware
		String config = net.violet.common.StringShop.EMPTY_STRING;
		final HARDWARE theHardware;

		if (RegexTools.isInt(inHardware) && ((theHardware = HARDWARE.findById(Long.parseLong(inHardware))) != null) && theHardware.checkIdentifier(inSerial)) {

			if (HARDWARE.V2 == theHardware) {
				final VObject theObject = Factories.VOBJECT.findBySerial(inSerial);
				if (theObject != null) {
					// Mise à jour de la version
					if (RegexTools.isInt(inVersion)) {
						final long theVersion = Long.parseLong(inVersion);
						if ((theVersion > 0) && (theObject.getObject_bc_version() >= 0) && (theVersion != theObject.getObject_bc_version())) {
							theObject.setObject_bc_version(theVersion);
						}
					}
				} else {
					Provisionning.addObjectInQueue(inIp, inSerial, theHardware);
				}

				config = LocateEntryPoint.CONFIG;

				LocateEntryPoint.LOGGER.debug("config for serial " + inSerial + " : " + config);

			} else if (HARDWARE.MIRROR == theHardware) {

				final VObject theObject = Factories.VOBJECT.findBySerial(inSerial);
				if (theObject == null) {
					final TagTmpSite tagTmpSite = Factories.TAG_TMP_SITE.findBySerial(inSerial);
					final long theTimeInMillis = System.currentTimeMillis();
					if (tagTmpSite == null) {
						try {
							new TagTmpSiteImpl(inSerial, GeoIpViolet.getVioletLocIdByIp(inIp), theTimeInMillis, theHardware, inIp);
						} catch (final SQLException e) {
							LocateEntryPoint.LOGGER.debug(e, e);
						}
					} else {
						tagTmpSite.setLast_day(theTimeInMillis);
						//
					}
				}
			}
		}

		return config;
	}
}
