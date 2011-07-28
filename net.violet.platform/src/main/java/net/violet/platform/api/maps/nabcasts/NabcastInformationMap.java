package net.violet.platform.api.maps.nabcasts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationProfileData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.util.StringShop;

public class NabcastInformationMap extends AbstractAPIMap {

	public NabcastInformationMap(ApplicationData nabcast, APICaller caller) {
		super();

		final ApplicationProfileData theProfile = nabcast.getProfile();

		put("id", nabcast.getApiId(caller));
		put("title", theProfile.getTitle());
		put("author", nabcast.getOwner().getApiId(caller));
		put("description", theProfile.getDescription());
		final List<String> theLanguages = new LinkedList<String>();
		for (final SiteLangData aLang : nabcast.getLangs()) {
			theLanguages.add(aLang.getLang_iso_code());
		}
		put("picture", theProfile.getPictureFile().getApiId(caller));

		put("languages", theLanguages);
		put("category", nabcast.getApplicationCategory().getApiId(caller));

		final ApplicationSettingData modeSetting = ApplicationSettingData.findByApplicationAndKey(nabcast, "modes");
		put("modes", Arrays.asList(modeSetting.getValue().split(StringShop.COMMA)));

		// TODO manque signature, hardware
	}

}
