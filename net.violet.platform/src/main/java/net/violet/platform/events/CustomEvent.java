package net.violet.platform.events;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.events.maps.CustomEventMap;
import net.violet.platform.events.maps.EventMap;

/**
 * A custom event can contain any information.
 */
public class CustomEvent extends AbstractEvent {

	private final PojoMap eventMap;

	/**
	 * Creates a custom event based on the given map.
	 * @param map
	 * @throws InvalidParameterException if the 'name' key is not in the map or if 'ttl' does not refer to an integer.
	 */
	public CustomEvent(String eventName, PojoMap properties) throws InvalidParameterException {
		super(eventName, properties.getInt("ttl", AbstractEvent.DEFAULT_TTL));
		this.eventMap = new PojoMap(properties);
		EventMap.clearMap(this.eventMap);
	}

	public PojoMap getPojoMap(APICaller caller) {
		return new CustomEventMap(this);
	}

	public PojoMap getProperties() {
		return this.eventMap;
	}

}
