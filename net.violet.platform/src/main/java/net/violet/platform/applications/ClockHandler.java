package net.violet.platform.applications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;

public class ClockHandler implements ApplicationHandler, SettingsEditor {

	private static final ApplicationData CLOCK_APPLICATION = ApplicationData.getData(Application.NativeApplication.CLOCK.getApplication());

	public static final String LANGUAGES = "languages";
	public static final String TYPES = "types";

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		return SubscriptionData.create(ClockHandler.CLOCK_APPLICATION, object, getSettings(settings));
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		subscription.setSettings(getSettings(settings));
	}

	private Map<String, Object> getSettings(Map<String, Object> settings) {
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		final Object languages = settings.get(ClockHandler.LANGUAGES);
		final Object types = settings.get(ClockHandler.TYPES);

		theSettings.put(ClockHandler.LANGUAGES, (languages instanceof List) ? (List<String>) languages : Collections.singletonList(languages.toString()));
		theSettings.put(ClockHandler.TYPES, (types instanceof List) ? (List<String>) types : Collections.singletonList(types.toString()));
		return theSettings;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) throws MissingSettingException, InvalidSettingException {
		final Object languages = settings.get(ClockHandler.LANGUAGES);
		final Object types = settings.get(ClockHandler.TYPES);

		if ((languages == null) || (types == null)) {
			throw new MissingSettingException(languages == null ? ClockHandler.LANGUAGES : ClockHandler.TYPES);
		}

		final List<String> isoCodes = (languages instanceof List) ? new ArrayList<String>((List<String>) languages) : Collections.singletonList(languages.toString());
		for (final String aCode : isoCodes) {
			if (Factories.LANG.findByIsoCode(aCode) == null) {
				throw new InvalidSettingException(ClockHandler.LANGUAGES, languages.toString());
			}
		}

		final List<String> theTypes = (types instanceof List) ? new ArrayList<String>((List<String>) types) : Collections.singletonList(types.toString());
		for (final String aType : theTypes) {
			try {
				final int typeId = Integer.parseInt(aType);
				if ((typeId < 1) || (typeId > 3)) {
					throw new InvalidSettingException(ClockHandler.TYPES, types.toString());
				}
			} catch (final NumberFormatException e) {
				throw new InvalidSettingException(ClockHandler.TYPES, types.toString());
			}
		}

	}

	public void delete(SubscriptionData subscription) {
		subscription.delete();
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		return subscription.getSettings();
	}

	/**
	 * The ClockHandler is allowed to edit the given schedulings. It clears it and fills it up with a proper hourly-frequency scheduling
	 * with a correct time reference.
	 */
	public void editSettings(VObjectData object, Map<String, Object> settings, List<Map<String, Object>> schedulings, String callerKey, boolean updateSubscription) {
		schedulings.clear();
		final Map<String, Object> theSched = new HashMap<String, Object>();
		theSched.put("type", SchedulingType.SCHEDULING_TYPE.Frequency.getLabel());
		theSched.put("frequency", FrequencyHandler.Frequency.HOURLY.getLabel());

		final CCalendar theCalendar = new CCalendar(object.getReference().getTimeZone().getJavaTimeZone());
		theCalendar.setMinute(0);
		theCalendar.setSecond(0);
		theSched.put("last_time", theCalendar.getTimestamp());

		schedulings.add(theSched);
	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData inSubscription, VObjectData inObject, String[] langsList, String[] typesList) throws InvalidSettingException, InvalidSchedulingsException, MissingSettingException {
		final Map<String, Object> theSettings = new HashMap<String, Object>();

		final List<String> langsCodeList = new LinkedList<String>();
		for (final String langId : langsList) {
			langsCodeList.add(Factories.LANG.find(Integer.parseInt(langId)).getIsoCode());
		}

		theSettings.put(ClockHandler.LANGUAGES, langsCodeList);
		theSettings.put(ClockHandler.TYPES, Arrays.asList(typesList));

		if (inSubscription == null) {
			return SubscriptionManager.createSubscription(ClockHandler.CLOCK_APPLICATION, inObject, theSettings, Collections.<Map<String, Object>> emptyList(), null);
		}

		SubscriptionManager.updateSubscription(inSubscription, theSettings, Collections.<Map<String, Object>> emptyList(), null);
		return inSubscription;
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return null;
	}
}
