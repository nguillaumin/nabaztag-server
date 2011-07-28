package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;

public interface SubscriptionSchedulingFactory extends RecordFactory<SubscriptionScheduling> {

	SubscriptionScheduling create(Subscription inSubscription, SchedulingType.SCHEDULING_TYPE inType);

	List<SubscriptionScheduling> findAllBySubscription(Subscription inSubscription);

	List<SubscriptionScheduling> findAllBySubscriptionAndType(Subscription inSubscription, SchedulingType.SCHEDULING_TYPE inType);

	List<SubscriptionScheduling> findAllByObjectAndType(VObject object, SCHEDULING_TYPE type);

}
