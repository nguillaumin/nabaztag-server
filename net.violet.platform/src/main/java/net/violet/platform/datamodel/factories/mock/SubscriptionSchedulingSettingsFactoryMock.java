package net.violet.platform.datamodel.factories.mock;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.SubscriptionSchedulingSettingsFactory;
import net.violet.platform.datamodel.mock.SubscriptionSchedulingSettingsMock;

public class SubscriptionSchedulingSettingsFactoryMock extends RecordFactoryMock<SubscriptionSchedulingSettings, SubscriptionSchedulingSettingsMock> implements SubscriptionSchedulingSettingsFactory {

	SubscriptionSchedulingSettingsFactoryMock() {
		super(SubscriptionSchedulingSettingsMock.class);
	}

	public SubscriptionSchedulingSettings create(SubscriptionScheduling inSubscription, String inKey, String inValue) {
		return new SubscriptionSchedulingSettingsMock(0, inSubscription, inKey, inValue);
	}

	public List<SubscriptionSchedulingSettings> findAllBySubscriptionScheduling(SubscriptionScheduling inSubscription) {
		final List<SubscriptionSchedulingSettings> theResult = new LinkedList<SubscriptionSchedulingSettings>();
		for (final SubscriptionSchedulingSettings theSSS : findAll()) {
			if (theSSS.getSubscriptionScheduling().getId().longValue() == inSubscription.getId().longValue()) {
				theResult.add(theSSS);
			}
		}
		return theResult;
	}

	public SubscriptionSchedulingSettings findBySubscriptionSchedulingAndKey(SubscriptionScheduling inSubscription, String key) {
		for (final SubscriptionSchedulingSettings theSubsSetting : findAll()) {
			if ((theSubsSetting.getSubscriptionScheduling().getId().longValue() == inSubscription.getId().longValue()) && theSubsSetting.getKey().equals(key)) {
				return theSubsSetting;
			}
		}
		return null;
	}

	public Map<String, SubscriptionSchedulingSettings> findAllBySubscriptionSchedulingAsMap(SubscriptionScheduling inScheduling) {
		final Map<String, SubscriptionSchedulingSettings> theMap = new HashMap<String, SubscriptionSchedulingSettings>();

		for (final SubscriptionSchedulingSettings aSetting : findAllBySubscriptionScheduling(inScheduling)) {
			theMap.put(aSetting.getKey(), aSetting);
		}

		return Collections.unmodifiableMap(theMap);
	}

	public List<SubscriptionSchedulingSettings> findBySubscriptionAndTypeAndKey(Subscription inSubscription, SCHEDULING_TYPE inType, String inKeyWord) {
		throw new UnsupportedOperationException();
	}

	public boolean usesFiles(Files inFile) {
		for (final SubscriptionSchedulingSettings aSetting : findAll()) {
			if (aSetting.getKey().toString().equals(VActionFullHandler.FILE) && aSetting.getValue().toString().equals(inFile.getId().toString())) {
				return true;
			}
		}
		return false;
	}
}
