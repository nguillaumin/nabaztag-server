package net.violet.platform.datamodel;

import java.util.Map;

import net.violet.db.records.Record;

public interface UserPrefs extends Record<UserPrefs> {

	String USER_PREFS_LAYOUT = "userprefs_layout";

	String getUserprefs_layout();

	void setLayout(String layout);

	Map<String, String> getUserPrefs();
}
