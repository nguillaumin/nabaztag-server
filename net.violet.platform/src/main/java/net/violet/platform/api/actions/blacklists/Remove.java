package net.violet.platform.api.actions.blacklists;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.BlackData;
import net.violet.platform.dataobjects.UserData;

public class Remove extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchPersonException {

		final UserData theUser = SessionManager.getUserFromSessionParam(inParam);

		final String blackedId = inParam.getMainParamAsString();
		final UserData blackedUser = UserData.findByAPIId(blackedId, inParam.getCallerAPIKey());
		if (blackedUser == null) {
			throw new NoSuchPersonException(APIErrorMessage.NO_SUCH_PERSON);
		}

		BlackData.removeFromBlackList(theUser, blackedUser);

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	public boolean isCacheable() {
		return false;
	}

}
