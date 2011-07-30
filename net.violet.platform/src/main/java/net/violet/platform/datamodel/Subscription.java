package net.violet.platform.datamodel;

import java.util.Map;

import net.violet.db.records.Record;
import net.violet.platform.api.maps.PojoMap;

/**
 * Objects subscription to applications
 * 
 */
public interface Subscription extends Record<Subscription> {

	Application getApplication();

	VObject getObject();

	PojoMap getSettings();

	void setSettings(Map<String, ? extends Object> settings);

}
