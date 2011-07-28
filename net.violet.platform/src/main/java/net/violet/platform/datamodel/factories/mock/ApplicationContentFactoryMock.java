package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationContent;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.ApplicationContentFactory;
import net.violet.platform.datamodel.mock.ApplicationContentMock;

public class ApplicationContentFactoryMock extends RecordFactoryMock<ApplicationContent, ApplicationContentMock> implements ApplicationContentFactory {

	public ApplicationContentFactoryMock() {
		super(ApplicationContentMock.class);
	}

	public ApplicationContent create(Application inApplication, Files inFile) {
		return new ApplicationContentMock(inApplication, inFile);
	}

	public boolean usesFiles(Files inFile) {
		for (final ApplicationContent c : findAll()) {
			if (c.getFiles().equals(inFile)) {
				return true;
			}
		}

		return false;
	}

}
