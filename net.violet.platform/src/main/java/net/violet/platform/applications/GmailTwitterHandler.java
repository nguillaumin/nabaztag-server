package net.violet.platform.applications;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.FeedsTools;
import net.violet.platform.util.CipherTools;

public class GmailTwitterHandler implements ApplicationHandler {

	public static final int NB_NEWS_TO_READ = 10;

	public static final String LOGIN = "login";
	public static final String PASSWORD = "password";
	public static final String LANGUAGE = "language";

	public static final String ETAG = "etag";
	public static final String LAST_MODIFIED = "last_modified";
	public static final String LAST_ENTRY_ID = "last_entry_id";

	private final ApplicationData application;
	private final String url;

	protected GmailTwitterHandler(ApplicationData application) {
		this.application = application;
		this.url = ApplicationSettingData.findByApplicationAndKey(application, ApplicationSetting.FeedHandler.URL).getValue();
	}

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		return SubscriptionData.create(this.application, object, getSettings(null, settings));
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		subscription.setSettings(getSettings(subscription, settings));
	}

	private Map<String, Object> getSettings(SubscriptionData subscription, Map<String, Object> settings) {
		// caution here : we have to keep the three internal settings used to analyze the feed
		final Map<String, Object> oldSettings = ((subscription != null) && subscription.isValid()) ? subscription.getSettings() : Collections.<String, Object> emptyMap();

		final Map<String, Object> theNewSettings = new HashMap<String, Object>(oldSettings);
		theNewSettings.putAll(settings);
		final String uncipheredPassword = theNewSettings.remove(GmailTwitterHandler.PASSWORD).toString();
		theNewSettings.put(GmailTwitterHandler.PASSWORD, CipherTools.cipher(uncipheredPassword));
		return theNewSettings;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) throws MissingSettingException, InvalidSettingException {

		if (settings.size() > 3) {
			throw new InvalidSettingException("All settings", "too much settings");
		}

		final Object languageSetting = settings.get(GmailTwitterHandler.LANGUAGE);
		if (languageSetting == null) {
			throw new MissingSettingException(GmailTwitterHandler.LANGUAGE);
		}

		final Lang theLang = Factories.LANG.findByIsoCode(languageSetting.toString());
		if (theLang == null) {
			throw new InvalidSettingException(GmailTwitterHandler.LANGUAGE, languageSetting.toString());
		}

		final Object loginSetting = settings.get(GmailTwitterHandler.LOGIN);
		final Object passwordSetting = settings.get(GmailTwitterHandler.PASSWORD);
		if ((loginSetting == null) || (passwordSetting == null)) {
			throw new MissingSettingException(loginSetting == null ? GmailTwitterHandler.LOGIN : GmailTwitterHandler.PASSWORD);
		}

		if (!FeedsTools.isFeedValid(this.url, loginSetting.toString(), passwordSetting.toString())) {
			throw new InvalidSettingException(GmailTwitterHandler.LOGIN + "/" + GmailTwitterHandler.PASSWORD, loginSetting.toString());
		}

	}

	public void delete(SubscriptionData subscription) {
		subscription.delete();
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		final Map<String, Object> theSettings = new HashMap<String, Object>(subscription.getSettings());
		//theSettings.remove(GmailTwitterHandler.PASSWORD);
		theSettings.remove(GmailTwitterHandler.ETAG);
		theSettings.remove(GmailTwitterHandler.LAST_ENTRY_ID);
		theSettings.remove(GmailTwitterHandler.LAST_MODIFIED);
		return theSettings;
	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData inSubscription, VObjectData inObject, String login, String password, Lang language, boolean isGmail) throws InvalidSettingException, InvalidSchedulingsException, MissingSettingException {
		final Map<String, Object> theSettings = new HashMap<String, Object>();

		theSettings.put(GmailTwitterHandler.LOGIN, login);
		theSettings.put(GmailTwitterHandler.PASSWORD, password);
		theSettings.put(GmailTwitterHandler.LANGUAGE, language.getIsoCode());

		final List<Map<String, Object>> schedulings = Collections.singletonList(Collections.singletonMap(SchedulingType.TYPE_KEY, (Object) SCHEDULING_TYPE.NewContent.getLabel()));

		if (inSubscription == null) {
			final ApplicationData appli = isGmail ? ApplicationData.getData(Application.NativeApplication.GMAIL.getApplication()) : ApplicationData.getData(Application.NativeApplication.TWITTER.getApplication());
			return SubscriptionManager.createSubscription(appli, inObject, theSettings, schedulings, null);
		}

		SubscriptionManager.updateSubscription(inSubscription, theSettings, schedulings, null);
		return inSubscription;
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return subscription.getSettings().get(GmailTwitterHandler.LOGIN).toString();
	}
}
