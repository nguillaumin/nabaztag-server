package net.violet.platform.schedulers;

import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.VObjectData;

/**
 * This interface describes the useful methods to create/update or delete a scheduling type.
 * Each SchedulingHandler is in charge of a specific kind of scheduling.
 */
public interface SchedulingHandler {

	/**
	 * Checks if the given settings are correct according to the involved scheduling type. This method DOES NOT modify the 
	 * given settings.
	 * @param object
	 * @param settings
	 * @param callerKey
	 * @throws InvalidParameterException
	 */
	void checkSettings(VObjectData object, Map<String, Object> settings, String callerKey) throws InvalidSettingException, InvalidSchedulingsException, MissingSettingException;

	/**
	 * Generates the settings which have to be saved, based on the given ones. This method DOES NOT modify the given settings. 
	 * @param object
	 * @param settings
	 * @param callerKey
	 * @return
	 */
	Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey);

	/**
	 * Deletes the scheduling.
	 * @param scheduling
	 */
	void deleteElements(SubscriptionSchedulingData scheduling);

	/**
	 * A very specific method, should be executed when the scheduling has been successfully created.
	 * @param scheduling
	 */
	void executeWhenDone(SubscriptionSchedulingData scheduling);

	SchedulingInformationMap getSchedulingInformation(SubscriptionSchedulingData scheduling, APICaller caller);
}
