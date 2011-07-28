package net.violet.platform.api.actions.blacklists;

import java.util.ArrayList;
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

public class Get extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException {

		final UserData theUser = SessionManager.getUserFromSessionParam(inParam);

		final List<PersonInformationMap> result = new ArrayList<PersonInformationMap>();
		for (final UserData blacked : theUser.getBlackedUsers()) {
			result.add(new PersonInformationMap(inParam.getCaller(), blacked, false));
		}

		return result;

	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	public boolean isCacheable() {
		return false;
	}

}
