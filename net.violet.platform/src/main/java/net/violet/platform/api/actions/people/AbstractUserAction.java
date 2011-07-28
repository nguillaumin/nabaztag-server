package net.violet.platform.api.actions.people;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.dataobjects.UserData;

abstract class AbstractUserAction extends AbstractAction {

	/**
	 * Check whether or not a session belongs to the given user. Returns
	 * <code>true</code> if that is the case throws a {@link ForbiddenException}
	 * otherwise
	 * 
	 * @param inUser
	 * @param inParams parameters for the call.
	 * @return true if the given session id can be associated with the given
	 *         {@link UserData}
	 * @throws APIException
	 * @throws InvalidParameterException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws APIException if anything wrong happen while getting the session
	 *             or if the session does not belong to the given user
	 */
	protected static boolean doesSessionBelongToUser(UserData inUser, ActionParam inParams) throws InvalidParameterException, ForbiddenException, InvalidSessionException {
		return AbstractUserAction.doesSessionBelongToUser(inUser, inParams.getString(ActionParam.SESSION_PARAM_KEY, true), inParams.getCaller());
	}

	/**
	 * Check whether or not a session belongs to the given user. Returns
	 * <code>true</code> if that is the case throws a {@link ForbiddenException}
	 * otherwise
	 * 
	 * @param inUser
	 * @param inSessionId
	 * @param inApplication
	 * @return true if the given session id can be associated with the given
	 *         {@link UserData}
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws APIException if anything wrong happen while getting the session
	 *             or if the session does not belong to the given user
	 */
	protected static boolean doesSessionBelongToUser(UserData inUser, String inSessionId, APICaller inCaller) throws ForbiddenException, InvalidSessionException {
		// Check Session
		final UserData theSessionUser = SessionManager.getUserFromSessionId(inSessionId, inCaller);
		if (!inUser.equals(theSessionUser)) {
			throw new ForbiddenException();
		}

		return true;
	}
}
