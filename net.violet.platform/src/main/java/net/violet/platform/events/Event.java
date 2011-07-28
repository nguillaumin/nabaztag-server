package net.violet.platform.events;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.PojoMap;

/**
 * Minimal interface for an Event. An event contains three important pieces of information : name of the event, 
 * when the event has been forged and the event ttl.
 */
public interface Event extends Cloneable {

	String getName();

	public long getTimestamp();

	public int getTTL();

	PojoMap getPojoMap(APICaller caller);

}
