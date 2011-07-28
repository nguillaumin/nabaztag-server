package net.violet.platform.api.maps.applications;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ObjectType;

public class ApplicationInformationMap extends AbstractAPIMap {

	public static String ID = "id";
	public static String NAME = "name";
	public static String OWNER = "owner";
	public static String SUPPORTED_HARDWARE = "supported_hardware";
	public static String CREATION_DATE = "creation_date";
	public static String IS_REMOVABLE = "is_removable";
	public static String CLASS = "class";

	public ApplicationInformationMap(APICaller inAPICaller, ApplicationData inApplicationData) {
		super(5);

		put(ApplicationInformationMap.ID, inApplicationData.getApiId(inAPICaller));
		put(ApplicationInformationMap.NAME, inApplicationData.getName());
		put(ApplicationInformationMap.OWNER, inApplicationData.getOwner().getApiId(inAPICaller));
		final List<String> labelsList = new LinkedList<String>();
		for (final ObjectType aType : inApplicationData.getSupportedTypes()) {
			labelsList.add(aType.getTypeName());
		}
		put(ApplicationInformationMap.SUPPORTED_HARDWARE, labelsList);
		put(ApplicationInformationMap.CREATION_DATE, inApplicationData.getCreationDate());
		put(ApplicationInformationMap.IS_REMOVABLE, inApplicationData.isRemovable());
		put(ApplicationInformationMap.CLASS, inApplicationData.getApplicationClass().toString());
	}
}
