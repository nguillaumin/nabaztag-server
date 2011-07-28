package net.violet.platform.applications;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.daemons.crawlers.feedHandler.ConnectionHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.FeedSubscriptionData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.FeedsTools;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.voice.TTSServices;

import org.apache.log4j.Logger;

public class VActionFullHandler implements ApplicationHandler {

	private static final Logger LOGGER = Logger.getLogger(VActionFullHandler.class);

	public static final String URL = "url";

	public static final String LANG = "language";
	public static final String LABEL = "label";
	public static final String FILE = "fileId";

	public static final String NB_NEWS = "nbNews";

	public static final String ACTION = "actionId";
	public static final String FEED = "feed_id";

	private final ApplicationData application;
	private final boolean isPodcast;
	private final Feed.Type type;

	public VActionFullHandler(ApplicationData application) {
		this.application = application;
		final Application.NativeApplication nativeApplication = Application.NativeApplication.findByApplication(this.application.getReference());
		this.isPodcast = nativeApplication == Application.NativeApplication.PODCAST_FULL;
		this.type = this.isPodcast ? Feed.Type.PODCAST : Feed.Type.RSS;
	}

	private static final Pattern FEED_REPLACER = Pattern.compile("feed:\\/\\/", Pattern.CASE_INSENSITIVE);

	private static String getNiceUrl(String dirtyUrl) {
		return VActionFullHandler.FEED_REPLACER.matcher(dirtyUrl).replaceFirst(ConnectionHandler.PROTOCOL_HTTP).trim();
	}

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		final Map<String, Object> theSettings = new HashMap<String, Object>();

		final String theText = settings.get(VActionFullHandler.LABEL).toString();
		final boolean needsTreatment = object.getObjectType().instanceOf(ObjectType.NABAZTAG_V1);
		final TtsVoice theVoice = Factories.TTSVOICE.findByLang(Factories.LANG.findByIsoCode(settings.get(VActionFullHandler.LANG).toString()));
		final Files files = TTSServices.getDefaultInstance().postTTS(theText, needsTreatment, needsTreatment, theVoice);
		if (files == null) {
			VActionFullHandler.LOGGER.fatal("tts generation failed");
			return null;
		}

		final String url = VActionFullHandler.getNiceUrl(settings.get(VActionFullHandler.URL).toString());

		final TtsLangData feedLanguage = FeedsTools.extractFeedLanguage(url);
		final FeedData theFeed = FeedData.getFeed(url, feedLanguage, this.type);

		theSettings.put(VActionFullHandler.FEED, theFeed.getId().toString());

		final String nbNews = this.isPodcast ? "1" : settings.get(VActionFullHandler.NB_NEWS).toString();

		FeedSubscriptionData.create(theFeed, object, Integer.parseInt(nbNews));

		theSettings.put(VActionFullHandler.NB_NEWS, nbNews);
		theSettings.put(VActionFullHandler.FILE, files.getId());
		theSettings.put(VActionFullHandler.LANG, theVoice.getLang().getIsoCode());
		theSettings.put(VActionFullHandler.LABEL, theText);

		return SubscriptionData.create(this.application, object, theSettings);
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		final VObjectData object = subscription.getObject();
		final String theText = settings.get(VActionFullHandler.LABEL).toString();
		final Lang theLang = Factories.LANG.findByIsoCode(settings.get(VActionFullHandler.LANG).toString());
		final String theOldText = subscription.getSettings().get(VActionFullHandler.LABEL).toString();
		final Lang theOldLang = Factories.LANG.findByIsoCode(subscription.getSettings().get(VActionFullHandler.LANG).toString());

		final long fileId;
		final String langCode;
		if (theText.equals(theOldText) && theLang.equals(theOldLang)) { // text and lang didn't change. Keep the same announce file
			fileId = Long.parseLong(subscription.getSettings().get(VActionFullHandler.FILE).toString());
			langCode = subscription.getSettings().get(VActionFullHandler.LANG).toString();
		} else {
			final boolean needsTreatment = object.getObjectType().instanceOf(ObjectType.NABAZTAG_V1);
			final TtsVoice theVoice = Factories.TTSVOICE.findByLang(theLang);
			final Files files = TTSServices.getDefaultInstance().postTTS(theText, needsTreatment, needsTreatment, theVoice);
			if (files == null) {
				VActionFullHandler.LOGGER.fatal("tts generation failed");
				return;
			}
			langCode = theVoice.getLang().getIsoCode();
			fileId = files.getId();
			deleteFile(subscription);
		}

		final String url = VActionFullHandler.getNiceUrl(settings.get(VActionFullHandler.URL).toString());

		final TtsLangData feedLanguage = FeedsTools.extractFeedLanguage(url);
		final FeedData theNewFeed = FeedData.getFeed(url, feedLanguage, this.type);

		final String nbNews = this.isPodcast ? "1" : settings.get(VActionFullHandler.NB_NEWS).toString();

		final FeedData currentFeed = getFeed(subscription);
		final FeedSubscriptionData feedSubscription = FeedSubscriptionData.findByObjectAndFeed(object, currentFeed);
		feedSubscription.updateFeedSubscription(theNewFeed, Integer.parseInt(nbNews));

		theSettings.put(VActionFullHandler.FEED, theNewFeed.getId().toString());

