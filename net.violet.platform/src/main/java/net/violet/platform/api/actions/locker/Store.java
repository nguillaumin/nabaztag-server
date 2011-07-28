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
 * Store a secret in the locker with different retrieval options.
 * 
 * Method params :
 * 	data : the data to store in the locker. required. String
 *                  
 * @author christophe - Violet
 */
public class Store extends AbstractAction {

	/**
	 * Store a secret in the locker
	 * Return the key to retrieve it later
	 * @see net.violet.platform.api.actions.AbstractAction#doProcessRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String secret = inParam.getString("data", true);
		final boolean useOwnerID = inParam.getString("options.restrict_access_to", "").equalsIgnoreCase("owner");
		final long expirationDelay = inParam.getLong("options.expiration_delay", LockerService.DEFAULT_EXPIRATION_TIME);

		String lockersKey = inParam.getString("key", null);

		final String ownerID = (useOwnerID) ? inParam.getCallerAPIKey() : null;

		if (lockersKey == null) {
			lockersKey = LockerService.store(secret, ownerID, expirationDelay);
		} else {
			LockerService.store(lockersKey, secret, ownerID, expirationDelay);
		}
		return lockersKey;
	}

//  -------------------------------------------------------------------------80

	public ActionType getType() {
		return ActionType.CREATE;
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
