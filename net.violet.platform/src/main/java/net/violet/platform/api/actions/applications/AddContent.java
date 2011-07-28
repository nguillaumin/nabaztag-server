package net.violet.platform.api.actions.applications;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationContentData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.UserData;

public class AddContent extends AbstractAction {

	public static final String FILE_ID = "file_id";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws NoSuchFileException, InvalidParameterException, ForbiddenException, InvalidSessionException, NoSuchApplicationException {

		final ApplicationData theApplication = ApplicationData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey(), true);

		final UserData theUser = SessionManager.getUserFromSessionId(inParam.getString(ActionParam.SESSION_PARAM_KEY, true), inParam.getCaller());
		if (!theApplication.getOwner().equals(theUser)) {
			throw new ForbiddenException();
		}

		final FilesData theFile = FilesData.getFilesData(inParam.getString(AddContent.FILE_ID, true), inParam.getCallerAPIKey());

		final ApplicationContentData theNewContent = ApplicationContentData.create(theApplication, theFile);

		if ((theNewContent == null) || !theNewContent.isValid()) {
			return null;
		}

		return theNewContent.getApiId(inParam.getCaller());
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
