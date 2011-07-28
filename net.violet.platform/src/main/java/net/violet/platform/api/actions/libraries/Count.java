package net.violet.platform.api.actions.libraries;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.MusicData.MimeTypes;

public class Count extends AbstractItemAction {

	/**
	 * Returns the preferences of the given object.
	 * 
	 * @throws InvalidParameterException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException {

		final MimeTypes mimeType = getLibrarySelectorId(inParam, true);

		final UserData theUserData = SessionManager.getUserFromSessionParam(inParam);

		return MusicData.countAllItemsOfUser(theUserData.getReference(), mimeType);
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return false;
	}

	/**
	 * Object informations may be cached one day
	 * 
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return 0;
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.GET;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
