package net.violet.platform.schedulers;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;

/**
 * Deprecated class, should use the interactionTrigger with Voice event
 *
 */
@Deprecated
public class KeywordHandler implements SchedulingHandler {

	public static final String KEYWORD = "keyword";

	public void deleteElements(SubscriptionSchedulingData scheduling) {
		// TODO Auto-generated method stub

	}

	public void executeWhenDone(SubscriptionSchedulingData scheduling) {
		// nothing to do right here
	}

	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		final Map<String, String> theResult = new HashMap<String, String>();
		theResult.put(KeywordHandler.KEYWORD, (String) settings.get(KeywordHandler.KEYWORD));
		return theResult;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings, String callerKey) throws MissingSettingException {
		if (!settings.containsKey(KeywordHandler.KEYWORD)) {
			throw new MissingSettingException(KeywordHandler.KEYWORD);
		}
	}

	public SchedulingInformationMap getSchedulingInformation(SubscriptionSchedulingData scheduling, APICaller caller) {
		final SchedulingInformationMap infoMap = new SchedulingInformationMap(scheduling);
		final SubscriptionSchedulingSettingsData setting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, KeywordHandler.KEYWORD);
		if (setting != null) {
			infoMap.put(KeywordHandler.KEYWORD, setting.getValue());
		}
		return infoMap;
	}

}
