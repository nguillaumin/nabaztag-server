package net.violet.platform.events;

import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.events.maps.InteractionEventMap;
import net.violet.platform.events.maps.ZtampEventMap;

public class ZtampDetectionEvent extends ZtampEvent {

	public static final String NAME = "ZtampDetection";

	public ZtampDetectionEvent(VObjectData inReader, String inRfidSerial) {
		super(ZtampDetectionEvent.NAME, inReader, inRfidSerial);
	}

	/**
	 * Used by refelction! 
	 * @param properties
	 * @throws InvalidParameterException
	 */
	public ZtampDetectionEvent(PojoMap properties) throws InvalidParameterException {
		this(VObjectData.findBySerial(properties.getString(ZtampEventMap.READER, true)), properties.getString(InteractionEventMap.OBJECT, true));
	}

}
