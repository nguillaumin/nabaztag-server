package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Notification;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.NotificationFactory;
import net.violet.platform.datamodel.mock.NotificationMock;

public class NotificationFactoryMock extends RecordFactoryMock<Notification, NotificationMock> implements NotificationFactory {

	NotificationFactoryMock() {
		super(NotificationMock.class);
	}

	public long countReceived(VObject inRecipient) {
		int result = 0;
		for (final Notification theNotification : findAllMapped().values()) {
			if (theNotification.getRecipient().equals(inRecipient)) {
				result += 1;
			}
		}
		return result;
	}

	public long countSent(VObject inSender) {
		int result = 0;
		for (final Notification theNotification : findAllMapped().values()) {
			if (theNotification.getSender().equals(inSender) && theNotification.getStatus().equals(NOTIFICATION_STATUS.PENDING.toString())) {
				result += 1;
			}
		}
		return result;
	}

	public Notification create(VObject inSender, VObject inRecipient, Application inApplication, NOTIFICATION_STATUS inStatus) {
		return new NotificationMock(0, inSender, inRecipient, inApplication, inStatus);
	}

	public List<Notification> findAllReceived(VObject inRecipient, int inSkip, int inGetCount) {
		final List<Notification> theResult = new ArrayList<Notification>();
		for (final Notification theNotification : findAllMapped().values()) {
			if (theNotification.getRecipient().equals(inRecipient)) {
				theResult.add(theNotification);
			}
		}

		return getSkipList(theResult, inSkip, inGetCount);
	}

	public List<Notification> findAllSent(VObject inSender, int inSkip, int inGetCount) {
		final List<Notification> theResult = new ArrayList<Notification>();
		for (final Notification theNotification : findAllMapped().values()) {
			if (theNotification.getSender().equals(inSender) && theNotification.getStatus().equals(NOTIFICATION_STATUS.PENDING.toString())) {
				theResult.add(theNotification);
			}
		}

		return getSkipList(theResult, inSkip, inGetCount);
	}

	public Notification findPending(VObject inSender, Application inApplication) {
		Notification theResult = null;
		for (final Notification theNotification : findAllMapped().values()) {
			if (theNotification.getSender().equals(inSender) && theNotification.getApplication().equals(inApplication) && theNotification.getStatus().equals(NOTIFICATION_STATUS.PENDING.toString())) {
				theResult = theNotification;
			}
		}
		return theResult;
	}
}
