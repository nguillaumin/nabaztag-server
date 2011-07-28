package net.violet.platform.applications;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.NativeApplication;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

public class ApplicationHandlerManager {

	private static final Pattern JS_APPLICATION = Pattern.compile("^net\\.violet\\.js\\..+$");

	private static final Map<NativeApplication, ApplicationHandler> HANDLERS;

	static {
		final Map<NativeApplication, ApplicationHandler> theMap = new HashMap<NativeApplication, ApplicationHandler>();
		theMap.put(Application.NativeApplication.AIR, new AirHandler());
		theMap.put(Application.NativeApplication.BILAN, new BilanHandler());
		theMap.put(Application.NativeApplication.BOURSE_FREE, new TradeFreeHandler());
		theMap.put(Application.NativeApplication.BOURSE_FULL, new TradeFullHandler());
		theMap.put(Application.NativeApplication.CLOCK, new ClockHandler());
		theMap.put(Application.NativeApplication.GMAIL, new GmailTwitterHandler(ApplicationData.getData(Application.NativeApplication.GMAIL.getApplication())));
		theMap.put(Application.NativeApplication.TWITTER, new GmailTwitterHandler(ApplicationData.getData(Application.NativeApplication.TWITTER.getApplication())));
		theMap.put(Application.NativeApplication.FACEBOOK, new FacebookHandler(ApplicationData.getData(Application.NativeApplication.FACEBOOK.getApplication())));
		theMap.put(Application.NativeApplication.MAIL, new MailAlertHandler());
		theMap.put(Application.NativeApplication.MOOD, new MoodHandler());
		theMap.put(Application.NativeApplication.PODCAST_FULL, new VActionFullHandler(ApplicationData.getData(Application.NativeApplication.PODCAST_FULL.getApplication())));
		theMap.put(Application.NativeApplication.RSS_FULL, new VActionFullHandler(ApplicationData.getData(Application.NativeApplication.RSS_FULL.getApplication())));
		theMap.put(Application.NativeApplication.TAICHI, new TaichiHandler());
		theMap.put(Application.NativeApplication.TRAFIC, new TraficHandler());
		theMap.put(Application.NativeApplication.WEATHER, new WeatherHandler());
		theMap.put(Application.NativeApplication.EARS_COMMUNION, new EarsCommunionHandler());

		HANDLERS = Collections.unmodifiableMap(theMap);
	}

	/**
	 * Returns the handler in charge of the provided application. A DefaultHandler exists, so the returned handler is never null.
	 * @param application
	 * @return
	 */
	public static ApplicationHandler getHandler(ApplicationData application) {
		final Application.NativeApplication nativeAppli = Application.NativeApplication.findByApplication(application.getReference());
		final ApplicationHandler theHandler;

		if ((nativeAppli != null) && ApplicationHandlerManager.HANDLERS.containsKey(nativeAppli)) {
			theHandler = ApplicationHandlerManager.HANDLERS.get(nativeAppli);
		} else if (nativeAppli == Application.NativeApplication.WEBRADIO) {
			theHandler = new WebradioHandler(application);
		} else if ((nativeAppli == Application.NativeApplication.RSS_FREE) || (nativeAppli == Application.NativeApplication.PODCAST_FREE)) {
			theHandler = new VActionFreeHandler(application);
		} else if (ApplicationHandlerManager.JS_APPLICATION.matcher(application.getName()).matches()) {
			theHandler = new VoiceHandler(application);
		} else {
			theHandler = new DefaultHandler(application);
		}

		return theHandler;
	}

	/**
	 * Creates a subscription. This method first checks if the given settings are valid (if they are not the InvalidSettingsException is 
	 * thrown).
	 * @param application
	 * @param object
	 * @param settings
	 * @return
	 * @throws InvalidSettingException 
	 * @throws MissingSettingException 
	 */
	static SubscriptionData createSubscription(ApplicationData application, VObjectData object, Map<String, Object> settings) throws InvalidSettingException, MissingSettingException {
		final ApplicationHandler theHandler = ApplicationHandlerManager.getHandler(application);
		theHandler.checkSettings(object, settings);
		return theHandler.create(object, settings);
	}

	/**
	 * Updates the subscriptions. First checks if the given settings are valid.
	 * @param subscription
	 * @param settings
	 * @throws InvalidSettingException
	 * @throws MissingSettingException
	 */
	static void updateSubscription(SubscriptionData subscription, Map<String, Object> settings) throws InvalidSettingException, MissingSettingException {
		final ApplicationHandler theHandler = ApplicationHandlerManager.getHandler(subscription.getApplication());
		theHandler.checkSettings(subscription.getObject(), settings);
		theHandler.update(subscription, settings);
	}

	/**
	 * Deletes the given subscription, after calling this method the subscription is NO longer stored in the database.
	 * @param subscription
	 */
	static void deleteSubscription(SubscriptionData subscription) {
		ApplicationHandlerManager.getHandler(subscription.getApplication()).delete(subscription);
	}

	public static Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inCaller) {
		final ApplicationHandler theHandler = ApplicationHandlerManager.getHandler(subscription.getApplication());
		return theHandler.getUISettings(subscription, inCaller);
	}

	public static String getSubscriptionInformation(SubscriptionData subscription) {
		final ApplicationHandler theHandler = ApplicationHandlerManager.getHandler(subscription.getApplication());
		return theHandler.getSubscriptionInformation(subscription);
	}

}
