package net.violet.platform.util;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.SubscriptionData;

import org.apache.log4j.Logger;

/**
 * @author mleblanc
 */
public final class StaticTools {

	private static final Logger LOGGER = Logger.getLogger(StaticTools.class);

	// TODO used by two f***g jsps !
	public static Map<String, String> getServiceFromSubscription(String inId, Lang dico_lang) {

		SubscriptionData subscription = null;
		ApplicationData application = null;
		try {
			final int subId = Integer.parseInt(inId);
			subscription = SubscriptionData.find(subId);
			application = ApplicationData.findById(subscription.getReference().getApplication().getId());
			StaticTools.LOGGER.info("Application = " + application);

		} catch (final NumberFormatException e) {
			// TODO
		}

		final Map<String, String> srvData = new HashMap<String, String>();

		if ((subscription != null) && (application != null)) {

			final String title = subscription.getSrv_name();
			if (title.startsWith(DicoTools.LOC_PREFIX)) {
				srvData.put("srv_name", DicoTools.dico_if(dico_lang, title));
			} else {
				srvData.put("srv_name", title);
			}

			final String theDescription = subscription.getDescription();
			if (theDescription.startsWith(DicoTools.LOC_PREFIX)) {
				srvData.put("srv_desc", DicoTools.dico_if(dico_lang, theDescription));

				if (theDescription.contains("description")) {
					srvData.put("srv_desc_long", DicoTools.dico_if(dico_lang, theDescription.replace("description", "long_description")));
					srvData.put("srv_manual", DicoTools.dico_if(dico_lang, theDescription.replace("description", "how_to")));
				} else {
					srvData.put("srv_desc_long", StringShop.EMPTY_STRING);
					srvData.put("srv_manual", StringShop.EMPTY_STRING);
				}

			} else {
				srvData.put("srv_desc", theDescription);
				srvData.put("srv_desc_long", StringShop.EMPTY_STRING);
				srvData.put("srv_manual", StringShop.EMPTY_STRING);
			}

			srvData.put("srv_img", application.getTemp().getImage());
			srvData.put("srv_icone", application.getTemp().getIcone());
			srvData.put("srv_shortCut", application.getTemp().getShortcut());

			final ApplicationSettingData urlSetting = ApplicationSettingData.findByApplicationAndKey(application, "url");
			if (urlSetting != null && urlSetting.isValid()) {
				// cas specifique des webradios qui ont toute la meme page de
				// manual et une reco specifique
				// ATTENTION : verifier dans la jsp que srv_vocal ne vaut pas
				// NOLOC
				srvData.put("srv_manual", DicoTools.dico_if(dico_lang, "LOC_srv_webradio/how_to"));
				if (theDescription.contains(DicoTools.LOC_PREFIX) && theDescription.contains("description")) {
					srvData.put("srv_vocal", DicoTools.dico_if(dico_lang, theDescription.replace("description", "vocal")));
				} else {
					srvData.put("srv_vocal", "NOLOC");
				}
			}
		}

		return srvData;
	}

	// see above!
	public static Map<String, String> getServiceFromApplication(String inId, Lang dico_lang) {
		ApplicationData application = null;
		try {
			final int subId = Integer.parseInt(inId);
			application = ApplicationData.findById(subId);
		} catch (final NumberFormatException e) {
			// TODO
		}

		final Map<String, String> srvData = new HashMap<String, String>();

		if (application != null) {

			final String title = application.getProfile().getTitle();
			if (title.startsWith(DicoTools.LOC_PREFIX)) {
				srvData.put("srv_name", DicoTools.dico_if(dico_lang, title));
			} else {
				srvData.put("srv_name", title);
			}

			final String theDescription = application.getProfile().getDescription();
			if (theDescription.startsWith(DicoTools.LOC_PREFIX)) {
				srvData.put("srv_desc", DicoTools.dico_if(dico_lang, theDescription));

				if (theDescription.contains("description")) {
					srvData.put("srv_desc_long", DicoTools.dico_if(dico_lang, theDescription.replace("description", "long_description")));
					srvData.put("srv_manual", DicoTools.dico_if(dico_lang, theDescription.replace("description", "how_to")));
				} else {
					srvData.put("srv_desc_long", StringShop.EMPTY_STRING);
					srvData.put("srv_manual", StringShop.EMPTY_STRING);
				}

			} else {
				srvData.put("srv_desc", theDescription);
				srvData.put("srv_desc_long", StringShop.EMPTY_STRING);
				srvData.put("srv_manual", StringShop.EMPTY_STRING);
			}

			srvData.put("srv_img", application.getTemp().getImage());
			srvData.put("srv_icone", application.getTemp().getIcone()); // srvList . getSrvType (). getNametype ());
			srvData.put("srv_shortCut", application.getTemp().getShortcut());

			final ApplicationSettingData urlSetting = ApplicationSettingData.findByApplicationAndKey(application, "url");
			if (urlSetting != null && urlSetting.isValid()) {
				// cas specifique des webradios qui ont toute la meme page de
				// manual et une reco specifique
				// ATTENTION : verifier dans la jsp que srv_vocal ne vaut pas
				// NOLOC
				srvData.put("srv_manual", DicoTools.dico_if(dico_lang, "LOC_srv_webradio/how_to"));
				if (theDescription.contains(DicoTools.LOC_PREFIX) && theDescription.contains("description")) {
					srvData.put("srv_vocal", DicoTools.dico_if(dico_lang, theDescription.replace("description", "vocal")));
				} else {
					srvData.put("srv_vocal", "NOLOC");
				}
			}
		}

		return srvData;
	}

}
