package net.violet.platform.applications;

import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

/**
 * An ApplicationHandler is in charge of describing an application-specific behavior. It is involved in the validation, the creation, 
 * the update and the deletion of a subscription to the application it's in charge of.
 */
public interface ApplicationHandler {

	/**
	 * Creates a subscription to the application. This method does NOT perform any check on the given settings and does NOT
	 *  call the checkSettings method which should be called before invoking the create method. 
	 * @param object
	 * @param settings
	 * @return
	 */
	SubscriptionData create(VObjectData object, Map<String, Object> settings);

	/**
	 * Checks the conformity of the provided settings. This method MUST NOT have any side-effect: it is not meant to modify the 
	 * given parameters even the settings map.
	 * @param object
	 * @param settings
	 * @throws InvalidSettingException
	 * @throws MissingSettingException
	 */
	void checkSettings(VObjectData object, Map<String, Object> settings) throws InvalidSettingException, MissingSettingException;

	/**
	 * Updates the given subscription's settings.
	 * @param subscription
	 * @param settings
	 */
	void update(SubscriptionData subscription, Map<String, Object> settings);

	/**
	 * Deletes the given subscription.
	 * @param subscription
	 */
	void delete(SubscriptionData subscription);

	/**
	 * Some applications use some UI settings but store different ones. This method is a way for them to return
	 * the original UI settings (used when the subscription was created) based on those they stored to perform their action.
	 * @param subscription
	 * @return
	 */
	Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller);

	String getSubscriptionInformation(SubscriptionData subscription);

}
