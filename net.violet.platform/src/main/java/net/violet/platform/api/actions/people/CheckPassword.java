package net.violet.platform.api.actions.people;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.UserData;

public class CheckPassword extends AbstractUserAction {

	/**
	 * @throws NoSuchPersonException
	 * @throws InvalidParameterException
	 * @throws APIException
	 * @see net.violet.platform.api.actions.Action#processRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException {

		final UserData theUser = getRequestedUser(inParam, null);
		final String thePassword = inParam.getString("password", true);

		return Boolean.valueOf(theUser.checkPasswordPlain(thePassword));
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

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
