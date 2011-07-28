package net.violet.platform.datamodel.factories;

import java.util.List;
import java.util.Map;

import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.common.FilesAccessor;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;

public interface SubscriptionFactory extends RecordFactory<Subscription>, FilesAccessor {

	Subscription create(Application inApplication, VObject inObject);

	List<Subscription> findByApplicationAndObject(Application inApplication, VObject inObject);

	List<Subscription> findAllByObject(VObject inObject);

	int walkTimelySubscription(Timezone inTimezone, SchedulingType.SCHEDULING_TYPE type, DailyHandler.Weekday day, CCalendar start, CCalendar end, JoinRecordsWalker<Subscription, SubscriptionSchedulingSettings> inWalker);

//	int walkByApplicationIdWithDistinctSettingValue(long id, String inSettingsKey, RecordWalker<Subscription> inWalker);

	//int walkByApplicationIdAndSettings(Long inApplicationId, String inKey, String inValue, String inKeyOfWantedValue, JoinRecordsWalker<Subscription, SubscriptionSettings> inWalker);

	int walkSubscriptionBySchedulingTypeAndTimezone(SCHEDULING_TYPE inType, Timezone inTimezone, JoinRecordsWalker<Subscription, SubscriptionScheduling> inWalker);

	List<Subscription> findAllByObjectAndSchedulingType(VObject inObject, SCHEDULING_TYPE schedulingType);

	int walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE inType, FrequencyHandler.Frequency inFrequency, Timezone theTimezone, CCalendar start, CCalendar end, JoinRecordsWalker<Subscription, SubscriptionScheduling> joinRecordsWalker);

	int walkSubscriptionByTimezoneAndNewContent(SCHEDULING_TYPE inType, Timezone inTimezone, JoinRecordsWalker<Subscription, SubscriptionScheduling> inWalker);

	Subscription create(Application application, VObject object, Map<String, Object> settings);

	List<Subscription> findAllByApplication(Application application);

	int walkSubscription(RecordWalker<Subscription> recordWalker);

	int walkByApplication(Application application, RecordWalker<Subscription> walker);

}
