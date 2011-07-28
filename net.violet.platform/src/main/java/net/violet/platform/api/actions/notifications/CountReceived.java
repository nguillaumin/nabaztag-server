package net.violet.platform.api.actions.notifications;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.objects.AbstractObjectAction;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.VObjectData;

public class CountReceived extends AbstractObjectAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException, ForbiddenException, InvalidSessionException {

		final VObjectData theObject = getRequestedObject(inParam);

		if (!checkOwnerObject(theObject, inParam)) {
			throw new ForbiddenException();
		}

		return ((Long) NotificationData.getCountReceived(theObject)).intValue();
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return false;
	}

	/**
	 * User informations may be cached one day
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
		return ActionType.GET;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
