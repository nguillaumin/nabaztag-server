package net.violet.platform.dataobjects;

import net.violet.platform.datamodel.ObjectPreferences;
import net.violet.platform.datamodel.factories.Factories;

public class ObjectPreferencesData extends RecordData<ObjectPreferences> {

	/**
	 * Constructeur à partir d'un user.
	 */
	public ObjectPreferencesData(ObjectPreferences inPreferences) {
		super(inPreferences);
	}

	public boolean isVisible() {
		final ObjectPreferences theRecord = getRecord();
		return (theRecord != null) && theRecord.isVisible();
	}

	public boolean isPrivate() {
		final ObjectPreferences theRecord = getRecord();
		return (theRecord != null) && theRecord.isPrivate();
	}

	public void setPreferences(boolean inVisible, boolean inPrivate, ObjectLangData inLang) {
		final ObjectPreferences theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setPreferences(inVisible, inPrivate, inLang.getReference());
		}
	}

	public void setLang(ObjectLangData inLang) {
		final ObjectPreferences theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setLangPreferences(inLang.getReference());
		}
	}

	public ObjectPreferences getReference() {
		return getRecord();
	}

	public ObjectLangData getLang() {
		final ObjectPreferences theResult = getRecord();
		if ((theResult != null) && (theResult.getLangPreferences() != null)) {
			return ObjectLangData.get(theResult.getLangPreferences());
		}
		return ObjectLangData.DEFAULT_OBJECT_LANGUAGE;
	}

	public static ObjectPreferencesData createObjectPreferences(VObjectData inObject, ObjectLangData inLang) {
		return new ObjectPreferencesData(Factories.VOBJECT.createObjectPreferences(inObject.getReference(), inLang.getReference()));
	}
}
