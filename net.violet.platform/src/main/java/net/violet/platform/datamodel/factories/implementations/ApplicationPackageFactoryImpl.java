package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Collections;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationPackage;
import net.violet.platform.datamodel.ApplicationPackageImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.ApplicationPackageFactory;

/**
 * DB or Memory Access to the ApplicationProfile table
 */
public class ApplicationPackageFactoryImpl extends RecordFactoryImpl<ApplicationPackage, ApplicationPackageImpl> implements ApplicationPackageFactory {

	public ApplicationPackageFactoryImpl() {
		super(ApplicationPackageImpl.SPECIFICATION);
	}

	public boolean usesFiles(Files inFile) {
		return count(null, "application_source_file_id = ?", Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

	/**
	 * @see net.violet.platform.datamodel.factories.ApplicationPackageFactory#create(net.violet.platform.datamodel.Application,
	 *      net.violet.platform.applets.AppLanguages, java.lang.String,
	 *      net.violet.platform.datamodel.Files)
	 */
	public ApplicationPackage create(Application app, AppLanguages language, String version, Files newSourceFile) throws SQLException {

		return new ApplicationPackageImpl(app, language, version, newSourceFile);
	}

}
