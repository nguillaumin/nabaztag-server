package net.violet.platform.api.maps;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.TtsVoiceData;

public class VoiceInformationMap extends AbstractAPIMap {

	public VoiceInformationMap(TtsVoiceData inTTS) {
		super(3);

		final Lang theLang = inTTS.getLang();
		put("id", inTTS.getVoice());
		put("language", theLang.getIsoCode());
		put("title", inTTS.getLibelle());
	}
}
