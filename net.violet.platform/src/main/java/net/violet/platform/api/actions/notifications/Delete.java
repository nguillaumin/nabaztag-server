package net.violet.platform.api.actions.notifications;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchNotificationException;
import net.violet.platform.api.exceptions.UnsupportedException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Constantes;

public class Delete extends AbstractNotificationAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchNotificationException, UnsupportedException {

		final UserData theSessionUser = SessionManager.getUserFromSessionParam(inParam);

		final NotificationData theNotification = getRequestedNotification(inParam);

		if (theSessionUser.equals(theNotification.getRecipient().getOwner())) {
			if (!theNotification.getStatus().equals(NOTIFICATION_STATUS.PENDING.toString())) {
				theNotification.delete();
			} else {
				throw new UnsupportedException(APIErrorMessage.CANNOT_DELETE_PENDING_NOTIFICATION);
			}
		} else {
			throw new ForbiddenException();
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
		return ActionType.DELETE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
