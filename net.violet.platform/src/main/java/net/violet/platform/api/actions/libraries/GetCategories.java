package net.violet.platform.api.actions.libraries;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.dataobjects.MusicStyleData;
import net.violet.platform.dataobjects.MusicData.StatusMusic;
import net.violet.platform.util.Constantes;

public class GetCategories extends AbstractItemAction {

	/**
	 * Returns the preferences of the given object.
	 * 
	 * @throws InvalidParameterException
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final StatusMusic libraryType = getLibraryTypeId(inParam);
		final List<String> listMusicStyleData = MusicStyleData.findAllCategoriesByType(libraryType);
		return listMusicStyleData;
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

}
