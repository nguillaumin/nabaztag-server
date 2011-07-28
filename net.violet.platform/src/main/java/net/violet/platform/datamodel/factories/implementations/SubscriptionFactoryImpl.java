package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.applications.MailAlertHandler;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionImpl;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingImpl;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.SubscriptionSchedulingSettingsImpl;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.SubscriptionFactory;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class SubscriptionFactoryImpl extends RecordFactoryImpl<Subscription, SubscriptionImpl> implements SubscriptionFactory {

	private static Logger LOGGER = Logger.getLogger(SubscriptionFactoryImpl.class);

	private static final String FIND_BY_VOBJECT = " subscription.object_id = ? ";

	public SubscriptionFactoryImpl() {
		super(SubscriptionImpl.SPECIFICATION);
	}

	public Subscription create(Application inApplication, VObject inObject) {
		Subscription subscr = null;
		try {
			subscr = new SubscriptionImpl(inApplication, inObject);
		} catch (final SQLException e) {
			SubscriptionFactoryImpl.LOGGER.fatal(e, e);
		}
		return subscr;
	}

	public Subscription create(Application application, VObject object, Map<String, Object> settings) {
		try {
			return new SubscriptionImpl(application, object, settings);
		} catch (final SQLException e) {
			SubscriptionFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	public List<Subscription> findByApplicationAndObject(Application inApplication, VObject inObject) {
		return findAll("application_id=? AND " + SubscriptionFactoryImpl.FIND_BY_VOBJECT, Arrays.asList(new Object[] { inApplication.getId(), inObject.getId() }));
	}

	public List<Subscription> findAllByObject(VObject inObject) {
		return findAll(SubscriptionFactoryImpl.FIND_BY_VOBJECT, Collections.singletonList((Object) inObject.getId()));
	}

	/**
	 * @throws SQLException
	 * @see net.violet.platform.datamodel.factories.SubscriptionFactory#findAllByObjectAndSchedulingType(net.violet.platform.datamodel.VObject,
	 *      net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE)
	 */
	public List<Subscription> findAllByObjectAndSchedulingType(VObject inObject, SCHEDULING_TYPE schedulingType) {

		final String condition = SubscriptionFactoryImpl.FIND_BY_VOBJECT + " AND subscription_scheduling.subscription_id = subscription.id AND scheduling_type_id = ? ";
		final Object[] values = { inObject.getId(), schedulingType.getRecord().getId() };

		final List<Subscription> lstSub = findAll(new String[] { "subscription_scheduling" }, condition, Arrays.asList(values), null);

		return lstSub;
	}

	public int walkTimelySubscription(Timezone inTimezone, SchedulingType.SCHEDULING_TYPE type, DailyHandler.Weekday day, CCalendar start, CCalendar end, JoinRecordsWalker<Subscription, SubscriptionSchedulingSettings> inWalker) {
		final CCalendar theExclusiveEndTime = ((CCalendar) end.clone());
		theExclusiveEndTime.addSeconds(-1);

		int walkCount = 0;
		final boolean isHumpDay = start.getDay() != end.getDay();

		final String[] joinTables = { "object", "subscription_scheduling" };

		final String theCondition = " object.time_zone IN " + TimezoneFactoryImpl.getTimeZonesCondition(inTimezone) + " AND object.object_id = subscription.object_id AND object.object_mode IN (?,?) " + "AND subscription.id = subscription_scheduling.subscription_id AND subscription_scheduling.scheduling_type_id = ? " + "AND subscription_scheduling.id = subscription_scheduling_settings.subscription_scheduling_id AND " + " subscription_scheduling_settings.scheduling_settings_key = ? " + " AND TIME(subscription_scheduling_settings.scheduling_settings_value) BETWEEN TIME(?) AND TIME(?) ";
		final Object[] commonValues = { VObject.MODE_PING, VObject.MODE_XMPP, type.getRecord().getId() };

//		 Ts <= T < Te where Te <= 23:59:59
		{
			final String startTime = start.getTimeFormated(true);
			final String endTime = (isHumpDay) ? "23:59:59" : theExclusiveEndTime.getTimeFormated(true);

			final Object[] theAddValues = { day.getValue(), startTime, endTime };
			final Object[] theValues = new Object[commonValues.length + theAddValues.length];
			System.arraycopy(commonValues, 0, theValues, 0, commonValues.length);
			System.arraycopy(theAddValues, 0, theValues, commonValues.length, theAddValues.length);

			walkCount += walk(SubscriptionSchedulingSettingsImpl.SPECIFICATION, theCondition, Arrays.asList(theValues), joinTables, null, 0, 0, 0, inWalker);
		}

//		Ts <= T < Te where Ts is '00:00:00' same day as end
		{
			if (isHumpDay) {
				final String startTime = StringShop.MIDNIGHT;
				final String endTime = theExclusiveEndTime.getTimeFormated(true);

				final Object[] theAddValues = { day.next().getValue(), startTime, endTime };
				final Object[] theValues = new Object[commonValues.length + theAddValues.length];
				System.arraycopy(commonValues, 0, theValues, 0, commonValues.length);
				System.arraycopy(theAddValues, 0, theValues, commonValues.length, theAddValues.length);

				walkCount += walk(SubscriptionSchedulingSettingsImpl.SPECIFICATION, theCondition, Arrays.asList(theValues), joinTables, null, 0, 0, 0, inWalker);
			}

		}

		return walkCount;
	}

	public int walkByApplication(Application application, RecordWalker<Subscription> inWalker) {
		return walk(" object.object_id = subscription.object_id AND object.object_mode IN (?,?) " + "AND subscription.application_id = ? ", Arrays.asList(new Object[] { VObject.MODE_PING, VObject.MODE_XMPP, application.getId() }), new String[] { "object" }, null, 0, null, null, inWalker);
		//return walk(" subscription.application_id = ? ", Collections.<Object> singletonList(application.getId()), null, 0, inWalker);
	}

	public List<Subscription> findAllByApplication(Application application) {
		final String condition = " object.object_id = subscription.object_id AND object.object_mode IN (?,?) " + "AND subscription.application_id = ? ";

		final List<Object> values = Arrays.asList(new Object[] { VObject.MODE_PING, VObject.MODE_XMPP, application.getId() });

		return findAll(new String[] { "object" }, condition, values, null);
	}

	public int walkSubscription(RecordWalker<Subscription> recordWalker) {
		return walk("subscription.id > 0 ", Collections.emptyList(), null, 0, recordWalker);
	}

	public int walkSubscriptionBySchedulingTypeAndTimezone(SCHEDULING_TYPE inType, Timezone inTimezone, JoinRecordsWalker<Subscription, SubscriptionScheduling> inWalker) {
		final String condition = " object.time_zone IN " + TimezoneFactoryImpl.getTimeZonesCondition(inTimezone) + " and object.object_id = subscription.object_id AND object.object_mode = ? AND " + "subscription.id = subscription_scheduling.subscription_id AND subscription_scheduling.scheduling_type_id = ? ";

		final List<Object> values = Arrays.asList(new Object[] { VObject.MODE_XMPP, inType.getRecord().getId() });
		final String[] joinTables = { "object" };

		return walk(SubscriptionSchedulingImpl.SPECIFICATION, condition, values, joinTables, null, 0, 0, 0, inWalker);
	}

	public int walkFrequencySubscriptionByTimezone(SCHEDULING_TYPE schedulingType, FrequencyHandler.Frequency inFrequency, Timezone inTimezone, CCalendar inStart, CCalendar inEnd, JoinRecordsWalker<Subscription, SubscriptionScheduling> inWalker) {

		final String condition = " object.time_zone IN " + TimezoneFactoryImpl.getTimeZonesCondition(inTimezone) + " AND object.object_id = subscription.object_id AND object.object_mode IN (?, ?) AND " + " subscription.id = subscription_scheduling.subscription_id AND subscription_scheduling.scheduling_type_id = ? AND " +
		// ssc1 holds FREQUENCY
		" subscription_scheduling.id = ssc1.subscription_scheduling_id AND " +
		// ssc2 holds LAST_TIME
		" subscription_scheduling.id = ssc2.subscription_scheduling_id AND " + " ssc2.scheduling_settings_key = ? AND " + " ssc1.scheduling_settings_key = ? AND ssc1.scheduling_settings_value = ? AND " +

		// ((Te - Tr) DIV freq) * freq + Tr
		"TIMESTAMPADD(" + "SECOND, " + "(TIMESTAMPDIFF( SECOND, ssc2.scheduling_settings_value, ? ) DIV ?) * ?," + " ssc2.scheduling_settings_value)" +
		// BETWEEN Te - d - 1 AND Te
		" BETWEEN ? AND ?";

		final String start = inStart.getTimestamp(inTimezone.getJavaTimeZone());
		final CCalendar enCal = (CCalendar) inEnd.clone();
		enCal.addSeconds(-1);
		final String end = enCal.getTimestamp(inTimezone.getJavaTimeZone());
		final String frequency = String.valueOf(inFrequency.getTimeInSecond());

		final List<Object> values = Arrays.asList(new Object[] { VObject.MODE_PING, VObject.MODE_XMPP, schedulingType.getRecord().getId(), FrequencyHandler.LAST_TIME, FrequencyHandler.FREQUENCY, inFrequency.getLabel(), end, frequency, frequency, start, end });

		return walk(SubscriptionSchedulingImpl.SPECIFICATION, condition, values, new String[] { "object", "subscription_scheduling_settings as ssc2", "subscription_scheduling_settings AS ssc1" }, null, 0, 0, 0, null, null, inWalker);
	}

	public int walkSubscriptionByTimezoneAndNewContent(SCHEDULING_TYPE inType, Timezone inTimezone, JoinRecordsWalker<Subscription, SubscriptionScheduling> inWalker) {

		final String condition = " object.time_zone IN " + TimezoneFactoryImpl.getTimeZonesCondition(inTimezone) + " AND object.object_mode IN (?, ?) AND object.object_id = subscription.object_id AND " + " subscription.id = subscription_scheduling.subscription_id AND subscription_scheduling.scheduling_type_id = ? AND " + " subscription_scheduling_settings.subscription_scheduling_id = subscription_scheduling.id AND " + " subscription_scheduling_settings.scheduling_settings_key = ? AND subscription_scheduling_settings.scheduling_settings_value = ? ";

		final List<Object> values = Arrays.asList(new Object[] { VObject.MODE_PING, VObject.MODE_XMPP, inType.getRecord().getId(), MailAlertHandler.NEW_CONTENT_FLAG, "1" });

		return walk(SubscriptionSchedulingImpl.SPECIFICATION, condition, values, new String[] { "object", "subscription_scheduling_settings" }, null, 0, 0, 0, null, null, inWalker);
	}

	public List<Subscription> findAllByApplications(Application... applications) {
		if ((applications == null) || (applications.length == 0)) {
			return Collections.emptyList();
		}

		final StringBuilder condition = new StringBuilder("application_id = ? ");
		final List<Object> values = new ArrayList<Object>();
		values.add(applications[0].getId());
		for (int i = 1; i < applications.length; i++) {
			condition.append(" OR application_id = ? ");
			values.add(applications[i].getId());
		}

		return findAll(condition.toString(), values);
	}

	private static final Set<String> FILES_IDS = new LinkedHashSet<String>();
	private static long updateTime = 0;
	private static final long TIMEOUT = 60 * 60000;

	public boolean usesFiles(Files inFile) {

		if (SubscriptionFactoryImpl.updateTime < System.currentTimeMillis()) {
			synchronized (SubscriptionFactoryImpl.FILES_IDS) {
				if (SubscriptionFactoryImpl.updateTime < System.currentTimeMillis()) {
					SubscriptionFactoryImpl.FILES_IDS.clear();
					for (final Subscription aSubscription : findAllByApplications(Application.NativeApplication.PODCAST_FULL.getApplication(), Application.NativeApplication.RSS_FULL.getApplication())) {
						final Map<String, Object> settings = aSubscription.getSettings();
						SubscriptionFactoryImpl.FILES_IDS.add(String.valueOf(settings.get(VActionFullHandler.FILE)));
					}
					SubscriptionFactoryImpl.updateTime = System.currentTimeMillis() + +SubscriptionFactoryImpl.TIMEOUT;
				}
			}
		}

		return SubscriptionFactoryImpl.FILES_IDS.contains(inFile.getId().toString());
	}
}
