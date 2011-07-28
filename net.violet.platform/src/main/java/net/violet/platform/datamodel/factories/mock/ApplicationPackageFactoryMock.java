package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationPackage;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.ApplicationPackageFactory;
import net.violet.platform.datamodel.mock.ApplicationPackageMock;

/**
 * @author christophe - Violet
 */
public class ApplicationPackageFactoryMock extends RecordFactoryMock<ApplicationPackage, ApplicationPackageMock> implements ApplicationPackageFactory {

	public ApplicationPackageFactoryMock() {
		super(ApplicationPackageMock.class);
	}

	public boolean usesFiles(Files inFile) {
		for (final ApplicationPackage anAPackage : findAll()) {
			if (inFile.equals(anAPackage.getSourceFile())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.factories.ApplicationPackageFactory#create
	 * (net.violet.platform.datamodel.Application,
	 * net.violet.platform.applets.AppLanguages, java.lang.String,
	 * net.violet.platform.datamodel.Files)
	 */
	public ApplicationPackage create(Application app, AppLanguages language, String version, Files newSourceFile) {
		// TODO Auto-generated method stub
		return null;
	}

}
