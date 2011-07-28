package net.violet.platform.api.actions.libraries;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.ItemInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.MusicData.StatusMusic;
import net.violet.platform.util.Constantes;

public class GetShuffle extends AbstractItemAction {

	/**
	 * Returns the preferences of the given object.
	 * 
	 * @throws InvalidParameterException
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final APICaller caller = inParam.getCaller();
		final StatusMusic type = getLibraryTypeId(inParam);
		final String thelanguageCode = inParam.getString("language", true);
		final ObjectLangData languageData = ObjectLangData.getByISOCode(thelanguageCode);

		final MusicData theMusicData = MusicData.findRandomByLang(type, languageData);

		return new ItemInformationMap(caller, theMusicData);
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

}
