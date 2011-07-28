package net.violet.platform.applications;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.FeedsTools;

public class FacebookHandler implements ApplicationHandler {

	public static final int NB_NEWS_TO_READ = 10;

	public static final String ETAG = "etag";
	public static final String LAST_MODIFIED = "last_modified";
	public static final String LAST_ENTRY_ID = "last_entry_id";

	public static final String URL = "url";
	public static final String LABEL = "label";

	private final ApplicationData application;
	private static final String START_FACEBOOK_URL = "http://www.facebook.com/feeds/notifications.php";

	protected FacebookHandler(ApplicationData application) {
		this.application = application;
	}

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		return SubscriptionData.create(this.application, object, getSettings(null, settings));
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		subscription.setSettings(getSettings(subscription, settings));
	}

	private Map<String, Object> getSettings(SubscriptionData subscription, Map<String, Object> settings) {
		// caution here : we have to keep the three internal settings used to analyze the feed
		final Map<String, Object> oldSettings = ((subscription != null) && subscription.isValid()) ? subscription.getSettings() : Collections
				.<String, Object> emptyMap();

		final Map<String, Object> theNewSettings = new HashMap<String, Object>(oldSettings);
		theNewSettings.putAll(settings);
		return theNewSettings;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) throws MissingSettingException, InvalidSettingException {

		if (settings.size() > 2) {
			throw new InvalidSettingException("All settings", "too much settings");
		}

		final Object urlSetting = settings.get(FacebookHandler.URL);
		if (urlSetting == null) {
			throw new MissingSettingException(FacebookHandler.URL);
		}

		final Object labelSetting = settings.get(FacebookHandler.LABEL);
		if (labelSetting == null) {
			throw new MissingSettingException(FacebookHandler.LABEL);
		}

		if (!checkFacebookUrl(urlSetting.toString())) {
			throw new InvalidSettingException(FacebookHandler.URL, urlSetting.toString());
		}

		if (!FeedsTools.isFeedValid(urlSetting.toString(), null, null)) {
			throw new InvalidSettingException(FacebookHandler.URL, urlSetting.toString());
		}

	}

	public void delete(SubscriptionData subscription) {
		subscription.delete();
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		final Map<String, Object> theSettings = new HashMap<String, Object>(subscription.getSettings());
		theSettings.remove(FacebookHandler.ETAG);
		theSettings.remove(FacebookHandler.LAST_ENTRY_ID);
		theSettings.remove(FacebookHandler.LAST_MODIFIED);
		return theSettings;
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return subscription.getSettings().get(FacebookHandler.LABEL).toString();
	}

	private boolean checkFacebookUrl(String inUrl) {
		return inUrl.startsWith(FacebookHandler.START_FACEBOOK_URL);
	}
}
