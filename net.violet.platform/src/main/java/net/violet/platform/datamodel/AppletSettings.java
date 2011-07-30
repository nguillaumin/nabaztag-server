package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Store parameters for an RFID book read
 * by a user (current chapter, voice used, etc)
 * 
 *
 */
public interface AppletSettings extends Record<AppletSettings> {

	long getApplet_id();

	String getSettings_Key();

	String getValue();

	void setValue(String inValue);

	void setSecondaryObject(VObject inSecondaryObject);

	VObject getPrimaryAppletSettingsObject();

	VObject getSecondaryObject();

}
