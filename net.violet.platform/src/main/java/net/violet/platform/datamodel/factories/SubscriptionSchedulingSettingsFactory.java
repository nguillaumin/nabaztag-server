package net.violet.platform.datamodel.factories;

import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface SubscriptionSchedulingSettingsFactory extends RecordFactory<SubscriptionSchedulingSettings>, FilesAccessor {

	SubscriptionSchedulingSettings create(SubscriptionScheduling inSubscription, String inKey, String inValue);

	List<SubscriptionSchedulingSettings> findAllBySubscriptionScheduling(SubscriptionScheduling inSubscription);

	SubscriptionSchedulingSettings findBySubscriptionSchedulingAndKey(SubscriptionScheduling inSubscription, String key);

	Map<String, SubscriptionSchedulingSettings> findAllBySubscriptionSchedulingAsMap(SubscriptionScheduling inScheduling);

	List<SubscriptionSchedulingSettings> findBySubscriptionAndTypeAndKey(Subscription inSubscription, SCHEDULING_TYPE inType, String inKeyWord);

}
