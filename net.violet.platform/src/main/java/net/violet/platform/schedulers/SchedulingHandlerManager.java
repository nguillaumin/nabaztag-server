package net.violet.platform.schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.violet.common.StringShop;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;

public class SchedulingHandlerManager {

	private static final Map<SCHEDULING_TYPE, SchedulingHandler> HANDLERS = new HashMap<SCHEDULING_TYPE, SchedulingHandler>();

	static {
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.Daily, new DailyHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.DailyWithMedia, new DailyWithMediaHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.DailyWithDuration, new DailyWithDurationHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.Weekly, new WeeklyHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.Frequency, new FrequencyHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.RandomWithFrequency, new FrequencyHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.VoiceTrigger, new KeywordHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.InteractionTrigger, new InteractionTriggerHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.NewContentWithFrequency, new FrequencyHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.NewContentWithKeywordAndMedia, new NewContentWithKeywordAndMediaHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.Ambiant, new AmbiantHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.AmbiantWithKeyword, new AmbiantWithKeywordHandler());
		SchedulingHandlerManager.HANDLERS.put(SCHEDULING_TYPE.NewContent, new EmptyHandler());
	}

	private static SchedulingHandler getHandler(SCHEDULING_TYPE type) {
		return SchedulingHandlerManager.HANDLERS.get(type);
	}

	private static SchedulingHandler getHandler(String typeKey) {
		return SchedulingHandlerManager.getHandler(SCHEDULING_TYPE.findByLabel(typeKey));
	}

	/**
	 * Checks if the given schedulings are correct. It means that for each of them we check its settings.
	 * @param object
	 * @param schedulings
	 * @param callerKey
	 * @throws InvalidSchedulingsException 
	 * @throws MissingSettingException 
	 * @throws InvalidSettingException 
	 * @throws InvalidParameterException
	 */
	public static void checkSchedulings(VObjectData object, List<Map<String, Object>> schedulings, String callerKey) throws InvalidSchedulingsException, InvalidSettingException, MissingSettingException {

		if (schedulings == null) {
			throw new InvalidSchedulingsException();
		}

		for (final Map<String, Object> aScheduling : schedulings) {
			final Map<String, Object> schedulingWithoutType = new HashMap<String, Object>(aScheduling);
			final SchedulingHandler handler = SchedulingHandlerManager.getHandler(String.valueOf(schedulingWithoutType.remove(SchedulingType.TYPE_KEY)));
			if (handler == null) {
				throw new InvalidSchedulingsException();
			}

			handler.checkSettings(object, schedulingWithoutType, callerKey);
		}

	}

	/**
	 * Sets the given schedulings for the provided subscription. No checks are performed on the given schedulings to ensure their validity.
	 * @param subscription
	 * @param schedulings
	 * @param callerKey
	 */
	public static void setSchedulings(SubscriptionData subscription, List<Map<String, Object>> schedulings, String callerKey) {

		final List<SubscriptionSchedulingData> theExistingSchedulings = SubscriptionSchedulingData.findAllBySubscription(subscription);

		for (final Map<String, Object> aNewScheduling : schedulings) {
			final Map<String, Object> schedWithoutType = new HashMap<String, Object>(aNewScheduling);
			final SCHEDULING_TYPE type = SCHEDULING_TYPE.findByLabel(schedWithoutType.remove(SchedulingType.TYPE_KEY).toString());

			final SchedulingHandler theHandler = SchedulingHandlerManager.getHandler(type);

			final Map<String, String> theNewSettings = theHandler.generateSettings(subscription.getObject(), schedWithoutType, callerKey);

			final SubscriptionSchedulingData theScheduling;
			if (theExistingSchedulings.isEmpty()) {
				theScheduling = SubscriptionSchedulingData.create(subscription, type);
			} else {
				theScheduling = theExistingSchedulings.remove(0);
				SchedulingHandlerManager.getHandler(theScheduling.getType()).deleteElements(theScheduling);
				theScheduling.setType(type);
			}
			SchedulingHandlerManager.setSchedulingSettings(theScheduling, theNewSettings);
			theHandler.executeWhenDone(theScheduling);
		}

		SchedulingHandlerManager.deleteSchedulings(theExistingSchedulings);
	}

	private static void deleteSchedulings(List<SubscriptionSchedulingData> schedulings) {
		for (final SubscriptionSchedulingData aScheduling : schedulings) {
			SchedulingHandlerManager.getHandler(aScheduling.getType()).deleteElements(aScheduling);
			aScheduling.delete();
		}
	}

	private static void setSchedulingSettings(SubscriptionSchedulingData scheduling, Map<String, String> inSettings) {
		final List<SubscriptionSchedulingSettingsData> currentSettings = SubscriptionSchedulingSettingsData.findAllBySubscriptionScheduling(scheduling);

		for (final Entry<String, String> anEntry : inSettings.entrySet()) {
			if (currentSettings.isEmpty()) {
				scheduling.createSetting(anEntry.getKey(), anEntry.getValue());
			} else {
				currentSettings.remove(0).update(anEntry.getKey(), anEntry.getValue());
			}
		}

		for (final SubscriptionSchedulingSettingsData aRemainingSetting : currentSettings) {
			aRemainingSetting.delete();
		}

	}

	public static void deleteSchedulings(SubscriptionData subscription) {
		SchedulingHandlerManager.deleteSchedulings(SubscriptionSchedulingData.findAllBySubscription(subscription));
	}

	public static List<SchedulingInformationMap> getUISettings(SubscriptionData inSubscriptionData, APICaller inCaller) {
		final List<SchedulingInformationMap> theSchedulings = new ArrayList<SchedulingInformationMap>();
		for (final SubscriptionSchedulingData aScheduling : SubscriptionSchedulingData.findAllBySubscription(inSubscriptionData)) {
			// specific case for the email alert
			if ((aScheduling.getType() == SCHEDULING_TYPE.Ambiant) && inSubscriptionData.getApplication().equals(ApplicationData.getData(Application.NativeApplication.MAIL.getApplication()))) {
				final SchedulingInformationMap sched = new SchedulingInformationMap(aScheduling);
				sched.put("type", SCHEDULING_TYPE.AmbiantWithKeyword.getLabel());
				sched.put("keyword", StringShop.EMPTY_STRING);
				theSchedulings.add(sched);
			} else {
				final SchedulingHandler theHandler = SchedulingHandlerManager.getHandler(aScheduling.getType());
				theSchedulings.add(theHandler.getSchedulingInformation(aScheduling, inCaller));
			}
		}

		return theSchedulings;
	}

}
