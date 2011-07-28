package net.violet.platform.api.actions.people;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.UserData;

public class ManageToken extends AbstractUserAction {

	private static final String ACTIVATE = "activate";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException {

		final UserData theSessionUser = SessionManager.getUserFromSessionParam(inParam);
		final Boolean activate = inParam.getBoolean(ManageToken.ACTIVATE);

		// just returns the token value if it exists
		if (activate == null) {
			if (theSessionUser.hasToken()) {
				return String.valueOf(theSessionUser.getToken());
			}

		} else {
			if (!theSessionUser.hasToken() && activate) {
				return String.valueOf(theSessionUser.generateToken());
			}

			if (theSessionUser.hasToken() && !activate) {
				theSessionUser.clearToken();
			}
		}

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
