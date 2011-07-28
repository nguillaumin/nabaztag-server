package net.violet.platform.api.actions.people;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.maps.persons.PersonProfileMap;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Constantes;

/**
 * Get User Profile Response is cacheable for 1 day.
 */
public class GetProfile extends AbstractUserAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException {

		final UserData theUser = getRequestedUser(inParam, null);
		return new PersonProfileMap(theUser);
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return true;
	}

	/**
	 * User informations may be cached one day
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
