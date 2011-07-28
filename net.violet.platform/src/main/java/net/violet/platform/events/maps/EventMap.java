package net.violet.platform.events.maps;

import java.util.Map;

import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.events.AbstractEvent;

public abstract class EventMap extends AbstractAPIMap {

	protected EventMap(AbstractEvent inEvent) {
		put("name", inEvent.getName());
		put("when", inEvent.getTimestamp());
		put("ttl", inEvent.getTTL());
	}

	public static void clearMap(Map<String, Object> map) {
		map.remove("name");
		map.remove("when");
		map.remove("ttl");
	}

}
