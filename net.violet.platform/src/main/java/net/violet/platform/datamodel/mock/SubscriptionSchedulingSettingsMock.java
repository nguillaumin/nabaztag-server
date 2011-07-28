/**
 * 
 */
package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;

public class SubscriptionSchedulingSettingsMock extends AbstractMockRecord<SubscriptionSchedulingSettings, SubscriptionSchedulingSettingsMock> implements SubscriptionSchedulingSettings {

	private final SubscriptionScheduling mSubscriptionScheduling;
	private String mKey;
	private String mValue;

	public SubscriptionSchedulingSettingsMock(long inId, SubscriptionScheduling inSubscription, String inKey, String inValue) {
		super(inId);
		this.mSubscriptionScheduling = inSubscription;
		this.mKey = inKey;
		this.mValue = inValue;
	}

	public String getKey() {
		return this.mKey;
	}

	public SubscriptionScheduling getSubscriptionScheduling() {
		return this.mSubscriptionScheduling;
	}

	public String getValue() {
		return this.mValue;
	}

	public void setValue(String value) {
		this.mValue = value;

	}

	public void updateKeyAndValue(String key, String value) {
		this.mKey = key;
		this.mValue = value;
	}

}
