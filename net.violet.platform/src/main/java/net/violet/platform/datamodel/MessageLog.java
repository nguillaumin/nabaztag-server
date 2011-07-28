package net.violet.platform.datamodel;

import net.violet.db.records.Record;
import net.violet.platform.util.CCalendar;

public interface MessageLog extends Record<MessageLog> {

	/**
	 * @return the body id
	 */
	long getBodyId();

	/**
	 * @return the timeOfDelivery
	 */
	CCalendar getTimeOfDelivery();

	/**
	 * @return the label
	 */
	String getLabel();

	/**
	 * @return the items number
	 */
	long getNbItems();

	/**
	 * @return recipient id
	 */
	long getRecipient();

	/**
	 * @return MessageLogImpl recipient as a VObjectImpl object, null if there
	 *         is none
	 */
	VObject getRecipientObject();

	/**
	 * @return service id
	 */
	long getService_id();

	/**
	 * @return the service associated to this MessageLogImpl as a ServiceImpl
	 *         object, null if there is none
	 */
	Service getService();

}
