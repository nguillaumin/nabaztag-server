package net.violet.platform.api.maps;

import net.violet.platform.dataobjects.SubscriptionSchedulingData;

public class SchedulingInformationMap extends AbstractAPIMap {

	public SchedulingInformationMap(SubscriptionSchedulingData inSubscriptionSchedulingData) {
		super(10);
		put("type", inSubscriptionSchedulingData.getType().toString());
	}
}
