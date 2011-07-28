package net.violet.platform.api.actions.notifications;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.objects.AbstractObjectAction;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.api.exceptions.NotificationAlreadyExistsException;
import net.violet.platform.api.exceptions.UnsupportedException;
import net.violet.platform.applications.ApplicationHandler;
import net.violet.platform.applications.ApplicationHandlerManager;
import net.violet.platform.applications.Notifier;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.Constantes;

public class Add extends AbstractObjectAction {

	@APIMethodParam
	public static final String APPLICATION_ID = "application_id";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException, ForbiddenException, InvalidSessionException, NoSuchApplicationException, NotificationAlreadyExistsException, NoSuchSubscriptionException, UnsupportedException {

		final VObjectData theObject = getRequestedObject(inParam);

		if (!checkOwnerObject(theObject, inParam)) {
			throw new ForbiddenException();
		}

		final ApplicationData theApplication = getRequestedApplication(inParam, Add.APPLICATION_ID);

		if (NotificationData.findPending(theObject, theApplication).isValid()) {
			throw new NotificationAlreadyExistsException();
		}

		final ApplicationHandler handler = ApplicationHandlerManager.getHandler(theApplication);
		if (handler instanceof Notifier) {
			((Notifier) handler).add(theObject);
		} else {
			throw new UnsupportedException(APIErrorMessage.CANNOT_SEND_NOTIFICATION);
		}

		return null;
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
		return ActionType.CREATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
