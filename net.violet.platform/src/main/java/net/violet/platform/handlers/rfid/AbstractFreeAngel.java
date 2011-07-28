package net.violet.platform.handlers.rfid;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.events.ZtampDetectionEvent;
import net.violet.platform.events.ZtampRemovalEvent;
import net.violet.platform.schedulers.InteractionTriggerHandler;

/**
 * FreeAngel applications automatically install themself with a double activation :
 * on ztamp detection and on ztamp removal
 * @author christophe - Violet
 */
public abstract class AbstractFreeAngel extends AbstractApplicationInitializer {

	@Override
	public void initRfid(VObject rfid, String defaultParam) {

		final Application promoApp = getApplication();

		if (SubscriptionData.findByApplicationAndObject(promoApp, rfid).isEmpty()) {
			final Subscription subscr = Factories.SUBSCRIPTION.create(promoApp, rfid);
			final SubscriptionScheduling theDetectionScheduling = Factories.SUBSCRIPTION_SCHEDULING.create(subscr, SchedulingType.SCHEDULING_TYPE.InteractionTrigger);
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theDetectionScheduling, InteractionTriggerHandler.EVENT, ZtampDetectionEvent.NAME);
			final SubscriptionScheduling theRemovalScheduling = Factories.SUBSCRIPTION_SCHEDULING.create(subscr, SchedulingType.SCHEDULING_TYPE.InteractionTrigger);
			Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theRemovalScheduling, InteractionTriggerHandler.EVENT, ZtampRemovalEvent.NAME);
		}
	}
}
