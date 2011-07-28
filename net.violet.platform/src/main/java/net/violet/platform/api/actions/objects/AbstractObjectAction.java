package net.violet.platform.api.actions.objects;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

/**
 * Common methods to every object actions
 * 
 * @author chdes - Violet
 */
public abstract class AbstractObjectAction extends AbstractAction {

	/**
	 * Every object action requires an interactive object....
	 * 
	 * @param params
	 * @return
	 * @throws InvalidParameterException
	 * @throws NoSuchObjectException
	 * @throws NoSuchObjectException
	 */
	protected VObjectData getRequestedObject(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException {

		final String objectId = inParam.getMainParamAsString();

		final VObjectData object = VObjectData.findByAPIId(objectId, inParam.getCallerAPIKey());
		if ((object == null) || !object.isValid()) {
			throw new NoSuchObjectException();
		}

		return object;

	}

	/**
	 * Check whether or not a session belongs to the given VObject. Returns
	 * <code>true</code> if that is the case throws a {@link ForbiddenException}
	 * otherwise
	 * 
	 * @param inObject
	 * @param inSessionId
	 * @param inApplication
	 * @return true if the given session id can be associated with the given
	 *         {@link VObjectData}
	 * @throws InvalidParameterException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws APIException if anything wrong happen while getting the session
	 *             or if the session does not belong to the given VObject
	 */
	protected boolean doesSessionBelongToVObject(VObjectData inObject, ActionParam inParams) throws ForbiddenException, InvalidSessionException, InvalidParameterException {
		// Check Session

		final UserData theUsersSession = SessionManager.getUserFromSessionId(inParams.getString(ActionParam.SESSION_PARAM_KEY, true), inParams.getCaller());

		if ((inObject.getOwner() == null) || (theUsersSession.getId() != inObject.getOwner().getId())) {
			throw new ForbiddenException();
		}

		return true;
	}

	/**
	 * Vérifie si l'objet appartient bien au user passé en session
	 * 
	 * @param inObject
	 * @param inParam
	 * @return true if the given session id can be associated with the given
	 *         {@link VObjectData}
	 * @throws InvalidParameterException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws APIException if anything wrong happen while getting the session
	 *             or if the session does not belong to the given VObject
	 */

	protected boolean checkOwnerObject(VObjectData inObject, ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException {
		boolean isVisible = false;
		final String theSession = inParam.getString(ActionParam.SESSION_PARAM_KEY, false);
		if (theSession != null) {
			final UserData theUser = SessionManager.getUserFromSessionId(theSession.toString(), inParam.getCaller());

			if (theUser.getId() == inObject.getOwner().getId()) {
				isVisible = true;
			}
		}

		return isVisible;
	}

}
