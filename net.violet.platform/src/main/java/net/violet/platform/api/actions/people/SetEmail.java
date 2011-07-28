package net.violet.platform.api.actions.people;

import java.util.List;

import net.violet.common.utils.RegexTools;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NameAlreadyExistsException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.UserData;

public class SetEmail extends AbstractUserAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException, ForbiddenException, InvalidSessionException, NameAlreadyExistsException {

		final UserData theUser = getRequestedUser(inParam, null);

		AbstractUserAction.doesSessionBelongToUser(theUser, inParam);

		final String email = inParam.getString("email", true);
		final String password = inParam.getString("password", true);

		if (!theUser.checkPasswordPlain(password)) {
			throw new ForbiddenException();
		}

		if (!RegexTools.isAValidEmail(email)) {
			throw new InvalidParameterException(APIErrorMessage.NOT_AN_EMAIL_ADDRESS, email);
		}

		if (UserData.findByEmail(email) != null) {
			throw new NameAlreadyExistsException(APIErrorMessage.EMAIL_ADDRESS_ALREADY_EXISTS);
		}

		theUser.setEmail(email);

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
