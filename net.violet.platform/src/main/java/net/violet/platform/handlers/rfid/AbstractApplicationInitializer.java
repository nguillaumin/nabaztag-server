package net.violet.platform.handlers.rfid;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.events.ZtampDetectionEvent;
import net.violet.platform.schedulers.InteractionTriggerHandler;

/**
 * The initializer to extend to implement quick promo apps initialization.
 * These promo apps typically requires no settings parameters
 */
public abstract class AbstractApplicationInitializer implements RfidInitializer {

	/**
	 * Create the application subscription with a Trigger event of type ZtampDetection
	 * @see net.violet.platform.handlers.rfid.RfidInitializer#initRfid(net.violet.platform.datamodel.VObject, java.lang.String)
	 */
	public void initRfid(VObject rfid, String defaultParam) {

		final Application promoApp = getApplication();

		if (SubscriptionData.findByApplicationAndObject(promoApp, rfid).isEmpty()) {
			final Subscription subscr = Factories.SUBSCRIPTION.create(promoApp, rfid);
			final SubscriptionScheduling theScheduling = Factories.SUBSCRIPTION_SCHEDULING.create(subscr, SchedulingType.SCHEDULING_TYPE.InteractionTrigger);
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theScheduling, InteractionTriggerHandler.EVENT, ZtampDetectionEvent.NAME);
		}
	}

	/**
	 * @return the application to install on this RFID
	 */
	protected abstract Application getApplication();

}
