package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionLog;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface SubscriptionLogFactory extends RecordFactory<SubscriptionLog>, FilesAccessor {

	/**
	 * Creates a new subscriptionLog instance based on the given subscription. A new file object is generated and filled up with
	 * the JSON syntax for an empty list; i.e. the initial log file contains [].
	 * @param subscription
	 * @return
	 */
	SubscriptionLog create(Subscription subscription);

}
