package net.violet.platform.api.actions.admin.user;

import java.util.Collections;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.UserData;

public class Delete extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException, ForbiddenException {

		final UserData theUserData = getRequestedUser(inParam, null);

//		!!!! Do not kill the Violet User !!!!
		if (theUserData.getId() == 1) {
			throw new ForbiddenException();
		}

		theUserData.delete();
		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public boolean isCacheable() {
		return false;
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.DELETE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Collections.emptyList();
	}

}
