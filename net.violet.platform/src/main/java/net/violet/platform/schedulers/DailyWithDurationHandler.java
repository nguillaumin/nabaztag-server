package net.violet.platform.schedulers;

import java.util.Map;
import java.util.Map.Entry;

import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;

public class DailyWithDurationHandler extends DailyHandler {

	public static final String DURATION_SUFFIXE = ".duration";

	@Override
	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		final Map<String, String> theSettings = super.generateSettings(object, settings, callerKey);

		for (final Entry<String, Object> anEntry : settings.entrySet()) {

			if (anEntry.getValue() != null) {
				final SchedulingAtom atom = new SchedulingAtom((Map<String, Object>) anEntry.getValue());
				theSettings.put(anEntry.getKey() + DailyWithDurationHandler.DURATION_SUFFIXE, String.valueOf(atom.getDuration()));
			}
		}

		return theSettings;
	}

	@Override
	protected boolean isAtomValid(Object atom) {
		return SchedulingAtom.isValid(atom, true, false);
	}

	@Override
	protected SchedulingAtom generateSchedulingAtom(Map<String, SubscriptionSchedulingSettingsData> settings, Weekday day) {
		final SubscriptionSchedulingSettingsData timeSetting = settings.get(day.getValue());
		final SubscriptionSchedulingSettingsData durationSetting = settings.get(day.getValue() + DailyWithDurationHandler.DURATION_SUFFIXE);
		return new SchedulingAtom(timeSetting.getValue(), Integer.parseInt(durationSetting.getValue()), null);
	}

}
