package net.violet.platform.api.actions.sessions;

import java.util.Date;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.BadCredentialsException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.StringShop;

public class Create extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException, BadCredentialsException {

		final String email = inParam.getString("email", false);
		final String name = inParam.getString("nabname", false);
		final String password = inParam.getString("password", true);
		final Date expiration = inParam.getDate("expiration", true);

		if ((email == null) && (name == null)) {
			throw new InvalidParameterException(APIErrorMessage.MISSING_PARAMETER, StringShop.EMPTY_STRING);
		}

		final UserData theUser = Create.findByNameOrEmailAddress(name, email);

		if (theUser == null) {
			throw new NoSuchPersonException(APIErrorMessage.NO_SUCH_PERSON);
		}

		if (!theUser.checkPasswordPlain(password)) {
			throw new BadCredentialsException(APIErrorMessage.INVALID_PASSWORD);
		}

		return SessionManager.generateSessionId(inParam.getCaller(), theUser, expiration);
	}

	private static UserData findByNameOrEmailAddress(String inName, String inEmailAddress) {
		if ((inName == null) && (inEmailAddress != null)) {
			return UserData.findByEmail(inEmailAddress);
		} else if ((inName != null) && (inEmailAddress == null)) {
			final VObjectData theVObjectData = VObjectData.findByName(inName);

			if (theVObjectData != null) {
				return theVObjectData.getOwner();
			}

		} else if ((inName != null) && (inEmailAddress != null)) {
			final UserData theUser = UserData.findByEmail(inEmailAddress);
			final VObjectData theVObjectData = VObjectData.findByName(inName);

			if ((theUser != null) && (theVObjectData != null) && theUser.equals(theVObjectData.getOwner())) {
				return theUser;
			}
		}
		return null;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}
}
