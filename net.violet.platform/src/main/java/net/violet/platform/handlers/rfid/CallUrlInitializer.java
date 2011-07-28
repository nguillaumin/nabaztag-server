package net.violet.platform.handlers.rfid;

import java.util.Collections;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.events.ZtampDetectionEvent;
import net.violet.platform.schedulers.InteractionTriggerHandler;

public class CallUrlInitializer implements RfidInitializer {

	public static final Application APPLICATION = Factories.APPLICATION.findByName("net.violet.js.callurl");

	private static final String URL = "url";

	public void initRfid(VObject rfid, String defaultParam) {
		if (SubscriptionData.findByApplicationAndObject(CallUrlInitializer.APPLICATION, rfid).isEmpty()) {
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(CallUrlInitializer.APPLICATION, rfid);
			final SubscriptionScheduling theScheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.InteractionTrigger);
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theScheduling, InteractionTriggerHandler.EVENT, ZtampDetectionEvent.NAME);
			theSubscription.setSettings(Collections.singletonMap(CallUrlInitializer.URL, defaultParam));
		}
	}

}
