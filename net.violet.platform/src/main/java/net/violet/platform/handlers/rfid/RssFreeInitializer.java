package net.violet.platform.handlers.rfid;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.applications.VActionFreeHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.events.ZtampDetectionEvent;
import net.violet.platform.schedulers.InteractionTriggerHandler;

import org.apache.log4j.Logger;

public class RssFreeInitializer implements RfidInitializer {

	private static final Logger LOGGER = Logger.getLogger(RssFreeInitializer.class);

	private final static String NB_DEFAULT_NEWS = "5";

	public void initRfid(VObject rfid, String defaultParam) {
		if (defaultParam != null) {
			final Application theRssApplication = Factories.APPLICATION.find(Integer.parseInt(defaultParam));
			if ((theRssApplication != null) && SubscriptionData.findByApplicationAndObject(theRssApplication, rfid).isEmpty()) {
				final ApplicationSetting setting = Factories.APPLICATION_SETTING.findByApplicationAndKey(theRssApplication, ApplicationSetting.FeedHandler.FEED_ID);
				final Feed feed = setting != null ? Factories.FEED.find(Long.parseLong(setting.getValue())) : null;

				if (feed != null) {

					final Map<String, Object> settings = new HashMap<String, Object>();
					settings.put(VActionFreeHandler.NB_NEWS, RssFreeInitializer.NB_DEFAULT_NEWS);

					final List<Map<String, Object>> theScSettings = new LinkedList<Map<String, Object>>();
					final Map<String, Object> theScSetting = new HashMap<String, Object>();
					theScSetting.put("type", SchedulingType.SCHEDULING_TYPE.InteractionTrigger.getLabel());
					theScSetting.put(InteractionTriggerHandler.EVENT, ZtampDetectionEvent.NAME);
					theScSettings.add(theScSetting);

					try {
						SubscriptionManager.createSubscription(ApplicationData.getData(theRssApplication), VObjectData.getData(rfid), settings, theScSettings, null);
					} catch (final Exception e) {
						RssFreeInitializer.LOGGER.fatal("Exception on object ID : " + rfid.getId() + " and application ID : " + defaultParam, e);
					}
				}
			}
		}
	}
}
