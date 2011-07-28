package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.ApplicationApiLib;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

/**
 * Factory pour application_apilib
 */
public interface ApplicationApiLibFactory extends RecordFactory<ApplicationApiLib>, FilesAccessor {

	/**
	 * Find by primary key
	 * 
	 * @param inLanguage
	 * @param inVersion
	 * @return
	 */
	ApplicationApiLib findByLanguageAndVersion(AppLanguages inLanguage, String inVersion);

	/**
	 * Returns all the api versions in the given dev language.
	 * 
	 * @param inLanguage
	 * @return
	 */
	List<ApplicationApiLib> findAllByLanguage(AppLanguages inLanguage);

	/**
	 * Returns the last api version for the given dev language.
	 * 
	 * @param inLanguage
	 * @return the last version or NULL
	 */
	ApplicationApiLib findLastVersionForLanguage(AppLanguages inLanguage);

	/**
	 * @param language
	 * @param version
	 * @param releaseNotes
	 * @param object
	 * @return
	 */
	ApplicationApiLib create(AppLanguages language, String version, Files inCodeFile);
}
