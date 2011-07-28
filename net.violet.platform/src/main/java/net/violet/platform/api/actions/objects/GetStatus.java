package net.violet.platform.api.actions.objects;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.StringShop;

/**
 * @author christophe - Violet
 */
public class GetStatus extends AbstractObjectAction {

	/**
	 * GetInfo
	 * 
	 * @throws NoSuchObjectException
	 * @throws InvalidParameterException
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException {

		final VObjectData theObject = getRequestedObject(inParam);

		if (theObject.isDisconnected()) {
			return "disconnected";
		}

		if (theObject.isAsleep()) {
			return "asleep";
		}

		if (theObject.isAwaken()) {
			return "awaken";
		}

		return StringShop.EMPTY_STRING;
	}

	/**
	 * No Cache
	 * 
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return false;
	}

	/**
	 * Object status must not be cached
	 * 
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return 0;
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return Action.ActionType.GET;
	}
}
