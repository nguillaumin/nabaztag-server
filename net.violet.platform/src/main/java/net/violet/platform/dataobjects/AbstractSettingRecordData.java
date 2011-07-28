package net.violet.platform.dataobjects;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.violet.db.records.Record;

/**
 *
 */
public abstract class AbstractSettingRecordData<Rec extends Record<Rec>> extends RecordData<Rec> {

	protected AbstractSettingRecordData(Rec inRecord) {
		super(inRecord);
	}

	protected abstract void update(String inKey, String inValue);

	public static abstract class AbstractSettingableRecordData<Rec extends Record<Rec>, Setting extends AbstractSettingRecordData> extends APIData<Rec> {

		protected AbstractSettingableRecordData(Rec inRecord) {
			super(inRecord);
		}

		public abstract Setting createSetting(String inKey, String inValue);

		protected abstract List<Setting> getAllSettings();

		public List<Setting> dealWithSettings(Map<String, String> inSettings) {
			final List<Setting> theResult = new LinkedList<Setting>();
			final List<Setting> theCurrentSettings = getAllSettings();

			for (final Iterator<Entry<String, String>> theIterator = inSettings.entrySet().iterator(); theIterator.hasNext();) {
				final Entry<String, String> theSetting2Insert = theIterator.next();
				final Setting theSetting;
				if (!theCurrentSettings.isEmpty()) {
					theSetting = theCurrentSettings.remove(0);
					theSetting.update(theSetting2Insert.getKey(), theSetting2Insert.getValue());
				} else {
					theSetting = createSetting(theSetting2Insert.getKey(), theSetting2Insert.getValue());
				}
				theResult.add(theSetting);
			}

			for (final Setting aSetting : theCurrentSettings) {
				aSetting.delete();
			}

			return theResult;
		}
	}

}
