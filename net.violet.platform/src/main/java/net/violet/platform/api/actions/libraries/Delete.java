package net.violet.platform.api.actions.libraries;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchItemException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;

public class Delete extends AbstractItemAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, InvalidSessionException, NoSuchItemException, ForbiddenException {

		final MusicData theMusicData = getMusicData(inParam);

		final UserData theUserData = SessionManager.getUserFromSessionParam(inParam);
		if (theMusicData.getMusic_owner() == theUserData.getId()) {
			theMusicData.delete();
		} else {
			throw new ForbiddenException();
		}

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.DELETE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_ALL;
	}
}
