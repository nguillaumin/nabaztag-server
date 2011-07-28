package net.violet.platform.applications;

import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

public class DefaultHandler implements ApplicationHandler {

	private final ApplicationData application;

	protected DefaultHandler(ApplicationData application) {
		this.application = application;
	}

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		return SubscriptionData.create(this.application, object, settings);
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) throws InvalidSettingException, MissingSettingException {
		// nothing to do
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		subscription.setSettings(settings);
	}

	public void delete(SubscriptionData subscription) {
		subscription.delete();
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		return subscription.getSettings();
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return null;
	}
}
