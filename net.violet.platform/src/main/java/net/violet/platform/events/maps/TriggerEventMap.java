package net.violet.platform.events.maps;

import java.util.Date;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.events.TriggerEvent;

public class TriggerEventMap extends AbstractAPIMap {

	/**
	 * The Trigger Event map wraps the underlying event
	 * and adds informations about the application to launch
	 * and its settings
	 * @param triggerEvent
	 * @param inCaller
	 * @throws InvalidParameterException 
	 */
	public TriggerEventMap(TriggerEvent triggerEvent, APICaller inCaller) {

		put("name", triggerEvent.getName());

		final APICaller apiCaller = inCaller == null ? new ApplicationAPICaller(ApplicationCredentialsData.findByApplication(triggerEvent.getApplication())) : inCaller;
		put("application", new ApplicationMap(triggerEvent.getApplication(), apiCaller));
		put("settings", triggerEvent.getSettings());
		put("trigger", triggerEvent.getEvent().getPojoMap(apiCaller));
	}

	/**
	 * The target application as it is known by all events.
	 * @author christophe - Violet
	 */
	private static class ApplicationMap extends AbstractAPIMap {

		public ApplicationMap(ApplicationData inApplication, APICaller inCaller) {
			put("id", inApplication.getApiId(inCaller));
			put("name", inApplication.getProfile().getTitle());
			put("author", new InteractionEventMap.OwnerMap(inApplication.getOwner(), inCaller));
			// TODO the application publication date should be moved in the application instead of application_package for less hussle
			put("publication", inApplication.getPackage().getModificationDate());
		}

		public ApplicationMap(Map<String, Object> inAppMap) {
			this.putAll(inAppMap);
		}

		public Date getModificatioDate() throws InvalidParameterException {
			return this.getDate("publication");
		}
	}

}
