package net.violet.platform.api.actions.applications;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.api.maps.applications.ApplicationProfileMap;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.util.Constantes;

public class GetProfile extends AbstractApplicationAction {

	/**
	 * Returns information about this application : returned key values are :
	 * title, description, tags, category, optimized_for, open_source
	 * 
	 * @throws NoSuchApplicationException
	 * @throws InvalidParameterException
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchApplicationException {

		final ApplicationData theApplication = getRequestedApplication(inParam, null);
		return new ApplicationProfileMap(theApplication);
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.GET;
	}

	/**
	 * YES ! this information may be cached !
	 * 
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return true;
	}

	/**
	 * Cached information expires after one day
	 * 
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}
}
