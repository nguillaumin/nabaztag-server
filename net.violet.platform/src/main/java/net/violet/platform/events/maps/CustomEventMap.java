package net.violet.platform.events.maps;

import net.violet.platform.events.CustomEvent;

public class CustomEventMap extends EventMap {

	public CustomEventMap(CustomEvent event) {
		super(event);
		putAll(event.getProperties());
	}
}
