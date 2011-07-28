package net.violet.platform.applications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.SchedulingHandlerManager;

import org.apache.log4j.Logger;

/**
 * This manager is the only valid access point to reliably create/update/delete subscriptions.
 */
public class SubscriptionManager {

	private static final Logger LOGGER = Logger.getLogger(SubscriptionManager.class);

	/**
	 * Creates a new subscription based on the given application, object, settings and schedulings. The callerKey can be null.
	 * This method first checks if the provided settings and schedulings are correct, if they are not the appropriate exception is thrown.
	 * 
	 * If all given parameters are valid, the subscription is created and returned.
	 * @param application
	 * @param object
	 * @param settings
	 * @param schedulings
	 * @param callerKey
	 * @return
	 * @throws InvalidParameterException
	 * @throws InternalErrorException
	 * @throws InvalidParameterException 
	 * @throws MissingSettingException 
	 */
	public static SubscriptionData createSubscription(ApplicationData application, VObjectData object, Map<String, Object> settings, List<Map<String, Object>> schedulings, String callerKey) throws InvalidSettingException, InvalidSchedulingsException, MissingSettingException {

		SchedulingHandlerManager.checkSchedulings(object, schedulings, callerKey);

		// some application Handlers can edit the given schedulings ! with great powers comes great responsibility
		final List<Map<String, Object>> theActualSchedulings;
		final Map<String, Object> theActualSettings;
		final ApplicationHandler handler = ApplicationHandlerManager.getHandler(application);

		if (handler instanceof SettingsEditor) {
			theActualSchedulings = new ArrayList<Map<String, Object>>(schedulings);
			theActualSettings = new HashMap<String, Object>(settings);
			((SettingsEditor) handler).editSettings(object, theActualSettings, theActualSchedulings, callerKey, false);
		} else {
			theActualSettings = settings;
			theActualSchedulings = schedulings;
		}

		final SubscriptionData theSub = ApplicationHandlerManager.createSubscription(application, object, theActualSettings);
		SchedulingHandlerManager.setSchedulings(theSub, theActualSchedulings, callerKey);

		if (handler instanceof Notifier) {
			try {
				((Notifier) handler).add(object);
			} catch (final NoSuchSubscriptionException e) {
				// normally, subscription is created
				SubscriptionManager.LOGGER.fatal(e, e);
			}
		}
		return theSub;
	}

	public static void updateSubscription(SubscriptionData subscription, Map<String, Object> settings, List<Map<String, Object>> schedulings, APICaller caller) throws InvalidSettingException, MissingSettingException, InvalidSchedulingsException {
		final ApplicationHandler handler = ApplicationHandlerManager.getHandler(subscription.getApplication());
		final String callerKey = caller != null ? caller.getAPIKey() : null;

		final Map<String, Object> theSettings = (settings != null) ? settings : handler.getUISettings(subscription, caller);
		final List<Map<String, Object>> theSchedulings = (List<Map<String, Object>>) ((schedulings != null) ? schedulings : SchedulingHandlerManager.getUISettings(subscription, caller));

		SchedulingHandlerManager.checkSchedulings(subscription.getObject(), theSchedulings, callerKey);

		// some application Handlers can edit the given schedulings ! with great powers comes great responsibility
		final List<Map<String, Object>> theActualSchedulings;
		final Map<String, Object> theActualSettings;

		if (handler instanceof SettingsEditor) {
			theActualSchedulings = new ArrayList<Map<String, Object>>(theSchedulings);
			theActualSettings = new HashMap<String, Object>(theSettings);
			((SettingsEditor) handler).editSettings(subscription.getObject(), theActualSettings, theActualSchedulings, callerKey, true);
		} else {
			theActualSettings = theSettings;
			theActualSchedulings = theSchedulings;
		}

		ApplicationHandlerManager.updateSubscription(subscription, theActualSettings);
		SchedulingHandlerManager.setSchedulings(subscription, theActualSchedulings, callerKey);

		if (handler instanceof Notifier) {
			try {
				((Notifier) handler).add(subscription.getObject());
			} catch (final NoSuchSubscriptionException e) {
				// normally, subscription is created
				SubscriptionManager.LOGGER.fatal(e, e);
			}
		}
	}

	public static void deleteSubscription(SubscriptionData subscription) {
		final ApplicationHandler handler = ApplicationHandlerManager.getHandler(subscription.getApplication());
		if (handler instanceof Notifier) {
			try {
				((Notifier) handler).remove(subscription.getObject(), false);
			} catch (final NoSuchSubscriptionException e) {
				// normally, subscription is created
				SubscriptionManager.LOGGER.fatal(e, e);
			}
		}
		SchedulingHandlerManager.deleteSchedulings(subscription);
		ApplicationHandlerManager.deleteSubscription(subscription);
	}

}
