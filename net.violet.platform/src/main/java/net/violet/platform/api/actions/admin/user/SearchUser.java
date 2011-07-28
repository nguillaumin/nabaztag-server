package net.violet.platform.api.actions.admin.user;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.maps.admin.AdminAccountInformationMap;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

public class SearchUser extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException {

		final String userId = inParam.getString("id", false);
		final String email = inParam.getString("email", false);
		final String name = inParam.getString("name", false);

		if ((email == null) && (userId == null) && (name == null)) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "missing email or id params");
		}

		final List<UserData> theUserDataList = new LinkedList<UserData>();

		UserData theUserData = null;
		if (email != null) {
			theUserData = UserData.findByEmail(email);
		} else if (userId != null) {
			theUserData = UserData.findByAPIId(userId, inParam.getCallerAPIKey());
		} else if (name != null) {
			theUserDataList.addAll(UserData.searchUsers(null, name, null, null, null, null, null, 0, 30));
		}

		if (theUserData != null) {
			theUserDataList.add(theUserData);
		}

		if (theUserDataList.isEmpty()) {
			throw new NoSuchPersonException(APIErrorMessage.NO_SUCH_PERSON);
		}

		final List<AdminAccountInformationMap> theAdminAccountInformation = new LinkedList<AdminAccountInformationMap>();

		for (final UserData aUserData : theUserDataList) {
			theAdminAccountInformation.add(new AdminAccountInformationMap(inParam.getCaller(), aUserData, VObjectData.findByOwner(aUserData)));
		}

		return theAdminAccountInformation;
	}

	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Collections.emptyList();
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}
}
