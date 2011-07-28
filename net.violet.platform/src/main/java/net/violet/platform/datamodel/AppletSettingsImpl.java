package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.platform.datamodel.factories.Factories;

public final class AppletSettingsImpl extends ObjectRecord<AppletSettings, AppletSettingsImpl> implements AppletSettings {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<AppletSettingsImpl> SPECIFICATION = new SQLObjectSpecification<AppletSettingsImpl>("applet_settings", AppletSettingsImpl.class, new SQLKey("id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "applet_id", "primary_object_id", "secondary_object_id", "settings_key", "settings_value", };

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected AppletSettingsImpl(long id) throws SQLException {
		init(id);
	}

	protected AppletSettingsImpl() {
		// This space for rent.
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 */
	public AppletSettingsImpl(long inApplet_id, String inSettings_key, String inSettings_value, VObject primaryObject, VObject secondaryObject) throws SQLException {
		this.applet_id = inApplet_id;
		this.primary_object_id = primaryObject.getId();
		this.secondary_object_id = (secondaryObject == null) ? 0 : secondaryObject.getId();
		this.settings_key = inSettings_key;
		this.settings_value = inSettings_value;

		init(AppletSettingsImpl.NEW_COLUMNS);
	}

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected long applet_id;
	protected long primary_object_id;
	protected long secondary_object_id;
	protected String settings_key;
	protected String settings_value;

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.AppletSettings#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.AppletSettings#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<AppletSettingsImpl> getSpecification() {
		return AppletSettingsImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.AppletSettings#getApplet_id()
	 */
	public long getApplet_id() {
		return this.applet_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.AppletSettings#getSettings_Key()
	 */
	public String getSettings_Key() {
		return this.settings_key;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.AppletSettings#getValue()
	 */
	public String getValue() {
		return this.settings_value;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.AppletSettings#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setSettings_Value(theUpdateMap, value);
		update(theUpdateMap);

	}

	private void setSettings_Value(Map<String, Object> inUpdateMap, String settings_value) {
		if (!this.settings_value.equals(settings_value)) {
			this.settings_value = settings_value;
			inUpdateMap.put("settings_value", settings_value);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.AppletSettings#setSecondaryObject(long)
	 */
	public void setSecondaryObject(VObject inSecondaryObject) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setSecondary_object_id(theUpdateMap, inSecondaryObject.getId());
		update(theUpdateMap);
	}

	private void setSecondary_object_id(Map<String, Object> inUpdateMap, long secondary_object_id) {
		if (this.secondary_object_id != secondary_object_id) {
			this.secondary_object_id = secondary_object_id;
			inUpdateMap.put("secondary_object_id", secondary_object_id);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.AppletSettings#getPrimaryAppletSettingsObject
	 * ()
	 */
	public VObject getPrimaryAppletSettingsObject() {
		return Factories.VOBJECT.find(this.primary_object_id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.AppletSettings#getSecondaryAppletSettingsObject
	 * ()
	 */
	public VObject getSecondaryObject() {
		return Factories.VOBJECT.find(this.secondary_object_id);
	}

	@Override
	public void doDelete() throws SQLException {
		super.doDelete();
	}
}
