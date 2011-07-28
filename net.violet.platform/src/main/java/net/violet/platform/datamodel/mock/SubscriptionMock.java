package net.violet.platform.datamodel.mock;

import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;

public class SubscriptionMock extends AbstractMockRecord<Subscription, SubscriptionMock> implements Subscription {

	private final Application mApplication;
	private final VObject mObject;
	private final Map<String, Object> settings;

	public SubscriptionMock(long inId, Application inApplication, VObject inObject) {
		this(inId, inApplication, inObject, new HashMap<String, Object>());
	}

	public SubscriptionMock(long inId, Application inApplication, VObject inObject, Map<String, Object> inSettings) {
		super(inId);
		this.mApplication = inApplication;
		this.mObject = inObject;
		this.settings = inSettings;
	}

	public Application getApplication() {
		return this.mApplication;
	}

	public VObject getObject() {
		return this.mObject;
	}

	public PojoMap getSettings() {
		return new PojoMap(this.settings);
	}

	public void setSettings(Map<String, ? extends Object> settings) {
		this.settings.clear();
		this.settings.putAll(settings);
	}

}
