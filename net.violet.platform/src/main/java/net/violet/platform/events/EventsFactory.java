package net.violet.platform.events;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;

import org.apache.log4j.Logger;

public class EventsFactory {

	private final static Logger LOGGER = Logger.getLogger(EventsFactory.class);

	private final static Map<String, Constructor> EVENTS_CONSTRUCTORS = new HashMap<String, Constructor>();

	static {
		try {
			EventsFactory.EVENTS_CONSTRUCTORS.put(ZtampDetectionEvent.NAME, ZtampDetectionEvent.class.getConstructor(PojoMap.class));
			EventsFactory.EVENTS_CONSTRUCTORS.put(ZtampRemovalEvent.NAME, ZtampRemovalEvent.class.getConstructor(PojoMap.class));
		} catch (final Exception e) {
			EventsFactory.LOGGER.fatal(e, e);
		}
	}

	/**
	 * Creates an event object based on the given name and properties.
	 * @param eventName
	 * @param eventProperties
	 * @return
	 * @throws InvalidParameterException
	 * @throws IllegalArgumentException
	 * @throws InternalErrorException
	 */
	public static Event create(String eventName, PojoMap eventProperties) throws InvalidParameterException, IllegalArgumentException, InternalErrorException {

		final Constructor eventConstructor = EventsFactory.EVENTS_CONSTRUCTORS.get(eventName);
		if (eventConstructor != null) {
			try {
				return (Event) eventConstructor.newInstance(eventProperties);
			} catch (final Exception e) {
				EventsFactory.LOGGER.fatal(e, e);
				throw new InternalErrorException("Failed to generate event " + eventName + " with params : " + eventProperties);
			}

		}
		return new CustomEvent(eventName, eventProperties);
	}

}
