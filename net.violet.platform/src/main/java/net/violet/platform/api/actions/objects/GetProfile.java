package net.violet.platform.api.actions.objects;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.maps.objects.ObjectProfileMap;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.Constantes;

public class GetProfile extends AbstractObjectAction {

	/**
	 * Returns the profile of the given object.
	 * 
	 * @throws NoSuchObjectException
	 * @throws InvalidParameterException
	 * @throws NoSuchObjectException if the requested object could not be found.
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException {

		final VObjectData theObject = getRequestedObject(inParam);
		return new ObjectProfileMap(theObject);
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
