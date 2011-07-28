package net.violet.platform.object;

import java.sql.SQLException;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.TagTmpSite;
import net.violet.platform.datamodel.TagTmpSiteImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Ztamp;
import net.violet.platform.datamodel.ZtampBatch;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.geocoding.geoip.GeoIpViolet;
import net.violet.platform.handlers.rfid.RfidInitializerFactory;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.util.Constantes;

import org.apache.log4j.Logger;

public class Provisionning {

	private static final Logger LOGGER = Logger.getLogger(Provisionning.class);

	private static final long TIMEOUT = 1800000;

	private static final String VIRTUAL_SERIAL_HARDWARE_3 = "FFFFFF000003";
	public static final String VIRTUAL_SERIAL_HARDWARE_4 = "FFFFFF000004";
	private static final String NEW = "NEW";
	private static final String RFID = "RFID";

	/**
	 * fonction qui permet d'inscrire l'objet dans la table d'attente
	 * (tag_tmp_site) et de créer le message à intervalle régulier de 30 minutes
	 * disant de venir s'inscrire sur le site
	 * 
	 * @param inIp : ip afin de choisir la bonne langue pour le message
	 * @param inSerial : serial de l'objet
	 * @param inHardware : hardware de l'objet
	 * @return null si erreur sinon le message a envoyé a l'objet ( paquet vide,
	 *         ou message d'accueil)
	 */
	public static MessageDraft addObjectInQueue(String inIp, String inSerial, HARDWARE inHardware) {
		final long theTimeInMillis = System.currentTimeMillis();

		final TagTmpSite updateTmpSite = Factories.TAG_TMP_SITE.findBySerial(inSerial);

		long langId = 0L;
		if ((updateTmpSite == null) || (theTimeInMillis - updateTmpSite.getLast_day() > Provisionning.TIMEOUT)) {
			// le lapin va dire qu'il n'est pas inscrit
			final String cp = GeoIpViolet.getCountryCodeByIp(inIp);

			final CountryData thePays = CountryData.findByCode(cp);
			langId = ((thePays != null) && thePays.isValid()) ? thePays.getMainLangId() : ObjectLangData.DEFAULT_OBJECT_LANGUAGE.getId();

			if (updateTmpSite != null) {
				updateTmpSite.setLast_day(theTimeInMillis);
			} else {
				// objet non inscrit, on l'enregistre dans tag_tmp_site
				Provisionning.LOGGER.debug("objet non inscrit, on l'enregistre dans tag_tmp_site");
				try {
					new TagTmpSiteImpl(inSerial, GeoIpViolet.getVioletLocIdByIp(inIp), theTimeInMillis, inHardware, inIp);
				} catch (final SQLException e) {
					Provisionning.LOGGER.debug(e, e);
				}
			}
		}

		final boolean isHardwareV1 = (inHardware == HARDWARE.V1) || (inHardware == HARDWARE.DALDAL);

		// TODO Mettre en cache les messages par langue et hardware
		String theSerial = Provisionning.VIRTUAL_SERIAL_HARDWARE_4;
		if (isHardwareV1) {
			theSerial = Provisionning.VIRTUAL_SERIAL_HARDWARE_3;
		}

		final VObject theObject = Factories.VOBJECT.findBySerial(theSerial);
		if (theObject == null) {
			Provisionning.LOGGER.fatal("Objet virtuel n'existe plus : " + theSerial);
		} else {

			final MessageDraft theMessage = new MessageDraft(theObject);

			if (langId != 0L) {
				try {
					final Files musicFile = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.RECO, Factories.LANG.find(langId)).get(Provisionning.NEW).get(0).getFiles();
					theMessage.addAudio(musicFile.getPath(), false, true);
					theMessage.addChoreography(musicFile.getPath2chor(), false);
					if (isHardwareV1) {
						theMessage.setEventID(-1L);
					}
					theMessage.setTTLInSecond(Constantes.QUEUE_TTL_FIVE_MINUTES);
				} catch (final IllegalArgumentException e) {
					Provisionning.LOGGER.fatal(e, e);
				}
			} else {
				if (isHardwareV1) {
					theMessage.setEventID(new Long(Message.EV_DUMMY)); // Valeur différente de 0 et non null afin de construire le paquet pour les
					// v1
				}
			}
			return theMessage;
		}

		return null;
	}

	/**
	 * This method is called to create a rfid object.
	 * @param inReader
	 * @param inSerial
	 * @return
	 */
	public static VObjectData provisionRfid(VObjectData inReader, String inSerial) {
		try {
			final HARDWARE theHardware;
			final ZtampBatch theBatch;
			final String thePrefix;
			final Files thePictureHardware;
			final String label;

			final Ztamp theZtamp = Factories.ZTAMP.findBySerial(inSerial);
			if (theZtamp != null) {
				theHardware = theZtamp.getHardware();
				theBatch = theZtamp.getBatch();
				thePictureHardware = theZtamp.getVisual();
				if (theBatch != null) {
					thePrefix = theBatch.getNamePrefix();
					if (HARDWARE.OTHER_RFID.equals(theHardware)) { //other rfid (violet partner) it's stored in ztamp table
						label = thePrefix;
					} else {
						label = theHardware.getLabel();
					}
				} else {
					thePrefix = Provisionning.RFID;
					label = theHardware.getLabel();
					Provisionning.LOGGER.fatal("batch_id invalid for ztamp " + inSerial);
				}
			} else {
				theHardware = HARDWARE.OTHER_RFID;
				label = theHardware.getLabel();
				thePrefix = Provisionning.RFID;
				theBatch = null;
				thePictureHardware = null;
			}

			final VObject theRfid = Factories.VOBJECT.createObject(theHardware, inSerial, thePrefix + inSerial, label, inReader.getOwner().getReference(), inReader.getLocation(), VObject.MODE_PING_INACTIVE, VObject.STATUS_NORMAL, thePictureHardware);
			if (theBatch != null) {
				RfidInitializerFactory.initRfid(theRfid, theBatch);
			}

			return VObjectData.getData(theRfid);

		} catch (final Exception anException) {
			Provisionning.LOGGER.fatal(anException, anException);
		}

		return null;
	}
}
