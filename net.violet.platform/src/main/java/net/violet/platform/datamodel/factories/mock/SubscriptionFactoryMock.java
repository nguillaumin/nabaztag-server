package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.applications.MailAlertHandler;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.daemons.schedulers.NewContentWithFrequencyScheduler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.SubscriptionFactory;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;

public class SubscriptionFactoryMock extends RecordFactoryMock<Subscription, SubscriptionMock> implements SubscriptionFactory {

	SubscriptionFactoryMock() {
		super(SubscriptionMock.class);
	}

	public Subscription create(Application inApplication, VObject inObject) {
		return new SubscriptionMock(0, inApplication, inObject);
	}

	public Subscription create(Application application, VObject object, Map<String, Object> settings) {
		final Subscription theSub = create(application, object);
		theSub.setSettings(settings);
		return theSub;
	}

	public List<Subscription> findByApplicationAndObject(Application inApplication, VObject inObject) {
		final List<Subscription> theResult = new LinkedList<Subscription>();

		for (final Subscription theSubscription : findAll()) {
			if ((theSubscription.getApplication().getId().longValue() == inApplication.getId().longValue()) && (theSubscription.getObject().getId().longValue() == inObject.getId().longValue())) {
				theResult.add(theSubscription);
			}
		}
		return theResult;
	}

	public List<Subscription> findAllByObject(VObject inObject) {
		final List<Subscription> result = new LinkedList<Subscription>();
		for (final Subscription subscription : findAll()) {
			if (subscription.getObject().equals(inObject)) {
				result.add(subscription);
			}
		}

		return result;
	}

	public int walkTimelySubscription(Timezone inTimezone, SchedulingType.SCHEDULING_TYPE type, DailyHandler.Weekday day, CCalendar start, CCalendar end, JoinRecordsWalker<Subscription, SubscriptionSchedulingSettings> inWalker) {

		final int amount = 0;

		for (final Subscription aSubscription : findAll()) {

			final VObject theObject = aSubscription.getObject();
			final Timezone theTimeZone = theObject.getTimeZone();

			if (Factories.TIMEZONE.findAllFriends(inTimezone).contains(theTimeZone) && ((theObject.getObject_mode() == VObject.MODE_PING) || (theObject.getObject_mode() == VObject.MODE_XMPP))) {

				for (final SubscriptionScheduling scheduling : Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(aSubscription, type)) {
					DailyHandler.Weekday aDay = day;
					for (int deltaDay = end.getDay() - (start.getDay() % start.getActualMaximum(Calendar.DAY_OF_MONTH)), i = 0; i <= deltaDay; i++, aDay = aDay.next()) {
						final SubscriptionSchedulingSettings theSetting = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findBySubscriptionSchedulingAndKey(scheduling, aDay.getValue());

						if (theSetting != null) {

							final CCalendar theCalendar = (CCalendar) start.clone();
							theCalendar.setTimeMYSQL(theSetting.getValue());
							theCalendar.add(Calendar.DAY_OF_MONTH, i);

							if (theCalendar.before(end) && (theCalendar.after(start) || theCalendar.equals(start))) {
								inWalker.process(aSubscription, theSetting);
							}

						}

					}

				}

			}
		}

		return amount;
	}

