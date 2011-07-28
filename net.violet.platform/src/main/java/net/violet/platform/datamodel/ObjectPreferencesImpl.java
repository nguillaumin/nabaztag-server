package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNull;

public class ObjectPreferencesImpl extends ObjectRecord<ObjectPreferences, ObjectPreferencesImpl> implements ObjectPreferences {

	public static final SQLObjectSpecification<ObjectPreferencesImpl> SPECIFICATION = new SQLObjectSpecification<ObjectPreferencesImpl>("object_preferences", ObjectPreferencesImpl.class, new SQLKey("object_id"));

	private static final String[] NEW_COLUMNS = new String[] { "object_id", "visible", "is_private", "object_lang", };

	protected long object_id;
	protected boolean visible;
	protected boolean is_private;
	protected long object_lang;

	private final SingleAssociationNull<ObjectPreferences, Lang, LangImpl> mLang;

	protected ObjectPreferencesImpl(long id) throws SQLException {
		init(id);
		this.mLang = new SingleAssociationNull<ObjectPreferences, Lang, LangImpl>(this, "object_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());

	}

	protected ObjectPreferencesImpl() {
		this.mLang = new SingleAssociationNull<ObjectPreferences, Lang, LangImpl>(this, "object_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());

	}

	public ObjectPreferencesImpl(VObject inObject, boolean inVisible, boolean inPrivate, Lang inLang) throws SQLException {
		this.object_id = inObject.getId();
		this.visible = inVisible;
		this.is_private = inPrivate;
		this.object_lang = inLang.getId();
		init(ObjectPreferencesImpl.NEW_COLUMNS);
		this.mLang = new SingleAssociationNull<ObjectPreferences, Lang, LangImpl>(this, "object_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());

	}

	@Override
	public SQLObjectSpecification<ObjectPreferencesImpl> getSpecification() {
		return ObjectPreferencesImpl.SPECIFICATION;
	}

	public void setPreferences(boolean inVisible, boolean inPrivate, Lang inLang) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		set_visible(theUpdateMap, inVisible);
		set_private(theUpdateMap, inPrivate);
		setUser_lang(theUpdateMap, inLang);
		update(theUpdateMap);
	}

	public boolean isPrivate() {
		return this.is_private;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setPrivate(boolean inPrivate) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		set_private(theUpdateMap, inPrivate);
		update(theUpdateMap);
	}

	private void set_private(Map<String, Object> inUpdateMap, boolean inPrivate) {
		if (inPrivate != this.is_private) {
			this.is_private = inPrivate;
			inUpdateMap.put("is_private", inPrivate);
		}
	}

	public void setVisible(boolean inVisible) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		set_visible(theUpdateMap, inVisible);
		update(theUpdateMap);
	}

	private void set_visible(Map<String, Object> inUpdateMap, boolean inVisible) {
		if (inVisible != this.visible) {
			this.visible = inVisible;
			inUpdateMap.put("visible", inVisible);
		}
	}

	public Lang getLangPreferences() {
		return this.mLang.get(this.object_lang);
	}

	public void setLangPreferences(Lang inLang) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_lang(theUpdateMap, inLang);
		update(theUpdateMap);
	}

	private void setUser_lang(Map<String, Object> inUpdateMap, Lang inLang) {
		if (this.object_lang != inLang.getId()) {
			this.object_lang = inLang.getId();
			this.mLang.set(inLang);
			inUpdateMap.put("object_lang", this.object_lang);
		}
	}
}
