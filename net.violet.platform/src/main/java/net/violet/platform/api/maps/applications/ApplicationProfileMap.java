package net.violet.platform.api.maps.applications;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationProfileData;
import net.violet.platform.dataobjects.ApplicationTagData;
import net.violet.platform.dataobjects.ObjectType;

public class ApplicationProfileMap extends AbstractAPIMap {

	final public static String TITLE = "title";
	final public static String DESCRIPTION = "description";
	final public static String OPEN_SOURCE = "open_source";
	final public static String SETTING_FILE = "setting";
	final public static String SCHEDULING_FILE = "scheduling";
	final public static String PICTURE_FILE = "picture";
	final public static String ICON_FILE = "icon";
	final public static String INSTRUCTIONS = "instructions";
	final public static String URL = "url";

	final private static String TAGS = "tags";
	final public static String CATEGORY = "category";
	final private static String OPTIMIZED = "optimized_for";

	public ApplicationProfileMap(ApplicationData inApplicationData) {
		super(12);

		final ApplicationProfileData theApplicationProfileData = inApplicationData.getProfile();

		put(ApplicationProfileMap.TITLE, theApplicationProfileData.getTitle());
		put(ApplicationProfileMap.DESCRIPTION, theApplicationProfileData.getDescription());
		put(ApplicationProfileMap.OPEN_SOURCE, theApplicationProfileData.isOpenSource());
		put(ApplicationProfileMap.PICTURE_FILE, theApplicationProfileData.getPictureFile().getPath());
		put(ApplicationProfileMap.ICON_FILE, theApplicationProfileData.getIconFile().getPath());
		put(ApplicationProfileMap.INSTRUCTIONS, theApplicationProfileData.getInstructions());
		put(ApplicationProfileMap.URL, theApplicationProfileData.getUrl());

		final List<String> result = new ArrayList<String>();
		for (final ApplicationTagData theApplicationTagData : inApplicationData.findAllTagByApplication()) {
			result.add(theApplicationTagData.getName());
		}
		put(ApplicationProfileMap.TAGS, result);

		put(ApplicationProfileMap.CATEGORY, inApplicationData.getApplicationCategory().getName());
		final List<String> labelsList = new LinkedList<String>();
		for (final ObjectType aType : inApplicationData.getSupportedTypes()) {
			labelsList.add(aType.getTypeName());
		}

		put(ApplicationProfileMap.OPTIMIZED, labelsList);

	}
}
