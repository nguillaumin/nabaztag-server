package net.violet.platform.events;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.events.maps.ZtampEventMap;

/**
 * Basic class for events involving ztamps. A ZtampEvent is an InteractionEvent with reader information.
 */
public abstract class ZtampEvent extends InteractionEvent {

	private final VObjectData reader;

	/**
	 * Creates a ZtampEvent with a name, a reader and a ztamp serial. The reader is also the target and the rfid is the emitter.
	 * The given reader cannot be null ! Otherwise a NullPointerException is thrown !
	 * @param inEventName
	 * @param inReader
	 * @param inRfidSerial
	 */
	protected ZtampEvent(String inEventName, VObjectData inReader, String inRfidSerial) {
		super(inEventName, inRfidSerial, inReader);
		this.reader = inReader;
		if ((this.reader == null) || !this.reader.isValid()) {
			throw new NullPointerException("reader is null");
		}
	}

	public VObjectData getReader() {
		return this.reader;
	}

	public String getReaderSerial() {
		return this.reader.getSerial();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public PojoMap getPojoMap(APICaller caller) {
		return new ZtampEventMap(this, caller);
	}

}
