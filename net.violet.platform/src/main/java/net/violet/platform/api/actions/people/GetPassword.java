package net.violet.platform.api.actions.people;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Templates;

public class GetPassword extends AbstractUserAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws NoSuchPersonException, InvalidParameterException {

		final String emailAddress = inParam.getMainParamAsString();

		final UserData theUser = UserData.findByEmail(emailAddress);
		if ((theUser == null) || (theUser.getReference() == null)) {
			throw new NoSuchPersonException(APIErrorMessage.UNKNOWN_EMAIL_ADDRESS, emailAddress);
		}

		Templates.returnIdentifying(theUser);
		return null;
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
