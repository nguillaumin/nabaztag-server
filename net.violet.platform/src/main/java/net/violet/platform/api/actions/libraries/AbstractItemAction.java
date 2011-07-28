package net.violet.platform.api.actions.libraries;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchItemException;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.MusicData.MimeTypes;
import net.violet.platform.dataobjects.MusicData.StatusMusic;

public abstract class AbstractItemAction extends AbstractAction {

	protected MimeTypes getLibrarySelectorId(ActionParam inParam, boolean isMandatory) throws InvalidParameterException {

		final String mimeType = inParam.getMainParamAsString();
		if (mimeType == null) {
			return null;
		}

		final MimeTypes mime_type = MimeTypes.getMimeTypeByName(mimeType);
		if ((mime_type == null) && isMandatory) {
			throw new InvalidParameterException(APIErrorMessage.BAD_MIME_TYPE, mimeType);
		}
		return mime_type;
	}

	protected StatusMusic getLibraryTypeId(ActionParam inParam) throws InvalidParameterException {

		final String musicType = inParam.getMainParamAsString();

		final StatusMusic music_type = StatusMusic.getStatusByName(musicType);
		if (music_type == null) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_STATUS_MUSIC, musicType, StatusMusic.getStatusNames());
		}
		return music_type;
	}

	protected StatusMusic getLibraryTypeParam(ActionParam inParam) throws InvalidParameterException {

		final String musicType = inParam.getString("type", true);
		final StatusMusic music_type = StatusMusic.getStatusByName(musicType);
		if (music_type == null) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_STATUS_MUSIC, musicType, StatusMusic.getStatusNames());
		}
		return music_type;
	}

	protected MusicData getMusicData(ActionParam inParam) throws InvalidParameterException, NoSuchItemException {
		final String itemAPIId = inParam.getMainParamAsString();

		final APICaller caller = inParam.getCaller();

		final MusicData theMusicData = MusicData.findByAPIId(itemAPIId, caller.getAPIKey());
		if (theMusicData == null) {
			throw new NoSuchItemException();
		}
		return theMusicData;
	}
}
