package net.violet.platform.applications;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Application.NativeApplication;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.util.CCalendar;

public class BilanHandler implements ApplicationHandler {

	private static final ApplicationData BILAN_APPLICATION = ApplicationData.getData(NativeApplication.BILAN.getApplication());

	public static final String NBR = "bilan_nbr";

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		final Map<String, Object> settingsMap = Collections.<String, Object> singletonMap(BilanHandler.NBR, "0");
		return SubscriptionData.create(BilanHandler.BILAN_APPLICATION, object, settingsMap);
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) {
		// nothing to do
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		//nothing to do here : the bilan_nbr setting is hidden
	}

	public void delete(SubscriptionData subscription) {
		subscription.delete();
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		return Collections.emptyMap();
	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData inSubscription, VObjectData inObject, String inTime, DailyHandler.Weekday inDays) throws InvalidParameterException, InvalidSettingException, InvalidSchedulingsException, MissingSettingException {

		if ((inTime != null) && (inDays != null)) {
			try {
				final CCalendar theTime = CCalendar.getSQLCCalendar(inTime, inObject.getReference().getTimeZone().getJavaTimeZone());
				final Map<String, Object> theSettings = Collections.emptyMap();
				final Map<String, Object> scheduling = new HashMap<String, Object>();
				scheduling.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.Weekly.getLabel());
				scheduling.put("weekday", inDays.getValue());
				scheduling.put("time_h", theTime.getHour());
				scheduling.put("time_m", theTime.getMinute());

				if (inSubscription == null) {
					return SubscriptionManager.createSubscription(BilanHandler.BILAN_APPLICATION, inObject, theSettings, Collections.singletonList(scheduling), null);
				}

				SubscriptionManager.updateSubscription(inSubscription, theSettings, Collections.singletonList(scheduling), null);
				return inSubscription;
			} catch (final ParseException e) {
				throw new InvalidParameterException("time", inTime);
			}
		}

		throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "Time or day null");

	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return null;
	}

}
