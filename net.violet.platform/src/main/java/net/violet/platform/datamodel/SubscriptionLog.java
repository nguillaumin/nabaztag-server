package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Logs an application on a specific subscription. Unused ?
 * 
 *
 */
public interface SubscriptionLog extends Record<SubscriptionLog> {

	Subscription getSubscription();

	Files getLogFile();

}
