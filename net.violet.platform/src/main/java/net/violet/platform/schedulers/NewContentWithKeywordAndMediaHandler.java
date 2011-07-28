package net.violet.platform.schedulers;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.applications.MailAlertHandler;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;

public class NewContentWithKeywordAndMediaHandler implements SchedulingHandler {

	public static final String MEDIA = "media";

	public void deleteElements(SubscriptionSchedulingData scheduling) {
		// Nothing to do here
	}

	public void executeWhenDone(SubscriptionSchedulingData scheduling) {
		// nothing to do right here
	}

	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		final Map<String, String> theResult = new HashMap<String, String>();
		final String mediaId = String.valueOf((callerKey == null) ? settings.get("media") : MusicData.findByAPIId((String) settings.get("media"), callerKey).getId());
		theResult.put(KeywordHandler.KEYWORD, (String) settings.get(KeywordHandler.KEYWORD));
		theResult.put(NewContentWithKeywordAndMediaHandler.MEDIA, mediaId);
		theResult.put(MailAlertHandler.NEW_CONTENT_FLAG, "0");
		return theResult;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings, String callerKey) throws MissingSettingException {
		if (!settings.containsKey(KeywordHandler.KEYWORD) || !settings.containsKey("media")) {
			throw new MissingSettingException(KeywordHandler.KEYWORD + " or media");
		}
	}

	public SchedulingInformationMap getSchedulingInformation(SubscriptionSchedulingData scheduling, APICaller caller) {
		final SchedulingInformationMap infoMap = new SchedulingInformationMap(scheduling);

		final Map<String, SubscriptionSchedulingSettingsData> settings = SubscriptionSchedulingSettingsData.findAllBySubscriptionSchedulingAsMap(scheduling);
		final SubscriptionSchedulingSettingsData theSettingsKeyword = settings.get(KeywordHandler.KEYWORD);
		final SubscriptionSchedulingSettingsData theSettingsMedia = settings.get(NewContentWithKeywordAndMediaHandler.MEDIA);
		infoMap.put(KeywordHandler.KEYWORD, theSettingsKeyword.getValue());

		final Music theMusic = Factories.MUSIC.find(Long.parseLong(theSettingsMedia.getValue()));
		if (theMusic != null) {
			infoMap.put(NewContentWithKeywordAndMediaHandler.MEDIA, MusicData.getData(theMusic).getApiId(caller));
		}

		return infoMap;
	}

}
