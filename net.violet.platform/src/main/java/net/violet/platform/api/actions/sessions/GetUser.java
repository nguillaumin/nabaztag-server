package net.violet.platform.api.actions.sessions;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.maps.persons.PersonInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.UserData;

public class GetUser extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException {
		final UserData theSessionUser = SessionManager.getUserFromSessionId(inParam.getMainParamAsString(), inParam.getCaller());
		return new PersonInformationMap(inParam.getCaller(), theSessionUser, true);
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public long getExpirationTime() {
		return 0;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
