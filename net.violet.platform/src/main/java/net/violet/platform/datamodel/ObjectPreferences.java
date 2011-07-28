package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface ObjectPreferences extends Record<ObjectPreferences> {

	boolean isVisible();

	boolean isPrivate();

	void setVisible(boolean isVisible);

	void setPrivate(boolean isPrivate);

	void setPreferences(boolean isVisible, boolean isPrivate, Lang inLang);

	/**
	 * Accesseur sur la langue de l'objet.
	 * 
	 * @return la langue.
	 */
	Lang getLangPreferences();

	/**
	 * set la langue principale primaire de l'objet.
	 * 
	 * @param inLang
	 */
	void setLangPreferences(Lang inLang);
}
