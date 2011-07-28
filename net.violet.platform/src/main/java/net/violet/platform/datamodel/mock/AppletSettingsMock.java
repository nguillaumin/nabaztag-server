package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.VObject;

public class AppletSettingsMock extends AbstractMockRecord<AppletSettings, AppletSettingsMock> implements AppletSettings {

	private final long mAppletId;
	private final VObject mPrimaryObject;
	private VObject mSecondaryObject;
	private final String mSettingsKey;
	private String mValue;

	public AppletSettingsMock(long inAppletId, VObject inPrimaryObject, String inKey) {
		this(inAppletId, inPrimaryObject, null, inKey, null);
	}

	public AppletSettingsMock(long inAppletId, VObject inPrimaryObject, VObject inSecondaryObject, String inKey, String inValue) {
		super(0);
		this.mAppletId = inAppletId;
		this.mPrimaryObject = inPrimaryObject;
		this.mSecondaryObject = inSecondaryObject;
		this.mSettingsKey = inKey;
		this.mValue = inValue;
	}

	public long getApplet_id() {
		return this.mAppletId;
	}

	public VObject getPrimaryAppletSettingsObject() {
		return this.mPrimaryObject;
	}

	public VObject getSecondaryObject() {
		return this.mSecondaryObject;
	}

	public String getSettings_Key() {
		return this.mSettingsKey;
	}

	public String getValue() {
		return this.mValue;
	}

	public void setSecondaryObject(VObject inSecondaryObject) {
		this.mSecondaryObject = inSecondaryObject;
	}

	public void setValue(String inValue) {
		this.mValue = inValue;
	}
}
