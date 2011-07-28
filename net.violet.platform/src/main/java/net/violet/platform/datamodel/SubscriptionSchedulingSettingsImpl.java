package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

public class SubscriptionSchedulingSettingsImpl extends ObjectRecord<SubscriptionSchedulingSettings, SubscriptionSchedulingSettingsImpl> implements SubscriptionSchedulingSettings {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<SubscriptionSchedulingSettingsImpl> SPECIFICATION = new SQLObjectSpecification<SubscriptionSchedulingSettingsImpl>("subscription_scheduling_settings", SubscriptionSchedulingSettingsImpl.class, new SQLKey("id"));

	private static final String[] NEW_COLUMNS = new String[] { "subscription_scheduling_id", "scheduling_settings_key", "scheduling_settings_value", };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected long subscription_scheduling_id;
	protected String scheduling_settings_key;
	protected String scheduling_settings_value;

	/**
	 * Association 1-1 pour l'application.
	 */
	private final SingleAssociationNotNull<SubscriptionSchedulingSettings, SubscriptionScheduling, SubscriptionSchedulingImpl> mSubscriptionSchedulingSettings;

	@Override
	public SQLObjectSpecification<SubscriptionSchedulingSettingsImpl> getSpecification() {
		return SubscriptionSchedulingSettingsImpl.SPECIFICATION;
	}

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected SubscriptionSchedulingSettingsImpl(long id) throws SQLException {
		init(id);
		this.mSubscriptionSchedulingSettings = new SingleAssociationNotNull<SubscriptionSchedulingSettings, SubscriptionScheduling, SubscriptionSchedulingImpl>(this, "subscription_scheduling_id", SubscriptionSchedulingImpl.SPECIFICATION);
	}

	protected SubscriptionSchedulingSettingsImpl() {
		this.mSubscriptionSchedulingSettings = new SingleAssociationNotNull<SubscriptionSchedulingSettings, SubscriptionScheduling, SubscriptionSchedulingImpl>(this, "subscription_scheduling_id", SubscriptionSchedulingImpl.SPECIFICATION);
	}

	/**
	 * Constructeur à partir de nouvelles colonnes.
	 */
	public SubscriptionSchedulingSettingsImpl(SubscriptionScheduling inSubscriptionScheduling, String inKey, String inValue) throws SQLException {
		this.mSubscriptionSchedulingSettings = new SingleAssociationNotNull<SubscriptionSchedulingSettings, SubscriptionScheduling, SubscriptionSchedulingImpl>(this, "subscription_scheduling_id", (SubscriptionSchedulingImpl) inSubscriptionScheduling);
		this.scheduling_settings_key = inKey;
		this.scheduling_settings_value = inValue;
		this.subscription_scheduling_id = inSubscriptionScheduling.getId();
		init(SubscriptionSchedulingSettingsImpl.NEW_COLUMNS);
	}

	public String getKey() {
		return this.scheduling_settings_key;
	}

	public SubscriptionScheduling getSubscriptionScheduling() {
		return this.mSubscriptionSchedulingSettings.get(this.subscription_scheduling_id);
	}

	public String getValue() {
		return this.scheduling_settings_value;
	}

	public void setValue(String inValue) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setSettings_value(inValue, theUpdateMap);
		update(theUpdateMap);
	}

	private void setSettings_value(String inValue, Map<String, Object> inUpdateMap) {
		if ((inValue != null) && !this.scheduling_settings_value.equals(inValue)) {
			this.scheduling_settings_value = inValue;
			inUpdateMap.put("scheduling_settings_value", this.scheduling_settings_value);
		}
	}

	public void updateKeyAndValue(String key, String value) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setSettings_value(value, theUpdateMap);
		setSettings_key(key, theUpdateMap);
		update(theUpdateMap);
	}

	private void setSettings_key(String inKey, Map<String, Object> inUpdateMap) {
		if ((inKey != null) && !this.scheduling_settings_key.equals(inKey)) {
			this.scheduling_settings_key = inKey;
			inUpdateMap.put("scheduling_settings_key", this.scheduling_settings_key);
		}
	}

}
