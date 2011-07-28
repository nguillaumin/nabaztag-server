package net.violet.platform.events.maps;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.events.ZtampEvent;

public class ZtampEventMap extends InteractionEventMap {

	public static final String READER = "reader";

	public ZtampEventMap(ZtampEvent inEvent, APICaller inCaller) {
		super(inEvent, inCaller);
		if (!inEvent.getReader().equals(inEvent.getTarget())) {
			put(ZtampEventMap.READER, new ObjectMap(inEvent.getReader(), inCaller));
		}
	}

}
