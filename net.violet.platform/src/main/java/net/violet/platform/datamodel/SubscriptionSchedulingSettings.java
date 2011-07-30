package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Scheduling type parameters when an application is added to
 * an object.
 * 
 *
 */
public interface SubscriptionSchedulingSettings extends Record<SubscriptionSchedulingSettings> {

	String getKey();

	String getValue();

	void setValue(String value);

	SubscriptionScheduling getSubscriptionScheduling();

	void updateKeyAndValue(String key, String value);

}
