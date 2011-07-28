package net.violet.platform.applications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

public class WeatherHandler extends DefaultHandler {

	public static final String SOURCE = "source";
	public static final String UNIT = "unit";
	public static final String LANGUAGE = "language";

	private static final Pattern SOURCE_PATTERN = Pattern.compile("^Nmeteo\\.[^.]+\\.[^.]+\\.weather$");

	private static final ApplicationData WEATHER_APPLICATION = ApplicationData.getData(Application.NativeApplication.WEATHER.getApplication());

	protected WeatherHandler() {
		super(WeatherHandler.WEATHER_APPLICATION);
	}

	@Override
	public void checkSettings(VObjectData object, Map<String, Object> settings) throws InvalidSettingException, MissingSettingException {

		final Object sourceSetting = settings.get(WeatherHandler.SOURCE);
		final Object unitSetting = settings.get(WeatherHandler.UNIT);
		final Object language = settings.get(WeatherHandler.LANGUAGE);

		if ((sourceSetting == null) || (unitSetting == null)) {
			throw new MissingSettingException(sourceSetting == null ? WeatherHandler.SOURCE : WeatherHandler.UNIT);
		}

		if (language == null) {
			throw new MissingSettingException(WeatherHandler.LANGUAGE);
		}

		if (Factories.LANG.findByIsoCode(language.toString()) == null) {
			throw new InvalidSettingException(AirHandler.LANGUAGE_SETTING, language.toString());
		}

		if (!WeatherHandler.SOURCE_PATTERN.matcher(sourceSetting.toString()).matches() || (Factories.SOURCE.findByPath(sourceSetting.toString()) == null)) {
			throw new InvalidSettingException(WeatherHandler.SOURCE, sourceSetting.toString());
		}

		if (!unitSetting.toString().equals("1") && !unitSetting.toString().equals("2")) {
			throw new InvalidSettingException(WeatherHandler.UNIT, unitSetting.toString());
		}

	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData inSubscription, VObjectData inObject, boolean isAmbiant, String isoCode, String inSrc, int inUnit, List<String> inTimeList) throws InvalidParameterException, InvalidSettingException, InvalidSchedulingsException, MissingSettingException {

		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(WeatherHandler.LANGUAGE, isoCode);
		theSettings.put(WeatherHandler.SOURCE, inSrc);
		theSettings.put(WeatherHandler.UNIT, Integer.toString(inUnit));

		final List<Map<String, Object>> theSchedulings = new ArrayList<Map<String, Object>>();

		if (isAmbiant) {
			final Map<String, Object> scheduling = new HashMap<String, Object>();
			scheduling.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.Ambiant.getLabel());
			theSchedulings.add(scheduling);
		}

		final Map<String, Object> scheduling = new HashMap<String, Object>();
		scheduling.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.VoiceTrigger.getLabel());
		scheduling.put(KeywordHandler.KEYWORD, AbstractRecoService.WEATHER);
		theSchedulings.add(scheduling);

		theSchedulings.addAll(ApplicationHandlerHelper.generateDailySchedulings(inTimeList, inObject.getJavaTimeZone(), true));

		if (inSubscription == null) {
			return SubscriptionManager.createSubscription(WeatherHandler.WEATHER_APPLICATION, inObject, theSettings, theSchedulings, null);
		}

		SubscriptionManager.updateSubscription(inSubscription, theSettings, theSchedulings, null);
		return inSubscription;
	}

}
