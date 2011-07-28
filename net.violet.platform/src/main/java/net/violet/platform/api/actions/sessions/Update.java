package net.violet.platform.api.actions.sessions;

import java.util.Calendar;
import java.util.Date;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class Update extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, InvalidSessionException {

		final String sessionId = inParam.getMainParamAsString();
		final Date expiration = inParam.getDate("expiration", true);
		final Calendar theCalendar = Calendar.getInstance();

		if (!SessionManager.isSessionValid(sessionId, inParam.getCaller())) {
			throw new InvalidSessionException();
		}
		if (expiration.getTime() <= theCalendar.getTimeInMillis()) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_EXPIRATION_DATE, StringShop.EMPTY_STRING);
		}
		final UserData theUser = SessionManager.getUserFromValidSessionId(sessionId);
		if (theUser == null) {
			throw new InvalidSessionException();
		}

		final String newSessionId = SessionManager.generateSessionId(inParam.getCaller(), theUser, expiration);

		return newSessionId;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}
}
