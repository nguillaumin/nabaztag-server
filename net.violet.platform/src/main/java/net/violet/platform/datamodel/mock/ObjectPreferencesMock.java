package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ObjectPreferences;
import net.violet.platform.datamodel.VObject;

public class ObjectPreferencesMock extends AbstractMockRecord<ObjectPreferences, ObjectPreferencesMock> implements ObjectPreferences {

	private boolean visible;
	private boolean isPrivate;
	private Lang mLang;

	public ObjectPreferencesMock(VObject inObject, boolean inVisible, boolean inPrivate, Lang inLang) {
		super(inObject.getId());
		this.visible = inVisible;
		this.isPrivate = inPrivate;
		this.mLang = inLang;
	}

	public ObjectPreferencesMock(VObject inObject, Lang inLang) {
		this(inObject, true, false, inLang);
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean inVisible) {
		this.visible = inVisible;
	}

	public boolean isPrivate() {
		return this.isPrivate;
	}

	public void setPrivate(boolean inPrivate) {
		this.isPrivate = inPrivate;
	}

	public void setPreferences(boolean inVisible, boolean inPrivate, Lang inLang) {
		this.visible = inVisible;
		this.isPrivate = inPrivate;
		this.mLang = inLang;
	}

	public Lang getLangPreferences() {
		return this.mLang;
	}

	public void setLangPreferences(Lang inLang) {
		this.mLang = inLang;
	}

}
