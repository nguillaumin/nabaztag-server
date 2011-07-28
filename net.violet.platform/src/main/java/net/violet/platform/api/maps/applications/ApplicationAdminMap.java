package net.violet.platform.api.maps.applications;

import java.util.ArrayList;
import java.util.List;

import net.violet.common.StringShop;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationProfileData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.ApplicationTempData;
import net.violet.platform.dataobjects.SiteLangData;

public class ApplicationAdminMap extends AbstractAPIMap {

	private static final long serialVersionUID = 1L;

	public static final String INFO = "info";
	public static final String PROFILE = "profile";
	public static final String URL = "url";
	public static final String LINK = "link";
	public static final String SHORTCUT = "shortcut";
	public static final String LANGUAGES = "languages";
	public static final String VISIBLE = "visible";
	public static final String ANNOUNCEMENT_FILE = "announcement";
	public static final String ANNOUNCEMENT_ID = "announcement_id";
	public static final String PICTURE_ID = "picture_id";
	public static final String ICON_ID = "icon_id";
	public static final String RANK = "rank";

	public ApplicationAdminMap(APICaller inAPICaller, ApplicationData inApplicationData) {
		super(12);

		final ApplicationTempData inTempData = inApplicationData.getTemp();
		final ApplicationProfileData theprofileData = inApplicationData.getProfile();

//		Fill the LINK and the URL from TEMP or VACTION 
		String inUrl;
		final ApplicationSettingData urlSetting = ApplicationSettingData.findByApplicationAndKey(inApplicationData, "url");
		if ((urlSetting != null) && urlSetting.isValid()) {
			inUrl = urlSetting.getValue();
		} else {
			inUrl = StringShop.EMPTY_STRING;
		}

		String inLink = StringShop.EMPTY_STRING;
		if (inTempData != null) {
			inLink = inTempData.getLink();
			put(ApplicationAdminMap.SHORTCUT, inTempData.getShortcut());
			if (StringShop.EMPTY_STRING.equals(inUrl) || "NULL".equals(inUrl)) {
				final ApplicationSetting setting = Factories.APPLICATION_SETTING.findByApplicationAndKey(inApplicationData.getReference(), ApplicationSetting.FeedHandler.FEED_ID);
				if (setting != null) {
					final Feed feed = Factories.FEED.find(Long.parseLong(setting.getValue()));
					if (feed != null) {
						inUrl = feed.getUrl();
					}
				}
			}
		}

		put(ApplicationAdminMap.URL, inUrl);
		put(ApplicationAdminMap.LINK, inLink);

//		Admin Information
		final List<String> listLangs = new ArrayList<String>();
		for (final SiteLangData inSiteLang : inApplicationData.getLangs()) {
			listLangs.add(inSiteLang.getLang_iso_code());
		}
		put(ApplicationAdminMap.LANGUAGES, listLangs);
		put(ApplicationAdminMap.VISIBLE, (inApplicationData.isVisible()) ? "true" : "false");

		final ApplicationProfileMap inAppliProfile = new ApplicationProfileMap(inApplicationData);
		put(ApplicationAdminMap.PROFILE, inAppliProfile);
		final ApplicationInformationMap inAppliInfo = new ApplicationInformationMap(inAPICaller, inApplicationData);
		put(ApplicationAdminMap.INFO, inAppliInfo);

		put(ApplicationAdminMap.ANNOUNCEMENT_ID, String.valueOf(theprofileData.getFile().getApiId(inAPICaller)));
		put(ApplicationAdminMap.PICTURE_ID, String.valueOf(theprofileData.getPictureFile().getApiId(inAPICaller)));
		put(ApplicationAdminMap.ICON_ID, String.valueOf(theprofileData.getIconFile().getApiId(inAPICaller)));
		put(ApplicationAdminMap.ANNOUNCEMENT_FILE, theprofileData.getFile().getPath());
	}
}
