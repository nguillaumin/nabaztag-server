package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.violet.platform.datamodel.Notification;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class NotificationData extends APIData<Notification> {

	private static final Logger LOGGER = Logger.getLogger(NotificationData.class);

	protected NotificationData(Notification inNotification) {
		super(inNotification);
	}

	public static NotificationData getData(Notification inNotification) {
		try {
			return RecordData.getData(inNotification, NotificationData.class, Notification.class);
		} catch (final InstantiationException e) {
			NotificationData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			NotificationData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			NotificationData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			NotificationData.LOGGER.fatal(e, e);
		}

		return null;
	}

	public static NotificationData findByAPIId(String inAPIId, String inAPIKey) {

		NotificationData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.NOTIFICATION, inAPIKey);

		if (theID != 0) {
			final Notification notification = Factories.NOTIFICATION.find(theID);

			if (notification != null) {
				theResult = NotificationData.getData(notification);
			}
		}

		return theResult;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		final Notification theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}

		return 0;
	}

	public static List<NotificationData> getReceived(VObjectData inRecipient, int inSkip, int inGetCount) {
		final List<NotificationData> theResult = new ArrayList<NotificationData>();
		for (final Notification theNotification : Factories.NOTIFICATION.findAllReceived(inRecipient.getRecord(), inSkip, inGetCount)) {
			theResult.add(NotificationData.getData(theNotification));
		}

		return theResult;
	}

	public static long getCountReceived(VObjectData inRecipient) {
		return Factories.NOTIFICATION.countReceived(inRecipient.getRecord());
	}

	public static List<NotificationData> getSent(VObjectData inSender, int inSkip, int inGetCount) {
		final List<NotificationData> theResult = new ArrayList<NotificationData>();
		for (final Notification theNotification : Factories.NOTIFICATION.findAllSent(inSender.getRecord(), inSkip, inGetCount)) {
			theResult.add(NotificationData.getData(theNotification));
		}

		return theResult;
	}

	public static long getCountSent(VObjectData inSender) {
		return Factories.NOTIFICATION.countSent(inSender.getRecord());
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.NOTIFICATION;
	}

	private static NotificationData create(VObjectData inSender, VObjectData inRecipient, ApplicationData inApplication, NOTIFICATION_STATUS inStatus) {
		return NotificationData.getData(Factories.NOTIFICATION.create(inSender.getRecord(), inRecipient.getRecord(), inApplication.getRecord(), inStatus));
	}

	public static NotificationData findPending(VObjectData inSender, ApplicationData inApplication) {
		return NotificationData.getData(Factories.NOTIFICATION.findPending(inSender.getRecord(), inApplication.getRecord()));
	}

	public VObjectData getSender() {
		final Notification theRecord = getRecord();
		if (theRecord != null) {
			return VObjectData.getData(theRecord.getSender());
		}

		return VObjectData.getData(null);
	}

	public VObjectData getRecipient() {
		final Notification theRecord = getRecord();
		if (theRecord != null) {
			return VObjectData.getData(theRecord.getRecipient());
		}

		return VObjectData.getData(null);
	}

	public ApplicationData getApplication() {
		final Notification theRecord = getRecord();
		if (theRecord != null) {
			return ApplicationData.getData(theRecord.getApplication());
		}

		return ApplicationData.getData(null);
	}

	public String getStatus() {
		final Notification theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getStatus();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public Date getNotificationDate() {
		final Notification theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNotificationDate();
		}
		return null;
	}

	public static void sendPendingNotification(VObjectData inSender, VObjectData inRecipient, ApplicationData inApplication) {
		NotificationData.create(inSender, inRecipient, inApplication, NOTIFICATION_STATUS.PENDING);
	}

	public static void sendFinishNotification(VObjectData inSender, VObjectData inRecipient, ApplicationData inApplication) {
		NotificationData.create(inSender, inRecipient, inApplication, NOTIFICATION_STATUS.FINISHED);
	}

	public static void sendCancelNotification(VObjectData inSender, ApplicationData inApplication) {
		final NotificationData theNotificationData = NotificationData.findPending(inSender, inApplication);
		if (theNotificationData.isValid()) {
			final VObjectData theRecipient = theNotificationData.getRecipient();
			theNotificationData.delete(); //delete pending notification
			NotificationData.create(inSender, theRecipient, inApplication, NOTIFICATION_STATUS.CANCELLED);
		}
	}

	public static void sendAcceptNotification(VObjectData inSender, ApplicationData inApplication) {
		final NotificationData theNotificationData = NotificationData.findPending(inSender, inApplication);
		if (theNotificationData.isValid()) {
			final VObjectData theRecipient = theNotificationData.getRecipient();
			theNotificationData.delete(); //delete pending notification
			NotificationData.create(theRecipient, inSender, inApplication, NOTIFICATION_STATUS.ACCEPTED);
		}
	}

	public static void sendRejectNotification(VObjectData inSender, ApplicationData inApplication) {
		final NotificationData theNotificationData = NotificationData.findPending(inSender, inApplication);
		if (theNotificationData.isValid()) {
			final VObjectData theRecipient = theNotificationData.getRecipient();
			theNotificationData.delete(); //delete pending notification
			NotificationData.create(theRecipient, inSender, inApplication, NOTIFICATION_STATUS.REJECTED);
		}
	}
}
