package net.violet.platform.schedulers;

import java.util.Map;
import java.util.Map.Entry;

import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;

public class DailyWithMediaHandler extends DailyHandler {

	public static final String MEDIA_SUFFIXE = ".media";

	@Override
	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		final Map<String, String> theSettings = super.generateSettings(object, settings, callerKey);

		for (final Entry<String, Object> anEntry : settings.entrySet()) {

			if (anEntry.getValue() != null) {
				final SchedulingAtom atom = new SchedulingAtom((Map<String, Object>) anEntry.getValue());
				final String mediaId = String.valueOf((callerKey == null) ? atom.getMedia() : MusicData.findByAPIId(atom.getMedia(), callerKey).getId());
				theSettings.put(anEntry.getKey() + DailyWithMediaHandler.MEDIA_SUFFIXE, mediaId);
			}
		}

		return theSettings;
	}

	@Override
	protected boolean isAtomValid(Object atom) {
		return SchedulingAtom.isValid(atom, false, true);
	}

	@Override
	protected SchedulingAtom generateSchedulingAtom(Map<String, SubscriptionSchedulingSettingsData> settings, Weekday day) {
		final SubscriptionSchedulingSettingsData timeSetting = settings.get(day.getValue());
		final SubscriptionSchedulingSettingsData mediaSetting = settings.get(day.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE);
		return new SchedulingAtom(timeSetting.getValue(), null, mediaSetting.getValue());
	}

}
