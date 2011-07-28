package net.violet.platform.api.actions.anim;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.util.Constantes;

public class Get extends AbstractAction {

	/**
	 * Returns all the animations names (list of String).
	 * 
	 * @see
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) {

		final List<String> result = new ArrayList<String>();

		for (final AnimData inAnim : AnimData.findAllAnim()) {
			result.add(inAnim.getLabel());
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
