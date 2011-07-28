package net.violet.platform.datamodel.factories;

import java.sql.SQLException;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationPackage;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

/**
 * Defines the DB or memory access methods to the application_package table
 */
public interface ApplicationPackageFactory extends RecordFactory<ApplicationPackage>, FilesAccessor {

	/**
	 * @param app
	 * @param language
	 * @param version
	 * @param newSourceFile
	 * @return
	 * @throws SQLException
	 */
	ApplicationPackage create(Application app, AppLanguages language, String version, Files newSourceFile) throws SQLException;
}
