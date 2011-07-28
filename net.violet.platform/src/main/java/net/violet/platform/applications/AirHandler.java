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
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Application.NativeApplication;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.KeywordHandler;
import net.violet.platform.util.AbstractRecoService;

public class AirHandler extends DefaultHandler {

	private static final Pattern AIR_SOURCE = Pattern.compile("^air\\..+$");

	private static final ApplicationData AIR_APPLICATION = ApplicationData.getData(NativeApplication.AIR.getApplication());

	public static final String SOURCE_SETTING = "source";
	public static final String LANGUAGE_SETTING = "language";

	protected AirHandler() {
		super(AirHandler.AIR_APPLICATION);
	}

	@Override
	public void checkSettings(VObjectData object, Map<String, Object> settings) throws InvalidSettingException, MissingSettingException {
		final Object source = settings.get(AirHandler.SOURCE_SETTING);
		final Object language = settings.get(AirHandler.LANGUAGE_SETTING);

		if ((source == null) || (language == null)) {
			throw new MissingSettingException(source == null ? AirHandler.SOURCE_SETTING : AirHandler.LANGUAGE_SETTING);
		}

		if (Factories.LANG.findByIsoCode(language.toString()) == null) {
			throw new InvalidSettingException(AirHandler.LANGUAGE_SETTING, language.toString());
		}

		if (!AirHandler.AIR_SOURCE.matcher(source.toString()).matches() || (Factories.SOURCE.findByPath(source.toString()) == null)) {
			throw new InvalidSettingException(AirHandler.SOURCE_SETTING, source.toString());
		}

	}

	// used by My
	public static SubscriptionData createOrUpdateSubscription(SubscriptionData inSubscription, VObjectData inObject, boolean isAmbiant, String isoCode, String inSrc, List<String> inTimeList) throws InvalidParameterException, InvalidSettingException, InvalidSchedulingsException, MissingSettingException {

		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(AirHandler.LANGUAGE_SETTING, isoCode);
		theSettings.put(AirHandler.SOURCE_SETTING, inSrc);

		final List<Map<String, Object>> theSchedulings = new ArrayList<Map<String, Object>>();

		if (isAmbiant) {
			final Map<String, Object> scheduling = new HashMap<String, Object>();
			scheduling.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.Ambiant.getLabel());
			theSchedulings.add(scheduling);
		}

		final Map<String, Object> scheduling = new HashMap<String, Object>();
		scheduling.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.VoiceTrigger.getLabel());
		scheduling.put(KeywordHandler.KEYWORD, AbstractRecoService.SMOG);
		theSchedulings.add(scheduling);

		theSchedulings.addAll(ApplicationHandlerHelper.generateDailySchedulings(inTimeList, inObject.getReference().getTimeZone().getJavaTimeZone(), true));

		if (inSubscription == null) {
			return SubscriptionManager.createSubscription(AirHandler.AIR_APPLICATION, inObject, theSettings, theSchedulings, null);
		}

		SubscriptionManager.updateSubscription(inSubscription, theSettings, theSchedulings, null);
		return inSubscription;
	}

}
