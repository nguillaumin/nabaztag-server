package net.violet.platform.api.actions.people;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.maps.objects.ObjectInformationMap;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

public class GetObjects extends AbstractUserAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException, ForbiddenException, InvalidSessionException {

		final UserData theUser = getRequestedUser(inParam, null);

		final List<ObjectInformationMap> result = new LinkedList<ObjectInformationMap>();

		final boolean addressable = inParam.getBoolean("addressable", false);

		boolean isVisible = false;
		final String theSession = inParam.getString(ActionParam.SESSION_PARAM_KEY, false);
		if (theSession != null) {
			final UserData theUserSession = SessionManager.getUserFromSessionId(theSession, inParam.getCaller());
			if (theUser.equals(theUserSession)) {
				isVisible = true;
			}
		}

		for (final VObjectData objectData : VObjectData.findByOwner(theUser)) {
			// this method does not return an invisible object unless it belongs to the session user.
			if (objectData.getPreferences().isVisible() || isVisible) {
				if (addressable) {
					if (objectData.hasInbox()) {
						result.add(new ObjectInformationMap(inParam.getCaller(), objectData, isVisible));
					}
				} else {
					result.add(new ObjectInformationMap(inParam.getCaller(), objectData, isVisible));
				}
			}
		}

		return result;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}
}
