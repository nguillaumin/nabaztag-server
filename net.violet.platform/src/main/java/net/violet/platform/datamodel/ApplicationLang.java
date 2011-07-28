package net.violet.platform.datamodel;

import net.violet.db.records.associations.AssoRecord;

/**
 * Associative between an application ant its supported language.
 * This relation tells us that an application has translations in this language
 * and therefore can be installed by a user in this language.
 * The application has a specific rank (popularity) in this language too
 * @author christophe - Violet
 */
public interface ApplicationLang extends AssoRecord<Application, ApplicationLang> {

	Application getApplication();

	Lang getLang();

	long getRank();

	void setRank(long rnk);
}
