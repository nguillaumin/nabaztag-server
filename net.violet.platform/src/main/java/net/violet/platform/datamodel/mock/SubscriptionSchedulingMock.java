/**
 * 
 */
package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;

public class SubscriptionSchedulingMock extends AbstractMockRecord<SubscriptionScheduling, SubscriptionSchedulingMock> implements SubscriptionScheduling {

	private final Subscription mSubscription;
	private SchedulingType.SCHEDULING_TYPE mType;

	public SubscriptionSchedulingMock(long inId, Subscription inSubscription, SchedulingType.SCHEDULING_TYPE inType) {
		super(inId);
		this.mSubscription = inSubscription;
		this.mType = inType;
	}

	public Subscription getSubscription() {
		return this.mSubscription;
	}

	public SchedulingType.SCHEDULING_TYPE getType() {
		return this.mType;
	}

	public void setType(SchedulingType.SCHEDULING_TYPE inType) {
		this.mType = inType;
	}

}
