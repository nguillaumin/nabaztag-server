package net.violet.platform.schedulers;

import java.util.Collections;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.VObjectData;

public class EmptyHandler implements SchedulingHandler {

	public void deleteElements(SubscriptionSchedulingData scheduling) {
		// Nothing to do
	}

	public void executeWhenDone(SubscriptionSchedulingData scheduling) {
		// Nothing to do
	}

	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		return Collections.emptyMap();
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		// Nothing to do
	}

	public SchedulingInformationMap getSchedulingInformation(SubscriptionSchedulingData scheduling, APICaller caller) {
		return new SchedulingInformationMap(scheduling);
	}

}
