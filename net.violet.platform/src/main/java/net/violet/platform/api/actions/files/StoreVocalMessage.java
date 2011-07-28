package net.violet.platform.api.actions.files;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.VocalMessageConversionFailedException;
import net.violet.platform.api.maps.FilesInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.UserData;

import org.apache.log4j.Logger;

/**
 * This action is used to convert flv file (vocal message) into mp3.
 * optional, save in user's library if session param is given 
 */
public class StoreVocalMessage extends AbstractAction {

	static final Logger LOGGER = Logger.getLogger(StoreVocalMessage.class);

	@Override
	public Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, VocalMessageConversionFailedException, InternalErrorException {

		final String theUserSession = inParam.getString(ActionParam.SESSION_PARAM_KEY, false);
		final String theTitle = inParam.getString("title", true);
		final String theUrl = inParam.getString("url", true);

		UserData theUserData = null;
		if (theUserSession != null) {// save vocal message in user's library
			theUserData = SessionManager.getUserFromSessionId(theUserSession, inParam.getCaller());
		}

		final FilesData theAudioFile = FilesData.postFLV(theUserData, theTitle, theUrl);

		return new FilesInformationMap(inParam.getCaller(), theAudioFile);
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
