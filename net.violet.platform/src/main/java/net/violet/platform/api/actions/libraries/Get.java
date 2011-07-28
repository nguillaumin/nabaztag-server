package net.violet.platform.api.actions.libraries;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchItemException;
import net.violet.platform.api.maps.ItemInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.MusicData.MimeTypes;

public class Get extends AbstractItemAction {

	/**
	 * Retrieve library items belonging to a given user
	 * 
	 * @throws InvalidParameterException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws NoSuchItemException
	 * @throws NoSuchItemException
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException, NoSuchItemException {

		final UserData ownerData = SessionManager.getUserFromSessionParam(inParam);
		final APICaller caller = inParam.getCaller();

		final List<ItemInformationMap> result = new ArrayList<ItemInformationMap>();

		/*
		 * accepted mime type : video, audio, all
		 */
		final MimeTypes mimeType = getLibrarySelectorId(inParam, false);

		if (mimeType == null) { // we have passed an item id !
			final MusicData item = getMusicData(inParam);

			if (!item.getOwner().equals(ownerData) && !item.isLibrary() && (item.getMusic_share() == 0)) {
				// he must belong to the current user !
				throw new ForbiddenException();
			}
			result.add(new ItemInformationMap(caller, item));

		} else {
			final int skip = inParam.getInt("skip", 0);
			final int count = inParam.getInt("count", 10);

			if ((count <= 0) || (skip < 0)) {
				throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, inParam.toString());
			}

			for (final MusicData item : MusicData.findByUserAndMimeType(ownerData, mimeType, skip, count)) {
				result.add(new ItemInformationMap(caller, item));
			}
		}

		return result;
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
