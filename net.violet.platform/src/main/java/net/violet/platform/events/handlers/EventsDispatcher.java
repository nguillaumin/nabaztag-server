package net.violet.platform.events.handlers;

import net.violet.platform.events.CustomEvent;
import net.violet.platform.events.Event;
import net.violet.platform.events.ZtampEvent;

/**
 * This Dispatcher receives Event objects and dispatches them to the appropriate EventHandler. 
 */
public class EventsDispatcher {

	public static void processEvent(Event inEvent) {

		if (inEvent instanceof ZtampEvent) {
			ZtampEventsHandler.processEvent((ZtampEvent) inEvent);

		} else if (inEvent instanceof CustomEvent) {
			CustomEventsHandler.processEvent((CustomEvent) inEvent);

		}
		//TODO to be continued ...
	}
}
