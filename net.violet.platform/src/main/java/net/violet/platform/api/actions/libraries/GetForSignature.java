package net.violet.platform.api.actions.libraries;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.maps.ItemInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.util.Constantes;

public class GetForSignature extends AbstractItemAction {

	/**
	 * Returns all signatures
	 * 
	 * @see
	 */

	@Override
	protected Object doProcessRequest(ActionParam inParam) {

		final List<ItemInformationMap> result = new ArrayList<ItemInformationMap>();

		for (final MusicData inMusic : MusicData.SIGNATURES) {
			result.add(new ItemInformationMap(inParam.getCaller(), inMusic));
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
		return Application.CLASSES_ALL;
	}

}
