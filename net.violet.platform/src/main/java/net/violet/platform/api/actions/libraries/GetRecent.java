package net.violet.platform.api.actions.libraries;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.ItemInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.MusicData.MimeTypes;
import net.violet.platform.util.Constantes;

public class GetRecent extends AbstractItemAction {

	/**
	 * Returns the preferences of the given object.
	 * 
	 * @throws InvalidParameterException
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final MimeTypes mimeType = getLibrarySelectorId(inParam, true);
		final int count = inParam.getInt("count", 10);
		if (count <= 0) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, inParam.toString());
		}

		final APICaller caller = inParam.getCaller();

		final List<ItemInformationMap> result = new ArrayList<ItemInformationMap>();
		for (final MusicData inMusic : MusicData.findByMimeType(mimeType, count)) {
			result.add(new ItemInformationMap(caller, inMusic));
		}

		return result;
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
		return Application.CLASSES_UI;
	}

}
