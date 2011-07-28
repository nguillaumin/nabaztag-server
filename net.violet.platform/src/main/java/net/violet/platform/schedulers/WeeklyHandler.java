package net.violet.platform.schedulers;

import java.util.HashMap;
import java.util.Map;

import net.violet.common.StringShop;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;

public class WeeklyHandler implements SchedulingHandler {

	public static final String WEEK_DAY = "weekday";

	public void deleteElements(SubscriptionSchedulingData scheduling) {
		// nothing to do
	}

	public void executeWhenDone(SubscriptionSchedulingData scheduling) {
		// nothing to do right here
	}

	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		final Map<String, String> theResult = new HashMap<String, String>();
		final SchedulingAtom atom = new SchedulingAtom(settings);
		theResult.put(settings.get(WeeklyHandler.WEEK_DAY).toString(), atom.getFormattedTime());
		return theResult;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings, String callerKey) throws InvalidSettingException, MissingSettingException {
		final Object daySetting = settings.get(WeeklyHandler.WEEK_DAY);
		if (daySetting == null) {
			throw new MissingSettingException(WeeklyHandler.WEEK_DAY);
		}

		if (!DailyHandler.Weekday.isValidLabel(daySetting.toString())) {
			throw new InvalidSettingException(WeeklyHandler.WEEK_DAY, daySetting.toString());
		}

		if (!SchedulingAtom.isValid(settings, false, false)) {
			throw new InvalidSettingException("not a valid atom", StringShop.EMPTY_STRING);

		}
	}

	public SchedulingInformationMap getSchedulingInformation(SubscriptionSchedulingData scheduling, APICaller caller) {
		final SchedulingInformationMap infoMap = new SchedulingInformationMap(scheduling);

		final SubscriptionSchedulingSettingsData setting = SubscriptionSchedulingSettingsData.findAllBySubscriptionScheduling(scheduling).get(0);
		infoMap.put(WeeklyHandler.WEEK_DAY, setting.getKey());
		final SchedulingAtom atom = new SchedulingAtom(setting.getValue(), null, null);
		infoMap.putAll(atom.getSchedulingAtomMap(caller));

		return infoMap;
	}

}
