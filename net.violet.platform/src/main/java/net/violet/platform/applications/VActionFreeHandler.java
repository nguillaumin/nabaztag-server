package net.violet.platform.applications;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.FeedSubscriptionData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;

public class VActionFreeHandler implements ApplicationHandler {

	public static final String NB_NEWS = "nbNews";

	private final ApplicationData application;
	private final boolean isPodcast;
	private final FeedData feed;

	protected VActionFreeHandler(ApplicationData application) {
		this.application = application;
		final Application.NativeApplication nativeApplication = Application.NativeApplication.findByApplication(this.application.getReference());
		this.isPodcast = nativeApplication == Application.NativeApplication.PODCAST_FREE;
		final ApplicationSettingData feedSetting = ApplicationSettingData.findByApplicationAndKey(this.application, ApplicationSetting.FeedHandler.FEED_ID);
		this.feed = FeedData.findById(Long.parseLong(feedSetting.getValue()));
	}

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		final Map<String, Object> theSettings = new HashMap<String, Object>();

		final String nbNews = this.isPodcast ? "1" : settings.get(VActionFreeHandler.NB_NEWS).toString();
		theSettings.put(VActionFreeHandler.NB_NEWS, nbNews);

		final FeedSubscriptionData feedSubscription = FeedSubscriptionData.findByObjectAndFeed(object, this.feed);
		if ((feedSubscription == null) || !feedSubscription.isValid()) {
			FeedSubscriptionData.create(this.feed, object, Integer.parseInt(nbNews));
		}

		return SubscriptionData.create(this.application, object, theSettings);
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		final Map<String, Object> theSettings = new HashMap<String, Object>();

		final String nbNews = this.isPodcast ? "1" : settings.get(VActionFreeHandler.NB_NEWS).toString();
		theSettings.put(VActionFreeHandler.NB_NEWS, nbNews);

		subscription.setSettings(theSettings);
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) throws MissingSettingException, InvalidSettingException {
		if (this.isPodcast && object.getObjectType().instanceOf(ObjectType.NABAZTAG_V1)) {
			throw new InvalidSettingException("type", "v1");
		}

		if (!this.isPodcast) {
			final Object nbNews = settings.get(VActionFreeHandler.NB_NEWS);
			if (nbNews == null) {
				throw new MissingSettingException(VActionFreeHandler.NB_NEWS);
			}
			final int newsAmount = Integer.parseInt(nbNews.toString());
			if ((newsAmount < 1) || (newsAmount > 30)) {
				throw new InvalidSettingException(VActionFreeHandler.NB_NEWS, nbNews.toString());
			}
		}
	}

	public void delete(SubscriptionData subscription) {
		final FeedSubscriptionData feedSubscription = FeedSubscriptionData.findByObjectAndFeed(subscription.getObject(), this.feed);
		feedSubscription.delete();
		subscription.delete();

	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		return Collections.<String, Object> singletonMap(VActionFreeHandler.NB_NEWS, subscription.getSettings().get(VActionFreeHandler.NB_NEWS).toString());
	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData subscription, VObjectData object, ApplicationData application, int nbNews, String timeW, String timeWe, String frequency) throws InvalidParameterException, ParseException, InvalidSettingException, InvalidSchedulingsException, MissingSettingException {
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(VActionFreeHandler.NB_NEWS, nbNews);

		final List<Map<String, Object>> theScSettings = new LinkedList<Map<String, Object>>();
		if (frequency != null) {
			final Map<String, Object> theScSetting = new HashMap<String, Object>();
			final FrequencyHandler.Frequency period = FrequencyHandler.Frequency.findByTime(frequency);

			if (period == null) {
				throw new InvalidParameterException("FREQUENCY", frequency);
			}

			theScSetting.put("type", SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency.getLabel());
			theScSetting.put("frequency", period.getLabel());
			theScSettings.add(theScSetting);
		} else if ((timeW != null) || (timeWe != null)) {

			final Map<List<DailyHandler.Weekday>, CCalendar> theTimes = new HashMap<List<DailyHandler.Weekday>, CCalendar>();
			if (timeW != null) {
				final CCalendar theCalendar = new CCalendar(true);
				theCalendar.setTimeFormatted(timeW);
				theTimes.put(DailyHandler.Weekday.getWorkweekDays(), theCalendar);
			}
			if (timeWe != null) {
				final CCalendar theCalendar = new CCalendar(true);
				theCalendar.setTimeFormatted(timeWe);
				theTimes.put(DailyHandler.Weekday.getWeekEndDays(), theCalendar);
			}

			theScSettings.addAll(ApplicationHandlerHelper.ExternalSettingToolBox.generateDailySchedulings(Collections.singletonList(theTimes)));
		} else {
			throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "a frequency or at least on set time is needed");
		}

		if (subscription == null) {
			return SubscriptionManager.createSubscription(application, object, theSettings, theScSettings, null);
		}

		SubscriptionManager.updateSubscription(subscription, theSettings, theScSettings, null);
		return subscription;
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return null;
	}

}
