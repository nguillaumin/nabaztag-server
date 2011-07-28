package net.violet.platform.api.maps.applications;

import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.datamodel.ApplicationApiLib;

/**
 * This map is used to expose the accessible fields of action
 * violet.apilib.getJavaScriptAPIPackage in the public API
 */
public class ApplicationApiLibMap extends AbstractAPIMap {

	public ApplicationApiLibMap(ApplicationApiLib apiData) {

		super(4);

		if (apiData == null) {
			throw new IllegalArgumentException("API Lib data cannot be null !");
		}

		put("language", apiData.getLanguage().name());
		put("version", apiData.getApiVersion());
		put("release_date", apiData.getReleaseDate());
		// For now, code is always a string in UTF-8.
		put("code", apiData.getCode());
	}
}
