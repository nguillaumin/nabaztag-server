package net.violet.platform.api.actions.objects;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.VObjectData;

public class SetStatus extends AbstractObjectAction {

	/**
	 * @throws NoSuchObjectException
	 * @throws InvalidParameterException
	 * @throws APIException (NoSuchObjectException, InvalidParameterException)
	 * @see net.violet.platform.api.actions.Action#processRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException {

		final VObjectData theObject = getRequestedObject(inParam);

		final String newStatus = inParam.getString("status", true);
		if ((newStatus == null) || (!newStatus.equals("asleep") && !newStatus.equals("awaken"))) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_STATUS, net.violet.common.StringShop.EMPTY_STRING);
		}

		if (newStatus.equals("asleep")) {
			theObject.changeStatus(VObject.STATUS_FORCE_VEILLE);
		} else {
			theObject.changeStatus(VObject.STATUS_FORCE_ACTIF);
		}

		return newStatus;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0; // expires immediately
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
