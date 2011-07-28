package net.violet.platform.events;

import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.events.maps.InteractionEventMap;
import net.violet.platform.events.maps.ZtampEventMap;

public class ZtampRemovalEvent extends ZtampEvent {

	public static final String NAME = "ZtampRemoval";

	public ZtampRemovalEvent(VObjectData inReader, String inRfidSerial) {
		super(ZtampRemovalEvent.NAME, inReader, inRfidSerial);
	}

	/**
	 * Used by reflection !
	 * @param properties
	 * @throws InvalidParameterException
	 */
	public ZtampRemovalEvent(PojoMap properties) throws InvalidParameterException {
		this(VObjectData.findBySerial(properties.getString(ZtampEventMap.READER, true)), properties.getString(InteractionEventMap.OBJECT, true));
	}
}
