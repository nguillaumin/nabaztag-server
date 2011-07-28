package net.violet.platform.applications;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.TaichiDataFactory;
import net.violet.platform.dataobjects.VObjectData;

public class TaichiHandler extends DefaultHandler {

	private static final ApplicationData TAICHI_APPLICATION = ApplicationData.getData(Application.NativeApplication.TAICHI.getApplication());

	public static final String SOURCE = "source";

	private static final Pattern SOURCE_PATTERN = Pattern.compile("^taichi\\.(slow|fast|normal)$");

	protected TaichiHandler() {
		super(TaichiHandler.TAICHI_APPLICATION);
	}

	@Override
	public void checkSettings(VObjectData object, Map<String, Object> settings) throws MissingSettingException, InvalidSettingException {
		if (!settings.containsKey(TaichiHandler.SOURCE)) {
			throw new MissingSettingException(TaichiHandler.SOURCE);
		}

		final String theSourcePath = settings.get(TaichiHandler.SOURCE).toString();
		if (!TaichiHandler.SOURCE_PATTERN.matcher(theSourcePath).matches()) {
			throw new InvalidSettingException(TaichiHandler.SOURCE, theSourcePath);
		}

	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData inSubscription, VObjectData inObject, TaichiDataFactory.TAICHI_FREQUENCY frequency) throws InvalidSettingException, InvalidSchedulingsException, MissingSettingException {

		final Map<String, Object> theSettings = new HashMap<String, Object>();
		theSettings.put(TaichiHandler.SOURCE, frequency.getLabel());

		final Map<String, Object> theSched = new HashMap<String, Object>();
		theSched.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.Ambiant.getLabel());

		if (inSubscription == null) {
			return SubscriptionManager.createSubscription(TaichiHandler.TAICHI_APPLICATION, inObject, theSettings, Collections.singletonList(theSched), null);
		}

		SubscriptionManager.updateSubscription(inSubscription, theSettings, Collections.singletonList(theSched), null);
		return inSubscription;
	}

}