	public int walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE schedulingType, FrequencyHandler.Frequency inFrequency, Timezone inTimezone, CCalendar start, CCalendar end, JoinRecordsWalker<Subscription, SubscriptionScheduling> inWalker) {

		int amount = 0;

		for (final Subscription aSubscription : findAll()) {
			final VObject theObject = aSubscription.getObject();
			final Timezone theTimeZone = theObject.getTimeZone();
			for (final SubscriptionScheduling scheduling : Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(aSubscription, schedulingType)) {

				if (Factories.TIMEZONE.findAllFriends(inTimezone).contains(theTimeZone) && ((theObject.getObject_mode() == VObject.MODE_PING) || (theObject.getObject_mode() == VObject.MODE_XMPP)) && (scheduling != null)) {
					final Map<String, SubscriptionSchedulingSettingsData> theSubscriptionSchedulingSettings = SubscriptionSchedulingSettingsData.findAllBySubscriptionSchedulingAsMap(SubscriptionSchedulingData.getData(scheduling));

					final SubscriptionSchedulingSettingsData theFrequencySetting = theSubscriptionSchedulingSettings.get(FrequencyHandler.FREQUENCY);

					if ((theFrequencySetting != null) && theFrequencySetting.getValue().equals(inFrequency.getLabel())) {
						final SubscriptionSchedulingSettingsData theRefTimeSetting = theSubscriptionSchedulingSettings.get(FrequencyHandler.LAST_TIME);
						final CCalendar theRefCal = CCalendar.parseTimestamp(theRefTimeSetting.getValue(), inTimezone.getTimezone_javaId());
						final CCalendar theDeliveryDate = new NewContentWithFrequencyScheduler(new String[] { "-p", "10" }).getDeliveryDate(theRefCal, start, end, inFrequency).getSecond();

						if ((theDeliveryDate == null) || (theDeliveryDate.after(start) && theDeliveryDate.before(end))) {
							amount++;
							inWalker.process(aSubscription, scheduling);

						}
					}

				}
			}
		}

		return amount;
	}

	public int walkByApplication(Application application, RecordWalker<Subscription> inWalker) {
		int amount = 0;

		for (final Subscription aSubscription : findAll()) {

			if (aSubscription.getApplication().getId().longValue() == application.getId()) {
				amount++;
				inWalker.process(aSubscription);
			}
		}

		return amount;
	}

	public int walkSubscriptionBySchedulingTypeAndTimezone(SCHEDULING_TYPE inType, Timezone inTimezone, JoinRecordsWalker<Subscription, SubscriptionScheduling> inWalker) {
		int amount = 0;

		for (final Subscription aSubscription : findAll()) {
			final VObject theObject = aSubscription.getObject();
			final Timezone theTimeZone = theObject.getTimeZone();

			if (Factories.TIMEZONE.findAllFriends(inTimezone).contains(theTimeZone) && ((theObject.getObject_mode() == VObject.MODE_PING) || (theObject.getObject_mode() == VObject.MODE_XMPP))) {

				for (final SubscriptionScheduling scheduling : Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(aSubscription, inType)) {
					if (scheduling.getType() == inType) {
						amount++;
						inWalker.process(aSubscription, scheduling);
					}
				}
			}
		}

		return amount;
	}

	public List<Subscription> findAllByObjectAndSchedulingType(VObject inObject, SCHEDULING_TYPE schedulingType) {
		final List<Subscription> theResult = new ArrayList<Subscription>();
		for (final Subscription aSubscription : findAll()) {
			if (inObject.equals(aSubscription.getObject())) {
				if (!Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(aSubscription, schedulingType).isEmpty()) {
					theResult.add(aSubscription);
				}
			}
		}
		return theResult;
	}

	public int walkSubscriptionByTimezoneAndNewContent(SCHEDULING_TYPE inType, Timezone inTimezone, JoinRecordsWalker<Subscription, SubscriptionScheduling> inWalker) {
		int amount = 0;

		for (final Subscription aSubscription : findAll()) {
			final VObject theObject = aSubscription.getObject();
			final Timezone theTimeZone = theObject.getTimeZone();

			if (Factories.TIMEZONE.findAllFriends(inTimezone).contains(theTimeZone) && ((theObject.getObject_mode() == VObject.MODE_PING) || (theObject.getObject_mode() == VObject.MODE_XMPP))) {

				for (final SubscriptionScheduling aScheduling : Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(aSubscription, inType)) {
					final SubscriptionSchedulingSettingsData theScSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(SubscriptionSchedulingData.getData(aScheduling), MailAlertHandler.NEW_CONTENT_FLAG);

					if ((theScSetting != null) && theScSetting.getValue().equals("1")) {
						inWalker.process(aSubscription, aScheduling);
						amount++;
					}
				}

			}
		}

		return amount;
	}

	public boolean isSettingPresentByKeyAndValue(String key, String value) {
		for (final Subscription aSubscription : findAll()) {
			final Map<String, Object> settings = aSubscription.getSettings();
			if (value.equals(String.valueOf(settings.get(key)))) {
				return true;
			}
		}

		return false;
	}

	public List<Subscription> findAllByApplication(Application application) {
		final List<Subscription> result = new ArrayList<Subscription>();
		for (final Subscription sub : findAll()) {
			if (sub.getApplication().equals(application)) {
				result.add(sub);
			}
		}

		return result;
	}

	public int walkSubscription(RecordWalker<Subscription> recordWalker) {
		int amount = 0;
		for (final Subscription aSub : findAll()) {
			recordWalker.process(aSub);
			amount++;
		}
		return amount;
	}

	public boolean usesFiles(Files inFile) {
		return isSettingPresentByKeyAndValue(VActionFullHandler.FILE, inFile.getId().toString());
	}

}
