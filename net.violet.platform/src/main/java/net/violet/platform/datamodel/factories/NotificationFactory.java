package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Notification;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;

public interface NotificationFactory extends RecordFactory<Notification> {

	/**
	 * gets all received notification
	 * 
	 * @param inRecipient : VObject
	 * @param inSkip  : number of request to skip
	 * @param inGetCount  : number of request to get
	 */
	List<Notification> findAllReceived(VObject inRecipient, int inSkip, int inGetCount);

	/**
	 * gets number of received notification
	 * @param inRecipient : VObject
	 */
	long countReceived(VObject inRecipient);

	/**
	 * gets all sent notification
	 * 
	 * @param inSender  : VObject
	 * @param inSkip : number of request to skip
	 * @param inGetCount : number of request to get
	 */
	List<Notification> findAllSent(VObject inSender, int inSkip, int inGetCount);

	/**
	 * gets number of sent notification
	 * @param inSender  : VObject
	 */
	long countSent(VObject inSender);

	/**
	 * Creates notification
	 * 
	 * @param inSender : sender Object
	 * @param inRecipient : recipient Object
	 * @param inApplication : given Application
	 * @param inStatus : given NOTIFICATION_STATUS request
	 */
	Notification create(VObject inSender, VObject inRecipient, Application inApplication, NOTIFICATION_STATUS inStatus);

	/**
	 * gets pending notification by sender and application
	 * 
	 * @param inSender  : VObject
	 * @param inApplication  : application
	 */
	Notification findPending(VObject inSender, Application inApplication);

}
