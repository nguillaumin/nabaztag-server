package net.violet.platform.handlers.rfid;

import java.sql.SQLException;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.ObjectProfileImpl;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.events.ZtampDetectionEvent;
import net.violet.platform.schedulers.InteractionTriggerHandler;
import net.violet.platform.util.DicoTools;

import org.apache.log4j.Logger;

public class PlayerInitializer implements RfidInitializer {

	private static final Logger LOGGER = Logger.getLogger(PlayerInitializer.class);

	public void initRfid(VObject rfid, String defaultParam) {

		final Application theBookApplication = Factories.APPLICATION.find(Integer.parseInt(defaultParam));
		if (theBookApplication != null) {
			try {
				ObjectProfile profile = rfid.getProfile();
				if (profile == null) {
					profile = new ObjectProfileImpl(rfid, null, net.violet.common.StringShop.EMPTY_STRING, rfid.getObject_login());
				}
				profile.setPicture(theBookApplication.getProfile().getPictureFile());

				final String rfidLabel = DicoTools.dico_if(rfid.getOwner().getAnnu().getLangPreferences(), theBookApplication.getProfile().getTitle());
				profile.setLabel(rfidLabel);

				if (SubscriptionData.findByApplicationAndObject(theBookApplication, rfid).isEmpty()) {
					final Subscription theSubscription = Factories.SUBSCRIPTION.create(theBookApplication, rfid);
					final SubscriptionScheduling theScheduling = Factories.SUBSCRIPTION_SCHEDULING.create(theSubscription, SchedulingType.SCHEDULING_TYPE.InteractionTrigger);
					Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(theScheduling, InteractionTriggerHandler.EVENT, ZtampDetectionEvent.NAME);
				}
			} catch (final SQLException e) {
				PlayerInitializer.LOGGER.fatal(e, e);
			} catch (final NullPointerException e) {
				PlayerInitializer.LOGGER.fatal(e, e);
			}
		}
	}
}
