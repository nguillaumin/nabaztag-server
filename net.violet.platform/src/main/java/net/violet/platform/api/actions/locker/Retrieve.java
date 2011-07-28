package net.violet.platform.api.actions.locker;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.APICaller.CallerClass;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.locker.LockerService;

/**
 * Retrieve secrets from the locker
 * @author christophe - Violet
 */
public class Retrieve extends AbstractAction {

	/**
	 * Pass the key to retrieve a locker content 
	 * Note : the key may serve one time only. A second tentative with the same key will allways return null
	 * @see net.violet.platform.api.actions.AbstractAction#doProcessRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		// Allow to retrieve the key under 'key' or 'id' param names
		String lockersKey = inParam.getString("key", null);
		if (lockersKey == null) {
			lockersKey = inParam.getMainParamAsString();
		}

		final String ownersID = inParam.getCallerAPIKey();

		// try owner's access first
		String secret = LockerService.retrieve(lockersKey, ownersID);

		if (secret == null) {
			// try anonymous access
			secret = LockerService.retrieve(lockersKey);
		}

		return secret;
	}

//  -------------------------------------------------------------------------80

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}

	/**
	 * Returns the list of caller classes authorized to call this action.
	 * 
	 * @return
	 */
	@Override
	public List<CallerClass> getAuthorizedCallerClasses() {
		return APICaller.APPLICATIONS;
	}

	/**
	 * Returns the list of application classes authorized to call this action.
	 * Default is to allow all application classes.
	 * 
	 * @return
	 */
	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_NOT_UI;
	}

}
