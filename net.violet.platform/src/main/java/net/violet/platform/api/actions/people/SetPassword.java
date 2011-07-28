package net.violet.platform.api.actions.people;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.BadCredentialsException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class SetPassword extends AbstractUserAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException, ForbiddenException, InvalidSessionException, BadCredentialsException {

		final UserData theUser = getRequestedUser(inParam, null);

		// Check Session
		AbstractUserAction.doesSessionBelongToUser(theUser, inParam);

		final String old_password = inParam.getString("old_password", true);
		final String new_password = inParam.getString("new_password", true);
		if (old_password.equals(StringShop.EMPTY_STRING)) {
			throw new InvalidParameterException(APIErrorMessage.PASSWORD_CANNOT_BE_EMPTY, StringShop.EMPTY_STRING);
		}
		if (new_password.equals(StringShop.EMPTY_STRING)) {
			throw new InvalidParameterException(APIErrorMessage.PASSWORD_CANNOT_BE_EMPTY, StringShop.EMPTY_STRING);
		}

		if (!theUser.checkPasswordPlain(old_password)) {
			throw new BadCredentialsException(APIErrorMessage.INVALID_PASSWORD);
		}

		theUser.setPassword(new_password);

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
