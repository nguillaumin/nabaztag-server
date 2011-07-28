package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.ApplicationApiLib;
import net.violet.platform.datamodel.ApplicationApiLibImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.FilesImpl;
import net.violet.platform.datamodel.factories.ApplicationApiLibFactory;

import org.apache.log4j.Logger;

/**
 * @author christophe - Violet
 */
public class ApplicationApiLibFactoryImpl extends RecordFactoryImpl<ApplicationApiLib, ApplicationApiLibImpl> implements ApplicationApiLibFactory {

	private static final Logger LOGGER = Logger.getLogger(ApplicationApiLibFactoryImpl.class);

	ApplicationApiLibFactoryImpl() {
		super(ApplicationApiLibImpl.SPECIFICATION);
	}

	/**
	 * @param inLanguage
	 * @param inVersion
	 * @return
	 */
	public ApplicationApiLib findByLanguageAndVersion(AppLanguages inLanguage, String inVersion) {
		return findByKey(0, new Object[] { inLanguage.name(), inVersion });
	}

	/**
	 * Returns all the api versions in the given dev language.
	 * 
	 * @param inLanguage
	 * @return
	 */
	public List<ApplicationApiLib> findAllByLanguage(AppLanguages inLanguage) {
		return findAll(" apilib_language = ? ", Arrays.asList((Object) inLanguage.name()), null);
	}

	/**
	 * Returns the last api version for the given dev language.
	 * 
	 * @param inLanguage
	 * @return the last version or <code>null</code>
	 */
	public ApplicationApiLib findLastVersionForLanguage(AppLanguages inLanguage) {

		return find(" apilib_language = ? order by apilib_version desc", Arrays.asList((Object) inLanguage.name()));
	}

	/**
	 * @throws SQLException
	 * @see net.violet.platform.datamodel.factories.ApplicationApiLibFactory#create(net.violet.platform.applets.AppLanguages,
	 *      java.lang.String, java.lang.String, java.lang.Long)
	 */
	public ApplicationApiLib create(AppLanguages language, String version, Files inCodeFile) {
		try {
			return new ApplicationApiLibImpl(language, version, (FilesImpl) inCodeFile);
		} catch (final SQLException e) {
			ApplicationApiLibFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public boolean usesFiles(Files inFile) {
		return count(null, " apilib_file_id = ? ", Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

}
