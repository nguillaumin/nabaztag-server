package net.violet.platform.schedulers;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.VObjectData;

public class AmbiantWithKeywordHandler extends AmbiantHandler {

	private final KeywordHandler keywordHandler = new KeywordHandler();

	@Override
	public void checkSettings(VObjectData object, Map<String, Object> settings, String callerKey) throws MissingSettingException {
		super.checkSettings(object, settings, callerKey);
		this.keywordHandler.checkSettings(object, settings, callerKey);
	}

	@Override
	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		final Map<String, String> theResult = new HashMap<String, String>();
		theResult.putAll(super.generateSettings(object, settings, callerKey));
		theResult.putAll(this.keywordHandler.generateSettings(object, settings, callerKey));
		return theResult;
	}

	@Override
	public SchedulingInformationMap getSchedulingInformation(SubscriptionSchedulingData scheduling, APICaller caller) {
		return this.keywordHandler.getSchedulingInformation(scheduling, caller);
	}

}
