package net.violet.platform.api.actions.voices;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.TTSGenerationFailedException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.dataobjects.UserData;

import org.apache.log4j.Logger;

/**
 * This action is used to synthetize TTS in order to listen
 */
@Deprecated
// il est encore utilisé par le mirware, nouvelle méthode dans le package files  
public class Synthetize extends AbstractAction {

	static final Logger LOGGER = Logger.getLogger(Synthetize.class);

	@Override
	public Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, TTSGenerationFailedException, InternalErrorException {

		final String tts = inParam.getString("text", true);
		final String voice = inParam.getString("voice", false);

		final Integer ttl = inParam.getInt("time_to_live", false); // default value : 24hours from now
		final String languageCode = inParam.getString("language", null);

		final UserData theUserData = null;
		final String theLabel = null;

		final TtsVoiceData voiceData = TtsVoiceData.getByParams(voice, languageCode, tts, null);

		if (Synthetize.LOGGER.isDebugEnabled()) {
			Synthetize.LOGGER.debug("violet.voices.synthetize : generating tts message \"" + tts + "\" with voice : " + voiceData.getLibelle());
		}

		final FilesData ttsFileData = FilesData.postTTS(theUserData, tts, theLabel, voiceData, false, false, false);

		if (ttl == null) {
			ttsFileData.scheduleDeletion();
		}

		return ttsFileData.getPath();
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_ALL;
	}
}
