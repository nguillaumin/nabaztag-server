package net.violet.platform.api.actions.libraries;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchItemException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Constantes;

public class GetProfile extends AbstractItemAction {

	/**
	 * Returns the profile of the given item.
	 * 
	 * @throws NoSuchItemException
	 * @throws InvalidParameterException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchItemException, ForbiddenException, InvalidSessionException {
		final MusicData inMusic = getMusicData(inParam);
		final UserData theUserData = SessionManager.getUserFromSessionParam(inParam);
		if (((inMusic.getMusic_owner() > 0) && (inMusic.getMusic_owner() == theUserData.getId())) || inMusic.isLibrary() || (inMusic.getMusic_share() != 0)) {
			return new ItemProfileMap(inMusic);
		}
		throw new ForbiddenException();
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return true;
	}

	/**
	 * Object informations may be cached one day
	 * 
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
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
		return Application.CLASSES_ALL;
	}

	private static class ItemProfileMap extends AbstractAPIMap {

		public ItemProfileMap(MusicData inMusic) {
			super(2);
			put("name", inMusic.getLabel());
			put("nabshare", inMusic.getNabshareCateg());
		}
	}

}
