package net.violet.platform.api.maps.applications;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.applications.ApplicationHandlerManager;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.schedulers.SchedulingHandlerManager;

public class ApplicationSubscriptionMap extends AbstractAPIMap {

	public ApplicationSubscriptionMap(APICaller inCaller, SubscriptionData inSubscriptionData) {
		super(5);

		put("id", inSubscriptionData.getApiId(inCaller));

		final ApplicationData applicationData = inSubscriptionData.getApplication();
		if (applicationData != null) {
			put("application_id", applicationData.getApiId(inCaller));
		}

		if (inSubscriptionData.getObject() != null) {
			put("object_id", inSubscriptionData.getObject().getApiId(inCaller));
		}

		//FIXME hack completement moche pour faire fonctionner le mirware !!
		final Map<String, Object> settings = new HashMap<String, Object>(ApplicationHandlerManager.getUISettings(inSubscriptionData, inCaller));
		settings.put("length", 0);
		put("settings", settings);
		put("scheduling", SchedulingHandlerManager.getUISettings(inSubscriptionData, inCaller));

		final String theInfo = ApplicationHandlerManager.getSubscriptionInformation(inSubscriptionData);
		if ((theInfo != null) && !net.violet.common.StringShop.EMPTY_STRING.equals(theInfo)) {
			put("information", theInfo);
		}

	}
}