		theSettings.put(VActionFullHandler.NB_NEWS, nbNews);
		theSettings.put(VActionFullHandler.FILE, fileId);
		theSettings.put(VActionFullHandler.LANG, langCode);
		theSettings.put(VActionFullHandler.LABEL, theText);

		subscription.setSettings(theSettings);
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) throws MissingSettingException, InvalidSettingException {
		final Object urlSetting = settings.get(VActionFullHandler.URL);
		if (urlSetting == null) {
			throw new MissingSettingException(VActionFullHandler.URL);
		}

		final Object langSetting = settings.get(VActionFullHandler.LANG);
		if (langSetting == null) {
			throw new MissingSettingException(VActionFullHandler.LANG);
		}

		if (Factories.LANG.findByIsoCode(langSetting.toString()) == null) {
			throw new InvalidSettingException(VActionFullHandler.LANG, langSetting.toString());
		}

		final Object labelSetting = settings.get(VActionFullHandler.LABEL);
		if (labelSetting == null) {
			throw new MissingSettingException(VActionFullHandler.LABEL);
		}

		if (!this.isPodcast) {
			final Object newsSetting = settings.get(VActionFullHandler.NB_NEWS);
			if (newsSetting == null) {
				throw new MissingSettingException(VActionFullHandler.NB_NEWS);
			}
			try {
				final int newsAmount = Integer.parseInt(newsSetting.toString());
				if ((newsAmount < 1) || (newsAmount > 30)) {
					throw new InvalidSettingException(VActionFullHandler.NB_NEWS, newsSetting.toString());
				}
			} catch (final NumberFormatException e) {
				throw new InvalidSettingException(VActionFullHandler.NB_NEWS, newsSetting.toString());
			}
		} else {
			if (object.getObjectType().instanceOf(ObjectType.NABAZTAG_V1)) {
				throw new InvalidSettingException("type", "v1");
			}
		}

		final String niceUrl = VActionFullHandler.getNiceUrl(urlSetting.toString());
		if ((FeedData.findByUrlAndType(niceUrl, this.type) == null) && !FeedsTools.isFeedValid(niceUrl, null, null)) {
			throw new InvalidSettingException(VActionFullHandler.URL, urlSetting.toString() + " [" + niceUrl + "]");
		}

	}

	private void deleteFile(SubscriptionData subscription) {
		final Object fileId = subscription.getSettings().get(VActionFullHandler.FILE);
		if (fileId != null) {
			final Files theFiles = Factories.FILES.find(Long.parseLong(fileId.toString()));
			if (theFiles != null) {
				theFiles.scheduleDeletion();
			}
		}
	}

	private FeedData getFeed(SubscriptionData subscription) {
		final Object feedSetting = subscription.getSettings().get(VActionFullHandler.FEED);
		return FeedData.findById(Long.parseLong(feedSetting.toString()));
	}

	public void delete(SubscriptionData subscription) {
		final FeedData feed = getFeed(subscription);
		if ((feed != null) && feed.isValid()) {
			final FeedSubscriptionData feedSubscription = FeedSubscriptionData.findByObjectAndFeed(subscription.getObject(), feed);
			if ((feedSubscription != null) && feedSubscription.isValid()) {
				feedSubscription.delete();
			}

			if (feed.shouldBeDeleted()) {
				feed.delete();
			}
		}

		deleteFile(subscription);
		subscription.delete();
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		final Map<String, Object> settings = new HashMap<String, Object>();
		for (final Entry<String, Object> anEntry : subscription.getSettings().entrySet()) {
			final String key = anEntry.getKey();
			if (key.equals(VActionFullHandler.FEED)) {
				final FeedData feed = FeedData.findById(Integer.parseInt(anEntry.getValue().toString()));
				settings.put(VActionFullHandler.URL, feed.getUrl());
			} else if (!key.equals(VActionFullHandler.FILE)) {
				settings.put(key, anEntry.getValue());
			}
		}
		return settings;
	}

	public boolean hasAlreadySubscribed(FeedData theFeed, VObjectData theObject) {
		for (final Subscription sub : Factories.SUBSCRIPTION.findByApplicationAndObject(this.application.getReference(), theObject.getReference())) {
			final Object actionSetting = sub.getSettings().get(VActionFullHandler.FEED);
			if ((actionSetting != null) && theFeed.getId().toString().equals(actionSetting.toString())) {
				return true;
			}
		}

		return false;
	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData subscription, VObjectData object, int nbNews, String timeW, String timeWe, String frequency, String alertName, String url, Lang lang, boolean isPodcast) throws InvalidParameterException, ParseException, InvalidSettingException, InvalidSchedulingsException, MissingSettingException {
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(VActionFreeHandler.NB_NEWS, nbNews);

		theSettings.put(VActionFullHandler.LABEL, alertName);
		theSettings.put(VActionFullHandler.URL, url);
		theSettings.put(VActionFullHandler.LANG, lang.getIsoCode());

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
			final ApplicationData appli = isPodcast ? ApplicationData.getData(Application.NativeApplication.PODCAST_FULL.getApplication()) : ApplicationData.getData(Application.NativeApplication.RSS_FULL.getApplication());
			return SubscriptionManager.createSubscription(appli, object, theSettings, theScSettings, null);
		}

		SubscriptionManager.updateSubscription(subscription, theSettings, theScSettings, null);
		return subscription;
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return subscription.getSettings().get(VActionFullHandler.LABEL).toString();
	}
}
