package net.violet.platform.api.actions.libraries;

import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchCategoryException;
import net.violet.platform.api.exceptions.NoSuchItemException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.CategData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;

public class SetProfile extends AbstractItemAction {

	/**
	 * Set the profile of the given item.
	 * 
	 * @throws NoSuchItemException
	 * @throws InvalidParameterException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws NoSuchCategoryException 
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchItemException, ForbiddenException, InvalidSessionException, NoSuchCategoryException {

		final MusicData inMusic = getMusicData(inParam);
		final Map<String, Object> profile = inParam.getMap("profile", true);
		final String name = (String) profile.get("name");
		final String nabshareCateg = (String) profile.get("nabshare");

		final UserData theUserData = SessionManager.getUserFromSessionParam(inParam);
		if (inMusic.getMusic_owner() == theUserData.getId()) {
			int shared = 0;
			if (nabshareCateg != null) {
				final CategData theNabshareCateg = CategData.findByName(nabshareCateg);
				if (theNabshareCateg == null) {
					throw new NoSuchCategoryException(nabshareCateg);
				}
				shared = (int) theNabshareCateg.getId();
			}
			inMusic.setMusicProfile(name, shared);
		} else {
			throw new ForbiddenException();
		}
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
