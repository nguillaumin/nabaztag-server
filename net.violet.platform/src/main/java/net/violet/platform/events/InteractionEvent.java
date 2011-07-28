package net.violet.platform.events;

import net.violet.platform.dataobjects.VObjectData;

/**
 * An InteractionEvent is a kind of event that involves objects. Two objects are actually involved : the main object
 * (known as the emitter) used to select the processes to launch, and the target object supposed to receive the processes' result.
 * The emitter and the target may be the same object.
 */
public abstract class InteractionEvent extends AbstractEvent {

	private final String emitterSerial;
	private VObjectData target;

	/**
	 * Creates an InteractionEvent with a given name, an emitter serial and a target object.
	 * We use the emitter's serial because the emitter may be missing in the object tables at the moment the event is created.
	 * @param eventName
	 * @param emitterSerial
	 * @param target
	 */
	protected InteractionEvent(String eventName, String emitterSerial, VObjectData target) {
		super(eventName);
		this.emitterSerial = emitterSerial;
		this.target = target;
	}

	/**
	 * @return the object on which the interaction did occur. May return null !
	 */
	public VObjectData getEmitter() {
		return VObjectData.findBySerial(this.emitterSerial);
	}

	/**
	 * @return the object on which the interaction did occur
	 */
	public String getEmitterSerial() {
		return this.emitterSerial;
	}

	/**
	 * Redirect an event to a new target object
	 * @param inTarget
	 */
	public void setExecutionTarget(VObjectData inTarget) {
		this.target = inTarget;
	}

	public VObjectData getTarget() {
		return this.target;
	}

}
