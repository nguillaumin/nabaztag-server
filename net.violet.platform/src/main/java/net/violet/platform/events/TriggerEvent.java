package net.violet.platform.events;

import java.util.Collections;
import java.util.Map;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.events.maps.TriggerEventMap;

/**
 * A special kind of event (is it really an event ? According me it's not) designed to wrap an other event.
 */
public class TriggerEvent extends AbstractEvent {

	public static final String NAME = "Trigger";

	private final ApplicationData application;
	private final SubscriptionData subscription;
	private final Event event;
	private final SchedulingType.SCHEDULING_TYPE schedulingType;

	public TriggerEvent(ApplicationData inApplication, Event inEvent, SCHEDULING_TYPE inSchedulingType) {
		this(inApplication, null, inEvent, inSchedulingType);
	}

	public TriggerEvent(SubscriptionData inSubscription, Event inEvent, SCHEDULING_TYPE inSchedulingType) {
		this(inSubscription.getApplication(), inSubscription, inEvent, inSchedulingType);
	}

	private TriggerEvent(ApplicationData inApplication, SubscriptionData inSubscription, Event inEvent, SCHEDULING_TYPE inSchedulingType) {
		super(TriggerEvent.NAME, inEvent.getTTL());
		this.application = inApplication;
		this.event = inEvent;
		this.schedulingType = inSchedulingType;
		this.subscription = inSubscription;
	}

	public Map<String, Object> getSettings() {
		if (this.subscription != null) {
			return this.subscription.getSettings();
		}

		return Collections.<String, Object> emptyMap();
	}

	public ApplicationData getApplication() {
		return this.application;
	}

	public Event getEvent() {
		return this.event;
	}

	public SchedulingType.SCHEDULING_TYPE getSchedulingType() {
		return this.schedulingType;
	}

	/**
	 * May be null !
	 * @return
	 */
	public SubscriptionData getSubscription() {
		return this.subscription;
	}

	public PojoMap getPojoMap(APICaller caller) {
		return new TriggerEventMap(this, caller);
	}
}
