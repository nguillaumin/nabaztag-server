package net.violet.platform.api.actions.voices;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.VoiceInformationMap;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.util.Constantes;

/**
 * violet.voices.get
 * Retrieves the list of all available TTS voices
 * 
 */
public class GetAll extends AbstractAction {

	private static final List<VoiceInformationMap> ALL_VOICES;

	static {
		final List<VoiceInformationMap> allVoices = new LinkedList<VoiceInformationMap>();

		for (final TtsVoiceData voiceData : TtsVoiceData.getAllVoices()) {
			final VoiceInformationMap voiceInfoMap = new VoiceInformationMap(voiceData);
			allVoices.add(voiceInfoMap);
		}

		ALL_VOICES = Collections.unmodifiableList(allVoices);
	}

	/**
	 * @throws InvalidParameterException 
	 * @see net.violet.platform.api.actions.AbstractAction#doProcessRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) {
		return GetAll.ALL_VOICES;
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
