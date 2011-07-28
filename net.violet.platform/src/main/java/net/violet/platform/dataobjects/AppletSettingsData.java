package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.VObject;

public final class AppletSettingsData extends RecordData<AppletSettings> {

	public AppletSettingsData(AppletSettings inAppletSettings) {
		super(inAppletSettings);
	}

	/**
	 * Finds all the appletSetting from the given Object
	 * 
	 * @param inObject
	 * @return a list of {@link AppletSettingsData}
	 */
	public static List<AppletSettingsData> findByAppletSettingIdObject(VObject inObject) {
		if (inObject != null) {
			return AppletSettingsData.generateList(inObject.getAppletServices());
		}
		return Collections.emptyList();
	}

	/**
	 * Generates a list of AppletSettingsData with the given Object
	 * 
	 * @return
	 */
	private static List<AppletSettingsData> generateList(List<AppletSettings> inAppletSetting) {
		final List<AppletSettingsData> appletSettingDataList = new ArrayList<AppletSettingsData>();

		if (inAppletSetting != null) {
			for (final AppletSettings tempApplet : inAppletSetting) {
				appletSettingDataList.add(new AppletSettingsData(tempApplet));
			}
		}

		return appletSettingDataList;
	}

	/**
	 * @return the applet_id attribute
	 */
	public long getAppletId() {
		final AppletSettings theAppletSettings = getRecord();
		if (theAppletSettings != null) {
			return theAppletSettings.getApplet_id();
		}
		return 0;
	}

}
