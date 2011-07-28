package net.violet.platform.api.actions.people;

import java.util.List;

import net.violet.common.utils.RegexTools;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.UserFriendsAddressData;
import net.violet.platform.util.Templates;

/**
 * @throws ForbiddenException
 * @throws InvalidSessionException
 * @throws InvalidParameterException
 * @see
 */
public class TellMyFriends extends AbstractUserAction {

	@Override
	public Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException {

		final UserData theSessionUser = SessionManager.getUserFromSessionParam(inParam);

		final List<String> theEmailList = inParam.getList("emails", true);
		int bad_addresses = 0;

		for (final String email : theEmailList) {
			if (RegexTools.isAValidEmail(email)) {
				Templates.tellMyFriends(theSessionUser.getReference(), email);
				UserFriendsAddressData.saveAddress(theSessionUser, email);
			} else {
				bad_addresses++;
			}
		}

		if (bad_addresses > 0) {
			throw new InvalidParameterException(APIErrorMessage.NOT_AN_EMAIL_ADDRESS, net.violet.common.StringShop.EMPTY_STRING);
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
