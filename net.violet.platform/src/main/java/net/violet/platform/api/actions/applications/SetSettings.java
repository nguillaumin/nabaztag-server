package net.violet.platform.api.actions.applications;

import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Constantes;

public class SetSettings extends AbstractApplicationAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException, NoSuchApplicationException {

		final ApplicationData theApplication = ApplicationData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey(), true);

		final UserData theUser = SessionManager.getUserFromSessionId(inParam.getString(ActionParam.SESSION_PARAM_KEY, true), inParam.getCaller());

		if (!theApplication.getOwner().equals(theUser)) {
			throw new ForbiddenException();
		}

		final Map<String, String> theSettings = inParam.getMap("settings", true);
		theApplication.dealWithSettings(theSettings);

		return null;
	}

	/**
	 * Cached information expires after one day
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	/**
	 * YES ! this information may be cached !
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
