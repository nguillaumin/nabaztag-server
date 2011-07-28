package net.violet.platform.api.actions.voices;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.VoiceInformationMap;
import net.violet.platform.dataobjects.AbstractLangData;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.util.Constantes;

/**
 * violet.voices.getByLang
 * 
 * Note : we extends here violet.voices.getAll to share its cached voices structure and lazy loading mechanism
 */
public class GetByLang extends AbstractAction {

	private static final Map<String, List<VoiceInformationMap>> FULL_ISO_VOICES;
	private static final Map<String, List<VoiceInformationMap>> SHORT_ISO_VOICES;

	static {
		final Map<String, List<VoiceInformationMap>> fullIsoVoices = new HashMap<String, List<VoiceInformationMap>>();
		final Map<String, List<VoiceInformationMap>> shortIsoVoices = new HashMap<String, List<VoiceInformationMap>>();

		for (final TtsVoiceData voiceData : TtsVoiceData.getAllVoices()) {
			final VoiceInformationMap voiceInfoMap = new VoiceInformationMap(voiceData);

			final String isoCode = voiceData.getLang().getIsoCode();
			final String shortIsoCode = isoCode.substring(0, 2);

			List<VoiceInformationMap> voicesForLang = shortIsoVoices.get(shortIsoCode);
			if (voicesForLang == null) {
				voicesForLang = new LinkedList<VoiceInformationMap>();
				shortIsoVoices.put(shortIsoCode, voicesForLang);
			}

			voicesForLang.add(voiceInfoMap);

			voicesForLang = fullIsoVoices.get(isoCode);

			if (voicesForLang == null) {
				voicesForLang = new LinkedList<VoiceInformationMap>();
				fullIsoVoices.put(isoCode, voicesForLang);
			}

			voicesForLang.add(voiceInfoMap);

		}

		for (final Entry<String, List<VoiceInformationMap>> anEntry : shortIsoVoices.entrySet()) {
			anEntry.setValue(Collections.unmodifiableList(anEntry.getValue()));
		}

		SHORT_ISO_VOICES = Collections.unmodifiableMap(shortIsoVoices);

		for (final Entry<String, List<VoiceInformationMap>> anEntry : fullIsoVoices.entrySet()) {
			anEntry.setValue(Collections.unmodifiableList(anEntry.getValue()));
		}

		FULL_ISO_VOICES = Collections.unmodifiableMap(fullIsoVoices);
	}

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String langIsocode = inParam.getMainParamAsString();

		if (!AbstractLangData.isLanguageCodeValid(langIsocode)) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_LANGUAGE_CODE, langIsocode);
		}

		final List<VoiceInformationMap> theResult;
		if (langIsocode.length() == 2) {
			// We have a 'short' ISO 639-1 language code
			theResult = GetByLang.SHORT_ISO_VOICES.get(langIsocode);
		} else {
			// We have a full code like 'fr-CA' >> filter the french ('fr') voices to keep only the canadian ones
			theResult = GetByLang.FULL_ISO_VOICES.get(langIsocode);
		}

		return (theResult != null) ? theResult : Collections.emptyList();

	}

	/**
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.GET;
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return true;
	}

	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

}
