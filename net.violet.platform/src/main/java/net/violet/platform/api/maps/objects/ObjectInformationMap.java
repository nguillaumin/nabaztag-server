package net.violet.platform.api.maps.objects;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.VObjectData;

/**
 * A Map implementation used to define the structure of an object information.
 */
public class ObjectInformationMap extends AbstractAPIMap {

	public static String ID = "id";
	public static String NAME = "name";
	public static String HW_TYPE = "hw_type";
	public static String SERIAL_NUMBER = "serial_number";
	public static String OWNER = "owner";
	public static String CREATION_DATE = "creation_date";

	public ObjectInformationMap(APICaller inCaller, VObjectData inObjectData, boolean isVisible) {
		super();

		put(ObjectInformationMap.ID, inObjectData.getApiId(inCaller));
		put(ObjectInformationMap.NAME, inObjectData.getObject_login());
		put(ObjectInformationMap.HW_TYPE, inObjectData.getObjectType().getTypeName());
		if (isVisible) {
			put(ObjectInformationMap.SERIAL_NUMBER, inObjectData.getSerial());
		}
		put(ObjectInformationMap.OWNER, inObjectData.getOwner().getApiId(inCaller));
		put(ObjectInformationMap.CREATION_DATE, inObjectData.getCreationDate());

	}

}
