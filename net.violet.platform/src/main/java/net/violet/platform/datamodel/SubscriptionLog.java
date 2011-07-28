package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface SubscriptionLog extends Record<SubscriptionLog> {

	Subscription getSubscription();

	Files getLogFile();

}
