package net.violet.platform.api.actions.objects;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.maps.objects.ObjectInformationMap;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.Constantes;

/**
 * Returns information about a given object.
 */
public class GetInfo extends AbstractObjectAction {

	/**
	 * Returns information about a given object. The returned object is a Map,
	 * actually an ObjectInformationMap object.
	 * 
	 * @throws NoSuchObjectException
	 * @throws InvalidParameterException
	 * @throws NoSuchObjectException if the requested object could not be found.
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException, ForbiddenException, InvalidSessionException {

		final VObjectData theObject = getRequestedObject(inParam);

		final boolean isVisible = checkOwnerObject(theObject, inParam);

		return new ObjectInformationMap(inParam.getCaller(), theObject, isVisible);
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
