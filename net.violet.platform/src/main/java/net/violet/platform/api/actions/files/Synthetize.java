package net.violet.platform.api.actions.files;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.TTSGenerationFailedException;
import net.violet.platform.api.maps.FilesInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.dataobjects.UserData;

import org.apache.log4j.Logger;

/**
 * This action is used to synthetize TTS into mp3
 * optional, save in user's library if session param is given
 */
public class Synthetize extends AbstractAction {

	static final Logger LOGGER = Logger.getLogger(Synthetize.class);

	@Override
	public Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, TTSGenerationFailedException, InternalErrorException, ForbiddenException, InvalidSessionException {

		final String tts = inParam.getString("text", true);
		final String voice = inParam.getString("voice", false);

		final Integer ttl = inParam.getInt("time_to_live", false); // default value : 24hours from now
		final String languageCode = inParam.getString("language", null);

		final String theUserSession = inParam.getString(ActionParam.SESSION_PARAM_KEY, false);

		UserData theUserData = null;
		String theLabel = null;
		boolean save = false;

		if (theUserSession != null) {// save tts in user's library

			theUserData = SessionManager.getUserFromSessionId(theUserSession, inParam.getCaller());
			theLabel = tts;
			if (theLabel.length() > 200) {
				theLabel = theLabel.substring(0, 200) + "...";
			}
			save = true;
		}

		final TtsVoiceData voiceData = TtsVoiceData.getByParams(voice, languageCode, tts, null);

		if (Synthetize.LOGGER.isDebugEnabled()) {
			Synthetize.LOGGER.debug("violet.voices.synthetize : generating tts message \"" + tts + "\" with voice : " + voiceData.getLibelle());
		}

		final FilesData ttsFileData = FilesData.postTTS(theUserData, tts, theLabel, voiceData, save, save, save);

		if ((ttl == null) && (save == false)) {
			ttsFileData.scheduleDeletion();
		}

		return new FilesInformationMap(inParam.getCaller(), ttsFileData);
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
