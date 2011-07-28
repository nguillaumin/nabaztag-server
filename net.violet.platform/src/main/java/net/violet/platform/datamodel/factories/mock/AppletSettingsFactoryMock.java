package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.AppletSettingsFactory;
import net.violet.platform.datamodel.mock.AppletSettingsMock;
import net.violet.platform.util.ConvertTools;

public class AppletSettingsFactoryMock extends RecordFactoryMock<AppletSettings, AppletSettingsMock> implements AppletSettingsFactory {

	public AppletSettingsFactoryMock() {
		super(AppletSettingsMock.class);
	}

	public void addCountAppletSettingsByObjects(VObject inPrimaryObject, VObject inSecondaryObject, long inAppletId, String inSettingsKey) {
		final AppletSettings thePreviousValue = getAppletSettingsByObjects(inPrimaryObject, inSecondaryObject, inAppletId, inSettingsKey);
		if (thePreviousValue == null) {
			new AppletSettingsMock(inAppletId, inPrimaryObject, inSecondaryObject, inSettingsKey, "1");
		} else {
			final long count = ConvertTools.atol(thePreviousValue.getValue()) + 1;
			thePreviousValue.setValue(Long.toString(count));// incrémente la valeur
		}
	}

	public List<AppletSettings> getAllAppletSettingsBySecondaryObjectAndKey(VObject inObject, long inAppletId, String inSettingsKey) {
		final List<AppletSettings> theResult = new LinkedList<AppletSettings>();
		final Map<Long, AppletSettings> allSettings = findAllMapped();
		for (final AppletSettings theSettings : allSettings.values()) {
			if ((theSettings.getApplet_id() == inAppletId) && (theSettings.getSecondaryObject().getId() == inObject.getId()) && theSettings.getSettings_Key().equals(inSettingsKey)) {
				theResult.add(theSettings);
			}
		}
		return theResult;
	}

	public AppletSettings getAppletSettingsByObject(VObject inObject, long inAppletId, String inSettingsKey) {
		AppletSettings theResult = null;
		final Map<Long, AppletSettings> allSettings = findAllMapped();
		for (final AppletSettings theSettings : allSettings.values()) {
			if ((theSettings.getApplet_id() == inAppletId) && (theSettings.getPrimaryAppletSettingsObject().getId() == inObject.getId()) && theSettings.getSettings_Key().equals(inSettingsKey)) {
				theResult = theSettings;
				break;
			}
		}
		return theResult;
	}

	public AppletSettings getAppletSettingsByObjects(VObject inPrimaryObject, VObject inSecondaryObject, long inAppletId, String inSettingsKey) {
		AppletSettings theResult = null;
		final long secondObjectId = (inSecondaryObject == null) ? 0 : inSecondaryObject.getId();
		final Map<Long, AppletSettings> allSettings = findAllMapped();
		for (final AppletSettings theSettings : allSettings.values()) {
			if ((theSettings.getApplet_id() == inAppletId) && (theSettings.getPrimaryAppletSettingsObject().getId() == inPrimaryObject.getId()) && (theSettings.getSecondaryObject() != null) && (theSettings.getSecondaryObject().getId() == secondObjectId) && theSettings.getSettings_Key().equals(inSettingsKey)) {
				theResult = theSettings;
				break;
			}
		}
		return theResult;
	}

	public AppletSettings setAppletSettingsByObjects(VObject inPrimaryObject, VObject inSecondaryObject, long inAppletId, String inSettingsKey, String inSettingsValue, long inIsbn) {
		final AppletSettings thePreviousValue = getAppletSettingsByObjects(inPrimaryObject, inSecondaryObject, inAppletId, inSettingsKey);
		if (thePreviousValue == null) {
			if (inSettingsValue != null) {
				new AppletSettingsMock(inAppletId, inPrimaryObject, inSecondaryObject, inSettingsKey, inSettingsValue);
			}
		} else {
			if (inSettingsValue != null) {
				thePreviousValue.setValue(inSettingsValue);
			} else {
				thePreviousValue.delete();
			}
		}
		return thePreviousValue;
	}

	public void deleteSettingsForObject(long inAppletId, VObject inObject) {
		final Map<Long, AppletSettings> allSettings = findAllMapped();
		final List<AppletSettings> toDelete = new LinkedList<AppletSettings>();
		for (final AppletSettings theSettings : allSettings.values()) {
			if ((theSettings.getApplet_id() == inAppletId) && (theSettings.getPrimaryAppletSettingsObject().getId() == inObject.getId())) {
				toDelete.add(theSettings);
			}
		}
		for (final AppletSettings theSettings : toDelete) {
			theSettings.delete();
		}
	}
}
