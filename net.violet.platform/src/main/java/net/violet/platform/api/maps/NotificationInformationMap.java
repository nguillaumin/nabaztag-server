package net.violet.platform.api.maps;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.dataobjects.NotificationData;

public class NotificationInformationMap extends AbstractAPIMap {

	public NotificationInformationMap(APICaller inCaller, NotificationData inNotification) {
		super(6);

		put("id", inNotification.getApiId(inCaller));
		put("sender_id", inNotification.getSender().getApiId(inCaller));
		put("recipient_id", inNotification.getRecipient().getApiId(inCaller));
		put("application_id", inNotification.getApplication().getApiId(inCaller));
		put("status", inNotification.getStatus());
		put("date", inNotification.getNotificationDate());

	}

}
