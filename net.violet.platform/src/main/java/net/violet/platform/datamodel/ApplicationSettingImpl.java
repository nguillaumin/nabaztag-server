package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

public class ApplicationSettingImpl extends ObjectRecord<ApplicationSetting, ApplicationSettingImpl> implements ApplicationSetting {

	public static final SQLObjectSpecification<ApplicationSettingImpl> SPECIFICATION = new SQLObjectSpecification<ApplicationSettingImpl>("application_setting", ApplicationSettingImpl.class, new SQLKey("id"));

	private static final String[] NEW_COLUMNS = new String[] { "application_id", "key", "value", };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected long application_id;
	protected String key;
	protected String value;

	private final SingleAssociationNotNull<ApplicationSetting, Application, ApplicationImpl> mApplication;

	@Override
	public SQLObjectSpecification<ApplicationSettingImpl> getSpecification() {
		return ApplicationSettingImpl.SPECIFICATION;
	}

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected ApplicationSettingImpl(long id) throws SQLException {
		init(id);
		this.mApplication = new SingleAssociationNotNull<ApplicationSetting, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
	}

	protected ApplicationSettingImpl() {
		this.mApplication = new SingleAssociationNotNull<ApplicationSetting, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
	}

	/**
	 * Constructeur à partir de nouvelles colonnes.
	 */
	public ApplicationSettingImpl(Application application, String inKey, String inValue) throws SQLException {
		this.mApplication = new SingleAssociationNotNull<ApplicationSetting, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
		this.application_id = application.getId();
		this.key = inKey;
		this.value = inValue;
		init(ApplicationSettingImpl.NEW_COLUMNS);
	}

	public Application getApplication() {
		return this.mApplication.get(this.application_id);
	}

	public String getKey() {
		return this.key;
	}

	public String getValue() {
		return this.value;
	}

	public void setKey(String key) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setKey(key, theUpdateMap);
		update(theUpdateMap);
	}

	public void setValue(String value) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setValue(value, theUpdateMap);
		update(theUpdateMap);
	}

	private void setKey(String key, Map<String, Object> updateMap) {
		if ((key != null) && !key.equals(this.key)) {
			this.key = key;
			updateMap.put("key", key);
		}
	}

	private void setValue(String value, Map<String, Object> updateMap) {
		if ((value != null) && !value.equals(this.value)) {
			this.value = value;
			updateMap.put("value", value);
		}
	}

}
