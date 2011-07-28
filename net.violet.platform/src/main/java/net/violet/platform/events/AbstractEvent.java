package net.violet.platform.events;

/**
 * Abstract class containing the basic treatment for data common to all kinds of event.
 * @author vincent
 *
 */
public abstract class AbstractEvent implements Event, Cloneable {

	protected static final int DEFAULT_TTL = 30;

	private final String name;
	private final long timestamp = System.currentTimeMillis();
	private final int ttl;

	/**
	 * Creates an event with the default ttl value.
	 * @param inEventName
	 */
	protected AbstractEvent(String inEventName) {
		this(inEventName, AbstractEvent.DEFAULT_TTL);
	}

	protected AbstractEvent(String inEventName, int ttl) {
		this.name = inEventName;
		this.ttl = ttl;
	}

	public String getName() {
		return this.name;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public int getTTL() {
		return this.ttl;
	}

}
