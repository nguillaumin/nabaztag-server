package net.violet.platform.applications;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.dataobjects.VObjectData;

public class VoiceHandler implements ApplicationHandler {

	private final ApplicationData application;

	protected VoiceHandler(ApplicationData application) {
		this.application = application;
	}

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		final Map<String, Object> theSettings = new HashMap<String, Object>(settings);

		final Object voiceSetting = theSettings.get("voice");
		if (voiceSetting != null) {
			final TtsVoiceData theVoice = TtsVoiceData.findTtsVoiceByName(voiceSetting.toString());
			if ((theVoice != null) && theVoice.isValid()) {
				theSettings.put("lang", theVoice.getLang().getIETFCode());
			}
		}

		return SubscriptionData.create(this.application, object, theSettings);
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		final Map<String, Object> theSettings = new HashMap<String, Object>(settings);

		final Object voiceSetting = theSettings.get("voice");
		if (voiceSetting != null) {
			final TtsVoiceData theVoice = TtsVoiceData.findTtsVoiceByName(voiceSetting.toString());
			if ((theVoice != null) && theVoice.isValid()) {
				theSettings.put("lang", theVoice.getLang().getIETFCode());
			}
		}

		subscription.setSettings(theSettings);

	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) {
		//nothing to do
	}

	public void delete(SubscriptionData subscription) {
		subscription.delete();
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		return subscription.getSettings();
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return null;
	}

}
