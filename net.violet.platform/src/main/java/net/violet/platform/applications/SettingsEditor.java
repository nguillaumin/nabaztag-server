package net.violet.platform.applications;

import java.util.List;
import java.util.Map;

import net.violet.platform.dataobjects.VObjectData;

public interface SettingsEditor {

	void editSettings(VObjectData object, Map<String, Object> settings, List<Map<String, Object>> originalSchedulings, String callerKey, boolean updateSubscription);
}
