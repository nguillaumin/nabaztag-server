package net.violet.platform.api.actions.notifications;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchNotificationException;
import net.violet.platform.dataobjects.NotificationData;

public abstract class AbstractNotificationAction extends AbstractAction {

	/**
	 * @param inParam
	 * @return
	 * @throws InvalidParameterException
	 * @throws NoSuchNotificationException
	 * @throws APIException
	 */
	protected NotificationData getRequestedNotification(ActionParam inParam) throws InvalidParameterException, NoSuchNotificationException {

		final String notificationId = inParam.getMainParamAsString();

		final NotificationData notification = NotificationData.findByAPIId(notificationId, inParam.getCaller().getAPIKey());

		if (notification == null) {
			throw new NoSuchNotificationException();
		}

		return notification;
	}

}
