package net.violet.platform.applications;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.KeywordHandler;
import net.violet.platform.util.AbstractRecoService;

public class TraficHandler implements ApplicationHandler {

	public static final String START = "start";
	public static final String END = "end";

	public static final String SOURCE = "source";

	private static final ApplicationData TRAFIC_APPLICATION = ApplicationData.getData(Application.NativeApplication.TRAFIC.getApplication());

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		return SubscriptionData.create(TraficHandler.TRAFIC_APPLICATION, object, getSettings(settings));
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		subscription.setSettings(getSettings(settings));
	}

	private Map<String, Object> getSettings(Map<String, Object> settings) {
		final Map<String, Object> theSettings = new HashMap<String, Object>(settings);
		theSettings.put(TraficHandler.SOURCE, "trafic" + "." + theSettings.get(TraficHandler.START) + "." + theSettings.get(TraficHandler.END));
		return theSettings;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) throws InvalidSettingException, MissingSettingException {

		final Object startSetting = settings.get(TraficHandler.START);
		final Object endSetting = settings.get(TraficHandler.END);

		if ((startSetting == null) || (endSetting == null)) {
			throw new MissingSettingException(startSetting == null ? TraficHandler.START : TraficHandler.END);
		}

		if (startSetting.equals(endSetting)) {
			throw new InvalidSettingException(TraficHandler.START + " and " + TraficHandler.END, startSetting + " = " + endSetting);
		}

		final String sourcePath = "trafic." + startSetting + "." + endSetting;
		if (Factories.SOURCE.findByPath(sourcePath) == null) {
			throw new InvalidSettingException(TraficHandler.START + "/" + TraficHandler.END, startSetting + "/" + endSetting);
		}

	}

	public void delete(SubscriptionData subscription) {
		subscription.delete();
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		final Map<String, Object> settings = new HashMap<String, Object>(subscription.getSettings());
		settings.remove(TraficHandler.SOURCE);
		return settings;
	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData inSubscription, VObjectData inObject, String start, String end, boolean ambiant, List<String> inTimesList, boolean allWeek) throws InvalidParameterException, InvalidSettingException, InvalidSchedulingsException, MissingSettingException {

		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(TraficHandler.START, start);
		theSettings.put(TraficHandler.END, end);

		final List<Map<String, Object>> theSchedulings = new LinkedList<Map<String, Object>>();

		if (ambiant) {
			final Map<String, Object> scheduling = new HashMap<String, Object>();
			scheduling.put("type", SchedulingType.SCHEDULING_TYPE.Ambiant.getLabel());
			theSchedulings.add(scheduling);
		}

		final Map<String, Object> scheduling = new HashMap<String, Object>();
		scheduling.put("type", SchedulingType.SCHEDULING_TYPE.VoiceTrigger.getLabel());
		scheduling.put(KeywordHandler.KEYWORD, AbstractRecoService.TRAFIC);
		theSchedulings.add(scheduling);

		theSchedulings.addAll(ApplicationHandlerHelper.generateDailySchedulings(inTimesList, inObject.getReference().getTimeZone().getJavaTimeZone(), allWeek));

		if (inSubscription == null) {
			return SubscriptionManager.createSubscription(TraficHandler.TRAFIC_APPLICATION, inObject, theSettings, theSchedulings, null);
		}

		SubscriptionManager.updateSubscription(inSubscription, theSettings, theSchedulings, null);
		return inSubscription;
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return null;
	}
}
