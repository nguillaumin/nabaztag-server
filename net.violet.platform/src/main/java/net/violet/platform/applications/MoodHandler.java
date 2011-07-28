package net.violet.platform.applications;

import java.util.ArrayList;
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

public class MoodHandler implements ApplicationHandler {

	private static final ApplicationData MOOD_APPLICATION = ApplicationData.getData(Application.NativeApplication.MOOD.getApplication());

	public static final String LANGUAGES = "languages";

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		return SubscriptionData.create(MoodHandler.MOOD_APPLICATION, object, getSettings(settings));
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		subscription.setSettings(getSettings(settings));
	}

	private Map<String, Object> getSettings(Map<String, Object> settings) {
		final Map<String, Object> theSettings = new HashMap<String, Object>();
		final Object languages = settings.get(MoodHandler.LANGUAGES);

		theSettings.put(MoodHandler.LANGUAGES, (languages instanceof List) ? (List<String>) languages : Collections.singletonList(languages.toString()));
		return theSettings;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) throws InvalidSettingException, MissingSettingException {
		final Object languages = settings.get(MoodHandler.LANGUAGES);

		if (languages == null) {
			throw new MissingSettingException(MoodHandler.LANGUAGES);
		}

		final List<String> isoCodes = (languages instanceof List) ? new ArrayList<String>((List<String>) languages) : Collections.singletonList(languages.toString());
		for (final String aCode : isoCodes) {
			if (Factories.LANG.findByIsoCode(aCode) == null) {
				throw new InvalidSettingException(MoodHandler.LANGUAGES, languages.toString());
			}
		}

	}

	public void delete(SubscriptionData subscription) {
		subscription.delete();
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		return subscription.getSettings();
	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData inSubscription, VObjectData inObject, String[] langsList, FrequencyHandler.Frequency inFrequency) throws InvalidSettingException, InvalidSchedulingsException, MissingSettingException {
		final Map<String, Object> theSettings = new HashMap<String, Object>();

		final List<String> langsCodeList = new LinkedList<String>();
		for (final String langId : langsList) {
			langsCodeList.add(Factories.LANG.find(Integer.parseInt(langId)).getIsoCode());
		}

		theSettings.put(MoodHandler.LANGUAGES, langsCodeList);

		final Map<String, Object> theSched = new HashMap<String, Object>();
		theSched.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.RandomWithFrequency.getLabel());
		theSched.put("frequency", inFrequency.getLabel());

		if (inSubscription == null) {
			return SubscriptionManager.createSubscription(MoodHandler.MOOD_APPLICATION, inObject, theSettings, Collections.singletonList(theSched), null);
		}

		SubscriptionManager.updateSubscription(inSubscription, theSettings, Collections.singletonList(theSched), null);
		return inSubscription;
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return null;
	}

}
