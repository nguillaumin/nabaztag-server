package net.violet.platform.api.actions.notifications;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidNotificationException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchNotificationException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.api.exceptions.UnsupportedException;
import net.violet.platform.applications.ApplicationHandler;
import net.violet.platform.applications.ApplicationHandlerManager;
import net.violet.platform.applications.Notifier;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Constantes;

public class Accept extends AbstractNotificationAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchNotificationException, InvalidNotificationException, UnsupportedException, NoSuchSubscriptionException {

		final UserData theSessionUser = SessionManager.getUserFromSessionParam(inParam);

		final NotificationData theNotification = getRequestedNotification(inParam);

		if (theSessionUser.equals(theNotification.getRecipient().getOwner())) {
			if (NOTIFICATION_STATUS.PENDING.toString().equals(theNotification.getStatus())) {
				final ApplicationHandler handler = ApplicationHandlerManager.getHandler(theNotification.getApplication());
				if (handler instanceof Notifier) {
					((Notifier) handler).accept(theNotification.getSender(), theNotification.getRecipient());
				} else {
					throw new UnsupportedException(APIErrorMessage.CANNOT_SEND_NOTIFICATION);
				}
			} else {
				throw new UnsupportedException(APIErrorMessage.CANNOT_ACCEPT_THIS_NOTIFICATION);
			}
		} else {
			throw new InvalidNotificationException();
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
