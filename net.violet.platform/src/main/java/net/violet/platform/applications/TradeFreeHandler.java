package net.violet.platform.applications;

import java.util.ArrayList;
import java.util.Collections;
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

public class TradeFreeHandler extends DefaultHandler {

	public static final String SOURCE = "source";

	static final Pattern SOURCE_PATTERN = Pattern.compile("^money\\..+$");

	private static final ApplicationData TRADE_FREE_APPLICATION = ApplicationData.getData(Application.NativeApplication.BOURSE_FREE.getApplication());

	protected TradeFreeHandler() {
		super(TradeFreeHandler.TRADE_FREE_APPLICATION);
	}

	@Override
	public void checkSettings(VObjectData object, Map<String, Object> settings) throws MissingSettingException, InvalidSettingException {
		final Object sourceSetting = settings.get(TradeFreeHandler.SOURCE);
		if (sourceSetting == null) {
			throw new MissingSettingException(TradeFreeHandler.SOURCE);
		}

		if (!TradeFreeHandler.SOURCE_PATTERN.matcher(sourceSetting.toString()).matches() || (Factories.SOURCE.findByPath(sourceSetting.toString()) == null)) {
			throw new InvalidSettingException(TradeFreeHandler.SOURCE, sourceSetting.toString());
		}

	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData inSubscription, VObjectData inObject, boolean isAmbiant, String inSrc, List<String> inTimeList, boolean isAllWeek) throws InvalidParameterException, InvalidSettingException, InvalidSchedulingsException, MissingSettingException {

		final Map<String, Object> theSettings = Collections.<String, Object> singletonMap(TradeFreeHandler.SOURCE, inSrc);

		final List<Map<String, Object>> theSchedulings = new ArrayList<Map<String, Object>>();

		if (isAmbiant) {
			final Map<String, Object> scheduling = new HashMap<String, Object>();
			scheduling.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.Ambiant.getLabel());
			theSchedulings.add(scheduling);
		}

		final Map<String, Object> scheduling = new HashMap<String, Object>();
		scheduling.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.VoiceTrigger.getLabel());
		scheduling.put(KeywordHandler.KEYWORD, AbstractRecoService.MARKET);
		theSchedulings.add(scheduling);

		theSchedulings.addAll(ApplicationHandlerHelper.generateDailySchedulings(inTimeList, inObject.getReference().getTimeZone().getJavaTimeZone(), isAllWeek));

		if (inSubscription == null) {
			return SubscriptionManager.createSubscription(TradeFreeHandler.TRADE_FREE_APPLICATION, inObject, theSettings, theSchedulings, null);
		}

		SubscriptionManager.updateSubscription(inSubscription, theSettings, theSchedulings, null);
		return inSubscription;
	}
}
