package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.SubscriptionSchedulingSettingsImpl;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.SubscriptionSchedulingSettingsFactory;

import org.apache.log4j.Logger;

public class SubscriptionSchedulingSettingsFactoryImpl extends RecordFactoryImpl<SubscriptionSchedulingSettings, SubscriptionSchedulingSettingsImpl> implements SubscriptionSchedulingSettingsFactory {

	private static Logger LOGGER = Logger.getLogger(SubscriptionSchedulingSettingsFactoryImpl.class);

	SubscriptionSchedulingSettingsFactoryImpl() {
		super(SubscriptionSchedulingSettingsImpl.SPECIFICATION);
	}

	public SubscriptionSchedulingSettings create(SubscriptionScheduling inSubscription, String inKey, String inValue) {
		SubscriptionSchedulingSettings theResult = null;
		try {
			theResult = new SubscriptionSchedulingSettingsImpl(inSubscription, inKey, inValue);
		} catch (final SQLException e) {
			SubscriptionSchedulingSettingsFactoryImpl.LOGGER.fatal(e, e);
		}
		return theResult;
	}

	public List<SubscriptionSchedulingSettings> findAllBySubscriptionScheduling(SubscriptionScheduling inSubscription) {
		return findAll("subscription_scheduling_id = ?", Collections.singletonList((Object) inSubscription.getId()));
	}

	public SubscriptionSchedulingSettings findBySubscriptionSchedulingAndKey(SubscriptionScheduling inSubscription, String key) {
		return find("subscription_scheduling_id = ? AND scheduling_settings_key = ?", Arrays.asList(new Object[] { inSubscription.getId(), key }));
	}

	public Map<String, SubscriptionSchedulingSettings> findAllBySubscriptionSchedulingAsMap(SubscriptionScheduling inScheduling) {
		final Map<String, SubscriptionSchedulingSettings> theMap = new HashMap<String, SubscriptionSchedulingSettings>();

		for (final SubscriptionSchedulingSettings aSetting : findAllBySubscriptionScheduling(inScheduling)) {
			theMap.put(aSetting.getKey(), aSetting);
		}

		return Collections.unmodifiableMap(theMap);
	}

	public List<SubscriptionSchedulingSettings> findBySubscriptionAndTypeAndKey(Subscription inSubscription, SCHEDULING_TYPE inType, String inKeyWord) {
		final String condition = "subscription_scheduling.subscription_id = ? AND subscription_scheduling.scheduling_type_id = ? " + "AND subscription_scheduling_settings.subscription_scheduling_id = subscription_scheduling.id " + "AND sss.subscription_scheduling_id = subscription_scheduling_settings.subscription_scheduling_id " + "AND sss.scheduling_settings_value = ?";
		final List<Object> thevalues = Arrays.asList(new Object[] { inSubscription.getId(), inType.getRecord().getId(), inKeyWord });
		final String[] theJoinTables = new String[] { "subscription_scheduling", "subscription_scheduling_settings as sss" };
		return findAll(theJoinTables, condition, thevalues, null);
	}

	public boolean usesFiles(Files inFile) {
		return count(null, "subscription_scheduling_settings.scheduling_settings_key = ? AND subscription_scheduling_settings.scheduling_settings_value = ?", Arrays.asList(new Object[] { VActionFullHandler.FILE, inFile.getId() }), null) > 0;
	}

}
