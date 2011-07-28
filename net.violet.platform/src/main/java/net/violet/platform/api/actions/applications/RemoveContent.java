package net.violet.platform.api.actions.applications;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationContentData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.UserData;

public class RemoveContent extends AbstractAction {

	public static final String CONTENT_ID = "content_id";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException, NoSuchApplicationException {

		final ApplicationData theApplication = ApplicationData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey(), true);

		final UserData theUser = SessionManager.getUserFromSessionId(inParam.getString(ActionParam.SESSION_PARAM_KEY, true), inParam.getCaller());
		if (!theApplication.getOwner().equals(theUser)) {
			throw new ForbiddenException();
		}

		final ApplicationContentData theContent = ApplicationContentData.findByAPIId(inParam.getString(RemoveContent.CONTENT_ID, true), inParam.getCallerAPIKey());
		theContent.delete();

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.DELETE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
